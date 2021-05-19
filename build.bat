set RepositoryAddress=127.0.0.1
set DepartureAirportAddress=127.0.0.1
set PlaneAddress=127.0.0.1
set ArrivalAirportAddress=127.0.0.1
set RepositoryPort=10001
set DepartureAirportPort=10002
set PlanePort=10003
set ArrivalAirportPort=10004

set NumberPassengers=21
set PlaneMinCapacity=5
set PlaneMaxCapacity=10

cd Servers/Repository/
javac src/Initializer.java
java -cp lib/* src/Initializer %RepositoryPort% %NumberPassengers% 
cd ../../

cd Servers/DepartureAirport/
javac src/Initializer.java
java -cp lib/* src/Initializer %DepartureAirportPort% %NumberPassengers% %PlaneMinCapacity% %PlaneMaxCapacity% %RepositoryAddress% %RepositoryPort%
cd ../../

cd Servers/Plane/
javac src/Initializer.java
java -cp lib/* src/Initializer %PlanePort% %RepositoryAddress% %RepositoryPort%
cd ../../

cd Servers/ArrivalAirport/
javac src/Initializer.java
java -cp lib/* src/Initializer %ArrivalAirportPort% %RepositoryAddress% %RepositoryPort%
cd ../../

cd Clients/Hostess/
javac src/Initializer.java
java -cp lib/* src/Initializer %DepartureAirportAddress% %DepartureAirportPort% %PlaneAddress% %PlanePort%
cd ../../

cd Clients/Pilot/
javac src/Initializer.java
java -cp lib/* src/Initializer %DepartureAirportAddress% %DepartureAirportPort% %PlaneAddress% %PlanePort% %ArrivalAirportAddress% %ArrivalAirportPort%
cd ../../

cd Clients/Passenger/
javac src/Initializer.java
FOR /L %%A IN (1, 1, %NumberPassengers%) DO (
    java -cp lib/* src/Initializer %A% %DepartureAirportAddress% %DepartureAirportPort% %PlaneAddress% %PlanePort% %ArrivalAirportAddress% %ArrivalAirportPort%
)
cd ../../

pause
del /s *.class