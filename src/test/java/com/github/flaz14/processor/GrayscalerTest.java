package com.github.flaz14.processor;

import com.github.flaz14.util.Image;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.awt.image.BufferedImage;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

/**
 *
 */
class GrayscalerTest {
    @Test
    @DisplayName("Grayscales image but keeps width, height, image format and file name unchanged.")
    void happyPath() {
        int targetWidth = 8;
        int targetHeight = 3;
        Image original = new Image(
                new BufferedImage(2, 5, BufferedImage.TYPE_INT_RGB),
                "JPEG",
                "file.jpg");
        Image resized = new Resizer(original, targetWidth, targetHeight).
                process();

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