package cinema;

import block.Block;
import client.ClientGroup;

public class BoxOffice {
    private OfficeCounter normalCounter;
    private OfficeCounter onlineCounter;

    public BoxOffice(Block leftBlock, int cinemaId) {
        normalCounter = new OfficeCounter(leftBlock, clientGroup -> clientGroup.getSize() <= 2, cinemaId);
        onlineCounter = new OfficeCounter(leftBlock, clientGroup -> clientGroup.consistsOfOnlineClients() ||
                clientGroup.getSize() >= 3, cinemaId);

        CinemaTicket.Builder ticketBuilder = new CinemaTicket.Builder();
        normalCounter.equip(ticketBuilder);
        onlineCounter.equip(ticketBuilder);
    }

    public void guideToCounter(ClientGroup group) {
        if (normalCounter.accepts(group)) {
            normalCounter.offerSeats(group);
            return;
        } else if (onlineCounter.accepts(group)) {
            onlineCounter.offerSeats(group);
            return;
        }

        triggerIllegalStateException(group);
    }

    public IClientVisitor getFittingCounterFor(ClientGroup group) {
        if (normalCounter.accepts(group)) {
            return normalCounter;
        } else if (onlineCounter.accepts(group)) {
            return onlineCounter;
        }

        triggerIllegalStateException(group);
        return null;
    }

    private void triggerIllegalStateException(ClientGroup group) {
        throw new IllegalStateException("client group not recognized: " + group);
    }

    public OfficeCounter getNormalCounter() {
        return normalCounter;
    }

    public OfficeCounter getOnlineCounter() {
        return onlineCounter;
    }
}
