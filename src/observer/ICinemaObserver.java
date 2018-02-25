package observer;

import client.ClientGroup;

public interface ICinemaObserver {
    void notifyCinemaIsFull();

    void notifyCinemaIs95PercentFull();

    void notifyOfferRejection(ClientGroup rejectingGroup);
}
