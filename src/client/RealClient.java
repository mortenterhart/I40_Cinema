package client;

import cinema.IClientVisitor;
import logging.Logger;

public class RealClient extends Client {

    public RealClient(String name) {
        super(name);
    }

    public RealClient(String name, int age) {
        super(name, age);
    }

    public void takeTicket(IClientVisitor boxOffice) {
        Logger.instance.log("   > Invoking visitor for RealClient");
        boxOffice.createTicketFor(this);
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
