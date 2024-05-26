<?php

include("connexion.php");

$sql = "SELECT * FROM panier";
$result = mysqli_query($connexion, $sql);

echo '<table>';
echo '<tr><th>Nom</th><th>Prix</th></tr>';

while($row = mysqli_fetch_assoc($result)) {
    echo "<tr><td>" . $row["nom_panier"] . "</td><td>" . $row["prix_panier"] . "</td></tr>";
}

echo "</table>";

// Fermeture de la connexion
mysqli_close($connexion);

?>