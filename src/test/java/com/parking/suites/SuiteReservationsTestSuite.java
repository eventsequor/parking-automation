package com.parking.suites;


import com.parking.testmodules.backend.ReservationsTests;
import com.parking.testmodules.front.CompositionValidationTests;
import com.parking.testmodules.integration.ReservationIntegrationTests;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.SuiteDisplayName;
import org.junit.runner.RunWith;

@RunWith(JUnitPlatform.class)
@SuiteDisplayName("Reservations")
@SelectClasses({
        CompositionValidationTests.class,
        ReservationsTests.class,
        ReservationIntegrationTests.class
})
public class SuiteReservationsTestSuite {
}
