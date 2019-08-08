var myUsername = document.getElementById("username");
var myPossword = document.getElementById("password");
var errorValue = /*[[(${error})]]*/ false;
if (errorValue) {
    myUsername.classList.add('is-invalid');
    myPossword.classList.add('is-invalid');
}