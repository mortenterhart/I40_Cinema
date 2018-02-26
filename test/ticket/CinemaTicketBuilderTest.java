package ticket;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import seat.Seat;
import seat.SeatLocation;

public class CinemaTicketBuilderTest {
    private static CinemaTicket.Builder ticketBuilder;
    private int cinemaId = 42;
    private String movieName = "The Hitchhiker's Guide To The Galaxy";

    @BeforeAll
    public static void initBuilder() {
        CinemaTicket.Builder.resetIdCounter();
        ticketBuilder = new CinemaTicket.Builder();

    }

    @Test
    public void testSetSeatLocationBuilderNotNull() {
        Assertions.assertNotNull(ticketBuilder.setSeatLocation(null));
    }

    @Test
    public void testSetPriceBuilderNotNull() {
        Assertions.assertNotNull(ticketBuilder.setPrice(10.0));
    }

    @Test
    public void testSetMovieNameBuilderNotNull() {
        Assertions.assertNotNull(ticketBuilder.setMovieName(movieName));
    }

    @Test
    public void testSetCinemaIdBuilderNotNull() {
        Assertions.assertNotNull(ticketBuilder.setCinemaId(cinemaId));
    }

    @Test
    public void testBuildCinemaTicketNotNull() {
        CinemaTicket ticket = ticketBuilder.setCinemaId(cinemaId)
                .setMovieName(movieName)
                .setPrice(10.0)
                .setSeatLocation(new SeatLocation(null, null, null, new Seat()))
                .build();

        Assertions.assertNotNull(ticket);
    }

    @Test
    public void testResetTicketIdCounterEqualsZero() {
        CinemaTicket.Builder.resetIdCounter();

        Assertions.assertEquals(0, CinemaTicket.Builder.getTicketIdCounter());
    }
}
