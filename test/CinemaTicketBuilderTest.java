import block.Block;
import cinema.CinemaTicket;
import org.junit.Before;
import org.junit.Test;
import seat.Seat;
import seat.SeatLocation;
import seat.SeatRow;
import seat.SeatSection;

import static org.junit.Assert.*;


public class CinemaTicketBuilderTest {
    CinemaTicket ticket;
    CinemaTicket.Builder builder;
    SeatLocation seatLocation;
    Block cinemaBlock;
    SeatSection section;
    SeatRow row;
    Seat seat;

    @Before
    public void buildBuilder() {


        this.builder = new CinemaTicket.Builder();
        this.cinemaBlock = new Block(20);
        this.section = new SeatSection("a", 1, 21, 20);
        this.row = new SeatRow(1, 20);
        this.seat = new Seat();
        this.seatLocation = new SeatLocation(cinemaBlock, section, row, seat);

        //this.ticket = new CinemaTicket(this.builder); TODO Change!

    }

    @Test
    public void testSetSeatLocatioin() {
        assertNotNull(builder.setSeatLocation(seatLocation));

    }

    @Test
    public void testSetPrice() {
       assertNotNull( builder.setPrice(22));
    }

    @Test
    public void testSetMovieName(){
        assertNotNull(builder.setMovieName("The Hitchhiker's Guide to the Galaxy"));
    }

    @Test
    public void testSetCinemaId(){
        assertNotNull(builder.setCinemaId(42));
    }

    @Test
    public void testBuild(){
        assertNotNull(builder.build());
    }

}
