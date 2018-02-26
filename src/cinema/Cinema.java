package cinema;

import block.Block;
import block.LeftBlock;
import block.MiddleBlock;
import block.RightBlock;
import client.ClientGroup;
import logging.Logger;
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
        Logger.instance.log(" -- Creating an instance for Cinema");
        Logger.instance.log("     > Building the chain of responsibility between the cinema blocks");
        Block rightBlock = new RightBlock();
        Block middleBlock = new MiddleBlock(rightBlock);
        leftBlock = new LeftBlock(middleBlock);

        Logger.instance.log("     > Building the ticket office with two counters (normal, online)");
        ticketOffice = new BoxOffice(leftBlock, 42);

        Logger.instance.log("     > Setting up screen and projector in cinema room");
        screen = new Screen();
        projector = new Projector();

        Logger.instance.log("     > Initializing the cinema observers notifying about actions connected to the terminating condition");
        offerRejectingObserver = new OfferRejectionObserver(this);
        seatAdmissionObserver = new SeatAdmissionObserver(this);

        ticketOffice.registerSeatAdmissionObserver(seatAdmissionObserver);
    }

    public void reportClientRejection(ClientGroup rejectingGroup) {
        offerRejectingObserver.notifyOfferRejection(rejectingGroup);
    }

    public void reportCinemaFull() {
        seatAdmissionObserver.notifyCinemaIsFull();
    }

    public void reportCinema95PercentFull() {
        if (offerRejectingObserver.wasTriggered()) {
            Logger.instance.log("      >> yes, closing box office");
            seatAdmissionObserver.notifyCinemaIs95PercentFull();
            return;
        }

        Logger.instance.log("      >> no");
    }

    public void closeTicketOffice() {
        ticketOffice.close();
    }

    public boolean isFull() {
        return isFull(leftBlock, 1);
    }

    // Chain of Responsibility
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
        Logger.instance.log("Cinema: Slowly dimming lights in cinema");
    }

    public ICinemaObserver getOfferRejectingObserver() {
        return offerRejectingObserver;
    }

    public ICinemaObserver getSeatAdmissionObserver() {
        return seatAdmissionObserver;
    }

    public BoxOffice getTicketOffice() {
        return ticketOffice;
    }

    public Block getLeftBlock() {
        return leftBlock;
    }
}
