import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Stack;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;


public class DrawingPanel extends JPanel {
	
	//helps when adding Points in the array
	private boolean ifDragging = false;
	//variabiles that hold the condition of a certain button
	private boolean isReflected = false;
	private boolean showSectors = false;
	private boolean erase = false;
	
	private String pictureName;
	
	//setting initial values for the start of the doily
	private int numberOfSectors = 6;
	private Color color = Color.WHITE;
	private int brushSize ;
	
	//store the points and the lines that have to be drawn
	private MyPoint myPoint = new MyPoint(color,brushSize, isReflected,false);
    private ArrayList<MyPoint> singlePoint = new ArrayList<MyPoint>();
	private ArrayList<MyPoint>pointsForLine = new ArrayList<MyPoint>();
	private ArrayList<ArrayList<MyPoint>> figureToDraw = new ArrayList<ArrayList<MyPoint>>();
	
	//used for undo and redo
	private Stack<ArrayList<MyPoint>> drawingsRemoved = new Stack <ArrayList<MyPoint>>();
	
	//listener for the drawings 
	MouseDrawListener mouseDrawListener = new MouseDrawListener();
	
	//adding the listener
	public DrawingPanel() {
		
		this.setBackground(Color.BLACK);
		addMouseListener(mouseDrawListener);
		addMouseMotionListener(mouseDrawListener);
	}
	
		//mouse listener that decides when and where to add the points
		public class MouseDrawListener implements MouseListener, MouseMotionListener{
	
			//adds points for the lines drawn
			@Override
			public void mouseDragged(MouseEvent evt) {
				ifDragging = true;
				if(!erase) 
					myPoint = new MyPoint(color,brushSize,isReflected,true);
				else 
					myPoint = new MyPoint(Color.BLACK,brushSize,isReflected,true);
				myPoint.setLocation(evt.getX(), evt.getY());
				pointsForLine.add(myPoint);
			
				repaint();
			}
			
			//adds the points that are actually points
			@Override
			public void mouseClicked(MouseEvent evt) {
				//removes the element that was thought to be a point
				if(figureToDraw.size()!=0)
					figureToDraw.remove(figureToDraw.size()-1);
				//checks weather is in the erase mode or not
				if(!erase)
					myPoint = new MyPoint(color,brushSize,isReflected,false);
				else
					myPoint = new MyPoint(Color.BLACK,brushSize,isReflected,false);
				myPoint.setLocation(evt.getX(), evt.getY());
				singlePoint.add(myPoint);
				figureToDraw.add(singlePoint);
				
				repaint();
			}
	
			@Override
			public void mouseEntered(MouseEvent arg0) {}
			@Override
			public void mouseExited(MouseEvent arg0) {}
			@Override
			//adding the line to the main array
			public void mousePressed(MouseEvent evt) {
					figureToDraw.add(pointsForLine);
			}
			@Override
			public void mouseMoved(MouseEvent arg0) {}
			@Override
			//creating new arrays for the next drawing
			public void mouseReleased(MouseEvent arg0) {
				singlePoint = new ArrayList<MyPoint>();
				if(ifDragging) 
					pointsForLine = new ArrayList<MyPoint>();
				ifDragging = false;
				
				//cleares the stack because you can't redo after clear dispay and undo
				drawingsRemoved = new Stack<ArrayList<MyPoint>>();
				repaint();
			}
			
	   }
	
	//draws the sectors,rotates the same line in different positions
	public void drawSectorLines(Graphics2D g2) {
		
		g2.setColor(Color.WHITE);
		g2.setStroke(new BasicStroke(1));
		for(int i=1; i<=numberOfSectors; i++) {
		      if(showSectors && numberOfSectors!=1) {
		    		g2.drawLine(0, 0, 0, getHeight()/2);
					g2.rotate(Math.toRadians((double)360/numberOfSectors));
		      }
		}
	}

	//paints all the drawings
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D)g;
		//makes the experience smoother through rendering
		RenderingHints h = new RenderingHints(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
		g2.setRenderingHints(h);
		drawFigure(g2);
		drawSectorLines(g2);
		
	}
	
	// draws the points and the lines
	public void drawFigure(Graphics2D g2) {
		// the beginning is the center
		g2.translate(getWidth() / 2, getHeight() / 2);

		for (int j = 0; j < figureToDraw.size(); j++) {
			// checks if it is a point,when starting a line with don't want an oval to
			// appear
			if (figureToDraw.get(j).size() == 1 && !figureToDraw.get(j).get(0).isThePointIsForLine())
				for (int h = 0; h < figureToDraw.get(j).size(); h++) {
					for (int i = 1; i <= numberOfSectors; i++) {

						g2.rotate(Math.toRadians((double) 360 / numberOfSectors));
						g2.setColor(figureToDraw.get(j).get(h).getColor());
						g2.setStroke(new BasicStroke((float) figureToDraw.get(j).get(0).getBrushSize()));
						// a single pixel can't be split, so there mus be a line drawn and not an oval
						if (figureToDraw.get(j).get(h).getBrushSize() == 1) {
							g2.drawLine((int) figureToDraw.get(j).get(0).getX() - getWidth() / 2,
									(int) figureToDraw.get(j).get(0).getY() - getHeight() / 2,
									(int) figureToDraw.get(j).get(0).getX() - getWidth() / 2,
									(int) figureToDraw.get(j).get(0).getY() - getHeight() / 2);

							if (figureToDraw.get(j).get(0).getIsReflected())
								g2.drawLine(((int) -(figureToDraw.get(j).get(0).getX() - getWidth() / 2)),
										(int) figureToDraw.get(j).get(0).getY() - getHeight() / 2,
										((int) -(figureToDraw.get(j).get(0).getX() - getWidth() / 2)),
										(int) figureToDraw.get(j).get(0).getY() - getHeight() / 2);
						} else {
							g2.fillOval((int) figureToDraw.get(j).get(0).getX() - getWidth() / 2,
									(int) figureToDraw.get(j).get(0).getY() - getHeight() / 2,
									figureToDraw.get(j).get(0).getBrushSize(),
									figureToDraw.get(j).get(0).getBrushSize());

							// mathematical function is used instead of calculating the position between two
							// drawn points
							if (figureToDraw.get(j).get(0).getIsReflected())
								g2.fillOval(
										((int) -(figureToDraw.get(j).get(0).getX() - getWidth() / 2)
												- figureToDraw.get(j).get(0).getBrushSize()),
										(int) figureToDraw.get(j).get(0).getY() - getHeight() / 2,
										figureToDraw.get(j).get(0).getBrushSize(),
										figureToDraw.get(j).get(0).getBrushSize());
						}
					}
				}
			else {
				// it is a line
				for (int h = 0; h < figureToDraw.get(j).size() - 1; h++) {
					for (int i = 1; i <= numberOfSectors; i++) {

						g2.rotate(Math.toRadians((double) 360 / numberOfSectors));
						g2.setColor(figureToDraw.get(j).get(h).getColor());
						g2.setStroke(new BasicStroke((float) figureToDraw.get(j).get(h).getBrushSize()));

						g2.drawLine((int) figureToDraw.get(j).get(h).getX() - getWidth() / 2,
								(int) figureToDraw.get(j).get(h).getY() - getHeight() / 2,
								(int) figureToDraw.get(j).get(h + 1).getX() - getWidth() / 2,
								(int) figureToDraw.get(j).get(h + 1).getY() - getHeight() / 2);

						if (figureToDraw.get(j).get(h).getIsReflected())
							g2.drawLine((int) -(figureToDraw.get(j).get(h).getX() - getWidth() / 2),
									(int) figureToDraw.get(j).get(h).getY() - getHeight() / 2,
									(int) -(figureToDraw.get(j).get(h + 1).getX() - getWidth() / 2),
									(int) figureToDraw.get(j).get(h + 1).getY() - getHeight() / 2);

					}
				}

			}

		}

	}
	
	//setters and getters
	public void setColor(Color color) {
		this.color = color;
	}
	public Color getColor() {
		return color;
	}
	public void setBrushSize(int burshSize) {
		this.brushSize = burshSize;
	}
	public int getBrushSize() {
		return brushSize;
	}
	public void setShowSectors(boolean showSectors) {
		this.showSectors = showSectors;
		repaint();
	}
	public void setIsReflected(boolean isReflected) {
		this.isReflected = isReflected;
	}
	public void setNumberOfSectors(int numberOfSectors) {
		this.numberOfSectors = numberOfSectors;
		repaint();
	}
	public void setEraser(boolean erase) {
		this.erase = erase;
	
	}
	
	//pushing in the stack elements at the end of the drawigs array
	public void undo() {
		if(figureToDraw.size()!=0)
		drawingsRemoved.push(figureToDraw.remove(figureToDraw.size()-1));
		
		repaint();
	}
	//like undo but in revers order
	public void redo() {	
		if(!drawingsRemoved.empty())
		figureToDraw.add(drawingsRemoved.pop());
		
		repaint();
	}
	
	//clears the drawing array
	public void clear() {
		while(figureToDraw.size()!=0) {
			figureToDraw.remove(figureToDraw.size()-1);
		}
		drawingsRemoved.clear();
		repaint();
	}
	
	//screenshots the drawingPanel so it can be saved,both as a .png and in the galerry
	public Image getScreenShot(DrawingPanel drawingPanel) {
		
		BufferedImage image = new BufferedImage(getWidth(),getHeight(), BufferedImage.TYPE_INT_RGB);
		Image newImage = image.getScaledInstance(150, 150, Image.SCALE_SMOOTH); 
		Graphics2D g2 = image.createGraphics();
		this.print( g2 );
		g2.dispose();
		
		createSaveWindow(image);
		paint(g2);

		return newImage;
	}
	
	//creates a new window for saving the images with user input for the name
	private void createSaveWindow(BufferedImage image) {

		//the window frame and the components
		DFrame user = new DFrame("Save");
		JPanel input = new JPanel();
		JTextField userInput = new JTextField();
		JLabel message = new JLabel("<html><font color='white'>Save as:</font></html>");
		JLabel message1 = new JLabel("<html><font color='white'>Press Enter and then OK:</font></html>");
		JButton ok = new JButton("Ok");
		
		//adds the buttons and the labels to the panel
		input.setBackground(Color.DARK_GRAY);
		userInput.setColumns(30);
		input.add(message);
		input.add(userInput);
		input.add(message1);
		input.add(ok);
		user.add(input);
		
		//saves the file with the name in the text field after enter
		userInput.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				pictureName = userInput.getText();
				
				try {
					   BufferedImage bi = image;
					    File outputfile = new File(pictureName);
					    ImageIO.write(bi, "png", outputfile);
					} catch (IOException e) {
					    
					}
				
			}
		});
		
		//closes the window when ok is pressed
		ok.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				user.dispose();
				
			}
		});
		
		user.setSize(new Dimension(300, 300));
		user.setVisible(true);
		//closing only the window
		user.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	
		
	}
}

