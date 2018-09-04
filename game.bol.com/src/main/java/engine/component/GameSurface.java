package engine.component;

import engine.behavior.Movable;
import engine.model.Configuration;
import engine.utility.Messages;

import java.util.*;
import java.util.stream.IntStream;

public class GameSurface {

    private Map<String, Pit> pits;

    private GameSurface(int pitCount) {
        pits = new LinkedHashMap<>(pitCount);
    }

    public Movable.Starter<Pit, Pit> move(Stone stone) {
        return new MoveUtility(stone);
    }

    public Pit get(String pitId) {
        return pits.get(pitId);
    }

    /**
     * Returns a list of all pits on the playing surface
     * @return
     */
    public List<Pit> pits() {
        List<Pit> state = new ArrayList<>(pits.values());
        return Collections.unmodifiableList(state);
    }

    /**
     * builder is a static factory method for intializing a game
     * surface builder.
     *
     * @param players
     * @param config
     * @return
     */
    public static Builder builder(List<Player> players, Configuration config) {
        return new Builder(players, config);
    }

    public static class Builder {

        private final int PER_PLAYER_LARGE_PIT_COUNT = 1;

        private final int pitCount;
        private final int stoneCount;
        private List<Player> players;
        private GameSurface surface;

        private Builder(List<Player> players, Configuration config) {
            this.players = players;
            pitCount = config.pitCountPerPlayer + PER_PLAYER_LARGE_PIT_COUNT; //add default pit
            stoneCount = config.startingStoneCountPerPit;
            surface = new GameSurface(pitCount * players.size());
        }

        public GameSurface build() {
            layoutPits();
            LinkedList<Map.Entry<String, Pit>> pitEntries = new LinkedList<>(surface.pits.entrySet());
            connectFirstAndLastPit(pitEntries);
            connectAdjacentPits(pitEntries);
            return surface;
        }

        private Pit createPit(Player owner, Pit.Type type, int stoneCount) {
            Pit pit = new Pit(owner, type);
            if(!pit.isLarge()) {
                IntStream.range(0, stoneCount).forEach(s -> pit.add(new Stone()));
            }
            surface.pits.put(pit.id, pit);
            return pit;
        }

        private void layoutPits() {
            Pit prevPit = null;
            for(Player player : players) {
                for (int n = 1; n <= pitCount ; n++) {
                    Pit.Type type = n == pitCount ? Pit.Type.LARGE : Pit.Type.SMALL;
                    Pit newPit = createPit(player, type, stoneCount);
                    if(prevPit != null) {
                        prevPit.setNext(newPit);
                        newPit.setPrevious(prevPit);
                    }
                    prevPit = newPit;
                }
            }
        }

        private void connectFirstAndLastPit(LinkedList<Map.Entry<String, Pit>> pitEntries) {
            Pit firstPit = pitEntries.getFirst().getValue();
            Pit lastPit = pitEntries.getLast().getValue();

            firstPit.setPrevious(lastPit);
            lastPit.setNext(firstPit);
        }

        private void connectAdjacentPits(List <Map.Entry<String, Pit>> pitEntries) {
            for (int i = 0; i < pitEntries.size() / players.size(); i++) {
                Pit pit = pitEntries.get(i).getValue();
                if(pit.type == Pit.Type.SMALL) {
                    int adjacentIdx = pitEntries.size() - (PER_PLAYER_LARGE_PIT_COUNT * players.size()) - i;
                    Pit adjacentPit = pitEntries.get(adjacentIdx).getValue();
                    pit.setAdjacent(adjacentPit);
                    adjacentPit.setAdjacent(pit);
                }
            }
        }
    }


    /**
     * MoveUtility implements Movable.Starter and Movable.Ender, and is used to
     * facilitate normalized behavior when moving stones throughout the game board
     * as well as provide a fluent syntax to the caller
     */
    private static class MoveUtility implements Movable.Starter<Pit, Pit>, Movable.Ender<Pit> {

        private final Stone stone;

        public MoveUtility(Stone selectedStone) {
            stone = selectedStone;
        }

        @Override
        public Movable.Ender<Pit> from(Pit origin) throws Exception {
            Stone removedStone = origin.remove(stone);
            if(removedStone == null) {
                throw new Exception(Messages.stoneDoesNotBelongToPit(stone, origin));
            }
            return this;
        }

        @Override
        public void to(Pit destination) {
            destination.add(stone);
        }
    }
}
