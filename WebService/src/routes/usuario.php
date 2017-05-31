<?php
use \Psr\Http\Message\ServerRequestInterface as Request;
use \Psr\Http\Message\ResponseInterface as Response;

$app = new \Slim\App;

// List
$app->get('/api/usuario/', function(Request $request, Response $response) {
	$sql = "SELECT * FROM user ORDER BY id ASC";
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
	$sql = "SELECT * FROM user WHERE id = $id";
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

// Entrar
$app->post('/api/usuario/entrar', function(Request $request, Response $response) {
	$data = json_decode(file_get_contents('php://input'), true);

	$email = $data['email'];
	$senha = $data['senha'];

	$sql = "SELECT * FROM user WHERE email = '$email' AND senha = '$senha'";
	try {
		$db = new db();
		$db = $db->connect();
		$stmt = $db->query($sql);
		$user = $stmt->fetchAll(PDO::FETCH_OBJ);
		$db = null;

		echo $user[0]->id.", ";
		echo $user[0]->nome.", ";
		echo $user[0]->email.", ";
		echo $user[0]->senha;
	} catch(PDOException $e) {
		echo "{'error': {'text': '".$e->getMessage()."'}}";
	}
});

// Insert
$app->post('/api/usuario/add', function(Request $request, Response $response) {
	$data = json_decode(file_get_contents('php://input'), true);

	$nome  = $data['nome'];
	$email = $data['email'];
	$senha = $data['senha'];

	$sql = "INSERT INTO user(nome, email, senha) VALUES ('$nome', '$email', '$senha')";

	try {
		$db = new db();
		$db = $db->connect();

		$stmt = $db->prepare($sql);
		$stmt->bindParam(':nome', $nome);
		$stmt->bindParam(':email', $email);
		$stmt->bindParam(':senha', $senha);

		$stmt->execute();
	} catch(PDOException $e) {
		echo "{'error': {'text': '".$e->getMessage()."'}}";
	}
});

// Update
$app->put('/api/usuario/update/{id}', function(Request $request, Response $response) {
	$data = json_decode(file_get_contents('php://input'), true);

	$id = $request->getAttribute('id');

	$nome  = $data['nome'];
	$email = $data['email'];
	$senha = $data['senha'];

	$sql = "UPDATE user SET nome = '$nome', email = '$email', senha = '$senha' WHERE id = $id";

	try {
		$db = new db();
		$db = $db->connect();

		$stmt = $db->prepare($sql);
		$stmt->bindParam(':nome', $nome);
		$stmt->bindParam(':email', $email);
		$stmt->bindParam(':senha', sha1(md5($senha)));
		$stmt->execute();
	} catch(PDOException $e) {
		echo "{'error': {'text': '".$e->getMessage()."'}}";
	}
});


// Delete
$app->delete('/api/usuario/delete/{id}', function(Request $request, Response $response) {
	$id = $request->getAttribute('id');
	$sql = "DELETE FROM user WHERE id = $id";
	
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