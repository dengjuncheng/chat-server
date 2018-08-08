package com.peanut.chat.service;

import com.peanut.chat.constants.ChatConstants;
import com.peanut.chat.controller.ChatWebSocketHandler;
import com.peanut.chat.pojo.vo.MsgVo;
import com.peanut.chat.utils.Base64Util;
import com.peanut.dao.mapper.UserMsgMapper;
import com.peanut.dao.mapper.UserMsgRelMapper;
import com.peanut.pojo.UserMsg;
import com.peanut.pojo.UserMsgRel;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class  ChatService {
    @Autowired
    UserMsgMapper userMsgMapper;
    @Autowired
    UserMsgRelMapper userMsgRelMapper;
    private ObjectMapper objectMapper = new ObjectMapper().
            configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false).
            setSerializationInclusion(Inclusion.NON_NULL);

    @Transactional
    public String binaryMsgReceived(String data, WebSocketSession session) throws IOException {

       Map<String, Object> result = new HashMap<>();
       MsgVo msgVo = null;
       try {
           msgVo = objectMapper.readValue(data, MsgVo.class);
       } catch (IOException e) {
           e.printStackTrace();
           result.put("code", 1);
           result.put("msg", "json转换出错");
           result.put("uuid",msgVo.getUuid());
           return objectMapper.writeValueAsString(result);
       }
       if(msgVo.getMsgType() == 2){
           msgVo.setMsg("<img src='" +ChatConstants.currentPath + msgVo.getUserId() + "/" + msgVo.getUuid() + ".png ' width ='100' />");
       }

       // 插入数据库
       UserMsg userMsg = new UserMsg();
       userMsg.cover(msgVo);
       userMsgMapper.insert(userMsg);
       Integer msgId = userMsg.getId();
       if(msgId == null || msgId == 0){
           throw new RuntimeException("数据错误");
       }
       UserMsgRel userMsgRel = new UserMsgRel();
       userMsgRel.cover(msgVo, msgId);
       userMsgRelMapper.insertSelective(userMsgRel);

       if(msgVo == null){
           throw new RuntimeException("json转换出错");
       }

       //检查接受方是否在线，在线则把消息发送过去
       String friendId = msgVo.getFriendId();
       WebSocketSession friendSession = ChatWebSocketHandler.sessions.get(friendId);
       if(friendSession != null && friendSession.isOpen()){
           MsgVo toVo = msgVo.rotate();
           toVo.setSendTime((int)(System.currentTimeMillis()/1000));
           String value = objectMapper.writeValueAsString(toVo);
           TextMessage msg = new TextMessage(Base64Util.encode(( AbsProcessor.NEW_MSG +"##" + value).getBytes()));
           friendSession.sendMessage(msg);
       }

       result.put("code", 0);
       result.put("msg", "发送成功");
       result.put("msgInfo",msgVo);
       return objectMapper.writeValueAsString(result);
    }

    public String msgStateChanged(String data, WebSocketSession session) throws IOException {
        Integer count = userMsgRelMapper.updateStateByUuId(data);
        return data;
    }

    public String getUnreadMsg(String stuId, WebSocketSession session) throws IOException {
        Map<String, Object> result = new HashMap<>();
        if(ChatWebSocketHandler.sessions.get(stuId) == null){
            result.put("code", 1);
            return objectMapper.writeValueAsString(result);
        }
        List<MsgVo> unreadMsgs = userMsgRelMapper.getUnreadMsg(stuId);
        result.put("code", 0);
        result.put("msgInfo", unreadMsgs);
        return objectMapper.writeValueAsString(result);
    }

    public static void main(String[] args) {
        Boolean[] value = new Boolean[10000];
        for(int i = 0; i< 10000; i++){
            value[i] = false;
        }
        int count = 0;
        Long t1 = System.currentTimeMillis();
        while(count < 10000){
            int b = (int) (Math.random() *10000);
            if(!value[b]){
                value[b]=true;
                System.out.println(b);
                count++;
            }
        }
        System.out.println("花费时间：" + (System.currentTimeMillis() - t1)/1000.0);
    }
}
