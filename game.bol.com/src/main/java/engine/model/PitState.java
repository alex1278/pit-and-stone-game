package engine.model;

import engine.component.Pit;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * PitState is a simple readonly class that represents
 * the state of a Pit on the game surface
 */
public class PitState {
    public final String id;
    public final Pit.Type type;
    public final List<String> stones;
    public final String ownerId;

    /**
     * Initialize PitState by providing the constructor in instance of a pit
     * @param pit
     */
    public PitState(Pit pit) {
        id = pit.id;
        type = pit.type;
        stones = Collections.unmodifiableList(
                pit.viewAll().stream().map(stone -> stone.id).collect(Collectors.toList()));
        ownerId = pit.owner.id;
    }
}
