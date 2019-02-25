package com.github.flaz14.util;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Set;
import java.util.TreeSet;

import static java.lang.String.format;
import static java.util.Collections.unmodifiableSet;
import static java.util.Objects.requireNonNull;
import static java.util.Set.of;

/**
 * A wrapper for {@link URL} with some validations (without checked exceptions thrown).
 * <p>
 * Helps to check an URL in the very beginning of application execution.
 */
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

    // According to interview task specification, we support a few of URL
    // protocols. We can add more protocols into this map in the future
    // without modification the rest of this class.
    //
    // We keep content in `TreeMap' for the sake of predictable order of
    // validation. And we wrap the map by its unmodifiable twin just
    // for preventing of accidental modification.
    private static final Set<String> SUPPORTED_PROTOCOLS =
            unmodifiableSet(
                    new TreeSet<>(of("FILE", "HTTP")));
}
