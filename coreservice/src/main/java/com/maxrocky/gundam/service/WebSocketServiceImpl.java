package com.maxrocky.gundam.service;

import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by yuer5 on 16/7/26.
 */
@Service
public class WebSocketServiceImpl implements WebSocketService {

    Map<String,WebSocketSession> sessions=new HashMap<String,WebSocketSession>();

    @Override
    public void addRobotSession(WebSocketSession session){
        String robotId=getRobotIdFromSession(session);
        WebSocketSession oldSession=sessions.get(robotId);
        if(oldSession!=null){
            closeSession(oldSession);
        }
        sessions.put(robotId,session);
    }

    @Override
    //TODO: message参数可能需要在函数内部实例化，通过jackson序列化并实例化，传入参数改为要传输和实据模型
    public void sendMessageToRobot(String robotId, TextMessage message) {
        WebSocketSession session=sessions.get(robotId);
        if(session!=null){
            try {
                session.sendMessage(message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public String getRobotIdFromSession(WebSocketSession session){
        return (String)session.getAttributes().get("robotId");
    }

    @Override
    public void closeSession(WebSocketSession session) {
        String robotId=getRobotIdFromSession(session);
        sessions.remove(robotId);
        if(session.isOpen()){
            try {
                session.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
