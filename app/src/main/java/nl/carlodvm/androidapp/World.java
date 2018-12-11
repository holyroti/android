package nl.carlodvm.androidapp;

import java.util.List;

public class World {
    public List<Grid> gridList;
    public int x;
    public int y;

    public int numberOfCoords;

    public World(Grid grid){
        this.gridList.add(grid);
    }

    public World(int x, int y, int numberOfCoords){
        this.x = x;
        this.y = y;
        int worldSize[][] = new int[x][y]; //Is dit wel nodig want vgm gebruiken we geen 2d array maar object oriented
        this.numberOfCoords = numberOfCoords;


    }

    public String toString(){
        return "Dit is een kaart van de begane grond";
    }
}
