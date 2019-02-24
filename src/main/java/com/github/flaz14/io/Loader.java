package com.github.flaz14.io;

import com.github.flaz14.Image;
import com.github.flaz14.Limits;
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
 * Loads image from network or disk into memory.
 * <p>
 * Currently only HTTP and locally mounted filesystem sources are supported.
 *
 * @see Image
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

    /**
     * Reads image from an URL and preserves information about image format and file name. So the image can be processed
     * and saved in the same form as the original.
     * <p>
     * File name is considered as "base name". E.g. a name without path but with extension.
     *
     * @return a "fresh" instance of an image. Caching for multiple loading of the same picture is not implemented
     * currently.
     */
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

    // Contains simple validations that haven't side effects.
    //
    // We use non-static inner class in order to reduce amount of dull
    // typing and make chains of validations possible.
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
            if (width > Limits.IMAGE_WIDTH_IN_PIXELS) {
                var message = format("The image at URL [%s] " +
                                "is [%d] pixels wide; " +
                                "maximum allowed width of an image is equal to [%d] pixels.",
                        url,
                        width,
                        Limits.IMAGE_WIDTH_IN_PIXELS);
                throw new IllegalStateException(message);
            }
            return this;
        }

        private Check height(BufferedImage image) {
            var height = image.getHeight();
            if (height > Limits.IMAGE_HEIGHT_IN_PIXELS) {
                var message = format("The image at URL [%s] " +
                                "is [%d] pixels high; " +
                                "maximum allowed height of an image is equal to [%d] pixels.",
                        url,
                        height,
                        Limits.IMAGE_WIDTH_IN_PIXELS);
                throw new IllegalStateException(message);
            }
            return this;
        }
    }

    // According to interview task specification, we support a few of URL
    // protocols. We can add more protocols into this map in the future
    // without modification the rest of this class.
    //
    // We keep content in `TreeMap' for the sake of predictable order of
    // validation. And we wrap the map by its unmodifiable twin just
    // for preventing of accidental modification.
    private static final Set<String> SUPPORTED_PROTOCOLS =
            unmodifiableSet(
                    new TreeSet<>(of("FILE", "HTTP")));

    // Currently we support a few image formats.
    //
    // In general, handling each format should be done with great care.
    // For example, BMP can support transparency or not depending on
    // compression method (32-bit BITFIELDS vs. 24-bit RGB respectively).
    // GIF is even more intricate (due to animation).
    //
    // The explanations about extensibility and robustness from the above
    // map are relevant for this map as well.
    private static final Set<String> SUPPORTED_FORMATS =
            unmodifiableSet(
                    new TreeSet<>(of("JPEG", "PNG")));

    private final URL url;
}
