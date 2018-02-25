package proxy;

import cinema.OfficeCounter;
import client.OnlineClient;

public class ProxyAccess implements ICounterOfficeAccess {
    private OfficeCounter onlineCounter;
    private OnlineClient client;
    private RealAccess realAccess;

    public ProxyAccess(OfficeCounter onlineCounter, OnlineClient client) {
        this.onlineCounter = onlineCounter;
        this.client = client;
    }

    public void bookTicket() throws IllegalAccessException {
        realAccess = new RealAccess(onlineCounter, client);
        realAccess.bookTicket();
    }
}
