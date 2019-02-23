package com.github.flaz14;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Set;
import java.util.TreeSet;

import static java.lang.String.format;
import static java.util.Collections.unmodifiableSet;
import static java.util.Objects.requireNonNull;
import static java.util.Set.of;

/**
 *
 */
public class Loader {


    public Loader(String urlString) {
        requireNonNull(urlString, "URL string should not be null.");
        requireNonEmpty(urlString);
        try {
            url = new URL(urlString);
        } catch (MalformedURLException onParsing) {
            var message = format("URL string [%s] is malformed.", urlString);
            throw new IllegalArgumentException(message, onParsing);
        }
        requireSupportedProtocol(url);
    }

    private static void requireNonEmpty(String urlString) {
        if (urlString.isEmpty()) {
            throw new IllegalArgumentException("URL string should not be empty.");
        }
    }

    private static void requireSupportedProtocol(URL url) {
        var protocol = url.
                getProtocol().
                toUpperCase();
        if (!SUPPORTED_PROTOCOLS.contains(protocol)) {
            var message = format("Protocol [%s] " +
                            "specified in URL string [%s] " +
                            "is not supported; " +
                            "supported protocols are %s.",
                    protocol,
                    url.toString(),
                    SUPPORTED_PROTOCOLS);
            throw new IllegalArgumentException(message);
        }

    }

    private final URL url;

    // TODO we use set because many other protocols can be supported in future,
    // TODO also, it simplifies composition of readable and informative exception messages
    // TODO we wrap it with tree set in order to keep the order of comparison consistent.
    private static final Set<String> SUPPORTED_PROTOCOLS =
            unmodifiableSet(
                    new TreeSet<>(of("FILE", "HTTP")));
}
