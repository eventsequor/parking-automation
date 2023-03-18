package com.parking.basetestclasses;

import com.parking.models.reservations.viewmodel.Reservation;
import io.qameta.allure.Step;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import java.util.List;


public class BaseTest {

    protected SoftAssertions softAssertions;

    protected String baseUrl = "http://localhost:8000/";
    protected String apiReservations = "/api/reservations";
    protected String apiReservation = "/api/reservation";

    @BeforeEach
    public void beforeAll() {
        softAssertions = new SoftAssertions();
    }

    @AfterEach
    public void afterAll() {
        softAssertions.assertAll();
    }

    @Step("Compare list reservations list one {0} against list two {1} should be equals")
    protected void compareList(List<Reservation> listOne, List<Reservation> listTwo) {
        softAssertions.assertThat(listOne)
                .as("Comparison between data base and information sent through service")
                .isEqualTo(listTwo);
    }
}
