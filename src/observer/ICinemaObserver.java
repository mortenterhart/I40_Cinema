package observer;

import client.Client;

public interface ICinemaObserver {
    void notifyCinemaIsFull();

    void notifyCinemaIs95PercentFull();

    void notifyOfferRejection(Client rejectingClient);
}
