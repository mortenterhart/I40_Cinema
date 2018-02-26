package ticket;

public class HotDealTicket extends OnlineTicket {

    public HotDealTicket() {
        super.setPrice(8.0);
        super.setType(OnlineTicketType.hotDeal);
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
