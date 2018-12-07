package nl.carlodvm.androidapp;

public class Coords
{
    String Name;
    int x;
    int y;

    //On this grid?
    Grid grid;

    public Coords(String Name, int x, int y){
        this.Name = Name;
        this.x = x;
        this.y = y;
    }
}
