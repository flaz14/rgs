package com.github.flaz14.util;

import java.net.URL;
import java.nio.file.Paths;

import static java.lang.String.format;
import static java.util.Objects.requireNonNull;

public class FileName {
    private FileName() {
        throw new UnsupportedOperationException("This a collection of pure functions." +
                "Please, don't instantiate this class via reflection. " +
                "But use its static methods.");
    }

    public static String fromUrl(URL url) {
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

    /**
     * Converts given file name into another one in OS-independent manner.
     * <p>
     * The title of this method sounds bombastic. But let's recall famous
     * <a href="http://infozip.sourceforge.net/">Info-ZIP</a>
     * toolbox. It can be compiled for vast set of platforms, from ancient VMS to exotic Amiga (obviously, Info-ZIP
     * archiver handles diverse file systems correctly).
     * <p>
     * The situation with Java is much simpler. Java runs only on a few operating systems:
     * <a href="https://www.oracle.com/technetwork/java/javase/documentation/jdk10certconfig-4417031.html">
     * Oracle JDK 10 Certified System Configurations Contents</a>. They can be divided into two categories: Unix-like
     * systems and Windows.
     * <p>
     * Unix-like systems, for example, Linux, don't expose strict limitations on the name of a file: only forward slash
     * (e.g. {@code /} and zero-character (e.g. {code NULL}) are forbidden. Windows is less tolerant. There are many
     * reserved characters: {@code &lt;}, {@code &gt;}, {@code :}, {@code "}, {@code /}, {@code \}, {@code |}, {@code
     * ?}, {@code *} (<a href="https://docs.microsoft.com/en-us/windows/desktop/FileIO/naming-a-file">Naming Files,
     * Paths, and Namespaces</a> article contains complete explanation).
     * <p>
     * <p>
     * As for reserved names, like {@code CON},{@code PRN}, it's an issue of Windows shell but not file system. So this
     * method doesn't prevent reserved names.
     * <p>
     * Please note, this method is only suitable in case of base file name (e.g. a name without a path).
     *
     * @return a string where all the "suspicious" characters are replaced with underscore ({@code _}). If input file
     * name is empty, the same instance of string is returned.
     * <p>
     */
    public static String osTolerant(String fileName) {
        requireNonNull(fileName, "Input file name should not be null.");
        return fileName.replaceAll(
                FORBIDDEN_CHARACTERS_REGEX,
                SAFE_CHARACTER);
    }

    // We keep each reserved character at separate line just for
    // readability.
    //
    // Generally, Some special characters (like backslash) should be
    // escaped in regular expression. Due to absence of raw string
    // literals in Java at that time, we need to write tedious
    // escape-sequences. This is one more argument for keeping
    // pieces of regex at separate lines in the source code.
    private static final String FORBIDDEN_CHARACTERS_REGEX = "[" +
            "<" +
            ">" +
            ":" +
            "\"" +
            "/" +
            "\\\\" +
            "|" +
            "?" +
            "]";

    private static final String SAFE_CHARACTER = "_";
}

