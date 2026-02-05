package com.example.d424_software_engineering_capstonee.utils;

import com.example.d424_software_engineering_capstonee.entities.Conference;
import com.example.d424_software_engineering_capstonee.entities.Trip;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ReportGen {
    public static String generateTripReport(List<Trip> trips, List<Conference> conferences){
        StringBuilder report = new StringBuilder();

        SimpleDateFormat sf = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss", Locale.US);
        String timeStamp = sf.format(new Date());

        report.append("-------------------------------------\n");
        report.append("               Trip Report           \n");
        report.append("-------------------------------------\n");
        report.append("Generated: ").append(timeStamp).append("\n");
        report.append("-------------------------------------\n");

        report.append("SUMMARY\n");
        report.append("-------------------------------------\n");
        report.append(String.format("%-30s %d\n", "Total Trips:", trips.size()));
        report.append(String.format("%-30s %d\n", "Total Conferences:", conferences.size()));

        double budget = 0;
        for(Trip trip : trips){
            budget += trip.getPrice();
        }
        report.append(String.format("%-3s $%.2f\n", "Budget:", budget));
        report.append("\n\n");

        //Trip details

        report.append("Trip Details\n");
        report.append("==========================================\n");

        for(Trip trip : trips) {
            report.append(String.format("%-20s %s\n", "Name", trip.getTripName()));
            report.append(String.format("%-20s %s\n", "Hotel:", trip.getHotel()));
            report.append(String.format("%-20s %s\n", "Start Date:", trip.getStartDate()));
            report.append(String.format("%-20s %s\n", "End Date:", trip.getEndDate()));
            report.append(String.format("%-20s $%.2f\n", "Budget:", trip.getPrice()));

            List<Conference> tripConferences = new ArrayList<>();
            for(Conference conf : conferences){
                if(conf.getTripID() == trip.getTripID()){
                    tripConferences.add(conf);
                }
            }
            report.append(String.format("%-20s %d\n", "Conferences:", tripConferences.size()));

            if(!tripConferences.isEmpty()){
                report.append("\n Conference Details:\n");
                report.append("     =============================================\n");
                for(Conference conf : tripConferences){
                    report.append(String.format("   | %-15s | %s\n", conf.getConferenceName(), conf.getDate()));
                }
                report.append("   ======================================\n");
            }
            report.append("\n\n");

        }
        report.append("=========================================================");
        report.append("                     END OF REPORT                ");
        report.append("=====================================================");

        return report.toString();
    }

    public static String generateReport(List<Trip> trips) {
        StringBuilder report = new StringBuilder();

        SimpleDateFormat sf = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss", Locale.US);
        String timestamp = sf.format(new Date());

        report.append("=================================================================");
        report.append("                         TRIP SUMMARY REPORT                      ");
        report.append("Generated: ").append(timestamp).append("\n");
        report.append("==================================================================");
        report.append(String.format("%-5s %-25s %-20s %-15s %-12s\n",
                "ID", "NAME", "HOTEL", "START DATE", "BUDGET"));
        report.append("--------------------------------------------------------------------\n");

        double total = 0;
        for(Trip trip : trips) {
            report.append(String.format("%-5d %-25s %-20s %-15s $%-11.2f\n",
                    trip.getTripID(),
                    truncate(trip.getTripName(), 25),
                    truncate(trip.getHotel(), 20),
                    trip.getStartDate(),
                    trip.getPrice()));
            total += trip.getPrice();

        }
        report.append("-----------------------------------------------------\n");
        report.append(String.format("%-61s $%-11.2f\n", "TOTAL:", total));
        report.append("========================================================================\n");

        return report.toString();
    }
    private static String truncate(String str, int length) {
        if(str == null) {
            return "";
        } else {
            return str.length() > length ? str.substring(0, length - 3) + "...." : str;
        }
    }
}
