<?php

if ($_SERVER["REQUEST_METHOD"] == "POST") {
  
  // Récupérer les informations du produit sélectionné
  $nom_panier = $_POST["nom"];
  $prix_panier = $_POST["prix"];
  
  include("connexion.php");

  // Insertion des données dans la table panier
  $sql = "INSERT INTO Panier (nom_panier, prix_panier) VALUES ('$nom_panier', '$prix_panier')";

  if (mysqli_query($connexion, $sql)) {
    header('Location: confirmation_panier.html');
  } else {
    echo "Erreur lors de l'ajout au panier: " . $sql . mysqli_error($connexion);
  }

  mysqli_close($connexion);
}
?>