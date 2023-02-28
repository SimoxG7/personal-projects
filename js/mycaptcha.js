function generateSum() {
    let num1 = Math.floor(Math.random() * 10) + 1;
    let num2 = Math.floor(Math.random() * 10) + 1;
    let result = num1 + num2;
    //console.log(num1, num2, result);
    return {
        num1: num1,
        num2: num2,
        result: result,
    };
}

function addCaptchaToText() {
    var captchavalues = generateSum();
    //console.log(captchavalues);
    captchaparagraph = document.getElementById("captchanumbers");
    captchaparagraph.innerText = captchavalues.num1 + "+" + captchavalues.num2;
}

function checkCaptchaResponse(num1, num2, result) {
    if (num1 + num2 == result) return true;
    else return false;
}



