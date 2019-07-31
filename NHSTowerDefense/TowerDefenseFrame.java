import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.ArrayList;

public class TowerDefenseFrame extends JFrame
{
	private static Toolkit tk=Toolkit.getDefaultToolkit();  //This toolkit is used to create images throughout the program

	public Clip backgroundMusicClip;  //This is used to play the background music
	
	public boolean soundOn=false;  //Is used to toggle between sound on and sound off
	
	public Thread thread=new Thread(new FrameUpdater());
	public Thread waveStarter;

	public static Menu mainMenu=new Menu();

	public static Store store=new Store();
	
	public static boolean paused=false,justUnpaused=false;
	
	public boolean justStarted=true;
	
	public static int currentWaveIndex=0;
	
	public static boolean won;
	
	public GridBagConstraints c=new GridBagConstraints();

	public static int cashMoneyFlow=2500,livesLeft=20;
	
	public static ArrayList<Wave> waves=new ArrayList<Wave>();
	
	public static JPanel infoPanel=new JPanel();

	public static JLabel livesLabel=new JLabel("Lives: "+livesLeft);
	public static JLabel cashLabel=new JLabel("Cash: "+cashMoneyFlow);
	public static JLabel waveLabel=new JLabel("Level "+(currentWaveIndex+1));
	
	public static Map map=new Map();
	
	public JPanel buttonPanel=new JPanel();

	public ImageIcon blankButtonIcon=new ImageIcon("./Images/Icon_BlankTile.png");
	public ImageIcon destroyerTowerButtonIcon=new ImageIcon("./Images/Icon_Destroyer.png");
	public ImageIcon gayRookTowerButtonIcon=new ImageIcon("./Images/Icon_GayRook.png");
	public ImageIcon nathanTowerButtonIcon=new ImageIcon("./Images/Icon_NathanTower.png");
	public ImageIcon lightningTowerButtonIcon=new ImageIcon("./Images/Icon_Lightning.png");
	public ImageIcon helpImTrappedInADoughnutTowerButtonIcon=new ImageIcon("./Images/Icon_HelpImTrappedInADoughnut.png");
	public ImageIcon customTowerButtonIcon=new ImageIcon("./Images/Icon_CustomTower.png");
	public ImageIcon cancelButtonIcon=new ImageIcon("./Images/Icon_Delete.png");
	public ImageIcon sellTowerButtonIcon=new ImageIcon("./Images/Icon_SellTower.png");
	public ImageIcon storeButtonIcon=new ImageIcon("./Images/Icon_Store.png");

	public ImageIcon startLevelButtonIcon=new ImageIcon("./Images/Button_NextLevel.png");
	public ImageIcon pauseLevelButtonIcon=new ImageIcon("./Images/Button_Pause.png");
	public ImageIcon resumeLevelButtonIcon=new ImageIcon("./Images/Button_Resume.png");

	public ImageIcon soundOnButtonIcon=new ImageIcon("./Images/Icon_MusicOn.png");  //TODO if the music on/off buttons are separate from the sound on/off buttons
	public ImageIcon soundOffButtonIcon=new ImageIcon("./Images/Icon_MusicOff.png");

	public JButton destroyerTowerButton=new JButton(destroyerTowerButtonIcon);
	public JButton gayRookTowerButton=new JButton(gayRookTowerButtonIcon);
	public JButton nathanTowerButton=new JButton(nathanTowerButtonIcon);
	public JButton lightningTowerButton=new JButton(lightningTowerButtonIcon);
	public JButton helpImTrappedInADoughnutTowerButton=new JButton(helpImTrappedInADoughnutTowerButtonIcon);
	public JButton customTowerButton=new JButton(customTowerButtonIcon);
	public JButton cancelButton=new JButton(cancelButtonIcon);
	public JButton storeButton=new JButton(storeButtonIcon);
	public JButton startLevelButton=new JButton(startLevelButtonIcon);
	public JButton pauseLevelButton=new JButton(pauseLevelButtonIcon);
	public JButton soundToggleButton=new JButton(soundOffButtonIcon);
	
	public String towerTypeSelected="None";
	public int towerButtonWidth=80,towerButtonHeight=80;

	public boolean buyingATower=false,towerJustBought,towerJustCanceled; //These variables are used to determine whether a tower is being bought or was just bought or was just canceled
	
	public static boolean rayStopIt=false;
	
	public TowerDefenseFrame()
	{
		setLayout(new GridBagLayout());
		
		initialize();
		
		map.addMouseListener(new MapMouseListener());
		map.addMouseMotionListener(new MapMouseListener());
		
		thread.start();
	}
	public void initialize()
	{
		buildWaves();
		
		addInfoPanel();
		addMap();
		addButtonPanel();
		
		pack();
		setTitle("The Adventures of Jeff: Tower Defense Edition");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setResizable(false);
		setVisible(true);
	}
	
	public void addInfoPanel()  //TODO make this more artistic
	{
		waveLabel.setFont(new Font(Font.SANS_SERIF,Font.BOLD,16));
		waveLabel.setPreferredSize(new Dimension(100,20));
		livesLabel.setFont(new Font(Font.SANS_SERIF,Font.BOLD,16));
		livesLabel.setPreferredSize(new Dimension(120,20));
		cashLabel.setFont(new Font(Font.SANS_SERIF,Font.BOLD,16));
		cashLabel.setPreferredSize(new Dimension(150,20));
		infoPanel.add(waveLabel);
		infoPanel.add(livesLabel);
		infoPanel.add(cashLabel);
		c.gridx=0;
		c.gridy=0;
		c.weighty=20;
		c.insets=new Insets(10,40,10,40);
		c.fill=GridBagConstraints.BOTH;
		add(infoPanel,c);
	}
	
	public void addMap()
	{
		c.gridx=0;
		c.gridy=1;
		c.gridwidth=2;
		c.weightx=Map.width;
		c.weighty=Map.height;
		c.insets=new Insets(0,-5,-4,-5);
		c.fill=GridBagConstraints.NONE;
		map.setPreferredSize(new Dimension(Map.width,Map.height));
		add(map,c);
	}
	
	public void addButtonPanel()
	{
		buttonPanel.setLayout(new GridBagLayout());
		GridBagConstraints bp=new GridBagConstraints();
		bp.gridy=0;
		bp.gridheight=2;
		bp.fill=GridBagConstraints.NONE;
		
		addTowerButton(destroyerTowerButton,Tower.getCost("Tower_Destroyer"));
		addTowerButton(gayRookTowerButton,Tower.getCost("Tower_GayRook"));
		addTowerButton(nathanTowerButton,Tower.getCost("Tower_NathanTower"));
		addTowerButton(lightningTowerButton,Tower.getCost("Tower_Lightning"));
		addTowerButton(helpImTrappedInADoughnutTowerButton,Tower.getCost("Tower_HelpImTrappedInADoughnut"));
		addTowerButton(customTowerButton,Tower.getCost("Tower_CustomTower"));
		addCancelButton(cancelButton);
		addStoreButton(storeButton);
		addSoundToggleButton(soundToggleButton);
		
		addStartLevelButton(startLevelButton);
		addPauseLevelButton(pauseLevelButton);
		

		bp.insets=new Insets(0,0,0,-1);
		
		bp.gridx=0;
		buttonPanel.add(customTowerButton,bp);
		bp.gridx=1;
		buttonPanel.add(destroyerTowerButton,bp);
		bp.gridx=2;
		buttonPanel.add(lightningTowerButton,bp);
		bp.gridx=3;
		buttonPanel.add(nathanTowerButton,bp);
		bp.gridx=4;
		buttonPanel.add(helpImTrappedInADoughnutTowerButton,bp);
		bp.gridx=5;
		buttonPanel.add(gayRookTowerButton,bp);
		bp.gridx=6;
		buttonPanel.add(cancelButton,bp);

		bp.gridx=7;
		bp.gridwidth=2;
		buttonPanel.add(storeButton,bp);
		
		bp.gridx=9;
		bp.gridwidth=1;
		bp.gridheight=1;
		bp.insets=new Insets(0,0,0,0);
		buttonPanel.add(startLevelButton,bp);
		bp.gridy=1;
		buttonPanel.add(pauseLevelButton,bp);

		bp.gridx=10;
		bp.gridheight=2;
		bp.gridy=0;
		buttonPanel.add(soundToggleButton,bp);
		
		c.gridx=0;
		c.gridy=2;
		c.weightx=towerButtonWidth*(bp.gridx+1);  //This weights the x by the pixel width of the panel. The last gridx position plus one is the number of towerButton sized buttons
		c.weighty=towerButtonHeight;
		c.insets=new Insets(0,0,-3,0);
		c.fill=GridBagConstraints.NONE;
		
		add(buttonPanel,c);
	}

	public void addTowerButton(JButton t,int cost)
	{
		t.setPreferredSize(new Dimension(towerButtonWidth,towerButtonHeight));
		t.setToolTipText("Cost: "+cost);
		t.addActionListener(new TowerButtonListener());
	}

	public void addCancelButton(JButton t)
	{
		t.setEnabled(false);
		t.setPreferredSize(new Dimension(towerButtonWidth,towerButtonHeight));
		t.setToolTipText("Sell Tower/Cancel Purchase");
		t.addActionListener(new CancelButtonListener());
	}

	public void addStoreButton(JButton t)
	{
		t.setPreferredSize(new Dimension(towerButtonWidth*2,towerButtonHeight));
		t.addActionListener(new StoreButtonListener());
	}
	
	public void enableTowerButton(JButton t)
	{
		t.setEnabled(true);
	}

	public void disableTowerButton(JButton t)
	{
		t.setEnabled(false);
	}
	
	public void addStartLevelButton(JButton t)
	{
		t.setPreferredSize(new Dimension(80,40));
		t.addActionListener(new StartLevelButtonListener());
	}
	
	public void addPauseLevelButton(JButton t)
	{
		t.setPreferredSize(new Dimension(towerButtonWidth,towerButtonHeight/2));
		t.addActionListener(new PauseLevelButtonListener());
	}

	public void addSoundToggleButton(JButton t)
	{
		t.setPreferredSize(new Dimension(towerButtonWidth,towerButtonHeight));
		t.setToolTipText("Sound On/Off");
		t.addActionListener(new SoundToggleButtonListener());
	}
	
	public void buildWaves()
	{
		Waves w=new Waves();
		waves=w.waves;
	}
	
	public void upgradeTowerAttribute(int x,int y)
	{
		if(map.getMouseClickingButton(x,y).equals("Option1") && map.towerSelected.option1UpgradesLeft>0)
		{
			map.towerSelected.upgrade("Option1");
		}
		else if(map.getMouseClickingButton(x,y).equals("Option2") && map.towerSelected.option2UpgradesLeft>0)
		{
			map.towerSelected.upgrade("Option2");
		}
		else if(map.getMouseClickingButton(x,y).equals("Option3") && map.towerSelected.option3UpgradesLeft>0)
		{
			map.towerSelected.upgrade("Option3");
		}
		else if(map.getMouseClickingButton(x,y).equals("Option4") && map.towerSelected.option4UpgradesLeft>0)
		{
			map.towerSelected.upgrade("Option4");
		}
	}
	
	public boolean buyTower(int cost)
	{
		if(cashMoneyFlow>=cost)
		{
			cashMoneyFlow-=cost;
			cashLabel.setText("Cash: "+cashMoneyFlow);
			return true;
		}
		return false;
	}

	public boolean upgradeTower(int cost)
	{
		return buyTower(cost);
	}
	
	public void selectTower(Tower t)
	{
		map.towerUpgraderCounter=0;
		map.towerSelected=t;
		map.towerIsSelected=true;
		cancelButton.setIcon(sellTowerButtonIcon);
		cancelButton.setEnabled(true);
	}

	public void unselectTower()
	{
		map.towerIsSelected=false;
		map.towerSelected=null;
		map.towerUpgraderCounter=0;
		cancelButton.setIcon(cancelButtonIcon);
		cancelButton.setEnabled(false);
	}

	public void createBackgroundMusic()
	{
		if(soundOn)
		{
			try
			{
				File soundFile=new File("./Sounds/RetroBG1.wav");
				AudioInputStream sound=AudioSystem.getAudioInputStream(soundFile);
				
				DataLine.Info soundInfo=new DataLine.Info(Clip.class,sound.getFormat());
				backgroundMusicClip=(Clip)AudioSystem.getLine(soundInfo);
				backgroundMusicClip.open(sound);
				
			}
			catch(Exception e)
			{
				System.out.println("Exception handled:"+e);
			}
		}
	}

	public void playBackgroundMusic()
	{
		if(soundOn)
		{
			backgroundMusicClip.start();
		}
	}

	public void pauseBackgroundMusic()
	{
		backgroundMusicClip.stop();
	}
	
	public static void lose()
	{
		won=false;
		paused=true;
		mainMenu.open();
	}

	public static void win()
	{
		won=true;
		paused=true;
		mainMenu.open();
	}
	
	public class FrameUpdater implements Runnable
	{
		public void run()
		{
			while(!paused)
			{
				//This does basically everything
				map.repaint();
				
				if(backgroundMusicClip==null)
				{
					createBackgroundMusic();  //Plays the background music
				}
				else if(!backgroundMusicClip.isRunning())
				{
					if(!justUnpaused)  //If it wasn't just unpaused, set the song to play over again from the beginning
					{
						backgroundMusicClip.setFramePosition(0);
					}
					else
						justUnpaused=false;
					playBackgroundMusic();
				}
				
				//Re-enable the startLevelButton if the current level is finished
				if(map.enemies.size()!=0)
				{
					startLevelButton.setEnabled(false);
					justStarted=false;
				}
				else if(!justStarted)
				{
					if(currentWaveIndex>=waves.size())
						win();
					startLevelButton.setEnabled(true);
				}

				try
				{
					Thread.sleep(5);
				}
				catch(Exception e){}
				
				//This waits until the map is done painting
				while(map.isPainting)
				{
					try
					{
						Thread.sleep(1);
					}
					catch(Exception e){}
				}
			}
			while(paused)
			{
				if(backgroundMusicClip!=null && backgroundMusicClip.isRunning())
				{
					pauseBackgroundMusic();
					justUnpaused=true;
				}
				try
				{
					Thread.sleep(2);
				}
				catch(Exception e){}
			}
			run();
		}
	}
	
	//This class listens for all mouse events within the map
	public class MapMouseListener implements MouseListener, MouseMotionListener
	{
		public void mouseDragged(MouseEvent event)
		{
			if(!paused && !rayStopIt)
			{
				map.cursorX=event.getX();
				map.cursorY=event.getY();
			}
		}
		public void mouseMoved(MouseEvent event)
		{
			if(!paused && !rayStopIt)
			{
				map.cursorX=event.getX();
				map.cursorY=event.getY();
			}
			if(towerJustCanceled)
			{
				buyingATower=false;
				towerJustCanceled=false;
			}
		}
		public void mouseClicked(MouseEvent event)
		{
			if(!paused && !rayStopIt && map.towerIsSelected && !map.getMouseClickingButton(event.getX(),event.getY()).equals("None"))  //This checks to see if the user is clicking on a towerUpgrader button
			{
				upgradeTowerAttribute(event.getX(),event.getY());
			}
			else if(!paused && !rayStopIt && !buyingATower && map.getTowerAtLocation(event.getX(),event.getY())!=null)
			{
				selectTower(map.getTowerAtLocation(event.getX(),event.getY()));
			}
			else if(!paused && !rayStopIt && !buyingATower && map.getTowerAtLocation(event.getX(),event.getY())==null)
			{
				unselectTower();
			}
			else if(towerJustBought)
			{
				buyingATower=false;
				towerJustBought=false;
			}
		}
		public void mouseEntered(MouseEvent event){}
		public void mouseExited(MouseEvent event){}
		public void mousePressed(MouseEvent event){}
		public void mouseReleased(MouseEvent event)
		{
			if(!paused && !rayStopIt)
			{
				if(!towerTypeSelected.equals("None") && !map.isTowerAtLocation(event.getX(),event.getY()) && !map.isPathAtLocation(event.getX(),event.getY()) && buyTower(Tower.getCost(towerTypeSelected)))
				{
					map.addTower(event.getX(), event.getY(), towerTypeSelected);
					cancelButton.doClick();
					towerJustBought=true;
				}
			}
		}
	}
	
	public class TowerButtonListener implements ActionListener
	{
		public void actionPerformed(ActionEvent event)
		{
			if(!paused && !rayStopIt)
			{
				if(event.getSource()==destroyerTowerButton)
				{
					towerTypeSelected="Tower_Destroyer";
					map.setCursor("Tower_Destroyer");
				}
				else if(event.getSource()==gayRookTowerButton)
				{
					towerTypeSelected="Tower_GayRook";
					map.setCursor("Tower_GayRook");
				}
				else if(event.getSource()==nathanTowerButton)
				{
					towerTypeSelected="Tower_NathanTower";
					map.setCursor("Tower_NathanTower");
				}
				else if(event.getSource()==lightningTowerButton)
				{
					towerTypeSelected="Tower_Lightning";
					map.setCursor("Tower_Lightning");
				}
				else if(event.getSource()==helpImTrappedInADoughnutTowerButton)
				{
					towerTypeSelected="Tower_HelpImTrappedInADoughnut";
					map.setCursor("Tower_HelpImTrappedInADoughnut");
				}
				else if(event.getSource()==customTowerButton)
				{
					towerTypeSelected="Tower_CustomTower";
					map.setCursor("Tower_CustomTower");
				}
				buyingATower=true;
				unselectTower();
				cancelButton.setEnabled(true);
			}
		}
	}

	public class CancelButtonListener implements ActionListener
	{
		public void actionPerformed(ActionEvent event)
		{
			if(!paused && !rayStopIt)
			{
				if(map.towerIsSelected)
				{
					cashMoneyFlow+=map.towerSelected.cost*.8;  //Refunds 80 percent of the tower's cost when it is deleted
					cashLabel.setText("Cash: "+cashMoneyFlow);
					map.towers.remove(map.towerSelected);
					unselectTower();
				}
				else
				{
					towerTypeSelected="None";
					map.setCursor("None");
					cancelButton.setEnabled(false);
					towerJustCanceled=true;
				}
			}
		}
	}
	
	public class StoreButtonListener implements ActionListener
	{
		public void actionPerformed(ActionEvent event)
		{
			if(!paused && !rayStopIt)
				store.open();
		}
	}
	
	public class StartLevelButtonListener implements ActionListener  //Listens for the startLevelButton
	{
		public void actionPerformed(ActionEvent event)
		{
			if(!paused && currentWaveIndex<waves.size())  //If there are waves left and it is not paused
			{
				justStarted=true;  //Is used by the frame updater class to make sure the first iteration is not seen as a clear wave
				
				waveStarter=new Thread(waves.get(currentWaveIndex));  //Starts the waveStarter thread
				waveStarter.start();

				waveLabel.setText("Level "+(currentWaveIndex+1));  //Updates the waveLabel
				
				startLevelButton.setEnabled(false);  //Disables the startLevelButton
				
				currentWaveIndex++;  //Adds one the the currentWaveIndex
			}
			else if(!paused)
			{
				win();  //This calls the win method
			}
		}
	}
	public class PauseLevelButtonListener implements ActionListener  //Listens for the pauseLevelButton
	{
		public void actionPerformed(ActionEvent event)
		{
			if(pauseLevelButton.getIcon()==pauseLevelButtonIcon)  //Don't implement rayStopIt here, it messes it up
			{
				paused=true;
				pauseLevelButton.setIcon(resumeLevelButtonIcon);
			}
			else
			{
				paused=false;
				pauseLevelButton.setIcon(pauseLevelButtonIcon);
			}
		}
	}
	public class SoundToggleButtonListener implements ActionListener  //Listens for the soundToggleButton
	{
		public void actionPerformed(ActionEvent event)
		{
			if(soundToggleButton.getIcon()==soundOnButtonIcon)  //Don't implement rayStopIt here, it messes it up
			{
				pauseBackgroundMusic();
				soundOn=false;
				soundToggleButton.setIcon(soundOffButtonIcon);
			}
			else
			{
				soundOn=true;
				soundToggleButton.setIcon(soundOnButtonIcon);
			}
		}
	}
}