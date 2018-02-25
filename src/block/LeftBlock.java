package block;

import main.Configuration;

public class LeftBlock extends Block {

    public LeftBlock(Block successor) {
        super(successor, Configuration.instance.seatNumberOuterBlocks);
    }
}
