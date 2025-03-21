**Booking.com:**

**Search Hotels:**

```java
HttpResponse<String> response = Unirest.get("https://booking-com15.p.rapidapi.com/api/v1/hotels/searchHotels?dest_id=-2092174&search_type=CITY&arrival_date=2025-03-22&departure_date=2025-03-25&adults=1&children_age=0%2C17&room_qty=1&page_number=1&units=metric&temperature_unit=c&languagecode=en-us&currency_code=AED")
	.header("x-rapidapi-key", "410bb84dccmshe1bf03f1e953c69p13989ejsna73c05ae6736")
	.header("x-rapidapi-host", "booking-com15.p.rapidapi.com")
	.asString();
```

```shell
curl --request GET 
	--url 'https://booking-com15.p.rapidapi.com/api/v1/hotels/searchHotels?dest_id=-2092174&search_type=CITY&arrival_date=2025-03-22&departure_date=2025-03-25&adults=1&children_age=0%2C17&room_qty=1&page_number=1&units=metric&temperature_unit=c&languagecode=en-us&currency_code=AED' 
	--header 'x-rapidapi-host: booking-com15.p.rapidapi.com' 
	--header 'x-rapidapi-key: 410bb84dccmshe1bf03f1e953c69p13989ejsna73c05ae6736'
```

Data voor verblijf:
begin- en eindatum => dit is iets wat een gebruiker zelf bepaald
prijs => We pakken de goedkoopste prijs voor een kamer in deze tijd periode.
location => booking.com maakt gebruikt van latitude en longitude, er is een endpoint om van een locatie de latitude en longitude te geven:

**Location to Lat Long:**
```java
HttpResponse<String> response = Unirest.get("https://booking-com15.p.rapidapi.com/api/v1/meta/locationToLatLong?query=man")
	.header("x-rapidapi-key", "410bb84dccmshe1bf03f1e953c69p13989ejsna73c05ae6736")
	.header("x-rapidapi-host", "booking-com15.p.rapidapi.com")
	.asString();
```

```shell
curl --request GET 
	--url 'https://booking-com15.p.rapidapi.com/api/v1/meta/locationToLatLong?query=man' 
	--header 'x-rapidapi-host: booking-com15.p.rapidapi.com' 
	--header 'x-rapidapi-key: 410bb84dccmshe1bf03f1e953c69p13989ejsna73c05ae6736'
```

**Get Nearby Cities:**
Met deze endpoint kan je zoeken naar de dichtsbijzijnde steden, als je het bovenste object pakt heb je de stad waar die het dichstbij zit.

```java
HttpResponse<String> response = Unirest.get("https://booking-com15.p.rapidapi.com/api/v1/hotels/getNearbyCities?latitude=65.9667&longitude=-18.5333&languagecode=en-us")
	.header("x-rapidapi-key", "410bb84dccmshe1bf03f1e953c69p13989ejsna73c05ae6736")
	.header("x-rapidapi-host", "booking-com15.p.rapidapi.com")
	.asString();
```

```shell
curl --request GET 
	--url 'https://booking-com15.p.rapidapi.com/api/v1/hotels/getNearbyCities?latitude=65.9667&longitude=-18.5333&languagecode=en-us' 
	--header 'x-rapidapi-host: booking-com15.p.rapidapi.com' 
	--header 'x-rapidapi-key: 410bb84dccmshe1bf03f1e953c69p13989ejsna73c05ae6736'
```
**Get room list:**

```java
HttpResponse<String> response = Unirest.get("https://booking-com15.p.rapidapi.com/api/v1/hotels/getRoomList?hotel_id=74717&arrival_date=2025-03-22&departure_date=2025-03-25&adults=1&children_age=1%2C0&room_qty=1&units=metric&temperature_unit=c&languagecode=en-us&currency_code=EUR")
	.header("x-rapidapi-key", "410bb84dccmshe1bf03f1e953c69p13989ejsna73c05ae6736")
	.header("x-rapidapi-host", "booking-com15.p.rapidapi.com")
	.asString();
```

```shell
curl --request GET 
	--url 'https://booking-com15.p.rapidapi.com/api/v1/hotels/getRoomList?hotel_id=74717&arrival_date=2025-03-22&departure_date=2025-03-25&adults=1&children_age=1%2C0&room_qty=1&units=metric&temperature_unit=c&languagecode=en-us&currency_code=EUR' 
	--header 'x-rapidapi-host: booking-com15.p.rapidapi.com' 
	--header 'x-rapidapi-key: 410bb84dccmshe1bf03f1e953c69p13989ejsna73c05ae6736'
```

Hiervoor heb je de - cheapest_avail_price_eur , dit wordt onze prijs.
