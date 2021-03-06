package com.github.flaz14.io;

import com.github.flaz14.limit.Limits;
import com.github.flaz14.limit.wrapper.PermissibleUrl;
import com.github.flaz14.util.FileName;
import com.github.flaz14.util.Image;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Iterator;

import static com.github.flaz14.limit.Limits.SUPPORTED_FORMATS;
import static java.lang.String.format;
import static java.util.Objects.requireNonNull;

/**
 * Loads image from network or disk into memory.
 * <p>
 * Currently only HTTP and locally mounted filesystem sources are supported.
 *
 * @see Image
 */
public class Loader {
    public Loader(PermissibleUrl url) {
        requireNonNull(url, "URL wrapper should not be null");
        this.url = url.get();
    }

    /**
     * Reads image from an URL and preserves information about image format and file name. So the image can be processed
     * and saved in the same form as the original.
     * <p>
     * File name is considered as "base name". E.g. a name without a path but with extension.
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
            checkWidth(buffer);
            checkHeight(buffer);
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

    // We can verify dimensions of an image only after we've
    // loaded it.
    //
    // Actually, we could use corresponding utility class for
    // validation of the dimensions (e.g. `PermissibleDimensions').
    // But we validate width and height of loaded image with aid
    // of specific methods in order to provide informative
    // exception messages. So a user (or third-party developer)
    // can know what exact image (we included URL into exception
    // message) has incorrect size.
    private void checkWidth(BufferedImage image) {
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
    }

    private void checkHeight(BufferedImage image) {
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
    }

    private final URL url;
}
