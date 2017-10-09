package com.github.smallcreep.appium.element;

import com.github.smallcreep.appium.Element;
import java.io.IOException;
import java.io.UncheckedIOException;
import org.openqa.selenium.WebElement;

/**
 * Element that doesn't throw checked {@link Exception}.
 * @author Ilia Rogozhin (ilia.rogozhin@gmail.com)
 * @version $Id$
 * @since 0.1
 */
public final class UncheckedElem implements Element {

    /**
     * Original origin.
     */
    private final Element origin;

    /**
     * Ctor.
     * @param element Encapsulated origin
     */
    public UncheckedElem(final Element element) {
        this.origin = element;
    }

    @Override
    public WebElement webElement() {
        try {
            return this.origin.webElement();
        } catch (final IOException error) {
            throw new UncheckedIOException(error);
        }
    }
}
