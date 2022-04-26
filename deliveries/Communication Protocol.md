# Communication Protocol
## Premise

The communication protocol used is **JSON** based. 
## Message Structure
All messages share the following structure:

- id (String)
- messageType (enum)
- payload (String)
    - if messageType == ACTION -> then payload is a JSON representation of an action class;
    - if messageType == MODEL_UPDATE -> then payload is a JSON representation of the objects that contains the changes made to the model;
    - if messageType == ERROR -> then payload is a JSON representation of the error enumeration.


## Communication Examples

### Checking connection
When connection is established server and client set a socket timeout. Then client and server keep sending each other periodical messages to keep the timeout from expiring (Ping message sent every _timeout/2 sec_)


### Player connection with match creation

Example of a login message with new match creation:

```json
{
   "id": "idPlayer",
   "messageType": "CREATE_MATCH",
   "payload": "GameID"
}
```

Example of a message from server asking for number of players:

```json
{
   "id": "idPlayer",
   "messageType": "NUMBER_OF_PLAYERS",
   "payload": null
}
```

Example of a message from client to specify the number of players:

```json
{
   "id": "idPlayer",
   "messageType": "NUMBER_OF_PLAYERS",
   "payload": "3"
}
```

Example of a possible error message during login:

```json
{
   "username": null,
   "messageType": "ERROR",
   "payload": "INVALID_LOGIN_USERNAME"
}
```


### Player connection joining an existing match

Example of a login message to join an existing match:

```json
{
   "id": "idPlayer",
   "messageType": "JOIN_MATCH",
   "payload": "idGame"
}
```

### Ask to perform an action

Example of a request to perform an action to buy a develop card:

```json
{
   "username": "idPlayer",
   "messageType": "ACTION",
   "payload":
   {
      "type": "BUY_CHARACTER_CARD",
      "characterCard": "card"
   }
}
```

## Message Tables

| Source     | Message Type  | payload content    |  description
| :----:     |    :----:   |          :----:  |     :----:  |
| Server     | LOGIN_SUCCESFUL       | null    | login procedure has been succesfull completed
| Server     | RECONNECTED  | null | client has been successfully reconnected to server
| Server     | DISCONNECTED   |   player's username   | inform clients that a player has disconnected
| Server     | ERROR  | an error type  | notifies the client that an error occured
| Server     | NUMBER_OF_PLAYERS       | null     | tell the client that server is in a state where he expects the number of players
| Server     | GAME_STARTED | List\<String\> of the players' usernames | indicates to all clients that game has started
| Server     | WAIT_FOR_LOBBY_CREATION | a String containing the message description | indicates to the client that there is another player creating the lobby
| Server     | LOBBY_CREATED |   null   | tells the 1st client that lobby has been created succesfully
| Server     | YOU_JOINED | number of remaining required players (int) | confirms to the client that he joined the lobby succesfully
| Server     | OTHER_USER_JOINED |   number of remaining required players (int)   | tells every client that a new player joined
| Server     | SERVER_DOWN |  null    | notifies the client that the server is crashed
| Client     | NUMBER_OF_PLAYERS        |  the chosen number of players    |
| Client     | CREATE_MATCH | the match ID     | sent by client to create a new match
| Client     | JOIN_MATCH |  the match ID    | sent by client to join an existing match
| Client     | ACTION        | an Action type     | this message contains an Action performed by the client as payload.
| Client     | QUIT |  null  | sent by the client to the server to QUIT the game


### Errors Tables
#### Generic Errors
| Error Type | description |
| :----: | :----: |
| UNKNOWN_ERROR |
| MALFORMED_MESSAGE | the server has recived a malformed Json so he cannot deserialize it into an action
| INVALID_LOGIN_USERNAME | username is empty or null
| INVALID_NUMBER_OF_PLAYERS | the selected number of players is invalid
| GAME_ALREADY_STARTED | a player tries to LOGIN when the game is already started
#### Controller Errors
| Error Type | description |
| :----: | :----: |
| UNKNOWN_CONTROLLER_ERROR |
| INVALID_ACTION | action not correctly initialized
| WRONG_ACTION | action can't be performed now
| WRONG_PLAYER | not their turn
