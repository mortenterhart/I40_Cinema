package client;

import block.Block;
import seat.SeatSection;

public class ClientSeatPreference {
    private Block block;
    private SeatSection section;

    public ClientSeatPreference(Block block, SeatSection section) {
        this.block = block;
        this.section = section;
    }

    public Block getBlock() {
        return block;
    }

    public SeatSection getSection() {
        return section;
    }
}
