package fr.dauphine.JavaAvance.Solve;

import java.io.IOException;
import java.util.ArrayDeque;
import java.util.Objects;

import fr.dauphine.JavaAvance.Components.Orientation;
import fr.dauphine.JavaAvance.Components.Pair;
import fr.dauphine.JavaAvance.Components.Piece;
import fr.dauphine.JavaAvance.Components.PieceType;
import fr.dauphine.JavaAvance.GUI.Grid;

public class Solver {

	public static void main(String[] args) {

		// To be implemented

	}

	// Solver way too slow with grids taller than 3x3
	public static boolean solveExhaustiveSearch(Grid grid) {
		if (backtrack(grid, grid.getPiece(0, 0))) {
			return true;
		}
		else {
			return false;
		}
	}

	public static boolean backtrack(Grid grid, Piece p) {
		Piece next;
		for(Orientation ori : p.getType().getListOfPossibleOri()) {
			if (Checker.isSolution(grid)) {
				return true;
			}
			else {
				p.setOrientation(ori.getOriValueFromType(p.getType()));
				// System.out.println(p);
				grid.setPiece(p.getPosY(), p.getPosX(), p);
				next = grid.getNextPiece(p);
				if(next != null){
					backtrack(grid, next);
					// System.out.println("on descend");
					if (Checker.isSolution(grid)) {
						return true;
					}
				}
			}
		}
		return false;
	}
}
