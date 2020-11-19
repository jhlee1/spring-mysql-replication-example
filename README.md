# spring-mysql-replication-example



## docker-compose.yml
```
version: '3'
services:
  maria-master:
    image: "mariadb:10.4"
    environment:
      MYSQL_ROOT_PASSWORD: master
    ports:
      - 3301:3306
    volumes:
      - ./master-init/cnf:/etc/mysql/conf.d
      - ./master-init/init-scripts:/docker-entrypoint-initdb.d
  maria-replica:
    image: "mariadb:10.4"
    environment:
      MYSQL_ROOT_PASSWORD: replica
    ports:
      - 3302:3306
    volumes:
      - ./replica-init/cnf:/etc/mysql/conf.d
      - ./replica-init/init-scripts:/docker-entrypoint-initdb.d
    depends_on:
      - maria-master
```
