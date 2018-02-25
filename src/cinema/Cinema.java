package cinema;

import block.Block;
import block.LeftBlock;
import block.MiddleBlock;
import block.RightBlock;
import client.Client;
import observer.ICinemaObserver;
import observer.OfferRejectionObserver;

public class Cinema {
    private Block leftBlock;
    private BoxOffice ticketOffice;
    private Screen screen;
    private Projector projector;

    private final ICinemaObserver offerRejectingObserver;

    public Cinema() {
        Block rightBlock = new RightBlock();
        Block middleBlock = new MiddleBlock(rightBlock);
        leftBlock = new LeftBlock(middleBlock);

        ticketOffice = new BoxOffice(leftBlock, 42);

        screen = new Screen();
        projector = new Projector();

        offerRejectingObserver = new OfferRejectionObserver(this);
    }

    public void reportClientRejection(Client rejectingClient) {
        offerRejectingObserver.notifyOfferRejection(rejectingClient);
    }

    public boolean isFull() {
        return isFull(leftBlock);
    }

    // COR
    private boolean isFull(Block responsibleBlock) {
        if (responsibleBlock == null) {
            return true;
        }

        if (responsibleBlock.isFull()) {
            return isFull(responsibleBlock.getSuccessor());
        }

        return false;
    }

    public boolean is95PercentFull() {
        return false;
    }

    public void startProjector() {
        projector.playFilm();
    }

    public void rollOutScreen() {
        screen.displayMovie();
    }

    public void darkenRoom() {
        System.out.println("Turning lights off");
    }

    public BoxOffice getTicketOffice() {
        return ticketOffice;
    }

    public Block getLeftBlock() {
        return leftBlock;
    }
}
