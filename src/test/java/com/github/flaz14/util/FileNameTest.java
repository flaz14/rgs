package com.github.flaz14.util;

import com.github.flaz14.util.FileName;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.net.URL;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.sameInstance;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 *
 */
class FileNameTest {
    @Nested
    class FromUrl {
        @Test
        @DisplayName("Throws exception when URL is null.")
        void nullUrl() {
            var exception = assertThrows(
                    NullPointerException.class,
                    () -> FileName.fromUrl(null));
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
                    () -> FileName.fromUrl(urlWithoutPath));
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
                    FileName.fromUrl(url),
                    equalTo(expectedFileName));
        }
    }

    @Nested
    class OsTolerant {
        @Test
        @DisplayName("Throw exception with input file name is null.")
        void nullFileName() {
            var exception = assertThrows(
                    NullPointerException.class,
                    () -> FileName.osTolerant(null));
            assertThat(
                    exception.getMessage(),
                    equalTo("Input file name should not be null."));
        }


        @ParameterizedTest(name = "Replace forbidden characters test case #{index}: inputFileName=[{0}], expectedOutputFileName=[{1}].")
        @CsvSource({
                "Linux rocks \\ Windows s:cks!, Linux rocks _ Windows s_cks!",
                "logo.png?, logo.png_",
        })
        void happyPath(String inputFileName, String expectedOutputFileName) {
            assertThat(
                    FileName.osTolerant(inputFileName),
                    equalTo(expectedOutputFileName));
        }

        @Test
        @DisplayName("Does not affect empty input file name.")
        void emptyFileName() {
            var inputFileName = "";
            var outputFileName = FileName.osTolerant(inputFileName);
            assertThat(
                    outputFileName,
                    is(sameInstance(inputFileName)));
        }
    }
}