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

cd Servers/Repository/src
javac -cp .;../lib/genclass.jar Initializer.java
start cmd /c java Initializer %RepositoryPort% %NumberPassengers%
cd ../../../

cd Servers/DepartureAirport/src
javac -cp .;../lib/genclass.jar Initializer.java
start cmd /c java Initializer %DepartureAirportPort% %NumberPassengers% %PlaneMinCapacity% %PlaneMaxCapacity% %RepositoryAddress% %RepositoryPort%
cd ../../../

cd Servers/Plane/src
javac -cp .;../lib/genclass.jar Initializer.java
start cmd /c java Initializer %PlanePort% %RepositoryAddress% %RepositoryPort%
cd ../../../

cd Servers/ArrivalAirport/src
javac -cp .;../lib/genclass.jar Initializer.java
start cmd /c java Initializer %ArrivalAirportPort% %RepositoryAddress% %RepositoryPort%
cd ../../../

cd Clients/Hostess/src/
javac -cp .;../lib/genclass.jar Initializer.java
start cmd /c java Initializer %DepartureAirportAddress% %DepartureAirportPort% %PlaneAddress% %PlanePort%
cd ../../../

cd Clients/Pilot/src
javac -cp .;../lib/genclass.jar Initializer.java
start cmd /c java Initializer %DepartureAirportAddress% %DepartureAirportPort% %PlaneAddress% %PlanePort% %ArrivalAirportAddress% %ArrivalAirportPort%
cd ../../../

cd Clients/Passenger/src
javac -cp .;../lib/genclass.jar Initializer.java
FOR /L %%A IN (1, 1, %NumberPassengers%) DO (
    start cmd /c java Initializer %A% %DepartureAirportAddress% %DepartureAirportPort% %PlaneAddress% %PlanePort% %ArrivalAirportAddress% %ArrivalAirportPort%
)
cd ../../../

pause
del /s *.class