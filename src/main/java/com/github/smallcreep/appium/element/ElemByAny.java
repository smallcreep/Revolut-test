package com.github.smallcreep.appium.element;

import com.github.smallcreep.appium.Element;
import com.jcabi.xml.XML;
import java.io.IOException;
import java.util.InvalidPropertiesFormatException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Wait;

/**
 * Element by any selector.
 * @author Ilia Rogozhin (ilia.rogozhin@gmail.com)
 * @version $Id$
 * @since 0.1
 */
public final class ElemByAny implements Element {

    /**
     * WebDriver wait.
     */
    private final Wait<WebDriver> wait;

    /**
     * XML element.
     */
    private final XML element;

    /**
     * Ctor.
     * @param wait WebDriver wait
     * @param element XML element
     */
    public ElemByAny(final Wait<WebDriver> wait, final XML element) {
        this.wait = wait;
        this.element = element;
    }

    @Override
    public WebElement webElement() throws IOException {
        final Element founded;
        final XML selector = this.element.nodes("./selector").get(0);
        final String type = selector.xpath("./@type").get(0);
        switch (type) {
            case "id":
                founded = new ElemById(
                    this.wait,
                    selector.xpath("./text()").get(0)
                );
                break;
            case "xpath":
                founded = new ElemByXpath(
                        this.wait,
                        selector.xpath("./text()").get(0)
                );
                break;
            default:
                throw new InvalidPropertiesFormatException(
                    String.format(
                        "Not found type '%s' for element '%s'",
                        type,
                        this.element
                    )
                );
        }
        return founded.webElement();
    }
}
