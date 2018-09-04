package engine.component;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

import static com.google.common.truth.Truth.assertThat;

class TurnProviderTest {


    TurnProvider turnProvider;
    private List<Player> players;

    private Player playerA = new Player("Player A");
    private Player playerB = new Player("Player B");

    @BeforeEach
    void setUp() {

        players = Arrays.asList(
                playerA,
                playerB);

        turnProvider = new TurnProvider();

        turnProvider.setPlayers(players);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void start_next_turn_properly_alternates_between_players() {
        turnProvider.startNextTurn();
        assertThat(turnProvider.getCurrentPlayer()).isEqualTo(playerA);
        turnProvider.startNextTurn();
        assertThat(turnProvider.getCurrentPlayer()).isEqualTo(playerB);
        turnProvider.startNextTurn();
        assertThat(turnProvider.getCurrentPlayer()).isEqualTo(playerA);
        turnProvider.startNextTurn();
        assertThat(turnProvider.getCurrentPlayer()).isEqualTo(playerB);
        turnProvider.startNextTurn();
        assertThat(turnProvider.getCurrentPlayer()).isEqualTo(playerA);
    }

    @Test
    void start_next_turn_properly_updates_turn_state() throws Exception {
        turnProvider.startNextTurn();
        assertThat(turnProvider.getCurrentPlayer()).isEqualTo(playerA);
        assertThat(turnProvider.getRoundNumber()).isEqualTo(1);
        assertThat(turnProvider.getOriginPitForTurn()).isNull();
        assertThat(turnProvider.getTargetPit()).isNull();

        turnProvider.setOriginPitForTurn(new Pit(playerA, Pit.Type.SMALL));
        turnProvider.setTargetPit(new Pit(playerA, Pit.Type.SMALL));
        turnProvider.updateMoveCountForTurn();

        turnProvider.startNextTurn();
        assertThat(turnProvider.getCurrentPlayer()).isEqualTo(playerB);
        assertThat(turnProvider.getRoundNumber()).isEqualTo(2);
        assertThat(turnProvider.getMoveCountForTurn()).isEqualTo(1);
        assertThat(turnProvider.getOriginPitForTurn()).isNull();
        assertThat(turnProvider.getTargetPit()).isNull();
    }

    @Test
    void set_origin_pit_properly_updates_turn_state() throws Exception {
        Pit pit = new Pit(playerA, Pit.Type.SMALL);
        IntStream.range(0, 6).forEach(n -> pit.add(new Stone()));
        Pit nextPit = new Pit(playerA, Pit.Type.SMALL);
        pit.setNext(nextPit);

        turnProvider.setOriginPitForTurn(pit);

        assertThat(turnProvider.getMaxMoveCountForTurn()).isEqualTo(6);
        assertThat(turnProvider.getTargetPit()).isEqualTo(nextPit);
    }

    @Test
    void set_origin_pit_does_permit_updating_while_turn_in_progess() throws Exception {
        Pit pit = new Pit(playerA, Pit.Type.SMALL);
        IntStream.range(0, 6).forEach(n -> pit.add(new Stone()));

        turnProvider.setOriginPitForTurn(pit);

        Exception exception = null;
        try {
            turnProvider.setOriginPitForTurn(pit);
        } catch (Exception ex) {
            exception = ex;
        } finally {
            assertThat(exception).isNotNull();
        }
    }

    @Test
    void update_move_count_works_as_expected() throws Exception {
        Pit pit = new Pit(playerA, Pit.Type.SMALL);
        IntStream.range(0, 6).forEach(n -> pit.add(new Stone()));
        turnProvider.setOriginPitForTurn(pit);

        assertThat(turnProvider.getMoveCountForTurn()).isEqualTo(0);
        turnProvider.updateMoveCountForTurn();
        assertThat(turnProvider.getMoveCountForTurn()).isEqualTo(1);
        turnProvider.updateMoveCountForTurn();
        turnProvider.updateMoveCountForTurn();
        assertThat(turnProvider.getMoveCountForTurn()).isEqualTo(3);
    }
}