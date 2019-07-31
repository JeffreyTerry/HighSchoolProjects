import java.awt.Color;

import javax.swing.JButton;

public class ColorButton extends JButton
{
	private final Color color;
	public ColorButton(Color c)
	{
		color=c;
		setBackground(color);
		setText("        ");
	}
	public ColorButton(Color c,boolean showRGBOnToolTipText)
	{
		color=c;
		setBackground(color);
		setText("        ");
		if(showRGBOnToolTipText)
			setToolTipText("RGB Value: ("+color.getRed()+","+color.getGreen()+","+color.getBlue()+")");
	}
	public Color getColor()
	{
		return color;
	}
}