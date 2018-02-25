package ticket;

import seat.SeatLocation;

public class OnlineTicket extends CinemaTicket {
    // COR
    private OnlineTicket successor;

    public OnlineTicket(OnlineTicket successor) {
        this.successor = successor;
    }

    public SeatLocation selectSeat() {
        return null;
    }

    public OnlineTicket getSuccessor() {
        return successor;
    }
}
