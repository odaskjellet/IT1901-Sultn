[![Gitpod Ready-to-Code](https://img.shields.io/badge/Gitpod-Ready--to--Code-blue?logo=gitpod)](https://gitpod.stud.ntnu.no/#https://gitlab.stud.idi.ntnu.no/it1901/groups-2021/gr2118/gr2118)

![](img/sultn-logo.png)

# SULTN

Dette prosjektet er en applikasjon som bruker en trelagsarkitektur med domene, brukergrensesnitt (UI) og persistenslag (lagring).
Applikasjonen er en kokebok, hvor man (når den er ferdig) skal kunne ha oversikt og lese, lagre, legge til og redigere oppskrifter.
Prosjektet inneholder foreløpig noen tester for å lage, legge til og slette en oppskrift i kokebok-klassen.

## Organisering

- sultn/core/src/main/java for klasser og logikk.
- sultn/ui/src/main/java for UI og ressurser tilhørende dette.
- sultn/json/src/main/java for persistenshåndtering.

## Bygging og kjøring av prosjektet

Bygges, kjøres og testes med maven.

- Kodelageret og riktig utgivelse ligger på greinen ***main***.
- Klon repoet lokalt eller kjør det med bruk av Gitpod (bruk Gitpod-knappen øverst).
- Prosjektet bygges fra mappen "gr2118/sultn/ui".
- Kjør kommandoen ***mvn compile***.
- Kan så enten kjøres med run eller med test:
    - ***mvn javafx:run***
    - ***mvn test***

## Testdekningsgrad

For å sjekke testdekningsgrad følges disse stegene:
- cd inn til gr2118/sultn
- I terminalen skriver vi:
    - `mvn clean jacoco:prepare-agent install jacoco:report`
- Deretter går man i Explorer til ..core/target/site/jacoco
- Her høyreklikker du på `index.html`, så `Copy Path` og limer det inn i nettleseren din.

## Utvikling

Utviklingen av applikasjonen gjøres med bruk av smidig-praksis og SCRUM-metoden.
Vi har designet brukerhistorier som vi knytter utviklingsoppgaver (issues) til.
Disse utviklingsoppgavene er markert etter viktighet, type oppgave, hvilket nåværende status (om oppgaven jobbes med) og blir tildelt ledige gruppemedlemmer.

### Første brukerhistorie

(UserStory) Åpne kokebok // issue #5

*"Jeg som bruker vil kunne åpne appen og se en oversikt med liste av oppskrifter."*

Her har vi laget flere deloppgaver:
- Lag kokebok (oversikt over oppskrifter).
- Lag oppskriftt-klasse 
- Lag ingrediens-klasse.
- Lag grafisk brukergrensesnitt for å vise kokebok og oppskrifter.
- Populer database med demo-oppskrifter.

### Andre brukerhistorie

(UserStory) Opprette ny oppskrift // issue #6

*"Som bruker ønsker jeg å kunne legge til ny oppskrifter i min kokebok. Jeg vil kunne benytte et skjema for å lage en ny oppskrift, som så legges til i den eksisterende lista over oppskrifter."*

### Tredje brukerhistorie

(UserStory) Se på oppskrift // issue #18

*"Jeg vil som bruker kunne velge en av oppskriftene på hovedsiden. Denne skal ta meg til en ny side som viser oppskriften. Oppskriften skal inneholde navn, ingrediensliste og instruksjoner på hvordan den lages."*

## Visuell støtte

![](img/sultn-design.png)
