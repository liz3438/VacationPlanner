package com.example.d424_software_engineering_capstonee.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

    @Entity(tableName = "conferences")
    public class Conference {
        @PrimaryKey(autoGenerate = true)
        private int conferenceID;
        private String conferenceName;
        private String date;
        private int tripID;

        public Conference(String conferenceName, String date, int tripID) {

            this.conferenceName = conferenceName;
            this.tripID = tripID;
            this.date= date;


        }

        public int getConferenceID() {
            return conferenceID;
        }

        public void setConferenceID(int conferenceID) {
            this.conferenceID = conferenceID;
        }

        public String getConferenceName() {
            return conferenceName;
        }

        public void setConferenceName(String conferenceName) {
            this.conferenceName = conferenceName;
        }
        public String getDate(){
            return date;
        }
        public void setDate(String date) {
            this.date = date;
        }

        public int getTripID() {
            return tripID;
        }

        public void setTripID(int tripID) {
            this.tripID = tripID;
        }
    }


