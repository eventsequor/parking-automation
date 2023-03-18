package com.parking.models.reservations.viewmodel;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@Builder
@ToString
@EqualsAndHashCode
public class Reservation {

    private String plate;
    private String location;
    private String schedule;
    private String scheduleDay;

}
