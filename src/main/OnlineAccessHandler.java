package main;

import cinema.BoxOfficeTerminal;
import cinema.Cinema;
import cinema.OfficeCounter;
import client.Client;
import client.ClientGroup;
import client.OnlineClient;
import logging.Logger;
import proxy.ICounterOfficeAccess;
import proxy.ProxyAccess;
import seat.SeatLocation;
import ticket.OnlineTicket;
import ticket.OnlineTicketType;
import utility.EnumUtility;

import java.util.List;

public class OnlineAccessHandler implements IAccessHandler {
    private Cinema cinema;
    private OfficeCounter onlineCounter;
    private BoxOfficeTerminal terminal;

    public OnlineAccessHandler(Cinema cinema) {
        Logger.instance.log(" -- Constructing an OnlineAccessHandler allowing clients to buy online tickets");
        this.cinema = cinema;
        this.onlineCounter = cinema.getTicketOffice().getOnlineCounter();
        this.terminal = onlineCounter.getTerminal();
    }

    public void handleAccess() {
        Logger.instance.log("### Online Access Handling");
        Logger.instance.log(" -- Performing bookings for " + Configuration.instance.numberOfOnlineBookings + " online client groups");
        for (int i = 0; i < Configuration.instance.numberOfOnlineBookings; i++) {
            ClientGroup onlineGroup = createOnlineVisitors();
            Logger.instance.log("    > Created new example group with " + onlineGroup.getSize() + " members: " + onlineGroup.getMembers());
            OnlineTicketType ticketType = EnumUtility.randomEnumConstant(OnlineTicketType.class);
            Logger.instance.log("    > Clients will receive " + ticketType.name() + " online ticket");

            for (Client client : onlineGroup.getMembers()) {
                Logger.instance.log("    > Setting up proxy access for client '" + client.getName() + "'");
                ICounterOfficeAccess proxyAccess = new ProxyAccess(onlineCounter, (OnlineClient) client, ticketType);
                try {
                    Logger.instance.log("    > Booking " + ticketType.name() + " ticket for this client\n");
                    proxyAccess.bookTicket();
                } catch (IllegalAccessException exception) {
                    Logger.instance.logError("ERROR: unknown client type " + client);
                    exception.printStackTrace();
                }
            }

            chooseSeatsForType(onlineGroup, ticketType);
            waitInterval(Configuration.instance.iterationInterval);
            Logger.instance.logLine();
        }
    }

    private void chooseSeatsForType(ClientGroup onlineGroup, OnlineTicketType ticketType) {
        switch (ticketType) {
            case standard:
                chooseSeatsStandard(onlineGroup);
                break;
            case hotDeal:
                chooseSeatsHotDeal(onlineGroup);
                break;
            case surprise:
                chooseSeatsSurprise(onlineGroup);
                break;
        }
    }

    private void chooseSeatsStandard(ClientGroup onlineGroup) {
        Logger.instance.log("    > Choosing seats for group according to preference");
        if (terminal.isPreferredSectionFree(onlineGroup)) {
            terminal.markOfferedSeats(onlineGroup);

            for (Client client : onlineGroup.getMembers()) {
                reserveSeat(client);
                Logger.instance.log("      -> Reserved seat " + client.getOfferedSeatLocation() + " for client '" + client.getName() + "'");
            }
        } else {
            Logger.instance.log("      Preference not free, choosing another seat combination in this block");
            List<SeatLocation> seatProposals = terminal.chooseNextFreeSection(onlineGroup.getSize());
            reserveAllSeats(onlineGroup, seatProposals);
        }
    }

    private void chooseSeatsHotDeal(ClientGroup onlineGroup) {
        Logger.instance.log("     > Choosing random seats in specific block " + onlineGroup.getPreference().getBlock().getLocation().name());
        List<SeatLocation> seatProposals = terminal.chooseNextFreeSection(onlineGroup.getSize());
        reserveAllSeats(onlineGroup, seatProposals);
    }

    private void chooseSeatsSurprise(ClientGroup onlineGroup) {
        Logger.instance.log("     > Choosing random seats for surprise tickets");
        List<SeatLocation> seatProposals = terminal.fetchRandomSeats(onlineGroup.getSize());
        reserveAllSeats(onlineGroup, seatProposals);
    }


    private void reserveAllSeats(ClientGroup onlineGroup, List<SeatLocation> seatProposals) {
        if (seatProposals != null) {
            int listIndex = 0;
            for (Client client : onlineGroup.getMembers()) {
                reserveSeat(client, seatProposals.get(listIndex));
                listIndex++;
            }
        } else {
            onlineCounter.notifyCinemaIsFull();
        }
    }

    private void reserveSeat(Client client, SeatLocation seat) {
        ((OnlineTicket) client.getTicket()).receiveSeatLocation(seat);
    }

    private void reserveSeat(Client client) {
        reserveSeat(client, client.getOfferedSeatLocation());
    }

    private ClientGroup createOnlineVisitors() {
        int groupSize = Configuration.instance.mersenneTwister.nextInt(1, 4);

        ClientGroup onlineClients = new ClientGroup();
        for (int i = 0; i < groupSize; i++) {
            onlineClients.addMember(new OnlineClient("Online Customer", randomAgeNearConfinement()));
        }
        return onlineClients;
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
