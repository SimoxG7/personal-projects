function displayAgeVerification() {
    //waiting for DOMContentLoaded
    document.addEventListener("DOMContentLoaded", function displayTrue() {
        let element = document.getElementById("ageverificationpopup");
        element.style.display = "block";
    });
}

function disableScroll() {
    // To get the scroll position of current webpage
    TopScroll = document.documentElement.scrollTop;
    LeftScroll = document.documentElement.scrollLeft;

    // if scroll happens, set it to the previous value
    window.onscroll = function () {
        window.scrollTo(LeftScroll, TopScroll);
    };
}

function blurBackground() {
    document.addEventListener("DOMContentLoaded", function blur() {
        let main = document.getElementById("main");
        console.log(main);
        main.style.filter = "blur(5px)";
    });
}

function changefirsttimevisitingvalue() {
    console.log("Setting firsttimevisiting in localstorage to false");
    localStorage.setItem("firsttimevisiting", false);
}

