RegistryAddress=l040101-ws02.ua.pt
RegistryName=registryp404 
RegistryEnginePort=40401
RegistryListeningPort=40402
RegistryUnbinds=4

RepositoryAddress=l040101-ws03.ua.pt
RepositoryName=repositoryp404
RepositoryPort=40403
Logfile=/home/sd404/repo.txt

DepartureAirportAddress=l040101-ws04.ua.pt
DepartureAirportName=dpp404
DepartureAirportPort=40404

PlaneAddress=l040101-ws05.ua.pt
PlaneName=planep404
PlanePort=40405

ArrivalAirportAddress=l040101-ws06.ua.pt
ArrivalAirportName=app404
ArrivalAirportPort=40406

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
    sshpass -e scp -r Registry/src/bin/ $sshlogin@$RegistryAddress:/home/sd404/
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
    sshpass -e scp -r Clients/Passenger/src/bin/ $sshlogin@$PassengerAddress2:/home/sd404/
    #sshpass -e scp -r Clients/Passenger/src/bin $sshlogin@$PassengerAddress3:/home/sd404/ 
else
    echo "passing..."
fi


echo "Cleaning Ports"

sshpass -e ssh  -o StrictHostKeyChecking=no  $sshlogin@$RegistryAddress " \
    boolREP=\$(/usr/sbin/lsof -t -i :$RegistryEnginePort);echo \$boolREP;\
    boolRLP=\$(/usr/sbin/lsof -t -i :$RegistryListeningPort);echo \$boolRLP;
    [ ! -z \"\$boolREP\" ] && kill -9 \$boolREP && echo OLA1;
    [ ! -z \"\$boolRLP\" ]  &&  kill -9 \$boolRLP && echo OLA2;"

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
sshpass -e ssh  -o StrictHostKeyChecking=no  $sshlogin@$RegistryAddress "rmiregistry -J-Djava.rmi.server.hostname=localhost -J-Djava.rmi.server.useCodebaseOnly=false $RegistryEnginePort " &
echo "asdasdasd"
sleep 5

echo "Registry 2"
sshpass -e ssh  -o StrictHostKeyChecking=no  $sshlogin@$RegistryAddress "cd bin; \
java -Djava.rmi.server.codebase="http://l040101-ws02.ua.pt/sd404/bin/Registry.jar"\
                             -Djava.rmi.server.useCodebaseOnly=false\
                             -Djava.security.policy=java.policy\
                             -cp Registry.jar:lib/* main.Initializer\
                             $RegistryAddress $RegistryEnginePort $RegistryName $RegistryListeningPort $RegistryUnbinds " & 

sleep 5

echo "Repository"
sshpass -e ssh  -o  StrictHostKeyChecking=no  $sshlogin@$RepositoryAddress "cd bin
java -Djava.rmi.server.codebase="http://l040101-ws03.ua.pt/sd404/bin/Repository.jar"\
                               -Djava.rmi.server.useCodebaseOnly=false\
                               -Djava.security.policy=java.policy\
                               -cp Repository.jar:lib/* main.Initializer\
                               $RegistryAddress $RegistryEnginePort $RegistryName $RepositoryName\
                               $RepositoryPort $NumberPassengers $Logfile " &  

sleep 5

echo "DepartureAirport" 
sshpass -e ssh -o StrictHostKeyChecking=no  $sshlogin@$DepartureAirportAddress "cd bin; \
java -Djava.rmi.server.codebase="http://l040101-ws04.ua.pt/sd404/bin/DepartureAirport.jar"\
                                     -Djava.rmi.server.useCodebaseOnly=false\
                                     -Djava.security.policy=java.policy\
                                     -cp DepartureAirport.jar:lib/* main.Initializer\
                                     $RegistryAddress $RegistryEnginePort $RegistryName\
                                     $DepartureAirportName $RepositoryName $DepartureAirportPort\
                                     $PlaneMinCapacity $PlaneMaxCapacity $NumberPassengers " & #cd  cd Registry/src/ cd

sleep 5

echo "Plane"
sshpass -e ssh -o StrictHostKeyChecking=no  $sshlogin@$PlaneAddress "cd bin; \
java -Djava.rmi.server.codebase="http://l040101-ws05.ua.pt/sd404/bin/Plane.jar"\
                          -Djava.rmi.server.useCodebaseOnly=false\
                          -Djava.security.policy=java.policy\
                          -cp Plane.jar:lib/* main.Initializer\
                          $RegistryAddress $RegistryEnginePort $RegistryName\
                          $PlaneName $RepositoryName $PlanePort " &

sleep 5

echo "ArrivalAirport"
sshpass -e ssh -o StrictHostKeyChecking=no $sshlogin@$ArrivalAirportAddress "cd bin;\
java -Djava.rmi.server.codebase="http://l040101-ws06.ua.pt/sd404/bin/ArrivalAirport.jar"\
                                   -Djava.rmi.server.useCodebaseOnly=false\
                                   -Djava.security.policy=java.policy\
                                   -cp ArrivalAirport.jar:lib/* main.Initializer\
                                   $RegistryAddress $RegistryEnginePort $RegistryName\
                                   $ArrivalAirportName $RepositoryName $ArrivalAirportPort " & 

sleep 1

echo "Hostess"
sshpass -e ssh -o StrictHostKeyChecking=no $sshlogin@$HostessAddress "cd bin; \
java -Djava.rmi.server.codebase="http://l040101-ws07.ua.pt/sd404/Hostess.jar"\
                            -Djava.rmi.server.useCodebaseOnly=false\
                            -cp Hostess.jar:lib/* main.Initializer\
                            $RegistryAddress $RegistryEnginePort\
                            $DepartureAirportName $PlaneName " &

sleep 1

echo "Pilot"
sshpass -e ssh -o StrictHostKeyChecking=no $sshlogin@$PilotAddress "cd bin; \
java -Djava.rmi.server.codebase="http://l040101-ws08.ua.pt/sd404/bin/Pilot.jar"\
                          -Djava.rmi.server.useCodebaseOnly=false\
                          -cp Pilot.jar:lib/* main.Initializer\
                          $RegistryAddress $RegistryEnginePort\
                          $DepartureAirportName $PlaneName $ArrivalAirportName " &

sleep 1 

echo "Passenger1"
for i in {0..13}
do
    sshpass -e ssh -o StrictHostKeyChecking=no $sshlogin@$PassengerAddress1 "cd bin; \
    java -Djava.rmi.server.codebase="http://l040101-ws09.ua.pt/sd404/bin/Passenger.jar"\
                                      -Djava.rmi.server.useCodebaseOnly=false\
                                      -cp Passenger.jar:lib/* main.Initializer\
                                      $i $RegistryAddress $RegistryEnginePort\
                                      $DepartureAirportName $PlaneName $ArrivalAirportName " & #"

done

sleep 1

echo "Passenger2" 
for i in {13..20} #7
do
    sshpass -e ssh -o StrictHostKeyChecking=no $sshlogin@$PassengerAddress2 "cd bin; \
    java -Djava.rmi.server.codebase="http://l040101-ws10.ua.pt/sd404/bin/Passenger.jar"\
                                      -Djava.rmi.server.useCodebaseOnly=false\
                                      -cp Passenger.jar:lib/* main.Initializer\
                                      $i $RegistryAddress $RegistryEnginePort\
                                      $DepartureAirportName $PlaneName $ArrivalAirportName " & #"

done   

# for i in {14..20}
# do
#     sshpass -e ssh -o StrictHostKeyChecking=no $sshlogin@PassengerAddress1 "cd bin; \
#     java -Djava.rmi.server.codebase="http://l040101-ws10.ua.pt/sd404/bin/Passenger.jar"\
#                                       -Djava.rmi.server.useCodebaseOnly=false\
#                                       -cp Passenger.jar:lib/* main.Initializer\
#                                       $i $RegistryAddress $RegistryEnginePort\
#                                       $DepartureAirportName $PlaneName $ArrivalAirportName &" #"

# done
     

#√# ## compile files v541

# echo "Compiling Repository..."
# sshpass -e ssh -o StrictHostKeyChecking=no  $sshlogin@$RepositoryAddress \
# "cd Repository/src && javac -cp .:../lib/genclass.jar main/Initializer.java" 

# echo "Compiling DepartureAirport..."
# sshpass -e ssh  -o StrictHostKeyChecking=no $sshlogin@$DepartureAirportAddress \
# "cd DeparturseAirport/src && javac -cp .:../lib/genclass.jar main/Initializer.java"  #YOUR_USERNAME@SOME_SITE.COM

# echo "Compiling Plane..."
# sshpass -e ssh  -o StrictHostKeyChecking=no $sshlogin@$PlaneAddress \
# "cd Plane/src && javac -cp .:../lib/genclass.jar main/Initializer.java" 

# echo "Compiling Arrival Airport..."
# sshpass -e ssh  -o StrictHostKeyChecking=no $sshlogin@$ArrivalAirportAddress \
# "cd ArrivalAirport/src && javac -cp .:../lib/genclass.jar main/Initializer.java" 

# echo "Compiling Hostess..."
# sshpass -e ssh  -o StrictHostKeyChecking=no $sshlogin@$HostessAddress \
# "cd Hostess/src/ && javac -cp .:../lib/genclass.jar main/Initializer.java" 

# echo "Compiling Pilot..."
# sshpass -e ssh  -o StrictHostKeyChecking=no $sshlogin@$PilotAddress \
# "cd Pilot/src && javac -cp .:../lib/genclass.jar main/Initializer.java"

# echo "Compiling Passengers..."
# sshpass -e ssh  -o StrictHostKeyChecking=no $sshlogin@$PassengerAddress1 \
# "cd Passenger/src && javac -cp .:../lib/genclass.jar main/Initializer.java" 

# echo "Compiling Passengers..."
# sshpass -e ssh  -o StrictHostKeyChecking=no $sshlogin@$PassengerAddress2 \
# "cd Passenger/src && javac -cp .:../lib/genclass.jar main/Initializer.java" 

# echo "Compiling Passengers..."
# sshpass -e ssh  -o StrictHostKeyChecking=no $sshlogin@$PassengerAddress3 \
# "cd Passenger/src && javac -cp .:../lib/genclass.jar main/Initializer.java" 
# echo "Compiling Passengers..."
#  sshpass -e ssh -o StrictHostKeyChecking=no $sshlogin@$PassengerAddress4 \
#  "cd Passenger/src && javac -cp .:../lib/genclass.jar main/Initializer.java" 

# ## run files

# sshpass -e ssh -o StrictHostKeyChecking=no  $sshlogin@$RepositoryAddress \
# "cd Repository/src && java -cp .:../lib/genclass.jar main/Initializer $RepositoryPort $NumberPassengers " &


# sshpass -e ssh -o StrictHostKeyChecking=no $sshlogin@$DepartureAirportAddress \
# "cd DepartureAirport/src && java -cp .:../lib/genclass.jar main/Initializer $DepartureAirportPort  $NumberPassengers $PlaneMinCapacity $PlaneMaxCapacity $RepositoryAddress $RepositoryPort" &

# sshpass -e ssh -o StrictHostKeyChecking=no $sshlogin@$PlaneAddress \
# "cd Plane/src && java -cp .:../lib/genclass.jar main/Initializer  $PlanePort   $RepositoryAddress   $RepositoryPort " &

# sshpass -e ssh -o StrictHostKeyChecking=no  $sshlogin@$ArrivalAirportAddress \
# "cd ArrivalAirport/src && java -cp .:../lib/genclass.jar main/Initializer  $ArrivalAirportPort   $RepositoryAddress   $RepositoryPort " &

# sleep 4

# sshpass -e ssh -o StrictHostKeyChecking=no  $sshlogin@$HostessAddress \
# "cd Hostess/src/ && java -cp .:../lib/genclass.jar main/Initializer  $DepartureAirportAddress   $DepartureAirportPort   $PlaneAddress   $PlanePort " & 

# sshpass -e ssh -o StrictHostKeyChecking=no  $sshlogin@$PilotAddress \
# "cd Pilot/src && java -cp .:../lib/genclass.jar main/Initializer  $DepartureAirportAddress   $DepartureAirportPort   $PlaneAddress   $PlanePort   $ArrivalAirportAddress   $ArrivalAirportPort " & 


# for i in {0..4}
# do
#     sshpass -e ssh -o StrictHostKeyChecking=no  $sshlogin@$PassengerAddress1 \
#     "cd Passenger/src && java -cp .:../lib/genclass.jar main/Initializer   $i  $DepartureAirportAddress   $DepartureAirportPort   $PlaneAddress   $PlanePort   $ArrivalAirportAddress   $ArrivalAirportPort " & 
# done

# for i in {5..9}
# do
#     sshpass -e ssh -o StrictHostKeyChecking=no  $sshlogin@$PassengerAddress2 \
#     "cd Passenger/src && java -cp .:../lib/genclass.jar main/Initializer   $i  $DepartureAirportAddress   $DepartureAirportPort   $PlaneAddress   $PlanePort   $ArrivalAirportAddress   $ArrivalAirportPort " &
# done

# for i in {10..14} 
# do
#     sshpass -e ssh -o StrictHostKeyChecking=no  $sshlogin@$PassengerAddress3 \
#     "cd Passenger/src && java -cp .:../lib/genclass.jar main/Initializer   $i  $DepartureAirportAddress   $DepartureAirportPort   $PlaneAddress   $PlanePort   $ArrivalAirportAddress   $ArrivalAirportPort " & 
# done 

# for i in {15..20}
# do 
#     sshpass -e ssh -o StrictHostKeyChecking=no $sshlogin@$PassengerAddress4 \
#     "cd Passenger/src && java -cp .:../lib/genclass.jar main/Initializer   $i  $DepartureAirportAddress   $DepartureAirportPort   $PlaneAddress   $PlanePort   $ArrivalAirportAddress   $ArrivalAirportPort " &
# done 

# wait    