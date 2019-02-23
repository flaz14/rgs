package com.github.flaz14;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;

import static java.lang.String.format;
import static java.util.Collections.unmodifiableSet;
import static java.util.Set.of;

/**
 *
 */
class Loader {
    Loader(String urlString) {
        new UrlRequirement().
                nonNull(urlString).
                nonEmpty(urlString);
        try {
            url = new URL(urlString);
        } catch (MalformedURLException onParsing) {
            var message = format("URL string [%s] is malformed.", urlString);
            throw new IllegalArgumentException(message, onParsing);
        }
        new UrlRequirement().protocol();
    }

    BufferedImage load() {
        try {
            BufferedImage image = ImageIO.read(url);
            if (image == null) {
                var message = format("There is no image at URL [%s]; " +
                                "perhaps, the URL points to the file of other type or to directory.",
                        url);
                throw new IllegalStateException(message);
            }
            new ImageRequirement().
                    width(image).
                    height(image);
            return image;
        } catch (IOException onRead) {
            var message = format("Failed to read image from URL [%s].", url);
            throw new IllegalStateException(message, onRead);
        }
    }

    private class UrlRequirement {
        UrlRequirement nonNull(String urlString) {
            Objects.requireNonNull(urlString, "URL string should not be null.");
            return this;
        }

        UrlRequirement nonEmpty(String urlString) {
            if (urlString.isEmpty()) {
                throw new IllegalArgumentException("URL string should not be empty.");
            }
            return this;
        }

        UrlRequirement protocol() {
            var protocol = url.
                    getProtocol().
                    toUpperCase();
            if (!SUPPORTED_PROTOCOLS.contains(protocol)) {
                var message = format("Protocol [%s] " +
                                "specified in URL string [%s] " +
                                "is not supported; " +
                                "supported protocols are %s.",
                        protocol,
                        url,
                        SUPPORTED_PROTOCOLS);
                throw new IllegalArgumentException(message);
            }
            return this;
        }

        // TODO we use set because many other protocols can be supported in future,
        // TODO also, it simplifies composition of readable and informative exception messages
        // TODO we wrap it with tree set in order to keep the order of comparison consistent.
        private final Set<String> SUPPORTED_PROTOCOLS =
                unmodifiableSet(
                        new TreeSet<>(of("FILE", "HTTP")));
    }

    private class ImageRequirement {
        ImageRequirement width(BufferedImage image) {
            var width = image.getWidth();
            if (width > Limitations.IMAGE_WIDTH_IN_PIXELS) {
                var message = String.format("The image at URL [%s] " +
                                "is [%d] pixels wide; " +
                                "maximum allowed width of an image is equal to [%d] pixels.",
                        url,
                        width,
                        Limitations.IMAGE_WIDTH_IN_PIXELS);
                throw new IllegalStateException(message);
            }
            return this;
        }

        ImageRequirement height(BufferedImage image) {
            var height = image.getHeight();
            if (height > Limitations.IMAGE_HEIGHT_IN_PIXELS) {
                var message = String.format("The image at URL [%s] " +
                                "is [%d] pixels high; " +
                                "maximum allowed height of an image is equal to [%d] pixels.",
                        url,
                        height,
                        Limitations.IMAGE_WIDTH_IN_PIXELS);
                throw new IllegalStateException(message);
            }
            return this;
        }
    }

    private final URL url;
}
