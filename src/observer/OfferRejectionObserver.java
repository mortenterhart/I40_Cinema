package observer;

import cinema.Cinema;
import client.Client;
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

    public void notifyOfferRejection(Client rejectingClient) {
        numberOfRejectedOffers++;
        Logger.instance.log("Client " + rejectingClient.getName() + " rejected the offer");

        if (numberOfRejectedOffers > 3) {
            cinema.
        }
    }

    public int getNumberOfRejectedOffers() {
        return numberOfRejectedOffers;
    }

    private void triggerUnsupportedOperationException(String operation) {
        throw new UnsupportedOperationException("unsupported operation (" + getClass().getName() + "):" + operation);
    }
}
