set RepositoryAddress=127.0.0.1
set DepartureAirportAddress=127.0.0.1
set PlaneAddress=127.0.0.1
set ArrivalAirportAddress=127.0.0.1
set RepositoryPort=10001
set DepartureAirportPort=10002
set PlaneAirportPort=10003
set ArrivalAirportPort=10004

set NumberPassengers=21
set PlaneMinCapacity=5
set PlaneMaxCapacity=10

javac Servers/Repository/Initializer.java
java Servers/Repository/Initializer %RepositoryAddress% %RepositoryPort%

javac Servers/DepartureAirport/Initializer.java
java Servers/DepartureAirport/Initializer %DepartureAirportAddress% %DepartureAirportPort% %NumberPassengers% %PlaneMinCapacity% %PlaneMaxCapacity%

javac Servers/Plane/Initializer.java
java Servers/Plane/Initializer %PlaneAddress% %PlaneAirportPort%

javac Servers/ArrivalAirport/Initializer.java
java Servers/ArrivalAirport/Initializer %ArrivalAirportAddress% %ArrivalAirportPort%

javac Clients/Hostess/Initializer.java
java Clients/Hostess/Initializer

javac Clients/Pilot/Initializer.java
java Clients/Pilot/Initializer

javac Clients/Passenger/Initializer.java
FOR /L %%A IN (1, 1, %NumberPassengers%) DO (
    java Clients/Passenger/Initializer %A%
)

pause
del /s *.class