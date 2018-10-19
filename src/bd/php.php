<?php
include("DAO.php");
include("Controller.php");

$gettt = $_GET['data'];
//var_dump($gettt);
$controller = new Controller();

$result = $controller->execute($gettt);
//var_dump($result . ' var_dum');
echo $result;
//var_dump($result);

