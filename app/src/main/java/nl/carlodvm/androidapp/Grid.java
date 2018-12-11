package nl.carlodvm.androidapp;

public class Grid {
    public int x;
    public int y;
    public boolean passable;
    public char Abbreviation;
    public String CoordinateString;

    /*Properties for Coordinates/ pictures*/
    public String pictureName;
    public boolean containsPicture;

    public Grid parent;
    public int Cost;
    public int finalCost;
    public boolean solution = false; //if grid is part of the solution path
    public int heuristicCost;

    public Grid(int x, int y, boolean passable, char Abbr){
        this.x = x;
        this.y = y;
        this.passable = passable;
        this.Abbreviation = Abbr;
        this.CoordinateString = "("+x+","+y+")";
    }

    public Grid(){}

    public Grid(int x, int y){
        this.x = x;
        this.y = y;
    }

    public int getY() {return this.y;}
    public int getX() {return this.x;}

    public String toString(){
        return "("+x+","+y+")";
    }


}
