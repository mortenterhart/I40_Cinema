package cinema;

import seat.SeatLocation;

public class CinemaTicket {
    private int ticketId = 0;
    private SeatLocation seatLocation;
    private double price = 10.0;
    private String movieName;
    private int cinemaId = 42;

    private CinemaTicket(Builder builder) {
        ticketId = Builder.ticketIdCounter;
        seatLocation = builder.seatLocation;
        price = builder.price;
        movieName = builder.movieName;
        cinemaId = builder.cinemaId;
    }

    public static class Builder implements IBuilder<CinemaTicket> {
        private static int ticketIdCounter = 0;

        private SeatLocation seatLocation;
        private double price;
        private String movieName;
        private int cinemaId;

        public static void resetIdCounter() {
            ticketIdCounter = 0;
        }

        public Builder setSeatLocation(SeatLocation seatLocation) {
            this.seatLocation = seatLocation;
            return this;
        }

        public Builder setPrice(double price) {
            this.price = price;
            return this;
        }

        public Builder setMovieName(String movieName) {
            this.movieName = movieName;
            return this;
        }

        public Builder setCinemaId(int cinemaId) {
            this.cinemaId = cinemaId;
            return this;
        }

        public CinemaTicket build() {
            CinemaTicket ticket = new CinemaTicket(this);
            ticketIdCounter++;
            // seatLocation.reserve();
            return ticket;
        }
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("CinemaTicket { ");
        builder.append("ticketId = ").append(ticketId).append(", ");
        builder.append("seatLocation = \"").append(seatLocation.getLocator()).append("\", ");
        builder.append("price = ").append(price).append(", ");
        builder.append("movieName = \"").append(movieName).append("\", ");
        builder.append("cinemaId = ").append(cinemaId).append(" }");
        builder.trimToSize();
        return builder.toString();
    }
}
