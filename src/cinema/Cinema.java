package cinema;

import block.Block;
import block.LeftBlock;
import block.MiddleBlock;
import block.RightBlock;

import java.util.ArrayList;
import java.util.List;

public class Cinema {
    private Block leftBlock;
    private List<ISeatDetector> observers;
    private Screen screen;
    private Projector projector;

    public Cinema() {
        Block rightBlock = new RightBlock();
        Block middleBlock = new MiddleBlock(rightBlock);
        leftBlock = new LeftBlock(middleBlock);

        observers = new ArrayList<>();

        screen = new Screen();
        projector = new Projector();
    }

    /*public boolean isFull() {
        // multiple CORs
        // return leftBlock.isBlockFull();
        return false;
    }

    public boolean is95PercentFull() {
        return false;
    }*/

    public void darkenRoom() {
        System.out.println("Turning lights off");
    }
}
