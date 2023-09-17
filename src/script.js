function validate(input){
    input.value = input.value.replace(/\W|\d/g, '').substring(0, 1).toUpperCase();
}

let allLetters = "";

for (let k = 1; k < 17; k++) {
    allLetters = allLetters + document.getElementById(k.toString()).value;
    console.log(k.toString())
}

function submitForm(event){

    //Preventing page refresh
    event.preventDefault();
}

function updateText () {
    document.getElementById("letters").addEventListener('submit', submitForm);
    document.getElementById('finalString').innerHTML = 'allLetters';
}
