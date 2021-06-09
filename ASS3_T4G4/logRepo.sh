RepositoryAddress="l040101-ws01.ua.pt"

sshlogin="sd404"
sshpassword="avioesp4"

export SSHPASS=$sshpassword

sshpass -e ssh -o StrictHostKeyChecking=no  $sshlogin@$RepositoryAddress \
"cat Repository/src/repo.txt"


