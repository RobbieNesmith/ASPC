package com.bobberto1995.aspc;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

public class Powerup extends GameElement
{
	private static int HEAL = 0;
	private static int PLAYER_UPGRADE = 1;
	private static int WEAPON_UPGRADE = 2;
	private static int WEAPON_SWITCH = 3;
	private static int SCORE_BONUS = 4;
	
	private int timeAlive;
	private int type;
	private String parameter;
	
	
	public Powerup(PlayingField parent, float x, float y)
	{
		super(parent, x, y, 0.5f,0.5f);
		timeAlive = 10000;
		this.setType(Powerup.HEAL);
		this.setParameter("20");
	}
	
	public int getType()
	{
		return type;
	}
	public void setType(int type)
	{
		this.type = type;
	}
	public String getParameter()
	{
		return parameter;
	}
	public void setParameter(String parameter)
	{
		this.parameter = parameter;
	}
	
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException
	{
		super.update(gc, sbg, delta);
		this.timeAlive -= delta;
		if(this.timeAlive < 0)
		{
			this.setAlive(false);
		}
	}
}
