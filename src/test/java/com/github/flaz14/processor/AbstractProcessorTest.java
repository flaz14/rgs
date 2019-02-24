package com.github.flaz14.processor;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.awt.image.BufferedImage;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 *
 */
class AbstractProcessorTest {
    @Test
    @DisplayName("Throws exception when image is null.")
    void nullImage() {
        var expectedException = assertThrows(
                NullPointerException.class,
                () -> new AbstractProcessor(null) {
                    @Override
                    protected BufferedImage getTargetBuffer() {
                        throw new UnsupportedOperationException("This method shouldn't be invoked during the test.");
                    }
                });
        assertThat(
                expectedException.getMessage(),
                equalTo("Source image should not be null."));
    }
}