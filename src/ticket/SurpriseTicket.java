package ticket;

public class SurpriseTicket extends OnlineTicket {

    public SurpriseTicket() {
        super.setPrice(5.0);
        super.setType(OnlineTicketType.surprise);
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
