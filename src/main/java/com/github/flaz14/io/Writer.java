package com.github.flaz14.io;

import com.github.flaz14.util.FileName;
import com.github.flaz14.util.Image;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

import static java.lang.String.format;
import static java.util.Objects.requireNonNull;

/**
 * Saves an image from memory to local disk.
 * <p>
 * This class is tied to runtime environment. So testing of the class is complicated because it requires a kind of
 * sandbox. So we haven't implemented Unit tests at all at this moment.
 */
public class Writer {
    private final Image image;

    public Writer(Image image) {
        requireNonNull(image, "Input image should not be null.");
        this.image = image;
    }

    /**
     * Writes image from memory into current directory.
     * <p>
     * The format and extension of original image will be prevented.
     */
    public void write() {
        String osTolerantFileName = FileName.osTolerant(
                image.fileName());
        try {
            File targetFile = new File(osTolerantFileName);
            ImageIO.write(
                    image.buffer(),
                    image.formatName(),
                    targetFile);

            // Seems it will be prettier to print the resultant file
            // in OS-specific manner (for example, capital drive
            // letters on Windows, etc).
            System.out.println(targetFile.getCanonicalPath());
        } catch (IOException onWrite) {
            var message = format("Failed to write image [%s] " +
                            "into current working directory [%s].",
                    image,
                    currentWorkingDirectory());
            throw new IllegalStateException(message, onWrite);
        }
    }

    private static String currentWorkingDirectory() {
        return Paths.get(".").
                toAbsolutePath().
                normalize().
                toString();
    }
}
