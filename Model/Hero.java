package Model;
public class Hero {
    private int row;
    private int col;

    public Hero(int row, int col) {
        this.row = row;
        this.col = col;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public void move(int newRow, int newCol) {
        this.row = newRow;
        this.col = newCol;
    }

    @Override
    public String toString(){
        return "hero "+"row="+row+"  col="+col;

    } 
}