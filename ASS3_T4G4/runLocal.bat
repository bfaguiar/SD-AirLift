set RegistryAddress=localhost
set RegistryName=registryp404
set RegistryEnginePort=40401
set RegistryListeningPort=40402
set RegistryUnbinds=4

set RepositoryAddress=localhost
set RepositoryName=repositoryp404
set RepositoryPort=40403
set Logfile=C:/Users/dmtar/Desktop/SD/dev/repo.txt

set DepartureAirportAddress=localhost
set DepartureAirportName=dpp404
set DepartureAirportPort=40404

set PlaneAddress=localhost
set PlaneName=planep404
set PlanePort=40405

set ArrivalAirportAddress=localhost
set ArrivalAirportName=app404
set ArrivalAirportPort=40406

set NumberPassengers=21
set MaxPassengerIndex=20
set PlaneMinCapacity=5
set PlaneMaxCapacity=10

cd Registry/src
javac -cp .;../lib/genclass.jar interfaces/* main/*
jar cf Registry.jar *
move Registry.jar bin/
cd ../../

cd Servers/Repository/src
javac -cp .;../lib/genclass.jar main/Initializer.java
jar cf Repository.jar *
move Repository.jar bin/
cd ../../../

cd Servers/DepartureAirport/src
javac -cp .;../lib/genclass.jar main/Initializer.java
jar cf DepartureAirport.jar *
move DepartureAirport.jar bin/
cd ../../../

cd Servers/Plane/src
javac -cp .;../lib/genclass.jar main/Initializer.java
jar cf Plane.jar *
move Plane.jar bin/
cd ../../../

cd Servers/ArrivalAirport/src
javac -cp .;../lib/genclass.jar main/Initializer.java
jar cf ArrivalAirport.jar *
move ArrivalAirport.jar bin/
cd ../../../

cd Clients/Hostess/src/
javac -cp .;../lib/genclass.jar main/Initializer.java
jar cf Hostess.jar *
move Hostess.jar bin/
cd ../../../

cd Clients/Pilot/src
javac -cp .;../lib/genclass.jar main/Initializer.java
jar cf Pilot.jar *
move Pilot.jar bin/
cd ../../../

cd Clients/Passenger/src
javac -cp .;../lib/genclass.jar main/Initializer.java
jar cf Passenger.jar *
move Passenger.jar bin/
cd ../../../

cd Registry
start "Registry Startup" cmd /c rmiregistry -J-Djava.rmi.server.hostname=localhost -J-Djava.rmi.server.useCodebaseOnly=false %RegistryEnginePort% ^& pause
cd ../

cd Registry/src/bin
start "Registry" cmd /c java -Djava.rmi.server.codebase="file:\C:\Users\dmtar\Desktop\SD\dev\Registry\src\bin\Registry.jar" ^
                             -Djava.rmi.server.useCodebaseOnly=false\ ^
                             -Djava.security.policy=java.policy ^
                             -cp Registry.jar;lib/* main.Initializer ^
                             %RegistryAddress% %RegistryEnginePort% %RegistryName% %RegistryListeningPort% %RegistryUnbinds% ^& pause
cd ../../../

timeout /t 1

cd Servers/Repository/src/bin
start "Repository" cmd /c java -Djava.rmi.server.codebase="file:\C:\Users\dmtar\Desktop\SD\dev\Repository\src\bin\Repository.jar" ^
                               -Djava.rmi.server.useCodebaseOnly=false\ ^
                               -Djava.security.policy=java.policy ^
                               -cp Repository.jar;lib/* main.Initializer ^
                               %RegistryAddress% %RegistryEnginePort% %RegistryName% %RepositoryName% ^
                               %RepositoryPort% %NumberPassengers% %Logfile% ^& pause
cd ../../../../

timeout /t 1

cd Servers/DepartureAirport/src/bin
start "DepartureAirport" cmd /c java -Djava.rmi.server.codebase="file:\C:\Users\dmtar\Desktop\SD\dev\Servers\DepartureAirport\src\bin\DepartureAirport.jar" ^
                                     -Djava.rmi.server.useCodebaseOnly=false\ ^
                                     -Djava.security.policy=java.policy ^
                                     -cp DepartureAirport.jar;lib/* main.Initializer ^
                                     %RegistryAddress% %RegistryEnginePort% %RegistryName% ^
                                     %DepartureAirportName% %RepositoryName% %DepartureAirportPort% ^
                                     %PlaneMinCapacity% %PlaneMaxCapacity% %NumberPassengers% ^& pause
cd ../../../../

timeout /t 1

cd Servers/Plane/src/bin
start "Plane" cmd /c java -Djava.rmi.server.codebase="file:\C:\Users\dmtar\Desktop\SD\dev\Servers\Plane\src\bin\Plane.jar" ^
                          -Djava.rmi.server.useCodebaseOnly=false\ ^
                          -Djava.security.policy=java.policy ^
                          -cp Plane.jar;lib/* main.Initializer ^
                          %RegistryAddress% %RegistryEnginePort% %RegistryName% ^
                          %PlaneName% %RepositoryName% %PlanePort% ^& pause
cd ../../../../

timeout /t 1

cd Servers/ArrivalAirport/src/bin
start "ArrivalAirport" cmd /c java -Djava.rmi.server.codebase="file:\C:\Users\dmtar\Desktop\SD\dev\Servers\ArrivalAirport\src\bin\ArrivalAirport.jar" ^
                                   -Djava.rmi.server.useCodebaseOnly=false\ ^
                                   -Djava.security.policy=java.policy ^
                                   -cp ArrivalAirport.jar;lib/* main.Initializer ^
                                   %RegistryAddress% %RegistryEnginePort% %RegistryName% ^
                                   %ArrivalAirportName% %RepositoryName% %ArrivalAirportPort% ^& pause
cd ../../../../

timeout /t 1

cd Clients/Hostess/src/bin
start "Hostess" cmd /c java -Djava.rmi.server.codebase="file:\C:\Users\dmtar\Desktop\SD\dev\Clients\Hostess\src\bin\Hostess.jar" ^
                            -Djava.rmi.server.useCodebaseOnly=false\ ^
                            -cp Hostess.jar;lib/* main.Initializer ^
                            %RegistryAddress% %RegistryEnginePort% ^
                            %DepartureAirportName% %PlaneName% 
cd ../../../../

cd Clients/Pilot/src/bin
start "Pilot" cmd /c java -Djava.rmi.server.codebase="file:\C:\Users\dmtar\Desktop\SD\dev\Clients\Pilot\src\bin\Pilot.jar" ^
                          -Djava.rmi.server.useCodebaseOnly=false\ ^
                          -cp Pilot.jar;lib/* main.Initializer ^
                          %RegistryAddress% %RegistryEnginePort% ^
                          %DepartureAirportName% %PlaneName% %ArrivalAirportName% 
cd ../../../../

cd Clients/Passenger/src/bin
FOR /L %%A IN (0, 1, %MaxPassengerIndex%) DO (
    start "Passenger %%A" cmd /c java -Djava.rmi.server.codebase="file:\C:\Users\dmtar\Desktop\SD\dev\Clients\Passenger\src\bin\Passenger.jar" ^
                                      -Djava.rmi.server.useCodebaseOnly=false\ ^
                                      -cp Passenger.jar;lib/* main.Initializer ^
                                      %%A %RegistryAddress% %RegistryEnginePort% ^
                                      %DepartureAirportName% %PlaneName% %ArrivalAirportName%
)
cd ../../../../

pause
del /s *.class
taskkill /FI "WindowTitle eq Registry Startup*" /T /F