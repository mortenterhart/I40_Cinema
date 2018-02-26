package observer;

import cinema.Cinema;
import client.ClientGroup;
import logging.Logger;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class OfferRejectionObserverTest {
    private static Cinema cinema;
    private static OfferRejectionObserver rejectionObserver;

    @BeforeAll
    public static void initCinema() {
        Logger.instance.init();
        cinema = new Cinema();
        ClientGroup.setLeftBlock(cinema.getLeftBlock());
        rejectionObserver = (OfferRejectionObserver) cinema.getOfferRejectingObserver();
    }

    @Test
    public void testObserverTriggersAfter3Rejections() {
        rejectionObserver.resetNumberOfRejectedOffers();
        triggerObserver(3);

        Assertions.assertTrue(rejectionObserver.wasTriggered());
    }

    @Test
    public void testObserverNotTriggersAfter2Rejections() {
        rejectionObserver.resetNumberOfRejectedOffers();
        triggerObserver(2);

        Assertions.assertFalse(rejectionObserver.wasTriggered());
    }

    @Test
    public void testObserverClosesBoxOfficeAfter3Rejections() {
        cinema.getTicketOffice().open();
        rejectionObserver.resetNumberOfRejectedOffers();
        triggerObserver(3);
        cinema.reportCinema95PercentFull();

        Assertions.assertTrue(cinema.getTicketOffice().isClosed());
    }

    private void triggerObserver(int number) {
        for (int i = 0; i < number; i++) {
            cinema.reportClientRejection(new ClientGroup());
        }
    }
}
