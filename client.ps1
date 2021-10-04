param(
    [string]$url = 'localhost',
    [string]$port = '12557',
    [string]$loglevel = 'FINEST'
)
& java "-Durl=$url" "-Dport=$port" "-Dloglevel=$loglevel" -jar .\client\target\client-0.0.1-SNAPSHOT.jar
