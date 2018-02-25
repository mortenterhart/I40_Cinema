package cinema;

import client.OnlineClient;
import client.RealClient;

public interface IClientVisitor {
    double createTicketFor(RealClient client);

    double createTicketFor(OnlineClient client);
}
