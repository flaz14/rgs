package com.github.flaz14;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.AllOf.allOf;
import static org.junit.jupiter.api.Assertions.assertThrows;

class LoaderTest {
    @Nested
    class Constructor {
        @Test
        @DisplayName("Throws exception when URL string is null.")
        void nullUrl() {
            var expectedException = assertThrows(
                    NullPointerException.class,
                    () -> new Loader(null));
            assertThat(
                    expectedException.getMessage(),
                    equalTo("URL string should not be null."));
        }

        @Test
        @DisplayName("Throws exception when URL string is empty.")
        void emptyUrl() {
            var expectedException = assertThrows(
                    IllegalArgumentException.class,
                    () -> new Loader(""));
            assertThat(
                    expectedException.getMessage(),
                    equalTo("URL string should not be empty."));
        }

        @Test
        @DisplayName("Throws exception when given protocol is not supported.")
        void unsupportedProtocol() {
            var expectedException = assertThrows(
                    IllegalArgumentException.class,
                    () -> new Loader("ftp://someftp.com"));
            assertThat(
                    expectedException.getMessage(),
                    equalTo(
                            "Protocol [FTP] specified in URL string [ftp://someftp.com] is not supported; " +
                                    "supported protocols are [FILE, HTTP]."));
        }

        @Test
        @DisplayName("Throws exception when URL string is malformed.")
        void malformedUrl() {
            var expectedException = assertThrows(
                    IllegalArgumentException.class,
                    () -> new Loader("invalid"));
            assertThat(
                    expectedException.getMessage(),
                    equalTo("URL string [invalid] is malformed."));
        }
    }

    @Nested
    class Load {
        @Test
        @DisplayName("Throws exception when there is no image file at given URL.")
        void notImage() {
            var expectedException = assertThrows(
                    IllegalStateException.class,
                    () -> new Loader("file:///").
                            load());
            assertThat(
                    expectedException.getMessage(),
                    equalTo("There is no image at URL [file:/]; perhaps, the URL " +
                            "points to the file of other type or to directory."));
        }

        @Test
        @DisplayName("Throws exception when given URL cannot be read.")
        void unreadableImage() {
            var expectedException = assertThrows(
                    IllegalStateException.class,
                    () -> new Loader("http://this_host_does_not_exist/this_image_does_not_exist").
                            load());
            assertThat(
                    expectedException.getMessage(),
                    equalTo("Failed to read image from URL " +
                            "[http://this_host_does_not_exist/this_image_does_not_exist]."));
        }

        @Test
        @DisplayName("Throws exception when image is too wide.")
        void tooWideImage() {
            var expectedException = assertThrows(
                    IllegalStateException.class,
                    () -> new Loader(testImage("too_wide.jpg")).
                            load());
            assertThat(
                    expectedException.getMessage(),
                    allOf(
                            containsString("The image at URL"),
                            containsString("is [5000] pixels wide"),
                            containsString("maximum allowed width of an image is equal to [4096] pixels.")));
        }

        @Test
        @DisplayName("Throws exception when image is too high.")
        void tooHighImage() {
            var expectedException = assertThrows(
                    IllegalStateException.class,
                    () -> new Loader(testImage("too_high.jpg")).
                            load());
            assertThat(
                    expectedException.getMessage(),
                    allOf(
                            containsString("The image at URL"),
                            containsString("is [5000] pixels high"),
                            containsString("maximum allowed height of an image is equal to [4096] pixels.")));
        }

        @Test
        @DisplayName("Throws exception when given image is malformed.")
        void malformedImage() {
            var expectedException = assertThrows(
                    IllegalStateException.class,
                    () -> new Loader(testImage("malformed.tif")).
                            load());
            assertThat(
                    expectedException.getMessage(),
                    allOf(
                            containsString("There is no image at URL"),
                            containsString("perhaps, the URL points to the file of other type or to directory")));
        }
    }

    private static String testImage(String fileName) {
        return ClassLoader.
                getSystemResource(fileName).
                toString();
    }
}

