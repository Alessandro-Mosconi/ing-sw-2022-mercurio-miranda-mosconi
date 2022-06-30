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
(Can we say they can double click on the jar?)\
To play locally you have to run one *server* and the *clients*. In order to run the jar file you need to use the Terminal and go to the directory where the jar is located, then type `java -jar <jarname>.jar`.\
To play online you need to have at least one sever running on a machine. Every client will connect to that server through an IP.\
NB: all the clients must be connected to the same network and use the IP (??? idk this must be completed) 

### Running
When you run the *server*, at launch you will be asked to choose a preferred server port.\
When you run the *client*, at launch you will be asked to choose between the CLI and the GUI versions, then insert your username, the server IP (127.0.0.1), server port (chosen by the server, 1234 as default), and decide whether to *create* a new match or *join* an existing one. 

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

## Screenshots
Scene of the login page\
\
![picture alt](https://github.com/michelelorenzo/ing-sw-2022-mercurio-miranda-mosconi/blob/main/src/resources/assets/Screenshot/Login_SS.png "Login page")\
\
\
Scene of the Assistant Card selection\
\
![picture alt](https://github.com/michelelorenzo/ing-sw-2022-mercurio-miranda-mosconi/blob/main/src/resources/assets/Screenshot/Assistant_SS.png "Assistant Cards")\
\
\
Scene of the Wizard choice\
\
![picture alt](https://github.com/michelelorenzo/ing-sw-2022-mercurio-miranda-mosconi/blob/main/src/resources/assets/Screenshot/Wizards_SS.png "Wizard choice")
