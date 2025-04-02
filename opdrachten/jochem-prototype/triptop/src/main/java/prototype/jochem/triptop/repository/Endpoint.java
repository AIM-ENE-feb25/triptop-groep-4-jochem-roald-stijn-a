package prototype.jochem.triptop.repository;

public class Endpoint {
    private String URL;
    private String method;

    public Endpoint(String URL, String method) {
        setURL(URL);
        setMethod(method);
    }

    public String getURL() {
        return URL;
    }

    public void setURL(String URL) {
        this.URL = URL;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }
}
