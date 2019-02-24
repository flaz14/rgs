package com.github.flaz14;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.net.URL;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 *
 */
class FileNameUtilTest {
    @Nested
    class FileNameFromUrl {
        @Test
        @DisplayName("Throws exception when URL is null.")
        void nullUrl() {
            var exception = assertThrows(
                    NullPointerException.class,
                    () -> FileNameUtil.fileNameFromUrl(null));
            assertThat(
                    exception.getMessage(),
                    equalTo("URL should not be null."));
        }

        @Test
        @DisplayName("Throws exception when no path has been specified.")
        void emptyPathSegment() throws Exception {
            var urlWithoutPath = new URL("file:///");
            var exception = assertThrows(
                    IllegalArgumentException.class,
                    () -> FileNameUtil.fileNameFromUrl(urlWithoutPath));
            assertThat(
                    exception.getMessage(),
                    equalTo("Failed to extract file name from URL [file:/] " +
                            "because path segment of the URL is empty;" +
                            " seems that the URL points to the root of hierarchy."));
        }

        @ParameterizedTest(name = "Extract file name test case #{index}: urlString=[{0}], expectedFileName=[{1}].")
        @CsvSource({
                "https://www.google.by/images/branding/googlelogo/2x/googlelogo_color_272x92dp.png, googlelogo_color_272x92dp.png",
                "file:///etc/photo.JPEG, photo.JPEG",
                "file:///etc/more/../image, image",
                "file:///C:/windows/logo.bmp, logo.bmp",
                "http:///host.com:, host.com:",
                "file:///C:, C:",

        })
        void happyPath(String urlString, String expectedFileName) throws Exception {
            var url = new URL(urlString);
            assertThat(
                    FileNameUtil.fileNameFromUrl(url),
                    equalTo(expectedFileName));
        }
    }

}