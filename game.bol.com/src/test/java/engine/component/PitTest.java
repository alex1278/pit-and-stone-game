package engine.component;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static com.google.common.truth.Truth.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class PitTest {

    private Pit pit;
    private List<Stone> stones;

    private final Player player = new Player("Bobby Fisher");

    @BeforeEach
    void setUp() {
        stones = Arrays.asList(new Stone(), new Stone(), new Stone());
        pit = new Pit(player, Pit.Type.SMALL);
    }

    @AfterEach
    void tearDown() {
        pit = null;
    }

    @Test
    void constructor_intializes_pit_correctly() {
        Player playerA = new Player("Player A");
        Pit smallPit = new Pit(playerA, Pit.Type.SMALL);

        Player playerB = new Player("Player B");
        Pit largePit = new Pit(playerB, Pit.Type.LARGE);

        assertEquals(playerA, smallPit.owner);
        assertEquals(Pit.Type.SMALL, smallPit.type);

        assertEquals(playerB, largePit.owner);
        assertEquals(Pit.Type.LARGE, largePit.type);
    }

    @Test
    void add_and_remove_work_as_expected() {
        for(Stone stone: stones) {
            pit.add(stone);
        }

        Stone st1= stones.get(1);
        assertEquals(pit.remove(stones.get(1)), st1);
        assertNull(pit.remove(st1));

        Stone st0 = stones.get(0);
        assertEquals(pit.remove(st0), st0);

        Stone st2 = stones.get(2);
        assertEquals(pit.remove(st2), st2);

        assertNull(pit.remove(new Stone()));
    }

    @Test
    void remove_all_works_as_expected() {
        for(Stone stone: stones) {
            pit.add(stone);
        }

        List<Stone> removedStones = pit.removeAll();
        assertThat(removedStones).containsExactlyElementsIn(stones);

        List<Stone> emptyList = pit.removeAll();
        assertThat(emptyList).isEmpty();
    }

    @Test
    void id_is_in_correct_format() {
        String pattern = "PIT.[A-Z0-9]{4}";
        assertThat(pit.id).containsMatch(pattern);
    }


}