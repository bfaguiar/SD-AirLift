set RegistryAddress=l040101-ws02.ua.pt
set RepositoryAddress=l040101-ws03.ua.pt
set DepartureAirportAddress=l040101-ws04.ua.pt
set PlaneAddress=l040101-ws05.ua.pt
set ArrivalAirportAddress=l040101-ws06.ua.pt
set HostessAddress=l040101-ws07.ua.pt
set PilotAddress=l040101-ws08.ua.pt
set PassengerAddress1=l040101-ws09.ua.pt
set PassengerAddress2=l040101-ws10.ua.pt

set sshlogin=sd404

cd Registry/src
javac -target 1.8 -cp .;../lib/genclass.jar interfaces/* main/*
jar cf Registry.jar *
move Registry.jar bin/
cd ../../

cd Servers/Repository/src
javac -target 1.8 -cp .;../lib/genclass.jar main/Initializer.java
jar cf Repository.jar *
move Repository.jar bin/
cd ../../../

cd Servers/DepartureAirport/src
javac -target 1.8 -cp .;../lib/genclass.jar main/Initializer.java
jar cf DepartureAirport.jar *
move DepartureAirport.jar bin/
cd ../../../

cd Servers/Plane/src
javac -target 1.8 -cp .;../lib/genclass.jar main/Initializer.java
jar cf Plane.jar *
move Plane.jar bin/
cd ../../../

cd Servers/ArrivalAirport/src
javac -target 1.8 -cp .;../lib/genclass.jar main/Initializer.java
jar cf ArrivalAirport.jar *
move ArrivalAirport.jar bin/
cd ../../../

cd Clients/Hostess/src/
javac -target 1.8 -cp .;../lib/genclass.jar main/Initializer.java
jar cf Hostess.jar *
move Hostess.jar bin/
cd ../../../

cd Clients/Pilot/src
javac -target 1.8 -cp .;../lib/genclass.jar main/Initializer.java
jar cf Pilot.jar *
move Pilot.jar bin/
cd ../../../

cd Clients/Passenger/src
javac -target 1.8 -cp .;../lib/genclass.jar main/Initializer.java
jar cf Passenger.jar *
move Passenger.jar bin/
cd ../../../

scp -r Registry/src/bin %sshlogin%@%RegistryAddress%:/home/sd404/Registry/
scp -r Servers/Repository/src/bin %sshlogin%@%RepositoryAddress%:/home/sd404/Repository/
scp -r Servers/DepartureAirport/src/bin %sshlogin%@%DepartureAirportAddress%:/home/sd404/DepartureAirport/
scp -r Servers/Plane/src/bin %sshlogin%@%PlaneAddress%:/home/sd404/Plane/
scp -r Servers/ArrivalAirport/src/bin %sshlogin%@%ArrivalAirportAddress%:/home/sd404/ArrivalAirport/
scp -r Clients/Hostess/src/bin %sshlogin%@%HostessAddress%:/home/sd404/Hostess/
scp -r Clients/Pilot/src/bin %sshlogin%@%PilotAddress%:/home/sd404/Pilot/
scp -r Clients/Passenger/src/bin %sshlogin%@%PassengerAddress1%:/home/sd404/Passenger/
scp -r Clients/Passenger/src/bin %sshlogin%@%PassengerAddress2%:/home/sd404/Passenger/