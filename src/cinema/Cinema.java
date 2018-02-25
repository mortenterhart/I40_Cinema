package cinema;

import block.Block;
import block.LeftBlock;
import block.MiddleBlock;
import block.RightBlock;
import client.ClientGroup;
import observer.ICinemaObserver;
import observer.OfferRejectionObserver;
import observer.SeatAdmissionObserver;

public class Cinema {
    private Block leftBlock;
    private BoxOffice ticketOffice;
    private Screen screen;
    private Projector projector;

    private final ICinemaObserver offerRejectingObserver;
    private final ICinemaObserver seatAdmissionObserver;

    public Cinema() {
        Block rightBlock = new RightBlock();
        Block middleBlock = new MiddleBlock(rightBlock);
        leftBlock = new LeftBlock(middleBlock);

        ticketOffice = new BoxOffice(leftBlock, 42);

        screen = new Screen();
        projector = new Projector();

        offerRejectingObserver = new OfferRejectionObserver(this);
        seatAdmissionObserver = new SeatAdmissionObserver(this);
    }

    public void reportClientRejection(ClientGroup rejectingGroup) {
        offerRejectingObserver.notifyOfferRejection(rejectingGroup);
    }

    public void reportCinemaFull() {
        seatAdmissionObserver.notifyCinemaIsFull();
    }

    public void reportCinema95PercentFull() {
        seatAdmissionObserver.notifyCinemaIs95PercentFull();
    }

    public void closeTicketOffice() {
        ticketOffice.close();
    }

    public boolean isFull() {
        return isFull(leftBlock, 1);
    }

    // COR
    private boolean isFull(Block responsibleBlock, int groupSize) {
        if (responsibleBlock == null) {
            return true;
        }

        if (responsibleBlock.isFull(groupSize)) {
            return isFull(responsibleBlock.getSuccessor(), groupSize);
        }

        return false;
    }

    public boolean is95PercentFull() {
        return is95PercentFull(leftBlock);
    }

    // COR
    private boolean is95PercentFull(Block responsibleBlock) {
        if (responsibleBlock == null) {
            return true;
        }

        if (responsibleBlock.is95PercentFull()) {
            return is95PercentFull(responsibleBlock.getSuccessor());
        }

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
