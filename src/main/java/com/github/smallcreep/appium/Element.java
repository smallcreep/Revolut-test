package com.github.smallcreep.appium;

import java.io.IOException;

import org.openqa.selenium.WebElement;

/**
 * Page element.
 * @author Ilia Rogozhin (ilia.rogozhin@gmail.com)
 * @version $Id$
 * @since 0.1
 */
public interface Element {

    /**
     * Get WebElement.
     * @return WebElement
     * @throws IOException If fails.
     */
    WebElement webElement() throws IOException;

}
