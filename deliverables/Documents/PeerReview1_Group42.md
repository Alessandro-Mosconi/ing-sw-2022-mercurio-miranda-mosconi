# Peer-Review 1: UML

Antonio Mercurio, Michele Lorenzo Miranda, Alessandro Mosconi

Gruppo 42

Valutazione del diagramma UML delle classi del gruppo 41.

## Lati positivi

Gli studenti del gruppo 41 hanno aggiunto delle note che rendono più chiara la lettura dell’UML, facendolo risultare più ordinato e lineare. Inoltre riteniamo positivo che siano state inserite già le parti relative alla View e al Controller, anche se abbozzate.
Fatta eccezione di qualche piccolo metodo, tutte le classi sono state dotate degli strumenti necessari a svolgere la partita del gioco e nessuna classe essenziale è, secondo noi, mancante.   

## Lati negativi


* **Considerazioni generali**
La cardinalità delle relazioni non sempre è espressa. Inoltre, mancano (o sono incomplete) le liste dei parametri date in input alla maggior parte dei metodi (e.g. il metodo moveStudentToIsland non riceve alcuno studente da spostare, così come getPlayerByID non riceve alcun ID). 
Crediamo poi che alcuni nomi dati ai metodi potrebbero essere modificati per facilitare la comprensione di quella che è la funzione da essi svolta (per esempio, il metodo getStudentFromCloud nella classe Player farebbe pensare ad un getter, ma in realtà non lo è).
L’array discardPile attribuito al Player potrebbe essere sostituito da un boolean sulle carte per indicare se queste siano state o meno usate


* **Considerazioni specifiche**
  * Nei metodi della classe Game:
    createNewPlayer non crediamo abbia bisogno di ricevere come parametro la modalità del gioco, in quanto la difficoltà dovrebbe essere comune a tutti i player partecipanti alla stessa partita. 
  * Nella classe Player:
	Crediamo manchi l’attributo relativo al colore delle torri scelto dal player;
	non ci è chiaro per quale motivo il metodo playerGetAdditionCoin() sia privato;
    il metodo addStudentToCloud() ci sembra ridondante, dal momento che già in Game è presente un metodo populateCloudTile() che (presumiamo) serva a riempire di studenti una nuvola;
  * Nella classe Gameboard: 
    Il metodo getStudentsFromBag dovrebbe ricevere come parametro in ingresso il numero di giocatori piuttosto che la modalità in cui si sta giocando, dal momento che la funzione che svolge non dipende dalla modalità, ma dal numero dei partecipanti al gioco. 

  * Per quanto riguarda, invece, la gestione delle isole, nella classe IslandRegion: 
    il metodo addIslandToRegion dovrebbe, secondo noi, essere un “addRegionToRegion” e prendere in input una IslandRegion anziché un’IslandTile di modo da garantire il corretto funzionamento del metodo anche nel caso in cui vengano accorpate delle isole già composte;
    sarebbe più indicato avere un metodo che elimini le regioni a seguito di una fusione con delle altre e non lasciare (come abbiamo immaginato che funzioni) che il processo di eliminazione delle regioni superflue venga fatto all’interno del metodo che opera la fusione, per pure questioni implementative.
    Non ci è chiaro inoltre come venga gestito il processo di eliminazioni delle isole (e quindi delle regioni), una volta accorpate tra di loro. Se il tutto avviene nello stesso metodo allora pensiamo che usare due metodi separati e che svolgano una singola funzione possa facilitare la comprensione. 

  * Nella classe AssistantCard:
    riteniamo che i metodi set della classe siano trascurabili, dal momento che i mazzi di carte sono creati all’inizio del gioco e restano immutabili per tutta la partita.



## Confronto tra le architetture

La presenza di una classe GameBoard è utile a rappresentare quella che è la disposizione delle parti fisiche del gioco (quindi le Island Tiles, le Cloud Tiles e il sacchetto), favorendo così una buona organizzazione dello stato corrente della partita.
Nella nostra architettura non è ancora presente alcun package relativo al Controller ed al View, per cui prenderemo spunto dal diagramma del gruppo 41 per poter rendere il nostro più completo. 
Per la gestione degli effetti delle carte personaggio noi abbiamo utilizzato uno strategy pattern, anziché un factory, ma la scelta del factory è sicuramente interessante e la terremo in considerazione. 

