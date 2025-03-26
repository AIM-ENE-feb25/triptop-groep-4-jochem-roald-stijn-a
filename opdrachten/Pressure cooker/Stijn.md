# Voorbereiding
De ontwerpvraag is alleen van belang voor de backend, omdat de frontend geen directe interactie heeft met externe services. De belangrijke componenten in de backend zijn:
- Generieke controller
- Generieke service
- CacheRepository
- Cache
- ExternalServiceClient

# Pressure cooker

## Componenten en verantwoordelijkheden
- **Generieke controller**: Verantwoordelijk voor het verwerken van requests van de frontend en het doorgeven aan de juiste service.
- **Generieke service**: Verantwoordelijk voor het aanroepen van externe services en het verwerken van de resultaten.
- **CacheRepository**: Verantwoordelijk voor het opslaan en ophalen van resultaten van externe services om prestaties te verbeteren en afhankelijkheid van externe services te verminderen.
- **ExternalServiceClient**: Verantwoordelijk voor het aanroepen van externe services en het afhandelen van fouten en retries.
- Buiten container: **Cache**: Verantwoordelijk voor het cachen van resultaten van externe services.

## Interfaces
- **Generieke controller**:
    ```
    GET /flights
    Body: {
        origin: string,
        destination: string,
        departureDate: Date,
        returnDate: Date
    }
    ```
- **Generieke service**:
    ```java
    public interface FlightsService {
        public List<Flight> getFlights(String origin, String destination, Date departureDate, Date returnDate);
    }
    ```
- **CacheRepository**:
    ```java
    public interface CacheRepository {
        public void save(Endpoint key, String response, Duration duration);
        public String get(Endpoint key);
    }

    public record Endpoint(Method method, String url, String queryParams, String bodyHash) {
        public Endpoint(Method method, String url, String queryParams) {
            this(method, url, queryParams, "");
        }

        public Endpoint(Method method, String url, String body) {
            this(method, url, "", body);
        }

        public Endpoint(Method method, String url) {
            this(method, url, "", "");
        }
    }

    public enum Method {
        GET, POST, PUT, DELETE
    }
    ```
- **ExternalServiceClient**:
    ```java
    public interface ExternalServiceClient {
        public Optional<String> call(Endpoint endpoint);
    }
    ```

## Volgorde van aanroepen
```puml
@startuml
!include <C4/C4_Component.puml>
title Volgorde van aanroepen

LAYOUT_TOP_DOWN()
SHOW_PERSON_OUTLINE()

Container(frontend, "Frontend", "Javascript en React", "Toont de UI voor het planproces van de reiziger en het ticketsysteem van de reisagent")

Container_Boundary(backend, "Backend") {
    Component(controller, "Generieke controller", "Spring Boot RestController", "Verwerkt requests van de frontend en geeft door aan de juiste service")
    Component(service, "Generieke service", "Spring Boot Service", "Aanroepen van externe services en verwerken van resultaten")
    Component(cacheRepo, "CacheRepository", "?", "Opslaan en ophalen van resultaten van externe services")
    Component(client, "ExternalServiceClient", "?", "Aanroepen van externe services en afhandelen van fouten en retries")
}

System_Ext(api, "Externe API", "API van externe service")

ContainerDb(cache, "Cache", "Redis", "Cachen van resultaten van externe services")

Rel(frontend, controller, "1: verstuurt naar", "JSON/REST")
Rel(controller, service, "2: stuurt request door om af te handelen naar", "?")
Rel(service, cacheRepo, "3: vraagt eerder resultaat op uit", "?")
Rel(cacheRepo, cache, "4: vraagt resultaat op uit", "Jedis")
Rel(cache, cacheRepo, "5: retourneert dat er geen eerder resultaat is aan", "Jedis")
Rel(cacheRepo, service, "6: geeft aan dat er geen eerder resultaat is aan", "?")
Rel(service, client, "7: roept externe service aan via", "?")
Rel(client, api, "8: roept externe service aan", "JSON/REST")
Rel(api, client, "9: retourneert resultaat aan", "JSON/REST")
Rel(client, service, "10: retourneert resultaat aan", "?")
Rel(service, cacheRepo, "11: stuurt resultaat door om op te slaan naar", "?")
Rel(cacheRepo, cache, "12: slaat resultaat op in", "Jedis")
Rel(cache, cacheRepo, "13: retourneert dat het resultaat is opgeslagen aan", "Jedis")
Rel(cacheRepo, service, "14: geeft aan dat het resultaat is opgeslagen aan", "?")
Rel(service, controller, "15: retourneert resultaat aan", "JSON/REST")
Rel_U(controller, frontend, "16: retourneert resultaat aan", "JSON/REST")


SHOW_FLOATING_LEGEND()
@enduml
```

## Classes en functies

> [!WARNING]
>
> TODO: Dit onderdeel is volledig gegenereerd door AI. Zal dit later nog aanpassen.

```puml
title C4 Class Diagram - Backend

interface FlightsService {
    List<Flight> getFlights(String origin, String destination, Date departureDate, Date returnDate)
}

interface CacheRepository {
    void save(Endpoint key, String response, Duration duration)
    String get(Endpoint key)
}

class Endpoint {
    +Method method
    +String url
    +String queryParams
    +String bodyHash
    Endpoint(Method method, String url, String queryParams, String bodyHash)
    Endpoint(Method method, String url, String queryParams)
    Endpoint(Method method, String url, String body)
    Endpoint(Method method, String url)
}

enum Method {
    GET
    POST
    PUT
    DELETE
}

interface ExternalServiceClient {
    Optional<String> call(Endpoint endpoint)
}

class Cache {
    +save(String key, String value, Duration duration)
    +String get(String key)
}

FlightsService --> ExternalServiceClient : uses
FlightsService --> CacheRepository : uses
CacheRepository --> Cache : uses
CacheRepository --> Endpoint : uses
ExternalServiceClient --> Endpoint : uses

@enduml
```