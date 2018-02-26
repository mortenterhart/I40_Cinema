package proxy;

import cinema.OfficeCounter;
import client.OnlineClient;
import ticket.OnlineTicketType;

public class ProxyAccess implements ICounterOfficeAccess {
    private OfficeCounter onlineCounter;
    private OnlineClient client;
    private OnlineTicketType type;
    private RealAccess realAccess;

    public ProxyAccess(OfficeCounter onlineCounter, OnlineClient client, OnlineTicketType type) {
        this.onlineCounter = onlineCounter;
        this.client = client;
        this.type = type;
    }

    public void bookTicket() throws IllegalAccessException {
        realAccess = new RealAccess(onlineCounter, client);
        realAccess.setOnlineTicketType(type);
        realAccess.bookTicket();
    }
}
