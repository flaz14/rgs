package com.github.flaz14.limit.wrapper;

import java.net.MalformedURLException;
import java.net.URL;

import static com.github.flaz14.limit.Limits.SUPPORTED_PROTOCOLS;
import static java.lang.String.format;
import static java.util.Objects.requireNonNull;

public class PermissibleUrl {
    public PermissibleUrl(String urlString) {
        checkNull(urlString);
        checkEmpty(urlString);
        try {
            url = new URL(urlString);
        } catch (MalformedURLException onParsing) {
            var message = format("URL string [%s] is malformed.", urlString);
            throw new IllegalArgumentException(message, onParsing);
        }
        checkProtocol();
    }

    public URL get() {
        return url;
    }

    private void checkNull(String urlString) {
        requireNonNull(urlString, "URL string should not be null.");
    }

    private void checkEmpty(String urlString) {
        if (urlString.isEmpty()) {
            throw new IllegalArgumentException("URL string should not be empty.");
        }
    }

    private void checkProtocol() {
        var protocol = url.
                getProtocol().
                toUpperCase();
        if (!SUPPORTED_PROTOCOLS.contains(protocol)) {
            var message = format("Protocol [%s] " +
                            "specified in URL string [%s] " +
                            "is not supported; " +
                            "supported protocols are %s.",
                    protocol,
                    url,
                    SUPPORTED_PROTOCOLS);
            throw new IllegalArgumentException(message);
        }
    }

    private final URL url;
}
