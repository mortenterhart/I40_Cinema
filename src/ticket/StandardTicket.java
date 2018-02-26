package ticket;

public class StandardTicket extends OnlineTicket {

    public StandardTicket() {
        super.setPrice(9.0);
        super.setType(OnlineTicketType.standard);
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
