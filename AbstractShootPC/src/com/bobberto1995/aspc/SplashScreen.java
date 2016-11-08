package com.bobberto1995.aspc;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.command.BasicCommand;
import org.newdawn.slick.command.Command;
import org.newdawn.slick.command.InputProvider;
import org.newdawn.slick.command.InputProviderListener;
import org.newdawn.slick.command.MouseButtonControl;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;


public class SplashScreen extends BasicGameState implements InputProviderListener
{
	// ID we return to class 'StateBasedTest'
		public static final int ID = 0;
		private float alpha;
		private boolean fadeout = false;
		Image splash;
		private Command advance = new BasicCommand("advance");
		private InputProvider provider;
		
		// init-method for initializing all resources
		@Override
		public void init(GameContainer gc, StateBasedGame sbg) throws SlickException
		{
			provider = new InputProvider(gc.getInput());
			provider.addListener(this);
			provider.bindCommand(new MouseButtonControl(Input.MOUSE_LEFT_BUTTON), advance);
			splash = new Image("gfx/bobberto1995.png");
			alpha = 0;
		}

		// render-method for all the things happening on-screen
		@Override
		public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException
		{
			
			splash.setAlpha(alpha);
			g.drawImage(splash,0,0);
		}

		// update-method with all the magic happening in it
		@Override
		public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException
		{
			if(!fadeout && alpha < 1)
			{
				alpha += 0.02;
			}
			if(fadeout)
			{
				if(alpha > 0)
				{
					alpha -= 0.02;
				}
				else
				{
					sbg.enterState(2);
				}
			}
		}

		// Returning 'ID' from class 'SplashScreen'
		@Override
		public int getID() {
			return SplashScreen.ID;
		}

		@Override
		public void controlPressed(Command com)
		{
			if(com.equals(advance))
			{
				fadeout = true;
			}
		}

		@Override
		public void controlReleased(Command com)
		{
			
		}
}