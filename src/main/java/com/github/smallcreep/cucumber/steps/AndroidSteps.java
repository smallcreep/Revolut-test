package com.github.smallcreep.cucumber.steps;

import com.github.smallcreep.appium.Application;
import com.github.smallcreep.appium.settings.SettingsIniFile;
import cucumber.api.java.After;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;

import java.io.File;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.openqa.selenium.remote.DesiredCapabilities;

/**
 * Basic Android steps.
 *
 * @author Ilia Rogozhin (ilia.rogozhin@gmail.com)
 * @version $Id$
 * @since 0.1
 */
public final class AndroidSteps {

    /**
     * Application.
     */
    private Application app;

    /**
     * Capabilities.
     */
    private DesiredCapabilities capabilities;

    /**
     * Get default android phone.
     *
     * @param name Name android phone
     * @throws IOException If fails
     */
    @Given("^a (.*) android phone$")
    public void androidPhone(final String name) throws IOException {
        this.capabilities = new DesiredCapabilities(
                new SettingsIniFile(
                        new File(
                                this.getClass()
                                        .getResource("/settings.ini")
                                        .getFile()
                        ),
                        name
                ).capabilities()
        );
    }

    /**
     * Start app on the phone.
     *
     * @param name App name
     * @throws IOException If fails
     */
    @Given("^(.*) app is started on the phone$")
    public void appIsStartedOnPhone(final String name)
            throws IOException {
        this.app = new Application(
                new File(
                        this.getClass().getResource("/app.xml").getFile()
                ),
                this.capabilities,
                name,
                System.getProperty("appPath"),
                System.getProperty("hubUrl")
        );
    }

    /**
     * Press button.
     *
     * @param name Button name
     */
    @Given("^click button (.*)$")
    public void clickButton(final String name) {
        this.app.click(name);
    }

    /**
     * Type text to input.
     * @param text Text
     * @param name Input name
     */
    @Given("^type \"([^\"]*)\" to (.*)$")
    public void typeTo(final String text, final String name) {
        this.app.type(name, text);
    }

    /**
     * Set password for user.
     * @param password Password
     * @throws IOException If fails
     */
    @Given("^set password (\\d{4})$")
    public void setPassword(final String password) throws IOException {
        final Matcher matcher = Pattern.compile("(\\d)(\\d)(\\d)(\\d)")
                .matcher(password);
        if (matcher.find()) {
            for (int digit = 0;
                 digit < matcher.groupCount();
                 digit = digit + 1) {
                this.clickButton(matcher.group(digit + 1));
            }
        } else {
            throw new IOException("Password has incorrect characters");
        }
    }

    /**
     * Close application after test.
     */
    @After
    public void tearDown() {
        this.app.close();
    }

    /**
     * Check that element with text present.
     * @param element Element name
     * @param text Text in element
     */
    @Then("^(.*) \"([^\"]*)\" is present$")
    public void elementIsPresent(
            final String element,
            final String text
    ) {
        this.app.present(element, text);
    }
}
