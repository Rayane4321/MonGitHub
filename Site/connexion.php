<?php
$serveur = "localhost";
$utilisateur = "root";
$mdp = "";
$bdd = "taxmex";

// Connexion à la base de données
$connexion = mysqli_connect($serveur, $utilisateur, $mdp, $bdd);

// Vérification de la connexion
if (!$connexion) {
    die("La connexion a échoué: " . mysqli_connect_error());
}
?>