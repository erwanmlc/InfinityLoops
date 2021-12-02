package fr.dauphine.JavaAvance.Solve;



import fr.dauphine.JavaAvance.Components.Piece;
import fr.dauphine.JavaAvance.GUI.Grid;

import java.io.*;


public class Checker {
    public static Grid buildGrid(String inputFile) {
        try (BufferedReader br = new BufferedReader(new FileReader(inputFile))) {
            String line;
            int w, h, i, j;
            Grid grid;
            String[] parts;

            line = br.readLine();
            w = Integer.parseInt(line); // width
            line = br.readLine();
            h = Integer.parseInt(line); // height
            grid = new Grid(w, h);

            for (i=0; i<h; i++) {
                for (j=0; j<w; j++) {
                    line = br.readLine();
                    parts = line.split(" ");
                    grid.setPiece(i, j, new Piece(
                            i,
                            j,
                            Integer.parseInt(parts[0]),
                            Integer.parseInt(parts[1])
                    ));
                }
            }

            return grid;

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
            throw new NullPointerException("Error with input file");
        }
        return null;
    }

    /*public void isSolution(Grid grid) {
        boolean bool = false;
        if(...) {   // à implémenter
            bool = true;
        } else {
            bool = false;
        }
        System.out.println("SOLVED: "+bool);
    }*/

// To be implemented
	
}
