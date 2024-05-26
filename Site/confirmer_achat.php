<?php

include("connexion.php");

// Suppression des données de la table panier
$req = $connexion->query("TRUNCATE TABLE Panier");

// Fermeture de la connexion à la base de données
mysqli_close($connexion);

// Affichage d'un message de confirmation
header('Location: confirmation_achat.html');?>