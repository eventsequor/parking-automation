package com.parking.drivermanager;

import com.parking.utils.Browser;
import com.parking.utils.PropertiesUtils;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class WebDriverManager {

    protected WebDriver webDriver;
    private WebDriverWait webDriverWait;

    public WebDriverManager() {
        initialWebDriverSetup();
        webDriverWait = new WebDriverWait(webDriver, Duration.ofSeconds(PropertiesUtils.PROPERTIES_UTILS.getExplicitlyWait()));
        webDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(PropertiesUtils.PROPERTIES_UTILS.getImplicitlyWait()));
    }

    private void initialWebDriverSetup() {
        switch (getBrowser(PropertiesUtils.PROPERTIES_UTILS.getBrowser())) {
            default:
            case GOOGLE:
                ChromeOptions chromeOptions = new ChromeOptions();
                chromeOptions.setPageLoadStrategy(PageLoadStrategy.NORMAL);
                chromeOptions.addArguments("--remote-allow-origins=*", "start-maximized");
                webDriver = new ChromeDriver(chromeOptions);
                break;
            case FIREFOX:
                webDriver = new FirefoxDriver();
                break;
        }
    }

    public WebDriverWait getWebDriverWait() {
        return webDriverWait;
    }

    public WebDriver getWebDriver() {
        return webDriver;
    }

    private Browser getBrowser(String browserName) {
        try {
            return Browser.valueOf(browserName.toUpperCase());
        } catch (IllegalArgumentException e) {
            return Browser.GOOGLE;
        }
    }

    public void closeWebDriver() {
        webDriver.close();
        webDriver.quit();
    }
}
