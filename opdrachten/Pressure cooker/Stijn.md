# Ontwerpvraag
**Fault Tolerance**
- Hoe ga je om met aanroepen van externe services die niet beschikbaar zijn en toch verwacht wordt dat er waardevolle output gegeven wordt?

# Voorbereiding
De ontwerpvraag is alleen van belang voor de backend, omdat de frontend geen directe interactie heeft met externe services. De belangrijke componenten in de backend zijn:
- Generieke controller
- Generieke service
- CacheRepository
- Cache
- ExternalServiceClient

# Pressure cooker

## 1. Componenten en verantwoordelijkheden
- **Generieke controller**: Verantwoordelijk voor het verwerken van requests van de frontend en het doorgeven aan de juiste service.
- **Generieke service**: Verantwoordelijk voor het verwerken van de resultaten van externe services en het omzetten naar domeinobjecten.
- **ExternalAPIHandler**: Verantwoordelijk voor het aanroepen van externe services, het afhandelen van fouten en retries, en het cachen van resultaten om fault tolerance te bieden.
- **CacheRepository**: Verantwoordelijk voor het opslaan en ophalen van resultaten in de cache. Wordt gebruikt door de ExternalAPIHandler.
- Buiten container: **Cache**: Verantwoordelijk voor het cachen van resultaten van externe services.

## 2. Interfaces
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

    public record Endpoint(Method method, String url, HashMap<String, String> queryParams, String bodyHash) {
        public Endpoint(Method method, String url, HashMap<String, String> queryParams) {
            this(method, url, queryParams, "");
        }

        public Endpoint(Method method, String url, String body) {
            this(method, url, new HashMap<>(), body);
        }

        public Endpoint(Method method, String url) {
            this(method, url, new HashMap<>(), "");
        }
    }

    public enum Method {
        GET, POST, PUT, DELETE
    }
    ```
- **ExternalAPIHandler**:
    ```java
    public interface ExternalAPIHandler {
        public String call(Endpoint endpoint);
    }
    ```

## 3. Volgorde van aanroepen
```puml
@startuml
!include <C4/C4_Component.puml>
title Volgorde van aanroepen

LAYOUT_TOP_DOWN()
SHOW_PERSON_OUTLINE()

Container(frontend, "Frontend", "Javascript en React", "Toont de UI voor het planproces van de reiziger en het ticketsysteem van de reisagent")

Container_Boundary(backend, "Backend") {
    Component(controller, "Generieke controller", "Spring Boot RestController", "Verwerkt requests van de frontend en geeft door aan de juiste service")
    Component(service, "Generieke service", "Spring Boot Service", "Verwerkt resultaten van externe services en zet om naar domeinobjecten")
    Component(apiHandler, "ExternalAPIHandler", "Spring Boot Component", "Aanroepen van externe services, afhandelen van fouten en retries, en cachen van resultaten")
    Component(cacheRepo, "CacheRepository", "Spring Data Redis", "Opslaan en ophalen van resultaten in de cache")
}

System_Ext(api, "Externe API", "API van externe service")

ContainerDb(cache, "Cache", "Redis", "Cachen van resultaten van externe services")

Rel(frontend, controller, "1: verstuurt naar", "JSON/REST")
Rel(controller, service, "2: stuurt request door om af te handelen naar", "Method call")
Rel(service, apiHandler, "3: roept externe service aan via", "Method call")

' Eerste poging - externe API aanroepen
Rel(apiHandler, api, "4: roept externe service aan", "JSON/REST")

' Succespad - externe API werkt
Rel(api, apiHandler, "5a: retourneert resultaat aan", "JSON/REST")
Rel(apiHandler, cacheRepo, "6a: slaat resultaat op in cache", "Method call")
Rel(cacheRepo, cache, "7a: slaat resultaat op in", "Jedis")
Rel(cache, cacheRepo, "8a: bevestigt opslag", "Jedis")
Rel(apiHandler, service, "9a: retourneert resultaat aan", "Method call")
Rel(service, controller, "10a: retourneert verwerkt resultaat aan", "Method call")
Rel_U(controller, frontend, "11a: retourneert resultaat aan", "JSON/REST")

' Foutpad - externe API faalt bij eerste poging
Rel_L(api, apiHandler, "5b: fout of timeout", "JSON/REST")

' Retry mechanisme
Rel_L(apiHandler, api, "6b: probeert opnieuw na korte wachttijd (retry 1)", "JSON/REST")
Rel_L(api, apiHandler, "7b: fout of timeout", "JSON/REST")
Rel_L(apiHandler, api, "8b: probeert opnieuw na langere wachttijd (retry 2)", "JSON/REST")
Rel_L(api, apiHandler, "9b: fout of timeout", "JSON/REST")

' Fallback naar cache
Rel_L(apiHandler, cacheRepo, "10b: vraagt eerder resultaat op uit cache", "Method call")
Rel_L(cacheRepo, cache, "11b: vraagt resultaat op uit", "Jedis")
Rel_L(cache, cacheRepo, "12b: retourneert eerder resultaat aan", "Jedis")
Rel_L(cacheRepo, apiHandler, "13b: retourneert eerder resultaat aan", "Method call")
Rel_L(apiHandler, service, "14b: retourneert gecached resultaat aan", "Method call")
Rel_L(service, controller, "15b: retourneert verwerkt resultaat aan", "Method call")
Rel_L(controller, frontend, "16b: retourneert resultaat aan", "JSON/REST")

SHOW_FLOATING_LEGEND()
@enduml
```

## 4. Classes en functies
```puml
@startuml
title C4 Class Diagram - Backend

'hide circle
skinparam linetype ortho

top to bottom direction

package "controller" {
    class FlightsController {
        getFlightPrice(origin: String, destination: String, departureDate: Date, returnDate: Date)
    }
}

package "service" {
    interface FlightsService {
        getFlightPrice(origin: String, destination: String, departureDate: Date, returnDate: Date): double
    }

    class FlightsServiceImpl {
        getFlightPrice(origin: String, destination: String, departureDate: Date, returnDate: Date): double
    }
}

package "api" {
    interface ExternalAPIHandler {
        call(endpoint: Endpoint): String
    }

    class ExternalAPIHandlerImpl {
        MAX_RETRIES: int
        RETRY_DELAY: Duration
        call(endpoint: Endpoint): String
        getCachedResponse(endpoint: Endpoint): Optional<String>
        cacheResponse(endpoint: Endpoint, response: String): void
    }

    class Endpoint {
        url: String
        queryParams: HashMap<String, String>
        bodyHash: String
    }
}

package "repository" {
    interface CacheRepository {
        save(key: Endpoint, response: String, duration: Duration): void
        get(key: Endpoint): Optional<String>
    }

    class RedisCacheRepository {
        save(key: Endpoint, response: String, duration: Duration): void
        get(key: Endpoint): Optional<String>
        generateKey(endpoint: Endpoint): String
    }
}

package "shared" {
    enum Method {
        GET, POST, PUT, DELETE
    }
}

FlightsController --> "flightService 1" FlightsService
FlightsServiceImpl .u.|> FlightsService
FlightsServiceImpl --> "externalAPIHandler 1" ExternalAPIHandler
ExternalAPIHandlerImpl .u.|> ExternalAPIHandler
ExternalAPIHandlerImpl --> "cacheRepository 1" CacheRepository
RedisCacheRepository .u.|> CacheRepository
ExternalAPIHandlerImpl ..> "endpoint 1" Endpoint
Endpoint ..> "method 1" Method

@enduml
```