package seat;

import java.util.ArrayList;
import java.util.List;

public class SeatRow {
    private int rowId = 0;
    private List<Seat> seats;

    public SeatRow(int rowId, int numberOfSeats) {
        this.rowId = rowId;
        seats = new ArrayList<>();
        buildSeats(numberOfSeats);
    }

    public void setRowId(int rowId) {
        checkNumberArgument(rowId, 1);
        this.rowId = rowId;
    }

    public int getRowId() {
        return rowId;
    }

    public List<Seat> getSeats() {
        return seats;
    }

    private void buildSeats(int numberOfSeats) {
        for (int seatId = 1; seatId <= numberOfSeats; seatId++) {
            seats.add(new Seat(seatId));
        }
    }

    private void checkNumberArgument(int argument, int minLimit) {
        if (argument < minLimit) {
            throw new IllegalArgumentException("number argument has to be greater than " + (minLimit - 1) +
                    " (actual: " + argument + ")");
        }
    }
}
