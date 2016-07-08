<?php
header('Content-type:application/json;charset=utf-8');

require 'database.php';

/*
 * Load the best network.
 */
if(isset($_POST['load_neural_network'])) {
	open_my_db();
  $result = query_my_db( "SELECT `object`, `rating`, `registered` FROM `neural_networks` ORDER BY `rating` ASC LIMIT 1;" );

  $response = "";
  if($result != false) {
    $response = array('found' => 'true', 'object' => ''.$result[0][0], 'rating' => ''.$result[0][1], 'registered' => ''.$result[0][2] );
  } else {
    $response = array('found' => 'false');
  }
  close_my_db();

  echo json_encode($response);
}
?>
