package Model;
public class Treasure {
    private int row;
    private int col;

    public Treasure(int row, int col) {
        this.row = row;
        this.col = col;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    @Override
    public String toString(){
        return "Tresure";

    } 
}