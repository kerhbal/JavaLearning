package JavaLearning.ChessGameObjectOriented;
public class Player {
    //here black is 1, white is 2, use int instead of boolean to directly write into the board(int array)
    private String name;

    public Player() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
