1. Eerst id's ophalen van vertrek en bestemming:
- Schiphol (AMS.AIRPORT):
- GET /api/v1/flights/searchDestination?query=schiphol
- {"status":true,"message":"Success","timestamp":1742514885108,"data":\[{"id":"AMS.AIRPORT","type":"AIRPORT","name":"Schiphol Airport","code":"AMS","city":"AMS","cityName":"Amsterdam","regionName":"Noord-Holland","country":"NL","countryName":"Netherlands","countryNameShort":"Netherlands","photoUri":"https://q-xx.bstatic.com/xdata/images/city/square150/976538.jpg?k=19a2487e30f31349e54aaf32d509121d81e2e31eee5b820c7c98576a4426d997&o=","distanceToCity":{"value":11.396990072691436,"unit":"km"},"parent":"AMS"}]}
- barcelona (BCN.AIRPORT):
- GET /api/v1/flights/searchDestination?query=barcelona
- {"status":true,"message":"Success","timestamp":1742514918421,"data":\[{"id":"BCN.AIRPORT","type":"AIRPORT","name":"Barcelona El Prat Airport","code":"BCN","city":"BCN","cityName":"Barcelona","regionName":"Catalonia","country":"ES","countryName":"Spain","countryNameShort":"Spain","photoUri":"https://q-xx.bstatic.com/xdata/images/city/square150/981982.jpg?k=48b5a9a560ff275cc487721d68e6c0a7aedfa23694ed5bb2ece7c56a1c39cc0c&o=","distanceToCity":{"value":12.61649150989104,"unit":"km"},"parent":"BCN"},{"id":"BLA.AIRPORT","type":"AIRPORT","name":"Generál José Antonio Anzoátegui International Airport","code":"BLA","city":"BLA","cityName":"Barcelona","country":"VE","countryName":"Venezuela","countryNameShort":"Venezuela","photoUri":"https://q-xx.bstatic.com/static/img/plane-100.jpg","distanceToCity":{"value":3.14404259313948,"unit":"km"},"parent":"BLA"}]}
2. Vluchten zoeken, en daar de prijs van ophalen
- GET /api/v1/flights/searchFlights?fromId=AMS.AIRPORT&toId=BCN.AIRPORT&departDate=2025-03-22&returnDate=2025-03-30&pageNo=1&sort=BEST&cabinClass=ECONOMY&currency_code=EUR
- response.data.aggregation.minPrice.units (hele euros) en .nanos (centen)
- VGM is dit heen en weer, want ik heb ook `returnDate` opgegeven?
3. Alleen de lat en lon moeten nog op een of andere manier worden opgehaald