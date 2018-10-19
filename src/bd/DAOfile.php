<?php

class DAOfile
{
    var $persons;

    private $file_name = "./file.txt";
    var $con;
    var $jsonDecode;

    public function connect()
    {
        $this->con = file_get_contents($this->file_name);
    }
    private function searchForPersonFromList($name)
    {

        $json = json_decode($this->con, true);
        foreach ($json as $item)
        {
            if ( $name == $item['name']) {
                $jsonEncod = json_encode($item);
                $string = "[" . $jsonEncod . "]";
                $jsonEqualsPerson = json_decode($string,true);
                $this->persons = $jsonEqualsPerson;
                return $this->persons;
            }
        }
    }
    public function read ($name) {
        $this->searchForPersonFromList($name);

        return $this->persons;

    }
    public function updata ( $name, $newSecrets )
    {

        $this->searchForPersonFromList($name);

        $json = json_decode($this->con, true);

        foreach ($json as $item)
        {
            if ( $item['name'] == $name)
            {
                $id = $item['id'] - 1;
                $json[$id]['secrets'] = $newSecrets;
            }
        }
        $jsonSaveFile = json_encode($json);
        file_put_contents($this->file_name,$jsonSaveFile);


        return $this->persons;
    }
    public function readAdmin()
    {
        $jsonCSV = json_decode($this->con, true);
        $this->persons = $jsonCSV;

        return $this->persons;

    }
}