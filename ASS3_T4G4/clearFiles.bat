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
set sshpassword=avioesp4

plink -batch %sshlogin%@%RegistryAddress% -pw %sshpassword% "rm -rf Registry"
plink -batch %sshlogin%@%RepositoryAddress% -pw %sshpassword% "rm -rf Repository"
plink -batch %sshlogin%@%DepartureAirportAddress% -pw %sshpassword% "rm -rf DepartureAirport"
plink -batch %sshlogin%@%PlaneAddress% -pw %sshpassword% "rm -rf Plane"
plink -batch %sshlogin%@%ArrivalAirportAddress% -pw %sshpassword% "rm -rf ArrivalAirport"
plink -batch %sshlogin%@%HostessAddress% -pw %sshpassword% "rm -rf Hostess"
plink -batch %sshlogin%@%PilotAddress% -pw %sshpassword% "rm -rf Pilot"
plink -batch %sshlogin%@%PassengerAddress1% -pw %sshpassword% "rm -rf Passenger"
plink -batch %sshlogin%@%PassengerAddress2% -pw %sshpassword% "rm -rf Passenger"