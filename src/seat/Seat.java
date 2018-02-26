package seat;

public class Seat {
    private static int seatIdCounter = 1;

    private int seatId = 1;
    private boolean isTaken = false;

    public Seat() {
        seatId = seatIdCounter;
        seatIdCounter++;
    }

    public void reserve() {
        isTaken = true;
    }

    public void free() {
        isTaken = false;
    }

    public static void resetIdCounter() {
        seatIdCounter = 1;
    }

    public int getSeatId() {
        return seatId;
    }

    public boolean isReserved() {
        return isTaken;
    }
}
