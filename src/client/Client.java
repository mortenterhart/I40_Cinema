package client;

import cinema.CinemaTicket;
import seat.SeatLocation;

public class Client {
    private CinemaTicket ticket;
    // Temporary field used by the box office to deposit the seat proposal
    private SeatLocation offeredSeat;
    private String name;
    private int age = 16;
    private boolean online = false;

    public Client(String name) {
        this(name, 16);
    }

    public Client(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public CinemaTicket getTicket() {
        return ticket;
    }

    public void receiveTicket(CinemaTicket ticket) {
        this.ticket = ticket;
    }

    public SeatLocation getOfferedSeat() {
        return offeredSeat;
    }

    public void offerSeat(SeatLocation offeredSeat) {
        this.offeredSeat = offeredSeat;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public boolean isOnlineClient() {
        return online;
    }

    public void setOnline() {
        online = true;
    }
}
