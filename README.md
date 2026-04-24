# Bibliotekssystem

Ett konsolbaserat bibliotekssystem byggt i Java med JDBC och MySQL. Systemet hanterar böcker, låntagare, lån och författare via ett menybaserat gränssnitt i terminalen.

## Tekniker

- Java 21
- JDBC (MySQL Connector/J 9.6.0)
- MySQL

## Kom igång

### Krav

- JDK Temurin 21
- MySQL
- IntelliJ IDEA (rekommenderas)

### Installation

1. Klona repot
2. Importera `bibliotek.sql` i din MySQL-databas
3. Uppdatera databasuppgifterna i respektive Repository-klass:
   ```java
   private final String URL = "jdbc:mysql://localhost:3306/bibliotek";
   private final String USER = "ditt-användarnamn";
   private final String PASS = "ditt-lösenord";
   ```
4. Lägg till MySQL Connector/J som bibliotek i projektet
5. Kör `Main.java`

## Funktioner

### Som låntagare kan du:
- Logga in med e-post och lösenord
- Se alla böcker och filtrera på tillgängliga
- Söka efter böcker på titel eller beskrivning
- Filtrera böcker efter kategori
- Se de tio mest populära böckerna
- Låna och lämna tillbaka böcker
- Förlänga sina lån
- Skriva recensioner på böcker du har läst
- Se och uppdatera din profilinfo

### Som bibliotekarie kan du:
- Lägga till, redigera och ta bort böcker
- Lägga till och redigera författare
- Hantera låntagarkonton
- Se alla aktiva och försenade lån
- Returnera böcker

## Arkitektur

Projektet följer en trelagersarkitektur:

```
Controller → Service → Repository → Databas
```

- **Controller** — hanterar användarinput och output via konsolen
- **Service** — affärslogik och validering
- **Repository** — databasanrop via JDBC

Paketen är organiserade efter domän: `book`, `loan`, `member`, `author`, `category`, `exceptions`, `base`, `main`.
