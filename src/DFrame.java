
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.UIManager;

public class DFrame extends JFrame{
	
	public DFrame(String s) {
		super(s);
	}
	
 public void init() {
	
	//the container of the panels
	Container doilyFrame = this.getContentPane();
	doilyFrame.setLayout(new BorderLayout());
	
	//creating the main panels
	DrawingPanel drawingDisplay = new DrawingPanel();
	GalleryPanel gallery = new GalleryPanel();
	ControlPanel controlPanel = new ControlPanel(drawingDisplay,gallery);
	
	//colloring the backgrounds
	gallery.setBackground(Color.DARK_GRAY);
	controlPanel.setBackground(Color.DARK_GRAY);
	controlPanel.init();
	
	//positioning the panels in the main frame
	doilyFrame.add(drawingDisplay,BorderLayout.CENTER);
	doilyFrame.add(controlPanel, BorderLayout.PAGE_START);
	doilyFrame.add(gallery, BorderLayout.PAGE_END);

	Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	//eliminates the dumb, old swing color
	try { 
	    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
	} catch (Exception e) {
	    e.printStackTrace();
	}
	
	//sets the sizes and closing and resizing operations
	this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	
	this.setSize((int)screenSize.getWidth(), (int)screenSize.getHeight());
	this.setResizable(false);
	this.setVisible(true); 
	
	}

 
}
