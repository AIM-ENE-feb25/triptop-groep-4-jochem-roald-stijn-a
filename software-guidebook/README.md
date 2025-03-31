# Software Guidebook Triptop

## 1. Introduction

Dit software guidebook geeft een overzicht van de Triptop-applicatie. Het bevat een samenvatting van het volgende:

1. De vereisten, beperkingen en principes.
2. De software-architectuur, met inbegrip van de technologiekeuzes op hoog niveau en de structuur van de software.
3. De ontwerp- en codebeslissingen die zijn genomen om de software te realiseren.
4. De architectuur van de infrastructuur en hoe de software kan worden geïnstalleerd.

## 2. Context

In dit hoofdstuk wordt de context van de software beschreven. Dit omvat de gebruikers, het systeem en de externe systemen die met de software communiceren.

![context diagram](sgb-bestanden/context-diagram-C4_Context_diagram__TripTop.svg)

> **Figuur 1:** Context diagram van de Triptop-applicatie.
> 
> - Rechtsboven in het diagram staat de legenda met de betekenis van de kleuren in het diagram.
> - Het externe systeem **Mollie** is toegevoegd voor betalingen, maar de implementatie hiervan is nog onduidelijk. Dit wordt in de prototypes niet meegenomen, omdat dit geen hoofdzaak is en geen prioriteit heeft.
> 
> In [figuur 11](#711-container-diagram) is een container diagram te zien dat ingezoomd is op het systeem uit dit context diagram.

### 2.1. Toelichting Functionaliteit
In het systeem kunnen reizigers reizen plannen en boeken, en wordt alle informatie opgehaald aan de hand van API's.

### 2.2. Toelichting Gebruikers
Het systeem heeft twee typen gebruikers: de **reiziger** en de **beheerder**.
- De reiziger is de primaire gebruiker van de applicatie. De reiziger kan met zijn account reizen plannen en boeken.
- De reis agent heeft sinds de implementatie van deze applicatie een minder belangrijke rol. Hij kan gebeld worden door de reiziger om te helpen met het boeken van een reis, en kan tickets afhandelen van reizigers die problemen hebben met hun reis.

### 2.3. Toelichting Externe Systemen
Wij hebben gekozen voor de onderstaande API's, omdat deze de meeste bouwstenen bevatten die wij nodig hebben voor onze applicatie. Deze keuze staat vastgelegd in [ADR-001 Externe systemen](#81-adr-001-externe-systemen).

- [Booking.com](https://rapidapi.com/DataCrawler/api/booking-com15/playground/apiendpoint_6767dbac-969b-4230-8d26-f8b007bb8094)
  - hotels
  - vluchten
  - autoverhuur
  - taxi
  - attracties
- [Tripadvisor](https://rapidapi.com/DataCrawler/api/tripadvisor16/playground/apiendpoint_b0128f2a-67c5-4bbd-9369-721cc7170f9c)
  - Flights
  - Hotels
  - Restaurants
  - "Vacation Rentals"
  - Cruises
  - Auto verhuur

## 3. Functional Overview

Om de belangrijkste features toe te lichten zijn er user stories en twee domain stories gemaakt en een overzicht van het domein in de vorm van een domeinmodel. Op deze plek staat typisch een user story map maar die ontbreekt in dit voorbeeld.

### 3.1 User Stories

#### 3.1.1 User Story 1: Reis plannen

Als gebruiker wil ik een zelfstandig op basis van diverse variabelen (bouwstenen) een reis kunnen plannen op basis van mijn reisvoorkeuren (wel/niet duurzaam reizen, budget/prijsklasse, 's nachts reizen of overdag etc.) zodat ik op vakantie kan gaan zonder dat hiervoor een reisbureau benodigd is.

#### 3.1.2 User Story 2: Reis boeken

Als gebruiker wil ik een geplande reis als geheel of per variabele (bouwsteen) boeken en betalen zodat ik op vakantie kan gaan zonder dat hiervoor een reisbureau benodigd is.

#### 3.1.3 User Story 3: Reis cancelen

Als gebruiker wil ik een geboekte reis, of delen daarvan, kunnen annuleren zodat ik mijn geld terug kan krijgen zonder inmenging van een intermediair zoals een reisbureau.

#### 3.1.4 User Story 4: Reisstatus bewaren

Als gebruiker wil ik mijn reisstatus kunnen bewaren zonder dat ik een extra account hoef aan te maken zodat ik mijn reis kan volgen zonder dat ik daarvoor extra handelingen moet verrichten.

#### 3.1.5 User Story 5: Bouwstenen flexibel uitbreiden

Als gebruiker wil ik de bouwstenen van mijn reis flexibel kunnen uitbreiden met een zelf te managen stap (bijv. met providers die niet standaard worden aangeboden zoals een andere reisorganisatie, hotelketen etc.) zodat ik mijn reis helemaal kan aanpassen aan mijn wensen.

### 3.2 Domain Story Reis Boeken (AS IS)

![Domain Story Reis Boeken AS IS](../opdracht-diagrammen/reis-boeken-asis-coursegrained_2024-06-11.egn.svg)
> **Figuur 2:** Domain Story Reis Boeken AS IS

### 3.3 Domain Story Reis Boeken (TO BE)

![Domain Story Reis Boeken TO BE](../opdracht-diagrammen/reis-boeken-tobe-coursegrained_2024-06-11.egn.svg)
> **Figuur 3:** Domain Story Reis Boeken TO BE

### 3.4 Domain Model

![Domain Model](../opdracht-diagrammen/Domain%20Model.png)
> **Figuur 4:** Domain Model

## 4. Quality Attributes

Voordat deze casusomschrijving tot stand kwam, heeft de opdrachtgever de volgende ISO 25010 kwaliteitsattributen benoemd als belangrijk:

- Compatibility -> Interoperability (Degree to which a system, product or component can exchange information with other products and mutually use the information that has been exchanged)
- Reliability -> Fault Tolerance (Degree to which a system or component operates as intended despite the presence of hardware or software faults)
- Maintainability -> Modularity (Degree to which a system or computer program is composed of discrete components such that a change to one component has minimal impact on other components)
- Maintainability -> Modifiability (Degree to which a product or system can be effectively and efficiently modified without introducing defects or degrading existing product quality)
- Security -> Integrity (Degree to which a system, product or component ensures that the state of its system and data are protected from unauthorized modification or deletion either by malicious action or computer error)
- Security -> Confidentiality (Degree to which a system, product or component ensures that data are accessible only to those authorized to have access)

Naar aanleiding van deze kwaliteitsattributen zijn de volgende ontwerpvragen opgesteld en uitgewerkt:

### 4.1. Modularity - Alternatieve bouwstenen aanbieden

De volgende ontwerpvraag is uitgewerkt door **Jochem**:

> Hoe bied je de gebruiker op basis van zelfgekozen bouwstenen alternatieve bouwstenen aan, bijvoorbeeld als een bepaalde overnachting niet beschikbaar is of om een keuze te geven tussen vervoer per auto, trein of bus

De ontwerpvraag wordt uitgewerkt door middel van het **Strategy Design Pattern**.

#### 4.1.1. Componenten en verantwoordelijkheden

- **Generieke controller**: Verantwoordelijk voor het verwerken van requests van de frontend en het doorgeven aan de juiste service.
- **Generieke service**: Verantwoordelijk voor het verwerken van de resultaten van externe services en het omzetten naar domeinobjecten.
- **Generieke strategy**: Verantwoordelijk voor het ophalen van alternatieve bouwstenen uit externe services, het genereren van aanvullende opties op basis van gebruikersvoorkeuren en het omzetten van deze alternatieven naar domeinobjecten.
- **ExternalAPIHandler**: Verantwoordelijk voor het aanroepen van externe services, het afhandelen van fouten en retries.

#### 4.1.2. Interfaces

- **Generieke controller**:
  ```
  GET /journey
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
  public interface JourneyService {
      List<Journey> getJourneys(String origin, String destination, Date departureDate, Date returnDate, double price, Transport transport);
  }
  ```

- **Generieke strategy**:

  ```java
  public interface JourneyStrategy {
      List<Journey> getJourneys(String origin, String destination, Date departureDate, Date returnDate, double price, Transport transport);
  }
  ```

- **ExternalAPIHandler**:
  ```java
  public interface ExternalAPIHandler {
      String call(Endpoint endpoint);
  }
  ```

#### 4.1.3. Component diagram

Hieronder is een component diagram uitgewerkt.

![Modularity - component diagram-Component_diagram.png](sgb-bestanden%2Fontwerpvragen%2FModularity%20-%20component%20diagram-Component_diagram.png)
> **Figuur 5:** Component diagram van alternatieve bouwstenen aanbieden
>
> TODO Jochem: uitleg

#### 4.1.4. Klassen en functies

Hieronder is een class diagram uitgewerkt die de classes en functies weergeeft die van belang zijn voor de ontwerpvraag.

![Modularity - class diagram-C4_Class_Diagram___Backend.png](sgb-bestanden%2Fontwerpvragen%2FModularity%20-%20class%20diagram-C4_Class_Diagram___Backend.png)
> **Figuur 6:** Class diagram van alternatieve bouwstenen aanbieden
> 
> TODO Jochem: uitleg

### 4.2. Modifiability - Verschillende boekingsservices integreren
De volgende ontwerpvraag is uitgewerkt door **Roald**:
> Hoe kunnen verschillende boekingsservices (zoals Booking.com en eigen beheer in Triptop) worden geïntegreerd?

#### 4.2.1. Componenten en verantwoordelijkheden

- **BookingController**: Verantwoordelijk voor het verwerken van requests van de frontend en het doorgeven aan de juiste service.
- **BookingService**: Verantwoordelijk voor het verwerken van de resultaten van boeking services en het omzetten naar domeinobjecten.
- **BookingAdapter**: Verantwoordelijk voor het aanroepen van boeking services, het afhandelen van fouten en retries, en het cachen van resultaten om fault tolerance te bieden.
- **EigenBeheerService**: Verantwoordelijk voor het aanroepen van de interne API's van Triptop en het omzetten naar domeinobjecten.
- **BookingComAdapter**: Verantwoordelijk voor het aanroepen van de externe API's van Booking.com en het omzetten naar domeinobjecten.
- **BookingComApi**: Verantwoordelijk voor het aanroepen van de externe API's van Booking.com en het omzetten naar domeinobjecten.


#### 4.2.2. Interfaces
Hieronder zijn de interfaces van de componenten die van belang zijn voor de ontwerpvraag uitgewerkt. Deze interfaces geven een overzicht van de methodes die de componenten aanbieden.


#### 4.2.3. Component diagram
Hieronder is een component diagram uitgewerkt die de componenten en hun verantwoordelijkheden weergeeft. Dit diagram geeft een overzicht van de componenten en hun verantwoordelijkheden.
![Modifiability - Component diagram.svg](sgb-bestanden/ontwerpvragen/Modifiability%20-%20Component%20diagram.svg)
> **Figuur 7:** Component diagram van verschillende boekingsservices integreren
> 
> TODO Roald: uitleg

#### 4.2.4. Classes en functies
Hieronder is een class diagram uitgewerkt die de classes en functies weergeeft die van belang zijn voor de ontwerpvraag. Dit diagram geeft een overzicht van de classes en hun verantwoordelijkheden.
![Modifiability - class diagram.svg](sgb-bestanden/ontwerpvragen/Modifiability%20-%20class%20diagram.svg)
> **Figuur 8:** Class diagram van verschillende boekingsservices integreren
> 
> TODO Roald: uitleg

### 4.3. Fault Tolerance - Externe services die niet beschikbaar zijn

De volgende ontwerpvraag is uitgewerkt door **Stijn**:

> Hoe ga je om met aanroepen van externe services die niet beschikbaar zijn en toch verwacht wordt dat er waardevolle output gegeven wordt?

De ontwerpvraag wordt uitgewerkt door middel van het **Facade Design Pattern**.

#### 4.3.1. Componenten en verantwoordelijkheden

Als eerst is er een overzicht gemaakt van de componenten en hun verantwoordelijkheden die van belang zijn voor de ontwerpvraag.

- **Generieke controller**: Verantwoordelijk voor het verwerken van requests van de frontend en het doorgeven aan de juiste service.
- **Generieke service**: Verantwoordelijk voor het verwerken van de resultaten van externe services en het omzetten naar domeinobjecten.
- **ExternalAPIHandler**: Verantwoordelijk voor het aanroepen van externe services, het afhandelen van fouten en retries, en het cachen van resultaten om fault tolerance te bieden.
- **CacheRepository**: Verantwoordelijk voor het opslaan en ophalen van resultaten in de cache. Wordt gebruikt door de ExternalAPIHandler.
- Buiten container: **Cache**: Verantwoordelijk voor het cachen van resultaten van externe services.

#### 4.3.2. Interfaces

Hieronder zijn de interfaces van de componenten die van belang zijn voor de ontwerpvraag uitgewerkt. Deze interfaces geven een overzicht van de methodes die de componenten aanbieden.

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
      List<Flight> getFlights(String origin, String destination, Date departureDate, Date returnDate);
  }
  ```
- **CacheRepository**:

  ```java
  public interface CacheRepository {
      void save(Endpoint key, String response, Duration duration);
      String get(Endpoint key);
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
      String call(Endpoint endpoint);
  }
  ```

#### 4.3.3. Volgorde van aanroepen

Hieronder is een dynamisch container diagram uitgewerkt die de volgorde van aanroepen van externe services weergeeft.

![Fault Tolerance - volgorde van aanroepen](sgb-bestanden/ontwerpvragen/Fault%20Tolerance%20-%20volgorde%20van%20aanroepen-Volgorde_van_aanroepen.svg)
> **Figuur 9:** Dynamisch component diagram van externe services
> 
> De relaties naar de Externe API zijn slecht te lezen doordat ze overlappen. Deze connecties zijn beter te lezen in [het bestand zelf](./sgb-bestanden/ontwerpvragen/Fault%20Tolerance%20-%20volgorde%20van%20aanroepen.puml).
> 
> De beslissing om de nieuwste API-data voor de cache te laten gaan is vastgelegd in [ADR-004: Nieuwste API data gaat voor cache](#84-adr-004-nieuwste-api-data-gaat-voor-cache).

#### 4.3.4. Klassen en functies

Hieronder is een class diagram uitgewerkt die de klassen en functies weergeeft die van belang zijn voor de ontwerpvraag.

![Fault Tolerance - class diagram](sgb-bestanden/ontwerpvragen/Fault%20Tolerance%20-%20class%20diagram-C4_Class_Diagram___Backend.svg)
> **Figuur 10:** Class diagram van externe services
> 
> Dit klassendiagram maakt gebruik van de volgende design principles:
> - Program to an Interface
> - Law of Demeter
> - Dependency Inversion Principle

- evt. link naar ADR's

## 5. Constraints

<!-- TODO; Dit hoofdstuk kort uitwerken. Talen en framework keuzes, rest vind Bart niet interresant -->
<!-- - JavaScript
- React
- Java
- Spring boot
- SQL Server

(spring data jdbc?) -->

> [!IMPORTANT]
> Beschrijf zelf de beperkingen die op voorhand bekend zijn die invloed hebben op keuzes die wel of niet gemaakt kunnen of mogen worden.

## 6. Principles

> [!IMPORTANT]
> Beschrijf zelf de belangrijkste architecturele en design principes die zijn toegepast in de software.

- Program to an Interface
- Single Responsibility Principle
- Open/Closed Principle
- Dependency Inversion Principle

## 7. Software Architecture

### 7.1. Containers

[//]: # "> [!IMPORTANT]"
[//]: # "> Voeg toe: Container Diagram plus een Dynamic Diagram van een aantal scenario's inclusief begeleidende tekst."

#### 7.1.1. Container diagram

In het container diagram is te zien hoe de verschillende containers met elkaar communiceren. De containers zijn de verschillende onderdelen van de applicatie. In dit geval zijn dat de front-end, back-end, database, cache en de externe API's.

![container diagram](sgb-bestanden/container-diagram-C4_Container_diagram__TripTop.svg)
> **Figuur 11:** Container diagram van de Triptop-applicatie
> 
> Dit diagram geeft een overzicht van de verschillende containers binnen het systeem dat in [figuur 1](#2-context) is beschreven.
> 
> - De backend en database communiceren via Spring JDBC. Hierbij wordt gebruik gemaakt van Spring JDBC Template.
> - De backend en Redis cache communiceren via Jedis. Dit is een dependency in de pom.xml.
> - Mollie wordt aangeroepen via een REST API, maar het is nog onduidelijk wat voor response we krijgen (bijvoorbeeld JSON).

#### 7.1.2. Dynamische container diagrammen

In de dynamische container diagrammen is te zien hoe de containers met elkaar communiceren, en in welke volgorde dat gebeurt. Hieronder zijn twee scenario's uitgewerkt: inloggen en een reis boeken.

##### 7.1.2.1 Inloggen

![dynamisch-container-diagram-inloggen](sgb-bestanden/dynamisch-container-diagram-inloggen-C4_Dynamisch_container_diagram__inloggen_op_TripTop.svg)
> **Figuur 12:** Dynamisch container diagram van inloggen op TripTop

##### 7.1.2.2 Reis boeken

![dynamisch-container-diagram-reis-boeken](sgb-bestanden/dynamisch-container-diagram-reis-boeken-C4_Dynamisch_container_diagram__een_reis_boeken_op_TripTop.svg)
> **Figuur 13:** Dynamisch container diagram van een reis boeken op TripTop

Aangezien we nog niet weten hoe Mollie in elkaar zit houdt het diagram hier op.

### 7.2. Components

> [!IMPORTANT]
> Voeg toe: Component Diagram plus een Dynamic Diagram van een aantal scenario's inclusief begeleidende tekst.

### 7.3. Design & Code

> [!IMPORTANT]
> Voeg toe: Per ontwerpvraag een Class Diagram plus een Sequence Diagram van een aantal scenario's inclusief begeleidende tekst.

## 8. Architectural Decision Records

<!-- > [!IMPORTANT]
> Voeg toe: 3 tot 5 ADR's die beslissingen beschrijven die zijn genomen tijdens het ontwerpen en bouwen van de software. -->

### 8.1. ADR-001 Externe systemen

#### Context

Onze applicatie heeft data van externe systemen nodig zodat gebruikers een trip kunnen boeken. Om het aantal externe systemen kort te houden hebben we een tweetal externe systemen gekozen die zoveel mogelijk bouwstenen bevatten.

#### Considered Options

We hadden een aardige lijst van api's gemaakt die we mogelijk konden gebruiken voor onze applicatie.
Er waren een paar systemen die er boven uit kwamen. Hieronder is een tabel die aantoont welke bouwstenen de externe systemen bevatten.

| **Extern systeem** | **Hotels** | **Vluchten** | **Attracties** | **Autoverhuur** | **Restaurants** | **Overig vervoer** |
| ------------------ | ---------- | ------------ | -------------- | --------------- | --------------- | ------------------ |
| **Booking**        | x          | x            | x              | x               |                 | x                  |
| **TravelData**     |            | x            |                |                 |                 |                    |
| **Flight Scraper** | x          | x            |                | x               |                 |                    |
| **Tripadvisor**    | x          | x            |                | x               | x               | x                  |

We hebben uiteindelijk de verschillende bouwstenen vergeleken van alle api's en hebben gekozen voor Tripadvisor en Booking.
Aangezien Tripadvisor en Booking zowel vluchten, hotels en autoverhuur bevatten raken ze alle bouwstenen die TravelData en Flight Scraper hebben.
Verder hebben de gekozen systemen ook nog meer bouwstenen die noodzakelijk zijn voor de applicatie zoals attracties en restaurants.

#### Decision

We gaan Booking en Tripadvisor als onze externe systemen gebruiken.

#### Status

Geaccepteerd

#### Consequences

- Als een van de externe systemen uit valt zal gelijk een groot deel van de mogelijkheden op onze website niet meer zichtbaar zijn.
- Doordat we slechts twee externe systemen gebruiken, kunnen we informatie sneller ophalen en efficiënt verwerken. Dit vermindert de complexiteit van onze integratie en verhoogt de stabiliteit.
- Er zijn minder kosten aan het gebruiken van externe systemen.
- Minder API's betekend minder onderhoud.

### 8.2. ADR-002 Hoe we omgaan met het "tegelijk" versturen van meerdere API requests

#### Context

Binnen deze applicatie worden er een hoop API requests gedaan. Deze requests kunnen erg lang duren, afhankelijk van de API die wordt aangesproken. Het is daarom belangrijk om te bepalen hoe we omgaan met het moeten versturen van meerdere requests om data uit verschillende API's te halen.

#### Alternatieven

| Methode           | Beschrijving                         | Implementatie | Snelheid | Flexibiliteit |
| ----------------- | ------------------------------------ | ------------- | -------- | ------------- |
| Synchroon         | Requests achter elkaar versturen     | ++            | --       | +             |
| CompletableFuture | Snel en flexibel voor meerdere calls | -             | ++       | +             |
| ExecutorService   | Als je expliciet threadbeheer wilt   | --            | ++       | -             |
| Spring @Async     | Voor betere Spring-integratie        | --            | ++       | -             |

#### Besluit

Met oog op simpliciteit hebben wij er voor gekozen om voorlopig de requests achter elkaar te versturen. Dit betekent dat we eerst de ene request versturen en wachten op een response voordat we de volgende request versturen.

#### Status

Voorgesteld

#### Consequenties

- De applicatie is simpeler te implementeren
- De snelheid van de applicatie kan hierdoor afnemen

### 8.3. ADR-003 Keuze Database

#### Context

We moeten een keuze maken voor een database voor de applicatie.

#### Alternatieven

| Database      | Kennis | Open Source |
| ------------- | ------ | ----------- |
| MS SQL Server | +      | -           |
| PostgreSQL    | -      | +           |
| MySQL         |        | +           |

#### Besluit

We kiezen voor MS SQL Server als database omdat we al ervaring hebben met deze database.

#### Status

Geaccepteerd

#### Consequenties

- We moeten de database in docker draaien.
- Alle huidige developers kunnen meteen beginnen met implementere

### 8.4. ADR-004 Nieuwste API data gaat voor cache

#### Context

Actuele reisgegevens zijn cruciaal vanwege snel veranderende prijzen en beschikbaarheid. Verouderde data kan leiden tot frustratie bij gebruikers. Tegelijkertijd kunnen frequente API-aanroepen de prestaties beïnvloeden en kosten verhogen. Een balans tussen actualiteit, prestaties en betrouwbaarheid is noodzakelijk.

#### Alternatieven

| Strategie                  | Beschrijving                                                                                          | Actualiteit van gegevens | Prestaties | Betrouwbaarheid |
| -------------------------- | ----------------------------------------------------------------------------------------------------- | ------------------------ | ---------- | --------------- |
| **API-first**              | Altijd eerst de API aanroepen, cache alleen gebruiken als fallback wanneer de API niet beschikbaar is | ++                       | -          | +               |
| **Stale-while-revalidate** | Eerst cache tonen (indien beschikbaar), dan API op de achtergrond aanroepen om cache te verversen     | -                        | ++         | -               |
| **Cache-first**            | Altijd cache gebruiken als die beschikbaar is, API alleen aanroepen als cache leeg of verlopen is     | -                        | ++         | --              |

#### Besluit

We kiezen voor de API-first strategie: altijd eerst de API aanroepen voor actuele data en alleen terugvallen op cache bij onbeschikbaarheid. Dit garandeert actuele informatie en voorkomt frustratie door verouderde gegevens. Andere strategieën bieden minder betrouwbaarheid of actualiteit.

#### Status

Geaccepteerd

#### Consequenties

**Positieve consequenties:**

- Gebruikers krijgen altijd de meest actuele informatie over prijzen, beschikbaarheid en andere reisgegevens.
- Verhoogde betrouwbaarheid van de getoonde informatie.

**Negatieve consequenties:**

- Meer API-verzoeken kunnen leiden tot hogere kosten.
- Mogelijk langere laadtijden voor gebruikers, vooral bij trage API-responses.

### 8.5. ADR-005 Strategy Pattern voor Alternatieve Bouwstenen

#### Context

Onze applicatie biedt verschillende alternatieve bouwstenen zoals vluchten, hotels en autoverhuur. We willen deze bouwstenen dynamisch kunnen wisselen zonder de bestaande code te breken.

#### Alternatieven

We hebben de volgende opties overwogen om alternatieve bouwstenen te beheren. Hieronder volgt een vergelijkingstabel op basis van verschillende criteria:

| **Optie**               | **Flexibiliteit** | **Onderhoudbaarheid** | **Uitbreidbaarheid** | **Testbaarheid** |
|-------------------------|-------------------|-----------------------|----------------------|------------------|
| **Conditionele Logica** | -                 | --                    | --                   | --               |
| **Strategy Pattern**    | ++                | ++                    | ++                   | ++               |
| **Factory Pattern**     | +                 | +                     | ++                   | +                |

#### Besluit

We kiezen voor het **Strategy Pattern** om alternatieve bouwstenen flexibel te beheren en dynamisch te wisselen.

#### Status

Geaccepteerd

#### Consequenties

- Nieuwe alternatieven kunnen eenvoudig worden toegevoegd zonder de bestaande code te breken.
- De logica wordt minder complex, waardoor het onderhoud van de applicatie gemakkelijker wordt.
- Het is mogelijk om snel nieuwe strategieën te implementeren zonder dat dit invloed heeft op andere onderdelen van de applicatie.
- Elke bouwsteen kan afzonderlijk worden getest.

[//]: # (### 8.5. ADR-005 TITLE)

[//]: # ()
[//]: # (> [!TIP])

[//]: # (> These documents have names that are short noun phrases. For example, "ADR 1: Deployment on Ruby on Rails 3.0.10" or "ADR 9: LDAP for Multitenant Integration". The whole ADR should be one or two pages long. We will write each ADR as if it is a conversation with a future developer. This requires good writing style, with full sentences organized into paragraphs. Bullets are acceptable only for visual style, not as an excuse for writing sentence fragments. &#40;Bullets kill people, even PowerPoint bullets.&#41;)

[//]: # ()
[//]: # (#### Context)

[//]: # ()
[//]: # (> [!TIP])

[//]: # (> This section describes the forces at play, including technological, political, social, and project local. These forces are probably in tension, and should be called out as such. The language in this section is value-neutral. It is simply describing facts about the problem we're facing and points out factors to take into account or to weigh when making the final decision.)

[//]: # ()
[//]: # (#### Alternatieven)

[//]: # ()
[//]: # (> [!TIP])

[//]: # (> This section describes the options that were considered, and gives some indication as to why the chosen option was selected.)

[//]: # ()
[//]: # (#### Besluit)

[//]: # ()
[//]: # (> [!TIP])

[//]: # (> This section describes our response to the forces/problem. It is stated in full sentences, with active voice. "We will …")

[//]: # ()
[//]: # (#### Status)

[//]: # ()
[//]: # (> [!TIP])

[//]: # (> A decision may be "proposed" if the project stakeholders haven't agreed with it yet, or "accepted" once it is agreed. If a later ADR changes or reverses a decision, it may be marked as "deprecated" or "superseded" with a reference to its replacement.)

[//]: # ()
[//]: # (#### Consequenties)

[//]: # ()
[//]: # (> [!TIP])

[//]: # (> This section describes the resulting context, after applying the decision. All consequences should be listed here, not just the "positive" ones. A particular decision may have positive, negative, and neutral consequences, but all of them affect the team and project in the future. -->)

## 9. Deployment, Operation and Support

> [!TIP]
> Zelf beschrijven van wat je moet doen om de software te installeren en te kunnen runnen.
