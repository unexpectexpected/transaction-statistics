# transaction-statistics

A restful API for our statistics. The main use case for our API is to
calculate real-time statistics from the last 60 seconds. There are two APIs, one of them is
called every time a transaction is made. It is also the sole input of this rest API. The other one
returns the statistic based of the transactions of the last 60 seconds.

##maven spring boot 
Project is a spring-boot application
to run application one might use following maven command:
mvn -pl transactions-rest-api spring-boot:run 