package ui;

public class Game {

    private Integer gameID;
    private Integer gameCounter;


    public Game(Integer gameID, Integer gameCounter) {
        this.gameID = gameID;
        this.gameCounter = gameCounter;
    }

    public Integer getgameID() {
        return gameID;
    }

    public Integer getGameCounter() {
        return gameCounter;
    }

}
