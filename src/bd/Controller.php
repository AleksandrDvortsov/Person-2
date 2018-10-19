<?php

//global $dao;
include('DAOfile.php');
class Controller
{
    var $dao;
    public function execute($jsonStr)
    {
        $data = json_decode($jsonStr, true);

        if ( $data['admin'] == 'login')
        {
            if ( $data['action'] == 'read')
            {
                if ( $data['name'] == null || $data['pass'] == null )
                {
                    return 'Fill in all the input fields!!!';
                }

                $user = $this->getUsers($data['name']);

                if ($data['name'] == $user['name'] && $data['pass'] == $user['pass']) {
                    if ( $user['name'] == 'admin')
                        return 'adminConnectOK';
                    else
                    return 'usersConnectOK';
                }
                else
                    return 'There is no such person in the DataBase!';
            }
        }

        if ( $data['admin'] == 'users')
        {
            switch ($data['action']) {
                case 'read':

                    if ( $data['name'] == null || $data['pass'] == null )
                    {
                        return 'Fill in all the input fields!!!';
                    }
                    else {

                        $user = $this->getUsers($data['name']);

                        if ($data['name'] == $user['name'] && $data['pass'] == $user['pass']) {
                            return $user['secrets'];
                        } else
                            return 'There is no such person in the DataBase!';
                    }

                case 'updata':
                    $user = $this->getUsersSecretsUpData($data['name'], $data['newSecrets']);

                    if ($data['name'] == $user['name'] && $data['pass'] == $user['pass']) {
                        return $user['secrets'];
                    }
                    else {
                        return 'Error';
                    }
                default:
                    return null ;
            }
        }
        if ( $data['admin'] == 'admin') {
            switch ($data['action']) {
                case 'read':

                    $user = $this->getAdmin();
                    if ( $data['name'] == 'admin' && $data['pass'] == '0' ) {
                        $json = json_encode($user);
                        return $json;
                        break;
                    }
                    else {
                        return 'Error';
                        break;
                    }
                default:
                    return null;
            }
        }
    }
    private function newDAO()
    {
        $this->dao = new DAOfile();
        $this->dao->connect();
    }
    private function getUsers($name)
    {
        $this->newDAO();

        $personList = $this->dao->read($name);

        return $personList[0];
    }
    private function getUsersSecretsUpData($name,$newSecrets)
    {
        $this->newDAO();
//        var_dump($name);
        $personList = $this->dao->updata($name, $newSecrets);

        return $personList[0];
    }
    private function getAdmin()
    {
        $this->newDAO();
        $personList = $this->dao->readAdmin();
//        var_dump($personList);
        return $personList;
    }
}