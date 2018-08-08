package com.peanut.chat.controller;

import com.peanut.chat.service.ChatService;
import com.peanut.chat.service.ChatUserService;
import com.peanut.chat.service.VoiceChatService;
import com.peanut.chat.utils.Base64Util;
import com.peanut.pojo.User;
import org.springframework.web.socket.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;

public class ChatWebSocketHandler implements WebSocketHandler{
    @Resource
    private ChatUserService chatUserService;
    @Resource
    private ChatService chatService;
    @Resource
    private VoiceChatService voiceChatService;
    //stuId, session 使用线程安全的Map存储用户session
    public static Map<String, WebSocketSession> sessions = new ConcurrentHashMap<>();
    //sessionId, User
    public static Map<String, User> onlineUser = new ConcurrentHashMap<>();
    //session;
    public static List<WebSocketSession> clients = new Vector<>();
    //有新的连接时调用的方法
    @Override
    public void afterConnectionEstablished(WebSocketSession webSocketSession) throws Exception {
        System.out.println("有新的连接");
        clients.add(webSocketSession);
    }
    //接收到请求
    @Override
    public void handleMessage(WebSocketSession webSocketSession, WebSocketMessage<?> msg) throws Exception {
        String msgStr = msg.getPayload().toString();
        System.out.println("接收到新消息  ：" + Base64Util.decode(msgStr.getBytes()));
        //这里根据请求code进行不同的处理
        String[] list = Base64Util.decode(msgStr.getBytes()).split("##");
        if(list.length != 2){
            return;
        }
        String result = "";
        switch (list[0]){
            case "0":
                result = chatUserService.handle(list[1], webSocketSession);
                break;
            case "2": //信息发送
                result = chatService.binaryMsgReceived(list[1], webSocketSession);
                break;
            case "4":
                result = chatService.msgStateChanged(list[1], webSocketSession);
                break;
            case "5":
                result = chatService.getUnreadMsg(list[1], webSocketSession);
                break;
            case "6":
                result = voiceChatService.onVoiceChatRequest(list[1], webSocketSession);
                break;
            case "8":
                voiceChatService.cancleVoiceChatRequest(list[1], webSocketSession);
                return;
            case "9":
                voiceChatService.acceptVoiceChatRequest(list[1], webSocketSession);
                return;
            case "10":
                voiceChatService.refuseVoiceChatRequest(list[1], webSocketSession);
                return;
            case "11":
                voiceChatService.breakVoiceChat(list[1], webSocketSession);
                return;
            case "12":
                result = chatUserService.searchUser(list[1], webSocketSession);
                break;
            case "13":
                result = chatUserService.addFriend(list[1], webSocketSession);
                break;
            case "15":
                result = chatUserService.getAllAddRequest(list[1], webSocketSession);
                break;
            case "16":
                result = chatUserService.modifyAddRequestState(list[1], webSocketSession);
                break;
            case "18":
                result = chatUserService.updateUserInfo(list[1], webSocketSession);
                break;
        }
        result = list[0] + "##" + result;
        webSocketSession.sendMessage(new TextMessage(Base64Util.encode(result.getBytes())));
    }
    //发生错误时调用的方法
    @Override
    public void handleTransportError(WebSocketSession webSocketSession, Throwable throwable) throws Exception {
        User u = onlineUser.get(webSocketSession.getId());
        chatUserService.notifyState(u, false);
        if(u != null)
            sessions.remove(u.getStuId());
        clients.remove(webSocketSession);
        onlineUser.remove(webSocketSession.getId());
        throwable.printStackTrace();
    }
    //关闭连接后调用
    @Override
    public void afterConnectionClosed(WebSocketSession webSocketSession, CloseStatus closeStatus) throws Exception {
        User u = onlineUser.get(webSocketSession.getId());
        chatUserService.notifyState(u, false);
        if(u != null)
            sessions.remove(u.getStuId());
        clients.remove(webSocketSession);
        onlineUser.remove(webSocketSession.getId());
        System.out.println(u.getStuId() + "------断开连接");
    }

    @Override
    public boolean supportsPartialMessages() {
        return false;
    }
}
