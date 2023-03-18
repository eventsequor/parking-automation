package com.parking.basetestclasses;

import com.parking.drivermanager.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;


public class BaseTestSingleUI extends BaseTest {

    private WebDriverManager webDriverManager;

    @BeforeEach
    public void beforeEach() {
        webDriverManager = new WebDriverManager();
    }

    @AfterEach
    public void afterEach() {
        webDriverManager.closeWebDriver();
    }

    public WebDriverManager getWebDriverManager() {
        return webDriverManager;
    }
}
