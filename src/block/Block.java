package block;

import main.Configuration;
import seat.Seat;
import seat.SeatLocation;
import seat.SeatRow;
import seat.SeatSection;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Block {
    private Block successor;
    private int numberOfSeatsPerRow = 0;
    private BlockLocation location;
    private List<SeatSection> sections;

    public Block(int numberOfSeatsPerRow) {
        this(null, numberOfSeatsPerRow);
    }

    public Block(Block successor, int numberOfSeatsPerRow) {
        sections = new ArrayList<>(5);
        setSuccessor(successor);
        this.numberOfSeatsPerRow = numberOfSeatsPerRow;

        instantiateSeatSections(numberOfSeatsPerRow);
    }

    private void instantiateSeatSections(int numberOfSeatsPerRow) {
        sections.add(new SeatSection("A", 1, 5, numberOfSeatsPerRow));
        sections.add(new SeatSection("B", 6, 15, numberOfSeatsPerRow));
        sections.add(new SeatSection("C", 16, 25, numberOfSeatsPerRow));
        sections.add(new SeatSection("D", 26, 45, numberOfSeatsPerRow));
        sections.add(new SeatSection("E", 46, 50, numberOfSeatsPerRow));
    }

    /**
     * Checks if the section preferred by a group of customers is free.
     *
     * @return true if the section is still free, false otherwise.
     */
    public boolean hasSectionWithFreeSeats(SeatSection preferredSection, int numberOfSeats) {
        return searchFreeSeatsFor(preferredSection, numberOfSeats) != null;
    }

    /**
     * Searches inside the specified section after a number of consecutive seats that are
     * not booked yet.
     *
     * @param section                  the section to search in
     * @param numberOfConsecutiveSeats the number of consecutive seats
     * @return a list of {@link SeatLocation} elements describing the free seats
     * or null, if there are no more seats available.
     */
    public List<SeatLocation> searchFreeSeatsFor(SeatSection section, int numberOfConsecutiveSeats) {
        Objects.requireNonNull(section);
        checkSeatNumberArgument(numberOfConsecutiveSeats);

        List<SeatLocation> consecutiveFreeSeats = new ArrayList<>(numberOfConsecutiveSeats);
        for (SeatRow sectionRow : section.getSectionRows()) {
            for (Seat rowSeat : sectionRow.getSeats()) {
                if (!rowSeat.isReserved()) {
                    consecutiveFreeSeats.add(new SeatLocation(this, section, sectionRow, rowSeat));
                } else {
                    consecutiveFreeSeats.clear();
                }

                if (consecutiveFreeSeats.size() == numberOfConsecutiveSeats) {
                    return consecutiveFreeSeats;
                }
            }
        }

        return null;
    }

    private void checkSeatNumberArgument(int numberOfSeats) {
        if (numberOfSeats <= 0) {
            throw new IllegalArgumentException("number of seats is smaller or equal to 0");
        }
    }

    /**
     * Chooses the next free section in the specific cinema seat block
     * starting from the last row proceeding to the first rows.
     *
     * @param groupSize the number of consecutive free seats
     * @return the seat section
     */
    public List<SeatLocation> chooseNextFreeSection(int groupSize) {
        // First Chain of Responsibility (Block)
        if (this.isFull(groupSize)) {
            if (successor != null) {
                return this.getSuccessor().chooseNextFreeSection(groupSize);
            }

            return null;
        }

        // Nested Chain of Responsibility (Section)

        // Start searching for next free seats at the back of the cinema
        // because people rather want to sit in the behind than directly
        // in front of the screen
        SeatSection upperSection = sections.get(sections.size() - 1);
        return locateFreeSeatsIn(upperSection, groupSize);
    }

    public List<SeatLocation> chooseRandomSeats(int groupSize) {
        int blockId = this.getBlockId();
        int randomBlockIndex = Configuration.instance.mersenneTwister.nextInt(blockId, 2);

        Block selectedBlock = chooseBlock(randomBlockIndex, this);
        return selectedBlock.chooseNextFreeSection(groupSize);
    }

    private int getBlockId() {
        switch (this.getLocation()) {
            case left:
                return 0;
            case middle:
                return 1;
            case right:
                return 2;
        }

        return -1;
    }

    // Chain of Responsibility (COR)
    private Block chooseBlock(int depth, Block block) {
        if (depth == 0) {
            return block;
        }

        return chooseBlock(depth - 1, block.getSuccessor());
    }

    private List<SeatLocation> locateFreeSeatsIn(SeatSection checkSection, int groupSize) {
        if (this.hasSectionWithFreeSeats(checkSection, groupSize)) {
            return searchFreeSeatsFor(checkSection, groupSize);
        }

        if (this.getSectionSuccessor(checkSection) == null) {
            return null;
        }

        return locateFreeSeatsIn(getSectionSuccessor(checkSection), groupSize);
    }

    private SeatSection getSectionSuccessor(SeatSection precursor) {
        if (sections.indexOf(precursor) == 0) {
            return null;
        }

        // Reverse delegating of sections because people rather
        // want to sit in higher sections (sections are counted
        // from the front of the cinema room to the back)
        return sections.get(sections.indexOf(precursor) - 1);
    }

    /**
     * Determines if a specific number of consecutive seats is still
     * free for customers. The seats have to be within the boundaries
     * of a section. If there is no such combination, the cinema block
     * is assumed to be full.
     *
     * @param groupSize the number of consecutive seats
     * @return true if those seats are still free, false otherwise.
     */
    public boolean isFull(int groupSize) {
        for (SeatSection checkSection : sections) {
            if (hasSectionWithFreeSeats(checkSection, groupSize)) {
                return false;
            }
        }
        return true;
    }

    public boolean isFull() {
        return isFull(1);
    }

    public boolean is95PercentFull() {
        int numberOfReservedSeats = 0;
        for (SeatSection section : sections) {
            for (Seat blockSeat : section.getOrderedSeats()) {
                if (blockSeat.isReserved()) {
                    numberOfReservedSeats++;
                }
            }
        }

        int numberOfBlockRows = 0;
        for (SeatSection section : sections) {
            numberOfBlockRows += section.getSectionRows().size();
        }

        return (numberOfReservedSeats / (numberOfBlockRows * numberOfSeatsPerRow)) >= Configuration.instance.percentageOfFullCinema;
    }

    public void setSuccessor(Block successor) {
        this.successor = successor;
    }

    public Block getSuccessor() {
        return successor;
    }

    public void setLocation(BlockLocation location) {
        this.location = location;
    }

    public BlockLocation getLocation() {
        return location;
    }

    public List<SeatSection> getSections() {
        return sections;
    }
}
