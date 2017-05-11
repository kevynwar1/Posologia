<?php
use \Psr\Http\Message\ServerRequestInterface as Request;
use \Psr\Http\Message\ResponseInterface as Response;

$app = new \Slim\App;

// List
$app->get('/api/customers/', function(Request $request, Response $response) {
	$sql = "SELECT * FROM customers";
	try {
		$db = new db();
		$db = $db->connect();
		$stmt = $db->query($sql);
		$customers = $stmt->fetchAll(PDO::FETCH_OBJ);
		$db = null;

		return json_encode($customers);
	} catch(PDOException $e) {
		echo "{'error': {'text': '".$e->getMessage()."'}}";
	}
});

// Search
$app->get('/api/customers/{id}', function(Request $request, Response $response) {
	$id = $request->getAttribute('id');
	$sql = "SELECT * FROM customers WHERE id = $id";
	try {
		$db = new db();
		$db = $db->connect();
		$stmt = $db->query($sql);
		$customers = $stmt->fetchAll(PDO::FETCH_OBJ);
		$db = null;
		echo json_encode($customers);
	} catch(PDOException $e) {
		echo "{'error': {'text': '".$e->getMessage()."'}}";
	}
});

// Insert
$app->post('/api/customers/add', function(Request $request, Response $response) {
	$nome = $request->getParam('nome');
	$sobrenome = $request->getParam('sobrenome');

	$sql = "INSERT INTO customers(nome, sobrenome) VALUES (':nome', ':sobrenome')";
	try {
		$db = new db();
		$db = $db->connect();

		$stmt = $db->prepare($sql);
		$stmt->bindParam(':nome', $nome);
		$stmt->bindParam(':sobrenome', $sobrenome);
		$stmt->execute();

		echo json_encode($customers);
	} catch(PDOException $e) {
		echo "{'error': {'text': '".$e->getMessage()."'}}";
	}
});


// Update
$app->put('/api/customers/update/{id}', function(Request $request, Response $response) {
	$id = $request->getAttribute('id');

	$nome = $request->getParam('nome');
	$sobrenome = $request->getParam('sobrenome');

	$sql = "UPDATE customers SET (':nome', ':sobrenome') WHERE id = $id";
	try {
		$db = new db();
		$db = $db->connect();

		$stmt = $db->prepare($sql);
		$stmt->bindParam(':nome', $nome);
		$stmt->bindParam(':sobrenome', $sobrenome);
		$stmt->execute();

		echo "Alterado";
	} catch(PDOException $e) {
		echo "{'error': {'text': '".$e->getMessage()."'}}";
	}
});


// Delete
$app->delete('/api/customers/delete/{id}', function(Request $request, Response $response) {
	$id = $request->getAttribute('id');
	$sql = "DELETE FROM customers WHERE id = $id";
	try {
		$db = new db();
		$db = $db->connect();
		$stmt = $db->prepare($sql);
		$stmt->execute();
		$db = null;
		echo "Excluido";
	} catch(PDOException $e) {
		echo "{'error': {'text': '".$e->getMessage()."'}}";
	}
});