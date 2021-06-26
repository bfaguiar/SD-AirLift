RepositoryAddress="l040101-ws01.ua.pt"
DepartureAirportAddress="l040101-ws02.ua.pt"
PlaneAddress="l040101-ws03.ua.pt"
ArrivalAirportAddress="l040101-ws04.ua.pt"
HostessAddress="l040101-ws05.ua.pt"
PilotAddress="l040101-ws06.ua.pt" 
PassengerAddress1="l040101-ws07.ua.pt"
PassengerAddress2="l040101-ws08.ua.pt" 
PassengerAddress3="l040101-ws09.ua.pt"
PassengerAddress4="l040101-ws10.ua.pt"

sshlogin="sd404"


export SSHPASS=$sshpassword

scp -r Servers/Repository $sshlogin@$RepositoryAddress:/home/sd404/
scp -r Servers/DepartureAirport $sshlogin@$DepartureAirportAddress:/home/sd404/
scp -r Servers/Plane $sshlogin@$PlaneAddress:/home/sd404/
scp -r Servers/ArrivalAirport $sshlogin@$ArrivalAirportAddress:/home/sd404/

scp -r Clients/Hostess $sshlogin@$HostessAddress:/home/sd404/
scp -r Clients/Pilot $sshlogin@$PilotAddress:/home/sd404/
scp -r Clients/Passenger $sshlogin@$PassengerAddress1:/home/sd404/
scp -r Clients/Passenger $sshlogin@$PassengerAddress2:/home/sd404/
scp -r Clients/Passenger $sshlogin@$PassengerAddress3:/home/sd404/
scp -r Clients/Passenger $sshlogin@$PassengerAddress4:/home/sd404/


