package com.parking.testmodules.front;

import com.parking.PagesLinks;
import com.parking.steps.BaseStepsUI;
import io.qameta.allure.Description;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class CompositionValidationTests extends BaseStepsUI {

    @Test
    @DisplayName("Reservation view components test")
    @Description("This test has the target to check the different view components")
    public void testLabelOnScreen() {
        // Given a page
        navigateTo(PagesLinks.RESERVATION);

        //Then and when
        checkTheElementIntoTheScreenReservation();
    }
}
