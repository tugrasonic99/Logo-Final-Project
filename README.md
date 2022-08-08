# LOGO Yazılım Final Projesi

## Proje Açıklaması

Bu projenin hedefi Spring Boot yardımıyla, mikroservis mimarisine bağlı kalarak bir seyahat uygulaması geliştirmektir. Mikroservisler 3'e ayrılır:
- *TravelAdminApp*: Admin kullanıcının seferleri manipule edebildiği ve sefer ekleyebildiği servis.
- *TravelMainApp*: Normal kullanıcıların sisteme kayıt ve giriş yapabildiği, seyahatlere bakabildiği ve seyahat için bilet satın alabildiği servis.
- *TravelUserProfile*: Normal kullanıcılara TravelMainApp tarafından gönderilen mesaj ve maillerin asenkron olarak kaydolduğu servis.

## Kullanılacak Teknolojiler
- Min Java 8
- JUnit
- RESTful
- Spring Boot
- Postgre
- RabbbitMQ

## Proje Bilgileri

Servislerle ilgili bazı önemli notlar:
- İlk baştaki önerilen çalıştırma sırası TravelAdminApp -> TravelUserApp -> TravelUserProfile olması önerilir.
- Helper sınıfları Postman tarafından fonksiyonlara uygun şekilde bir JSON query alabilmek adına tasarlandı.
- Exception sınıfları genel anlamda sadece BAD REQUEST adına tasarlanmış olup, genel anlamda diğer spesifik olası hatalarla genel Exception ilgilenmektedir.
- TravelMainApp'de her ne kadar kullanılan Voyage sınıfları TravelAdminApp ile aynı olsa da, Ticket değerlerinin diğer kullanıcılara görünmemesi adına null değer verilmiştir.
- Şifre Hash için SHA-256 kullanılmıştır. 

Bunkar belirtildiğine göre genel projeden bahsedilebilir.

### TravelAdminApp
#### Admin kullanıcı endpoint:

```
localhost:8080/adminuser/register (POST)
```
Admin kullanıcı kaydı için kullanılır. Gerekli değerler: name, password, email

```
localhost:8080/adminuser/login (GET)
```
Admin kullanıcı girişi için kullanılır. Gerekli değerler: name, password

#### Seyahat endpoint:

```
localhost:8080/voyages/allvoyages (GET)
```
Tüm seferleri görmek için kullanılır.


```
localhost:8080/voyages/findbydate (GET)
```
Aynı tarihteki tüm seferleri görmek için kullanılır. Gerekli değerler: date

```
localhost:8080/voyages/findbylocations (GET)
```
Aynı başlangıç-bitişi içeren tüm seferleri görmek için kullanılır. Gerekli değerler: to, from


```
localhost:8080/voyages/createvoyage (POST)
```
Yeni sefer oluşturmak için kullanılır. Gerekli değerler: voyage, vehicle(int değeri. 1 AIRPLANE, 2 BUS oluşturacak).


```
localhost:8080/voyages/deletevoyage (DELETE)
```
Sefer silmek için kullanılır. Gerekli değerler: id


```
localhost:8080/voyages/ticketamount (GET)
```
Seferde satılan bilet sayısını bulmak için kullanılır. Gerekli değerler: id


```
localhost:8080/voyages/totalcost (GET)
```
Seferin toplam maliyetini bulmak için kullanılır. Gerekli değerler: id


### TravelMainApp

#### Kullanıcı endpoint:

```
localhost:8080/mainuserpage/createuser (POST)
```
Yeni kullanıcı yaratılır. Gerekli değerler: name, email, phoneNumber, userType (Hepsi User objesi içinde gidecek)

```
localhost:8080/mainuserpage/login (GET)
```
Kullanıcı girişi olur. Gerekli değerler: name, email

```
localhost:8080/mainuserpage/changepayment (POST)
```
Kullanıcı için yeni ödeme yöntemi oluşturulur. Gerekli değerler: name, email, paymentMethod

```
localhost:8080/mainuserpage/usertickets (GET)
```
Kullanıcı biletlerini görmek adına kullanılır. Gerekli değerler: name, email



#### Seyahat endpoint:
```
localhost:8080/mainvoyage (GET)
```
Tüm seferleri görmek adına kullanılır.

```
localhost:8080/mainvoyage/buyticket (POST)
```
Bilet alım işlemi için kullanılır. Gerekli değerler: userId, voyageId, passengers (Hepsi Ticket objesi içinde gidecek)

```
localhost:8080/mainvoyage/voyagesbydate (GET)
```
Aynı tarihteki tüm seferleri görmek için kullanılır. Gerekli değerler: date

```
localhost:8080/mainvoyage/voyageslocations (GET)
```
Aynı başlangıç-bitişi içeren tüm seferleri görmek için kullanılır. Gerekli değerler: to, from

```
localhost:8080/mainvoyage/voyagesby (GET)
```
Spesifik bir araç tipi adına tüm seferleri görmek için kullanılır. Gerekli değerler: vehicleType


### TravelUserProfile

#### Kullanıcı mail kutusu endpoint:


```
localhost:8080/usermail (GET)
```
Kullanıcıya gelen maillere bakmak adına kullanılır. 

#### Kullanıcı mobil mesaj kutusu endpoint:


```
localhost:8080/userphone (GET)
```
Kullanıcıya gelen mesajlara bakmak adına kullanılır. 




