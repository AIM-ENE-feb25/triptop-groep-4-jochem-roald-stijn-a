# Prijs van vlucht ophalen

## Gebruikte API
- [Booking COM](https://rapidapi.com/DataCrawler/api/booking-com15)
  - [GET `Search Flight Location`](https://rapidapi.com/DataCrawler/api/booking-com15/playground/apiendpoint_ed5daa70-d9b6-4aa9-86ca-89f7543abb2a)
    - ID's ophalen van vertrek en bestemming
  - [GET `Search Flights`](https://rapidapi.com/DataCrawler/api/booking-com15/playground/apiendpoint_5b86beca-5c23-45ea-9682-4c5fa4075454)
    - Vluchten zoeken en prijs ophalen met vertrek en bestemming id's, en vertrek en terugkeer datum

## Bestemming id's ophalen
Voor het zoeken van vluchten zijn de id's van de vertrek- en bestemmingslocatie nodig. Deze kunnen worden opgehaald met de onderstaande twee requests.

De id's zijn te vinden in:
<!-- (JSONPath) -->
- `$.data[0].id`

### Vertreklocatie: schiphol (AMS.AIRPORT):
```shell
  curl --request GET \
    --url 'https://booking-com15.p.rapidapi.com/api/v1/flights/searchDestination?query=schiphol' \
    --header 'x-rapidapi-host: booking-com15.p.rapidapi.com' \
    --header 'x-rapidapi-key: 92b62a4079mshe417d603a8d8c05p1bbb0fjsn42969109f91c'
```

### Bestemmingslocatie: barcelona (BCN.AIRPORT):
```shell
  curl --request GET \
	--url 'https://booking-com15.p.rapidapi.com/api/v1/flights/searchDestination?query=barcelona' \
	--header 'x-rapidapi-host: booking-com15.p.rapidapi.com' \
	--header 'x-rapidapi-key: 92b62a4079mshe417d603a8d8c05p1bbb0fjsn42969109f91c'
```

## Vluchten zoeken
```shell
curl --request GET 
    --url 'https://booking-com15.p.rapidapi.com/api/v1/flights/searchFlights?fromId=AMS.AIRPORT&toId=BCN.AIRPORT&departDate=2025-04-12&returnDate=2025-04-20&pageNo=1&sort=BEST&cabinClass=ECONOMY&currency_code=EUR'
    --header 'x-rapidapi-host: booking-com15.p.rapidapi.com'
    --header 'x-rapidapi-key: 410bb84dccmshe1bf03f1e953c69p13989ejsna73c05ae6736'
```
Prijs te vinden in:
- `$.data.aggregation.minPrice.units` (hele euros)
- `$.data.aggregation.minPrice.nanos` (centen)
