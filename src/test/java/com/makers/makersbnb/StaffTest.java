package com.makers.makersbnb;


import com.microsoft.playwright.*;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

@SpringBootTest
public class StaffTest {

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
    public void staffListShown(){
        page.navigate("http://localhost:8080/team");
        Locator li = page.locator("#team-member");

        assertThat(li).containsText(new String[] {"Jon, Assistant Pot Wash", "Charlie, CEO"});

    }
}
