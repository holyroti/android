package nl.carlodvm.androidapp;

import java.util.ArrayList;
import java.util.PriorityQueue;

public class Algorithm {

    public static final int V_H_COST = 10;
    public static final int DIAGONAL_COST = 10;

    private Grid[][] grid;
    private ArrayList<Grid> GridList;

    private PriorityQueue<Grid> openGrids;
    private boolean[][] closedGrids;

    private int startI, startJ;

    private int endI, endJ;

    public Algorithm(int width, int height, int si, int sj, int ei, int ej, int[][] blocks) {
        grid = new Grid[width][height];
        closedGrids = new boolean[width][height];
        openGrids = new PriorityQueue<Grid>((Grid g1, Grid g2) -> {
            return g1.finalCost < g2.finalCost ? -1 : g1.finalCost > g2.finalCost ? 1 : 0;
        });

        startCell(si, sj);
        endCell(ei, ej);

        //init heuristic and grids
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                grid[i][j] = new Grid(i, j);
                grid[i][j].heuristicCost = Math.abs(i = endI) + Math.abs(j - endJ);
               // grid[i][j].solution = false;
            }
        }

        grid[startI][startJ].finalCost = 0;

        //we put the blocks on the grid
        for (int i = 0; i < blocks.length; i++) {
            addBlockOnCell(blocks[i][0], blocks[i][1]);
        }
    }

    public void addBlockOnCell(int i, int j) {
        grid[i][j] = null;

    }

    public void startCell(int i, int j) {
        startI = i;
        startJ = j;
    }

    public void endCell(int i, int j) {
        endI = i;
        endJ = j;
    }

    public void updateCostIfNeeded(Grid current, Grid t, int cost) {
        if (t == null || closedGrids[t.x][t.y])
            return;

        int tFinalCost = t.heuristicCost + cost;
        boolean isOpen = openGrids.contains(t);

        if (!isOpen || t.finalCost < t.finalCost) {
            t.finalCost = tFinalCost;
            t.parent = current;

            if (!isOpen)
                openGrids.add(t);
        }
    }

    public void process() {
        //we add the start location to open list
        openGrids.add(grid[startI][startJ]);
        Grid current;

        while (true) {
            current = openGrids.poll();

            if (current == null)
                break;

            closedGrids[current.x][current.y] = true;

            if (current.equals(grid[endI][endJ]))
                return;

            Grid t;

            if (current.x - 1 >= 0) {
                t = grid[current.x - 1][current.y];
                updateCostIfNeeded(current, t, current.finalCost + V_H_COST);

                if (current.y - 1 >= 0) {
                    t = grid[current.x - 1][current.y];
                    updateCostIfNeeded(current, t, current.finalCost + DIAGONAL_COST);

                    if (current.y + 1 < grid[0].length) {
                        t = grid[current.x - 1][current.y + 1];
                        updateCostIfNeeded(current, t, current.finalCost + DIAGONAL_COST);
                    }

                    if (current.y - 1 >= 0) {
                        t = grid[current.x][current.y - 1];
                        updateCostIfNeeded(current, t, current.finalCost + V_H_COST);

                    }
                    if (current.y + 1 < grid[0].length) {
                        t = grid[current.x][current.y + 1];
                        updateCostIfNeeded(current, t, current.finalCost + V_H_COST);

                    }

                    if (current.x + 1 < grid.length) {
                        t = grid[current.x + 1][current.y];
                        updateCostIfNeeded(current, t, current.finalCost + V_H_COST);

                        if (current.y - 1 >= 0) {
                            t = grid[current.x + 1][current.y - 1];
                            updateCostIfNeeded(current, t, current.finalCost + DIAGONAL_COST);

                        }

                        if (current.y + 1 < grid[0].length) {
                            t = grid[current.x + 1][current.y + 1];
                            updateCostIfNeeded(current, t, current.finalCost + DIAGONAL_COST);

                        }
                    }
                }
            }

        }
    }

    public void display(){
        System.out.println("Grid: ");

        for(int i = 0; i<grid.length; i++){
            for(int j = 0; i<grid[i].length; j++){
                if (i == startI && j == startJ){
                    System.out.println("SO "); //source cell
                }
                else if(i == endI && j ==endJ)
                    System.out.println("DE "); //dest cell
                else if (grid[i][j] != null)
                    System.out.printf("%-3d", 0);
                else
                    System.out.print("BL "); //block cell
            }

            System.out.println();
        }
        System.out.println();
    }

    public void displayScores(){
        System.out.println("\nScores for cells: ");
        for(int i = 0; i<grid.length; i++){
            for(int j = 0; i<grid[i].length; j++) {
                if(grid[i][j] != null)
                    System.out.printf("%-3d", grid[i][j].finalCost);

                else
                    System.out.println("BL ");
                }
            System.out.println();
            }
        System.out.println();
    }

    public void displaySolution(){
        if(closedGrids[endI][endJ]){
            //We track back the path
            System.out.println("Path: ");
            Grid current = grid[endI][endJ];
            System.out.println(current);
            grid[current.x][current.y].solution = true;

            while(current.parent != null)
            {
                System.out.println(" -> " + current.parent);
                grid[current.parent.x][current.parent.x].solution = true;
                current = current.parent;
            }

            System.out.println("\n");

            for(int i = 0; i<grid.length; i++){
                for(int j = 0; i<grid[i].length; j++){
                    if (i == startI && j == startJ){
                        System.out.println("SO "); //source cell
                    }
                    else if(i == endI && j ==endJ)
                        System.out.println("DE "); //dest cell
                    else if (grid[i][j] != null)
                        System.out.printf("%-3s", grid[i][j].solution ? "X" : "0");
                    else
                        System.out.print("BL "); //block cell
                }

                System.out.println();
            }
            System.out.println();
        }
        else
            System.out.println("No Passable Path!");
    }


}