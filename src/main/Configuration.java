package main;

import random.MersenneTwisterFast;

public enum Configuration {
    instance;

    public final int seatNumberOuterBlocks = 25;
    public final int seatNumberInnerBlock = 40;
    public final int maximumGroupSize = 4;
    public final double offerAcceptingProbability = 0.7;
    public final long iterationInterval = 200;
    public final int movieAgeConfinement = 16;

    public final MersenneTwisterFast mersenneTwister = new MersenneTwisterFast();

    public String userDirectory = System.getProperty("user.dir");
    public String fileSeparator = System.getProperty("file.separator");
    public String lineSeparator = System.lineSeparator();
    public String moduleName = "I40_Cinema";

    public String logFilePath = buildLogFilePath();

    /**
     * Used to construct the correct log file path. When running the JUnit Tests
     * the user directory already contains the part "I40_Cinema/" because they are
     * executed in a different directory and manner. To prevent the log file path
     * from containing "I40_Cinema" twice and causing {@link java.io.FileNotFoundException},
     * check the user directory for occurrence first before appending the rest.
     *
     * @return the correct log file path for the specific user directory
     */
    private String buildLogFilePath() {
        if (userDirectory.endsWith(moduleName)) {
            return userDirectory + fileSeparator + "log" + fileSeparator + moduleName + ".log";
        }

        return userDirectory + fileSeparator + moduleName + fileSeparator + "log" +
                fileSeparator + moduleName + ".log";
    }
}
