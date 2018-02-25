package cinema;

import block.Block;
import client.Client;
import client.ClientGroup;
import client.OnlineClient;
import client.RealClient;
import seat.SeatLocation;
import ticket.CinemaTicket;

import java.util.List;
import java.util.function.Predicate;

public class OfficeCounter implements IClientVisitor {
    private final BoxOfficeTerminal terminal;
    private CinemaTicket.Builder ticketBuilder;
    private final Predicate<ClientGroup> clientCondition;
    private final int cinemaId;

    public OfficeCounter(Block leftBlock, Predicate<ClientGroup> clientCondition, int cinemaId) {
        this.terminal = new BoxOfficeTerminal(leftBlock);
        this.clientCondition = clientCondition;
        this.cinemaId = cinemaId;
    }

    public boolean accepts(ClientGroup group) {
        return clientCondition.test(group);
    }

    // 1. Gruppe von Kunden kommt mit bestimmter Präferenz zur Kasse
    // 2. Kunden geben ihre Präferenz an
    // 3. Kasse macht Angebot (COR)
    // 4. Kunde akzeptiert mit 70% Wahrscheinlichkeit
    // 5. Kunde bekommt Ticket oder geht
    public void offerSeats(ClientGroup group) {
        if (terminal.isPreferredSectionFree(group)) {
            terminal.markOfferedSeats(group);

        } else {
            List<SeatLocation> nextSeatProposals = terminal.chooseNextFreeSection(group.getSize());
            if (nextSeatProposals != null) {
                int listIndex = 0;
                for (Client client : group.getMembers()) {
                    client.offerSeat(nextSeatProposals.get(listIndex));
                    listIndex++;
                }
            }
        }
    }

    public double createTicketFor(RealClient client) {
        CinemaTicket normalTicket = createTicket(client.getOfferedSeatLocation(), "Life of Brian", 10);
        client.receiveTicket(normalTicket);
        return 0;
    }

    public double createTicketFor(OnlineClient client) {
        return 0;
    }

    public void equip(CinemaTicket.Builder ticketBuilder) {
        this.ticketBuilder = ticketBuilder;
    }

    private CinemaTicket createTicket(SeatLocation location, String movieName, double price) {
        return ticketBuilder.setSeatLocation(location)
                .setMovieName(movieName)
                .setPrice(price)
                .setCinemaId(cinemaId)
                .build();
    }

    private void connectTerminal(Block leftBlock) {
        terminal.setLeftBlock(leftBlock);
    }
}
