import java.awt.FlowLayout;
import java.awt.Image;
import javax.swing.JPanel;

public class GalleryPanel extends JPanel {

	//counts the number of picture in the gallery
	private int  maximumPictures = 1;

	//creating and adding a new picturePanel if the number is under 12
	public void setImage(Image image) {
		this.setLayout(new FlowLayout());
		
		if(maximumPictures<=12) {
			PicturePanel picture = new PicturePanel(image, this);
			picture.init(picture);
			this.add(picture);
		}
		
		revalidate();
		repaint();
	}
	
	//setter and getter for the number of images in the gallery
	public void setMaximumPictures(int maximumPictures) {
		this.maximumPictures = maximumPictures;
	}
	
	public int getMaximumPictures() {
		return maximumPictures;
	}

	//removes an image from the gallery
	public void removeImage(PicturePanel picture) {
		this.remove(picture);
		this.revalidate();
		this.repaint();
	}
	
	
	
}
