function login() {
    let password = prompt("Password");


    fetch(host + 'login/' + password).then((response) => response.text()).then((answer) => {
        location.assign(answer);
    })



}