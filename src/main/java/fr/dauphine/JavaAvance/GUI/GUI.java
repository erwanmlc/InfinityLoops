package fr.dauphine.JavaAvance.GUI;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.Random;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import fr.dauphine.JavaAvance.Components.Orientation;
import fr.dauphine.JavaAvance.Components.Piece;
import fr.dauphine.JavaAvance.Components.PieceType;
import fr.dauphine.JavaAvance.Solve.Checker;

/**
 * This class handles the GUI
 * 
 *
 */
public class GUI implements MouseListener {

	private JFrame frame;
	private Grid grid;
	private JLabel label;
	private JPanel pan;
	private GridLayout layer;
	private boolean solved = false;

	/**
	 * 
	 * @param inputFile
	 *            String from IO
	 * @throws IOException
	 *             if there is a problem with the gui
	 */
	public static void startGUI(String inputFile) throws NullPointerException {
		// We have to check that the grid is generated before to launch the GUI
		// construction

		Runnable task = new Runnable() {
			
			public void run() {
				Grid grid = Checker.buildGrid(inputFile);
				SwingUtilities.invokeLater(new Runnable() {
					public void run() {
						GUI window;
						window = new GUI(grid);
						window.frame.setVisible(true);
					}
				});
			}
		};
		new Thread(task).start();

	}

	// public static void startGUI(Grid grid) throws NullPointerException {
	// 	// We have to check that the grid is generated before to launch the GUI
	// 	// construction

	// 	Runnable task = new Runnable() {
			
	// 		public void run() {
	// 			SwingUtilities.invokeLater(new Runnable() {
	// 				public void run() {
	// 					GUI window;
	// 					window = new GUI(grid);
	// 					window.frame.setVisible(true);
	// 				}
	// 			});
	// 		}
	// 	};
	// 	new Thread(task).start();

	// }

	/**
	 * Create the application.
	 * 
	 * @throws IOException
	 */
	public GUI(Grid grid) {

		initialize(grid);
	}

	/**
	 * Initialize the contents of the frame.
	 * 
	 * @throws IOException
	 */
	private void initialize(Grid grid) {
		this.grid = grid;
		System.out.println("frame");
		frame = new JFrame();
		frame.setLocation(100,100);
		frame.setSize(800,800);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// layer = new GridLayout(0,1);
		layer = new GridLayout(grid.getHeight(), grid.getWidth());
		pan = new JPanel();
		frame.setContentPane(pan);
		pan.setLayout(layer);

		// pan.add(new JLabel(getImageIcon(grid.getPiece(0,0))));
		for (int i=0; i<grid.getHeight(); i++) {
			for (int j=0; j<grid.getWidth(); j++) {
				// System.out.println(frame.getHeight()/grid.getHeight()+"  "+frame.getWidth()/grid.getWidth());
				// pan.add(new JLabel(new ImageIcon(getImageIcon(grid.getPiece(i, j)).getImage().getScaledInstance(frame.getHeight()/grid.getHeight(), frame.getWidth()/grid.getWidth(), Image.SCALE_SMOOTH))));
				label = new JLabel();
				label.setIcon(new ImageIcon(new ImageIcon((getImageIcon(grid.getPiece(i, j)))).getImage().getScaledInstance(frame.getHeight()/grid.getHeight(), frame.getWidth()/grid.getWidth(), Image.SCALE_SMOOTH)));
				pan.add(label);
			}
		}
		//frame.getContentPane().add(label);
     	//frame.validate();
		frame.addMouseListener(this);
		//frame.pack();		
		// To implement:
		// creating frame, labels
		// Implementing method mouse clicked of interface MouseListener.
	}

	public void mouseClicked(MouseEvent e) {  
		pan.removeAll();
		// System.out.println("x="+e.getX()+", y="+e.getY());
		for (int i=0, stepy=16; i<grid.getHeight(); i++, stepy+=frame.getHeight()/grid.getHeight()) {
			for (int j=0, stepx=0; j<grid.getWidth(); j++, stepx+=frame.getWidth()/grid.getWidth()) {
				if(e.getY() >= stepy && e.getY() < stepy+frame.getHeight()/grid.getHeight() && e.getX() >= stepx && e.getX() < stepx+frame.getWidth()/grid.getWidth()) {
					grid.getPiece(i, j).turn();
					if(solved = Checker.isSolution(grid)) {
						System.out.println("SOLVED: " + solved);
						System.out.println("GG ! YOU WIN !!!!!!!!!!!!!!!!!");
						//System.exit(1); // winner
					}
				}
				label = new JLabel();
				label.setIcon(new ImageIcon(new ImageIcon((getImageIcon(grid.getPiece(i, j)))).getImage().getScaledInstance(frame.getHeight()/grid.getHeight(), frame.getWidth()/grid.getWidth(), Image.SCALE_SMOOTH)));
				pan.add(label);
			}
		}
		//frame.validate();
		frame.invalidate();
		frame.validate();
		frame.repaint();
    }  
    public void mouseEntered(MouseEvent e) {}  
    public void mouseExited(MouseEvent e) {}  
    public void mousePressed(MouseEvent e) {}  
    public void mouseReleased(MouseEvent e) {}  

	// public static void updateFrame() {
	// 	System.out.println("update");
	// 	pan.removeAll();
	// 	for (int i=0; i<grid.getHeight(); i++) {
	// 		for (int j=0; j<grid.getWidth(); j++) {	
	// 			label = new JLabel();
	// 			label.setIcon(new ImageIcon(new ImageIcon((getImageIcon(grid.getPiece(i, j)))).getImage().getScaledInstance(frame.getHeight()/grid.getHeight(), frame.getWidth()/grid.getWidth(), Image.SCALE_SMOOTH)));
	// 			pan.add(label);
	// 		}
	// 	}
	// 	frame.invalidate();
	// 	frame.validate();
	// 	frame.repaint();
	// }

	/**
	 * Display the correct image from the piece's type and orientation
	 * 
	 * @param p
	 *            the piece
	 * @return an image icon
	 */
	private String getImageIcon(Piece p) {
		int n = 0;
		
		switch(p.getType()) {
			case ONECONN:
				switch(p.getOrientation()) {
					case NORTH:
						n = 1;
						break;
					case EAST:
						n = 2;
						break;
					case SOUTH:
						n = 3;
						break;
					case WEST:
						n = 4;
						break;
				}
				break;
			case BAR:
				switch(p.getOrientation()) {
					case NORTH:
						n = 5;
						break;
					case EAST:
						n = 6;
						break;
					case SOUTH:
						n = 5;
						break;
					case WEST:
						n = 6;
						break;
				}
				break;
			case TTYPE:
				switch(p.getOrientation()) {
					case NORTH:
						n = 7;
						break;
					case EAST:
						n = 8;
						break;
					case SOUTH:
						n = 9;
						break;
					case WEST:
						n = 10;
						break;
				}
				break;
			case FOURCONN:
				n = 11;
				break;
			case LTYPE:
				switch(p.getOrientation()) {
					case NORTH:
						n = 12;
						break;
					case EAST:
						n = 13;
						break;
					case SOUTH:
						n = 14;
						break;
					case WEST:
						n = 15;
						break;
				}
				break;
			default:
				return null;
		}

		return "src\\main\\resources\\fr\\dauphine\\JavaAvance\\icons\\io\\"+n+".png";
	}

}
