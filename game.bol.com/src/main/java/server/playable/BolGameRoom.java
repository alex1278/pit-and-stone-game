package server.playable;

import engine.BolGame;
import engine.component.Player;
import engine.event.Move;
import engine.model.GameState;
import engine.model.PlayerState;
import org.jetbrains.annotations.Nullable;
import server.model.SendablePlayer;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class BolGameRoom implements GameRoom<SendablePlayer, Move, GameState> {

    public final String id;
    private Set<SendablePlayer> players = ConcurrentHashMap.newKeySet();
    private BolGame game;

    public BolGameRoom(String roomId) {
        id = roomId;
    }

    @Override
    public GameState start() throws Exception {
        Iterator<SendablePlayer> iterator = players.iterator();

        game = BolGame.setup()
                .forPlayers(iterator.next(), iterator.next())
                .build();

        return game.start();
    }

    @Override
    public GameState play(Move move) throws Exception {
        return game.play(move);
    }

    @Override
    public GameState end() throws Exception {
        return game.end();
    }

    @Override
    public boolean isOver() {
        return game.isOver();
    }

    @Nullable
    @Override
    public GameState currentState() {
        if(game == null) { // Game has not started yet
            List<PlayerState> playerStates = players
                    .stream()
                    .map(p -> new PlayerState(p, 0))
                    .collect(Collectors.toList());
            return new GameState(playerStates);
        } else {
            return game.currentState();
        }
    }

    @Override
    public void add(SendablePlayer participant) throws Exception {
        if(players.size() >= 2) {
            throw new Exception("Game is already full");
        }
        players.add(participant);
    }

    @Override
    public List<SendablePlayer> getPlayers() {
        return new ArrayList<>(players);
    }

    @Override
    public boolean canStart() {
        return players.size() == 2;
    }
}
