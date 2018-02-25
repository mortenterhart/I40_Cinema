package seat;

import block.Block;

public class SeatLocation {
    private Block cinemaBlock;
    private SeatSection section;
    private SeatRow row;
    private Seat seat;

    public SeatLocation(Block cinemaBlock, SeatSection section, SeatRow row, Seat seat) {
        this.cinemaBlock = cinemaBlock;
        this.section = section;
        this.row = row;
        this.seat = seat;
    }

    public void reserve() {
        seat.reserve();
    }

    public Block getBlock() {
        return cinemaBlock;
    }

    public SeatSection getSection() {
        return section;
    }

    public SeatRow getRow() {
        return row;
    }

    public Seat getSeat() {
        return seat;
    }

    public String getLocator() {
        StringBuilder builder = new StringBuilder();
        builder.append("SeatLocation: block ").append(cinemaBlock.getLocation().name()).append(", ");
        builder.append("section ").append(section.getIdentifier()).append(", ");
        builder.append("row ").append(row.getRowId()).append(", ");
        builder.append("seat ").append(seat.getSeatId());
        builder.trimToSize();
        return builder.toString();
    }

    @Override
    public String toString() {
        return getLocator();
    }
}
