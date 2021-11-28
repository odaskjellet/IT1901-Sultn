# Fullførte brukerhistorier

## REST-API #55
_Som bruker ønsker jeg at mine oppskrifter lagres i en skytjeneste istedenfor lokalt på min maskin._

### Deloppgaver:
- REST-server som håndterer lagring ved hjelp av persistenslaget.
- REST-API som aksesserer REST-serveren, og brukes ved hjelp av HTTP.
- Knytt sammen UI med REST ved å ha en aksess-klasse som håndterer HTTP requests og response.
- Tester for REST-API.

## Endre oppskrifter #51
_Som bruker ønsker jeg å kunne endre på en eksisterende oppskrift i min kokebok. Alle parametere som jeg brukte for å lage oppskriften skal kunne redigeres ved å klikke på en knapp inne på kokeboken som tar meg til et skjema._

### Deloppgaver:
- Ny kontroller, EditController, med tilhørende FXML som lar brukeren endre på en oppskrift.
- Støtte for endring i Core og REST.
- UI-tester for EditController.

Denne brukerhistorien er bare delvis implementert, ettersom for ingredienser går det kun an å legge til nye ingredienser, men ikke slette gamle.

## Legge til ikoner i oppskrifter #54
_Som bruker ønsker jeg å kunne se ikoner ved siden av oppskriftene mine, både på hjemskjermen og inne på oppskriften. Denne skal jeg kunne legge til selv når jeg oppretter en oppskrift, og hjelper med med å lete i mine egne oppskrifter._

### Deloppgaver:
- Designer ikoner.
- IconPicker-klasse.
- Legge til i SultnFormController og EditController.

## Flere ulike kokebøker #69
_Som bruker vil jeg kunne velge mellom å åpne en allerede opprettet kokebok eller opprette en ny kokebok når appen starter._

- Ny kontroller og FXML, CookbookSelectorController, som lar brukeren velge mellom å lage ny eller hente eksisterende kokebok.
- Funksjonalitet på REST for å hente alle oppskrifter for sjekke eksistens av kokebok.
- Funksjonalitet på REST for å ha flere kokebøker med ulike navn.
