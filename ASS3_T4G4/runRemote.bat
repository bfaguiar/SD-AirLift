set RegistryAddress=l040101-ws02.ua.pt
set RegistryName=registryp404
set RegistryEnginePort=22431
set RegistryListeningPort=22432
set RegistryUnbinds=4

set RepositoryAddress=l040101-ws03.ua.pt
set RepositoryName=repositoryp404
set RepositoryPort=22433
set Logfile=/home/sd404/repo.txt

set DepartureAirportAddress=l040101-ws04.ua.pt
set DepartureAirportName=dpp404
set DepartureAirportPort=22434

set PlaneAddress=l040101-ws05.ua.pt
set PlaneName=planep404
set PlanePort=22435

set ArrivalAirportAddress=l040101-ws06.ua.pt
set ArrivalAirportName=app404
set ArrivalAirportPort=22436

set HostessAddress=l040101-ws07.ua.pt
set PilotAddress=l040101-ws08.ua.pt
set PassengerAddress1=l040101-ws09.ua.pt
set PassengerAddress2=l040101-ws10.ua.pt

set sshlogin=sd404
set sshpassword=avioesp4

set NumberPassengers=21
set MaxPassengerIndex=20
set PlaneMinCapacity=5
set PlaneMaxCapacity=10

start "Registry Startup" cmd /c plink -batch %sshlogin%@%RegistryAddress% -pw %sshpassword% "rmiregistry -J-Djava.rmi.server.hostname=localhost -J-Djava.rmi.server.useCodebaseOnly=false %RegistryEnginePort%" ^& pause

start "Registry" cmd /c plink -batch %sshlogin%@%RegistryAddress% -pw %sshpassword% "cd Registry/ ; java -Djava.rmi.server.codebase='http://l040101-ws02.ua.pt/sd404/Registry/Registry.jar' -Djava.rmi.server.useCodebaseOnly=false\ -Djava.security.policy=java.policy -cp 'Registry.jar:lib/*' main.Initializer %RegistryAddress% %RegistryEnginePort% %RegistryName% %RegistryListeningPort% %RegistryUnbinds%" ^& pause

timeout /t 2
