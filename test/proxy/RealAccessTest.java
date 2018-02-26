package proxy;

import block.Block;
import cinema.Cinema;
import client.Client;
import client.RealClient;
import logging.Logger;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import seat.Seat;
import seat.SeatLocation;
import seat.SeatRow;
import seat.SeatSection;

public class RealAccessTest {
    private static Cinema cinema;

    @BeforeAll
    public static void initCinema() {
        Logger.instance.init();
        cinema = new Cinema();
    }

    @Test
    public void testReceivedTicketNotNull() {
        Client realClient = new RealClient("Monty Python", 18);
        Block leftBlock = cinema.getLeftBlock();
        SeatSection section = leftBlock.getSections().get(0);
        SeatRow row = section.getSectionRows().get(0);
        Seat seat = row.getSeats().get(0);
        realClient.offerSeat(new SeatLocation(leftBlock, section, row, seat));

        ICounterOfficeAccess realAccess = new RealAccess(cinema.getTicketOffice().getNormalCounter(), realClient);
        try {
            realAccess.bookTicket();
        } catch (IllegalAccessException exception) {
            exception.printStackTrace();
        }

        Assertions.assertNotNull(realClient.getTicket());

        Logger.instance.close();
    }
}
