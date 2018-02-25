package observer;

import cinema.Cinema;
import client.Client;

public class SeatAdmissionObserver implements ICinemaObserver {
    private Cinema cinema;

    public SeatAdmissionObserver(Cinema cinema) {
        this.cinema = cinema;
    }

    public void notifyCinemaIsFull() {

    }

    public void notifyCinemaIs95PercentFull() {

    }

    public void notifyOfferRejection(Client rejectingClient) {
        throw new UnsupportedOperationException("unsupported operation (" + getClass().getName() + "): notifyOfferRejection");
    }
}
