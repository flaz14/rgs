package com.github.flaz14.io;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 *
 */
class WriterTest {
    @Test
    @DisplayName("Throws exception when input image is null.")
    void nullImage() {
        var exception = assertThrows(
                NullPointerException.class,
                () -> new Writer(null));
        assertThat(
                exception.getMessage(),
                equalTo("Input image should not be null."));
    }

    @Test
    @DisplayName("Writes file into current directory.")
    void happyPath() {

    }
}