package client;

import cinema.IClientVisitor;
import logging.Logger;
import ticket.OnlineTicketType;

public class OnlineClient extends Client {

    public OnlineClient(String name) {
        super(name);
    }

    public OnlineClient(String name, int age) {
        super(name, age);
        super.setOnline();
    }

    public void takeTicket(IClientVisitor boxOffice, OnlineTicketType type) {
        Logger.instance.log("    > Invoking visitor for this OnlineClient with ticket type " + type.name());
        boxOffice.createTicketFor(this, type);
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
