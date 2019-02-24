package com.github.flaz14;

import java.net.URL;
import java.nio.file.Paths;

import static java.lang.String.format;
import static java.util.Objects.requireNonNull;

/**
 *
 */
public class FileNameUtil {
    public static String fileNameFromUrl(URL url) {
        requireNonNull(url, "URL should not be null.");
        var path = url.getPath();
        var fileName = Paths.
                get(path).
                getFileName();
        if (fileName == null) {
            var message = format("Failed to extract file name from URL [%s] " +
                            "because path segment of the URL is empty; " +
                            "seems that the URL points to the root of hierarchy.",
                    url);
            throw new IllegalArgumentException(message);
        }
        return fileName.toString();
    }
}
