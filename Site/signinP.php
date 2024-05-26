<?php
// Inclusion du fichier de connexion à la base de données
include("connexion.php");

// Récupération des données du formulaire
$prenom = $_POST['user_surname'];
$nom = $_POST['user_name'];
$email = $_POST['user_email'];
$password = $_POST['user_password'];
$adress = $_POST['user_adress'];
$phonenumber = $_POST['user_phonenumber'];

// Insertion de l'utilisateur dans la base de données
$sql = "INSERT INTO utilisateurs (nom_client, prenom_client, adresse_mail_client, mot_de_passe_client, adresse_domicile_client, num_tel_client) VALUES ('$nom', '$prenom', '$email', '$password', '$adress', '$phonenumber')";
if (mysqli_query($connexion, $sql)) {
    header('Location: confirmation.html');
} 
else {
    echo "Erreur : " . $sql . "<br>" . mysqli_error($connexion);
}

// Fermeture de la connexion à la base de données
mysqli_close($connexion);
?>