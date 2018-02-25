package block;

import main.Configuration;

public class MiddleBlock extends Block {

    public MiddleBlock(Block successor) {
        super(successor, Configuration.instance.seatNumberInnerBlock);
    }
}
