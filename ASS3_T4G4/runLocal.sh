dir=$(pwd)
RegistryAddress=localhost
RegistryName=registryp404
RegistryEnginePort=40401
RegistryListeningPort=40402
RegistryUnbinds=4

RepositoryAddress=localhost
RepositoryName=repositoryp404
RepositoryPort=40403
Logfile=$dir/repo.txt

DepartureAirportAddress=localhost
DepartureAirportName=dpp404
DepartureAirportPort=40404

PlaneAddress=localhost
PlaneName=planep404
PlanePort=40405

ArrivalAirportAddress=localhost
ArrivalAirportName=app404
ArrivalAirportPort=40406

NumberPassengers=21
MaxPassengerIndex=20
PlaneMinCapacity=5 
PlaneMaxCapacity=10

boolREP=$(sudo lsof -ti:$RegistryEnginePort)
boolRLP=$(sudo lsof -ti:$RegistryListeningPort)
boolRP=$(sudo lsof -ti:$RepositoryPort)
boolDAP=$(sudo lsof -ti:$DepartureAirportPort)
boolPP=$(sudo lsof -ti:$PlanePort)
boolAAP=$(sudo lsof -ti:$ArrivalAirportPort)

[ ! -z "$boolREP" ] && kill -9 $boolREP #  

[ ! -z "$boolRLP" ]  &&  kill -9 $boolRLP #

[ ! -z "$boolRP" ]  &&  kill -9 $boolRP

[ ! -z "$boolDAP" ] &&  kill -9 $boolDAP

[ ! -z "$boolPP" ]  &&  kill -9 $boolPP

[ ! -z "$boolAAP" ] &&  kill -9 $boolAAP

echo "Compiling Registry"
cd Registry/src
javac -cp .:../lib/genclass.jar  interfaces/*.java main/*.java
jar cf Registry.jar *
mv Registry.jar bin/
cd ../../

echo "Compiling Repository"
cd Servers/Repository/src
javac -cp .:../lib/genclass.jar main/Initializer.java
jar cf Repository.jar *
mv Repository.jar bin/
cd ../../../

echo "Compiling DepartureAirport"
cd Servers/DepartureAirport/src
javac -cp .:../lib/genclass.jar main/Initializer.java
jar cf DepartureAirport.jar *
mv DepartureAirport.jar bin/
cd ../../../

echo "Compiling Plane"
cd Servers/Plane/src
javac -cp .:../lib/genclass.jar main/Initializer.java
jar cf Plane.jar *
mv Plane.jar bin/
cd ../../../

echo "Compiling ArrivalAirport"
cd Servers/ArrivalAirport/src
javac -cp .:../lib/genclass.jar main/Initializer.java
jar cf ArrivalAirport.jar *
mv ArrivalAirport.jar bin/
cd ../../../

echo "Compiling Hostess"
cd Clients/Hostess/src/
javac -cp .:../lib/genclass.jar main/Initializer.java
jar cf Hostess.jar *
mv Hostess.jar bin/
cd ../../../

echo "COMPILING PILOT"
cd Clients/Pilot/src
javac -cp .:../lib/genclass.jar main/Initializer.java
jar cf Pilot.jar *
mv Pilot.jar bin/
cd ../../../

echo "Compiling Passenger"
cd Clients/Passenger/src
javac -cp .:../lib/genclass.jar main/Initializer.java
jar cf Passenger.jar *
mv Passenger.jar bin/
cd ../../../

echo "Registry 1"
cd Registry
rmiregistry -J-Djava.rmi.server.hostname=localhost -J-Djava.rmi.server.useCodebaseOnly=false $RegistryEnginePort &
cd ../

echo "Registry 2"
cd Registry/src/bin
java -Djava.rmi.server.codebase="file://$dir/Registry/src/bin/Registry.jar"\
                             -Djava.rmi.server.useCodebaseOnly=false\
                             -Djava.security.policy=java.policy\
                             -cp Registry.jar:lib/* main.Initializer\
                             $RegistryAddress $RegistryEnginePort $RegistryName $RegistryListeningPort $RegistryUnbinds &
cd ../../../

sleep 1

echo "Repository"
cd Servers/Repository/src/bin
java -Djava.rmi.server.codebase="file://$dir/Servers/Repository/src/bin/Repository.jar"\
                               -Djava.rmi.server.useCodebaseOnly=false\
                               -Djava.security.policy=java.policy\
                               -cp Repository.jar:lib/* main.Initializer\
                               $RegistryAddress $RegistryEnginePort $RegistryName $RepositoryName\
                               $RepositoryPort $NumberPassengers $Logfile &
cd ../../../../

sleep 1

echo "DepartureAirPort"
cd Servers/DepartureAirport/src/bin
java -Djava.rmi.server.codebase="file://$dir/Servers/DepartureAirport/src/bin/DepartureAirport.jar"\
                                     -Djava.rmi.server.useCodebaseOnly=false\
                                     -Djava.security.policy=java.policy\
                                     -cp DepartureAirport.jar:lib/* main.Initializer\
                                     $RegistryAddress $RegistryEnginePort $RegistryName\
                                     $DepartureAirportName $RepositoryName $DepartureAirportPort\
                                     $PlaneMinCapacity $PlaneMaxCapacity $NumberPassengers &
cd ../../../../

sleep 1

echo "Plane"
cd Servers/Plane/src/bin
java -Djava.rmi.server.codebase="file://$dir/Servers/Plane/src/bin/Plane.jar"\
                          -Djava.rmi.server.useCodebaseOnly=false\
                          -Djava.security.policy=java.policy\
                          -cp Plane.jar:lib/* main.Initializer\
                          $RegistryAddress $RegistryEnginePort $RegistryName\
                          $PlaneName $RepositoryName $PlanePort &
cd ../../../../

sleep 1

echo "AirportArrival"
cd Servers/ArrivalAirport/src/bin
java -Djava.rmi.server.codebase="file://$dir/Servers/ArrivalAirport/src/bin/ArrivalAirport.jar"\
                                   -Djava.rmi.server.useCodebaseOnly=false\
                                   -Djava.security.policy=java.policy\
                                   -cp ArrivalAirport.jar:lib/* main.Initializer\
                                   $RegistryAddress $RegistryEnginePort $RegistryName\
                                   $ArrivalAirportName $RepositoryName $ArrivalAirportPort &
cd ../../../../

sleep 1

echo "Hostess"
cd Clients/Hostess/src/bin
java -Djava.rmi.server.codebase="file://$dir/Clients/Hostess/src/bin/Hostess.jar"\
                            -Djava.rmi.server.useCodebaseOnly=false\
                            -cp Hostess.jar:lib/* main.Initializer\
                            $RegistryAddress $RegistryEnginePort\
                            $DepartureAirportName $PlaneName &
cd ../../../../

echo "Pilot"
cd Clients/Pilot/src/bin
java -Djava.rmi.server.codebase="file://$dir/Clients/Pilot/src/bin/Pilot.jar"\
                          -Djava.rmi.server.useCodebaseOnly=false\
                          -cp Pilot.jar:lib/* main.Initializer\
                          $RegistryAddress $RegistryEnginePort\
                          $DepartureAirportName $PlaneName $ArrivalAirportName &
cd ../../../../

echo "Passengers"
cd Clients/Passenger/src/bin
for i in {0..20}
do
java -Djava.rmi.server.codebase="file://$dir/Clients/Passenger/src/bin/Passenger.jar"\
                                      -Djava.rmi.server.useCodebaseOnly=false\
                                      -cp Passenger.jar:lib/* main.Initializer\
                                      $i $RegistryAddress $RegistryEnginePort\
                                      $DepartureAirportName $PlaneName $ArrivalAirportName &
done
cd ../../../../

wait
exit 1

 
      