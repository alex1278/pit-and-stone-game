package engine.utility;

import engine.component.Pit;
import engine.component.Player;

import java.util.List;

public interface TurnManager {
    void startNextTurn();
    void startNextTurn(boolean curPlayerGoesAgain);
    Player getCurrentPlayer();
    void setOriginPitForTurn(Pit pit) throws Exception;
    Pit getOriginPitForTurn();
    void setTargetPit(Pit pit);
    Pit getTargetPit();
    int getRoundNumber();
    void updateMoveCountForTurn();
    int getMoveCountForTurn();
    int getMaxMoveCountForTurn();
    void setPlayers(List<Player> players);
}
