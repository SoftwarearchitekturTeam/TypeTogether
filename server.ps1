param(
    [string]$port = '12557',
    [string]$loglevel = 'FINEST'
)
& mvn clean install -DskipTests
& java "-Dport=$port" "-Dloglevel=$loglevel" -jar .\server\target\server-0.0.1-SNAPSHOT.jar