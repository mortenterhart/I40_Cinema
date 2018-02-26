package main;

import logging.Logger;

public class Application {

    private void init() {
        Logger.instance.init();
    }

    private void execute() {
        Logger.instance.log("### Start Application Cinema\n");
        CinemaSimulator simulator = new CinemaSimulator();
        simulator.startSimulation();
        Logger.instance.log("\n### End Application Cinema");
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
