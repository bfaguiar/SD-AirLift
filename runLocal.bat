set RepositoryAddress=127.0.0.1
set DepartureAirportAddress=127.0.0.1
set PlaneAddress=127.0.0.1
set ArrivalAirportAddress=127.0.0.1
set RepositoryPort=10001
set DepartureAirportPort=10002
set PlanePort=10003
set ArrivalAirportPort=10004

set NumberPassengers=21
set MaxPassengerIndex=20
set PlaneMinCapacity=5
set PlaneMaxCapacity=10

cd Servers/Repository/src
javac -cp .;../lib/genclass.jar main/Initializer.java
start "Repository" cmd /c java main/Initializer %RepositoryPort% %NumberPassengers% ^& pause
cd ../../../

cd Servers/DepartureAirport/src
javac -cp .;../lib/genclass.jar main/Initializer.java
start "DepartureAirport" cmd /c java main/Initializer %DepartureAirportPort% %NumberPassengers% %PlaneMinCapacity% %PlaneMaxCapacity% %RepositoryAddress% %RepositoryPort% ^& pause
cd ../../../

cd Servers/Plane/src
javac -cp .;../lib/genclass.jar main/Initializer.java
start "Plane" cmd /c java main/Initializer %PlanePort% %RepositoryAddress% %RepositoryPort% ^& pause
cd ../../../

cd Servers/ArrivalAirport/src
javac -cp .;../lib/genclass.jar main/Initializer.java
start "ArrivalAirport" cmd /c java main/Initializer %ArrivalAirportPort% %RepositoryAddress% %RepositoryPort% ^& pause
cd ../../../

cd Clients/Hostess/src/
javac -cp .;../lib/genclass.jar main/Initializer.java
start "Hostess" cmd /c java main/Initializer %DepartureAirportAddress% %DepartureAirportPort% %PlaneAddress% %PlanePort% ^& pause
cd ../../../

cd Clients/Pilot/src
javac -cp .;../lib/genclass.jar main/Initializer.java
start "Pilot" cmd /c java main/Initializer %DepartureAirportAddress% %DepartureAirportPort% %PlaneAddress% %PlanePort% %ArrivalAirportAddress% %ArrivalAirportPort% ^& pause
cd ../../../

cd Clients/Passenger/src
javac -cp .;../lib/genclass.jar main/Initializer.java
FOR /L %%A IN (0, 1, %MaxPassengerIndex%) DO (
    start "Passenger %%A" cmd /c java main/Initializer %%A %DepartureAirportAddress% %DepartureAirportPort% %PlaneAddress% %PlanePort% %ArrivalAirportAddress% %ArrivalAirportPort% ^& pause
)
cd ../../../

pause
del /s *.class