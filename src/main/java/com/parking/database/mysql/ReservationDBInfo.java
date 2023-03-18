package com.parking.database.mysql;

import com.parking.models.reservations.viewmodel.Reservation;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ReservationDBInfo {

    public static List<Reservation> getListReservation() {
        List<Reservation> reservationList = new ArrayList<>();
        String query = "SELECT \n" +
                "parking_db.vehicles.plat_number as plate\n" +
                ",parking_db.locations.name as location\n" +
                ",parking_db.vehicle_ins.schedule\n" +
                ",parking_db.vehicle_ins.schedule_day as scheduleDay\n" +
                " FROM parking_db.vehicle_ins \n" +
                " JOIN parking_db.vehicles ON parking_db.vehicles.id = parking_db.vehicle_ins.vehicle_id\n" +
                " JOIN parking_db.locations ON parking_db.locations.id = parking_db.vehicle_ins.location_id;";
        MySQLAccess mySQLAccess = new MySQLAccess("root", "root", "3307", "parking_db");
        ResultSet resultSet = mySQLAccess.executeQuery(query);
        while (true) {
            try {
                if (!resultSet.next()) break;
                reservationList.add(Reservation.builder()
                        .plate(resultSet.getString("plate"))
                        .location(resultSet.getString("location"))
                        .schedule(resultSet.getString("schedule"))
                        .scheduleDay(resultSet.getString("scheduleDay"))
                        .build()
                );
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        mySQLAccess.closeConnection();

        return reservationList;
    }

    public static List<String> getReservatedPlatesList() {
        List<String> plateList = new ArrayList<>();
        String query = "Select \n" +
                " parking_db.vehicles.plat_number as plate\n" +
                " FROM parking_db.vehicle_ins \n" +
                " JOIN parking_db.vehicles ON parking_db.vehicles.id = parking_db.vehicle_ins.vehicle_id;";
        MySQLAccess mySQLAccess = new MySQLAccess("root", "root", "3307", "parking_db");
        ResultSet resultSet = mySQLAccess.executeQuery(query);
        while (true) {
            try {
                if (!resultSet.next()) break;
                plateList.add(resultSet.getString("plate"));
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        mySQLAccess.closeConnection();
        return plateList;
    }

}
