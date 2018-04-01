import java.awt.Color;
import java.awt.Point;

public class MyPoint extends Point{
	
	//variabiles for helping with the drawings
	private Color color ;
	private int brushSize = 1;
	private boolean isReflected;
	private boolean thePointIsForLine ;
	
	public MyPoint(Color color, int brushSize, boolean isReflected, boolean thePointIsForBoolean) {
		super();
		this.color = color;
		this.brushSize = brushSize;
		this.isReflected = isReflected;
		this.thePointIsForLine = thePointIsForBoolean;
	}
	
	//setters and getters for the variabiles
	public Color getColor() {
		return color;
	}
	
	public void setColor(Color color) {
		this.color = color;
	}
	
	public int getBrushSize() {
		return brushSize;
	}
	
	public void setBrushSize(int brushSize) {
		this.brushSize = brushSize;
	}
	
	public boolean getIsReflected() {
		return isReflected;
	}
	
	public void setIsReflected(boolean isReflected) {
		this.isReflected = isReflected;
	}
	
	public boolean isThePointIsForLine() {
		return thePointIsForLine;
	}
	
	
}
