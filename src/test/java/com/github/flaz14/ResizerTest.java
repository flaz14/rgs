package com.github.flaz14;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 *
 */
class ResizerTest {
    @Test
    @DisplayName("Throws exception when image is null.")
    void nullImage() {
        var expectedException = assertThrows(
                NullPointerException.class,
                () -> new Resizer(null));
        assertThat(
                expectedException.getMessage(),
                equalTo("Image buffer should not be null."));
    }
}