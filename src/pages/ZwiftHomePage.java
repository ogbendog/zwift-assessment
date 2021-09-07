package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.PageUtils;

public class ZwiftHomePage {
    WebDriver driver;
    WebDriverWait wait;

    public ZwiftHomePage(WebDriver driver) {
        super();
        this.driver = driver;
        wait = new WebDriverWait(driver, 5);
        PageFactory.initElements(driver, this);
    }

    @FindBy(how = How.ID, using =
            "truste-consent-button")
    WebElement acceptAllButton;

    @FindBy(how = How.CLASS_NAME, using =
            "solvvy-chat-icon")
    WebElement chatButton;

    @FindBy(how = How.CLASS_NAME, using =
            "PrimaryNav-module__hamburger--1y_LN")
    WebElement hamburgerMenu;

    //TODO open ticket to add IDs to hamburger menu elements
    @FindBy(how = How.XPATH, using =
            "//*[text()='Events']")
    WebElement eventsButton;

    public void open() {
        driver.navigate().to("https://www.zwift.com/");
        PageUtils.waitForPageLoaded(driver, wait);
        // all controls are disabled unless cookies are accepted
        if (acceptAllButton.isDisplayed()) {
            clickAcceptButton();
        }
        System.out.println("Home page loaded");
    }


    public void clickAcceptButton() {
        System.out.println("Accepting cookies for this session");
        acceptAllButton.click();
    }

    public boolean isChatButtonPresent() {
        return chatButton.isDisplayed();
    }

    public ZwiftEventsPage openEventsPage() {
        hamburgerMenu.click();
        wait.until(ExpectedConditions.elementToBeClickable(eventsButton));
        eventsButton.click();
        return new ZwiftEventsPage(driver);
    }

}
