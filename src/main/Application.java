package main;

import cinema.Cinema;

public class Application {

    private void init() {
        // Logger.instance.init();
    }

    private void execute() {
        Cinema cinema = new Cinema();
    }

    private void prepareShutdown() {
        // Logger.instance.close();
    }

    public static void main(String[] args) {
        Application application = new Application();
        application.init();
    }
}
