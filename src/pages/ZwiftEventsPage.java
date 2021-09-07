package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.PageUtils;

import java.security.InvalidParameterException;

public class ZwiftEventsPage {

    WebDriver driver;
    WebDriverWait wait;

    public ZwiftEventsPage(WebDriver driver) {
        super();
        this.driver = driver;
        wait = new WebDriverWait(driver, 5);
        PageUtils.waitForPageLoaded(driver, wait);
        PageFactory.initElements(driver, this);
    }

    @FindBy(how = How.CLASS_NAME, using =
            "filter-toggle")
    WebElement filterButton;

    // TODO add other filter options
    @FindBy(how = How.XPATH, using =
            "//*[text()='Running']")
    WebElement runningButton;

    @FindBy(how = How.XPATH, using =
            "//*[text()='Cycling']")
    WebElement cyclingButton;

    @FindBy(how = How.XPATH, using =
            "//*[text()='Morning']")
    WebElement morningButton;

    @FindBy(how = How.XPATH, using =
            "//*[text()='apply filters']")
    WebElement applyFilterButton;

    @FindBy(how = How.CLASS_NAME, using =
            "listing-no-results")
    WebElement noResults;

    public void openFilterPane() {
        filterButton.click();
    }

    public int countEvents() {
        int count = driver.findElements(By.className("tab-listing")).size();
        if (count == 1) {
            // no events returns a single tab, have to verify if there is one or none
            try {
                driver.findElement(By.className("listing-no-results"));
                count = 0;
            }
            catch (NoSuchElementException e) {
                //TODO add a test case to verify the 1 and only 1 returned scenario
                //if "no results" element is not present, we do have 1 event
            }
        }
        return count;
    }

    public void openFilter() {
        filterButton.click();
    }

    public void setCyclingFilter() {
        cyclingButton.click();
    }

    public void setRunningFilter() {
        runningButton.click();
    }

    public void setMorningFilter() {
        morningButton.click();
    }

    public void applyFilters() {
        applyFilterButton.click();
    }

    public void setIntensityFilter(String intensity) {
        if ("ABCDE (open)".indexOf(intensity) == -1) {
            throw new InvalidParameterException(intensity + " is not a valid parameter");
        }
        driver.findElement(By.xpath("//*[text()='" + intensity + "']")).click();
    }
}
