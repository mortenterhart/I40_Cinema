package proxy;

import cinema.OfficeCounter;
import client.Client;
import client.OnlineClient;
import client.RealClient;
import logging.Logger;
import ticket.OnlineTicketType;

public class RealAccess implements ICounterOfficeAccess {
    private OfficeCounter counter;
    private Client client;
    private OnlineTicketType type;

    public RealAccess(OfficeCounter counter, Client client) {
        this.counter = counter;
        this.client = client;
    }

    public void bookTicket() throws IllegalAccessException {
        if (client instanceof RealClient) {
            ((RealClient) client).takeTicket(counter);
            Logger.instance.log("    > Booking ticket for RealClient over RealAccess interface");
        } else if (client instanceof OnlineClient) {
            ((OnlineClient) client).takeTicket(counter, type);
            Logger.instance.log("    > Booking ticket for OnlineClient over ProxyAccess interface");
        } else {
            throw new IllegalAccessException("invalid access strategy: " + client);
        }
    }

    public void setOnlineTicketType(OnlineTicketType type) {
        this.type = type;
    }
}
