# PGH Recycles

## Docker Stuff
To get the container and run it
 * `docker run --name rec-db-container -e MYSQL_ROOT_PASSWORD=pass mysql:latest`

 ## MySql stuff
 To connect to the container
 * install mysql client for cmd
 * `mysql -h 172.0.0.1 -P 3306 --protocol=tcp -u root -p`
    * The `-h` specifies the IP address. This will change per machine probably.
    * `docker inspect -f '{{range .NetworkSettings.Networks}}{{.IPAddress}}{{end}}' container_id_or_name` should give you the ip

## Running MySql on container
 To run the sql scripts against the mysql container:
 * go in to the directory with the sql scripts
 * `docker exec -i rec-db-container mysql -u root -ppass < rec-tables.sql`
