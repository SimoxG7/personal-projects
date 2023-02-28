async function createObjects() { 
    cocktail = [];
    await fetch("./cocktail.json").then(response => {return response.json();}).then(function(result) {
        cocktail = result.cocktail;
    });
    console.log("parsed cocktail");

    ingredient = [];
    await fetch("./ingredient.json").then(response => {return response.json();}).then(function(result) {
        ingredient = result.ingredient;
    });
    console.log("parsed ingredient");

    ci_relation = [];
    await fetch("./ci_relation.json").then(response => {return response.json();}).then(function(result) {
        ci_relation = result.ci_relation;
    });
    console.log("parsed ci_relation");

    //DEBUG LOG
    console.log(cocktail[0]);
    console.log(ingredient[0]);
    console.log(ci_relation[0]);

    /*OLD
    var completecocktail;
    for (i = 1; i <= cocktail.length; i++) {
        completecocktail = cocktail;
        //completecocktail.ingredients = [];
        h = 0;
        ingredients = [];
        for (j = 1; j <= ingredient.length; j++) {
            if (ci_relation.c_id == i && ci_relation.i_id == j) {
                ingredients[h++] = ci_relation.i_quantity + " - " + ingredient[j].name;
            } 
            
        }
        console.log(ingredients);
        //console.log(completecocktail);
        completecocktail[i].ingredients = ingredients;
    }

    console.log(completecocktail);
    */

    var completecocktail = [];
    for (i = 0; i < cocktail.length; i++) {
        ingredients = [];
        h = 0;
        for (j = 0; j < ci_relation.length; j++) {
            for (k = 0; k < ingredient.length; k++) {
                if (ci_relation[j].c_id == cocktail[i].id && ci_relation[j].i_id == ingredient[k].id) {
                    ingredients[h++] = ci_relation[j].i_quantity + " - " + ingredient[k].name;
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






