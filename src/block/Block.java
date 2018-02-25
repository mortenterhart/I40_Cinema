package block;

import seat.SeatSection;
import client.ClientGroup;

import java.util.ArrayList;
import java.util.List;

public class Block {
    private Block successor;
    private int numberOfSeatsPerRow = 0;
    private List<SeatSection> rows;

    public Block(int numberOfSeatsPerRow) {
        this(null, numberOfSeatsPerRow);
    }

    public Block(Block successor, int numberOfSeatsPerRow) {
        rows = new ArrayList<>(5);
        setSuccessor(successor);
        this.numberOfSeatsPerRow = numberOfSeatsPerRow;
    }

    /**
     * Checks if the section preferred by a group of customers is free.
     *
     * @param group the group
     * @return true if the section is still free, false otherwise.
     */
    public boolean isPreferredSectionFree(ClientGroup group) {
        return false;
    }

    /**
     * Chooses the next free section in the specific cinema seat block
     * starting from the last row proceeding to the first rows.
     *
     * @param groupSize the number of consecutive free seats
     * @return the seat section
     */
    protected SeatSection chooseNextSection(int groupSize) {
        return null;
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
    protected boolean isBlockFull(int groupSize) {
        return false;
    }

    public void setSuccessor(Block successor) {
        this.successor = successor;
    }

    public Block getSuccessor() {
        return successor;
    }
}
