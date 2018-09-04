package engine.component;

import engine.utility.Messages;
import engine.utility.TurnManager;

import java.util.Iterator;
import java.util.List;


/**
 * TurnProvider is a default implementation of a TurnManager
 * that manages turn state in memory
 */
public class TurnProvider implements TurnManager {

    private int roundNumber = 0;

    private Player currentPlayer;
    private Pit originPit;
    private Pit targetPit;
    private int maxMoveCountForTurn = 0;
    private int currentMoveCount = 0;

    private List<Player> players;
    private Iterator<Player> playerIterator;

    public TurnProvider() { }

    @Override
    public void startNextTurn() {
        if(!playerIterator.hasNext()) {
            playerIterator = players.iterator();
        }
        startTurn(playerIterator.next());
    }

    @Override
    public void startNextTurn(boolean curPlayerGoesAgain) {
        startTurn(currentPlayer);
    }

    @Override
    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    @Override
    public void setOriginPitForTurn(Pit pit) throws Exception {
        if(originPit != null) {
            throw new Exception(Messages.turnInProgress(currentPlayer));
        }
        originPit = pit;
        targetPit = pit.getNext();
        maxMoveCountForTurn = pit.count();
    }

    @Override
    public Pit getOriginPitForTurn() {
        return originPit;
    }

    @Override
    public void setTargetPit(Pit pit) {
        targetPit = pit;
    }

    @Override
    public Pit getTargetPit() {
        return targetPit;
    }

    @Override
    public int getRoundNumber() {
        return roundNumber;
    }

    @Override
    public void updateMoveCountForTurn() {
        currentMoveCount++;
    }

    @Override
    public int getMoveCountForTurn() {
        return currentMoveCount;
    }

    @Override
    public int getMaxMoveCountForTurn() {
        return maxMoveCountForTurn;
    }

    @Override
    public void setPlayers(List<Player> players) {
        this.players = players;
        playerIterator = players.iterator();
    }

    private void startTurn(Player player) {
        currentPlayer = player;
        originPit = null;
        targetPit = null;
        roundNumber++;
    }
}
