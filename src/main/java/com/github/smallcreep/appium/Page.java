package com.github.smallcreep.appium;

import com.github.smallcreep.appium.element.ElemByAny;
import com.github.smallcreep.appium.element.UncheckedElem;
import com.jcabi.xml.XML;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Wait;

/**
 * Application page.
 *
 * @author Ilia Rogozhin (ilia.rogozhin@gmail.com)
 * @version $Id$
 * @since 0.1
 */
final class Page {

    /**
     * Xml application.
     */
    private final XML application;

    /**
     * Xml page.
     */
    private final XML xml;

    /**
     * WebDriver wait.
     */
    private final Wait<WebDriver> wait;

    /**
     * Count clicked element.
     */
    private final AtomicInteger count;

    /**
     * Ctor.
     * @param application Application xml
     * @param xml XML page
     * @param wait WebDriver wait
     */
    Page(final XML application, final XML xml, final Wait<WebDriver> wait) {
        this.application = application;
        this.xml = xml;
        this.wait = wait;
        this.count = new AtomicInteger(1);
    }

    /**
     * Click by element.
     * @param name Element name
     * @return Opened page
     */
    Page click(final String name) {
        final XML element = this.element(name);
        this.webElement(element).click();
        final List<String> redirect = element.xpath("./@clickRedirect");
        final Page page;
        if (redirect.size() > 0
                && count.get() < Integer.parseInt(redirect.get(0))) {
            count.set(count.get() + 1);
            page = this;
        } else {
            final List<String> pages = element.xpath("./@clickPage");
            if (pages.size() > 0) {
                page = this.page(pages.get(0));
            } else {
                page = this;
            }
        }
        return page;
    }

    /**
     * Get WebElement by element xml.
     * @param element Element xml
     * @return WebElement
     */
    private WebElement webElement(final XML element) {
        return new UncheckedElem(
                new ElemByAny(
                        this.wait,
                        element
                )
        ).webElement();
    }

    /**
     * Get element xml by name.
     * @param name Element name
     * @return Element xml
     */
    private XML element(final String name) {
        return this.xml.nodes(
                String.format(
                        "./elements/element[@name='%s']",
                        name
                )
        ).get(0);
    }

    /**
     * Type text to input element.
     * @param name Element name
     * @param text Text
     */
    void type(final String name, final String text) {
        this.webElement(this.element(name)).sendKeys(text);
    }

    /**
     * Check all element page present.
     * @return Page
     */
    public Page opened() {
        this.xml.nodes("./elements/element").forEach(
                this::webElement
        );
        return this;
    }

    /**
     * Get page by id.
     * @param id Page id
     * @return Page
     */
    private Page page(final String id) {
        return new Page(
                this.application,
                this.application.nodes(
                        String.format(
                                "./pages/page[@id='%s']",
                                id
                        )
                ).get(0),
                this.wait
        ).opened();
    }

    /**
     * Check element with text present.
     * @param name Element name
     * @param text Text in element
     */
    public void present(final String name, final String text) {
        if (!this.webElement(this.element(name)).getText().equals(text)) {
            throw new AssertionError("Element has another text");
        }
    }
}
