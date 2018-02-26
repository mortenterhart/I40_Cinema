package ticket;

import seat.SeatLocation;

public class OnlineTicket extends CinemaTicket {
    private OnlineTicketType type;

    public void setType(OnlineTicketType type) {
        this.type = type;
    }

    public void receiveSeatLocation(SeatLocation chosenSeat) {
        super.setSeatLocation(chosenSeat);
        super.getSeatLocation().reserve();
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
