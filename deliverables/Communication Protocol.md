# Communication Protocol

The communication protocol is based on JSON.

Each message has an username used to indicate who’s requesting an action (in case the message starts from a Client) or who’s receiving a response or an update (in case the message starts from the Server).

Each message has also a MessageType (from an Enum) which defines a different way of being processed.

On the Client side, the MessageType is defined by a phases based logic: each client has a phase from which it can only send a specific type of message (Character Cards related messages are handled a little differently). This helps avoiding errors in the communication.

Moreover, messages are also used to update the client’s phase: whenever a request is sent (from the Client to the Server) the client’s phase becomes “WAITING” since it has to receive the response before making a new request. When a request is finally processed, the Server sends its response to all the clients through different kind of model-updates  (a MessageType for each possible update) which do not alter the client’s phase. 

The Server also sends “IS_YOUR_TURN” messages to tell a client that now it’s its turn to make a request.

Regarding the activation of a Character Card, the client can send a CHOSEN_CHARACTER_CARD message while in any of the Action phases. Obviously, the payload of that very same MessageType depends on the character card itself, since each of them requires different parameters to be activated.

Here are examples of Sequence Diagrams for each possible scenario.

![LOGIN.png](https://github.com/michelelorenzo/ing-sw-2022-mercurio-miranda-mosconi/blob/main/deliverables/Sequence_Diagram_Examples/LOGIN.png)

![JOINING.png](https://github.com/michelelorenzo/ing-sw-2022-mercurio-miranda-mosconi/blob/main/deliverables/Sequence_Diagram_Examples/JOINING.png)

![SETTINGS.png](https://github.com/michelelorenzo/ing-sw-2022-mercurio-miranda-mosconi/blob/main/deliverables/Sequence_Diagram_Examples/SETTINGS.png)

![SETUP.png](https://github.com/michelelorenzo/ing-sw-2022-mercurio-miranda-mosconi/blob/main/deliverables/Sequence_Diagram_Examples/SETUP.png)

![PLANNING.png](https://github.com/michelelorenzo/ing-sw-2022-mercurio-miranda-mosconi/blob/main/deliverables/Sequence_Diagram_Examples/PLANNING.png)

![PAWN_MOVE.png](https://github.com/michelelorenzo/ing-sw-2022-mercurio-miranda-mosconi/blob/main/deliverables/Sequence_Diagram_Examples/PAWN_MOVE.png)

![MN_SHIFT.png](https://github.com/michelelorenzo/ing-sw-2022-mercurio-miranda-mosconi/blob/main/deliverables/Sequence_Diagram_Examples/MN_SHIFT.png)

![EFFECT.png](https://github.com/michelelorenzo/ing-sw-2022-mercurio-miranda-mosconi/blob/main/deliverables/Sequence_Diagram_Examples/EFFECT.png)

![CLOUDTILE.png](https://github.com/michelelorenzo/ing-sw-2022-mercurio-miranda-mosconi/blob/main/deliverables/Sequence_Diagram_Examples/CLOUDTILE.png)

![endgame.png](https://github.com/michelelorenzo/ing-sw-2022-mercurio-miranda-mosconi/blob/main/deliverables/Sequence_Diagram_Examples/endgame.png)

![disconnection.png](https://github.com/michelelorenzo/ing-sw-2022-mercurio-miranda-mosconi/blob/main/deliverables/Sequence_Diagram_Examples/disconnection.png)


## Message Table:
| Source | MessageType | Payloads content | Description |
| --- | --- | --- | --- |
| Client.NetworkHandler | ASSISTANT_CARD | value of the chosen assistant card | represents the request to use an assistant card |
| Client.NetworkHandler | CHOSEN_CHARACTER_CARD | player’s nickname + chosen character card ID + appropriate parameters | represents the request to use a character card’s effect using the parameters chosen by the user |
| Client.NetworkHandler | CHOSEN_CT | id of the chosen cloud tile | represents the chosen cloud tile |
| Client.NetworkHandler | CREATE_MATCH | username + idGame + number of players + gamemode | represents the request to create a match and its parameters |
| Client.NetworkHandler | JOIN_MATCH | username + idGame | represents the request to join an existing match |
| Client.NetworkHandler | MN_SHIFT | mother nature movements number | represents the request to move mother nature |
| Client.NetworkHandler | PAWN_MOVE | student color + destination | represents the request to move a pawn |
| Client.NetworkHandler | SETTINGS | chosen wizard and chosen towers color | represents the settings choice |
| Client.NetworkHandler | WAITING | none | - |
| Server.ClientHandler | A_PLAYER_DISCONNECTED | none | communicates that a player disconnected and triggers a method that leads to the login phase |
| Server.ClientHandler | AVAILABLE_TOWER_COLORS | a list containing the available tower colors | communicates which tower color can still be chosen |
| Server.ClientHandler | AVAILABLE_WIZARDS | a list containing the available wizards | communicates which wizard type can still be chosen |
| Server.ClientHandler | EFFECT_ENDED | none | - |
| Server.ClientHandler | GAME_ENDED | the winner’s nickname | - |
| Server.ClientHandler | INIT_CHARACTER_CARDS | a list of character cards containing: the card ID, its price and its students map, if it has one | it’s used after the setup of the game board for expert mode only to show which character cards have been chosen, their price and their attributes (if they have any) |
| Server.ClientHandler | LOBBY_UPDATED | none | communicates to each client in the lobby that a new client joined it |
| Server.ClientHandler | MODEL_CREATED | none | - |
| Server.ClientHandler | PRICE_INCREASE | a character card ID | communicates that a card’s price increased |
| Server.ClientHandler | SETUP_PLAYERS | a list of all players, their chosen wizard and their chosen towers color | it’s used when the model is created to store in the view each player’s settings |
| Server.ClientHandler | SOMEONE_ACTIVATED_AN_EFFECT | the username of the player who activated a character card and that card’s ID | communicates that a a player used a character card |
| Server.ClientHandler | UPDATE_ASSISTANT_CARD | a player nickname and the value of that player’s last used assistant card | tells which assistant card has been used by a player |
| Server.ClientHandler | UPDATE_CARD_STUDENTS | the character card ID and its students map | communicates that the student map on a character card has changed |
| Server.ClientHandler | UPDATE_CLOUDTILES | a list of all the clouds and their corresponding students | communicates the status of each cloud tile |
| Server.ClientHandler | UPDATE_ISLAND | an island ID and a map that indicates that island’s students | tells that a single island has changed |
| Server.ClientHandler | UPDATE_ISLAND_LIST | a list of all the islands and their corresponding students | tells that the island list has changed |
| Server.ClientHandler | UPDATE_MAX_SHIFT | a player nickname | triggers a boolean that increases a player’s max shift by 2 |
| Server.ClientHandler | UPDATE_PROFESSORS | a list of all players and their corresponding professor table | communicates changes in each player’s professor table |
| Server.ClientHandler | UPDATE_SCHOOL_BOARD_ENTRANCE | a player nickname and a map that indicates that player’s entrance | tells that a single schoolboard entrance has changed |
| Server.ClientHandler | UPDATE_SCHOOL_BOARD_HALL | a player nickname and a map that indicates that player’s hall | tells that a single schoolboard hall has changed |
| Server.ClientHandler | UPDATE_TOWERS_NUM | a player nickname and that player’s available towers number | tells that the towers number on a schoolboard has changed |
| Server.ClientHandler | UPDATE_WALLET | a player nickname and that player’s wallet | tells that a player’s wallet has changed |
| Server.VirtualView  | CARD_ACTIVATED | none | communicates that the character card has been correctly activated |
| Server.VirtualView  | ERROR | a string that specifies the type of error | - |
| Server.VirtualView + Server.ClientHandler | IS_YOUR_TURN | none | triggers the next client’s phase when its turn comes or when the server completed that client’s last request |
| Server.VirtualView  | LOBBY_WAITING | a list containing the usernames of all the players that are in the lobby already | puts the client in a waiting state, in which remains until its turn to choose wizard and towers color comes |
| Server.VirtualView + Server.ClientHandler | WAIT | none | puts the client in a waiting state while processing its request or while waiting for its turn to come |
