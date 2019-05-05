import json
import socket
from threading import Thread
from random import randint

from flask import Flask, send_from_directory, request, render_template
from flask_socketio import SocketIO

import eventlet

eventlet.monkey_patch()

app = Flask(__name__)
socket_server = SocketIO(app)

usernameToSid = {}
sidToUsername = {}

scala_socket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
scala_socket.connect(('localhost', 8000))

delimiter = "~"


def listen_to_scala(the_socket):
    buffer = ""
    while True:
        buffer += the_socket.recv(1024).decode()
        while delimiter in buffer:
            message = buffer[:buffer.find(delimiter)]
            buffer = buffer[buffer.find(delimiter) + 1:]
            get_from_scala(message)


Thread(target=listen_to_scala, args=(scala_socket,)).start()


def get_from_scala(data):
        socket_server.emit('message', data, broadcast=True)


def send_to_scala(data):
    scala_socket.sendall((json.dumps(data)+delimiter).encode())


@socket_server.on('register')
def got_message(username):
    usernameToSid[username] = request.sid
    sidToUsername[request.sid] = username
    print(username + " connected")
    message = {"username": username, "action": "connected"}
    send_to_scala(message)


@socket_server.on('disconnect')
def got_connection():
    if request.sid in sidToUsername:
        username = sidToUsername[request.sid]
        del sidToUsername[request.sid]
        del usernameToSid[username]
        print(username + " disconnected")
        message = {"username": username, "action": "disconnected"}
        send_to_scala(message)


@socket_server.on('play')
def play():
    username = sidToUsername[request.sid]
    print(username + " try to play")
    message = {"username": username, "action": "play"}
    send_to_scala(message)


@socket_server.on('slap')
def slap():
    username = sidToUsername[request.sid]
    print(username + " trying to slap")
    message = {"username": username, "action": "slap"}
    send_to_scala(message)


@app.route('/')
def index():
    return send_from_directory('static', 'index.html')


@app.route('/game', methods=["POST", "GET"])
def game():
    if request.method == "POST":
        username = request.form.get('username')
    else:
        username = "guest" + str(randint(0, 100000))
    return render_template('game.html', username=username)


@app.route('/<path:filename>')
def static_files(filename):
    return send_from_directory('static', filename)


socket_server.run(app, port=8080)