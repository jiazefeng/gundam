package com.maxrocky.gundam.coreservice.websocket;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.maxrocky.gundam.commons.model.biz.Graph;
import com.maxrocky.gundam.coreservice.managment.RobotSnapshotManagement;
import com.maxrocky.gundam.coreservice.model.biz.RobetUpStreamInfo;
import com.maxrocky.gundam.coreservice.model.biz.RobotDownStreamInfo;
import com.maxrocky.gundam.service.WebSocketServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.Date;
import java.util.Map;

/**
 * Created by yuer5 on 16/7/26.
 */
public class IotWebSocketHandler implements WebSocketHandler {

    @Autowired
    private WebSocketServiceImpl webSocketService;

    @Autowired
    private RobotSnapshotManagement manager;

    private ObjectMapper mapper = new ObjectMapper();
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {

    }

    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
        String messageText=(String)message.getPayload();
        String robotId=webSocketService.getRobotIdFromSession(session);
        if(robotId==null){
            robotId=getRobotIdFromInitMessageText(messageText);
            if(robotId!=null) {
                session.getAttributes().put("robotId",robotId);
                webSocketService.addRobotSession(session);
            }
        }
        else{
            //TODO:处理数据逻辑
            RobetUpStreamInfo upStreamInfo = null;
            try {
                upStreamInfo = mapper.readValue(messageText, RobetUpStreamInfo.class);
                manager.UpdateReport(upStreamInfo);
            } catch (IOException e) {
                e.printStackTrace();
            }
            manager.GetDownStream(robotId);

        }
    }

    private String getRobotIdFromInitMessageText(String messageText){
        try {
            Map message = mapper.readValue(messageText,Map.class);
            String robotId=(String)message.get("robotId");
            return robotId;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        exception.printStackTrace();
        webSocketService.closeSession(session);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
        webSocketService.closeSession(session);
    }

    @Override
    public boolean supportsPartialMessages() {
        return false;
    }
}
