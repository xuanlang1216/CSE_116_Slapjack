var remainingCard = 0;
var username = "";
var CardOnDesk="";
var Point=0;
var lastCardOnDesk="";
var gameStatement=""

var socket = io.connect({transports: ['websocket']});

setupSocket();


function setupSocket() {
    socket.on('connect', function (event) {
        socket.send('Hello Server!');
    });

    socket.on('message', function (event) {
        var gameState = JSON.parse(event);
        document.getElementById("NumberCardOnDesk").innerHTML = gameState['NumberCardOnDesk'].toFixed(0);

        var equipmentState = gameState['equipment'];
        for (var i in equipmentList) {
            var equipment = equipmentList[i];
            var buttonText = equipmentState[equipment]['buttonText'];
            buttonText = buttonText.replace(/\n/g, "<br/>");
            document.getElementById(equipment).innerHTML = buttonText;
        }
    });
}


function initializeGame(inputUsername) {
    username = inputUsername;

    var html = "";
    for (var i in equipmentList) {
        var equipment = equipmentList[i];
        html += '<button id="' + equipment + '" onclick="buyEquipment(\'' + equipment + '\')">' + equipment + '</button>';
        html += '<br/>';
    }
    document.getElementById("equipmentButtons").innerHTML = html;
    document.getElementById("NumberCardOnDesk").innerHTML = remainingCard;

    socket.emit("register", username);
}


function slap() {
    socket.emit("slap");
}


function play() {
    socket.emit("play");
}
