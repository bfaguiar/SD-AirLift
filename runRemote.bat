set RepositoryAddress=l040101-ws01.ua.pt
set RepositoryPort=40401

set DepartureAirportAddress=l040101-ws02.ua.pt
set DepartureAirportPort=40402

set PlaneAddress=l040101-ws03.ua.pt
set PlanePort=40403

set ArrivalAirportAddress=l040101-ws04.ua.pt
set ArrivalAirportPort=40404

set HostessAddress=l040101-ws05.ua.pt
set PilotAddress=l040101-ws06.ua.pt
set PassengerAddress1=l040101-ws07.ua.pt
set PassengerAddress2=l040101-ws08.ua.pt
set PassengerAddress3=l040101-ws09.ua.pt
set PassengerAddress4=l040101-ws10.ua.pt

set sshlogin=sd404
set sshpassword=avioesp4

set NumberPassengers=21
set MaxPassengerIndex=20
set PlaneMinCapacity=5
set PlaneMaxCapacity=10

plink -batch %sshlogin%@%RepositoryAddress% -pw %sshpassword% "cd Repository/src && javac -cp .:../lib/genclass.jar main/Initializer.java"
plink -batch %sshlogin%@%DepartureAirportAddress% -pw %sshpassword% "cd DepartureAirport/src && javac -cp .:../lib/genclass.jar main/Initializer.java"
plink -batch %sshlogin%@%PlaneAddress% -pw %sshpassword% "cd Plane/src && javac -cp .:../lib/genclass.jar main/Initializer.java"
plink -batch %sshlogin%@%ArrivalAirportAddress% -pw %sshpassword% "cd ArrivalAirport/src && javac -cp .:../lib/genclass.jar main/Initializer.java"
plink -batch %sshlogin%@%HostessAddress% -pw %sshpassword% "cd Hostess/src/ && javac -cp .:../lib/genclass.jar main/Initializer.java"
plink -batch %sshlogin%@%PilotAddress% -pw %sshpassword% "cd Pilot/src && javac -cp .:../lib/genclass.jar main/Initializer.java"
plink -batch %sshlogin%@%PassengerAddress1% -pw %sshpassword% "cd Passenger/src && javac -cp .:../lib/genclass.jar main/Initializer.java"
plink -batch %sshlogin%@%PassengerAddress2% -pw %sshpassword% "cd Passenger/src && javac -cp .:../lib/genclass.jar main/Initializer.java"
plink -batch %sshlogin%@%PassengerAddress3% -pw %sshpassword% "cd Passenger/src && javac -cp .:../lib/genclass.jar main/Initializer.java"
plink -batch %sshlogin%@%PassengerAddress4% -pw %sshpassword% "cd Passenger/src && javac -cp .:../lib/genclass.jar main/Initializer.java"

start "Repository" cmd /c plink -batch %sshlogin%@%RepositoryAddress% -pw %sshpassword% "cd Repository/src && java -cp .:../lib/genclass.jar main/Initializer %RepositoryPort% %NumberPassengers%" ^& pause
start "DepartureAirport" cmd /c plink -batch %sshlogin%@%DepartureAirportAddress% -pw %sshpassword% "cd DepartureAirport/src && java -cp .:../lib/genclass.jar main/Initializer %DepartureAirportPort% %NumberPassengers% %PlaneMinCapacity% %PlaneMaxCapacity% %RepositoryAddress% %RepositoryPort%" ^& pause
start "Plane" cmd /c plink -batch %sshlogin%@%PlaneAddress% -pw %sshpassword% "cd Plane/src && java -cp .:../lib/genclass.jar main/Initializer %PlanePort% %RepositoryAddress% %RepositoryPort%" ^& pause
start "ArrivalAirport" cmd /c plink -batch %sshlogin%@%ArrivalAirportAddress% -pw %sshpassword% "cd ArrivalAirport/src && java -cp .:../lib/genclass.jar main/Initializer %ArrivalAirportPort% %RepositoryAddress% %RepositoryPort%" ^& pause

timeout /t 4

start "Hostess" cmd /c plink -batch %sshlogin%@%HostessAddress% -pw %sshpassword% "cd Hostess/src/ ; java -cp .:../lib/genclass.jar main/Initializer %DepartureAirportAddress% %DepartureAirportPort% %PlaneAddress% %PlanePort%" ^& pause
start "Pilot" cmd /c plink -batch %sshlogin%@%PilotAddress% -pw %sshpassword% "cd Pilot/src && java -cp .:../lib/genclass.jar main/Initializer %DepartureAirportAddress% %DepartureAirportPort% %PlaneAddress% %PlanePort% %ArrivalAirportAddress% %ArrivalAirportPort%" ^& pause
FOR /L %%A IN (0, 1, 4) DO (
    start "Passenger %%A" cmd /c plink -batch %sshlogin%@%PassengerAddress1% -pw %sshpassword% "cd Passenger/src && java -cp .:../lib/genclass.jar main/Initializer %%A %DepartureAirportAddress% %DepartureAirportPort% %PlaneAddress% %PlanePort% %ArrivalAirportAddress% %ArrivalAirportPort%" ^& pause
)
FOR /L %%A IN (5, 1, 9) DO (
    start "Passenger %%A" cmd /c plink -batch %sshlogin%@%PassengerAddress2% -pw %sshpassword% "cd Passenger/src && java -cp .:../lib/genclass.jar main/Initializer %%A %DepartureAirportAddress% %DepartureAirportPort% %PlaneAddress% %PlanePort% %ArrivalAirportAddress% %ArrivalAirportPort%" ^& pause
)
FOR /L %%A IN (10, 1, 14) DO (
    start "Passenger %%A" cmd /c plink -batch %sshlogin%@%PassengerAddress3% -pw %sshpassword% "cd Passenger/src && java -cp .:../lib/genclass.jar main/Initializer %%A %DepartureAirportAddress% %DepartureAirportPort% %PlaneAddress% %PlanePort% %ArrivalAirportAddress% %ArrivalAirportPort%" ^& pause
)
FOR /L %%A IN (15, 1, 20) DO (
    start "Passenger %%A" cmd /c plink -batch %sshlogin%@%PassengerAddress4% -pw %sshpassword% "cd Passenger/src && java -cp .:../lib/genclass.jar main/Initializer %%A %DepartureAirportAddress% %DepartureAirportPort% %PlaneAddress% %PlanePort% %ArrivalAirportAddress% %ArrivalAirportPort%" ^& pause
)