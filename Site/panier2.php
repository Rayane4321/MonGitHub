<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <link rel="stylesheet" type="text/css" href="site2.css" />
	<title>Votre Panier</title>
</head>
<body class="BG2">

    <div class="main">
        <div class="barreNav2">
            <a href="accueil2.html">Accueil</a>
            <a href="accueil.html">Se déconecter</a>
            <a href="choix.html">Commander</a>
            <a href="chauffeur2.php">Nos chauffeurs</a>
            <a class="actif" href="panier2.php">Panier</a>
            <a href="surNous2.html">Sur Nous</a>
        </div>



    <div class="panier">
    	
        <?php
    	include "panier.php";
        ?>
    </div>



        <p class="text3">Plata o plomo ! <br> on accepte que des payements en métalique, préparez votre poche ! </p>

        <form method="post" action="confirmer_achat.php">
            <input class="button panier_button" type="submit" value="Confirmer l'achat">

    </div>
        </form>

    


</body>
</html>