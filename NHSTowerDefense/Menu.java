import java.awt.*;

import javax.swing.JFrame;

public class Menu extends JFrame
{
	public Thread thread=new Thread(new DisplayAreaUpdater());
	public MenuDisplayArea displayArea=new MenuDisplayArea();
	public GridBagConstraints c=new GridBagConstraints();
	
	public boolean open=false;
	
	public Menu()
	{
		initialize();
	}
	
	public void initialize()
	{
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setResizable(false);
		setLayout(new GridBagLayout());
		
		c.gridx=0;
		c.gridy=0;
		c.gridwidth=MenuDisplayArea.width;
		c.gridheight=MenuDisplayArea.height;
		c.fill=GridBagConstraints.BOTH;
		displayArea.setPreferredSize(new Dimension(MenuDisplayArea.width,MenuDisplayArea.height));
		add(displayArea,c);
		
		pack();
		setLocationRelativeTo(null);
	}
	
	public void open()
	{
		setVisible(true);
		open=true;
		if(!thread.isAlive())
			thread.start();
	}
	
	public void close()
	{
		setVisible(false);
		open=false;
	}
	
	public class DisplayAreaUpdater implements Runnable
	{
		public void run()
		{
			while(open)
			{
				displayArea.repaint();
				try
				{
					Thread.sleep(10);
				}
				catch(Exception e){}
			}
		}
	}
}