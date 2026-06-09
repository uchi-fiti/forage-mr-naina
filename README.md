# forage-mr-naina
Aperçu du projet:

IT University Andoharanofotsy, Semestre 4 (Mai-Juin 2026, Prof: Mr Naina).

Utiliser le framework Spring MVC pour construire une application web de gestion de forage.

## Principales fonctionnalités:
- création d'une demande de forage
- liste des demandes avec alertes
- création de devis pour une demande 
- liste des devis
- ajout de nouveau statut pour une demande
- liste de tous les statuts
- un api pour voir les alertes des demandes

## Ce que j'ai appris
1. Quand c'est une relation Many To Many, on doit utiliser une table d'association (DemandeStatut dans notre cas)
2. Si c'est Many To One, les Many doit avoir des clés étrangères du One
3. Les syntaxes JSTL comme `<c:forEach>`, `<c:if>`, `<c:choose>` et `<c:when>`
4. Le type d'input 'datetime-local' pour entrer une date avec le temps en heure et en minutes
5. Comment ne pas afficher un élément en html avec le style display: none; et que c'est mieux avec une classe .hidden si on veut l'utiliser dans js
6. Quand on passe un map dans un vue jsp avec jstl, `map[key]` est équivalente à `map.get(key)`
Je peux toujours continuer mais ce sont les principales choses que j'ai apprises

## Comment compiler et tester
> **Niveau requis :** cette section suppose que vous savez installer un logiciel et ouvrir un terminal. Chaque étape est détaillée, suivez-les dans l'ordre.

  

---

  

### Étape 1: Installer les outils nécessaires

  

Installez les outils suivants si ce n'est pas déjà fait :

  

| Outil                 | Rôle                        | Lien                                                       |
| --------------------- | --------------------------- | ---------------------------------------------------------- |
| Java JDK 17+          | Faire tourner l'application | [Télécharger](https://adoptium.net/)                       |
| Maven                 | Compiler le projet          | [Télécharger](https://maven.apache.org/download.cgi)       |
| Apache Tomcat 11.0.15 | Serveur web Java            | [Télécharger](https://tomcat.apache.org/download-11.cgi)   |
| XAMPP                 | Base de données + API       | [Télécharger](https://www.apachefriends.org/fr/index.html) |


  

> 💡 **Comment savoir si Java est déjà installé ?** Ouvrez un terminal et tapez `java -version`. Si vous voyez un numéro de version 17 ou plus, c'est bon. Sinon, installez-le via le lien ci-dessus.

  

---

  

### Étape 2: Démarrer XAMPP

  

1. Ouvrez **XAMPP**.

2. Cliquez sur **Start** à côté de **Apache Web Server** et de **MySQL Database**.

3. Les "status" doivent être tous les deux "Running"

  

---

  

### Étape 3: Créer la base de données

  

1. Ouvrez votre navigateur et allez sur `http://localhost/phpmyadmin`.

2. Dans le menu de gauche, cliquez sur **New** (ou Nouveau).

3. Sur **Database Name**, mettez forageDB.

4. Cliquez sur l'onglet **Import** en haut.

5. Cliquez sur **Browse** et cherchez reset-forage-db.sql dans le dossier du projet et sélectionnez ce fichiez.

6. Faites défiler vers le bas et cliquez sur **Import**.

  

---

  

### Étape 4: Déployer l'API

  

Il faut copier le dossier `api-alerte` dans le dossier `htdocs` de XAMPP.


| Votre système | Chemin de destination                    |
| ------------- | ---------------------------------------- |
| macOS         | `/Applications/XAMPP/xamppfiles/htdocs/` |
| Linux         | `/opt/lampp/htdocs`                      |
| Windows       | `C:\xampp\htdocs\`                      |


  

**macOS / Linux**: dans un terminal :

```bash

# macOS

cp -r api-alerte/ /Applications/XAMPP/xamppfiles/htdocs/

  

# Linux

cp -r api-alerte/ /opt/lampp/htdocs/

```

  

**Windows**: dans PowerShell :

```powershell

Copy-Item -Recurse api-alerte\ C:\xampp\htdocs\

```

  

> 💡 Vous pouvez aussi faire un simple **copier-coller** du dossier `api-alerte` depuis l'explorateur de fichiers vers le dossier `htdocs`.

  

Une fois copié, l'API sera accessible à : `http://localhost/api-alerte/`

  

---

  

### Étape 5: Compiler et lancer l'application

  

#### Trouver le dossier de Tomcat

  

Repérez le dossier Tomcat qui contient les sous-dossiers `bin/` et `webapps/`.

  

| Système | Chemins fréquents |

|---|---|

| macOS (Homebrew) | `/opt/homebrew/Cellar/tomcat/11.0.15/libexec/` |

| Linux | `/opt/tomcat/` ou `/usr/share/tomcat/` |

| Windows | `C:\Program Files\Apache Software Foundation\Tomcat 11.0\` |

  

> 💡 **Vous ne savez pas où est Tomcat ?**

> - macOS/Linux : tapez `find / -name "catalina.sh" 2>/dev/null` dans le terminal.

> - Windows : tapez `where catalina.bat` dans l'invite de commande.

> 📝 **Notez ces chemins quelque part**

> 💡 Lors de la première exécution, Maven télécharge automatiquement toutes les dépendances du projet. Cela peut prendre quelques minutes selon votre connexion Internet, c'est normal.
#### macOS / Linux

  

Remplacez `/opt/homebrew/Cellar/tomcat/11.0.15/libexec/` ici par le dossier Tomcat de votre système, puis remplacez tout le contenu du fichier deploy.sh dans le dossier du projet, par le bloc de code mis à jour.

  

```bash

# 1. Adaptez ce chemin selon votre installation de Tomcat

TOMCAT_WEBAPPS='/opt/homebrew/Cellar/tomcat/11.0.15/libexec/webapps'

TOMCAT_BIN='/opt/homebrew/Cellar/tomcat/11.0.15/libexec/bin'

  

# 2. Compiler le projet

mvn clean package

  

# 3. Déployer le fichier compilé vers Tomcat

cp -f target/*.war "$TOMCAT_WEBAPPS"

  

# 4. Lancer le serveur

"$TOMCAT_BIN/catalina.sh" run

```

  Localisez le dossier du projet dans votre gestionnaire de fichiers, faites un clic droit sur ce dossier, et ouvrez un terminal sur ce dossier. Puis exécutez la commande `chmod +x ./deploy.sh`  pour changez pouvoir exécuter le script de compilation.

### Windows

Remplacez `C:\Program Files\Apache Software Foundation\Tomcat 11.0` ici par le dossier Tomcat de votre système, puis créez un nouveau fichier `deploy.bat` dans le dossier du projet, puis collez dans le fichier `deploy.bat` le bloc de code mis à jour.

```bat
@echo off

REM 1. Adaptez ce chemin selon votre installation de Tomcat
set TOMCAT_WEBAPPS=C:\Program Files\Apache Software Foundation\Tomcat 11.0\webapps
set TOMCAT_BIN=C:\Program Files\Apache Software Foundation\Tomcat 11.0\bin

REM 2. Compiler le projet
mvn clean package

REM 3. Déployer le fichier compilé vers Tomcat
copy /Y target\*.war "%TOMCAT_WEBAPPS%"

REM 4. Lancer le serveur
"%TOMCAT_BIN%\catalina.bat" run
```

Localisez le dossier du projet dans l'Explorateur de fichiers, cliquez sur la barre d'adresse en haut, tapez `cmd` et appuyez sur **Entrée** pour ouvrir un terminal dans ce dossier. Puis double-cliquez sur `deploy.bat` pour lancer le déploiement.

> 💡 Pas besoin de `chmod` sur Windows, les fichiers `.bat` sont exécutables directement.

---

  

### Étape 6: Ouvrir l'application

Une fois que le terminal affiche `Server startup in [...] ms`, ouvrez votre navigateur et allez sur [le lien de l'application web](http://localhost:8080/forage) 

Voici le lien de l'[API](http://localhost/api-alerte)