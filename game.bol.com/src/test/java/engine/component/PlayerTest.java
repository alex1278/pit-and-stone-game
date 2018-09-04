package engine.component;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.google.common.truth.Truth.assertThat;

class PlayerTest {

    private List<Player> players = new ArrayList<>();
    private final List<String> testNames = Arrays.asList("mary", "gerald", "polly", "reginald", "markus");
    private final String pattern = "PLYR.[A-Z0-9]{4}";

    @BeforeEach
    void setUp() {
        for(String name: testNames) {
            players.add(new Player(name));
        }
    }

    @AfterEach
    void tearDown() {
        players.clear();
    }

    @Test
    void name_returns_assigned_player_name() {
        for(int i = 0; i < testNames.size(); i++) {
            String expectedName = testNames.get(i);
            Player player = players.get(i);
            assertThat(player.name).isEqualTo(expectedName);
        }
    }

    @Test
    void to_string_return_expected_format() {
        for(int i = 0; i < testNames.size(); i++) {
            String expectedName = testNames.get(i);
            String expectedPattern = "PLAYER - " + pattern + " - " + expectedName;
            Player player = players.get(i);
            assertThat(player.toString()).matches(expectedPattern);
        }
    }
}