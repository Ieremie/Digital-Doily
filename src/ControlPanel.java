import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JToggleButton;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class ControlPanel extends JPanel {

	private DrawingPanel drawingPanel;
	private GalleryPanel galery;
	//variabiles that are used to control toggle buttons
	private boolean showSectorsIsPressed = false;
	private boolean reflectedIsPress = false;
	private boolean eraseIsPressed = false;
	
	//gets the panels as reference
	public ControlPanel(DrawingPanel drawingPanel, GalleryPanel galery) {
		this.drawingPanel = drawingPanel;
		this.galery = galery;
	}
	
	public void init() {
		
		//creating buttons, jlabels and adding proper icons
		JButton clearDisplay = new JButton("Clear Display");
		 try {
			    Image img = ImageIO.read(getClass().getResourceAsStream("clear.png"));
			    clearDisplay.setIcon(new ImageIcon(img));
			  } catch (Exception ex) {
			    System.out.println(ex);
			  }
		JButton color = new JButton("Color");
		 try {
			    Image img = ImageIO.read(getClass().getResourceAsStream("color.png"));
			    color.setIcon(new ImageIcon(img));
			  } catch (Exception ex) {
			    System.out.println(ex);
			  }
		
		JButton undo = new JButton("Undo");
		 try {
			    Image img = ImageIO.read(getClass().getResourceAsStream("icon.png"));
			    undo.setIcon(new ImageIcon(img));
			  } catch (Exception ex) {
			    System.out.println(ex);
			  }
		JButton redo = new JButton("Redo");
		 try {
			    Image img = ImageIO.read(getClass().getResourceAsStream("redo.png"));
			    redo.setIcon(new ImageIcon(img));
			  } catch (Exception ex) {
			    System.out.println(ex);
			  }
		JToggleButton eraser = new JToggleButton("Eraser");
		 try {
			    Image img = ImageIO.read(getClass().getResourceAsStream("eraser.png"));
			    eraser.setIcon(new ImageIcon(img));
			  } catch (Exception ex) {
			    System.out.println(ex);
			  }
		JButton save = new JButton("Save");
		 try {
			    Image img = ImageIO.read(getClass().getResourceAsStream("save.png"));
			    save.setIcon(new ImageIcon(img));
			  } catch (Exception ex) {
			    System.out.println(ex);
			  }
		JToggleButton showSectors = new JToggleButton("Show Sectors");
		try {
		    Image img = ImageIO.read(getClass().getResourceAsStream("show.png"));
		    showSectors.setIcon(new ImageIcon(img));
		  } catch (Exception ex) {
		    System.out.println(ex);
		  }
		JToggleButton reflected = new JToggleButton("Reflect");
		 try {
			    Image img = ImageIO.read(getClass().getResourceAsStream("reflect.png"));
			    reflected.setIcon(new ImageIcon(img));
			  } catch (Exception ex) {
			    System.out.println(ex);
			  }
		
		SpinnerNumberModel spinner = new SpinnerNumberModel(1,1,1000,1);
		JSpinner jSpinner = new JSpinner(spinner);
		JLabel jLabelForPenSize = new JLabel("<html><font color='white'>Ustensil Size</font></html>");
		 try {
			    Image img = ImageIO.read(getClass().getResourceAsStream("pen.png"));
			    jLabelForPenSize.setIcon(new ImageIcon(img));
			  } catch (Exception ex) {
			    System.out.println(ex);
			  }
		SpinnerNumberModel numberOfSectors = new SpinnerNumberModel(6,1,1000,1);
		JSpinner jSpinnerForSectors = new JSpinner(numberOfSectors);
		JLabel jLabelForNumberOfSectors = new JLabel("<html><font color='white'> Number Of Sectors</font></html>");
		JLabel space0 = new JLabel("          ");
		JLabel space1 = new JLabel("          ");
		JLabel space2 = new JLabel("                      ");
		JLabel space3 = new JLabel("                      ");
		JLabel space4 = new JLabel("                      ");
		
		//adding the elements to the control panel
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		add(undo);
		if(screenSize.getWidth()>1400)
			add(space0);
		add(clearDisplay);
		if(screenSize.getWidth()>1400)
			add(space1);
		add(redo);
		if(screenSize.getWidth()>1400)
			add(space2);
		add(jLabelForNumberOfSectors);
		add(jSpinnerForSectors);
		add(showSectors);
		add(reflected);
		if(screenSize.getWidth()>1400)
			add(space4);
		add(jLabelForPenSize);
		add(jSpinner);
		add(color);
		add(eraser);
		if(screenSize.getWidth()>1400)
			add(space3);
		add(save);		
		
		//setting the brush size
		drawingPanel.setBrushSize((int)jSpinner.getValue());
		
		//adding action listeners to the buttons and calling proper methods
		undo.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent evt) {
				drawingPanel.undo();
			}
		});
		
		redo.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				drawingPanel.redo();
				
			}
		});
		
		color.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				//it uses the color chooser that is already implemented
				Color newColor = JColorChooser.showDialog(drawingPanel, "Choose your pen Color", drawingPanel.getColor());
				if(newColor!= null)
					drawingPanel.setColor(newColor);
				
			}
		});
		
		spinner.addChangeListener(new ChangeListener() {
			
			@Override
			public void stateChanged(ChangeEvent arg0) {
				drawingPanel.setBrushSize((int)jSpinner.getValue());
			}
		});
		
		showSectors.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				
				showSectorsIsPressed = !showSectorsIsPressed;
				if(showSectorsIsPressed)
					drawingPanel.setShowSectors(true);
				else
					drawingPanel.setShowSectors(false);
				
				
			}
		});
		
		reflected.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				reflectedIsPress = !reflectedIsPress;
				if(reflectedIsPress)
					drawingPanel.setIsReflected(true);
				else
					drawingPanel.setIsReflected(false);
				
			}
		});
		
		jSpinnerForSectors.addChangeListener(new ChangeListener() {
			
			@Override
			public void stateChanged(ChangeEvent arg0) {
				drawingPanel.setNumberOfSectors((int)jSpinnerForSectors.getValue());
				
			}
		});
		
		clearDisplay.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				drawingPanel.clear();
				
			}
		});
		
		eraser.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				eraseIsPressed = !eraseIsPressed;
				if(eraseIsPressed)
					drawingPanel.setEraser(true);
				else
					drawingPanel.setEraser(false);
				
			}
		});	
		
		save.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
			
				galery.setImage(drawingPanel.getScreenShot(drawingPanel));
				galery.revalidate();
				galery.repaint();
			}
		});
		
		//sets the layout
		this.setLayout(new GridLayout(1, 1));
		
	}
	
	
}
