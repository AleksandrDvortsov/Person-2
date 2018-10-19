<?php

class DAO
{
    var $servername = "localhost";
    var $username = "root";
    var $password = "";
    var $dbname = "hw_bd_24.07";

    var $connection;
    var $persons;
    var $stmt;

    public function connect()
    {
        $this->connection = new PDO("mysql:dbname=" .$this->dbname . ";host=" .$this->servername, $this->username, $this->password);
    }
    private function searchForPersonFromList($name)
    {
        $query = "SELECT * FROM person WHERE name="."'".$name."'" ;
        $this->stmt = $this->connection->prepare($query);
        $this->stmt->execute();
        $this->persons = $this->stmt->fetchAll(PDO::FETCH_ASSOC);
//        $xz = json_encode($this->persons);
//        $xz2 = json_decode($xz);
//        var_dump($xz);
//        var_dump("777");
//
//        var_dump($this->persons);
    }
    public function read ($name) {

        $this->searchForPersonFromList($name);

        if ( $this->persons == null )
            return null;
//        var_dump($this->persons);
        else return $this->persons;
    }
    public function updata ( $name, $newSecrets )
    {

        $this->searchForPersonFromList($name);

        $newPer = $this->persons[0];
        $id = $newPer['id'];
        $text = $newSecrets;

        $sql = "UPDATE person SET secrets= '$text' WHERE id=$id";
        $stmt = $this->connection->prepare($sql);
        $stmt->execute();

        return $this->persons;
    }
    public function readAdmin()
    {
        $query = 'SELECT * FROM person' ;
        $stmt = $this->connection->prepare($query);
        $stmt->execute();
        $persons1 = $stmt->fetchAll(PDO::FETCH_ASSOC);
        var_dump(json_encode($persons1));
        return $persons1;
    }
}