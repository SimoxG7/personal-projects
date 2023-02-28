// Configure dotenv package
require("dotenv").config();

const express = require("express");
const path = require("path");
const fs = require("fs");
const stringdecoder = require("string_decoder").StringDecoder;
const app = express();
const port = process.env.PORT;
const bp = require('body-parser')
const os = require("os");

app.set('view engine', 'ejs');
//app.set('views', path.join(__dirname, 'views'));

app.use(bp.json());

app.use(bp.urlencoded({ extended: true }));

app.use('/', express.static(path.join(__dirname + '/')));

app.get("/", function (req, res) {
    res.sendFile(path.join(__dirname + "/iba.html"));
    console.log("sending file /iba.html (from /)");
})

app.get("/*.json", function (req, res) {
    res.sendFile(path.join(__dirname + "/" + req.url));
    console.log("sending file " + req.url);
});

app.get("/*.jpg", function (req, res) {
    //console.log("Directory:" + __dirname)
    //console.log("Requrl: " + req.url)
    res.sendFile(path.join(__dirname + "/" + req.url));
    console.log("sending file " + req.url);
});

app.get("/*.ico", function (req, res) {
    //console.log("dirname: " + __dirname + " ; req.url: " + req.url)
    res.sendFile(path.join(__dirname + req.url + ".html"));
    console.log("sending file " + req.url + ".html");
});

app.get("/*", function (req, res) {
    //console.log("dirname: " + __dirname + " ; req.url: " + req.url)
    res.sendFile(path.join(__dirname + req.url + ".html"));
    console.log("sending file " + req.url + ".html");
});

//Express static file module
//app.use(express.static(__dirname + '/assets'));

app.listen(port);
console.log('Server started at http://localhost:' + port);

checks = [false, false, false, false, false, false, false, false];

//connecting to db

function getDataFromDB() {
    const { Client } = require('pg');

    const client = new Client({
        user: 'simox',
        host: 'localhost',
        database: 'ibacocktails',
        password: 'simox',
        port: 5432,
    });

    client.connect();

    console.log("Connected to the database.");
    checks[0] = true;

    client.query("set search_path = ibacocktails;");

    console.log("Changed the search_path.");
    checks[1] = true;

    //params: filename, query
    function doQuery(filename, query) {
        var holder = [];
        client.query(query, (err, res) => {
            if (err) {
                console.log(err.stack)
            } else {
                for (i = 0; i < res.rowCount; i++) {
                    holder.push(res.rows[i]);
                }
                //obj.table.push(res.rows);
                //console.log(obj)
            }
            var json = JSON.stringify(holder); 
            //json.replace("[", "{");
            //json.replace("]", "}");
            fs.writeFile(filename + ".json", "{\"" + filename + "\":" + json + "}", "utf8", function (err, data) {
                if (err) console.log("error", err);
                else console.log("Written " + filename + ".json.");
            });
        });
        
        console.log("Query " + filename + " executed.");
    }

    console.log("Executing queries...");

    doQuery("cocktail", "select * from cocktail;");
    doQuery("ingredient", "select * from ingredient;");
    doQuery("ci_relation", "select * from ci_relation;");
    //doQuery("completecocktail", "SELECT cocktail.name, string_agg(ingredient.name, ', ') FROM cocktail JOIN ci_relation ON cocktail.id = ci_relation.c_id JOIN ingredient ON ingredient.id = ci_relation.i_id GROUP BY cocktail.name;")
    //doQuery("completecocktail", "SELECT * FROM perfect_viewer_with_quantity");

    console.log("All queries executed.");
    checks[2] = true;

    setTimeout(function () {
        client.end();
        console.log("Closed connection to the database.");
    }, 1000);
}

getDataFromDB();
console.log("Updated the json files with the DB queries.");
checks[4] = true;

function createObjects() {

    var cocktail = JSON.parse(fs.readFileSync("./cocktail.json")).cocktail;
    var ingredient = JSON.parse(fs.readFileSync("./ingredient.json")).ingredient;
    var ci_relation = JSON.parse(fs.readFileSync("./ci_relation.json")).ci_relation;

    //DEBUG LOG
    /*
    console.log(cocktail[0]);
    console.log(ingredient[0]);
    console.log(ci_relation[0]);
    */

    var completecocktail = [];
    for (i = 0; i < cocktail.length; i++) {
        let ingredients = [];
        h = 0;
        for (j = 0; j < ci_relation.length; j++) {
            for (k = 0; k < ingredient.length; k++) {
                if (ci_relation[j].c_id == cocktail[i].id && ci_relation[j].i_id == ingredient[k].id) {
                    ingredients[h++] = ci_relation[j].i_quantity + " " + ingredient[k].name;
                }
            }
        }

        //manipulating original url so that it's the local URL
        let newimageurl = cocktail[i].imageurl;
        newimageurl = newimageurl.replace("https://iba-world.com/wp-content/uploads/2021/02/", "./img/cocktails/")
        //console.log(newimageurl)

        //ingredients[h++] = ci_relation[j].i_quantity + " - " + ingredient[k-1].name;
        completecocktail[i] = {
            id: cocktail[i].id,
            name: cocktail[i].name,
            category: cocktail[i].category,
            method: cocktail[i].method,
            garnish: cocktail[i].garnish,
            history: cocktail[i].history,
            note: cocktail[i].note,
            simoxrate: cocktail[i].simoxrate,
            link: cocktail[i].link,
            //imageurl: cocktail[i].imageurl,
            imageurl: newimageurl,
            ingredients: ingredients
        };
        //completecocktail.ingredients = [];

    }
    //console.log(completecocktail);
    return completecocktail;

}

var completecocktail = createObjects();
filename = "completecocktail";

function createCompleteCocktail() {
    var json = JSON.stringify(completecocktail);
    fs.writeFile(filename + ".json", "{\"" + filename + "\":" + json + "}", "utf8", function (err, data) {
        if (err) {
            console.log("error", err);
        } else {
            console.log("Written completecocktail.json.");
        }
    });
    checks[5] = true;
}

createCompleteCocktail();

//console.log(completecocktail);

console.log("\nCreating the cocktail pages...");

var data = "";
var skeletontext = "";

setTimeout(function() {
    data = fs.readFileSync("./skeleton.html", "utf8", function (err, data) {
        if (err) {
            return console.log(err);
        } else {
            return data;
        }
    })
    skeletontext = data;
    console.log("Assigned skeleton.html to a variable...");
    
    for (i = 0; i < completecocktail.length; i++) {
        //console.log(completecocktail[i].name);

        var skeletontextcopy = skeletontext;

        //TODO - FIX THIS

        const dict = {"#!TITLE!#":completecocktail[i].name, "#!CATEGORY!#":completecocktail[i].category, "#!IMAGEURL!#":completecocktail[i].imageurl, "#!METHOD!#":completecocktail[i].method, "#!GARNISH!#":completecocktail[i].garnish, "#!HISTORY!#":completecocktail[i].history, "#!NOTE!#":completecocktail[i].note, "#!LINK!#":completecocktail[i].link};

        for (var key in dict) {
            var value = dict[key];
            if (value == "NULL") {
                value = "N/A";
            }
            skeletontextcopy = skeletontextcopy.replace(key, value);
        }

        skeletontextcopy = skeletontextcopy.replace("#!TITLE!#", dict["#!TITLE!#"]);

        var ingredients = "";

        for (j = 0; j < completecocktail[i].ingredients.length; j++) {
            ingredients += completecocktail[i].ingredients[j] + "<br>";
        }

        skeletontextcopy = skeletontextcopy.replace("#!INGREDIENTS!#", ingredients);
        
        //skeletontextcopy = skeletontextcopy.replace("#!TITLE!#", completecocktail[i].name).replace();

        // / /g replaces globally
        fs.writeFileSync("./cocktails/" + completecocktail[i].name.replace(/ /g, "_").replace("#", "") + ".html", skeletontextcopy, "utf8", function (err, data) {
            if (err) {
                console.log("error", err);
            } else {
                console.log("Written " + completecocktail[i].name + ".html");
            }
        });
    }

    console.log("Completed the creation of the cocktail pages.")
    checks[6] = true;

}, 1000);

skeletontext = ""
data = ""
tocopy = '<div class="grid-item"><a href="./cocktails/#!TITLENOSPACE!#.html"><img class="insidegridimage" src="#!IMAGEURL!#"></a><div class="textunderpreview">#!TITLE!#</div></div>';
toadd = "";

setTimeout(function() {
    data = fs.readFileSync("./searchcocktailsskeleton.html", "utf8", function (err, data) {
        if (err) {
            return console.log(err);
        } else {
            return data;
        }
    })
    skeletontext = data;
    console.log("Assigned searchcocktailsskeleton.html to a variable...");
    
    for (i = 0; i < completecocktail.length; i++) {
        //console.log(completecocktail[i].name);

        var skeletontextcopy = skeletontext;

        var namenospace = completecocktail[i].name.replace(/ /g, "_").replace("#", "");

        const dict = {"#!TITLE!#":completecocktail[i].name, "#!IMAGEURL!#":completecocktail[i].imageurl, "#!TITLENOSPACE!#":namenospace};

        toadd += tocopy.replace("#!TITLE!#", dict["#!TITLE!#"]).replace("#!IMAGEURL!#", dict["#!IMAGEURL!#"]).replace("#!TITLENOSPACE!#", dict["#!TITLENOSPACE!#"]);

        var result = skeletontextcopy.replace("!#INSERT HERE#!", toadd);

        // / /g replaces globally
        fs.writeFileSync("./cocktail.html", result, "utf8", function (err, data) {
            if (err) {
                console.log("error", err);
            } else {
                console.log("Written cocktail.html");
            }
        });
    }

    console.log("Completed the creation of the page with the cocktail grid.")
    checks[7] = true;

    //console.log(tocopy)
    //console.log(toadd)

}, 100);

/*
function writeform(filename, data) {
    fs.writeFileSync(filename, data, "utf8", {"flags": "a"});
}
*/

/*
let buffer = "";
let decoder = new StringDecoder("utf8");
*/

setTimeout(function (){
    console.log("Service is ready.");
}, 2000);

app.post("/*", function (req, res) {
    console.log("Received form response.");
    string = JSON.stringify(req.body).slice(2, -5);
    //console.log(string);
    fs.appendFileSync("form.log", string, "utf8");
    //fs.writeFileSync("feedbackform.txt", string, "utf8", {"flags":"a"});
    fs.appendFileSync("form.log", "\n", "utf8");
});






















