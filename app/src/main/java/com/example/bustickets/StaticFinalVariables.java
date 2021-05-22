package com.example.bustickets;

public class StaticFinalVariables {
    static String NameOfSelectedCity = "";
    static String NameOfSelectedBus = "";
    static int PositionOfSelectedCity = -1;
    static int PostiotionOfSelectedBus = -1;
    static double balance;
    static int ticketType;
    static String nextBus = "";
    static boolean next = false;

    public static boolean isNext() {
        return next;
    }

    public static void setNext(boolean next) {
        StaticFinalVariables.next = next;
    }

    public static void setNextBus(String nextBus) {
        StaticFinalVariables.nextBus = nextBus;
    }

    public static String getNextBus() {
        return nextBus;
    }

    public static void setTicketType(int ticketType) {
        StaticFinalVariables.ticketType = ticketType;
    }

    public static int getTicketType() {
        return ticketType;
    }

    public static double getBalance() {
        return balance;
    }

    public static void setBalance(double balance) {
        StaticFinalVariables.balance = balance;
    }

    public static void setNameOfSelectedBus(String nameOfSelectedBus) {
        NameOfSelectedBus = nameOfSelectedBus;
    }

    public static int getPostiotionOfSelectedBus() {
        return PostiotionOfSelectedBus;
    }

    public static String getNameOfSelectedBus() {
        return NameOfSelectedBus;
    }

    public static void setNameOfSelectedCity(String nameOfSelectedCity) {
        NameOfSelectedCity = nameOfSelectedCity;
    }

    public static void setPositionOfSelectedCity(int positionOfSelectedCity) {
        PositionOfSelectedCity = positionOfSelectedCity;
    }

    public static void setPostiotionOfSelectedBus(int postiotionOfSelectedBus) {
        PostiotionOfSelectedBus = postiotionOfSelectedBus;
    }

    public static String getNameOfSelectedCity() {
        return NameOfSelectedCity;
    }

    public static int getPositionOfSelectedCity() {
        return PositionOfSelectedCity;
    }
}
