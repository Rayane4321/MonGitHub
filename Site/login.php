<?php

// Récupération des données du formulaire
$email = $_POST['user_email'];
$password = $_POST['user_password'];

// Connexion à la base de données
$dsn = 'mysql:host=localhost;dbname=taxmex;charset=utf8mb4';
$username = 'root';
$password_db = '';
$options = [
    PDO::ATTR_ERRMODE            => PDO::ERRMODE_EXCEPTION,
    PDO::ATTR_DEFAULT_FETCH_MODE => PDO::FETCH_ASSOC,
    PDO::ATTR_EMULATE_PREPARES   => false,
];
try {
    $connexion = new PDO($dsn, $username, $password_db, $options);
} catch (PDOException $e) {
    die('Erreur : ' . $e->getMessage());
}

// Vérification des informations de connexion
$stmt = $connexion->prepare("SELECT * FROM utilisateurs WHERE adresse_mail_client = :email");
$stmt->bindParam(':email', $email);
$stmt->execute();
$user = $stmt->fetch();

$stmt2 = $connexion->prepare("SELECT * FROM utilisateurs WHERE mot_de_passe_client = :password");
$stmt2->bindParam(':password', $password);
$stmt2->execute();
$user2 = $stmt2->fetch();


if ($user && $user2) {
    header("Location: confirmation3.html");
} else {
    // Erreur de connexion
    echo "Erreur de connexion : l'email ou le mot de passe est incorrect.";
}

?>