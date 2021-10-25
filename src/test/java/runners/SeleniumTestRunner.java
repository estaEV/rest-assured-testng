package runners;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.annotations.DataProvider;

@CucumberOptions(

        plugin = {"pretty", "html:target/cucumber-report.html", "json:target/cucumber-report2.json"},
        features = {"classpath:/features"},
        glue = {"stepdefs"}
)

public class SeleniumTestRunner extends AbstractTestNGCucumberTests {

    @Override
    @DataProvider(parallel = false)
    public Object[][] scenarios() {
        return super.scenarios();
    }
}
