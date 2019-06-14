<?php
/**
 * Created by IntelliJ IDEA.
 * User: Nikolaj Wassmann
 * Date: 14-06-2019
 * Time: 09:40
 */
//Starts the session
session_start();


$_SESSION["id"];
$data = $_POST['userJson'];

//Unsets the session
session_unset();


//Destroys the session
session_destroy();

