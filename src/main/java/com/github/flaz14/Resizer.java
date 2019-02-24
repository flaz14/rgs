package com.github.flaz14;

import java.awt.*;
import java.awt.image.BufferedImage;

import static java.util.Objects.requireNonNull;

/**
 *
 */
public class Resizer {
    private final BufferedImage image;

    public Resizer(BufferedImage image) {
        requireNonNull(image, "Image buffer should not be null.");
        this.image = image;
    }

    public BufferedImage resize(int width, int height) {
        BufferedImage resizedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics = resizedImage.createGraphics();
        graphics.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        graphics.drawImage(image, 0, 0, width, height, null);
        return resizedImage;
//        private float xScaleFactor, yScaleFactor = ...;
//        private BufferedImage originalImage = ...;
//        public void paintComponent (Graphics g){
//            Graphics2D g2 = (Graphics2D) g;
//            int newW = (int) (originalImage.getWidth() * xScaleFactor);
//            int newH = (int) (originalImage.getHeight() * yScaleFactor);
//            g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
//            g2.drawImage(originalImage, 0, 0, newW, newH, null);
//        }
    }

}
