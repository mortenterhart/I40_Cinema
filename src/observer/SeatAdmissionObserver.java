package observer;

import cinema.Cinema;
import client.ClientGroup;
import logging.Logger;

public class SeatAdmissionObserver implements ICinemaObserver {
    private Cinema cinema;
    private boolean triggered = false;

    public SeatAdmissionObserver(Cinema cinema) {
        this.cinema = cinema;
    }

    public void notifyCinemaIsFull() {
        Logger.instance.log("SeatAdmissionObserver: Detected full cinema, closing box office");
        cinema.closeTicketOffice();
        triggered = true;
    }

    public void notifyCinemaIs95PercentFull() {
        Logger.instance.log("SeatAdmissionObserver: Detected 95 percent full cinema, closing box office");
        cinema.closeTicketOffice();
        triggered = true;
    }

    public boolean wasTriggered() {
        return triggered;
    }

    public void resetTriggered() {
        triggered = false;
    }

    public void notifyOfferRejection(ClientGroup rejectingGroup) {
        throw new UnsupportedOperationException("unsupported operation (" + getClass().getName() + "): notifyOfferRejection");
    }
}
