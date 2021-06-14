RepositoryAddress="l040101-ws01.ua.pt"
RepositoryPort="22430"

DepartureAirportAddress="l040101-ws02.ua.pt"
DepartureAirportPort="22431"

PlaneAddress="l040101-ws03.ua.pt"
PlanePort="22432"

ArrivalAirportAddress="l040101-ws04.ua.pt"
ArrivalAirportPort="22433"

sshlogin="sd404"
sshpassword="avioesp4"


sshpass -p "avioesp4" ssh -o StrictHostKeyChecking=no  $sshlogin@$RepositoryAddress "killall -9 java -u $sshlogin"
sshpass -p "avioesp4" ssh -o StrictHostKeyChecking=no  $sshlogin@$DepartureAirportAddress "killall -9 java -u $sshlogin"
sshpass -p "avioesp4" ssh -o StrictHostKeyChecking=no  $sshlogin@$PlaneAddress "killall -9 java -u $sshlogin"
sshpass -p "avioesp4" ssh -o StrictHostKeyChecking=no $sshlogin@$ArrivalAirportAddress "killall -9 java -u $sshlogin"