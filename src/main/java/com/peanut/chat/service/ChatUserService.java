package com.peanut.chat.service;

import com.peanut.chat.constants.ChatConstants;
import com.peanut.chat.controller.ChatWebSocketHandler;
import com.peanut.chat.pojo.vo.*;
import com.peanut.chat.system.config.WebsocketConfig;
import com.peanut.chat.utils.Base64Util;
import com.peanut.dao.mapper.AddRequestMapper;
import com.peanut.dao.mapper.FriendMapper;
import com.peanut.dao.mapper.UserMapper;
import com.peanut.pojo.AddRequest;
import com.peanut.pojo.User;
import com.peanut.pojo.UserRel;
import com.peanut.pojo.VoiceRequest;
import com.peanut.utils.MD5Util;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class ChatUserService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private FriendMapper friendMapper;
    @Autowired
    private AddRequestMapper addRequestMapper;
    @Autowired
    private ChatService chatService;
    private Map<String, WebSocketSession> allClient;

    private ObjectMapper objectMapper = new ObjectMapper();

    public String handle(String value, WebSocketSession session) throws IOException {
        try{
            allClient = ChatWebSocketHandler.sessions;
            LoginVo vo;
            Response response = new Response();
            vo = objectMapper.readValue(value, LoginVo.class);

            String pass = MD5Util.getMD5(vo.getPassword());
            User u = userMapper.selectByStuId(vo.getUserName());
            if(u == null || !u.getPassword().equals(pass)){
                response.setCode(1);
                response.setMsg("用户名不存在或者密码错误");
                return objectMapper.writeValueAsString(response);
            }
            if(ChatWebSocketHandler.sessions.get(vo.getUserName()) != null){
                response.setCode(1);
                response.setMsg("用户已登录，如果非本人登录，那也没办法😂");
                return objectMapper.writeValueAsString(response);
            }

            //更新最后一次登录时间
            Date currentDate = new Date();
            userMapper.updateLastLogin(u.getStuId(), currentDate);
            u.setLastestLogin(currentDate);
            //把登录信息放入session
            u.setState(vo.getState());
            ChatWebSocketHandler.onlineUser.put(session.getId(), u);
            allClient.put(u.getStuId(), session);
            u.setPassword(vo.getPassword());
            UserVo userVO = new UserVo();
            userVO.coverUser(u);
            response.setCode(0);
            response.setMsg("登录成功");
            Map<String, Object> data = new HashMap<>();
            data.put("user", userVO);
            data.put("friend", getFriends(u));
            response.setData(data);
            return objectMapper.writeValueAsString(response);
        }catch (Exception e){
            e.printStackTrace();
        }
        return "";
    }

    private List<FriendVo> getFriends(User u) throws IOException {
        List<String> idList = friendMapper.selectFriendIds(u.getStuId());
        idList.add("00000000000");//插入一个不存在id防止查询时导致 in内的id为空报sql错误
        List<FriendVo> userList = userMapper.selectByStuIds(idList);
        Map<String, String> onlineMap = new HashMap<>();
        ChatWebSocketHandler.onlineUser.forEach((key, value) -> {
            onlineMap.put(value.getStuId(), value.getState());
        });

        userList.forEach(user -> {
            if(onlineMap.keySet().contains(user.getStuId()) && "online".equals(onlineMap.get(user.getStuId()))){
                user.setIsOnline(true);
            }else{
                user.setIsOnline(false);
            }
        });
        //如果当前登录的状态是在线不是隐身
        notifyState(u, true);

        return userList;
    }

    public void notifyState(User u, boolean isOnline) throws IOException {
        List<String> idList = friendMapper.selectFriendIds(u.getStuId());
        idList.add("00000000");
        List<FriendVo> friendVoList = userMapper.selectByStuIds(idList);
        Map<String, String> onlineMap = new HashMap<>();
        ChatWebSocketHandler.onlineUser.forEach((key, value) -> onlineMap.put(value.getStuId(), value.getState()));
        if("online".equals(u.getState())){
            //给每个好友客户端发送的上线消息
            FriendVo info = new FriendVo();
            info.cover(u);
            info.setIsOnline(isOnline);
            String infoStr = objectMapper.writeValueAsString(info);
            new Thread(() -> {
                //发送上线消息给每个在线好友
                friendVoList.forEach(user -> {
                    if(onlineMap.keySet().contains(user.getStuId())) {
                        TextMessage msg = new TextMessage(Base64Util.encode(( AbsProcessor.TO_FRIEND_ONLINE +"##" + infoStr).getBytes()));
                        try {
                            allClient.get(user.getStuId()).sendMessage(msg);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }).start();
        }
    }

    public String searchUser(String value, WebSocketSession session) throws IOException {
        Map<String ,Object> result = new HashMap<>();
        User u = userMapper.selectByStuId(value);
        if(u == null){
            result.put("code", 1);
            result.put("msg", "搜索的用户不存在");
            return objectMapper.writeValueAsString(result);
        }
        FriendVo vo = u.cover();
        result.put("code", 0);
        result.put("msg", vo);
        return objectMapper.writeValueAsString(result);
    }

    public String addFriend(String msg, WebSocketSession webSocketSession) throws IOException {
        VoiceRequest request = objectMapper.readValue(msg, VoiceRequest.class);
        if(request == null || request.getUserId() == null || request.getFriendId() == null){
            return "";
        }
        Map<String ,Object> result = new HashMap<>();
        if(request.getUserId().equals(request.getFriendId())){
            result.put("code", 1);
            result.put("msg", "无法添加自己为好友");
            return objectMapper.writeValueAsString(result);
        }

        List<String> friendIds = friendMapper.selectFriendIds(request.getUserId());
        if(friendIds.contains(request.getFriendId())){
            result.put("code", 1);
            result.put("msg", "对方已经是你的好友");
            return objectMapper.writeValueAsString(result);
        }

        AddRequest addRequest = new AddRequest();
        addRequest.setFromId(request.getUserId());
        addRequest.setToId(request.getFriendId());
        addRequest.setIsRead((byte) 0);
        addRequest.setType((byte) 0);
        addRequest.setComplete((byte) 0);
        int count = addRequestMapper.checkRepetition(addRequest);
        if(count == 0) {
            addRequestMapper.insert(addRequest);
        }
        AddRequest req = addRequestMapper.selectByUserIdAndFriendId(request.getUserId(), request.getFriendId());
        WebSocketSession session = ChatWebSocketHandler.sessions.get(request.getFriendId());
        if(session != null && req != null){
            RequestUserInfo userInfo = new RequestUserInfo();
            FriendVo friendVo = userMapper.selectFriendById(request.getUserId());
            if(friendVo != null){
                userInfo.setIsOnline(friendVo.getIsOnline());
                userInfo.setBirthday(friendVo.getBirthday());
                userInfo.setDeclaration(friendVo.getDeclaration());
                userInfo.setHeadPic(friendVo.getHeadPic());
                userInfo.setLastestLogin(friendVo.getLastestLogin());
                userInfo.setNickName(friendVo.getNickName());
                userInfo.setPersonalIntroduction(friendVo.getPersonalIntroduction());
                userInfo.setSex(friendVo.getSex());
                userInfo.setStuId(friendVo.getStuId());
                userInfo.setId(req.getId());
                userInfo.setIsRead(req.getIsRead());
                userInfo.setComplete(req.getComplete());

                String info = AbsProcessor.NEW_ADD_REQUEST + "##" + objectMapper.writeValueAsString(userInfo);
                session.sendMessage(new TextMessage(Base64Util.encode(info.getBytes())));
            }
        }
        result.put("code", 0);
        result.put("msg", "请求已经发送");

        return objectMapper.writeValueAsString(result);
    }

    public String getAllAddRequest(String userId, WebSocketSession webSocketSession) throws IOException {
        List<AddRequest> requestList = addRequestMapper.selectByToId(userId);
        List<String> ids = requestList.stream().map(AddRequest::getFromId).collect(Collectors.toList());

        ids.add("00000000000");
        List<FriendVo> friendVoList = userMapper.selectByStuIds(ids);
        Map<String, FriendVo> friendVoMap = friendVoList.stream().
                collect(Collectors.toMap(FriendVo::getStuId, item -> item));

        List<RequestUserInfo> result = new ArrayList<>();

        requestList.forEach(item -> {
            RequestUserInfo info = new RequestUserInfo();
            FriendVo friendVo = friendVoMap.get(item.getFromId());
            if(friendVo == null){
                return;
            }
            info.setIsOnline(friendVo.getIsOnline());
            info.setBirthday(friendVo.getBirthday());
            info.setDeclaration(friendVo.getDeclaration());
            info.setHeadPic(friendVo.getHeadPic());
            info.setLastestLogin(friendVo.getLastestLogin());
            info.setNickName(friendVo.getNickName());
            info.setPersonalIntroduction(friendVo.getPersonalIntroduction());
            info.setSex(friendVo.getSex());
            info.setStuId(friendVo.getStuId());

            info.setComplete(item.getComplete());
            info.setIsRead(item.getIsRead());
            info.setId(item.getId());
            result.add(info);
        });

        return objectMapper.writeValueAsString(result);
    }

    public String modifyAddRequestState(String msg, WebSocketSession webSocketSession) throws IOException {
        Map<String, Object> result = new HashMap<>();
        AddRequestResult request = objectMapper.readValue(msg, AddRequestResult.class);
        //验证是否已操作
        AddRequest addRequest = addRequestMapper.selectByPrimaryKey(request.getId());
        if(addRequest.getComplete() == 1){
            result.put("code" , 1);
            result.put("msg", "无效操作");
            return objectMapper.writeValueAsString(result);
        }

        Integer rowCount = addRequestMapper.updateById(request.getId(), request.getOperation());
        if(rowCount == 0){
            result.put("code" , 1);
            result.put("msg", "未知问题");
            return objectMapper.writeValueAsString(result);
        }
        if(request.getOperation() != null && request.getOperation() == 1) {
            UserRel userRel = new UserRel(addRequest.getFromId(), addRequest.getToId(), 0);
            friendMapper.insert(userRel);
            userRel.rotate();
            friendMapper.insert(userRel);
            WebSocketSession fromSession = ChatWebSocketHandler.sessions.get(addRequest.getFromId());
            if (fromSession != null) {
                FriendVo vo = userMapper.selectFriendById(addRequest.getToId());
                String info = AbsProcessor.NEW_FRIEND + "##" + objectMapper.writeValueAsString(vo);
                fromSession.sendMessage(new TextMessage(Base64Util.encode(info.getBytes())));
            }
            FriendVo vo = userMapper.selectFriendById(addRequest.getFromId());
            String info = AbsProcessor.NEW_FRIEND + "##" + objectMapper.writeValueAsString(vo);
            webSocketSession.sendMessage(new TextMessage(Base64Util.encode(info.getBytes())));
            //发送问候消息;
            String uuid = UUID.randomUUID().toString();
            MsgVo msgVo = new MsgVo(addRequest.getFromId(), addRequest.getToId(),
                    null, uuid, ChatConstants.sayHello, true, 1, null);
            chatService.binaryMsgReceived(objectMapper.writeValueAsString(msgVo), null);

            msgVo.setUserId(addRequest.getToId());
            msgVo.setFriendId(addRequest.getFromId());
            msgVo.setUuid(UUID.randomUUID().toString());
            chatService.binaryMsgReceived(objectMapper.writeValueAsString(msgVo), null);
        }
        result.put("code", 0);
        result.put("msg","操作成功");
        result.put("operation", request.getOperation());
        result.put("id", request.getId());
        return objectMapper.writeValueAsString(result);
    }

    public String updateUserInfo(String userInfo, WebSocketSession webSocketSession) throws IOException {
        try {
            Map<String, Object> result = new HashMap<>();
            UserVo userVo =objectMapper.readValue(userInfo, UserVo.class);
            int count = userMapper.updatePersonalInfo(userVo);
            if(count != 1){
                result.put("code", 1);
                return objectMapper.writeValueAsString(result);
            }
            result.put("code", 0);
            result.put("userInfo", userVo);
            return objectMapper.writeValueAsString(result);
        }catch (Exception e){
            e.printStackTrace();
        }
        return "";
    }
}
