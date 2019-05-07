var remainingCard = 0;
var username = "";
var CardOnDesk="";
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
        document.getElementById("leaderbroad").innerHTML=game['Leaderbroad']
    });
}


function initializeGame(inputUsername) {
    username = inputUsername;
    document.getElementById("PlayerAction").innerHTML="";
    document.getElementById("LastCard").innerHTML = "";
    document.getElementById("NumCardDesk").innerHTML = remainingCard;
    document.getElementById("Points").innerHTML = remainingCard;
    document.getElementById("leaderbroad").innerHTML=""
    socket.emit("register", username);
}


function slap() {
    socket.emit("slap");
}


function play() {
    socket.emit("play");
}
