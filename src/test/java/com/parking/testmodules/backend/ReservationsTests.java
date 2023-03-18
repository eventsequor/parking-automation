package com.parking.testmodules.backend;

import com.parking.basetestclasses.BaseTestApiRest;
import com.parking.database.mysql.ReservationDBInfo;
import com.parking.models.reservations.apimodel.ResponseReservations;
import com.parking.models.reservations.viewmodel.Reservation;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Step;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.*;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Epic("Reservations")
public class ReservationsTests extends BaseTestApiRest {


    private Response response;

    @BeforeEach
    public void beforeEach() {
        response = RestAssured.given().baseUri(baseUrl)
                .filter(new AllureRestAssured())
                .when()
                .get(apiReservations);
    }

    @Tag("example")
    @Test
    @DisplayName("Reservation service check status code 200")
    @Description("This test check the list of reservations present")
    public void checkListReservations() {
        checkStatusCode(200, response.getStatusCode());
    }

    @Test
    @DisplayName("Reservation service check list reservations against database")
    @Description("This test check values are showing the response against data base")
    public void checkListActiveReservation() {
        ResponseReservations dataItem = response.body().as(ResponseReservations.class);
        List<Reservation> reservationListFromService = dataItem.getData().stream().map(dataItem1 ->
                        Reservation.builder()
                                .plate(dataItem1.getPlat())
                                .location(dataItem1.getReservationArea())
                                .schedule(dataItem1.getSchedule())
                                .scheduleDay(dataItem1.getDay())
                                .build())
                .sorted(Comparator.comparing(Reservation::getPlate))
                .collect(Collectors.toList());

        List<Reservation> reservationListFromDB = ReservationDBInfo.getListReservation().stream()
                .sorted(Comparator.comparing(Reservation::getPlate))
                .collect(Collectors.toList());

        compareList(reservationListFromService, reservationListFromDB);
        softAssertions.assertThat(reservationListFromDB)
                .as("Comparison between data base and information sent through service")
                .isEqualTo(reservationListFromService);
    }



    @Step("Check status code {0} expected vs {1} currently")
    public void checkStatusCode(int expectedStatus, int currentlyResult) {
        softAssertions.assertThat(expectedStatus)
                .as("The expected result status should be: " + currentlyResult)
                .isEqualTo(currentlyResult);
    }

    @Step("Check string values {0} expected vs {1} currently")
    public void checkStringValues(String expectedStatus, String currentlyResult) {
        softAssertions.assertThat(expectedStatus)
                .as("The expected result status should be: " + currentlyResult)
                .isEqualToIgnoringCase(currentlyResult);
    }

}
