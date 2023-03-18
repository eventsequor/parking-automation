package com.parking.testmodules.integration;

import com.parking.PagesLinks;
import com.parking.database.mysql.ReservationDBInfo;
import com.parking.models.reservations.viewmodel.Reservation;
import com.parking.steps.BaseStepsUI;
import io.qameta.allure.Description;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class ReservationIntegrationTests extends BaseStepsUI {

    @Test
    @DisplayName("Reservation: Check list of values against service")
    @Description("This test has the target of check the values in the screen are the same against the service")
    public void checkListReservationsService() {
        //Given
        navigateTo(PagesLinks.RESERVATION);

        //When
        getReservationListFromScreen();

        //Then
        compareReservationListAgainstService();
    }


    @Test
    @DisplayName("Check list of values against service")
    @Description("This test has the target of check the values in the screen are the same against the database")
    public void checkListReservationsDataBase() {
        //Given
        navigateTo(PagesLinks.RESERVATION);

        //When
        getReservationListFromScreen();

        //Then
        compareReservationListAgainstDataBase();
    }

    @ParameterizedTest(name = "Validate the reservation exist for plate {0}")
    @DisplayName("Create reservation")
    @MethodSource("reservationPlateList")
    @Description("This test has the target of check the values in the screen are the same against the database")
    public void createReservationFailed(String plate) {
        // Given
        navigateTo(PagesLinks.RESERVATION);

        // When
        Reservation reservation = Reservation.builder()
                .plate(plate)
                .scheduleDay("04/11/2023")
                .location("Bloque A 2")
                .schedule("Tarde")
                .build();
        createReservation(reservation);

        // Then
        checkMessageAlertOnReservationView("Error sin cupo disponible para esa locacionya tiene reservacion");
    }

    public static Stream<Object> reservationPlateList() {
        List<Arguments> argumentsList = new ArrayList<>();
        ReservationDBInfo.getReservatedPlatesList().forEach(
                s -> argumentsList.add(Arguments.of(s))
        );
        return Stream.of(argumentsList.toArray(new Arguments[0]));
    }
}
