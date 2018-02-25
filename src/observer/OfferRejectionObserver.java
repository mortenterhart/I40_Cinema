package observer;

import cinema.Cinema;
import client.ClientGroup;
import logging.Logger;

public class OfferRejectionObserver implements ICinemaObserver {
    private int numberOfRejectedOffers = 0;
    private Cinema cinema;

    public OfferRejectionObserver(Cinema cinema) {
        this.cinema = cinema;
    }

    public void notifyCinemaIsFull() {
        triggerUnsupportedOperationException("notifyCinemaIsFull");
    }

    public void notifyCinemaIs95PercentFull() {
        triggerUnsupportedOperationException("notifyCinemaIs95PercentFull");
    }

    public void notifyOfferRejection(ClientGroup rejectingGroup) {
        numberOfRejectedOffers++;
        Logger.instance.log("ClientGroup " + rejectingGroup + " rejected the offer");

        if (numberOfRejectedOffers > 3) {
            cinema.closeTicketOffice();
        }
    }

    public int getNumberOfRejectedOffers() {
        return numberOfRejectedOffers;
    }

    private void triggerUnsupportedOperationException(String operation) {
        throw new UnsupportedOperationException("unsupported operation (" + getClass().getName() + "):" + operation);
    }
}
