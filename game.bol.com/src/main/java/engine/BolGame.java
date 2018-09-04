package engine;

import com.sun.istack.internal.NotNull;
import engine.behavior.Playable;
import engine.component.*;
import engine.error.PlayableStateInvalid;
import engine.event.Move;
import engine.model.*;
import engine.utility.Messages;
import engine.utility.TurnManager;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.stream.Collectors;

public class BolGame implements Playable<Move, GameState> {

    private List<Player> players;
    private GameSurface surface;
    private TurnManager turnManager;

    private boolean gameIsOver = false;
    private boolean gameHasStarted = false;

    private BolGame() {}

    public static Builder setup() {
        return new Builder();
    }

    @Override
    public GameState start() throws PlayableStateInvalid {

        if(gameIsOver) {
            throw new PlayableStateInvalid(Messages.GAME_IS_OVER);
        }

        if(gameHasStarted) {
            throw new PlayableStateInvalid(Messages.GAME_ALREADY_STARTED);
        }

        gameHasStarted = true;
        turnManager.startNextTurn();
        return currentState();
    }

    @Override
    public GameState play(Move move) throws Exception {
        if(gameIsOver) {
            throw new Error(Messages.GAME_IS_OVER);
        }

        if(!gameHasStarted) {
            start();
        }

        Play play = getPlayableMove(move);

        if(!currentPlayerOwnsPit(play.origin)) {
            throw new Exception(Messages.CANNOT_MOVE_STONE_FROM_OPPONENT_PIT);
        }

        surface.move(play.stone)
                .from(play.origin)
                .to(play.target);

        turnManager.updateMoveCountForTurn();

        if(isLastMoveOfTurn()) {

            if(shouldPerformAdjacentCapture(play.target)) {
                performAdjacentCapture(play.target);
            }

            if(gamePlayIsComplete()) {
                return end();
            }

            turnManager.startNextTurn(currentPlayerGetsAnotherTurn(play.target));

        } else {
            updateTargetPitForTurn();
        }

        return currentState();
    }

    @Override
    public GameState end() {
        gameIsOver = true;
        return currentState();
    }

    @Override
    public boolean isOver() {
        return gameIsOver;
    }

    @NotNull
    @Override
    public GameState currentState() {
        List<PlayerState> playerStates = getPlayerState();
        Player curPlayer = turnManager.getCurrentPlayer();
        String playerId = curPlayer == null ? null : curPlayer.id;
        ActivityState activityState = gameIsOver ? ActivityState.COMPLETE : !gameHasStarted ? ActivityState.NOT_STARTED : ActivityState.PLAYING;

        return new GameState(
                playerStates,
                playerId,
                turnManager.getRoundNumber(),
                getPitState(),
                getWinnerId(playerStates),
                activityState
        );
    }

    @Nullable
    private String getWinnerId(List<PlayerState> playerStates) {
        if(!gameIsOver) {
            return null;
        }

        boolean tie = true;
        int scoreToCompare = playerStates.get(0).points;
        for(PlayerState state : playerStates) {
            if (state.points != scoreToCompare) {
                tie = false;
                break;
            }
        }

        if(tie) {
            return "Game ended in a tie!";
        }

        Optional<PlayerState> winner = playerStates
                .stream()
                .max(Comparator.comparing(playerState -> playerState.points, Integer::compare));

        return winner.get().id;
    }

    private List<PlayerState> getPlayerState() {
        List<PlayerState> playerStates = new ArrayList<>();
        players.forEach(player -> {
            playerStates.add(new PlayerState(player, getScoreForPlayer(player)));
        });
        return Collections.unmodifiableList(playerStates);
    }

    private int getScoreForPlayer(Player player) {
        int score = 0;
        for(Pit pit: surface.pits()) {
            if(pit.owner.equals(player)) {
                score += pit.count();
            }
        }
        return score;
    }

    private List<PitState> getPitState() {
        List<PitState> pits = surface.pits()
                .stream()
                .map(PitState::new).collect(Collectors.toList());
        return Collections.unmodifiableList(pits);
    }

    private void throwIfNotPlayersTurn(String playerId) throws Error {
        String curPlayerId = turnManager.getCurrentPlayer().id;
        if(!Objects.equals(curPlayerId, playerId)) {
            throw new Error(Messages.playerOutOfTurn(playerId));
        }
    }

    private Pit ensureOriginPitForTurn(Pit pit) throws Exception {
        Pit origin = turnManager.getOriginPitForTurn();
        if(origin != null) {
            return origin;
        }
        turnManager.setOriginPitForTurn(pit);
        return turnManager.getOriginPitForTurn();
    }

    private Play getPlayableMove(Move move) throws Exception {
        throwIfNotPlayersTurn(move.playerId);

        Pit origin = ensureOriginPitForTurn(surface.get(move.fromPitId));
        Pit target = turnManager.getTargetPit();

        Pit fromPit = surface.get(move.fromPitId);
        Pit toPit = surface.get(move.toPitId);

        if(!origin.equals(fromPit)) {
            throw new Error(Messages.invalidFromPit(origin, fromPit));
        }

        Stone stone = fromPit.get(move.stoneId);
        if(stone == null) {
            throw new Error(Messages.stoneDoesNotBelongToPit(move.stoneId, fromPit));
        }

        if(target.isLarge() && !currentPlayerOwnsPit(target)) {
            throw new Error(Messages.cannotPlayInOpponentsLargePit(target));
        }

        if(!target.equals(toPit)) {
            throw new Error(Messages.invalidTargetPit(origin, fromPit));
        }

        return new Play(stone, fromPit, toPit);
    }

    private boolean isLastMoveOfTurn() {
        return turnManager.getMaxMoveCountForTurn() == turnManager.getMoveCountForTurn();
    }

    private boolean currentPlayerOwnsPit(Pit pit) {
        return pit.owner.equals(turnManager.getCurrentPlayer());
    }

    private void updateTargetPitForTurn() {
        Pit pit = turnManager.getTargetPit();
        Pit newTarget = null;
        while(newTarget == null) {
            pit = pit.getNext();
            if(pit.isLarge() && !currentPlayerOwnsPit(pit)) {
                continue;
            }
            newTarget = pit;
        }
        turnManager.setTargetPit(newTarget);
    }

    private boolean shouldPerformAdjacentCapture(Pit pit) {
        return !pit.isLarge() &&
                pit.count() == 1 &&
                currentPlayerOwnsPit(pit);
    }

    private void performAdjacentCapture(Pit pit) {
        if (pit.getAdjacent() != null) {
            pit.getAdjacent()
                .removeAll()
                .forEach(pit::add);
        }
    }

    private boolean currentPlayerGetsAnotherTurn(Pit pit) {
        return pit.isLarge() && currentPlayerOwnsPit(pit);
    }

    @Nullable
    private boolean gamePlayIsComplete() {
        boolean gameOver = false;
        for(Player player : players) {
            int stonesLeft = surface.pits().stream()
                    .filter(pit -> !pit.isLarge() && pit.owner.equals(player))
                    .mapToInt(Pit::count)
                    .sum();
            if(stonesLeft == 0) {
                gameOver = true;
                break;
            }
        }
        return gameOver;
    }

    private static class Play {
        private final Stone stone;
        private final Pit origin;
        private final Pit target;

        private Play(Stone stone, Pit fromPit, Pit toPit) {
            this.stone = stone;
            this.origin = fromPit;
            this.target = toPit;
        }
    }

    /**
     * BolGame Builder class used to ensure proper
     * initialization of a game
     */
    public static class Builder {

        private final int NUM_PLAYERS_REQUIRED = 2;

        private BolGame game = new BolGame();
        private List<Player> players = new LinkedList<>();
        private Configuration config = new Configuration();
        private TurnManager turnManager;

        private Builder() {}

        public Builder forPlayers(@NotNull Player playerOne, @NotNull Player playerTwo) throws Error {
            add(playerOne);
            add(playerTwo);
            game.players = players;
            return this;
        }

        public Builder withConfiguration(Configuration configuration) {
            config = configuration;
            return this;
        }

        /**
         * Builds the game with the provided implementation of a TurnManager,
         * otherwise the game will initialize with a provided in memory default
         * implementation
         *
         * @param manager
         * @return
         */
        public Builder withTurnManager(TurnManager manager) {
            turnManager = manager;
            return this;
        }

        public BolGame build() throws Error {
            int numPlayers = players.size();
            if(numPlayers != NUM_PLAYERS_REQUIRED) {
                throw new Error(Messages.playerCountInvalid(NUM_PLAYERS_REQUIRED, numPlayers));
            }

            if(turnManager == null) {
                turnManager = new TurnProvider();
            }
            turnManager.setPlayers(players);

            game.turnManager = turnManager;
            game.surface = GameSurface
                    .builder(players, config)
                    .build();
            return game;
        }

        private void add(@NotNull Player player) throws Error {
            if(isAddable(player)) {
                players.add(player);
            }
        }

        private boolean isAddable(Player player) throws Error {
            if(players.contains(player)) {
                throw new Error(Messages.playerExists(player));
            }
            return true;
        }
    }
}
