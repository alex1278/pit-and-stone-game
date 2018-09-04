package engine.model;

public class Configuration {

    public final int pitCountPerPlayer;
    public final int startingStoneCountPerPit;

    /**
     * Initializing a configuration object with its empty constructor
     * sets the playerId pit count and starting stone count per pit to the default of 6
     * and gamePlayDirection to the right
     */
    public Configuration() {
        pitCountPerPlayer = 6;
        startingStoneCountPerPit = 6;
    }

    /**
     * sets the number of puts per playerId excluding the players respective large pit
     *
     * @param numPitsPerPlayer
     */
    public Configuration(Integer numPitsPerPlayer) {
        pitCountPerPlayer = numPitsPerPlayer;
        startingStoneCountPerPit = 6;
    }

    /**
     * sets the number of puts per playerId excluding the players respective large pit
     * as well as the number of stones in each pit at the beginning of the game
     *
     * @param numPitsPerPlayer
     * @param startingStoneCount
     */
    public Configuration(Integer numPitsPerPlayer, Integer startingStoneCount) {
        pitCountPerPlayer = numPitsPerPlayer;
        startingStoneCountPerPit = startingStoneCount;
    }
}
