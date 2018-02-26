package cinema;

import client.OnlineClient;
import client.RealClient;
import ticket.OnlineTicketType;

public interface IClientVisitor {
    void createTicketFor(RealClient client);

    void createTicketFor(OnlineClient client, OnlineTicketType type);
}
