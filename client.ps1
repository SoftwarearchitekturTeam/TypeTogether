param(
    [string]$url = 'localhost',
    [string]$port = '12577'
)
& java "-Durl=$url" "-Dport=$port" -jar .\client\target\client-0.0.1-SNAPSHOT.jar
