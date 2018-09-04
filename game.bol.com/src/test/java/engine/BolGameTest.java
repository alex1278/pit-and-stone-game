package engine;

import engine.component.Pit;
import engine.component.Player;
import engine.event.Move;
import engine.model.GameState;
import engine.model.PitState;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

import static com.google.common.truth.Truth.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class BolGameTest {

    BolGame game;

    private List<Player> players;

    private Player playerA = new Player("Player A");
    private Player playerB = new Player("Player B");

    @BeforeEach
    void setUp() {
        players = Arrays.asList(
                playerA,
                playerB);

        game = BolGame.setup()
                .forPlayers(playerA, playerB)
                .build();
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void setup_builder_properly_initializes_game_before_start() {
        GameState state = game.currentState();
        assertThat(state).isNotNull();
        assertThat(state.currentPlayerId).isNull();
        assertThat(state.pits).hasSize(14);
        assertThat(state.players).hasSize(2);
        state.players.forEach(playerState -> assertThat(playerState.points).isEqualTo(36));
        assertThat(state.winnerId).isNull();
    }

    @Test
    void start_intializes_beginning_of_game_correctly() throws Exception {
        GameState state = game.start();

        assertThat(state).isNotNull();
        assertThat(state.currentPlayerId).isEqualTo(playerA.id);
        assertThat(state.pits).hasSize(14);
        assertThat(state.players).hasSize(2);
        state.players.forEach(playerState -> assertThat(playerState.points).isEqualTo(36));
        assertThat(state.winnerId).isNull();
    }

    @Test
    void state_updates_correctly_when_player_starts_moving_stones_form_left_most_pit() throws Exception {
        GameState initialState = game.start();

        String playerId = playerA.id;

        IntStream.range(0, 6).forEach(n -> {
            PitState startPit = initialState.pits.get(0);
            String startingPitId = startPit.id;
            String stoneId = startPit.stones.get(n);
            PitState targetPit = initialState.pits.get(n + 1);

            Move move = new Move(playerId, stoneId, startingPitId, targetPit.id);

            try {
                GameState nextState = game.play(move);
                assertThat(nextState.currentPlayerId).isEqualTo(playerId);
                assertThat(nextState.pits.get(0).stones).hasSize(6 - (n + 1));
                assertThat(nextState.pits.get(n + 1).stones).contains(stoneId);
                if(targetPit.type == Pit.Type.SMALL) {
                    assertThat(nextState.pits.get(n + 1).stones).hasSize(7);
                } else {
                    assertThat(nextState.pits.get(n + 1).stones).hasSize(1);
                }
                nextState.players.forEach(playerState -> assertThat(playerState.points).isEqualTo(36));
            } catch (Exception ex) {
                assertThat(ex).isNull();
            }
        });

        // Last stone ends up in large pit - player a goes again
        assertThat(game.currentState().currentPlayerId).isEqualTo(playerA.id);
    }

    @Test
    void end_called_before_start_returns_expected_state() {
        GameState endState = game.end();
        assertThat(endState).isNotNull();
        endState.players.forEach(playerState -> assertThat(playerState.points).isEqualTo(36));
        assertThat(endState.winnerId).isEqualTo("Game ended in a tie!");
        assertThat(game.isOver()).isTrue();
    }

    @Test
    void isOver_returns_false_when_game_is_not_over() throws Exception {
        assertThat(game.isOver()).isFalse();
        game.start();
        assertThat(game.isOver()).isFalse();
    }

    @Test
    void isOver_return_true_after_explicit_call_to_end() throws Exception {
        game.start();
        game.end();
        assertThat(game.isOver()).isTrue();
    }
}