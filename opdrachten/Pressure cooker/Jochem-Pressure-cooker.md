# Ontwerpvraag
**Modularity**
- Hoe bied je de gebruiker op basis van zelfgekozen bouwstenen alternatieve bouwstenen aan, bijvoorbeeld als een bepaalde overnachting niet beschikbaar is of om een keuze te geven tussen vervoer per auto, trein of bus?

# Voorbereiding
Aangezien je op de frontend alleen de opties te zien krijgt van de bouwstenen is de functionaliteit van de ontwerpvraag op de backend.
De belangrijkste componenten in de backend zijn:

- Generieke controller
- Generieke service
- Alternative service
- ExternalServiceClient

# Pressure cooker

## 1. Componenten en verantwoordelijkheden

- **Generieke controller**: Verantwoordelijk voor het verwerken van requests van de frontend en het doorgeven aan de juiste service.
- **Generieke service**: Verantwoordelijk voor het verwerken van de resultaten van externe services en het omzetten naar domeinobjecten.
- **Alternative service**: Verantwoordelijk voor het ophalen van alternatieve bouwstenen uit externe services, het genereren van aanvullende opties op basis van gebruikersvoorkeuren en het omzetten van deze alternatieven naar domeinobjecten.
- **ExternalAPIHandler**: Verantwoordelijk voor het aanroepen van externe services, het afhandelen van fouten en retries.

## 2. Interfaces
- **Generieke controller**:
    ```
    GET /trip
    Body: {
        origin: String
        destination: String
        departureDate: Date
        returnDate: Date
        price: double
        transport: Transport
    }
    ```
- **Generieke service**:
    ```java
    public interface TripService {
        List<Trip> getTrips(String origin, String destination, Date departureDate, Date returnDate, double price, Transport transport);
    }
    ```
  
- **Alternative service**:
    ```java
    public interface AlternativeService {
        List<Trip> getAlternativeTrips(String origin, String destination, Date departureDate, Date returnDate, double price, Transport transport);
    }
    ```

- **ExternalAPIHandler**:
    ```java
    public interface ExternalAPIHandler {
        String call(Endpoint endpoint);
    }
    ```

## 3. Volgorde van aanroepen


## 4. Classes en functies
