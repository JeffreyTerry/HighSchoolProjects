import java.awt.*;

import javax.swing.JFrame;

public class Store extends JFrame
{
	public Thread thread=new Thread(new DisplayAreaUpdater());
	public StoreDisplayArea displayArea=new StoreDisplayArea();
	public GridBagConstraints c=new GridBagConstraints();
	
	public boolean open=false;
	
	public Store()
	{
		initialize();
	}
	
	public void initialize()
	{
		setDefaultCloseOperation(HIDE_ON_CLOSE);
		setTitle("Store");
		setResizable(false);
		setLayout(new GridBagLayout());
		
		c.gridx=0;
		c.gridy=0;
		c.gridwidth=StoreDisplayArea.width;
		c.gridheight=StoreDisplayArea.height;
		c.fill=GridBagConstraints.BOTH;
		displayArea.setPreferredSize(new Dimension(StoreDisplayArea.width,StoreDisplayArea.height));
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