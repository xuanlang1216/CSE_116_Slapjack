var remainingCard = 0;
var username = "";
var NumCardOnDesk="";
var Point=0;
var lastCardOnDesk="";
var gamestate="";

var socket = io.connect({transports: ['websocket']});

setupSocket();


function setupSocket() {
    socket.on('connect', function (event) {
        socket.send('Hello Server!');
    });

    socket.on('message', function (event) {
        var gameState = JSON.parse(event);
        document.getElementById("NumCardDesk").innerHTML = gameState['NumberCardOnDesk'].toFixed(0);
        document.getElementById("LastCard").innerHTML = gameState['lastcard'];
        var gameaction=gameState['laststatement'];
        gameaction=gameaction.replace(/\n/g, "<br/>");
        document.getElementById("PlayerAction").innerHTML=gameaction;
        var equipmentState = gameState['equipment'];
        document.getElementById("Points").innerHTML=gameState['playerinfo'][username]['Points']
    });
}


function initializeGame(inputUsername) {
    username = inputUsername;
    document.getElementById("PlayerAction").innerHTML="";
    document.getElementById("LastCard").innerHTML = "Last card is " + lastCardOnDesk;
    document.getElementById("NumCardDesk").innerHTML = "Number of cards on desk: " + NumCardOnDesk;
    document.getElementById("Points").innerHTML = "Points: " + Point;

    socket.emit("register", username);
}


function slap() {
    socket.emit("slap");
}


function play() {
    socket.emit("play");
}
