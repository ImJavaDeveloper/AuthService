# AuthService

Auth service will provide functionality of login and validating the JWT token. It will allow authenticated user to login and provide token in response once login is successful.

### Steps to run project

1. Take maven build
2. Run docker file using command - docker compose up --build
3. Login on MySql - docker exec -it mysql-db mysql -uroot -p
4. You might need to reset the MySQL password, check MySQL logs for first time password- docker logs mysql-db

docker logs mysql 2>&1 | grep GENERATED
mysql> ALTER USER 'root'@'localhost' IDENTIFIED BY 'password';

5. Create DB and required tables with data;

CREATE DATABASE AuthZ;

CREATE TABLE users (
userName VARCHAR(30) PRIMARY KEY,
password VARCHAR(100) NOT NULL,
roleId INT NOT NULL
);

CREATE TABLE user_roles (
roleId INT PRIMARY KEY,
username VARCHAR(30) NOT NULL
);

CREATE TABLE roles (
roleId INT PRIMARY KEY,
roleName VARCHAR(100) NOT NULL
);

mysql> insert into users values('rahat','$2a$12$Od2lpHG38IiU5p93S44ax.xlbFLmf.iGr5fg2NcFPgN9xOgpvIsaO',1);

mysql> insert into roles values (1,'ADMIN');

mysql> insert into user_roles values(1,'rahat');

