# Design Patterns Individual (DPI) - I40 // Kino

**Used Design Patterns**: Proxy, Chain of Responsibility, Observer

Ein Kino ist unterteilt in drei Blöcke
1. **links**: 50 Reihen mit je 25 Sitzplätzen
2. **mitte**: 50 Reihen mit je 40 Sitzplätzen und
3. **rechts**: 50 Reihen mit je 25 Sitzplätzen.

Die Reihen sind in Abschnitte beginnend von vorne eingeteilt:
1. A: 1-5,
2. B: 6-15,
3. C: 16-25,
4. D: 26-45 und 
5. E: 46 – 50.

Es existieren zwei Typen von Kunden
1. normal und
2. online.

Kunden vom Typ **normal** kaufen ein Ticket zum Normalpreis von 10 Euro an der Abendkasse.
Die Abendkasse hat zwei Schalter
1. Einzelperson, Gruppe mit 2 Personen und
2. Online, Gruppe mit mindestens 3 Personen.

Grundsätzlich werden Gruppen nicht aufgeteilt, sondern sitzen nebeneinander. Die Einzelperson oder
Gruppe nennt Ihre Präferenz bezüglich Block und Abschnitt. Sofern die Präferenz nicht erfüllt werden
kann, wird ein anderer Abschnitt im präferierten Block ausgewählt und zugeteilt. Ist der Block ausgebucht,
wird ein anderer Block angeboten. Mit einer Wahrscheinlichkeit von 70% stimmt der Kunde dem Angebot zu.
Wird das Angebot abgelehnt, verlässt der Kunde das Kino. Registrierte Kunden vom Typ **online** buchen
über ein Portal (Proxy). Online-Buchungen sind bis zum Öffnen der Abendkasse möglich. Beim Online-Ticket
existieren drei Typen
1. Standard (9 Euro, Kunde wählt Sitzplatz aus)
2. Hot Deal (8 Euro, Kunde wählt Block aus, zufällig freier Sitzplatz im selektierten Block) und
3. Surprise (5 Euro, zufällig freier Sitzplatz).

Die Abendkasse schließt 10 Minuten vor dem Beginn der Vorstellung. Zu Simulationszwecken wird alle 200 ms
zufällig eine Einzelperson, Gruppe mit 2 Personen, Gruppe mit 3 Personen oder Gruppe mit 4 Personen generiert.
Die Simulation endet, wenn
1. alle Plätze belegt sind oder
2. die Plätze zu 95% belegt sind und zum dritten Mal ein Kunde das Angebot abgelehnt hat.
