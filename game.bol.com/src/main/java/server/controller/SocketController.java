package server.controller;

import io.javalin.websocket.CloseHandler;
import io.javalin.websocket.ConnectHandler;
import io.javalin.websocket.MessageHandler;

public interface SocketController extends ConnectHandler, MessageHandler, CloseHandler { }
