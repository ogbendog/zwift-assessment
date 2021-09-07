package com.zwift;

import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import pages.ZwiftEventsPage;
import pages.ZwiftHomePage;

import java.util.concurrent.TimeUnit;

public class SampleTests {

    private static WebDriver driver;
    private static ZwiftHomePage homePage;

    @BeforeSuite
    private void setupSuite() {
        System.setProperty("webdriver.chrome.driver",
                "lib/chromedriver/chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
    }

    @BeforeTest
    private void setupTests() {
        homePage = new ZwiftHomePage(driver);
        homePage.open();
    }

    @AfterTest
    private void cleanup() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    public void homePageChatButtonPresent() {
        System.out.println("** Starting home page test");
        Assert.assertTrue(homePage.isChatButtonPresent());
        System.out.println("PASS: Found the chat button");
    }

    @Test
    public void eventsFilteringTest() {
        System.out.println("** Starting events filter test");
        ZwiftEventsPage eventsPage = homePage.openEventsPage();
        int totalEvents = eventsPage.countEvents();
        System.out.println(totalEvents + " events found");
        eventsPage.openFilterPane();
        eventsPage.setCyclingFilter();
        eventsPage.setMorningFilter();
        eventsPage.setIntensityFilter("C");
        System.out.println("Applying filter");
        eventsPage.applyFilters();
        int filteredEvents = eventsPage.countEvents();
        System.out.println(filteredEvents + " events found");
        Assert.assertFalse(totalEvents == filteredEvents, "FAIL: filter did not change total number of events");
        System.out.println("PASS: Filtered " + Integer.toString(totalEvents) + " events down to " + Integer.toString(filteredEvents));
    }

    // this test is inherently fragile, as events are added and removed routinely.
    // it is more in the nature of a unit test of the count code
 //   @Test
    public void eventsFilteringOneFoundTest() {
        ZwiftEventsPage eventsPage = homePage.openEventsPage();
        int totalEvents = eventsPage.countEvents();
        System.out.println(totalEvents);
        eventsPage.openFilterPane();
        eventsPage.setRunningFilter();
        eventsPage.setMorningFilter();
        eventsPage.setIntensityFilter("A");
        eventsPage.applyFilters();
        int filteredEvents = eventsPage.countEvents();
        System.out.println(filteredEvents);
        Assert.assertTrue(filteredEvents == 1);
        //Assertions.assertFalse(totalEvents == filteredEvents, "FAIL: filter did not change total number of events");
        System.out.println("PASS: Found exactly one event");
    }

    // this test is inherently fragile, as events are added and removed routinely.
    // a test for zero results returned is needed to verify that boundary condition
    // TODO discuss with dev and product adding a test hook
 //   @Test
    public void eventsFilteringNoneFoundTest() {
        ZwiftEventsPage eventsPage = homePage.openEventsPage();
        int totalEvents = eventsPage.countEvents();
        System.out.println(totalEvents);
        eventsPage.openFilterPane();
        eventsPage.setRunningFilter();
        eventsPage.setMorningFilter();
        eventsPage.setIntensityFilter("C");
        eventsPage.applyFilters();
        int filteredEvents = eventsPage.countEvents();
        System.out.println(filteredEvents);
        Assert.assertTrue(filteredEvents == 0);
        //Assertions.assertFalse(totalEvents == filteredEvents, "FAIL: filter did not change total number of events");
        System.out.println("PASS: No elements found, as expected");
    }
}
