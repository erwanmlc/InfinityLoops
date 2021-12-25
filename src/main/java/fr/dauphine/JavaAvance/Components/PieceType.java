package fr.dauphine.JavaAvance.Components;

import com.thoughtworks.qdox.model.expression.Or;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

/**
 * 
 * Type of the piece enum
 * 
 */
public enum PieceType {
	// Each Type has a number of connectors and a specific value
    VOID, ONECONN, BAR, TTYPE, FOURCONN, LTYPE;

    public static PieceType getTypefromValue(int typeValue) {
        PieceType pt;
        switch (typeValue) {
            case 0:
                pt = VOID;
                break;
            case 1:
                pt = ONECONN;
                break;
            case 2:
                pt = BAR;
                break;
            case 3:
                pt = TTYPE;
                break;
            case 4:
                pt = FOURCONN;
                break;
            case 5:
                pt = LTYPE;
                break;
            default:
                pt = VOID;
                break;
        }
        return pt;
    }

    public int getTypeValue() {
        int value = 0;
        switch (this) {
            case VOID:
                value = 0;
                break;
            case ONECONN:
                value = 1;
                break;
            case BAR:
                value = 2;
                break;
            case TTYPE:
                value = 3;
                break;
            case FOURCONN:
                value = 4;
                break;
            case LTYPE:
                value = 5;
                break;
        }
        return value;
    }


    public Orientation getOrientation(Orientation ori) {
        return ori;
    }

    public LinkedList<Orientation> setConnectorsList(Orientation ori) {
        LinkedList<Orientation> ll = new LinkedList<Orientation>();
        switch (ori) {
            case NORTH:
                if (this == ONECONN) {
                    ll.add(Orientation.NORTH);
                } else if (this == BAR) {
                    ll.add(Orientation.NORTH);
                    ll.add(Orientation.SOUTH);
                } else if (this == TTYPE) {
                    ll.add(Orientation.WEST);
                    ll.add(Orientation.NORTH);
                    ll.add(Orientation.EAST);
                } else if (this == FOURCONN) {
                    ll.add(Orientation.NORTH);
                    ll.add(Orientation.EAST);
                    ll.add(Orientation.SOUTH);
                    ll.add(Orientation.WEST);
                } else if (this == LTYPE) {
                    ll.add(Orientation.NORTH);
                    ll.add(Orientation.EAST);
                }
                break;

            case EAST:
                if (this == ONECONN) {
                    ll.add(Orientation.EAST);
                } else if (this == BAR) {
                    ll.add(Orientation.EAST);
                    ll.add(Orientation.WEST);
                } else if (this == TTYPE) {
                    ll.add(Orientation.NORTH);
                    ll.add(Orientation.EAST);
                    ll.add(Orientation.SOUTH);
                } else if (this == FOURCONN) {
                    ll.add(Orientation.EAST);
                    ll.add(Orientation.SOUTH);
                    ll.add(Orientation.WEST);
                    ll.add(Orientation.NORTH);
                } else if (this == LTYPE) {
                    ll.add(Orientation.EAST);
                    ll.add(Orientation.SOUTH);
                }
                break;

            case SOUTH:
                if (this == ONECONN) {
                    ll.add(Orientation.SOUTH);
                } else if (this == BAR) {
                    ll.add(Orientation.SOUTH);
                    ll.add(Orientation.NORTH);
                } else if (this == TTYPE) {
                    ll.add(Orientation.EAST);
                    ll.add(Orientation.SOUTH);
                    ll.add(Orientation.WEST);
                } else if (this == FOURCONN) {
                    ll.add(Orientation.SOUTH);
                    ll.add(Orientation.WEST);
                    ll.add(Orientation.NORTH);
                    ll.add(Orientation.EAST);
                } else if (this == LTYPE) {
                    ll.add(Orientation.SOUTH);
                    ll.add(Orientation.WEST);
                }
                break;

            case WEST:
                if (this == ONECONN) {
                    ll.add(Orientation.WEST);
                } else if (this == BAR) {
                    ll.add(Orientation.WEST);
                    ll.add(Orientation.EAST);
                } else if (this == TTYPE) {
                    ll.add(Orientation.SOUTH);
                    ll.add(Orientation.WEST);
                    ll.add(Orientation.NORTH);
                } else if (this == FOURCONN) {
                    ll.add(Orientation.WEST);
                    ll.add(Orientation.NORTH);
                    ll.add(Orientation.EAST);
                    ll.add(Orientation.SOUTH);
                } else if (this == LTYPE) {
                    ll.add(Orientation.WEST);
                    ll.add(Orientation.NORTH);
                }
                break;
        }
        return ll;
    }

    public ArrayList<Orientation> getListOfPossibleOri() {
        ArrayList<Orientation> al = new ArrayList<Orientation>();
        switch (this) {
            case VOID:
                al.add(Orientation.NORTH);
                break;
            case BAR:
                al.add(Orientation.NORTH);
                al.add(Orientation.EAST);
                break;
            case FOURCONN:
                al.add(Orientation.NORTH);
                break;
            default: //ONECONN, TTYPE, LTYPE
                al.add(Orientation.NORTH);
                al.add(Orientation.EAST);
                al.add(Orientation.SOUTH);
                al.add(Orientation.WEST);
                break;
        }
        return al;
    }

    public int getNbConnectors() {
        int nb;
        switch (this) {
            case VOID:
                nb = 0;
                break;
            case BAR:
                nb = 2;
                break;
            case FOURCONN:
                nb = 1;
                break;
            default: //ONECONN, TTYPE, LTYPE
                nb = 4;
                break;
        }
        return nb;
    }
}
