package com.bobberto1995.aspc;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.command.BasicCommand;
import org.newdawn.slick.command.Command;
import org.newdawn.slick.command.InputProvider;
import org.newdawn.slick.command.InputProviderListener;
import org.newdawn.slick.command.KeyControl;
import org.newdawn.slick.command.MouseButtonControl;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;


public class Game extends BasicGameState implements InputProviderListener
{
	public static final int ID = 2;
	public static int mouseX;
	public static int mouseY;
	private PlayingField tpf;
	
	
	public static Command moveLeft = new BasicCommand("moveLeft");
	public static Command moveRight = new BasicCommand("moveRight");
	public static Command moveUp = new BasicCommand("moveUp");
	public static Command moveDown = new BasicCommand("moveDown");
	public static Command fireBullet = new BasicCommand("fireBullet");
	private InputProvider provider;
	
	@Override
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException
	{
		try
		{
			WeaponListGenerator.generateListFromFile("gamedata/weapons.ini");
			PowerupListGenerator.generateListFromFile("gamedata/powerups.ini");
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		tpf = new PlayingField(gc,sbg);
		provider = new InputProvider(gc.getInput());
		provider.addListener(this);
		provider.bindCommand(new KeyControl(Input.KEY_A),moveLeft);
		provider.bindCommand(new KeyControl(Input.KEY_D),moveRight);
		provider.bindCommand(new KeyControl(Input.KEY_W),moveUp);
		provider.bindCommand(new KeyControl(Input.KEY_S),moveDown);
		provider.bindCommand(new MouseButtonControl(Input.MOUSE_LEFT_BUTTON),fireBullet);
		mouseX = 0;
		mouseY = 0;
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException
	{
		tpf.render(gc, sbg, g);
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException
	{
		tpf.update(gc, sbg, delta);
	}

	@Override
	public int getID()
	{
		return Game.ID;
	}

	@Override
	public void controlPressed(Command com)
	{
		tpf.controlPressed(com);
	}

	@Override
	public void controlReleased(Command com)
	{
		tpf.controlReleased(com);
	}
	public void mouseMoved(int oldx, int oldy, int newx, int newy)
	{
		tpf.setMouseX(newx);
		tpf.setMouseY(newy);
	}
	public void mouseDragged(int oldx, int oldy, int newx, int newy)
	{
		tpf.setMouseX(newx);
		tpf.setMouseY(newy);
	}
}
