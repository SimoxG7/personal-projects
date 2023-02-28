# A full-stack recreation of the IBA website from scratch
This project is a recreation of the IBA website https://iba-world.com/ simplified a little and created from scratch by myself. Every file in this repository has been created by me, with a few exception being: the images (which are property of IBA) and a couple Bootstrap's files.
<br>
Dependencies required: PostgreSQL, NodeJS, Express.
# How it works
In order for this to work properly, a Postgres database needs to be set up by using the dump of my own database located in ./useful/dump.txt. The data that is the database has been scraped from the official IBA website. 
<br>
The database has been created with the idea of using an Entity-Relationship model. This is a graphical representation of this database's ER model:
<br><br>
![Alt text](https://github.com/SimoxG7/personal-projects/blob/main/IBA%20Website/useful/schemaeriba.png "ER Model")
<br><br>
Once the database is set up, it's only needed to run the server.js file through Node and the server is set, at the specified port in the .env file. 
<br>
When the server.js file is ran, 3 different queries are executed from the ServerSide JavaScript in order to retrieve any update that the database received:<br>
- the first query retrieves all the cocktails.
- the second query retrieves all the ingredients.
- the third query retrieves all the relations between cocktails and ingredients. 
<br>
Once the queries are done, the results are written (using 'JSON.stringify') into their own .json files. The results are also used to create an array of CompleteCocktail objects, in which the relation between Cocktail and Ingredients is reinstated, so that each and every cocktail contains an array of Ingredients and their measurements. After completing the creation of the CompleteCocktail array, this objects are also written into 'completecocktail.json'. 
<br>
The server then runs a function that creates a new .html page using the information for each cocktail. This is done by using a 'skeleton.html' file, which has all the common parts of every different cocktail page (such as sponsors, feedback form, exc.) and some placeholders for the cocktail's informations, which are deleted in order to add into the .html page the cocktail informations. 
<br>
There are also other pages, each with their own design and utility. 
<br>
Every page also has client-side scripting regarding: a simple arithmetic captcha, a pop-up asking the user if he's 18 or older, a form for the feedback. 
<br>
All the stylesheets are done by myself aswell. 
<br>
Here are a couple of screenshots that show how the website looks once it's up and running:
![Alt text](https://github.com/SimoxG7/personal-projects/blob/main/IBA%20Website/useful/exampleimage1.png "exampleimage1")
<br>
![Alt text](https://github.com/SimoxG7/personal-projects/blob/main/IBA%20Website/useful/exampleimage2.png "exampleimage2")
<br>
![Alt text](https://github.com/SimoxG7/personal-projects/blob/main/IBA%20Website/useful/exampleimage3.png "exampleimage3")
<br>
![Alt text](https://github.com/SimoxG7/personal-projects/blob/main/IBA%20Website/useful/exampleimage4.png "exampleimage4")
<br>
![Alt text](https://github.com/SimoxG7/personal-projects/blob/main/IBA%20Website/useful/exampleimage5.png "exampleimage5")
