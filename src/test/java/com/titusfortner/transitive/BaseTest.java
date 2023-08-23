package com.titusfortner.transitive;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.junit.jupiter.api.extension.TestWatcher;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class BaseTest {
    WebDriver driver;
    boolean REMOTE = Boolean.parseBoolean(System.getProperty("REMOTE", "false"));
    String BASE_URL = System.getProperty("BASE_URL", "http://localhost:3000");

    @RegisterExtension
    public CustomTestWatcher customTestWatcher = new CustomTestWatcher();

    @BeforeEach
    public void setUp(TestInfo testinfo) throws MalformedURLException {
        driver = REMOTE ? runRemote(testinfo) : runLocal();
    }

    private WebDriver runLocal() {
        ChromeOptions options = new ChromeOptions();
        options.setImplicitWaitTimeout(Duration.ofSeconds(1));
        Map<String, Object> prefs = new HashMap<>();
        prefs.put("autofill.profile_enabled", false);
        options.setExperimentalOption("prefs", prefs);

        return new ChromeDriver(options);
    }

    private WebDriver runRemote(TestInfo testinfo) throws MalformedURLException {
        ChromeOptions browserOptions = new ChromeOptions();
        browserOptions.setPlatformName("Windows 10");
        browserOptions.setBrowserVersion("latest");
        browserOptions.setImplicitWaitTimeout(Duration.ofSeconds(1));

        Map<String, Object> sauceOptions = new HashMap<>();
        sauceOptions.put("username", System.getenv("SAUCE_USERNAME"));
        sauceOptions.put("accessKey", System.getenv("SAUCE_ACCESS_KEY"));
        sauceOptions.put("name", testinfo.getDisplayName());

        browserOptions.setCapability("sauce:options", sauceOptions);

        URL url = new URL("https://ondemand.us-west-1.saucelabs.com:443/wd/hub");
        return new RemoteWebDriver(url, browserOptions);
    }

    public class CustomTestWatcher implements TestWatcher {
        @Override
        public void testFailed(ExtensionContext context, Throwable cause) {
            reportResults("failed");
        }

        @Override
        public void testSuccessful(ExtensionContext context) {
            reportResults("passed");
        }

        @Override
        public void testAborted(ExtensionContext context, Throwable cause) {
        }

        @Override
        public void testDisabled(ExtensionContext context, Optional<String> reason) {
        }
    }

    private void reportResults(String result) {
        try {
            if (REMOTE) {
                ((JavascriptExecutor) driver).executeScript("sauce:job-result=" + result);
            } else {
                System.out.println("Test " + result);
            }
        } catch (Exception ignored) {
        } finally {
            driver.quit();
        }
    }
}
