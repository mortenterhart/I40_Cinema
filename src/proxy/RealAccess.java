package proxy;

import client.Client;
import ticket.CinemaTicket;

public class RealAccess implements ICounterOfficeAccess {
    private Client client;

    public RealAccess(Client client) {
        this.client = client;
    }

    public CinemaTicket bookTicket() {
        return null;
    }
}
