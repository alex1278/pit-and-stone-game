package engine.behavior;

import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.google.common.truth.Truth.assertThat;

class IdentifiableTest {

    private List<Identifiable> identifiables = new ArrayList<>();

    private final int testCases = 10000;

    @BeforeEach
    void setUp() {
        for(int i = 0;i < testCases; i++) {
            identifiables.add(new Identifiable("pre", 5));
        }
    }

    @AfterEach
    void tearDown() {
        identifiables.clear();
    }

    @Test
    void test_id_is_unique() {
        List<String> listOfIds = identifiables.stream().map(i -> i.id).collect(Collectors.toList());
        assertThat(listOfIds).containsNoDuplicates();
    }

    @Test
    void test_equals_and_hash_code() {
        EqualsVerifier.forClass(Identifiable.class)
                .usingGetClass()
                .verify();
    }
}