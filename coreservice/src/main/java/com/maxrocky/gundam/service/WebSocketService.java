package com.maxrocky.gundam.service;

import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

/**
 * Created by yuer5 on 16/7/26.
 */
public interface WebSocketService {
    void addRobotSession(WebSocketSession session);

    void sendMessageToRobot(String robotId, TextMessage message);

    String getRobotIdFromSession(WebSocketSession session);

    void closeSession(WebSocketSession session);
}
