package com.github.flaz14.limit.wrapper;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 *
 */
class PermissibleDimensionsTest {
    @Test
    @DisplayName("Throws exception when width is too small.")
    void smallWidth() {
        var exception = assertThrows(
                IllegalArgumentException.class,
                () -> new PermissibleDimensions(0, 50));
        assertThat(
                exception.getMessage(),
                equalTo("Image width should be in range of [1..4096] " +
                        "pixels (inclusive) but 0 has been specified."));
    }

    @Test
    @DisplayName("Throws exception when width is too large.")
    void largeWidth() {
        var exception = assertThrows(
                IllegalArgumentException.class,
                () -> new PermissibleDimensions(9000, 50));
        assertThat(
                exception.getMessage(),
                equalTo("Image width should be in range of [1..4096] " +
                        "pixels (inclusive) but 9000 has been specified."));
    }

    @Test
    @DisplayName("Throws exception with height is too small.")
    void smallHeight() {
        var exception = assertThrows(
                IllegalArgumentException.class,
                () -> new PermissibleDimensions(20, 0));
        assertThat(
                exception.getMessage(),
                equalTo("Image height should be in range of [1..4096] " +
                        "pixels (inclusive) but 0 has been specified."));
    }

    @Test
    @DisplayName("Throws exception with height is too large.")
    void largeHeight() {
        var exception = assertThrows(
                IllegalArgumentException.class,
                () -> new PermissibleDimensions(20, 10000));
        assertThat(
                exception.getMessage(),
                equalTo("Image height should be in range of [1..4096] " +
                        "pixels (inclusive) but 10000 has been specified."));
    }
}