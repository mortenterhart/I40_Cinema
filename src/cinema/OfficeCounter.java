package cinema;

import block.Block;
import client.Client;
import client.ClientGroup;
import client.OnlineClient;
import client.RealClient;
import logging.Logger;
import observer.ICinemaObserver;
import seat.SeatLocation;
import ticket.*;

import java.util.List;
import java.util.function.Predicate;

public class OfficeCounter implements IClientVisitor {
    private final BoxOfficeTerminal terminal;
    private CinemaTicket.Builder ticketBuilder;
    private final Predicate<ClientGroup> clientCondition;
    private final int cinemaId;

    private ICinemaObserver seatAdmissionObserver;

    public OfficeCounter(Block leftBlock, Predicate<ClientGroup> clientCondition, int cinemaId) {
        this.terminal = new BoxOfficeTerminal(leftBlock);
        this.clientCondition = clientCondition;
        this.cinemaId = cinemaId;
    }

    public boolean accepts(ClientGroup group) {
        return clientCondition.test(group);
    }

    public void offerSeats(ClientGroup group) {
        Logger.instance.log("   > Check if preferred section (" + group.getPreference().getBlock().getLocation().name() +
                "block , section " + group.getPreference().getSection().getIdentifier() + ") is free");
        if (terminal.isPreferredSectionFree(group)) {
            Logger.instance.log(">> Preferred section is free");
            terminal.markOfferedSeats(group);
        } else {
            Logger.instance.log(">> Preferred section is not free, searching in current block for next seats");
            List<SeatLocation> nextSeatProposals = terminal.chooseNextFreeSection(group.getSize());
            if (nextSeatProposals != null) {
                Logger.instance.log(">> Found another seat combination, setting new found seats to clients");
                int listIndex = 0;
                for (Client client : group.getMembers()) {
                    client.offerSeat(nextSeatProposals.get(listIndex));
                    Logger.instance.log("  > Seat " + nextSeatProposals.get(listIndex) + " offered to client " + client);
                    listIndex++;
                }
            } else {
                Logger.instance.log(">> Did not find any other seat combination, sending notification to observer");
                Logger.instance.log("    > Cinema is full, notifying observer");
                this.notifyCinemaIsFull();
            }
        }
    }

    public void createTicketFor(RealClient client) {
        Logger.instance.log("OfficeCounter: Creating ticket for RealClient " + client);
        CinemaTicket normalTicket = createTicket(client.getOfferedSeatLocation(), "Life of Brian", 10);
        Logger.instance.log("OfficeCounter: Registering ticket in client and reserving seat location");
        Logger.instance.log("   > Ticket information: " + normalTicket);
        client.receiveTicket(normalTicket);
    }

    public void createTicketFor(OnlineClient client, OnlineTicketType type) {
        Logger.instance.log("OfficeCounter: Creating ticket for OnlineClient of type " + type.name());
        switch (type) {
            case standard:
                client.receiveTicket(new StandardTicket());
                break;
            case hotDeal:
                client.receiveTicket(new HotDealTicket());
                break;
            case surprise:
                client.receiveTicket(new SurpriseTicket());
        }
    }

    public void notifyCinemaIsFull() {
        seatAdmissionObserver.notifyCinemaIsFull();
    }

    public void equip(CinemaTicket.Builder ticketBuilder) {
        this.ticketBuilder = ticketBuilder;
    }

    public void equip(ICinemaObserver seatAdmissionObserver) {
        this.seatAdmissionObserver = seatAdmissionObserver;
    }

    private CinemaTicket createTicket(SeatLocation location, String movieName, double price) {
        return ticketBuilder.setSeatLocation(location)
                .setMovieName(movieName)
                .setPrice(price)
                .setCinemaId(cinemaId)
                .build();
    }

    public BoxOfficeTerminal getTerminal() {
        return terminal;
    }
}
