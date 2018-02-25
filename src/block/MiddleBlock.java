package block;

import main.Configuration;
import seat.SeatLocation;
import seat.SeatSection;

import java.util.List;

public class MiddleBlock extends Block {

    public MiddleBlock(Block successor) {
        super(successor, Configuration.instance.seatNumberInnerBlock);
        super.setLocation(BlockLocation.middle);
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
