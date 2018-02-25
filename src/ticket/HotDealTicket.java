package ticket;

import seat.SeatLocation;

public class HotDealTicket extends OnlineTicket {

    public HotDealTicket(OnlineTicket successor) {
        super(successor);
        super.setPrice(8.0);
    }

    @Override
    public SeatLocation selectSeat() {
        return super.selectSeat();
    }
}
