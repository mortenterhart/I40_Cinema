package main;

import logging.Logger;

public class Application {

    private void init() {
        Logger.instance.init();
    }

    private void execute() {
        CinemaSimulator simulator = new CinemaSimulator();
        simulator.startSimulation();
    }

    private void prepareShutdown() {
        Logger.instance.close();
    }

    public static void main(String[] args) {
        Application application = new Application();
        application.init();
        application.execute();
        application.prepareShutdown();
    }
}
