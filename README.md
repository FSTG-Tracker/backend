# FSTraker - Gestion des Absences Universitaires

## Description
Plateforme de gestion des absences universitaires intégrant un système IA (YOLO + FaceNet).

## Technologies
- Spring Boot 3.x
- Spring Security + JWT
- PostgreSQL
- MapStruct, OpenAPI

## Démarrage rapide

1. Lancer la base de données PostgreSQL:
   - S'assurer d'avoir une base de données `absences_db` avec l'utilisateur `postgres` et mot de passe `password`.

2. Lancer le projet Spring Boot :
```bash
./mvnw spring-boot:run
```

3. Swagger UI :
   - http://localhost:8080/swagger-ui.html

