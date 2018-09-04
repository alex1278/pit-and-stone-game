package engine.component;

import engine.model.Configuration;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static com.google.common.truth.Truth.assertThat;

class GameSurfaceTest {

    private GameSurface surface;

    private List<Player> players;
    private Configuration config;


    @BeforeEach
    void setUp() {
        players = Arrays.asList(
                new Player("Player A"),
                new Player("Player B"));

        config = new Configuration();

        surface = GameSurface
                .builder(players, config)
                .build();
    }

    @AfterEach
    void tearDown() {

    }

    @Test
    void moving_single_stone_updates_pits_correctly() throws Exception {
        List<Pit> pits = surface.pits();

        Pit pitA = pits.get(2);
        Stone stone = pitA.viewAll().get(1);
        Pit pitB = pits.get(3);

        surface.move(stone).from(pitA).to(pitB);

        assertThat(pitA.count()).isEqualTo(5);
        assertThat(pitA.viewAll()).doesNotContain(stone);
        assertThat(pitB.count()).isEqualTo(7);
        assertThat(pitB.viewAll()).contains(stone);
    }

    @Test
    void moving_stones_in_bulk_works_as_expected() throws Exception {
        List<Pit> pits = surface.pits();

        Pit pit = pits.get(0);
        Pit adjacentPit = pit.getAdjacent();

        List<Stone> stones = pit.viewAll();

        for(Stone stone: stones) {
            surface.move(stone).from(pit).to(adjacentPit);
        }

        assertThat(pit.count()).isEqualTo(0);
        assertThat(pit.viewAll()).isEmpty();
        assertThat(adjacentPit.count()).isEqualTo(12);
        assertThat(adjacentPit.viewAll()).containsAllIn(stones);
    }

    @Test
    void get() {
        List<Pit> pits = surface.pits();
        Pit pit = pits.get(0);
        assertThat(surface.get(pit.id)).isEqualTo(pit);
    }

    @Test
    void builder_initalized_pits_with_expected_size() {
        assertThat(surface.pits()).hasSize(14);
    }

    @Test
    void builder_initalized_all_pits_with_correct_next_pointer() {
        List<Pit> pits = surface.pits();
        for (int i = 0; i < pits.size() - 1; i++) {
            Pit curPit = pits.get(i);
            Pit nextPit = pits.get(i + 1);
            assertThat(curPit.getNext()).isEqualTo(nextPit);
        }
        Pit lastPit = pits.get(pits.size() - 1);
        Pit firstPit = pits.get(0);
        assertThat(lastPit.getNext()).isEqualTo(firstPit);
    }

    @Test
    void builder_initalized_all_pits_with_correct_prev_pointer() {
        List<Pit> pits = surface.pits();
        for (int i = 0; i < pits.size() - 1; i++) {
            Pit curPit = pits.get(i);
            Pit nextPit = pits.get(i + 1);
            assertThat(nextPit.getPrevious()).isEqualTo(curPit);
        }
        Pit lastPit = pits.get(pits.size() - 1);
        Pit firstPit = pits.get(0);
        assertThat(firstPit.getPrevious()).isEqualTo(lastPit);
    }

    @Test
    void builder_initalized_all_pits_with_correct_adjacent_pointer() {
        List<Pit> pits = surface.pits();
        for (int i = 0; i < pits.size() / 2; i++) {
            Pit pit = pits.get(i);
            if(pit.type == Pit.Type.LARGE) {
                assertThat(pit.getAdjacent()).isNull();
            } else {
                int adjacentIdx = pits.size() - 2 - i;
                Pit adjacentPit = pits.get(adjacentIdx);
                assertThat(pit.getAdjacent()).isEqualTo(adjacentPit);
                assertThat(adjacentPit.getAdjacent()).isEqualTo(pit);
            }
        }
    }
}