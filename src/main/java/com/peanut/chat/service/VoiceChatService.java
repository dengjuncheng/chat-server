package com.peanut.chat.service;

import com.peanut.chat.controller.ChatWebSocketHandler;
import com.peanut.chat.pojo.vo.ChatRequestRelVo;
import com.peanut.chat.utils.Base64Util;
import com.peanut.pojo.VoiceRequest;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class VoiceChatService {
    private ObjectMapper objectMapper = new ObjectMapper();
    private static Map<String, String> voiceChatMap = new ConcurrentHashMap<>();
    public String onVoiceChatRequest(String msg, WebSocketSession session) throws IOException {
        Map<String, Object> result = new HashMap<>();
        VoiceRequest info = objectMapper.readValue(msg, VoiceRequest.class);
        WebSocketSession friendSession = ChatWebSocketHandler.sessions.get(info.getFriendId());
        //这里需要查询数据库，确保两个用户存在且是好友关系;否则返回对应的错误消息;

        if(friendSession == null){
            result.put("code", 1);
            result.put("msg", "对方未在线");
            result.put("command", info);
            return objectMapper.writeValueAsString(result);
        }
        //查看请求的好友是否在列表中
        voiceChatMap.forEach((key, value) ->{
            if(key.equals(info.getFriendId()) || value.equals(info.getFriendId())){
                result.put("code", 2);
                result.put("msg", "对方正忙");
            }
        });
        int code = 0;
        if(result.get("code") != null)
            code = (int) result.get("code");
        if(code == 2){
            result.put("command", info);
            return objectMapper.writeValueAsString(result);
        }

        VoiceRequest toFriendInfo = info.rotate();
        String requestStr = AbsProcessor.NEW_VOICE_CHAT_REQUEST + "##" + objectMapper.writeValueAsString(toFriendInfo);
        friendSession.sendMessage(new TextMessage(Base64Util.encode(requestStr.getBytes())));
        voiceChatMap.put(info.getUserId(), info.getFriendId());
        result.put("code", 0);
        result.put("msg", "正在等待对方回应");
        result.put("command", info);

        return objectMapper.writeValueAsString(result);
    }

    public void cancleVoiceChatRequest(String msg, WebSocketSession webSocketSession) throws IOException {
        VoiceRequest request = objectMapper.readValue(msg, VoiceRequest.class);
        voiceChatMap.remove(request.getUserId());
        String friendId = request.getFriendId();
        WebSocketSession friendSession = ChatWebSocketHandler.sessions.get(friendId);
        if(friendSession == null){
            return;
        }
        VoiceRequest toFriendInfo = request.rotate();
        String requestStr = AbsProcessor.CANCLE_VOICE_CHAT + "##" + objectMapper.writeValueAsString(toFriendInfo);
        friendSession.sendMessage(new TextMessage(Base64Util.encode(requestStr.getBytes())));
    }

    public void acceptVoiceChatRequest(String msg, WebSocketSession session) throws IOException {
        VoiceRequest request = objectMapper.readValue(msg, VoiceRequest.class);
        //获取最开始请求连接的id
        String requestId = request.getFriendId();
        WebSocketSession requestSession = ChatWebSocketHandler.sessions.get(requestId);
        if(voiceChatMap.get(requestId) == null || requestSession == null){
            return;
        }
        String receivedAddress = session.getRemoteAddress().getAddress().getHostAddress();
        String requestAddress = requestSession.getRemoteAddress().getAddress().getHostAddress();
        ChatRequestRelVo vo = new ChatRequestRelVo();
        vo.setUserId(requestId);
        vo.setFriendId(request.getUserId());
        vo.setTargetIp(receivedAddress);
        String info = AbsProcessor.ACCEPT_VOICE_CHAT + "##" + objectMapper.writeValueAsString(vo);
        requestSession.sendMessage(new TextMessage(Base64Util.encode(info.getBytes())));

        vo.setUserId(request.getUserId());
        vo.setFriendId(requestId);
        vo.setTargetIp(requestAddress);

        info = AbsProcessor.ACCEPT_VOICE_CHAT + "##" +objectMapper.writeValueAsString(vo);
        session.sendMessage(new TextMessage(Base64Util.encode(info.getBytes())));
    }

    public void refuseVoiceChatRequest(String msg, WebSocketSession webSocketSession) throws IOException {
        VoiceRequest request = objectMapper.readValue(msg, VoiceRequest.class);
        String id = request.getFriendId();
        voiceChatMap.remove(id);

        WebSocketSession session = ChatWebSocketHandler.sessions.get(id);
        if(session == null){
            return;
        }
        VoiceRequest vo = new VoiceRequest();
        vo.setUserId(request.getFriendId());
        vo.setFriendId(request.getUserId());
        String info = AbsProcessor.REFUSE_VOICE_CHAT + "##" + objectMapper.writeValueAsString(vo);
        session.sendMessage(new TextMessage(Base64Util.encode(info.getBytes())));
    }

    public void breakVoiceChat(String msg, WebSocketSession webSocketSession) throws IOException {
        VoiceRequest request = objectMapper.readValue(msg, VoiceRequest.class);
        voiceChatMap.remove(request.getFriendId());
        voiceChatMap.remove(request.getUserId());
        WebSocketSession session = ChatWebSocketHandler.sessions.get(request.getFriendId());
        if(session == null){
            return;
        }
        VoiceRequest vo = new VoiceRequest();
        vo.setFriendId(request.getUserId());
        vo.setUserId(request.getFriendId());
        String info = AbsProcessor.BREAK_VOICE_CHAT + "##" + objectMapper.writeValueAsString(vo);
        session.sendMessage(new TextMessage(Base64Util.encode(info.getBytes())));
    }
}
