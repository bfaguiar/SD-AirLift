set RepositoryAddress=l040101-ws01.ua.pt
set RepositoryPort=40401

set DepartureAirportAddress=l040101-ws02.ua.pt
set DepartureAirportPort=40402

set PlaneAddress=l040101-ws03.ua.pt
set PlanePort=40403

set ArrivalAirportAddress=l040101-ws04.ua.pt
set ArrivalAirportPort=40404

set sshlogin=sd404
set sshpassword=avioesp4

plink -batch %sshlogin%@%RepositoryAddress% -pw %sshpassword% "killall -9 java -u %sshlogin%"
plink -batch %sshlogin%@%DepartureAirportAddress% -pw %sshpassword% "killall -9 java -u %sshlogin%"
plink -batch %sshlogin%@%PlaneAddress% -pw %sshpassword% "killall -9 java -u %sshlogin%"
plink -batch %sshlogin%@%ArrivalAirportAddress% -pw %sshpassword% "killall -9 java -u %sshlogin%"