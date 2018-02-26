package main;

import cinema.Cinema;
import client.ClientGroup;
import logging.Logger;

public class CinemaSimulator {
    private IAccessHandler realHandler;
    private IAccessHandler onlineHandler;

    public void startSimulation() {
        Cinema cinema = new Cinema();
        // Set the left block as static member to ClientGroup to be
        // able to generate random client preferences for seats
        ClientGroup.setLeftBlock(cinema.getLeftBlock());

        Logger.instance.log("\nCinema is open for online bookings now, waiting for access requests");
        onlineHandler = new OnlineAccessHandler(cinema);
        onlineHandler.handleAccess();
        Logger.instance.newLine();

        Logger.instance.log("Online Access has retired, box office opens eventually ...");

        realHandler = new RealAccessHandler(cinema);
        realHandler.handleAccess();

        Logger.instance.log("Box office has closed, movie starts in 10 minutes\n");

        Logger.instance.log("10 minutes later ...");
        cinema.rollOutScreen();
        cinema.darkenRoom();
        cinema.startProjector();
    }
}
