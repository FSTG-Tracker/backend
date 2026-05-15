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
   - http://localhost:8086/swagger-ui.html

## Architecture du Backend

Le backend est conçu selon une architecture en couches (Layered Architecture) classique de Spring Boot, garantissant une séparation claire des responsabilités :

### 1. Couche de Présentation (Controllers)
Les **Controllers** (`@RestController`) exposent les endpoints RESTful. Ils gèrent la réception des requêtes HTTP (DTOs d'entrée), valident les données, font appel à la couche de service, et retournent les réponses formatées (DTOs de sortie) avec les codes HTTP appropriés (200, 201, 404, etc.).

### 2. Couche Métier (Services)
Les **Services** (`@Service`) contiennent toute la logique métier de l'application. Ils orchestrent les opérations complexes, appliquent les règles de gestion (comme le calcul des absences, l'affectation des étudiants) et agissent comme intermédiaire entre les contrôleurs et les dépôts de données.

### 3. Couche d'Accès aux Données (Repositories)
Les **Repositories** (`@Repository`) utilisent Spring Data JPA pour interagir avec la base de données PostgreSQL. Ils simplifient les requêtes CRUD complexes en utilisant des méthodes générées dynamiquement par Spring, ou via des requêtes JPQL/SQL spécifiques (`@Query`).

### 4. Modèle de Données (Entities & DTOs)
- **Entities** (`@Entity`) : Représentent les tables de la base de données PostgreSQL. Elles contiennent les relations (OneToMany, ManyToOne, etc.) entre les objets (ex: `Etudiant`, `Absence`, `Seance`).
- **DTOs (Data Transfer Objects)** : Utilisés pour transférer les données entre le client et le serveur sans exposer directement le modèle de base de données.
- **Mappers** (`@Mapper`) : **MapStruct** est utilisé pour automatiser la conversion entre les Entities et les DTOs, réduisant ainsi le code boilerplate.

### 5. Sécurité (Spring Security & JWT)
L'application est sécurisée avec **Spring Security**. 
L'authentification est de type "Stateless" et s'appuie sur des tokens **JWT (JSON Web Tokens)**. 
- Les requêtes nécessitent un token valide passé dans le header `Authorization: Bearer <token>`.
- Les routes sont protégées selon les rôles (ex: `/api/absences/**` peut nécessiter des droits spécifiques).

### Diagramme de flux de données (Mermaid)

```mermaid
graph TD
    Client(Client Web/Mobile)
    Controller[Controllers<br/>(Endpoints REST)]
    Service[Services<br/>(Logique métier)]
    Repository[Repositories<br/>(Spring Data JPA)]
    DB[(Base de données<br/>PostgreSQL)]
    
    Client -- "Requête HTTP (JSON)" --> Controller
    Controller -- "DTO" --> Service
    Service -- "Entité" --> Repository
    Repository -- "SQL" --> DB
    
    DB -- "ResultSet" --> Repository
    Repository -- "Entité" --> Service
    Service -- "DTO (via MapStruct)" --> Controller
    Controller -- "Réponse HTTP (JSON)" --> Client
```
