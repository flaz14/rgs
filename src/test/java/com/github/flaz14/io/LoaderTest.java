package com.github.flaz14.io;

import com.github.flaz14.limit.wrapper.PermissibleUrl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.AllOf.allOf;
import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.assertThrows;

class LoaderTest {
    @Test
    @DisplayName("Throws exception when there is no image file at given URL.")
    void notImage() {
        var expectedException = assertThrows(
                IllegalStateException.class,
                () -> new Loader(
                        new PermissibleUrl("file:///")).
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
                () -> new Loader(
                        new PermissibleUrl("http://this_host_does_not_exist/this_image_does_not_exist")).
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
                () -> new Loader(sampleImage("too_wide.jpg")).
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
                () -> new Loader(sampleImage("too_high.jpg")).
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
                () -> new Loader(sampleImage("malformed.jpg")).
                        load());
        assertThat(
                expectedException.getMessage(),
                allOf(
                        containsString("There is no image at URL"),
                        containsString("malformed.jpg"),
                        containsString("perhaps, the URL points to the file of other type or to directory")));
    }

    @Test
    @DisplayName("Loads image and preserves original format, file name and extension.")
    void happyPath() {
        var image = new Loader(sampleImage("1024x280.jpg")).
                load();
        // There should be single assertion.
        // But implementation of custom Hamcrest matcher
        // is superfluous in case of just three properties
        // (exact comparison of image buffers is useless,
        // this is why we only check for `null').
        assertThat(image.buffer(), is(notNullValue()));
        assertThat(image.formatName(), is("JPEG"));
        assertThat(image.fileName(), equalTo("1024x280.jpg"));
    }

    private static PermissibleUrl sampleImage(String fileName) {
        String url = ClassLoader.
                getSystemResource(fileName).
                toString();
        return new PermissibleUrl(url);
    }
}

