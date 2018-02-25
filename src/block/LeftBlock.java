package block;

import main.Configuration;
import seat.SeatLocation;
import seat.SeatSection;

import java.util.List;

public class LeftBlock extends Block {

    public LeftBlock(Block successor) {
        super(successor, Configuration.instance.seatNumberOuterBlocks);
        super.setLocation(BlockLocation.left);
    }

    @Override
    public boolean hasSectionWithFreeSeats(SeatSection preferredSection, int groupSize) {
        return super.hasSectionWithFreeSeats(preferredSection, groupSize);
    }

    @Override
    public List<SeatLocation> chooseNextFreeSection(int groupSize) {
        return super.chooseNextFreeSection(groupSize);
    }

    @Override
    public boolean isFull(int groupSize) {
        return super.isFull(groupSize);
    }
}
