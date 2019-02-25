package com.github.flaz14.util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.net.URL;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 *
 */
class PermissibleUrlTest {
    @Test
    @DisplayName("Throws exception when URL string is null.")
    void nullUrl() {
        var expectedException = assertThrows(
                NullPointerException.class,
                () -> new PermissibleUrl(null));
        assertThat(
                expectedException.getMessage(),
                equalTo("URL string should not be null."));
    }

    @Test
    @DisplayName("Throws exception when URL string is empty.")
    void emptyUrl() {
        var expectedException = assertThrows(
                IllegalArgumentException.class,
                () -> new PermissibleUrl(""));
        assertThat(
                expectedException.getMessage(),
                equalTo("URL string should not be empty."));
    }

    @Test
    @DisplayName("Throws exception when given protocol is not supported.")
    void unsupportedProtocol() {
        var expectedException = assertThrows(
                IllegalArgumentException.class,
                () -> new PermissibleUrl("ftp://someftp.com"));
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
                () -> new PermissibleUrl("invalid"));
        assertThat(
                expectedException.getMessage(),
                equalTo("URL string [invalid] is malformed."));
    }

    @Test
    @DisplayName("Converts URL string into corresponding instance from standard library.")
    void happyPath() throws Exception {
        var expected = new URL("http://some.host.com/path");
        var actual = new PermissibleUrl("http://some.host.com/path").
                get();
        assertThat(actual, equalTo(expected));
    }
}