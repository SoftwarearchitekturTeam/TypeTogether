param(
    [string]$port = '12557',
    [string]$loglevel = 'FINEST'
)
& mvn clean install -DskipTests
& java '-Djava.util.logging.SimpleFormatter.format=%1$tY-%1$tm-%1$td %1$tH:%1$tM:%1$tS %4$-6s %2$-95s %5$s%6$s%n' "-Dport=$port" "-Dloglevel=$loglevel" -jar .\server\target\server-0.0.1-SNAPSHOT.jar