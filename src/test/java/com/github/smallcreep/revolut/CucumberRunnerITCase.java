package com.github.smallcreep.revolut;

import com.github.mkolisnyk.cucumber.runner.ExtendedCucumber;
import com.github.mkolisnyk.cucumber.runner.ExtendedCucumberOptions;
import cucumber.api.CucumberOptions;
import org.junit.runner.RunWith;

/**
 * Cucumber runner tests.
 * @author Ilia Rogozhin (ilia.rogozhin@gmail.com)
 * @version $Id$
 * @since 0.1
 * @checkstyle MagicNumberCheck (10 lines)
 */
@RunWith(ExtendedCucumber.class)
@ExtendedCucumberOptions(
    jsonReport = "build/reports/tests/cucumber.json",
    detailedReport = true,
    detailedAggregatedReport = true,
    overviewReport = true,
    jsonUsageReport = "build/reports/tests/cucumber-usage.json",
    usageReport = true,
    outputFolder = "build/reports")
@CucumberOptions(
    plugin = {
        "html:build/reports/tests/cucumber-html-report",
        "json:build/reports/tests/cucumber.json",
        "pretty:build/reports/tests/cucumber-pretty.txt",
        "usage:build/reports/tests/cucumber-usage.json",
        "junit:build/reports/tests/cucumber-results.xml"
    },
    features = {"src/test/resources/com/github/smallcreep/"},
    glue = {"com/github/smallcreep/cucumber/steps"}
)
public final class CucumberRunnerITCase {

}
