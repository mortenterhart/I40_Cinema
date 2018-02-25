package proxy;

import client.OnlineClient;
import ticket.CinemaTicket;

public class ProxyAccess implements ICounterOfficeAccess {
    private OnlineClient client;
    private RealAccess realAccess;

    public ProxyAccess(OnlineClient client) {
        this.client = client;
    }

    public CinemaTicket bookTicket() {
        realAccess = new RealAccess(client);
        return realAccess.bookTicket();
    }
}
