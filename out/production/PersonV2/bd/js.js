let login = document.getElementById("login");
let password = document.getElementById("passw");
let mySecrets = document.getElementById("mySecrets");
let newSecrets = document.getElementById("mySecretsUpdata")
let table = document.getElementById("myTable");

let xhr = new XMLHttpRequest();
let typeConnect;

function read()
{
    typeConnect = 'read';

    // let log = login.value;

    if ( isAdmin == 'login')
    {
        connectDataOnServer( login.value, password.value, typeConnect, '', isAdmin);

        if ( xhr.responseText == 'usersConnectOK') {
            isAdmin = 'users';
            // window.open("http://localhost/bd/users.html");
            document.location.href = "users.html";
            localStorage.setItem("login", login.value);
            localStorage.setItem("pass", password.value);
            return mySecrets.value = 'открыть вкладку как пользователь';
        }

        else if ( xhr.responseText == 'adminConnectOK') {

            let newWindow = confirm("Зайти как админ?");

            if ( newWindow == true ) {
                isAdmin = 'admin';
                // window.open("http://localhost:8080/admin.html");
                document.location.href = "admin.html";
                localStorage.setItem("login", login.value);
                localStorage.setItem("pass", password.value);
                return mySecrets.value = 'открыть вкладку админа как админ';
            }
            else{
                isAdmin = 'users';
                // window.open("http://localhost:8080/users.html");
                document.location.href = "users.html";
                // sessionStorage
                // localStorage
                localStorage.setItem("login", login.value);
                localStorage.setItem("pass", password.value);
                return mySecrets.value = 'открыть вкладку админа как пользователь';
            }
        }
        else return mySecrets.value = xhr.responseText;
    }

    let loginIndex = localStorage.getItem("login");
    let passIndex = localStorage.getItem("pass");

    if ( isAdmin == 'users' ) {

        connectDataOnServer( loginIndex, passIndex, typeConnect, newSecrets.value, isAdmin);
        mySecrets.value = xhr.responseText;
    }

    if ( isAdmin == 'admin' ){

        connectDataOnServer(loginIndex,passIndex,typeConnect,newSecrets.value,isAdmin);

        if ( xhr.responseText == 'Error')
        {
            return newSecrets.value = 'Error admin - login and pass';
        }
        else
        {
            let json =  JSON.parse(xhr.responseText);
            let row, id, name, pass, secret, newBtnUpData, newBtnDel;
            console.log(json[1]['id'] + "js json");

            if ( document.getElementById('myTable').rows.length >= json.length)
                return;

            for ( let i = 0; i < json.length; i++ )
            {
                row = table.insertRow(i+1);

                id = row.insertCell(0);
                name = row.insertCell(1);
                pass = row.insertCell(2);
                secret = row.insertCell(3);
                newBtnUpData = row.insertCell(4);
                newBtnDel = row.insertCell(5);

                id.innerHTML = json[i]['id'];
                name.innerHTML = json[i]['name'];
                pass.innerHTML = json[i]['pass'];
                secret.innerHTML = json[i]['secrets'];

                newBtnUpData.innerHTML = newBtnUpData.innerHTML + "<button class=\"newBtnAdmin\" id=\"" + json[i]['id']+"\" onclick=\"adminUpData(this.id)\">UpData</button>";
                newBtnDel.innerHTML = newBtnDel.innerHTML + "<button class=\"newBtnAdmin\" id=\"" + json[i]['id']+"\"onclick=\"adminDellSecrets(this.id)\">Delete</button>";
            }
        }
    }
}

function adminUpData(clickedId) {
    typeConnect = 'updata';

    var nameFromTable = document.getElementById('myTable').rows[clickedId].cells[1].innerHTML;
    var passwordFromTable = document.getElementById('myTable').rows[clickedId].cells[2].innerHTML;

    isAdmin = 'users';

    connectDataOnServer(nameFromTable,passwordFromTable,'updata',newSecrets.value,isAdmin);

    document.getElementById('myTable').rows[clickedId].cells[3].innerHTML = newSecrets.value;

}

function adminDellSecrets(clickedId) {
    newSecrets.value = "";
    typeConnect = 'updata';
    adminUpData(clickedId);
}

function updata() {
    typeConnect = 'updata';

    let loginIndex = localStorage.getItem("login");
    let passIndex = localStorage.getItem("pass");

    connectDataOnServer(loginIndex,passIndex,'updata',newSecrets.value,isAdmin);

    mySecrets.value = xhr.responseText;
    read();
}

function dellSecrets() {
    newSecrets.value = "";
    typeConnect = 'updata';
    updata();
}

function connectDataOnServer (login, password, acrion, newSecretst, isAdmin) {
    // xhr.open('GET', 'php.php?data={"name":"' + login + '","pass":"' + password + '","action":"' + acrion + '","newSecrets":"' + newSecretst + '","admin":"' + isAdmin + '"}', false);
    // xhr.send();
    xhr.open('GET', 'http://localhost:8080/API/{"name":"' + login + '","pass":"' + password + '","action":"' + acrion + '","newSecrets":"' + newSecretst + '","admin":"' + isAdmin + '"}', false);
    // xhr.open('GET', 'http://localhost:8080/API/{"name":"' + login + '"}', false); в sen
    xhr.send();
    if ( xhr.responseText == 'invalid') {
        alert("Срок действия сессии истек!" + "\n" + "Авторизируйтесь еще раз.");
        document.location.href = "index.html";
        xhr.responseText = "";
        return ;
    }

}