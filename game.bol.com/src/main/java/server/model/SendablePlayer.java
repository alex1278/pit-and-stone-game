package server.model;

import engine.component.Player;
import io.javalin.websocket.WsSession;
import server.Sendable;

public class SendablePlayer extends Player implements Sendable {

    private WsSession session;

    public SendablePlayer(String name, WsSession session) {
        super(name);
        this.session = session;
    }

    @Override
    public void send(String message) {
        if(session.isOpen()) {
            session.send(message);
        }
    }
}
