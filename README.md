```
_______   _____ ___ _____ __   __ ___ _____ _  _ ___ ___  
|_   _\ `v' / _,\ __|_   _/__\ / _] __|_   _| || | __| _ \ 
  | |  `. .'| v_/ _|  | || \/ | [/\ _|  | | | >< | _|| v / 
  |_|   !_! |_| |___| |_| \__/ \__/___| |_| |_||_|___|_|_\ 
```
![CI](https://github.com/SoftwarearchitekturTeam/TypeTogether/actions/workflows/ci.yml/badge.svg)

In order to start, navigate to the target directory and build the project using
```
mvn clean install
```

After the build was successfull, the server can be started by calling the PowerShell script
```
.\server.ps1
```

The client can be started by calling the PowerShell script
```
.\client.ps1
```

The commands itself are platform independent and can be manually executed. The simplest way to start a server on the default port (12557) and to start a client that connects to said server on localhost:12557 can be done using the following commands:
```sh
java -jar .\server\target\server-0.0.1-SNAPSHOT.jar
java -jar .\client\target\client-0.0.1-SNAPSHOT.jar
```

In the server, the `port` and the `loglevel` environment variables can be set.
In the client, the `url` of the server, the server `port` and the `loglevel` environment variables can be set.