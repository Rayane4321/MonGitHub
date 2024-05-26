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
        <div class="barreNav">
            <a href="accueil.html">Accueil</a>
            <a href="log.html">Se connecter</a>
            <a href="signin.html">Création de compte</a>
            <a class="actif" href="chauffeur.php">Nos chauffeurs</a>
            <a href="devenirchauffeur.html">Devenir chauffeur</a>
            <a href="surNous.html">Sur Nous</a>
        </div>


    <div class="table1">
    	
        <?php
    	include "livreur.php";
        ?>

        <bouton class="button b1" onclick="window.location.href='devenirchauffeur.html'">Devenir chauffeur</buton>

    </div>



</body>
</html>