#!/bin/bash

# Connect to the mysql container db-ms-login
docker exec -i db-ms-login mysql -uuser -psecret < ./src/main/resources/db/migration/base_db.sql

# Check if the database was created
if [ "$(docker exec -i db-ms-login mysql -uuser -psecret -e 'show databases;' | grep 'db-ms-login')" ]; then
    echo "The database was created successfully"
else
    echo "The database was not created"
fi

docker exec -i db-ms-login mysql -uuser -psecret < ./src/main/resources/db/migration/V2__.sql

# Check if the tables were created
if [ "$(docker exec -i db-ms-login mysql -uuser -psecret -e 'show tables;' db-ms-login | grep 'user')" ]; then
    echo "The tables were created successfully"
else
    echo "The tables were not created"
fi

# Echo success message
echo "The database was created successfully"