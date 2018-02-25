package proxy;

import cinema.OfficeCounter;
import client.Client;
import client.OnlineClient;
import client.RealClient;

public class RealAccess implements ICounterOfficeAccess {
    private OfficeCounter counter;
    private Client client;

    public RealAccess(OfficeCounter counter, Client client) {
        this.counter = counter;
        this.client = client;
    }

    public void bookTicket() throws IllegalAccessException {
        if (client instanceof RealClient) {
            ((RealClient) client).takeTicket(counter);
        } else if (client instanceof OnlineClient) {
            ((OnlineClient) client).takeTicket(counter);
        }

        throw new IllegalAccessException("invalid access strategy: " + client);
    }
}
