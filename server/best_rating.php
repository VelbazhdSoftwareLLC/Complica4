<?php
header('Content-type:application/json;charset=utf-8');

require 'database.php';

if(isset($_POST['best_rating'])) {
	open_my_db();
  $result = query_my_db( "SELECT `rating` FROM `neural_networks` ORDER BY `rating` ASC LIMIT 1;" );

  $response = "";
  if($result != false) {
    $response = array( 'rating' => ''.$result[0][0] );
  } else {
    //TODO Use named constant instead number.
    $response = array( 'rating' => ''.PHP_INT_MAX );
  }
  close_my_db();

  echo json_encode($response);
}
?>
