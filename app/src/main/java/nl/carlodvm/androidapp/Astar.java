package nl.carlodvm.androidapp;

import java.util.ArrayList;

public class Astar {

    public ArrayList<Grid> openPath = new ArrayList<>();
    public ArrayList<Grid> closedPath = new ArrayList<>();
    public ArrayList<Grid> resultPath = new ArrayList<>();

    public Grid left;
    public Grid right;
    public Grid up;
    public Grid down;

    public void process(ArrayList<Grid> gridList, Grid beginGrid, Grid endGrid)
    {
        Grid current = new Grid(beginGrid.getX(), beginGrid.getY());
        double totalDistance = calcDistance(current, endGrid);

        while(current.getX() != endGrid.getX() && current.getY() != endGrid.getY())
        {
            //get neighbours from current grid
            for(Grid g : gridList)
            {
                if(g.passable == true) {

                    if (g.getX() == current.getX() - 1 && g.getY() == current.getY())
                        left = g;

                    if (g.getX() == current.getX() + 1 && g.getY() == current.getY())
                        right = g;

                    if (g.getX() == current.getX() && g.getY() == current.getY() + 1)
                        up = g;

                    if (g.getX() == current.getX() - 1 && g.getY() == current.getY() - 1)
                        down = g;

                    double leftCost = calcDistance(left, endGrid);
                    double rightCost = calcDistance(right, endGrid);
                    double upCost = calcDistance(up, endGrid);
                    double downCost = calcDistance(down, endGrid);

                    /*wat calc ik eig hiermee moet ff kijken??*/

                    double totalLeftCost = leftCost - rightCost + totalDistance;
                    double totalRightCost = rightCost - leftCost + totalDistance;
                    double totalUpCost = upCost - downCost + totalDistance;
                    double totalDownCost = downCost - upCost+ totalDistance;

                    if (totalLeftCost < totalRightCost) {
                        current = left;
                    }
                    else if (totalRightCost < totalLeftCost){
                        current = right;
                    }
                    else if(totalDownCost < totalUpCost){
                        current = down;
                    }
                    else if(totalUpCost < totalDownCost){
                        current = up;
                    }
                    resultPath.add(current);
                }


            }
        }
    }

    //Totale pythagoras waarde?
    public double calcDistance(Grid current, Grid end)
    {
        double sx = current.getX();
        double sy = current.getX();
        double ex = end.getX();
        double ey = end.getY();
        return Math.sqrt(((ex-sx) * (ex-sx)) +  ((ey-sy) * (ey-sy)));
    }

    public String printPath(){
        for(Grid g : resultPath)
        {
           return "PATH " + g.toString();
        }
        return "No path found";
    }
}
