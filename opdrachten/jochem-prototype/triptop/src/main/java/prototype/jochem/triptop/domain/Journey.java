package prototype.jochem.triptop.domain;

public class Journey {
    private String origin;
    private String destination;
    private String departureDate;
    private String returnDate;
    private double price;
    private Transport transport;

    public Journey(String origin, String destination, String departureDate, String returnDate, double price, Transport transport) {
        setOrigin(origin);
        setDestination(destination);
        setDepartureDate(departureDate);
        setReturnDate(returnDate);
        setPrice(price);
        setTransport(transport);
    }


    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getDepartureDate() {
        return departureDate;
    }

    public void setDepartureDate(String departureDate) {
        this.departureDate = departureDate;
    }

    public String getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(String returnDate) {
        this.returnDate = returnDate;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Transport getTransport() {
        return transport;
    }

    public void setTransport(Transport transport) {
        this.transport = transport;
    }
}
