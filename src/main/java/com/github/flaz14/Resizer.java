package com.github.flaz14;

import java.awt.image.BufferedImage;

import static java.util.Objects.requireNonNull;

/**
 *
 */
public class Resizer {
    private final BufferedImage image;

    public Resizer(BufferedImage image) {
        requireNonNull(image, "Image should not be null.");
        this.image = image;
    }

    public void resize() {
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
