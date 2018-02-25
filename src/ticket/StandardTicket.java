package ticket;

import seat.SeatLocation;

public class StandardTicket extends OnlineTicket {

    public StandardTicket(OnlineTicket successor) {
        super(successor);
        super.setPrice(9.0);
    }

    @Override
    public SeatLocation selectSeat() {
        return super.selectSeat();
    }
}
