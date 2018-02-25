package client;

import cinema.IClientVisitor;

public class RealClient extends Client {

    public RealClient(String name) {
        super(name);
    }

    public RealClient(String name, int age) {
        super(name, age);
    }

    public double takeTicket(IClientVisitor boxOffice) {
        return boxOffice.createTicketFor(this);
    }
}
