package ua.foxminded.javaspring.consoleMenu.util;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class MyRandomTest {

    int bound = 10;
    MyRandom random = new MyRandom();

    @Test
    void generateBetweenOneAnd_shouldReturnIntegerValue_whenIsCalled() {
        assertAll(
                () -> assertThat(random.getInt(bound)).isLessThanOrEqualTo(bound),
                () -> assertThat(random.getInt(bound)).isLessThanOrEqualTo(bound),
                () -> assertThat(random.getInt(bound)).isLessThanOrEqualTo(bound)
        );
    }

    @Test
    void getLong_shouldReturnRandomLongNumber_whenIsCalled() {
        assertAll(
                () -> assertThat(random.getLong(bound)).isLessThanOrEqualTo(bound),
                () -> assertThat(random.getLong(bound)).isLessThanOrEqualTo(bound),
                () -> assertThat(random.getLong(bound)).isLessThanOrEqualTo(bound)
        );

    }
}
