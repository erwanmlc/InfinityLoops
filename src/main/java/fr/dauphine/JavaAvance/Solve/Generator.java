package fr.dauphine.JavaAvance.Solve;


import java.io.*;
import java.nio.charset.*;
import java.nio.file.*;
import java.util.*;

import com.thoughtworks.qdox.model.expression.Or;
import fr.dauphine.JavaAvance.Components.Orientation;
import fr.dauphine.JavaAvance.Components.Pair;
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
	 * @param fileName
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
			HashMap<Pair<Boolean, Boolean>, List<Pair<PieceType,List<Integer>>>> hm = new HashMap<>();
			hm.put(new Pair<Boolean, Boolean>(false, false),
					Arrays.asList(	// 0, 1, 5
							new Pair<>(PieceType.VOID, Arrays.asList(0)),
							new Pair<>(PieceType.ONECONN, Arrays.asList(1,2)),
							new Pair<>(PieceType.LTYPE, Arrays.asList(1))
					)
			);
			hm.put(new Pair<Boolean, Boolean>(false, true),
					Arrays.asList( // 1, 2, 3, 5
							new Pair<>(PieceType.ONECONN, Arrays.asList(0)),
							new Pair<>(PieceType.BAR, Arrays.asList(0)),
							new Pair<>(PieceType.TTYPE, Arrays.asList(1)),
							new Pair<>(PieceType.LTYPE, Arrays.asList(0))
					)
			);
			hm.put(new Pair<Boolean, Boolean>(true, false),
					Arrays.asList( // 1, 2, 3, 5
							new Pair<>(PieceType.ONECONN, Arrays.asList(3)),
							new Pair<>(PieceType.BAR, Arrays.asList(1)),
							new Pair<>(PieceType.TTYPE, Arrays.asList(2)),
							new Pair<>(PieceType.LTYPE, Arrays.asList(2))
					)
			);
			hm.put(new Pair<Boolean, Boolean>(true, true),
					Arrays.asList( // 3, 4, 5
							new Pair<>(PieceType.TTYPE, Arrays.asList(3,0)),
							new Pair<>(PieceType.FOURCONN, Arrays.asList(0)),
							new Pair<>(PieceType.LTYPE, Arrays.asList(3))
					)
			);

			ArrayList<Orientation> lori = new ArrayList<Orientation>();
			lori.add(Orientation.NORTH);
			lori.add(Orientation.EAST);
			lori.add(Orientation.SOUTH);
			lori.add(Orientation.WEST);

			ArrayList<PieceType> tori = new ArrayList<>();
			tori.add(PieceType.VOID);
			tori.add(PieceType.ONECONN);
			tori.add(PieceType.BAR);
			tori.add(PieceType.TTYPE);
			tori.add(PieceType.FOURCONN);
			tori.add(PieceType.LTYPE);

			Piece left = null, top = null;
			boolean lbool = false, tbool = false;
			int i, j, type;

			for (i=0; i<inputGrid.getHeight(); i++) {
				for (j = 0; j < inputGrid.getWidth(); j++) {
					inputGrid.setPiece(i, j, new Piece(i, j));
				}
			}

			writer.write(String.valueOf(inputGrid.getWidth()));
			writer.newLine();
			writer.write(String.valueOf(inputGrid.getHeight()));
			writer.newLine();
			for (i=0; i<inputGrid.getHeight(); i++) {
				for (j=0; j<inputGrid.getWidth(); j++) {

					left = inputGrid.leftNeighbor(inputGrid.getPiece(i,j));
					lbool = left != null && left.hasRightConnector();

					top = inputGrid.topNeighbor(inputGrid.getPiece(i,j));
					tbool = top != null && top.hasBottomConnector();

					List<Pair<PieceType,List<Integer>>> l = hm.get(new Pair<>(lbool, tbool));

					Pair<PieceType, List<Integer>> p = new Pair(null, null);
					type = new Random().nextInt(l.size());
					p = l.get(type);

					List<Integer> li = p.getValue();

					inputGrid.getPiece(i,j).setType(p.getKey());
					inputGrid.getPiece(i,j).setOrientation(li.get(new Random().nextInt(li.size())));

					boolean lastcol = j == inputGrid.getWidth()-1;
					boolean lastline = i == inputGrid.getHeight()-1;

					while ((lastcol && inputGrid.getPiece(i,j).hasRightConnector()) || (lastline && inputGrid.getPiece(i,j).hasBottomConnector())) {
						type = new Random().nextInt(l.size());
						p = l.get(type);
						li = p.getValue();

						inputGrid.getPiece(i,j).setType(p.getKey());
						inputGrid.getPiece(i,j).setOrientation(li.get(new Random().nextInt(li.size())));
					}

					ArrayList<Orientation> al = inputGrid.getPiece(i,j).getType().getListOfPossibleOri();

					// we write the line with the type of the Piece and his orientation separed by a space
					writer.write(tori.indexOf(inputGrid.getPiece(i,j).getType()) +" "+ lori.indexOf(al.get(new Random().nextInt(al.size()))));
					writer.newLine();
				}
			}
		} catch(IOException e){
			e.printStackTrace();
		}


		// Test
	}

	public static void generateFileFromGrid(String fileName, Grid inputGrid) {
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
			writer.write(String.valueOf(inputGrid.getWidth()));
			writer.newLine();
			writer.write(String.valueOf(inputGrid.getHeight()));
			writer.newLine();
			for (int i=0; i<inputGrid.getHeight(); i++) {
				for (int j=0; j<inputGrid.getWidth(); j++) {
					// we write the line with the type of the Piece and his orientation separed by a space
					writer.write(
						inputGrid.getPiece(i,j).getType().getTypeValue()
						+" "
						+ inputGrid.getPiece(i,j).getOrientation().getOriValueFromType(inputGrid.getPiece(i,j).getType())
					);
					writer.newLine();
				}
			}
		} catch(IOException e){
			e.printStackTrace();
		}
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