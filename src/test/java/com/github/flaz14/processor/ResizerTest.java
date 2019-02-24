package com.github.flaz14.processor;

import com.github.flaz14.Image;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.awt.image.BufferedImage;

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

    @Test
    @DisplayName("Resizes image according to desired width and height but keeps image type and file name unchanged.")
    void happyPath() {
        int targetWidth = 8;
        int targetHeight = 3;
        Image original = new Image(
                new BufferedImage(2, 5, BufferedImage.TYPE_INT_RGB),
                "JPEG",
                "file.jpg");
        Image resized = new Resizer(original).
                resize(targetWidth, targetHeight);

        // We compare only actual width and height, there is useless effort to compare buffers.
        assertThat(
                resized.buffer().getWidth(),
                equalTo(targetWidth));
        assertThat(
                resized.buffer().getHeight(),
                equalTo(targetHeight));

        // But other properties are unchanged
        assertThat(
                resized.formatName(),
                equalTo(original.formatName()));
        assertThat(
                resized.fileName(),
                equalTo(original.fileName()));
    }
}