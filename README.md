# Eriantys - Software Engineering Project - AY 2021/2022
![picture alt](https://github.com/michelelorenzo/ing-sw-2022-mercurio-miranda-mosconi/blob/GUI/src/resources/assets/eriantys_cropped.jpg "Eriantys game image")

## Project Description
This project is meant to create a digital version of the board game [Eriantys](https://www.craniocreations.it/prodotto/eriantys/), published by *Cranio Creations*. 

The coding language used is Java and the repository contains: 

- The game itself
- The resources to run the game
- Peer review documents, sequence diagrams and UMLs, kept in the *deliverables* folder.

## Team 🧑🏼‍💻
- [Mercurio Antonio](https://github.com/antonio-mercurio)
- [Miranda Michele Lorenzo](https://github.com/michelelorenzo)
- [Mosconi Alessandro](https://github.com/Alessandro-Mosconi)

## How to play 👾
In the jar folder (deliverables/jar) you will find two jar files, *client* and *server*. The game can either be played locally on one single machine or online, from different machines connected to the same network.\
To play locally you have to run one *server* and the *clients*. In order to run the jar file you need to use the Terminal and go to the directory where the jar is located, then type `java -jar <jarname>.jar`.\
To play online you need to have one machine running a server. Every client will connect to that server through the machine IP.\
NB: all the clients must be connected to the same network and use the IP of the server. We furthermore suggest to disable the firewall. 

### Running
When you run the *server*, at launch you will be asked to choose a preferred server port. This server port will be used to open the socket and connect with the client.\
When you run the *client*, at launch you will be asked to choose between the CLI and the GUI versions, then insert your username, the server IP (defaul is 127.0.0.1, if you play local you don't need to change it), server port (chosen by the server, 1234 as default), and decide whether to *create* a new match or *join* an existing one. 

- **Create**: in the *create settings* you will need to insert one *game ID*, the *game mode* (*easy/expert*) and the *number of players* that will join the game (*up to 3*)
- **Join**: in the *join settings* you will only need to insert the *game ID* of the match you want to join and you will be redirected to the match lobby, waiting for set number of active players to be reached. 

## Implemented Functionalities 
| FUNCTIONALITY | PROGRESS |
| --- | --- |
| Simplified Rules | ✅ |
| Complete Rules | ✅ |
| CLI  | ✅ |
| Socket | ✅ |
| GUI | ✅ |
| AF: all 12 character cards implemented  | ✅ |
| AF: multiple cuncurrent matches | ✅ |

✅ = implemented\
🟠 = WIP

## Documentation
- [UML](url to add)
- [Network Protocol](https://github.com/michelelorenzo/ing-sw-2022-mercurio-miranda-mosconi/blob/main/deliverables/Communication%20Protocol.md)
- [Peer reviews](https://github.com/michelelorenzo/ing-sw-2022-mercurio-miranda-mosconi/tree/main/deliverables/Documents) 

## Screenshots
Scene of the login page\
\
![picture alt](https://github.com/michelelorenzo/ing-sw-2022-mercurio-miranda-mosconi/blob/main/src/resources/assets/Screenshot/Login_SS.png "Login page")\
\
\
Login (CLI VERSION)\
\
![picture alt](https://github.com/michelelorenzo/ing-sw-2022-mercurio-miranda-mosconi/blob/main/src/resources/assets/Screenshot/LoginCLI.jpeg "Login CLI")\
\
\
Scene of the Assistant Card selection\
\
![picture alt](https://github.com/michelelorenzo/ing-sw-2022-mercurio-miranda-mosconi/blob/main/src/resources/assets/Screenshot/Assistant_SS.png "Assistant Cards")\
\
\
Scene of the Wizard choice\
\
![picture alt](https://github.com/michelelorenzo/ing-sw-2022-mercurio-miranda-mosconi/blob/main/src/resources/assets/Screenshot/WizardSelectionGUI.jpeg "Wizard choice")\
\
\
Scene of the game board (GUI VERSION)\
\
![picture alt](https://github.com/michelelorenzo/ing-sw-2022-mercurio-miranda-mosconi/blob/main/src/resources/assets/Screenshot/Mainboard_SS.png "Game board")
