package com.github.flaz14.limit;

import java.util.Set;
import java.util.TreeSet;

import static java.util.Collections.unmodifiableSet;
import static java.util.Set.of;

/**
 * All the limits are gathered into single place in order to avoid duplication (and accidental copy'n'paste errors in
 * documentation of Command-Line Interface).
 * <p>
 * We need limitations not only by the specification of interview task, but just for predictable behavior of the
 * application. It will be better to stop processing right in the beginning rather that let the program silently fail
 * somewhere in the middle due to out of memory.
 */
public class Limits {
    public static final int IMAGE_WIDTH_IN_PIXELS = 4096;
    public static final int IMAGE_HEIGHT_IN_PIXELS = 4096;

    // According to interview task specification, we support a few of URL
    // protocols. We can add more protocols into this map in the future
    // without modification the rest of this class.
    //
    // We keep content in `TreeMap' for the sake of predictable order of
    // validation. And we wrap the map by its unmodifiable twin just
    // for preventing of accidental modification.
    public static final Set<String> SUPPORTED_PROTOCOLS =
            unmodifiableSet(
                    new TreeSet<>(of("FILE", "HTTP")));

    // Currently we support only JPEG because it's the most dumb format among
    // others.
    //
    // In general, handling each format should be done with great care.
    // For example, PNG supports transparency. BMP can support transparency
    // or not depending on compression method (32-bit BITFIELDS vs.
    // 24-bit RGB respectively). GIF is even more complicated (due to
    // animation).
    //
    // We just don't dig into those difficulties in the scope of
    // interview task.
    //
    // The explanations about extensibility and robustness for the former
    // map are relevant for this map as well.
    public static final Set<String> SUPPORTED_FORMATS =
            unmodifiableSet(
                    new TreeSet<>(of("JPEG")));
}
