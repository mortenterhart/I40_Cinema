package client;

import cinema.IClientVisitor;

public class OnlineClient extends Client {

    public OnlineClient(String name) {
        super(name);
    }

    public OnlineClient(String name, int age) {
        super(name, age);
        super.setOnline();
    }

    public double takeTicket(IClientVisitor boxOffice) {
        return boxOffice.createTicketFor(this);
    }

}
