package com.makers.makersbnb;


import com.makers.makersbnb.model.Booking;
import com.makers.makersbnb.model.Space;
import com.makers.makersbnb.repository.BookingRepository;
import com.makers.makersbnb.repository.SpaceRepository;
import com.microsoft.playwright.*;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

@SpringBootTest
public class BookingsTest {

    static Playwright playwright;
    static Browser browser;

    BrowserContext context;
    Page page;

    @Autowired
    BookingRepository bookingRepository;

    @Autowired
    SpaceRepository spaceRepository;

    @BeforeAll
    static void launchBrowser(){
        playwright = Playwright.create();
        browser = playwright.chromium().launch();
    }

    @AfterAll
    static void closeBrowser(){
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
    public void BookingDetailsRetrieved(){
        Space space = new Space();
        spaceRepository.save(space);

        Booking booking = new Booking(LocalDate.of(2026, 9, 12), LocalDate.of(2026, 9, 15), "1232131", space);
        Booking savedBooking = bookingRepository.save(booking);

        Booking foundBooking = bookingRepository.findById(savedBooking.getId())
                .orElseThrow(() -> new RuntimeException("Booking not found"));

        Assertions.assertEquals(LocalDate.of(2026, 9, 12), foundBooking.getBookedFrom());
    }




}

