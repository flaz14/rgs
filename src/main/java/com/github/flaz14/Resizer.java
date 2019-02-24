package com.github.flaz14;

import java.awt.image.BufferedImage;

import static java.util.Objects.requireNonNull;

/**
 * // TODO implement cache but not right now
 */
public class Resizer {
    private final Image image;

    public Resizer(Image image) {
        requireNonNull(image, "Image buffer should not be null.");
        this.image = image;
    }

    public Image resize(int width, int height) {
        BufferedImage oldBuffer = image.buffer();
        BufferedImage newBuffer = new BufferedImage(
                width,
                height,
                BufferedImage.TYPE_INT_RGB);
        try (Graphics graphics = new Graphics(newBuffer)) {
            graphics.draw(oldBuffer, width, height);
            return new Image(
                    newBuffer,
                    image.formatName(),
                    image.fileName());
        }
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
