import java.awt.*;
import javax.swing.*;
public class MainProgram
{
	public static void main(String[] args)
	{
		JFrame frame = new JFrame();
		final int FRAME_WIDTH=1024;
		final int FRAME_HEIGHT=720;
		//int M1=Integer.parseInt(JOptionPane.showInputDialog("What is the mass of the first cart?"));
		//int V1I=Integer.parseInt(JOptionPane.showInputDialog("What is the initial velocity of the first cart?"));
		//int M2=Integer.parseInt(JOptionPane.showInputDialog("What is the mass of the second cart?"));
		//int V2I=Integer.parseInt(JOptionPane.showInputDialog("What is the initial velocity of the second cart?"));
		//GUI frame=new GUI();
		frame.setSize(FRAME_WIDTH,FRAME_HEIGHT);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		UFO Aliens=new UFO(10,280,40);
		frame.setVisible(true);
		Aliens.paintWindow(Color.CYAN);
		Aliens.paintBody(Color.BLUE);
		//TextBox NameBox=new TextBox(frame,285,200,"Jeffrey","Tom","Steve","Jim","Doc","Big Dan","Brandon","T-Murder","Slemmons","Forrest","Jeffrey","Tom","Steve","Jim","Doc","Big Dan","Brandon","T-Murder","Slemmons","Forrest");
		frame.add(Aliens);
		frame.setVisible(true);
		//frame.add(NameBox);
		frame.setVisible(true);
		Car BoxCar=new Car(340,600,20);
		BoxCar.paintWindow(Color.BLUE);
		BoxCar.paintBody(Color.GREEN);
		BoxCar.paintWheels(Color.BLACK);
		frame.add(BoxCar);
		frame.setVisible(true);
		for(int i=0;i<=800000000;i++){}
		Aliens.moveUFO(1,150);
		for(int i=0;i<=800000000;i++){}
		Aliens.shoot();
		for(int i=0;i<=1200000000;i++){}
		Aliens.stopShooting();
		for(int i=0;i<=800000000;i++){}
		Aliens.moveUFO(6,596);
		for(int i=0;i<=120000000;i++){}
		Aliens.moveUFO(4,-746);
		for(int i=0;i<=200000000;i++){}
		BoxCar.moveCar(1,100);
		for(int i=0;i<=200000000;i++){}
		GUI mouse=new GUI();
		mouse.setSize(1024,200);
		mouse.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mouse.setVisible(true);
		Aliens.attack(BoxCar);
	}
}
