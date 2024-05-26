<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <link rel="stylesheet" type="text/css" href="site2.css" />
	<title>Nos chauffeurs</title>
</head>
<body class="BG2">

    <div class="main">
        <div class="barreNav2">
            <a href="accueil2.html">Accueil</a>
            <a href="accueil.html">Se déconecter</a>
            <a href="choix.html">Commander</a>
            <a class="actif" href="chauffeur2.php">Nos chauffeurs</a>
            <a href="panier2.php">Panier</a>
            <a href="surNous2.html">Sur Nous</a>
        </div>


    	<div class="table1">

    	<?php
        include "livreur.php";
        ?>
        </div>



</body>
</html>