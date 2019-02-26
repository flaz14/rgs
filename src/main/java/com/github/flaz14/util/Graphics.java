package com.github.flaz14.util;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Wrapper for {@link Graphics2D} class with automatic resource handling.
 * <p>
 * While this class does some useful job, the main purpose of its implementation was resource management. Once work is
 * done, {@link java.awt.Graphics#dispose()} should be called. We do exactly the same thing but in auto-closable manner.
 * So our custom class can be used in try-with-resources statement as habitual AutoCloseable-JDK classes.
 * <p>
 * This class makes low-level manipulations. Such functionality can be verified only via white-box testing. Perhaps, it
 * will be better do not implement Unit tests for this class at all rather that confuse third-party developers with
 * bloated and over-mocked test code.
 */
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