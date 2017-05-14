<?php
use \Psr\Http\Message\ServerRequestInterface as Request;
use \Psr\Http\Message\ResponseInterface as Response;

$app = new \Slim\App;

// List
$app->get('/api/usuario/', function(Request $request, Response $response) {
	$sql = "SELECT * FROM usuario";
	try {
		$db = new db();
		$db = $db->connect();
		$stmt = $db->query($sql);
		$user = $stmt->fetchAll(PDO::FETCH_OBJ);
		$db = null;

		return json_encode($user);
	} catch(PDOException $e) {
		echo "{'error': {'text': '".$e->getMessage()."'}}";
	}
});

// Search
$app->get('/api/usuario/{id}', function(Request $request, Response $response) {
	$id = $request->getAttribute('id');
	$sql = "SELECT * FROM usuario WHERE id = $id";
	try {
		$db = new db();
		$db = $db->connect();
		$stmt = $db->query($sql);
		$user = $stmt->fetchAll(PDO::FETCH_OBJ);
		$db = null;

		echo json_encode($user);
	} catch(PDOException $e) {
		echo "{'error': {'text': '".$e->getMessage()."'}}";
	}
});

// Insert
$app->post('/api/usuario/add', function(Request $request, Response $response) {
	$nome 			= $request->getParam('nome');
	$email 			= $request->getParam('email');
	$sexo 			= $request->getParam('sexo');
	$nascimento 	= $request->getParam('nascimento');
	$senha 			= $request->getParam('senha');

	$sql =  "INSERT INTO usuario(nome, email, sexo, nascimento, senha) "; 
	$sql += "VALUES (':nome', ':email', ':sexo', ':nascimento', ':senha')";
	try {
		$db = new db();
		$db = $db->connect();

		$stmt = $db->prepare($sql);
		$stmt->bindParam(':nome'	, $nome);
		$stmt->bindParam(':email'	, $email);
		$stmt->bindParam(':sexo'	, $sexo);
		$stmt->bindParam(':nascimento', $nascimento);
		$stmt->bindParam(':senha'	, sha1(md5($senha)));
		$stmt->execute();
	} catch(PDOException $e) {
		echo "{'error': {'text': '".$e->getMessage()."'}}";
	}
});


// Update
$app->put('/api/usuario/update/{id}', function(Request $request, Response $response) {
	$id 			= $request->getAttribute('id');

	$nome 			= $request->getParam('nome');
	$email 			= $request->getParam('email');
	$sexo 			= $request->getParam('sexo');
	$nascimento 	= $request->getParam('nascimento');
	$senha 			= $request->getParam('senha');

	$sql = "UPDATE usuario ";
	$sql += "SET (':nome', ':email', ':sexo', ':nascimento', ':senha') ";
	$sql += "WHERE id = $id";

	try {
		$db = new db();
		$db = $db->connect();

		$stmt = $db->prepare($sql);
		$stmt->bindParam(':nome'	, $nome);
		$stmt->bindParam(':email'	, $email);
		$stmt->bindParam(':sexo'	, $sexo);
		$stmt->bindParam(':nascimento', $nascimento);
		$stmt->bindParam(':senha'	, sha1(md5($senha)));
		$stmt->execute();
	} catch(PDOException $e) {
		echo "{'error': {'text': '".$e->getMessage()."'}}";
	}
});


// Delete
$app->delete('/api/usuario/delete/{id}', function(Request $request, Response $response) {
	$id = $request->getAttribute('id');
	$sql = "DELETE FROM usuario WHERE id = $id";
	
	try {
		$db = new db();
		$db = $db->connect();
		$stmt = $db->prepare($sql);
		$stmt->execute();
		$db = null;
	} catch(PDOException $e) {
		echo "{'error': {'text': '".$e->getMessage()."'}}";
	}
});