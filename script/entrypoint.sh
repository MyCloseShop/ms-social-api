#!/bin/bash

# Attendre que MySQL soit prêt en vérifiant la connexion sur le port 3306
until nc -z db-ms-login 3306; do
    echo "En attente de MySQL..."
    sleep 5
done

# Importer les scripts SQL une fois que MySQL est prêt (si nécessaire)
# Assure-toi que l'initialisation est faite uniquement quand nécessaire
if ! mysql -h db-ms-login -uuser -psecret -e "USE db-ms-login"; then
    mysql -h db-ms-login -uuser -psecret < /app/db/migration/base_db.sql
    mysql -h db-ms-login -uuser -psecret < /app/db/migration/V2__.sql
    echo "Base de données et tables créées avec succès"
fi

# Lancer l’application Java
exec java -jar /app.jar