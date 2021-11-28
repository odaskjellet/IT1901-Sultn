[![Gitpod Ready-to-Code](https://img.shields.io/badge/Gitpod-Ready--to--Code-blue?logo=gitpod)](https://gitpod.stud.ntnu.no/#https://gitlab.stud.idi.ntnu.no/it1901/groups-2021/gr2118/gr2118)

![](img/sultn-logo.png)

# SULTN

**SULTN** er en personlig samling med digital kokebøker. Her kan du lage flere kokebøker med flere oppskrifter. Du kan gi oppskriftene dine navn, lage en ingrediensliste og skrive instruksjoner for hvordan retten lages. Oppskriften lagres automatisk i din kokebok og kan hentes opp neste gang du er på kjøkkene.

Per nå har Sultn følgende funksjonalitet:
- Laste inn en default kokebok eller lage din egen kokebok.
- Se en oversiktlig liste over alle oppskrifter på hjemskjermen.
- Legge til ny oppskrift gjennom et skjema.
- Klikke inn på hver oppskrift for å se instruksjoner og ingredienser.
- Slette oppskrifter.
- Endre på innholdet til lagrede oppskrifter.

## Hvordan SULTN fungerer
### Velge kokebok
Når applikasjonen åpnes tas man til en forside der man kan laste inn en tidligere kokebok eller velge å lage en ny som inneholder default oppskrifter. For å laste inn en tidligere kokebok trykker man på "Load an existing cookbook" og skriver inn navnet på en tidligere kokebok. For å lage en ny kokebok trykker man på "Create a new cookbook" og skriver inn navnet man vil ha på den nye kokeboka.  

### Åpne oppskrift
Etter å ha valgt kokebok tas man til kokebokens oversikt over lagrede oppskrifter. Herfra kan man åpne tilgjengelige oppskrifter ved å klikke "Open" ved siden av navnet på retten som tar deg til oppskriftens ingredienser og instruksjoner.

### Ny oppskrift
For å legge til ny oppskrift klikker man på "Add Recipe" på hjemskjermen. Man blir tatt videre til et skjema som fylles ut med rettens navn, kategori/ikon, ingredienser og instruksjoner. Ingredienser legges til én og én, hvor "Amount" er et tall og "Unit" er tekst som signaliserer enhet, f.eks. "stk", "liter", "kg". Instruksjoner skrives inn linje for linje ved å bruke "Add instruction knappen". Kategori velges via en liste med ikoner som symboliserer hver sin kategori. Hvis man ikke velger en, så settes kategorien og ikonet til en default verdi. Når man har lagt til kategori/ikon, alle ingredienser og alle instruksjoner, klikker man "Add Recipe" og man får en pop-up bekrefter at man har lagt til en ny oppskrift. Skjemaet tømmes og man kan legge til en til ny oppskrift. Går man tilbake til hjemskjermen vil man se at de nye oppskriftene som har blitt oprettet er lagt til i oversikten over oppskrifter. 

### Slette oppskrift
Inne på en oppskrift kan man trykke på "Delete recipe" for å slette en oppskrift. En dialogboks bekrefter at oppskriften er slettet, og man blir automatisk sendt tilbake til hjemskjermen der oppskriften ikke lenger skal være i oversikten.

### Endre på oppskrift
Inne på en oppskrift kan man trykke på "Edit recipe" for å endre på en oppskrift. Man blir tatt videre til et ferdig utfylt skjema med oppskriftens navn, ikon, ingredienser og instruksjoner. Her kan man endre på rettens navn og kategori. Man kan også endre, legge til eller slette instruksjoner. Videre kan man også legge til ekstra ingredienser, men funksjonalitet for å endre eller slette ingredienser er ennå ikke lagt til. Når man har endret på det man vil, trykker man på knappen "Save recipe". Deretter vil en dialogboks dukke opp å bekrefte at oppskriften er endret på, så blir man automatisk sendt tilbake til en oppdatert side av oppskriften.

<div>
    <!-- <img src="/img/home-screen.png" alt="Home" height="400">
    <img src="/img/new-recipe-form.png" alt="New recipe" height="400">
    <img src="/img/example-recipe.png" alt="Example recipe" height="400"> -->
    <img src ="/img/SultnCookbookSelector.JPG" alt="Start screen" height="400">
    <img src ="/img/SultnRecipeLIst.JPG" alt="Example recipelist" height="400">
    <img src ="/img/SultnRecipeView.JPG" alt="Example recipe" height="400">
    <img src ="/img/SultnAddNewRecipe.JPG" alt="Example recipe" height="400">
</div>

## Bygging og kjøring av prosjektet

Prosjektet er skrevet i Java ved bruk av [JavaFX](https://openjfx.io/). Koden bygges, kjøres og testes med [Maven](https://maven.apache.org/).

- Kodelageret og riktig utgivelse ligger på greinen ***master***.
- Klon repoet lokalt eller kjør det med bruk av Gitpod (bruk Gitpod-knappen øverst).
- Fra mappen `gr2118/sultn` kjøres først `mvn clean install`.
- Start to terminaler for å kunne kjøre springserven separat fra UI. `cd` inn i `gr2118/sultn/springboot` og kjør `mvn spring-boot:run`
- I den andre terminalen, `cd` inn i `gr2118/sultn/ui` og kjør `mvn javafx:run`. Dette starter brukergrensesnittet.
- Andre kommandoer som kan kjøres enten inni en enkelt modul (`core`, `json`, `springboot` eller `ui`) eller globalt i `gr2118/sultn`:
    - `mvn test` - kjører tilgjengelige tester.
    - `mvn jacoco:report` - rapporterer testdekningsgrad samlet i modulen [jacoco](https://gitlab.stud.idi.ntnu.no/it1901/groups-2021/gr2118/gr2118/-/tree/sprint3/sultn/jacoco) sin `target/site/index.html` og lokalt i hver av moduelenes `target/site/index.html` (benytt tillegget [Live Server](https://marketplace.visualstudio.com/items?itemName=ritwickdey.LiveServer) for å vise html i Gitpod).
    - `mvn checkstyle:checkstyle` - rapporterer formateringsfeil i koden.
    - `mvn spotbugs:spotbugs` - rapporterer bugs i koden.
    - `mvn verify` - kjører alle de overnevnte vektøyene samtidig.

### Testing
Testing av prosjektet er gjort ved bruk av Junit tester og en integrasjonstest. Springboot modulen sin test er prosjektets integrasjonstest i tillegg til å fungere som en utvidet junit test for rest apiet. 
Testene i UI modulen er avhengig av at en server kjører i bakgrunnen for at alle testene skal gå gjennom.

## Shippable produkt

Ved hjelp av tilleggene jlink og jpackage er prosjektet gjort om til et shippable produkt. Produktet har ikke lokal lagring og avhenger av at man starter serveren manuelt.

- For å gjøre produktet shippable kjøres `mvn clean compile javafx:jlink jpackage:jpackage` i ui mappa.
- For å starte serveren cd inn i springboot mappa og kjør `mvn spring-boot:run`
- Man kan gå i .pom-filen til UI og endre hvilket OS man shipper til (UNIX/Windows).

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

## Organisering
Dette prosjektet er en applikasjon som bruker en trelagsarkitektur med domene, brukergrensesnitt (UI) og persistenslag (lagring).

- **core-modul:** `sultn/core/src/main/java` for klasser og logikk.
- **ui-modul:** `sultn/ui/src/main/java` for UI og ressurser tilhørende dette.
- **json-modul:** `sultn/json/src/main/java` for persistenshåndtering.

REST-klient ligger også i en egen modul.
- **spring-boot:** `sultn/springboot/src/main/java`

### PUML-diagrammer

#### Pakkediagram (klikk for større bilde)
<img src="/img/diagrams/packagediagram.png" alt="Package diagram" width="300">

#### Sekvensdiagram (klikk for større bilde)
<img src="/img/diagrams/sequencediagram.png" alt="Sequence diagram" width="300">

## Designskisse

![](img/sultn-design.png)
