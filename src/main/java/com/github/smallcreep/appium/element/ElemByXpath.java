package com.github.smallcreep.appium.element;

import com.github.smallcreep.appium.Element;
import java.util.function.Function;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Wait;

/**
 * Element found by id.
 * @author Ilia Rogozhin (ilia.rogozhin@gmail.com)
 * @version $Id$
 * @since 0.1
 */
public final class ElemByXpath implements Element {

    /**
     * WebDriver wait.
     */
    private final Wait<WebDriver> wait;

    /**
     * Selector element.
     */
    private final String selector;

    /**
     * Ctor.
     * @param wait WebDriver wait
     * @param selector Selector element
     */
    public ElemByXpath(final Wait<WebDriver> wait, final String selector) {
        this.wait = wait;
        this.selector = selector;
    }

    @Override
    public WebElement webElement() {
        return this.wait.until(
                new Function<WebDriver, WebElement>() {
                    public WebElement apply(final WebDriver driver) {
                        return driver.findElement(
                                By.xpath(selector)
                        );
                    }
                });
    }
}
