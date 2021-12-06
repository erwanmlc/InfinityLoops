package fr.dauphine.JavaAvance.Solve;


import java.io.*;
import java.nio.charset.*;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.Random;

import fr.dauphine.JavaAvance.Components.Piece;
import fr.dauphine.JavaAvance.Components.PieceType;
import fr.dauphine.JavaAvance.GUI.Grid;

/**
 * Generate a solution, number of connexe composant is not finished
 *
 */

public class Generator {

	private static Grid filledGrid;

	/**
	 * @param output
	 *            file name
	 * @throws IOException
	 *             - if an I/O error occurs.
	 * @return a File that contains a grid filled with pieces (a level)
	 * @throws FileNotFoundException
	 * @throws UnsupportedEncodingException
	 */
	public static void generateLevel(String fileName, Grid inputGrid) {
		Path path = Paths.get(fileName);
		try {
			if (Files.notExists(path)) {
				Files.createFile(path);
			} else {
				System.out.println("File : "+fileName+" already exists!");
				System.exit(-1);
			}
		} catch(IOException e) {
			e.printStackTrace();
		}

		try(BufferedWriter writer = Files.newBufferedWriter(path, Charset.forName("UTF-8"))){
			ArrayList<Integer> possiblesTypes = new ArrayList<Integer>();
			Piece left, top;
			boolean lbool = false, tbool = false;
			int i, j, type, ori;

			writer.write(String.valueOf(inputGrid.getWidth()));
			writer.newLine();
			writer.write(String.valueOf(inputGrid.getHeight()));
			writer.newLine();
			for (i=0; i<inputGrid.getHeight(); i++) {
				for (j=0; j<inputGrid.getWidth(); j++) {
					inputGrid.setPiece(i,j,new Piece(i,j));
					left = inputGrid.leftNeighbor(inputGrid.getPiece(i,j));
					if(left != null && left.hasRightConnector()) {
						lbool = true;
					}
					top = inputGrid.topNeighbor(inputGrid.getPiece(i,j));
					if(top != null && top.hasBottomConnector()) {
						tbool = true;
					}
					if (inputGrid.isCorner(i,j)) {	// PieceType : 0, 1 ou 5
						System.out.println("isCorner");
						possiblesTypes.add(0);
						possiblesTypes.add(1);
						possiblesTypes.add(5);
						if (lbool ^ tbool) {
							possiblesTypes.remove(Integer.valueOf(5)); // if it is the bottom right corner and and only one connector
						}
					} else if (inputGrid.isBorderColumn(i,j) || inputGrid.isBorderLine(i,j)) {	// PieceType : 0, 1, 2, 3 ou 5
						System.out.println("isBorder or isColumn");
						possiblesTypes.add(0);
						possiblesTypes.add(1);
						possiblesTypes.add(2);
						possiblesTypes.add(3);
						possiblesTypes.add(5);
					} else {
						System.out.println("isNormal");
						possiblesTypes.add(0);
						possiblesTypes.add(1);
						possiblesTypes.add(2);
						possiblesTypes.add(3);
						possiblesTypes.add(4);
						possiblesTypes.add(5);
					}

					if (lbool && tbool) {
						possiblesTypes.remove(Integer.valueOf(0));
						possiblesTypes.remove(Integer.valueOf(1));
						possiblesTypes.remove(Integer.valueOf(2));
					} else if (lbool || tbool) {
						possiblesTypes.remove(Integer.valueOf(0));
					}

					// we randomly draw a possible PieceType for the current Piece
					type = new Random().nextInt(possiblesTypes.size());
					inputGrid.getPiece(i,j).setType(PieceType.getTypefromValue(possiblesTypes.get(type)));

					// we randomly draw an Orientation for the current Piece
					ori = new Random().nextInt(4);
					inputGrid.getPiece(i,j).setOrientation(ori);

					// we write the line with the type of the Piece and his orientation separed by a space
					writer.write(type+" "+ori);

					lbool = false;
					tbool = false;
					writer.newLine();
					possiblesTypes.clear();
				}
			}
		} catch(IOException e){
			e.printStackTrace();
		}


		// Test
	}
	public static int[] copyGrid(Grid filledGrid, Grid inputGrid, int i, int j) {
		Piece p;
		int hmax = inputGrid.getHeight();
		int wmax = inputGrid.getWidth();

		if (inputGrid.getHeight() != filledGrid.getHeight())
			hmax = filledGrid.getHeight() + i; // we must adjust hmax to have the height of the original grid
		if (inputGrid.getWidth() != filledGrid.getWidth())
			wmax = filledGrid.getWidth() + j;

		int tmpi = 0;// temporary variable to stock the last index
		int tmpj = 0;

		// DEBUG System.out.println("copyGrid : i =" + i + " & j = " + j);
		// DEBUG System.out.println("hmax = " + hmax + " - wmax = " + wmax);
		for (int x = i; x < hmax; x++) {
			for (int y = j; y < wmax; y++) {
				// DEBUG System.out.println("x = " + x + " - y = " + y);
				p = filledGrid.getPiece(x - i, y - j);
				// DEBUG System.out.println("x = " + x + " - y = " +
				// y);System.out.println(p);
				inputGrid.setPiece(x, y, new Piece(x, y, p.getType(), p.getOrientation()));
				// DEBUG System.out.println("x = " + x + " - y = " +
				// y);System.out.println(inputGrid.getPiece(x, y));
				tmpj = y;
			}
			tmpi = x;
		}
		//DEBUGSystem.out.println("tmpi =" + tmpi + " & tmpj = " + tmpj);
		return new int[] { tmpi, tmpj };
	}

}