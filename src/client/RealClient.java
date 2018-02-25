package client;

import cinema.IClientVisitor;
import main.Configuration;

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

    public boolean acceptsOffer() {
        return Configuration.instance.mersenneTwister.nextBoolean(Configuration.instance.offerAcceptingProbability);
    }

    public void leaveCinema() {
        System.out.println("Leaving cinema ...");
    }
}
