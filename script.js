function validate(input){
    input.value = input.value.replace(/\W|\d/g, '').substring(0, 1).toUpperCase();
}

let allLetters = "";

for (let k = 1; k < 17; k++) {
    allLetters += document.getElementById(k.toString()).value;
}

document.getElementById("finalString").innerHTML = 'allLetters';
