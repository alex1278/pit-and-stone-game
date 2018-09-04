package engine.component;

import engine.behavior.Countable;
import engine.behavior.Identifiable;
import engine.behavior.Modifyable;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class Pit extends Identifiable implements Modifyable<Stone>, Countable {

    public final Player owner;
    public final Type type;

    private Pit previous;
    private Pit next;
    private Pit adjacent;

    private Map<String, Stone> stones = new ConcurrentHashMap<>();

    public Pit(Player player, Type pitType) {
        super("PIT", 4);
        owner = player;
        type = pitType;
    }

    @Override
    public void add(Stone stone) {
        stones.putIfAbsent(stone.id, stone);
    }

    @Nullable
    @Override
    public Stone remove(Stone stone) {
        return stones.remove(stone.id);
    }

    @Nullable
    public Stone get(String stoneId) { return stones.get(stoneId); }

    /**
     * removeAll removes all stones from the pit
     * and returns them
     * @return
     */
    @Override
    public List<Stone> removeAll() {
        return stones.keySet()
                .stream()
                .map(key -> stones.remove(key))
                .collect(Collectors.toList());
    }

    /**
     * view all creates an immutable list
     * of the current stones within the pit
     * @return
     */
    public List<Stone> viewAll() {
        return Collections.unmodifiableList(new ArrayList<>(stones.values()));
    }

    @Override
    public int count() {
        return stones.size();
    }

    public boolean isLarge() {
        return type == Type.LARGE;
    }

    public Pit getPrevious() {
        return previous;
    }

    public void setPrevious(Pit previous) {
        this.previous = previous;
    }

    public Pit getNext() {
        return next;
    }

    public void setNext(Pit next) {
        this.next = next;
    }

    public void setAdjacent(Pit adjacent) {
        this.adjacent = adjacent;
    }

    @Nullable
    public Pit getAdjacent() {
        return adjacent;
    }

    public enum Type {
        SMALL,
        LARGE
    }
}
