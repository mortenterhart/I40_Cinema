package seat;

public class Seat {
    private int seatId = 0;
    private boolean isTaken = false;

    public Seat(int seatId) {
        this.seatId = seatId;
    }

    public void reserve() {
        isTaken = true;
    }

    public void free() {
        isTaken = false;
    }

    public int getSeatId() {
        return seatId;
    }

    public boolean isTaken() {
        return isTaken;
    }
}
