package ticket;

import seat.SeatLocation;

public class SurpriseTicket extends OnlineTicket {

    public SurpriseTicket(OnlineTicket successor) {
        super(successor);
        super.setPrice(5.0);
    }

    @Override
    public SeatLocation selectSeat() {
        return super.selectSeat();
    }
}
