package fr.dauphine.JavaAvance.Components;

import javax.lang.model.util.ElementScanner6;
import java.util.HashMap;

/**
 * 
 * Orientation of the piece enum
 * 
 */
public enum Orientation {
	/* Implement all the possible orientations and 
	 *  required methods to rotate
	 */
	NORTH, EAST, SOUTH, WEST;

	public static Orientation getOrifromValue(int orientationValue) {
		switch (orientationValue) {
			case 0:
				return NORTH;
			case 1:
				return EAST;
			case 2:
				return SOUTH;
			case 3:
				return WEST;
		}
		return null;
	}

	public int getOriValueFromType(PieceType pt) {
		int value = 0;
		switch (pt) {
			case VOID:
				value = 0;
			case ONECONN:
				switch(this) {
					case NORTH:
						value = 0;
					case EAST:
						value = 1;
					case SOUTH:
						value = 2;
					case WEST:
						value = 3;
				}
			case BAR:
				switch(this) {
					case NORTH:
						value = 0;
					case EAST:
						value = 1;
					case SOUTH:
						value = 0;
					case WEST:
						value = 1;
				}
			case TTYPE:
				switch(this) {
					case NORTH:
						value = 0;
					case EAST:
						value = 1;
					case SOUTH:
						value = 2;
					case WEST:
						value = 3;
				}
			case FOURCONN:
				value = 0;
			case LTYPE:
				switch(this) {
					case NORTH:
						value = 0;
					case EAST:
						value = 1;
					case SOUTH:
						value = 2;
					case WEST:
						value = 3;
				}
		}
		return value;
	}

	public Orientation turn90() {
		Orientation ori = this;
		switch(this) {
			case NORTH:
				ori = EAST;
				break;
			case EAST:
				ori = SOUTH;
				break;
			case SOUTH:
				ori = WEST;
				break;
			case WEST:
				ori = NORTH;
				break;
		}
		return ori;
	}

	public Orientation getOpposedOrientation() {
		Orientation ori = this;
		switch(this) {
			case NORTH:
				ori = SOUTH;
				break;
			case EAST:
				ori = WEST;
				break;
			case SOUTH:
				ori = NORTH;
				break;
			case WEST:
				ori = EAST;
				break;
		}
		return ori;
	}

	public int[] getOpposedPieceCoordinates(Piece p) {
		int tab[] = new int[2];
		switch(this) {
			case NORTH:
				tab[0]=p.getPosY()-1;
				tab[1]=p.getPosX();
				break;
			case EAST:
				tab[0]=p.getPosY();
				tab[1]=p.getPosX()+1;
				break;
			case SOUTH:
				tab[0]=p.getPosY()+1;
				tab[1]=p.getPosX();
				break;
			case WEST:
				tab[0]=p.getPosY();
				tab[1]=p.getPosX()-1;
				break;
		}
		return tab;
	}

}
