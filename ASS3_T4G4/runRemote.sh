RegistryAddress=l040101-ws02.ua.pt
RegistryName=registryp404 
RegistryEnginePort=22430
RegistryListeningPort=40402
RegistryUnbinds=4

RepositoryAddress=l040101-ws03.ua.pt
RepositoryName=repositoryp404
RepositoryPort=22431
Logfile=/home/sd404/repo.txt

DepartureAirportAddress=l040101-ws04.ua.pt
DepartureAirportName=dpp404
DepartureAirportPort=22432

PlaneAddress=l040101-ws05.ua.pt
PlaneName=planep404
PlanePort=22433

ArrivalAirportAddress=l040101-ws06.ua.pt
ArrivalAirportName=app404
ArrivalAirportPort=22434

NumberPassengers=21
MaxPassengerIndex=20
PlaneMinCapacity=5 
PlaneMaxCapacity=10

HostessAddress=l040101-ws07.ua.pt
PilotAddress=l040101-ws08.ua.pt

PassengerAddress1=l040101-ws09.ua.pt
PassengerAddress2=l040101-ws10.ua.pt
#PassengerAddress3=  "l040101-ws09.ua.pt"  """"

sshlogin="sd404" #876 
sshpassword="avioesp4"

export SSHPASS=$sshpassword

echo "Do you want to copy files (y/n)? "
read answer
if [ "$answer" != "${answer#[Yy]}" ] ; then
    echo "Coping bins..."
    sshpass -e ssh -o StrictHostKeyChecking=no $sshlogin@$RegistryAddress "rm -rf bin"
    sshpass -e scp -r Registry/src/bin $sshlogin@$RegistryAddress:/home/sd404/
    sshpass -e ssh -o StrictHostKeyChecking=no $sshlogin@$RepositoryAddress "rm -rf bin"
    sshpass -e scp -r Servers/Repository/src/bin $sshlogin@$RepositoryAddress:/home/sd404/
    sshpass -e ssh -o StrictHostKeyChecking=no $sshlogin@$DepartureAirportAddress "rm -rf bin"
    sshpass -e scp -r Servers/DepartureAirport/src/bin $sshlogin@$DepartureAirportAddress:/home/sd404/
    sshpass -e ssh -o StrictHostKeyChecking=no $sshlogin@$PlaneAddress "rm -rf bin"
    sshpass -e scp -r Servers/Plane/src/bin $sshlogin@$PlaneAddress:/home/sd404/
    sshpass -e ssh -o StrictHostKeyChecking=no $sshlogin@$ArrivalAirportAddress "rm -rf bin"
    sshpass -e scp -r Servers/ArrivalAirport/src/bin $sshlogin@$ArrivalAirportAddress:/home/sd404/
     sshpass -e ssh -o StrictHostKeyChecking=no $sshlogin@$HostessAddress "rm -rf bin"
    sshpass -e scp -r Clients/Hostess/src/bin $sshlogin@$HostessAddress:/home/sd404/
    sshpass -e ssh -o StrictHostKeyChecking=no $sshlogin@$PilotAddress "rm -rf bin"
    sshpass -e scp -r Clients/Pilot/src/bin $sshlogin@$PilotAddress:/home/sd404/
    sshpass -e ssh -o StrictHostKeyChecking=no $sshlogin@$PassengerAddress1 "rm -rf bin"
    sshpass -e scp -r Clients/Passenger/src/bin $sshlogin@$PassengerAddress1:/home/sd404/
    sshpass -e ssh -o StrictHostKeyChecking=no $sshlogin@$PassengerAddress2 "rm -rf bin"
    sshpass -e scp -r Clients/Passenger/src/bin $sshlogin@$PassengerAddress2:/home/sd404/
    #sshpass -e scp -r Clients/Passenger/src/bin $sshlogin@$PassengerAddress3:/home/sd404/ 
else
    #echo "passing..."
    : #§nop 
fi


echo "Cleaning Ports..."

sshpass -e ssh  -o StrictHostKeyChecking=no  $sshlogin@$RegistryAddress " \
    boolREP=\$(/usr/sbin/lsof -t -i :$RegistryEnginePort);\
    boolRLP=\$(/usr/sbin/lsof -t -i :$RegistryListeningPort);
    [ ! -z \"\$boolREP\" ] && kill -9 \$boolREP;
    [ ! -z \"\$boolRLP\" ]  &&  kill -9 \$boolRLP"

sshpass -e ssh  -o  StrictHostKeyChecking=no  $sshlogin@$RepositoryAddress " \
    boolRP=\$(/usr/sbin/lsof -t -i :$RepositoryPort);\
    [ ! -z \"\$boolRP\" ]  &&  kill -9 \$boolRP"

sshpass -e ssh -o StrictHostKeyChecking=no  $sshlogin@$DepartureAirportAddress " \
    boolDAP=\$(/usr/sbin/lsof -t -i :$DepartureAirportPort); \
    [ ! -z \"\$boolDAP\" ] &&  kill -9 \$boolDAP"

sshpass -e ssh -o StrictHostKeyChecking=no  $sshlogin@$PlaneAddress " \
    boolPP=\$(/usr/sbin/lsof -t -i:$PlanePort); \
    [ ! -z \"\$boolPP\" ]  &&  kill -9 \$boolPP"

sshpass -e ssh -o StrictHostKeyChecking=no $sshlogin@$ArrivalAirportAddress " \
    boolAAP=\$(/usr/sbin/lsof -t -i:$ArrivalAirportPort) ; \
    [ ! -z \"\$boolAAP\" ] &&  kill -9 \$boolAAP"

echo "Running Program"

echo "Registry"
sshpass -e ssh  -o StrictHostKeyChecking=no  $sshlogin@$RegistryAddress "cd bin/;rmiregistry  -J-Djava.rmi.server.useCodebaseOnly=true $RegistryEnginePort " &
echo "asdasdasd"
sleep 5

echo "Registry 2"
sshpass -e ssh  -o StrictHostKeyChecking=no  $sshlogin@$RegistryAddress "cd bin;jar xf *.jar; \
    java -Djava.rmi.server.codebase="http://l040101-ws02.ua.pt/sd404/bin"\
                             -Djava.rmi.server.useCodebaseOnly=true\
                             -Djava.security.policy=java.policy\
                             -cp Registry.jar:lib/* main.Initializer\
                             $RegistryAddress $RegistryEnginePort $RegistryName $RegistryListeningPort $RegistryUnbinds " & 

sleep 5

echo "Repository"
sshpass -e ssh  -o  StrictHostKeyChecking=no  $sshlogin@$RepositoryAddress "cd bin;jar xf *.jar; \
java -Djava.rmi.server.codebase="http://l040101-ws03.ua.pt/sd404/bin"\
                               -Djava.rmi.server.useCodebaseOnly=true\
                               -Djava.security.policy=java.policy\
                               -cp Repository.jar:lib/* main.Initializer\
                               $RegistryAddress $RegistryEnginePort $RegistryName $RepositoryName $RepositoryPort $NumberPassengers $Logfile " &  

sleep 5

echo "DepartureAirport" 
sshpass -e ssh -o StrictHostKeyChecking=no  $sshlogin@$DepartureAirportAddress "cd bin;jar xf *.jar; \
java -Djava.rmi.server.codebase="http://l040101-ws04.ua.pt/sd404/bin"\
                                     -Djava.rmi.server.useCodebaseOnly=true\
                                     -Djava.security.policy=java.policy\
                                     -cp DepartureAirport.jar:lib/* main.Initializer\
                                     $RegistryAddress $RegistryEnginePort $RegistryName\
                                     $DepartureAirportName $RepositoryName $DepartureAirportPort\
                                     $PlaneMinCapacity $PlaneMaxCapacity $NumberPassengers " & #cd  cd Registry/src/ cd

sleep 5

echo "Plane"
sshpass -e ssh -o StrictHostKeyChecking=no  $sshlogin@$PlaneAddress "cd bin;jar xf *.jar; \
java -Djava.rmi.server.codebase="http://l040101-ws05.ua.pt/sd404/bin"\
                          -Djava.rmi.server.useCodebaseOnly=true\
                          -Djava.security.policy=java.policy\
                          -cp Plane.jar:lib/* main.Initializer\
                          $RegistryAddress $RegistryEnginePort $RegistryName\
                          $PlaneName $RepositoryName $PlanePort " &

sleep 5

echo "ArrivalAirport"
sshpass -e ssh -o StrictHostKeyChecking=no $sshlogin@$ArrivalAirportAddress "cd bin;jar xf *.jar; \
java -Djava.rmi.server.codebase="http://l040101-ws06.ua.pt/sd404/bin"\
                                   -Djava.rmi.server.useCodebaseOnly=true\
                                   -Djava.security.policy=java.policy\
                                   -cp ArrivalAirport.jar:lib/* main.Initializer\
                                   $RegistryAddress $RegistryEnginePort $RegistryName\
                                   $ArrivalAirportName $RepositoryName $ArrivalAirportPort " & 

sleep 1

echo "Hostess"
sshpass -e ssh -o StrictHostKeyChecking=no $sshlogin@$HostessAddress "cd bin;jar xf *.jar; \
java -Djava.rmi.server.codebase="http://l040101-ws07.ua.pt/sd404/bin"\
                            -Djava.rmi.server.useCodebaseOnly=true\
                            -cp Hostess.jar:lib/* main.Initializer\
                            $RegistryAddress $RegistryEnginePort\
                            $DepartureAirportName $PlaneName " &

sleep 1

echo "Pilot"
sshpass -e ssh -o StrictHostKeyChecking=no $sshlogin@$PilotAddress "cd bin;jar xf *.jar; \
java -Djava.rmi.server.codebase="http://l040101-ws08.ua.pt/sd404/bin"\
                          -Djava.rmi.server.useCodebaseOnly=true\
                          -cp Pilot.jar:lib/* main.Initializer\
                          $RegistryAddress $RegistryEnginePort\
                          $DepartureAirportName $PlaneName $ArrivalAirportName " &

sleep 1 

echo "Passenger1"
for i in {0..13}
do
    sshpass -e ssh -o StrictHostKeyChecking=no $sshlogin@$PassengerAddress1 "cd bin;jar xf *.jar; \
    java -Djava.rmi.server.codebase="http://l040101-ws09.ua.pt/sd404/bin"\
                                      -Djava.rmi.server.useCodebaseOnly=true\
                                      -cp Passenger.jar:lib/* main.Initializer\
                                      $i $RegistryAddress $RegistryEnginePort\
                                      $DepartureAirportName $PlaneName $ArrivalAirportName " & #"

done

sleep 1

echo "Passenger2" 
for i in {13..20} #7
do
    sshpass -e ssh -o StrictHostKeyChecking=no $sshlogin@$PassengerAddress2 "cd bin;jar xf *.jar; \
    java -Djava.rmi.server.codebase="http://l040101-ws10.ua.pt/sd404/bin"\
                                      -Djava.rmi.server.useCodebaseOnly=true\
                                      -cp Passenger.jar:lib/* main.Initializer\
                                      $i $RegistryAddress $RegistryEnginePort\
                                      $DepartureAirportName $PlaneName $ArrivalAirportName " & #"

done   
