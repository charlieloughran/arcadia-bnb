package com.makers.makersbnb;

import com.microsoft.playwright.*;
import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import org.junit.jupiter.api.*;

public class LandingPageTest {

    static Playwright playwright;
    static Browser browser;

    BrowserContext context;
    Page page;

    @BeforeAll
    static void launchBrowser() {
        playwright = Playwright.create();
        browser = playwright.chromium().launch();
    }

    @AfterAll
    static void closerBrowser() {
        playwright.close();
    }

    @BeforeEach
    void createContextAndPage() {
        context = browser.newContext();
        page = context.newPage();
    }

    @AfterEach
    void closeContext() {
        context.close();
    }

    @Test
    public void usersAreWelcomedToTheApp() {
        page.navigate("http://localhost:8080");
        Locator pageBody = page.locator("body");
        assertThat(pageBody).containsText("Welcome to ArcadiaBnB");
    }

    @Test
    public void numberOfSpacesAreShown() {
        page.navigate("http://localhost:8080");
        Locator h3 = page.locator("h2");
        assertThat(h3).containsText("spaces are waiting to be discovered");
    }

    @Test
    public void fakeEmailAddressIsShown() {
        page.navigate("http://localhost:8080/contactus");
        Locator li  = page.locator("#email-address");
        assertThat(li).containsText("Email Address: example@makersbnb.com");
    }

    @Test
        public void teamListIsShown() {
            page.navigate("http://localhost:8080/team");
            Locator li = page.locator("#team-list");
            assertThat(li).hasText(new String[] {"Toby", "Katerina", "Sandy"});

    }

    @Test
    public void noOfBookingsLastWeekShown() {
        page.navigate("http://localhost:8080/");
        Locator h5 = page.locator("h5");
        assertThat(h5).containsText("Number of bookings made last week: 123");

    }
}
