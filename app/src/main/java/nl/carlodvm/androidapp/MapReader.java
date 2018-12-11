package nl.carlodvm.androidapp;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class MapReader {
    //Had wereldkaart gwn in /sdcard/ gezet dat was dit pad voor me
    private final String FILENAME = "/storage/emulated/0/begane-grond-kaart.txt";

    private BufferedReader br = null;
    private FileReader fr = null;


    public int allX;
    public int allY;
    public ArrayList<Grid> GridList = new ArrayList<>();
    public int NumberOfCoords;
    public ArrayList<Coords> CoordsList = new ArrayList<>();


    //Er is nu een gridlist, met x/y/abbr/passable
    //En een Coords voor de 9,3,hoofdingang met een x,y,name
    //En een World met een GridList en een maxX,maxY (14,28), is gevuld alleen nog kijken met die coords want die zijn fotos op de gridlist koppeling. een getGrid erin ofzo
    public void readFile() {
        try {
            fr = new FileReader(FILENAME);
            br = new BufferedReader(fr);

            String sCurrentLine;

            StringBuilder File = new StringBuilder();
            StringBuilder Grids = new StringBuilder();

            int lineCount = 0;

            while ((sCurrentLine = br.readLine()) != null) {
                File.append(sCurrentLine + "\n");

                if (lineCount == 0) {
                    String mapCoords[] = sCurrentLine.split(",");
                    allX = Integer.parseInt(mapCoords[0]);
                    allY = Integer.parseInt(mapCoords[1]);
                }

                if (sCurrentLine.length() == allX) {
                    InitGrids(sCurrentLine, lineCount);
                    Grids.append(sCurrentLine + "\n");
                }

                if (lineCount == allY + 1) {
                    NumberOfCoords = Integer.parseInt(sCurrentLine);
                }

                if (lineCount > allY + 1 && lineCount <= allY + 1 + NumberOfCoords) {
                    String coordsLine[] = sCurrentLine.split(",");
                    CoordsList.add(new Coords(coordsLine[2], Integer.parseInt(coordsLine[0]), Integer.parseInt(coordsLine[1])));
                }
                lineCount++;
            }

            setCoordsToGrid(CoordsList, GridList);

            World world = new World(allX, allY, NumberOfCoords);
            world.gridList = GridList;

            for (Coords c : CoordsList) {
                System.out.println(c.toString() + "//" + c.Name);
            }

            for (Grid g :
                    GridList) {
                System.out.println(g.toString() + " " + g.Abbreviation + g.containsPicture + "," + g.pictureName);
            }

            Astar star = new Astar();
            Grid begin = GridList.get(0);
            Grid end =  GridList.get(3);
            star.process(GridList, begin, end);
            star.printPath();

        } catch (IOException e) {

            e.printStackTrace();
        } finally {
            try {
                if (br != null)
                    br.close();

                if (fr != null)
                    fr.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    private void InitGrids(String nextLine, int lineCount) {
        int y = allY + 1 - lineCount;
        int x;
        for (int i = 1; i < nextLine.length() + 1; i++) {
            x = i;

            if (nextLine.charAt(i - 1) == 'N') {
                GridList.add(new Grid(x, y, false, nextLine.charAt(i - 1)));
            }

            if (nextLine.charAt(i - 1) == 'W') {
                GridList.add(new Grid(x, y, true, nextLine.charAt(i - 1)));
            }
        }
    }

    private void setCoordsToGrid(ArrayList<Coords> coordList, ArrayList<Grid> gridList) {
        for (Coords c : coordList) {
            int cX = c.getX();
            int cY = c.getY();

            for (int i = 0; i < gridList.size(); i++) {
                Grid g = gridList.get(i);
                if (g.getX() == cX && g.getY() == cY) {
                    g.pictureName = c.Name;
                    g.containsPicture = true;
                }
            }
        }
    }
}