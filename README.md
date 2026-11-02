# DiscordRolesPlugin

Plugin Spigot destiné à relier un serveur Minecraft à un serveur Discord afin d’associer les rôles Discord aux joueurs Minecraft. Il ajoute également des outils de modération, des annonces synchronisées avec Discord, un formatage du chat par rôle et quelques éléments d’interface propres au serveur NukaMC.

> **État du projet :** prototype fonctionnel basé sur Spigot 1.16.x et JDA 5.

## Fonctionnalités

- Connexion du plugin à un bot Discord avec JDA.
- Vérification manuelle d’un compte Discord à l’aide d’un code envoyé en message privé.
- Récupération du rôle Discord le plus élevé d’un membre vérifié.
- Attribution d’un rôle Minecraft affiché dans :
  - le préfixe du joueur dans le chat ;
  - la liste des joueurs ;
  - le nom du joueur dans le tablist ;
  - les messages envoyés sur Discord.
- Synchronisation du chat Minecraft vers un salon Discord sous forme d’embed.
- Annonces serveur et annonces de développement visibles dans Minecraft et Discord.
- Notification Discord lors du démarrage et de l’arrêt du serveur.
- Message de bienvenue et titre affiché lors de la connexion d’un joueur.
- En-tête et pied de page animés dans la tablist, avec le ping et le nombre de joueurs.
- Téléportation vers une position prédéfinie lors d’un clic droit sur un panneau en jungle.
- Menu « Premium » préparé dans le code, accessible depuis `/premiummenu` lorsqu’il est enregistré par la configuration du plugin.

## Architecture

Le plugin est organisé autour de cinq classes principales :

| Classe | Responsabilité |
|---|---|
| `DiscordRolesPlugin` | Point d’entrée Bukkit, commandes, persistance des joueurs vérifiés et gestion du cycle de vie. |
| `DiscordPlayerJoin` | Connexion JDA, traitement des connexions Minecraft, affichage des rôles et animation de la tablist. |
| `RoleSystem` | Stockage temporaire du rôle Minecraft de chaque joueur et relais du chat vers Discord. |
| `Badge` | Association entre un nom de rôle et son badge affiché dans le chat/tablist. |
| `OnPlayerInteract` | Téléportation lors d’un clic droit sur un `JUNGLE_SIGN`. |

## Déroulement général

### Démarrage

Lors de l’activation :

1. Le fichier `verified_players.txt` est lu depuis le répertoire courant du serveur.
2. Le menu premium et ses éléments sont créés en mémoire.
3. Les listeners Bukkit sont enregistrés.
4. JDA est initialisé avec le token défini dans `DiscordPlayerJoin`.
5. Un embed « Server Start Up! » est envoyé dans le salon Discord configuré.

### Connexion d’un joueur

À chaque connexion, le plugin :

1. affiche un message de bienvenue et un titre ;
2. recherche le rôle Minecraft déjà associé au joueur ;
3. applique un préfixe coloré et un badge dans la tablist ;
4. utilise `Wastelander` comme rôle par défaut lorsqu’aucun rôle n’est trouvé ;
5. actualise périodiquement l’en-tête et le pied de page de la tablist.

La liste des joueurs est triée selon une hiérarchie interne : `Owner`, `Head-Admin`, `Admin`, `Super-Moderator`, `Moderator`, `Developer`, `Builder`, `Server Booster`, `Faction Leader`, `Beta Tester`, puis `Wastelander`.

### Vérification Discord

Le processus prévu est le suivant :

1. Le joueur exécute `/startverification <discord_id>`.
2. Le bot vérifie que l’identifiant appartient au serveur Discord configuré.
3. Un code à six chiffres est envoyé en message privé Discord.
4. Le joueur exécute `/verify <code> <discord_id>`.
5. L’association joueur Minecraft/identifiant Discord est conservée en mémoire puis écrite dans `verified_players.txt` à l’arrêt du plugin.
6. `/refreshRoles <discord_id>` récupère le rôle Discord le plus élevé et met à jour le rôle Minecraft.

Un code de vérification expire après cinq minutes. Les rôles ne sont pas automatiquement recalculés en continu : le joueur doit utiliser `/refreshRoles` ou se reconnecter.

### Chat et Discord

Chaque message Minecraft est formaté selon le rôle du joueur et envoyé dans un salon Discord fixe sous forme d’embed. Le message Discord contient le contenu, le nom du joueur et son rôle le plus élevé.

Les rôles pris en charge dans l’affichage sont notamment :

`Developer`, `Moderator`, `Super-Moderator`, `Head-Admin`, `Owner`, `Builder`, `Admin`, `Wastelander`, `Server Booster`, `Faction Leader`, `Beta Tester` et `In-Test`.

## Commandes

| Commande | Utilisation | Description |
|---|---|---|
| `/hello` | `/hello` | Répond avec un message de bienvenue. |
| `/devannounce` | `/devannounce <message>` | Publie une annonce de développement dans Minecraft et Discord. Réservée aux développeurs. |
| `/serverannounce` | `/serverannounce <message>` | Publie une annonce serveur dans Minecraft et Discord. Réservée aux rôles de direction/modération. |
| `/requestAdmin` | `/requestAdmin <raison>` | Informe les administrateurs et modérateurs connectés qu’un joueur demande de l’aide. |
| `/startverification` | `/startverification <discord_id>` | Démarre la vérification Discord. |
| `/verify` | `/verify <code> <discord_id>` | Valide le code reçu par message privé. |
| `/refreshRoles` | `/refreshRoles <discord_id>` | Actualise le rôle Minecraft depuis Discord. |
| `/manualverify` | `/manualverify <joueur> <discord_id>` | Ajoute manuellement une association. Réservée aux développeurs. |
| `/resetverifiedplayers` | `/resetverifiedplayers <joueur> <discord_id>` | Supprime manuellement une association. Réservée aux développeurs. |
| `/refreshallroles` | `/refreshallroles` | Prépare le tri des joueurs connectés. Réservée aux développeurs. |
| `/premiummenu` | `/premiummenu` | Ouvre le menu premium préparé par le plugin. |
| `/tpspawn` | `/tpspawn` | Déclarée dans `plugin.yml`, mais aucune logique correspondante n’est actuellement implémentée. |

Les commandes déclarées dans `plugin.yml` mais sans logique complète dans le code sont à considérer comme incomplètes. Les permissions Bukkit ne sont pas déclarées dans `plugin.yml` : les contrôles d’accès sont réalisés directement selon le rôle stocké en mémoire.

## Installation

### Prérequis

- Serveur Spigot compatible avec l’API `1.16`.
- Java 8 ou version compatible avec les classes compilées actuellement fournies.
- Bibliothèque JDA 5.0.0-beta.9 avec ses dépendances.
- Un bot Discord avec accès au serveur Discord cible, aux membres et aux messages privés.

### Procédure

1. Compiler le projet avec les dépendances Spigot et JDA disponibles.
2. Copier le JAR obtenu dans le dossier `plugins/` du serveur.
3. Configurer le token du bot dans `DiscordPlayerJoin.java`.
4. Vérifier les identifiants du serveur Discord et du salon utilisés dans `DiscordRolesPlugin.java` et `DiscordPlayerJoin.java`.
5. Démarrer le serveur et vérifier les logs ainsi que le salon Discord.

Le fichier `build/DivPlugin-1.16.5.jar` est un artefact déjà compilé présent dans le dépôt. Il est recommandé de produire un nouveau JAR après toute modification du code.

## Configuration actuelle

Les valeurs suivantes sont codées en dur :

- token du bot Discord ;
- identifiant du serveur Discord ;
- identifiant du salon Discord ;
- position de téléportation ;
- adresse affichée dans la tablist (`nukamc.com`) ;
- capacité affichée de la tablist (`20` joueurs).

Pour une utilisation en production, le token ne doit pas être stocké dans le code source. Il est recommandé de le déplacer vers une configuration locale ou une variable d’environnement et de régénérer immédiatement tout token ayant été exposé.

## Persistance et limites connues

- Les associations vérifiées sont enregistrées dans `verified_players.txt` lors de l’arrêt du plugin.
- Le chemin du fichier est relatif au répertoire de lancement du serveur.
- Les rôles et badges sont conservés en mémoire pendant l’exécution.
- Les identifiants Discord ne sont pas configurables sans modifier le code.
- Plusieurs opérations JDA sont lancées depuis des événements Bukkit et le plugin dépend de la disponibilité du bot et du salon ciblé.
- Le menu premium contient actuellement un objet Elytra, mais aucun gestionnaire d’inventaire n’est présent pour lui attribuer un comportement.
- Le fichier `plugin.yml` ne déclare pas toutes les commandes utilisées par le code, notamment `/premiummenu`.
- Les arguments de plusieurs commandes ne sont pas validés avant accès ; une saisie incomplète peut donc provoquer une erreur.
- Le code utilise des comparaisons de chaînes et des contrôles de rôles dispersés, ce qui rend l’ajout de nouveaux rôles plus difficile.

## Développement

Le projet utilise une structure Eclipse simple :

```text
src/
└── fr/div/roleplugin/
    ├── Badge.java
    ├── DiscordPlayerJoin.java
    ├── DiscordRolesPlugin.java
    ├── OnPlayerInteract.java
    └── RoleSystem.java
plugin.yml
```

Les dépendances ne sont pas gérées par Maven ou Gradle dans le dépôt actuel. Le fichier `.classpath` référence des JAR locaux propres à l’environnement de développement initial ; ces chemins doivent être adaptés avant une compilation sur une autre machine.

## Licence
License (License)
