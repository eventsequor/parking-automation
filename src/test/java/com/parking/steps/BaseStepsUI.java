package com.parking.steps;

import com.parking.PagesLinks;
import com.parking.basetestclasses.BaseTestSingleUI;
import com.parking.database.mysql.ReservationDBInfo;
import com.parking.models.reservations.apimodel.ResponseReservations;
import com.parking.models.reservations.viewmodel.Reservation;
import com.parking.pageobjects.reservation.ReservationPageObject;
import io.qameta.allure.Step;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Assert;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.*;


public class BaseStepsUI extends BaseTestSingleUI {

    private List<Reservation> reservationListFromScreen;

    @Step("Given a view {0} the system goes to it")
    public void navigateTo(PagesLinks pagesLinks) {
        getWebDriverManager().getWebDriver().get(pagesLinks.getPath());
    }

    @Step("When as a user I watch the reservation view and Then I check the all elements should be present")
    public void checkTheElementIntoTheScreenReservation() {
        ReservationPageObject reservationPO = new ReservationPageObject(getWebDriverManager());

        checkPresentElement("Reservation button", true, reservationPO.isPresentNewReservationButton());

        checkText("Reservaciones", reservationPO.getTextReservationButton());

        checkText("Perfil", reservationPO.getTextProfileButton());

        checkText("Ayuda", reservationPO.getTextHelpButton());

        checkText("Cerrar sesión", reservationPO.getTextCloseSession());

        checkText("Nueva reserva", reservationPO.getTextNewReservationButton());

        checkText("Listado de reservaciones", reservationPO.getTextContainerTitle());
    }

    @Step("The element {0} should be present {1}, expected result {2}")
    private void checkPresentElement(String nameElement, boolean expected, boolean currently) {
        softAssertions.assertThat(expected)
                .as("The " + nameElement + " element should be present")
                .isEqualTo(currently);
    }

    @Step("Validate text expected result '{0}' actual result '{1}'")
    public void checkText(String expected, String actual) {
        softAssertions.assertThat(expected)
                .as("The text should be: " + expected)
                .isEqualToIgnoringCase(actual);
    }

    @Step("When the user gets the information of reservation into the screen")
    public void getReservationListFromScreen() {
        reservationListFromScreen = new ReservationPageObject(getWebDriverManager()).getListReservation();
    }

    @Step("Then the user compare the values between the screen to service")
    public void compareReservationListAgainstService() {
        Response response = RestAssured.given().baseUri(baseUrl)
                .filter(new AllureRestAssured())
                .when()
                .get(apiReservations);
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

        compareList(reservationListFromScreen, reservationListFromService);
    }

    @Step("Then the user compare the values between the screen to service")
    public void compareReservationListAgainstDataBase() {
        Response response = RestAssured.given().baseUri(baseUrl)
                .filter(new AllureRestAssured())
                .when()
                .get(apiReservations);
        ResponseReservations dataItem = response.body().as(ResponseReservations.class);
        List<Reservation> reservationListFromDataBase = ReservationDBInfo.getListReservation()
                .stream()
                .sorted(Comparator.comparing(Reservation::getPlate))
                .collect(Collectors.toList());

        compareList(reservationListFromScreen, reservationListFromDataBase);
    }

    @Step("When the user do the reservation {0}")
    public void createReservation(Reservation reservation) {
        ReservationPageObject reservationPageObject = new ReservationPageObject(getWebDriverManager());
        reservationPageObject.clickNewReservation();
        assertEquals(true, reservationPageObject.isPresentModalDialog());
        reservationPageObject.sendKeysPlateInput(reservation.getPlate());
        reservationPageObject.sendKeysInputDate(reservation.getScheduleDay());
        reservationPageObject.selectDate(reservation.getSchedule());
        reservationPageObject.selectLocation(reservation.getLocation());
        reservationPageObject.clickSaveReservationButton();
    }

    @Step("Then validate alert message  '{0}'")
    public void checkMessageAlertOnReservationView(String alertMessage) {
        ReservationPageObject reservationPageObject = new ReservationPageObject(getWebDriverManager());
        String messageFromAlert = reservationPageObject.getAlertMessage();
        checkText(alertMessage, messageFromAlert);
    }
}
