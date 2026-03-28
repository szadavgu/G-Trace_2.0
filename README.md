# G-Trace 2.0 - High-Performance Telemetry System 🏎️

A **G-Trace 2.0** egy professzionális szintű telemetriai alkalmazás, amely a motorsportok világából ismert G-G diagramot hozza el az Android készülékekre. Ez a verzió teljes dizájn- és logikai frissítésen esett át.



## 🌟 Kiemelt Funkciók
- **Dinamikus G-G Diagram:** Egyedi fejlesztésű `GForceView` komponens, amely valós időben rajzolja ki a nehézségi erőket egy Canvas-alapú koordináta-rendszerben.
- **Tehetetlenségi Nyomvonal:** A pont mozgása után hagyott "szellemkép" segít vizualizálni a gyorsulási és kanyarodási vektorokat.
- **Paddock History:** SQLite adatbázis-alapú naplózás, amely rögzíti a méréseket.
- **Napi Top 10:** Automatikus rangsorolás a legjobb eredményekhez.
- **23:59 Reset Protokoll:** Intelligens adattörlési logika, amely minden nap éjfélkor tiszta lappal (Daily Challenge) indítja a rendszert.

## 🛠 Fejlesztői Megoldások
- **Custom View Drawing:** Közvetlen manipuláció az Android Canvas API-val a nagy sebességű vizualizációért.
- **SQLite Database:** Strukturált adattárolás komplex lekérdezésekkel (ORDER BY, LIMIT).
- **Material Dark Design:** Versenyautó-műszerfal ihlette sötét kezelőfelület a jobb olvashatóságért és az alacsonyabb akkumulátor-használatért.

## 📱 Használat
1. Indítsd el a **Live Telemetry**-t a főmenüben.
2. Helyezd a telefont stabilan a járműbe.
3. A mérés végén a **Stop Session** gombbal rögzítsd az eredményt.
4. Tekintsd meg a napi rekordokat a **History** menüpontban.

## 👨‍💻 Szerző
**szadavgu** - *Minden jog fenntartva*
