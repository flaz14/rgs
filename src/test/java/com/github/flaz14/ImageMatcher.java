package com.github.flaz14;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

/**
 *
 */
public class ImageMatcher extends TypeSafeMatcher<Image> {

    @Override
    protected boolean matchesSafely(Image image) {
        return false;
    }

    @Override
    public void describeTo(Description description) {

    }
}
