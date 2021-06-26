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
 sshpassword="avioesp4"


export SSHPASS=$sshpassword

echo "Cleaning Repository..."
sshpass -e ssh -o StrictHostKeyChecking=no  $sshlogin@$RepositoryAddress \
"rm -rf Repository/"

echo "Cleaning DepartureAirport..."
sshpass -e ssh  -o StrictHostKeyChecking=no $sshlogin@$DepartureAirportAddress \
 "rm -rf DepartureAirport/"  #YOUR_USERNAME@SOME_SITE.COM

echo "Cleaning Plane..."
sshpass -e ssh  -o StrictHostKeyChecking=no $sshlogin@$PlaneAddress \
"rm -rf Plane/"

echo "Cleaning Arrival Airport"
sshpass -e ssh  -o StrictHostKeyChecking=no $sshlogin@$ArrivalAirportAddress \
"rm -rf ArrivalAirport/"

echo "Cleaning Hostess"
sshpass -e ssh  -o StrictHostKeyChecking=no $sshlogin@$HostessAddress \
"rm -rf Hostess/"

echo "Cleaning Pilot"
sshpass -e ssh  -o StrictHostKeyChecking=no $sshlogin@$PilotAddress \
"rm -rf Pilot/"

echo "Cleaning Passenger"
sshpass -e ssh  -o StrictHostKeyChecking=no $sshlogin@$PassengerAddress1 \
"rm -rf Passenger/"

echo "Cleaning Passenger"
sshpass -e ssh  -o StrictHostKeyChecking=no $sshlogin@$PassengerAddress2 \
"rm -rf Passenger/"

echo "Cleaning Passenger"
sshpass -e ssh  -o StrictHostKeyChecking=no $sshlogin@$PassengerAddress3 \
"rm -rf Passenger/"

echo "Cleaning Passenger"
sshpass -e ssh -o StrictHostKeyChecking=no $sshlogin@$PassengerAddress4 \
"rm -rf Passenger/"
