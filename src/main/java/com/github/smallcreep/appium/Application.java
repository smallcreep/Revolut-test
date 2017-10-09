package com.github.smallcreep.appium;

import com.jcabi.xml.XML;
import com.jcabi.xml.XMLDocument;
import io.appium.java_client.android.AndroidDriver;

import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;

/**
 * Application.
 *
 * @author Ilia Rogozhin (ilia.rogozhin@gmail.com)
 * @version $Id$
 * @since 0.1
 */
public final class Application implements Closeable {

    /**
     * Xml application root.
     */
    private final XML xml;

    /**
     * WebDriver.
     */
    private WebDriver driver;

    /**
     * Capabilities WebDriver.
     */
    private final DesiredCapabilities capabilities;

    /**
     * Appium hub.
     */
    private final URL hub;

    /**
     * Path application.
     */
    private final String path;

    /**
     * WebDriver wait.
     */
    private Wait<WebDriver> wait;

    /**
     * Current page.
     */
    private Page page;

    /**
     * Ctor.
     *
     * @param file File with xml application
     * @param capabilities Capabilities WebDriver
     * @param name Name application
     * @param path Path to apk
     * @param hub Hub url
     * @throws IOException If fails
     * @checkstyle ParameterNumberCheck (8 lines)
     */
    public Application(
            final File file,
            final DesiredCapabilities capabilities,
            final String name,
            final String path,
            final String hub
    ) throws IOException {
        this(
                new XMLDocument(
                        file
                ).nodes("//app[@name='" + name + "']").get(0),
                capabilities,
                path,
                hub
        );
    }

    /**
     * Ctor.
     *
     * @param xml Application xml
     * @param capabilities Capabilities WebDriver
     * @param path Path to apk
     * @param hub Hub url
     * @throws MalformedURLException If fails
     * @checkstyle ParameterNumberCheck (7 lines)
     */
    Application(
            final XML xml,
            final DesiredCapabilities capabilities,
            final String path,
            final String hub
    ) throws MalformedURLException {
        this(xml, capabilities, path, new URL(hub));
    }

    /**
     * Ctor.
     *
     * @param xml Application xml
     * @param capabilities Capabilities WebDriver
     * @param path Path to apk
     * @param hub Hub url
     * @checkstyle ParameterNumberCheck (7 lines)
     */
    Application(
            final XML xml,
            final DesiredCapabilities capabilities,
            final String path,
            final URL hub
    ) {
        this.xml = xml;
        this.capabilities = capabilities;
        this.path = path;
        this.hub = hub;
    }

    /**
     * Click element.
     * @param element Element name
     */
    public void click(final String element) {
        this.page = this.currentPage().click(element);
    }

    /**
     * Type text to input element.
     * @param name Element name
     * @param text Text
     */
    public void type(final String name, final String text) {
        this.currentPage().type(name, text);
    }

    @Override
    public void close() {
        this.driver().quit();
    }

    /**
     * Get WebDriver.
     * @return WebDriver
     */
    private WebDriver driver() {
        if (this.driver == null) {
            this.capabilities.setCapability(
                    "appPackage",
                    this.xml.xpath("./package/text()").get(0)
            );
            this.capabilities.setCapability(
                    "appWaitActivity",
                    this.xml.xpath("./startActivity/text()").get(0)
            );
            this.capabilities.setCapability(
                    "app",
                    this.path
            );
            this.driver = new AndroidDriver<>(
                    this.hub,
                    this.capabilities
            );
        }
        return this.driver;
    }

    /**
     * Get WebDriver Waiting.
     * @return WebDriver Waiting
     * @checkstyle MagicNumberCheck (7 lines)
     */
    private Wait<WebDriver> waiting() {
        if (this.wait == null) {
            this.wait = new FluentWait<>(this.driver())
                    .withTimeout(30, TimeUnit.SECONDS)
                    .pollingEvery(5, TimeUnit.SECONDS)
                    .ignoring(NoSuchElementException.class);
        }
        return this.wait;
    }

    /**
     * Get current page.
     * @return Current page
     */
    private Page currentPage() {
        if (this.page == null) {
            this.updateCurrentPage(
                    this.xml.xpath("./startPage/text()").get(0)
            );
        }
        return this.page;
    }

    /**
     * Open new current page.
     * @param id Page id
     */
    private void updateCurrentPage(final String id) {
        this.page = new Page(
                this.xml,
                this.xml.nodes(
                        String.format(
                                "./pages/page[@id='%s']",
                                id
                        )
                ).get(0),
                this.waiting()
        );
        this.page.opened();
    }

    /**
     * Check element with text present.
     * @param element Element
     * @param text Text in element
     */
    public void present(final String element, final String text) {
        this.page.present(element, text);
    }
}
