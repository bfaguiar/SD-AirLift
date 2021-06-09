set RepositoryAddress=l040101-ws01.ua.pt
set DepartureAirportAddress=l040101-ws02.ua.pt
set PlaneAddress=l040101-ws03.ua.pt
set ArrivalAirportAddress=l040101-ws04.ua.pt
set HostessAddress=l040101-ws05.ua.pt
set PilotAddress=l040101-ws06.ua.pt
set PassengerAddress1=l040101-ws07.ua.pt
set PassengerAddress2=l040101-ws08.ua.pt
set PassengerAddress3=l040101-ws09.ua.pt
set PassengerAddress4=l040101-ws10.ua.pt

set sshlogin=sd404

scp -r Servers/Repository %sshlogin%@%RepositoryAddress%:/home/sd404/
scp -r Servers/DepartureAirport %sshlogin%@%DepartureAirportAddress%:/home/sd404/
scp -r Servers/Plane %sshlogin%@%PlaneAddress%:/home/sd404/
scp -r Servers/ArrivalAirport %sshlogin%@%ArrivalAirportAddress%:/home/sd404/

scp -r Clients/Hostess %sshlogin%@%HostessAddress%:/home/sd404/
scp -r Clients/Pilot %sshlogin%@%PilotAddress%:/home/sd404/
scp -r Clients/Passenger %sshlogin%@%PassengerAddress1%:/home/sd404/
scp -r Clients/Passenger %sshlogin%@%PassengerAddress2%:/home/sd404/
scp -r Clients/Passenger %sshlogin%@%PassengerAddress3%:/home/sd404/
scp -r Clients/Passenger %sshlogin%@%PassengerAddress4%:/home/sd404/