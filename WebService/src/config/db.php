<?php
class db {
	private $dbhost = 'mysql.hostinger.com.br';
	private $dbuser = 'u327363239_coop';
	private $dbpass = '34663827';
	private $dbname = 'u327363239_coop';

	public function connect() {
		$mysql_connect_str = "mysql:host=$this->dbhost;dbname=$this->dbname;";
		$dbConnection = new PDO($mysql_connect_str, $this->dbuser, $this->dbpass);
		$dbConnection->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);

		return $dbConnection;
	}	
}
?>