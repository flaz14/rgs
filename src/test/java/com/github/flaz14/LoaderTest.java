package com.github.flaz14;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class LoaderTest {
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

