# Fonctionnalités à implémenter
- [x] ajout demande statut
- [x] update demande statut
- [x] liste demande statut
- [x] calcul durée travaillée et update
- [x] api liste alertes demande

# Todo suite forage

**Ajout demande statut**

- [x] créer le dossier views/demandeStatut
- [x] créer le fichier ajout.jsp de ce nouveau dossier
- [x] créer la formulaire d'ajout avec: (field = label + input) 
    - [x] method post, et action = /demandeStatut/creer
    - [x] field refDemande
    - [x] field date avec heure et minute
    - [x] label statut + dropdown des statuts
    - [x] un bouton ajouter
- [x] créer le controller DemandeStatutController
- [x] créer la fonction formAjout qui redirige vers cette page
- [x] ajouter un lien Créer demande statut sur le sidebar
- [x] créer la fonction de DemandeStatutController: creerDemandeStatut()
- [x] tester

**Update demande statut**

- [x] créer le fichier update.jsp du nouveau dossier
- [x] créer la fonction formUpdate($idDemandeStatut) avec le route /demandeStatut/formUpdate/{idDemandeStatut}
- [x] créer la formulaire d'update préremplie avec: (field = label + input) 
    - [x] method post, et action = /demandeStatut/update
    - [x] field refDemande
    - [x] field date avec heure et minute
    - [x] label statut + dropdown des statuts
    - [x] un bouton enregister les modifications
- [ok] créer la fonction updateDemandeStatut() de DemandeStatutController
- [ok] tester

**Liste demande statut**
- [x] créer le fichier liste.jsp du nouveau dossier
- [x] créer la fonction listeDemandeStatut() avec le route /demandeStatut/liste
- [x] ~~créer la fonction getDetailedDemandeStatuts de DemandeStatutRepository (tsy nilaina ihany satria efa misy statut de toute façon le demandestatut)~~

```mysql

    select refDemande, s.libelle as statut, 
    ds.dateChangementStatut as dateChangementStatut from demandestatut ds
    join statut s on ds.id_statut = s.id;

```

- [x] créer le tableau des demandes statuts avec les colonnes Reference demande, Statut, et Date Changement Statut
- [x] boucler les demandes statuts passés à la vue pour construire les lignes avec Reference demande, Statut, et Date Changement Statut, et 
- [x] des boutons modifier et supprimer sur les lignes
- [x] tester

**Calcul durée travaillée et update**
- [x] modifier le type de la colonne dateChangementStatut dans la table demandestatut pour qu'il supporte les minutes et les heures
- [x] modifier le type de l'attribut dateChangementStatut à LocalDateTime (pour supporter les minutes et les heures)
- [x] ajouter une colonne dureeTravaillee dans la table demandestatut
- [x] ajouter l'attribut dureeTravaillee dans le modèle DemandeStatut
- [x] créer la fonction findPreviousDemandeStatut(DemandeStatut demandeStatut) 
```sql
    Select d from DemandeStatut d where d.dateChangementStatut < ds.dateChangementStatut order by d.dateChangementStatut DESC limit 1;
```
- [x] créer la fonction calculerDureeTravaillee(DemandeStatut demandeStatut)
- [x] créer la fonction saveWithDureeTravailleeUpdate(DemandeStatut ds)
- [x] créer la fonction findFollowingDemandeStatuts(DemandeStatut ds)
- [x] ajouter aux fonctions update et ajout le calcul de la duree
- [ok] tester
- [wip] inclure seulement les week-days de 8h à 16h dans le calcul de la duree
- [x] créer la fonction Integer durationWithOnlyWeekdaysAndWorkingHours comme ceci
```pseudo
    fonction durationWithOnlyWeekdaysAndWorkingHours(previousDate, currentDate):
    debutWorkingHour = 08:00
    finWorkingHour = 16:00

    si previous entre 00:00 et debutWorkingHour de son jour
        previous = debutWorkingHour du jour de previous
    fin si

    si previous entre finWorkingHour et 00:00 de son jour
        previous = debutWorkingHour de la journée suivante
    fin si

    si current entre finWorkingHour et 00:00 de son jour
        current = finWorkingHour du jour de current
    fin si

    si current entre 00:00 et debutWorkingHour de son jour
        current = finWorkingHour de la journée précédente
    fin si

    temp = previous
    dureeTravaillee = 0
    tant que temp < current:
        si jour de la semaine de temp entre lundi et vendredi inclusif:
            si temp pas même jour que current:
                dureeTravaillee += 16:00 du jour de temp - temp
            sinon
                dureeTravaillee += current - temp
            fin si
        fin si
        temp = temp + 1 jour à 8h
    fin tant que
```
- [x] intégrer
- [x] tester

**Api liste alertes demande**
- [x] créer la table AlerteParametre (id, id_statut_1, id_statut_2, duree, alerte)
- [x] créer un dossier api-alerte quelque part
- [x] créer un fichier api-alerte.php
- [x] créer le controller AlerteController.java
- [x] créer le route /api/alerte/:refDemande avec la fonction findAlertes(Integer refDemande) -> cette fonction retournerna les alertes en json
- [x] créer la fonction List <DemandeStatut[]> getIntervallesDemandeStatutByDemande(Integer refDemade)
- [x] créer la fonction calculerDureeTravaillee(DemandeStatut d1, DemandeStatut d2)
- [x] créer la fonction List <Alerte> getAlerte(DemandeStatut [] intervalle)
- [x] créer un simple formulaire avec un label reference demande et un input texte, et un bouton valider
- [x] créer le script js, qui va fetcher les alertes de la demande si il y en a, et affichera ces alertes sur le navigateur

- [x] changer la colonne reference de demande en varchar pour que ça soit une vraie référence

- [x] mettre a jour tous les demandes pour que ça soit dans le format REFXX<insert-ref-demande-here> (nouveau script sql)
- [x] mettre a jour la formulaire de creation de demande pour que quand on insère une nouvelle demande, on doit taper manuellement sa référence
- [x] terminer la page de liste des demandes et ajouter son lien sur le sidebar
- [x] ajouter la même fonctionnalité du champ "Reference de la demande" dans la page création de demande statut (blur -> appel ajax -> affiche json en bas)