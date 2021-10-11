```
_______   _____ ___ _____ __   __ ___ _____ _  _ ___ ___  
|_   _\ `v' / _,\ __|_   _/__\ / _] __|_   _| || | __| _ \ 
  | |  `. .'| v_/ _|  | || \/ | [/\ _|  | | | >< | _|| v / 
  |_|   !_! |_| |___| |_| \__/ \__/___| |_| |_||_|___|_|_\ 
```

Zum Starten in das Zielverzeichnis navigieren und einmalig 
```
mvn clean install
```
ausführen - danach kann der Server mit
```
.\server.ps1
```
und der Client mit
```
.\client.ps1
```
gestartet werden.

Die Commands innerhalb dieser Skripte sind Plattformunabhängig und können auch außerhalb der Skripte ausgeführt werden. Hierfür sollte das Setzen der Umgebungsvariablen mithilfe der Skriptvariablen jedoch zuvor entfernt oder durch konkrete Werten ersetzt werden:

```sh
java -jar .\server\target\server-0.0.1-SNAPSHOT.jar
java -jar .\client\target\client-0.0.1-SNAPSHOT.jar
```
