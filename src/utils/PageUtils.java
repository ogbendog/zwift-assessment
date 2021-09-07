package utils;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

public class PageUtils {

    public PageUtils() {

    }

    public static void waitForPageLoaded(WebDriver driver, WebDriverWait wait) {
        ExpectedCondition<Boolean> expectation = new
                ExpectedCondition<Boolean>() {
                    public Boolean apply(WebDriver driver) {
                        return ((JavascriptExecutor) driver).executeScript("return document.readyState").equals("complete");
                    }
                };
        try {
            wait.until(expectation);
        } catch (Throwable error) {
            throw new TimeoutException("Timeout waiting for Page Load Request to complete.");
        }
    }
}
