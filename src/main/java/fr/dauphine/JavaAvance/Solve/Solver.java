package fr.dauphine.JavaAvance.Solve;

import java.io.*;
import java.io.IOException;
import java.util.*;
import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import fr.dauphine.JavaAvance.Components.Orientation;
import fr.dauphine.JavaAvance.Components.Pair;
import fr.dauphine.JavaAvance.Components.Piece;
import fr.dauphine.JavaAvance.Components.PieceType;
import fr.dauphine.JavaAvance.GUI.GUI;
import fr.dauphine.JavaAvance.GUI.Grid;

public class Solver {

	private static int i = 0;

	public static void main(String[] args) {

		// To be implemented

	}

	// Solver way too slow with grids taller than 3x3
	public static boolean solveExhaustiveSearch(Grid grid) {
		if (backtrack_v1(grid, grid.getPiece(0, 0))) {
			System.out.println("Nombre de tests effectués: "+i);
			return true;
		}
		else {
			return false;
		}
	}

	public static boolean backtrack_v1(Grid grid, Piece p) {
		Piece next;
		for(Orientation ori : p.getType().getListOfPossibleOri()) {
			i++;
			if (Checker.isSolution(grid)) {
				return true;
			}
			else {
				p.setOrientation(ori.getOriValueFromType(p.getType()));
				// System.out.println(p);
				grid.setPiece(p.getPosY(), p.getPosX(), p);
				next = grid.getNextPiece(p);
				if(next != null){
					backtrack_v1(grid, next);
					// System.out.println("on descend");
					if (Checker.isSolution(grid)) {
						return true;
					}
				}
			}
		}
		return false;
	}

	// Solver better than the previous one but too slow with grids taller than 30x30
	public static boolean solveNonExhaustiveSearch(Grid grid) {
		HashMap<Pair<Boolean, Boolean>, List<Pair<PieceType,List<Integer>>>> hm = new HashMap<>();
		hm.put(new Pair<Boolean, Boolean>(false, false),
				Arrays.asList(	// 0, 1, 5
					new Pair<>(PieceType.VOID, Arrays.asList(0)),
					new Pair<>(PieceType.ONECONN, Arrays.asList(1,2)),
					new Pair<>(null, null),
					new Pair<>(null, null),
					new Pair<>(null, null),
					new Pair<>(PieceType.LTYPE, Arrays.asList(1))
				)
		);
		hm.put(new Pair<Boolean, Boolean>(false, true),
				Arrays.asList( // 1, 2, 3, 5
					new Pair<>(null, null),
					new Pair<>(PieceType.ONECONN, Arrays.asList(0)),
					new Pair<>(PieceType.BAR, Arrays.asList(0)),
					new Pair<>(PieceType.TTYPE, Arrays.asList(1)),
					new Pair<>(null, null),
					new Pair<>(PieceType.LTYPE, Arrays.asList(0))
				)
		);
		hm.put(new Pair<Boolean, Boolean>(true, false),
				Arrays.asList( // 1, 2, 3, 5
					new Pair<>(null, null),
					new Pair<>(PieceType.ONECONN, Arrays.asList(3)),
					new Pair<>(PieceType.BAR, Arrays.asList(1)),
					new Pair<>(PieceType.TTYPE, Arrays.asList(2)),
					new Pair<>(null, null),
					new Pair<>(PieceType.LTYPE, Arrays.asList(2))
				)
		);
		hm.put(new Pair<Boolean, Boolean>(true, true),
				Arrays.asList( // 3, 4, 5
					new Pair<>(null, null),
					new Pair<>(null, null),
					new Pair<>(null, null),
					new Pair<>(PieceType.TTYPE, Arrays.asList(3,0)),
					new Pair<>(PieceType.FOURCONN, Arrays.asList(0)),
					new Pair<>(PieceType.LTYPE, Arrays.asList(3))
				)
		);
		//GUI.startGUI(grid);
		if (backtrack_v2(grid, grid.getPiece(0, 0), hm)) {
			System.out.println("Nombre de tests effectués: "+i);
			return true;
		}
		else {
			return false;
		}
	}

	public static boolean backtrack_v2(Grid grid, Piece p, HashMap<Pair<Boolean, Boolean>, List<Pair<PieceType,List<Integer>>>> hm) {
		Piece next;

		Piece left = grid.leftNeighbor(p);
		boolean lbool = left != null && left.hasRightConnector();

		Piece top = grid.topNeighbor(p);
		boolean tbool = top != null && top.hasBottomConnector();

		List<Pair<PieceType,List<Integer>>> l = hm.get(new Pair<>(lbool, tbool));
		Pair<PieceType, List<Integer>> pair = l.get(p.getType().getTypeValue());
		List<Integer> li = pair.getValue();

		if (li != null) {
			for(int ori : li) {
				i++;
				if (Checker.isSolution(grid)) {
					return true;
				}
				else {
					p.setOrientation(ori);
					// System.out.println(p);
					grid.setPiece(p.getPosY(), p.getPosX(), p);

					next = grid.getNextPiece(p);
					if(next != null){
						backtrack_v2(grid, next, hm);
						// System.out.println("on descend");
						if (Checker.isSolution(grid)) {
							return true;
						}
					}
				}
			}
		}
		return false;
	}
}
