package main;

import cinema.BoxOffice;
import cinema.Cinema;
import cinema.IClientVisitor;
import cinema.OfficeCounter;
import client.Client;
import client.ClientGroup;
import client.RealClient;
import logging.Logger;
import proxy.ICounterOfficeAccess;
import proxy.RealAccess;

public class RealAccessHandler implements IAccessHandler {
    private Cinema cinema;
    private BoxOffice ticketOffice;

    public RealAccessHandler(Cinema cinema) {
        Logger.instance.log(" -- Initializing RealAccessHandler for real customers of the cinema");
        this.cinema = cinema;
        this.ticketOffice = cinema.getTicketOffice();
    }

    public void handleAccess() {
        ticketOffice.open();

        while (ticketOffice.isOpen()) {
            ClientGroup visitorGroup = createRealCinemaVisitors();
            Logger.instance.log("    > New client group encountered the box office: " + visitorGroup.getMembers());
            IClientVisitor officeCounter = ticketOffice.getFittingCounterFor(visitorGroup);
            Logger.instance.log("    > Retrieving suitable counter for group of size " + visitorGroup.getSize());
            ticketOffice.guideToCounter(visitorGroup);

            Logger.instance.log("    > Checking if group accepts offer (" + Configuration.instance.percentageOfFullCinema + " probability)");
            if (ticketOffice.isOpen()) {
                if (visitorGroup.acceptsOffer()) {
                    Logger.instance.log(">> Accepted");
                    connect((OfficeCounter) officeCounter, visitorGroup);
                } else {
                    Logger.instance.log(">> Not accepted");
                    Logger.instance.log("    > Client refused offer, reporting to observer ...");
                    cinema.reportClientRejection(visitorGroup);
                    visitorGroup.leaveCinema();
                }
            }

            if (cinema.isFull()) {
                Logger.instance.log("    > Cinema reached full capacity, closing the box office ...");
                cinema.reportCinemaFull();
            } else if (cinema.is95PercentFull()) {
                Logger.instance.log("    > Cinema reached capacity of 95 percent, checking if 3 clients rejected offers already");
                cinema.reportCinema95PercentFull();
            }

            waitInterval(Configuration.instance.iterationInterval);
            Logger.instance.logLine();
        }
    }

    private void connect(OfficeCounter officeCounter, ClientGroup visitorGroup) {
        for (Client client : visitorGroup.getMembers()) {
            ICounterOfficeAccess access = new RealAccess(officeCounter, client);
            try {
                access.bookTicket();
            } catch (IllegalAccessException exception) {
                Logger.instance.log("ERROR: unknown client type " + client);
                exception.printStackTrace();
            }
        }
    }

    private ClientGroup createRealCinemaVisitors() {
        int groupSize = Configuration.instance.mersenneTwister.nextInt(1, 4);

        ClientGroup group = new ClientGroup();
        for (int i = 0; i < groupSize; i++) {
            group.addMember(new RealClient("Monty Python", randomAgeNearConfinement()));
        }

        return group;
    }

    private int randomAgeNearConfinement() {
        int ageConfinement = Configuration.instance.movieAgeConfinement;
        return Configuration.instance.mersenneTwister.nextInt(ageConfinement - 5, ageConfinement + 5);
    }

    private void waitInterval(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException exc) {
            exc.printStackTrace();
        }
    }
}
