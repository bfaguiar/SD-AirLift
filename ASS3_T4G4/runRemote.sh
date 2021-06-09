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

RepositoryPort="22430"
DepartureAirportPort="22431"
PlanePort="22432"
ArrivalAirportPort="22433"

NumberPassengers="21"
PlaneMinCapacity="5"
PlaneMaxCapacity="10"

sshlogin="sd404"
sshpassword="avioesp4"

export SSHPASS=$sshpassword

sshpass -e ssh -o StrictHostKeyChecking=no  $sshlogin@$RepositoryAddress "killall -9 java -u $sshlogin"
sshpass -e ssh -o StrictHostKeyChecking=no  $sshlogin@$DepartureAirportAddress "killall -9 java -u $sshlogin"
sshpass -e ssh -o StrictHostKeyChecking=no  $sshlogin@$PlaneAddress "killall -9 java -u $sshlogin"
sshpass -e ssh -o StrictHostKeyChecking=no $sshlogin@$ArrivalAirportAddress "killall -9 java -u $sshlogin"

## compile files

echo "Compiling Repository..."
sshpass -e ssh -o StrictHostKeyChecking=no  $sshlogin@$RepositoryAddress \
"cd Repository/src && javac -cp .:../lib/genclass.jar main/Initializer.java" 

echo "Compiling DepartureAirport..."
sshpass -e ssh  -o StrictHostKeyChecking=no $sshlogin@$DepartureAirportAddress \
"cd DepartureAirport/src && javac -cp .:../lib/genclass.jar main/Initializer.java"  #YOUR_USERNAME@SOME_SITE.COM

echo "Compiling Plane..."
sshpass -e ssh  -o StrictHostKeyChecking=no $sshlogin@$PlaneAddress \
"cd Plane/src && javac -cp .:../lib/genclass.jar main/Initializer.java" 

echo "Compiling Arrival Airport..."
sshpass -e ssh  -o StrictHostKeyChecking=no $sshlogin@$ArrivalAirportAddress \
"cd ArrivalAirport/src && javac -cp .:../lib/genclass.jar main/Initializer.java" 

echo "Compiling Hostess..."
sshpass -e ssh  -o StrictHostKeyChecking=no $sshlogin@$HostessAddress \
"cd Hostess/src/ && javac -cp .:../lib/genclass.jar main/Initializer.java" 

echo "Compiling Pilot..."
sshpass -e ssh  -o StrictHostKeyChecking=no $sshlogin@$PilotAddress \
"cd Pilot/src && javac -cp .:../lib/genclass.jar main/Initializer.java"

echo "Compiling Passengers..."
sshpass -e ssh  -o StrictHostKeyChecking=no $sshlogin@$PassengerAddress1 \
"cd Passenger/src && javac -cp .:../lib/genclass.jar main/Initializer.java" 

echo "Compiling Passengers..."
sshpass -e ssh  -o StrictHostKeyChecking=no $sshlogin@$PassengerAddress2 \
"cd Passenger/src && javac -cp .:../lib/genclass.jar main/Initializer.java" 

echo "Compiling Passengers..."
sshpass -e ssh  -o StrictHostKeyChecking=no $sshlogin@$PassengerAddress3 \
"cd Passenger/src && javac -cp .:../lib/genclass.jar main/Initializer.java" 
echo "Compiling Passengers..."
 sshpass -e ssh -o StrictHostKeyChecking=no $sshlogin@$PassengerAddress4 \
 "cd Passenger/src && javac -cp .:../lib/genclass.jar main/Initializer.java" 

## run files

sshpass -e ssh -o StrictHostKeyChecking=no  $sshlogin@$RepositoryAddress \
"cd Repository/src && java -cp .:../lib/genclass.jar main/Initializer $RepositoryPort $NumberPassengers " &


sshpass -e ssh -o StrictHostKeyChecking=no $sshlogin@$DepartureAirportAddress \
"cd DepartureAirport/src && java -cp .:../lib/genclass.jar main/Initializer $DepartureAirportPort  $NumberPassengers $PlaneMinCapacity $PlaneMaxCapacity $RepositoryAddress $RepositoryPort" &

sshpass -e ssh -o StrictHostKeyChecking=no $sshlogin@$PlaneAddress \
"cd Plane/src && java -cp .:../lib/genclass.jar main/Initializer  $PlanePort   $RepositoryAddress   $RepositoryPort " &

sshpass -e ssh -o StrictHostKeyChecking=no  $sshlogin@$ArrivalAirportAddress \
"cd ArrivalAirport/src && java -cp .:../lib/genclass.jar main/Initializer  $ArrivalAirportPort   $RepositoryAddress   $RepositoryPort " &

sleep 4

sshpass -e ssh -o StrictHostKeyChecking=no  $sshlogin@$HostessAddress \
"cd Hostess/src/ && java -cp .:../lib/genclass.jar main/Initializer  $DepartureAirportAddress   $DepartureAirportPort   $PlaneAddress   $PlanePort " & 

sshpass -e ssh -o StrictHostKeyChecking=no  $sshlogin@$PilotAddress \
"cd Pilot/src && java -cp .:../lib/genclass.jar main/Initializer  $DepartureAirportAddress   $DepartureAirportPort   $PlaneAddress   $PlanePort   $ArrivalAirportAddress   $ArrivalAirportPort " & 


for i in {0..4}
do
    sshpass -e ssh -o StrictHostKeyChecking=no  $sshlogin@$PassengerAddress1 \
    "cd Passenger/src && java -cp .:../lib/genclass.jar main/Initializer   $i  $DepartureAirportAddress   $DepartureAirportPort   $PlaneAddress   $PlanePort   $ArrivalAirportAddress   $ArrivalAirportPort " & 
done

for i in {5..9}
do
    sshpass -e ssh -o StrictHostKeyChecking=no  $sshlogin@$PassengerAddress2 \
    "cd Passenger/src && java -cp .:../lib/genclass.jar main/Initializer   $i  $DepartureAirportAddress   $DepartureAirportPort   $PlaneAddress   $PlanePort   $ArrivalAirportAddress   $ArrivalAirportPort " &
done

for i in {10..14} 
do
    sshpass -e ssh -o StrictHostKeyChecking=no  $sshlogin@$PassengerAddress3 \
    "cd Passenger/src && java -cp .:../lib/genclass.jar main/Initializer   $i  $DepartureAirportAddress   $DepartureAirportPort   $PlaneAddress   $PlanePort   $ArrivalAirportAddress   $ArrivalAirportPort " & 
done 

for i in {15..20}
do 
    sshpass -e ssh -o StrictHostKeyChecking=no $sshlogin@$PassengerAddress4 \
    "cd Passenger/src && java -cp .:../lib/genclass.jar main/Initializer   $i  $DepartureAirportAddress   $DepartureAirportPort   $PlaneAddress   $PlanePort   $ArrivalAirportAddress   $ArrivalAirportPort " &
done 

wait    