<?php
// Inclusion du fichier de connexion à la base de données
include("connexion.php");

// Récupération des données du formulaire
$prenom = $_POST['user_surname'];
$nom = $_POST['user_name'];
$email = $_POST['user_email'];
$age = $_POST['user_age'];
$locomotion = $_POST['Moyen_locomotion'];

// Insertion de l'utilisateur dans la base de données
$sql = "INSERT INTO livreur (nom_livreur, prenom_livreur, email, vehicle, age_livreur) VALUES ('$nom', '$prenom', '$email', '$locomotion', '$age')";
if (mysqli_query($connexion, $sql)) {
    header('Location: confirmation2.html');
} 
else {
    echo "Erreur : " . $sql . "<br>" . mysqli_error($connexion);
}

// Fermeture de la connexion à la base de données
mysqli_close($connexion);
?>