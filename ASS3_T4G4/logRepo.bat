set RepositoryAddress=l040101-ws01.ua.pt

set sshlogin=sd404
set sshpassword=avioesp4

plink -batch %sshlogin%@%RepositoryAddress% -pw %sshpassword% "cat Repository/src/repo.txt"