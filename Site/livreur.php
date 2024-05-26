<?php

include("connexion.php");

// Requête SQL pour récupérer les données des chauffeurs
$sql = 'SELECT nom_livreur, prenom_livreur, vehicle, age_livreur FROM livreur';
$result = mysqli_query($connexion, $sql);

// Affichage des données dans un tableau HTML
echo '<table>';
echo '<tr>';
echo '<th>Nom</th>';
echo '<th>Prénom</th>';
echo '<th>Moyen de locomotion</th>';
echo '<th>Age</th>';
echo '</tr>';

while ($row = mysqli_fetch_assoc($result)) {
    echo '<tr>';
    echo '<td>' . $row['nom_livreur'] . '</td>';
    echo '<td>' . $row['prenom_livreur'] . '</td>';
    echo '<td>' . $row['vehicle'] . '</td>';
    echo '<td>' . $row['age_livreur'] . '</td>';
    echo '</tr>';
}

echo '</table>';

// Fermeture de la connexion à la base de données
mysqli_close($connexion);
?>