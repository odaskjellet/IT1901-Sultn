[![Gitpod Ready-to-Code](https://img.shields.io/badge/Gitpod-Ready--to--Code-blue?logo=gitpod)](https://gitpod.stud.ntnu.no/#https://gitlab.stud.idi.ntnu.no/it1901/groups-2021/gr2118/gr2118)

![](img/sultn-logo.png)

# SULTN

**SULTN** er en personlig digital kokebok. Her kan du lagre dine egne oppskrifter ved å gi den et navn, lage en ingrediensliste og skrive instruksjoner for hvordan retten lages. Oppskriften lagres så i din kokebok og kan hentes opp neste gang du er på kjøkkenet eller i butikken.

Per nå har Sultn følgende funksjonalitet:
- Legge til ny oppskrift gjennom et skjema.
- Se liste over alle oppskrifter på hjemskjermen.
- Klikke inn på hver oppskrift for å se instruksjoner og ingredienser.
- Slette oppskrifter.

## Hvordan SULTN fungerer
### Åpne oppskrift
Når applikasjonen åpnes tas man direkte til oversikt over lagrede oppskrifter. Herfra kan man åpne tilgjengelige oppskrifter ved å klikke "Open" ved siden av navnet på retten som tar deg til oppskriftens ingredienser og instruksjoner.

### Ny oppskrift
For å legge til ny oppskrift klikker man på "Add Recipe" på hjemskjermen. Man blir tatt videre til et skjema som fylles ut med rettens navn, ingredienser og instruksjoner. Ingredienser legges til én og én, hvor "Amount" er et tall og "Unit" er tekst som signaliserer enhet, f.eks. "stk", "liter", "kg". Instruksjoner skrives inn som tekst og adskilles ved å skrive punktum etter en instruksjon. Når man har lagt til alle ingredienser og instruksjoner, klikker man "Add Recipe" som tar deg tilbake til hjemskjermen, hvor den nye oppskriften nå er lagt til.

### Slette oppskrift
Inne på en oppskrift kan man trykke på "Delete recipe" for å slette en oppskrift. En dialogboks signaliserer at oppskriften er slettet, og man kan trykke på "X" for å gå ut av den slettede oppskriften.

<div>
    <img src="/img/home-screen.png" alt="Home" height="400">
    <img src="/img/new-recipe-form.png" alt="New recipe" height="400">
    <img src="/img/example-recipe.png" alt="Example recipe" height="400">
</div>

## Organisering
Dette prosjektet er en applikasjon som bruker en trelagsarkitektur med domene, brukergrensesnitt (UI) og persistenslag (lagring).

- **core-modul:** `sultn/core/src/main/java` for klasser og logikk.
- **ui-modul:** `sultn/ui/src/main/java` for UI og ressurser tilhørende dette.
- **json-modul:** `sultn/json/src/main/java` for persistenshåndtering.

## Bygging og kjøring av prosjektet

Prosjektet er skrevet i Java ved bruk av [JavaFX](https://openjfx.io/). Koden bygges, kjøres og testes med [Maven](https://maven.apache.org/).

- Kodelageret og riktig utgivelse ligger på greinen ***master***.
- Klon repoet lokalt eller kjør det med bruk av Gitpod (bruk Gitpod-knappen øverst).
- Fra mappen `gr2118/sultn` kjøres først `mvn clean install`.
- For å kjøre applikasjonen brukes `mvn javafx:run` inn i mappen `gr2118/sultn/ui`.
- Andre kommandoer som kan kjøres enten inni en enkelt modul (`core`, `json` eller `ui`) eller globalt i `gr2118/sultn`:
    - `mvn test` - kjører tilgjengelige tester.
    - `mvn jacoco:report` - rapporterer testdekningsgrad lokalt i `target/site/index.html`, evt. `sultn/ui/target/site/index.html` hvis kommandoen ble kjørt globalt (benytt tillegget [Live Server](https://marketplace.visualstudio.com/items?itemName=ritwickdey.LiveServer) for å vise html i Gitpod).
    - `mvn checkstyle:checkstyle` - rapporterer formateringsfeil i koden.
    - `mvn spotbugs:spotbugs` - rapporterer bugs i koden.
    - `mvn verify` - kjører alle de overnevnte vektøyene samtidig.

## Rutiner for arbeidsflyt og kodekvalitet

Dokumentasjon for arbeidsflyt, rutiner, møter og kodekvalitet er å finne i vår Gitlab-wiki:

### [Wiki](https://gitlab.stud.idi.ntnu.no/it1901/groups-2021/gr2118/gr2118/-/wikis/home)
- [Sprint workflow](https://gitlab.stud.idi.ntnu.no/it1901/groups-2021/gr2118/gr2118/-/wikis/Sprint-workflow)
- [Møter](https://gitlab.stud.idi.ntnu.no/it1901/groups-2021/gr2118/gr2118/-/wikis/M%C3%B8ter)
- [Issues](https://gitlab.stud.idi.ntnu.no/it1901/groups-2021/gr2118/gr2118/-/wikis/Issues)
- [Branches](https://gitlab.stud.idi.ntnu.no/it1901/groups-2021/gr2118/gr2118/-/wikis/Branches)
- [Kodekvalitet](https://gitlab.stud.idi.ntnu.no/it1901/groups-2021/gr2118/gr2118/-/wikis/Kodekvalitet)
- [Merge requests](https://gitlab.stud.idi.ntnu.no/it1901/groups-2021/gr2118/gr2118/-/wikis/Merge-requests)
- [Code review](https://gitlab.stud.idi.ntnu.no/it1901/groups-2021/gr2118/gr2118/-/wikis/Code-review)
- [Filformat for lagring](https://gitlab.stud.idi.ntnu.no/it1901/groups-2021/gr2118/gr2118/-/wikis/Filformat-for-lagring)

## Designskisse

![](img/sultn-design.png)
