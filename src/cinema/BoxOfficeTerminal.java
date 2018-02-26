package cinema;

import block.Block;
import client.Client;
import client.ClientGroup;
import logging.Logger;
import seat.SeatLocation;

import java.util.List;
import java.util.ListIterator;

public class BoxOfficeTerminal {
    private Block leftBlock;

    public BoxOfficeTerminal(final Block leftBlock) {
        this.leftBlock = leftBlock;
    }

    public boolean isPreferredSectionFree(ClientGroup group) {
        return group.getPreference().getBlock().hasSectionWithFreeSeats(group.getPreference().getSection(), group.getSize());
    }

    public List<SeatLocation> chooseNextFreeSection(int groupSize) {
        return leftBlock.chooseNextFreeSection(groupSize);
    }

    public List<SeatLocation> fetchRandomSeats(int groupSize) {
        return leftBlock.chooseRandomSeats(groupSize);
    }

    public void markOfferedSeats(ClientGroup group) {
        Logger.instance.log("BoxOfficeTerminal: Offering seats to client in group " + group);
        ListIterator<Client> clientIterator = group.getMembers().listIterator();
        for (SeatLocation offeredSeat : group.getPreference().getBlock().searchFreeSeatsFor(group.getPreference().getSection(), group.getSize())) {
            if (clientIterator.hasNext()) {
                Logger.instance.log("BoxOfficeTerminal: Setting seat " + offeredSeat.getLocator() + " to client");
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
