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

![MessageTable.png](https://github.com/michelelorenzo/ing-sw-2022-mercurio-miranda-mosconi/blob/main/deliverables/Sequence_Diagram_Examples/MessageTable.png)

