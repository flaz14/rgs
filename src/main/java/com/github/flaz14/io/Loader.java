package com.github.flaz14.io;

import com.github.flaz14.Image;
import com.github.flaz14.Limitations;
import com.github.flaz14.util.FileName;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

import static java.lang.String.format;
import static java.util.Collections.unmodifiableSet;
import static java.util.Objects.requireNonNull;
import static java.util.Set.of;

/**
 *
 */
public class Loader {
    public Loader(String urlString) {
        new Check().
                nonNull(urlString).
                nonEmpty(urlString);
        try {
            url = new URL(urlString);
            new Check().protocol();
        } catch (MalformedURLException onParsing) {
            var message = format("URL string [%s] is malformed.", urlString);
            throw new IllegalArgumentException(message, onParsing);
        }
    }

    public Image load() {
        try (InputStream urlStream = url.openStream();
             ImageInputStream imageStream = ImageIO.createImageInputStream(urlStream)) {
            ImageReader reader = getFirstImage(imageStream);
            reader.setInput(imageStream);
            String formatName = getFormatName(reader);
            BufferedImage buffer = reader.read(0);
            new Check().
                    width(buffer).
                    height(buffer);
            String fileName = FileName.fromUrl(url);
            return new Image(buffer, formatName, fileName);
        } catch (IOException onRead) {
            var message = format("Failed to read image from URL [%s].", url);
            throw new IllegalStateException(message, onRead);
        }
    }

    private ImageReader getFirstImage(ImageInputStream imageStream) {
        Iterator<ImageReader> readers = ImageIO.getImageReaders(imageStream);
        if (!readers.hasNext()) {
            var message = format("There is no image at URL [%s]; " +
                            "perhaps, the URL points to the file of other type or to directory.",
                    url);
            throw new IllegalStateException(message);
        }
        return readers.next();
    }

    private String getFormatName(ImageReader reader) throws IOException {
        String formatName = reader.
                getFormatName().
                toUpperCase();
        if (!SUPPORTED_FORMATS.contains(formatName)) {
            var message = format("Format [%s] " +
                            "of the image downloaded from URL [%s] " +
                            "is not supported; " +
                            "supported formats are %s.",
                    formatName,
                    url,
                    SUPPORTED_FORMATS);
            throw new IllegalArgumentException(message);
        }
        return formatName;
    }

    private class Check {
        private Check nonNull(String urlString) {
            requireNonNull(urlString, "URL string should not be null.");
            return this;
        }

        private Check nonEmpty(String urlString) {
            if (urlString.isEmpty()) {
                throw new IllegalArgumentException("URL string should not be empty.");
            }
            return this;
        }

        private Check protocol() {
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

        private Check width(BufferedImage image) {
            var width = image.getWidth();
            if (width > Limitations.IMAGE_WIDTH_IN_PIXELS) {
                var message = format("The image at URL [%s] " +
                                "is [%d] pixels wide; " +
                                "maximum allowed width of an image is equal to [%d] pixels.",
                        url,
                        width,
                        Limitations.IMAGE_WIDTH_IN_PIXELS);
                throw new IllegalStateException(message);
            }
            return this;
        }

        private Check height(BufferedImage image) {
            var height = image.getHeight();
            if (height > Limitations.IMAGE_HEIGHT_IN_PIXELS) {
                var message = format("The image at URL [%s] " +
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


    // TODO we use set because many other protocols can be supported in future,
    // TODO also, it simplifies composition of readable and informative exception messages
    // TODO we wrap it with tree set in order to keep the order of comparison consistent.
    private static final Set<String> SUPPORTED_PROTOCOLS =
            unmodifiableSet(
                    new TreeSet<>(of("FILE", "HTTP")));

    // TODO we use set because many other protocols can be supported in future,
    // TODO also, it simplifies composition of readable and informative exception messages
    // TODO we wrap it with tree set in order to keep the order of comparison consistent.
    private static final Set<String> SUPPORTED_FORMATS =
            unmodifiableSet(
                    new TreeSet<>(of("JPEG", "PNG")));

    private final URL url;
}
