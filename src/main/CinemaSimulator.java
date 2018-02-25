package main;

import cinema.BoxOffice;
import cinema.Cinema;
import cinema.IClientVisitor;
import client.Client;
import client.ClientGroup;
import client.RealClient;
import logging.Logger;

public class CinemaSimulator {

    public void startSimulation() {
        Cinema cinema = new Cinema();
        BoxOffice ticketOffice = cinema.getTicketOffice();
        ticketOffice.open();
        Logger.instance.write("Box office opens");
        for (int i = 0; i < 5; i++) {
            ClientGroup randomGroup = generateRandomGroup();
            IClientVisitor officeCounter = ticketOffice.getFittingCounterFor(randomGroup);
            ticketOffice.guideToCounter(randomGroup);

            boolean offerAccepted = true;
            RealClient rejectingClient = null;
            for (Client client : randomGroup.getMembers()) {
                RealClient realClient = (RealClient) client;
                if (!realClient.acceptsOffer()) {
                    offerAccepted = false;
                    rejectingClient = realClient;
                }
            }

            if (offerAccepted) {
                for (Client client : randomGroup.getMembers()) {
                    RealClient realClient = (RealClient) client;
                    realClient.takeTicket(officeCounter);
                }
            } else {
                cinema.reportClientRejection(rejectingClient);
                rejectingClient.leaveCinema();
            }

            waitInterval(Configuration.instance.iterationInterval);
        }
        ticketOffice.close();

        Logger.instance.write("Box office has closed, movie starts in 10 minutes");

        cinema.rollOutScreen();
        cinema.startProjector();
    }

    private ClientGroup generateRandomGroup() {
        int groupSize = Configuration.instance.mersenneTwister.nextInt(1, 4);

        ClientGroup group = new ClientGroup();
        for (int i = 0; i < groupSize; i++) {
            group.addMember(new RealClient("", randomAgeNearConfinement()));
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
