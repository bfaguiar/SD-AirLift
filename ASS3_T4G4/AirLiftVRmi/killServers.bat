set RegistryAddress=l040101-ws02.ua.pt
set RepositoryAddress=l040101-ws03.ua.pt
set DepartureAirportAddress=l040101-ws04.ua.pt
set PlaneAddress=l040101-ws05.ua.pt
set ArrivalAirportAddress=l040101-ws06.ua.pt

set sshlogin=sd404
set sshpassword=avioesp4

plink -batch %sshlogin%@%RegistryAddress% -pw %sshpassword% "killall -9 rmiregistry -u %sshlogin%"
plink -batch %sshlogin%@%RepositoryAddress% -pw %sshpassword% "killall -9 java -u %sshlogin%"
plink -batch %sshlogin%@%DepartureAirportAddress% -pw %sshpassword% "killall -9 java -u %sshlogin%"
plink -batch %sshlogin%@%PlaneAddress% -pw %sshpassword% "killall -9 java -u %sshlogin%"
plink -batch %sshlogin%@%ArrivalAirportAddress% -pw %sshpassword% "killall -9 java -u %sshlogin%"