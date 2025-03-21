

```shell
curl --request GET \
	--url 'https://booking-com15.p.rapidapi.com/api/v1/attraction/searchAttractions?id=eyJ1ZmkiOi0yMDkyMTc0fQ%3D%3D&sortBy=trending&page=1&currency_code=INR&languagecode=en-us' \
	--header 'x-rapidapi-host: booking-com15.p.rapidapi.com' \
	--header 'x-rapidapi-key: c2a11c5dc5mshc8163f22cd6e049p1b5d09jsn77de5b5baf7c'
```

```java
Unirest.setTimeouts(0, 0);
HttpResponse<String> response = Unirest.get("https://booking-com15.p.rapidapi.com/api/v1/attraction/searchAttractions?id=eyJ1ZmkiOi0yMDkyMTc0fQ%3D%3D&sortBy=trending&page=1&currency_code=INR&languagecode=en-us")
  .header("x-rapidapi-host", "booking-com15.p.rapidapi.com")
  .header("x-rapidapi-key", "c2a11c5dc5mshc8163f22cd6e049p1b5d09jsn77de5b5baf7c")
  .asString();
```