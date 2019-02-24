package com.github.flaz14.util;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 *
 */
// TODO just for try-with-resources for dispose()
public class Graphics implements AutoCloseable {
    public Graphics(BufferedImage target) {
        this.graphics2D = target.createGraphics();
        this.graphics2D.setRenderingHint(
                RenderingHints.KEY_INTERPOLATION,
                RenderingHints.VALUE_INTERPOLATION_BILINEAR);
    }

    public void draw(BufferedImage source, int width, int height) {
        this.graphics2D.drawImage(
                source,
                0, 0,
                width, height,
                null);
    }

    @Override
    public void close() {
        graphics2D.dispose();
    }

    private final Graphics2D graphics2D;
}