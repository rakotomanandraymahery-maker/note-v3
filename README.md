# 🌊 Système de Gestion de Forage

## 📖 Qu'est-ce que ce projet ?

C'est une **application web de gestion** pour une entreprise de forage qui permet de :
- **Gérer les clients** : Ajouter, modifier, supprimer des entreprises clientes
- **Gérer les demandes de forage** : Créer des commandes avec dates, districts, devis
- **Visualiser les données** : Interface web simple et intuitive

---

## 🎯 Objectif Pédagogique

Ce projet est **parfait pour les débutants** qui veulent apprendre :
- ✅ Spring Boot (framework Java)
- ✅ MySQL (base de données)
- ✅ Architecture en couches (Controller → Service → Repository)
- ✅ REST API (web services)
- ✅ HTML/CSS/JavaScript (interface web)

---

## 🏗️ Architecture Simplifiée

```
📱 Interface Web (HTML/JS)
       ↓
🌐 REST API (Controller)
       ↓
⚙️ Logique Métier (Service)
       ↓
🗄️ Base de Données (Repository)
       ↓
💾 MySQL
```

---

### 📋 Explication de chaque couche

#### 1. **Model** (Les données)
```java
@Entity  // "Je suis une table dans la base de données"
public class Client {
    @Id  // "Je suis l'identifiant unique"
    private Long id;
    private String nom;  // "Nom de l'entreprise"
    // ...
}
```
**Rôle** : Définir la structure des données

#### 2. **Repository** (La communication)
```java
public interface ClientRepository extends JpaRepository<Client, Long> {
    // Spring génère AUTOMATIQUEMENT toutes les méthodes :
    // save(), findAll(), deleteById(), etc.
}
```
**Rôle** : Parler à la base de données MySQL

#### 3. **Service** (La logique)
```java
@Service
public class ClientService {
    public List<Client> getAllClients() {
        return clientRepository.findAll();
    }
}
```
**Rôle** : Contenir les règles métier

#### 4. **Controller** (L'interface web)
```java
@RestController
public class ClientController {
    @GetMapping("/api/clients")
    public List<Client> getClients() {
        return clientService.getAllClients();
    }
}
```
**Rôle** : Créer les URLs de l'API

---

## 🗄️ Base de Données

### Tables créées automatiquement

#### **Table CLIENTS**
```sql
CREATE TABLE clients (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,  -- ID unique
    nom VARCHAR(255),                       -- Nom de l'entreprise
    contact VARCHAR(255),                   -- Email/téléphone
    devis DOUBLE                            -- Montant du contrat
);
```

#### **Table DEMANDES**
```sql
CREATE TABLE demandes (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,  -- ID unique
    date DATE,                             -- Date de la demande
    district VARCHAR(255),                  -- Localisation
    devis DOUBLE,                           -- Montant estimé
    client_id BIGINT,                      -- Référence au client
    FOREIGN KEY (client_id) REFERENCES clients(id)
);
```

### Relation entre les tables
```
CLIENTS (1) ←→ (PLUSIEURS) DEMANDES
Un client peut avoir plusieurs demandes
```

---

## 🌐 Comment ça marche ?

### Étape 1 : Démarrer l'application
```bash
mvn spring-boot:run
```

### Étape 2 : Ouvrir le navigateur
```
http://localhost:8080
```

### Étape 3 : Utiliser l'interface
1. **Ajouter un client** : Remplir le formulaire et cliquer "Ajouter"
2. **Voir les clients** : Tableau qui s'actualise automatiquement
3. **Ajouter une demande** : Sélectionner un client et remplir les infos
4. **Supprimer** : Cliquer sur les boutons "Supprimer"

---

## 🔄 Flux d'une opération (Exemple : Ajouter un client)

1. **Utilisateur** : Remplit le formulaire HTML
2. **JavaScript** : Envoie les données à `POST /api/clients`
3. **Controller** : Reçoit la requête HTTP
4. **Service** : Applique les règles métier
5. **Repository** : Exécute `INSERT INTO clients...`
6. **MySQL** : Sauvegarde les données
7. **Retour** : Le nouveau client est renvoyé en JSON
8. **JavaScript** : Met à jour le tableau HTML

---

## 📚 Technologies utilisées

| Technologie | Rôle | Pourquoi ? |
|-------------|------|------------|
| **Spring Boot** | Framework principal | Facilite le développement Java |
| **Spring Data JPA** | Accès aux données | Simplifie les requêtes SQL |
| **MySQL** | Base de données | Stockage persistant des données |
| **Jackson** | JSON | Conversion Java ↔ JSON |
| **HTML/CSS/JS** | Interface web | Interface utilisateur simple |

---

## 🎯 Concepts clés à comprendre

### REST API
- **GET** : Lire des données (`/api/clients`)
- **POST** : Créer des données (`/api/clients`)
- **PUT** : Mettre à jour des données
- **DELETE** : Supprimer des données (`/api/clients/1`)

### Annotations Spring
- `@Entity` : "Je suis une table de base de données"
- `@RestController` : "Je crée des URLs d'API"
- `@Service` : "Je contiens la logique métier"
- `@Repository` : "Je parle à la base de données"
- `@Autowired` : "Injecte-moi automatiquement cette dépendance"

### JSON (Format d'échange)
```json
{
    "id": 1,
    "nom": "Société Alpha",
    "contact": "contact@alpha.com",
    "devis": 15000.0
}
```

---

## 🚀 Comment utiliser ce projet

### Pour apprendre
1. **Lisez le code** : Chaque fichier est commenté
2. **Modifiez-le** : Ajoutez des champs, changez les règles
3. **Testez** : Utilisez l'interface et observez les résultats
4. **Explorez** : Regardez la base de données MySQL

### Pour développer
1. **Clonez le projet**
2. **Configurez MySQL** (créez la base `forage_db`)
3. **Lancez l'application**
4. **Ouvrez `http://localhost:8080`**

---

## 🎓 Pourquoi ce projet est génial pour apprendre ?

✅ **Architecture professionnelle** : Séparation claire des responsabilités  
✅ **Technologies modernes** : Spring Boot, MySQL, REST API  
✅ **Code simple** : Pas de complexité inutile  
✅ **Documentation complète** : Chaque partie est expliquée  
✅ **Évolutif** : Facile à améliorer et à étendre  

---

## 🔄 Étapes suivantes recommandées

1. **Ajouter de la validation** : Vérifier que les champs sont valides
2. **Améliorer l'interface** : Design plus moderne
3. **Ajouter la recherche** : Chercher des clients/demandes
4. **Exporter les données** : CSV, PDF
5. **Ajouter des tests** : JUnit pour tester le code
6. **Déployer** : Mettre en ligne sur un serveur

---

## 💡 Conseils pour les débutants

- **Ne modifiez pas tout d'un coup** : Changez une chose à la fois
- **Lisez les logs** : Ils expliquent ce qui se passe
- **Testez souvent** : Vérifiez que ça marche après chaque modification
- **Posez des questions** : Chaque concept a une explication

---

## 📞 Besoin d'aide ?

- **Lisez les commentaires** dans le code source
- **Consultez les logs** de l'application
- **Testez avec** `http://localhost:8080/debug.html`

**Bon apprentissage !** 🚀

---

## 📁 Structure des fichiers

```
forage/
├── pom.xml                                    # Dépendances Maven
├── README.md                                  # Ce fichier
└── src/
    └── main/
        ├── java/
        │   └── com/
        │       └── example/
        │           └── forage/
        │               ├── ForageApplication.java    # Démarrage
        │               ├── controller/              # URLs de l'API
        │               │   ├── ClientController.java # Gestion clients
        │               │   └── DemandeController.java # Gestion demandes
        │               ├── service/                 # Logique métier
        │               │   ├── ClientService.java    # Logique clients
        │               │   └── DemandeService.java    # Logique demandes
        │               ├── repository/              # Base de données
        │               │   ├── ClientRepository.java # Accès clients
        │               │   └── DemandeRepository.java # Accès demandes
        │               ├── model/                   # Entités
        │               │   ├── Client.java           # Table clients
        │               │   └── Demande.java          # Table demandes
        │               └── config/                  # Configuration
        │                   └── CorsConfig.java      # CORS
        └── resources/
            ├── application.properties               # Configuration
            └── static/                             # Interface web
                ├── index.html                      # Page principale
                ├── app.js                          # Logique JavaScript
                └── debug.html                      # Page de test
```

---

## 🎯 C'est votre premier pas vers le développement d'applications web professionnelles !
