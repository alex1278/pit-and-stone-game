package engine.component;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.google.common.truth.Truth.assertThat;

class StoneTest {

    private List<Stone> stones;
    private final String pattern = "ST.[A-Z0-9]{4}";

    // Current upper bound of stone count is 72
    // 2 players X 36 stones
    private final int testCases = 72;

    @BeforeEach
    void setUp() {
        stones = new ArrayList(testCases);
        for(int i = 0; i < testCases; i++) {
            stones.add(new Stone());
        }
    }

    @AfterEach
    void tearDown() {
        stones.clear();
    }

    @Test
    void id_is_in_correct_format() {
        stones.forEach(s -> assertThat(s.id).containsMatch(pattern));
    }

    @Test
    void no_duplicate_ids() {
        List<String> listOfIds = stones.stream().map(i -> i.id).collect(Collectors.toList());
        assertThat(listOfIds).containsNoDuplicates();
    }

    @Test
    void  to_string_returns_readable_format() {
        String stringified = "STONE - "+ pattern;
        stones.forEach(s -> assertThat(s.toString()).containsMatch(stringified));
    }
}