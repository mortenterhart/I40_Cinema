package cinema;

import block.Block;
import client.Client;
import client.ClientGroup;
import seat.SeatLocation;

import java.util.List;
import java.util.ListIterator;

public class BoxOfficeTerminal {
    private Block leftBlock;

    public BoxOfficeTerminal(final Block leftBlock) {
        this.leftBlock = leftBlock;
    }

    public boolean isPreferredSectionFree(ClientGroup group) {
        return leftBlock.hasSectionWithFreeSeats(group.getPreference().getSection(), group.getSize());
    }

    public List<SeatLocation> chooseNextFreeSection(int groupSize) {
        return leftBlock.chooseNextFreeSection(groupSize);
    }

    public void markOfferedSeats(ClientGroup group) {
        ListIterator<Client> clientIterator = group.getMembers().listIterator();
        for (SeatLocation offeredSeat : leftBlock.searchFreeSeatsFor(group.getPreference().getSection(), group.getSize())) {
            if (clientIterator.hasNext()) {
                clientIterator.next().offerSeat(offeredSeat);
            }
        }
    }

    public void setLeftBlock(Block leftBlock) {
        this.leftBlock = leftBlock;
    }

    public Block getLeftBlock() {
        return leftBlock;
    }
}
