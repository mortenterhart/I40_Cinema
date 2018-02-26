package proxy;

import block.Block;
import cinema.Cinema;
import client.OnlineClient;
import logging.Logger;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import seat.Seat;
import seat.SeatLocation;
import seat.SeatRow;
import seat.SeatSection;
import ticket.OnlineTicketType;

public class ProxyAccessTest {
    private static Cinema cinema;

    @BeforeAll
    public static void initCinema() {
        Logger.instance.init();
        cinema = new Cinema();
    }

    @Test
    public void testReceivedTicketNotNull() {
        OnlineClient onlineClient = new OnlineClient("Online Customer");
        Block leftBlock = cinema.getLeftBlock();
        SeatSection section = leftBlock.getSections().get(0);
        SeatRow row = section.getSectionRows().get(0);
        Seat seat = row.getSeats().get(0);
        onlineClient.offerSeat(new SeatLocation(leftBlock, section, row, seat));

        ICounterOfficeAccess proxyAccess = new ProxyAccess(cinema.getTicketOffice().getNormalCounter(), onlineClient, OnlineTicketType.hotDeal);
        try {
            proxyAccess.bookTicket();
        } catch (IllegalAccessException exception) {
            exception.printStackTrace();
        }

        Assertions.assertNotNull(onlineClient.getTicket());

        Logger.instance.close();
    }
}
