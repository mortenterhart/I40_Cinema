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
        ClientGroup.setLeftBlock(cinema.getLeftBlock());

        // Online Ticket production

        BoxOffice ticketOffice = cinema.getTicketOffice();
        ticketOffice.open();
        Logger.instance.write("Box office opens");
        while (ticketOffice.isOpen()) {
            ClientGroup customerGroup = generateRandomGroup();
            IClientVisitor officeCounter = ticketOffice.getFittingCounterFor(customerGroup);
            ticketOffice.guideToCounter(customerGroup);
            Logger.instance.log("Group Size: " + customerGroup.getSize());
            Logger.instance.log("Group Preference: " + customerGroup.getPreference().getSection().getIdentifier() + ", " +
                    customerGroup.getPreference().getBlock().getClass().getSimpleName());

            if (customerGroup.acceptsOffer()) {
                Logger.instance.log("Offer accepted");
                for (Client client : customerGroup.getMembers()) {
                    RealClient realClient = (RealClient) client;
                    realClient.takeTicket(officeCounter);
                }
            } else {
                // Report observer
                cinema.reportClientRejection(customerGroup);
                customerGroup.leaveCinema();
            }

            // Always check if the cinema is full
            if (cinema.isFull()) {
                cinema.reportCinemaFull();
            } else if (cinema.is95PercentFull()) {
                cinema.reportCinema95PercentFull();
            }

            //waitInterval(Configuration.instance.iterationInterval);
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
