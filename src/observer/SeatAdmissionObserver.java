package observer;

import cinema.Cinema;
import client.ClientGroup;

public class SeatAdmissionObserver implements ICinemaObserver {
    private Cinema cinema;

    public SeatAdmissionObserver(Cinema cinema) {
        this.cinema = cinema;
    }

    public void notifyCinemaIsFull() {
        cinema.closeTicketOffice();
    }

    public void notifyCinemaIs95PercentFull() {
        cinema.closeTicketOffice();
    }

    public void notifyOfferRejection(ClientGroup rejectingGroup) {
        throw new UnsupportedOperationException("unsupported operation (" + getClass().getName() + "): notifyOfferRejection");
    }
}
