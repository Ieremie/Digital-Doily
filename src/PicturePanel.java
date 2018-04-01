import java.awt.BorderLayout;

import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class PicturePanel extends JPanel {


	private JLabel pictureHolder;
	private JButton remove;
	private GalleryPanel galleryPanel;
	
	
	public PicturePanel(Image image, GalleryPanel galleryPanel) {
		
		//creating a Jpanel that holds a picture
		this.setLayout(new BorderLayout());
		this.pictureHolder = new JLabel(new ImageIcon(image));
		this.remove = new JButton("Remove");
		this.galleryPanel = galleryPanel;
		
		//increasing the number of pictures in the gallery
		galleryPanel.setMaximumPictures(galleryPanel.getMaximumPictures()+1);
	}
	
	//adding the buttons adn action listeners
	public void init(PicturePanel picture) {
		
		//setting the positions of the components in the PicturePanel
		add(pictureHolder,BorderLayout.CENTER);
		add(remove, BorderLayout.PAGE_START);
		 try {
			    Image img = ImageIO.read(getClass().getResourceAsStream("remove.png"));
			    remove.setIcon(new ImageIcon(img));
			  } catch (Exception ex) {
			    System.out.println(ex);
			  }
		
		//removes the picture from the gallery when actioned
		remove.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				//decreasing the number of pictures in the gallery
				galleryPanel.setMaximumPictures(galleryPanel.getMaximumPictures()-1);
				galleryPanel.removeImage(picture);
			}
		});
	}
	
	

}
