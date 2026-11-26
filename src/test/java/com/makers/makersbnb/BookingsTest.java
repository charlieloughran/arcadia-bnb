package com.makers.makersbnb;


import com.makers.makersbnb.model.Booking;
import com.makers.makersbnb.model.Space;
import com.makers.makersbnb.repository.BookingRepository;
import com.makers.makersbnb.repository.SpaceRepository;
import com.microsoft.playwright.*;
import com.microsoft.playwright.options.AriaRole;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.Optional;

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

    @BeforeEach
    void setUp() {
        bookingRepository.deleteAll();
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

        Assertions.assertEquals(LocalDate.of(2026, 9, 12), foundBooking.getBooked_from());
    }

    @Test
    // Booking is available when signed in
    public void bookingIsAvailableSignedIn(){
        page.navigate(("http://localhost:8080/spaces/1"));

        page.getByLabel("Book From").fill("2024-06-23");
        page.getByLabel("Book Until").fill("2024-07-23");
        page.getByRole(AriaRole.BUTTON).click();


        assertThat(page).hasURL("http://localhost:8080/");
    }
    @Test
    // Booking is outside space availability
    public void bookingIsOutsideSpaceAvailability(){


        page.navigate("http://localhost:8080/spaces/1");

        page.getByLabel("Book From").fill("2024-06-23");
        page.getByLabel("Book Until").fill("2027-07-23");
        page.getByRole(AriaRole.BUTTON).click();

        page.waitForURL("http://localhost:8080/spaces/1");

        assertThat(page).hasURL("http://localhost:8080/spaces/1");

    }

    @Test
    // Space is already booked for those dates
    public void spaceIsAlreadyBookedForRequestedDates(){
        Optional<Space> space = spaceRepository.findById(1L);

        Booking existingBooking = new Booking(LocalDate.of(2024, 6, 24), LocalDate.of(2024, 6, 26), "1232131", space.orElse(null));
        bookingRepository.save(existingBooking);

        page.navigate(("http://localhost:8080/spaces/1"));

        page.getByLabel("Book From").fill("2024-06-25");
        page.getByLabel("Book Until").fill("2024-06-27");
        page.getByRole(AriaRole.BUTTON).click();




        assertThat(page).hasURL("http://localhost:8080/spaces/1");


    }




}

