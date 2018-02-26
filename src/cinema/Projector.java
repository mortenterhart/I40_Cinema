package cinema;

import logging.Logger;

public class Projector {

    public void playFilm() {
        Logger.instance.log("Projector: Preheating the projector");
        Logger.instance.log("Projector: Starting movie ... Enjoy!");
    }
}
