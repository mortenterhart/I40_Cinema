package observer;

import cinema.Cinema;
import client.ClientGroup;
import logging.Logger;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class SeatAdmissionObserverTest {
    private static Cinema cinema;
    private static SeatAdmissionObserver seatAdmissionObserver;

    @BeforeAll
    public static void initCinema() {
        Logger.instance.init();
        cinema = new Cinema();
        ClientGroup.setLeftBlock(cinema.getLeftBlock());
        seatAdmissionObserver = (SeatAdmissionObserver) cinema.getSeatAdmissionObserver();

        triggerOfferRejectionObserver();
    }

    private static void triggerOfferRejectionObserver() {
        for (int i = 0; i < 3; i++) {
            cinema.reportClientRejection(new ClientGroup());
        }
    }

    @Test
    public void testObserverWasTriggeredAfterCinemaFull() {
        seatAdmissionObserver.resetTriggered();
        cinema.reportCinemaFull();

        Assertions.assertTrue(seatAdmissionObserver.wasTriggered());
    }

    @Test
    public void testObserverWasTriggeredAfterCinema95PercentFull() {
        seatAdmissionObserver.resetTriggered();
        cinema.reportCinema95PercentFull();

        Assertions.assertTrue(seatAdmissionObserver.wasTriggered());
    }

    @Test
    public void testObserverClosesBoxOfficeAfterCinemaFull() {
        cinema.getTicketOffice().open();
        seatAdmissionObserver.resetTriggered();
        cinema.reportCinemaFull();

        Assertions.assertTrue(cinema.getTicketOffice().isClosed());
    }

    @Test
    public void testObserverClosesBoxOfficeAfterCinema95PercentFull() {
        cinema.getTicketOffice().open();
        seatAdmissionObserver.resetTriggered();
        cinema.reportCinema95PercentFull();

        Assertions.assertTrue(cinema.getTicketOffice().isClosed());
    }
}
