RepositoryAddress="127.0.0.1"
DepartureAirportAddress="127.0.0.1"
PlaneAddress="127.0.0.1"
ArrivalAirportAddress="127.0.0.1"
RepositoryPort="12349"
DepartureAirportPort="12341"
PlanePort="12347"
ArrivalAirportPort="12348"

NumberPassengers="21"
PlaneMinCapacity="5"
PlaneMaxCapacity="10"

# USERNAME=l040101-ws04.ua.pt
# HOSTS="127.0.0.1 127.0.0.1 1270.0.1"
# SCRIPT="pwd; ls"
# for HOSTNAME in ${HOSTS} ; do
#     ssh -l ${USERNAME} ${HOSTNAME} "${SCRIPT}"
# done

kill $(sudo lsof -ti:$RepositoryPort)
kill $(sudo lsof -ti:$DepartureAirportPort)
kill $(sudo lsof -ti:$PlanePort)
kill $(sudo lsof -ti:$ArrivalAirportPort)

regId=$!

osType=$(uname) #"Darwin" xterm -e "cd /tmp/; watch 'pwd;date'"ww
vardir=$(pwd)
cd Servers/Repository/src
#osascript -e 'tell application "Terminal" to do script " cd /Users/brunoaguiar/Desktop/SD/p4_sd_84807_80177 && javac Initializer.java && java Initializer 12349 21"'
javac main/Initializer.java
java main/Initializer $RepositoryPort $NumberPassengers &
cd ../../../

cd Servers/DepartureAirport/src
javac main/Initializer.java
java  main/Initializer $DepartureAirportPort $NumberPassengers $PlaneMinCapacity $PlaneMaxCapacity $RepositoryAddress $RepositoryPort &
cd ../../../

cd Servers/Plane/src
javac main/Initializer.java
java  main/Initializer $PlanePort $RepositoryAddress $RepositoryPort &
cd ../../../

cd Servers/ArrivalAirport/src
javac main/Initializer.java
java  main/Initializer $ArrivalAirportPort $RepositoryAddress $RepositoryPort &
cd ../../../


sleep 1

cd Clients/Hostess/src
javac main/Initializer.java
java  main/Initializer $DepartureAirportAddress $DepartureAirportPort $PlaneAddress $PlanePort &
cd ../../../

cd Clients/Pilot/src
javac main/Initializer.java
    java  main/Initializer $DepartureAirportAddress $DepartureAirportPort $PlaneAddress $PlanePort $ArrivalAirportAddress $ArrivalAirportPort &
cd ../../../

cd Clients/Passenger/src
javac main/Initializer.java
for i in {0..20}
do
java  main/Initializer $i  $DepartureAirportAddress $DepartureAirportPort $PlaneAddress $PlanePort $ArrivalAirportAddress $ArrivalAirportPort &
done
wait