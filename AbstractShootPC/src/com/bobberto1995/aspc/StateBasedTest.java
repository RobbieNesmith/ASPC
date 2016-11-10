package com.bobberto1995.aspc;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

public class StateBasedTest extends StateBasedGame
{
	public static final int SPLASHSCREEN = 0;
	public static final int MAINMENU = 1;
	public static final int GAME = 2;
	
	public static final int WIDTH = 1280;
	public static final int HEIGHT = 720;
	public static final int FPS = 60;
	public static final double VERSION = 0.1;
	
	public StateBasedTest(String name)
	{
		super(name);
	}
	
	public void initStatesList(GameContainer gc) throws SlickException
	{
		this.addState(new SplashScreen());
		this.addState(new MainMenu());
		this.addState(new Game());
	}
	
	public static void main(String[] args)
	{
		try
		{
			AppGameContainer app = new AppGameContainer(new StateBasedTest("State Based Test"));
			app.setDisplayMode(WIDTH,HEIGHT, false);
			app.setTargetFrameRate(FPS);
			app.setShowFPS(false);
			app.start();
		}
		catch(SlickException e)
		{
			e.printStackTrace();
		}
	}
}
