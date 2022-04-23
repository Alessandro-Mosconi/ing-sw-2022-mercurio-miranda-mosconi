package it.polimi.ingsw.model;

public class Player {
    private final char[] nickName;
    private int wallet;
    private final Deck wizardDeck;
    private final int playerNumber;
    private final SchoolBoard schoolBoard;

    public Player(){
        this.nickName = null;
        this.wallet = 0;
        this.wizardDeck = null;
        this.playerNumber = 0;
        this.schoolBoard = null;
    }

    public Player(char[] nickName, Deck wizard, int playerNumber, SchoolBoard schoolBoard) {
        this.nickName = nickName;
        this.wallet = 0;
        this.wizardDeck = wizard;
        this.playerNumber = playerNumber;
        this.schoolBoard = schoolBoard;
    }

    public SchoolBoard getSchoolBoard(){
        return schoolBoard;
    }

    public char[] getNickName() {
        return nickName;
    }

    public int getWallet() {
        return wallet;
    }

    public Deck getDeck() {
        return wizardDeck;
    }

    public int getPlayerNumber() {
        return playerNumber;
    }

    public void setWallet(int wallet) {
        this.wallet = wallet;
    }

    public void collectCoin(PawnColor color){
        if(schoolBoard.checkForCoin(color))
            setWallet(getWallet()+1);
    }

//move a specific student from the entrance to the hall
    public void moveFromEntranceToHall(PawnColor color){
        if(schoolBoard.getStudentEntrance().get(color)<=0)
            System.out.println(color + "not present in Entrance");
        else {
            schoolBoard.getStudentEntrance().replace(color, schoolBoard.getStudentEntrance().get(color)-1);
            schoolBoard.getStudentHall().replace(color, schoolBoard.getStudentHall().get(color)+1);
        }
    }


 //remove all the student from a specific cloud and put them in the entrance
    public void moveFromCloudToEntrance(CloudTile cloud){
        int found;

        do
        {
            found=0;
            for(PawnColor color : PawnColor.values()){
                if(cloud.getStudents().get(color)!=0)
                {
                    cloud.removeStudents(color);
                    schoolBoard.getStudentEntrance().replace(color, schoolBoard.getStudentEntrance().get(color)+1);
                    found++;
                }
            }
        }while(found<1);

    }

    //move a specific student color from the Entrance to a specific island
    public void moveFromEntranceToIsland(Island island, PawnColor color){
        if(schoolBoard.getStudentEntrance().get(color)<=0)
            System.out.println(color + "not present in Entrance");
        else {
            schoolBoard.getStudentEntrance().replace(color, schoolBoard.getStudentEntrance().get(color)-1);
            island.getIslandStudents().replace(color, island.getIslandStudents().get(color)+1);
        }
    }

}
