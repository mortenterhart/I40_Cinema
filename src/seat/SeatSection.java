package seat;

import java.util.ArrayList;
import java.util.List;

public class SeatSection {
    private List<SeatRow> sectionRows;
    private String sectionIdentifier;

    public SeatSection(String identifier, int lowerRowId, int upperRowId, int numberOfSeatsPerRow) {
        sectionRows = new ArrayList<>();
        sectionIdentifier = identifier;
        buildRows(lowerRowId, upperRowId, numberOfSeatsPerRow);
    }

    public List<Seat> getOrderedSeats() {
        List<Seat> allSeats = new ArrayList<>();

        for (SeatRow row : sectionRows) {
            allSeats.addAll(row.getSeats());
        }
        return allSeats;
    }

    public List<SeatRow> getSectionRows() {
        return sectionRows;
    }

    public String getIdentifier() {
        return sectionIdentifier;
    }

    private void buildRows(int lowerRowId, int upperRowId, int numberOfSeatsPerRow) {
        for (int rowId = lowerRowId; rowId <= upperRowId; rowId++) {
            sectionRows.add(new SeatRow(rowId, numberOfSeatsPerRow));
        }
    }
}
