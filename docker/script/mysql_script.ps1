docker container run --name mysql_chat -d -p 33000:3306 `
  -v mysql_volume:/var/lib/mysql `
  -e MYSQL_ROOT_PASSWORD=1234 -e MYSQL_DATABASE=chat_db `
  -e MYSQL_USER=lastcoder -e MYSQL_PASSWORD=1234 mysql:8.4.5