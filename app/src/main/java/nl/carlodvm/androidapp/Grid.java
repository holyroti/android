package nl.carlodvm.androidapp;

public class Grid {
    public int x;
    public int y;
    public boolean passable;
    public Character Abbreviation;
    public String CoordinateString;

    public Grid(int x, int y, boolean passable, Character Abbr){
        this.x = x;
        this.y = y;
        this.passable = passable;
        this.Abbreviation = Abbr;
        this.CoordinateString = "("+x+","+y+")";
    }

    public Grid(){}
}
