function checkCaptchaResponse(num1, num2, result) {
    if (num1 + num2 == result) return true;
    else return false;
}

function formfeedback() {
    /*
    var name = form.name;
    var mail = form.mail;
    var message = form.message;
    var captcharesponse = form.captcharesponse;

    console.log(name + mail + message + captcharesponse);
    */
    let name = document.getElementById("formfeedbackname").value;
    let mail = document.getElementById("formfeedbackmail").value;
    let message = document.getElementById("formfeedbackmessage").value;
    let captcharesponse = document.getElementById("formfeedbackcaptcharesponse").value;
    let nums = document.getElementById("captchanumbers").innerHTML;

    nums = nums.split("+");

    if (parseInt(nums[0]) + parseInt(nums[1]) != parseInt(captcharesponse)) {
        console.log("Captcha response is not correct. Aborting XMLHttpRequest.")
        //console.log(parseInt(nums[0]) + parseInt(nums[1]) != parseInt(captcharesponse));
        return;
    } else {
        console.log("Captcha response is correct. Proceding with XMLHttpRequest.")
    }

    var d = new Date();
    date = d.toLocaleString();

    var data = "Type: Feedback - Date: " + date + " - Name: " + name + " - Mail: " + mail + " - Message: " + message;

    console.log(data);

    var xhr = new XMLHttpRequest();
    xhr.open("POST", "/");
    //xhr.setRequestHeader("Content-Type", "text/plain");
    xhr.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    xhr.send(String(data));
    console.log("Form data sent");

    //window.location.reload() 

    document.getElementById("formfeedbackname").value = "";
    document.getElementById("formfeedbackmail").value = "";
    document.getElementById("formfeedbackmessage").value = "";
    document.getElementById("formfeedbackcaptcharesponse").value = "";

    window.scrollTo(0,0);

    /*
    window.scrollTo(0,0);
    
    window.onload(window.scrollTo(0,0);
    */
}

function formacademy() {
    let name = document.getElementById("formacademyname").value;
    let mail = document.getElementById("formacademymail").value;
    let message = document.getElementById("formacademymessage").value;

    var d = new Date();
    date = d.toLocaleString();

    var data = "Type: Academy - Date: " + date + " - Name: " + name + " - Mail: " + mail + " - Message: " + message;

    console.log(data);

    var xhr = new XMLHttpRequest();
    xhr.open("POST", "/");
    //xhr.setRequestHeader("Content-Type", "text/plain");
    xhr.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    xhr.send(String(data));
    console.log("Form data sent");

    //window.location.reload() 

    document.getElementById("formacademyname").value = "";
    document.getElementById("formacademymail").value = "";
    document.getElementById("formacademymessage").value = "";

    window.scrollTo(0,0);
    
    /*    
    window.onload(window.scrollTo(0,0);
    */
}

function formsupporters() {
    let name = document.getElementById("formsupportersname").value;
    let mail = document.getElementById("formsupportersmail").value;
    let message = document.getElementById("formsupportersmessage").value;

    var d = new Date();
    date = d.toLocaleString();

    var data = "Type: Supporters - Date: " + date + " - Name: " + name + " - Mail: " + mail + " - Message: " + message;

    console.log(data);

    var xhr = new XMLHttpRequest();
    xhr.open("POST", "/");
    //xhr.setRequestHeader("Content-Type", "text/plain");
    xhr.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    xhr.send(String(data));
    console.log("Form data sent");

    //window.location.reload() 

    document.getElementById("formsupportersname").value = "";
    document.getElementById("formsupportersmail").value = "";
    document.getElementById("formsupportersmessage").value = "";

    window.scrollTo(0,0);
    
    /*    
    window.onload(window.scrollTo(0,0);
    */
}


