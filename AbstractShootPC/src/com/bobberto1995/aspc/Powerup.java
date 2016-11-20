package com.bobberto1995.aspc;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Transform;
import org.newdawn.slick.state.StateBasedGame;

public class Powerup extends GameElement
{
	public static final int HEAL = 0;
	public static final int PLAYER_UPGRADE = 1;
	public static final int WEAPON_UPGRADE = 2;
	public static final int WEAPON_SWITCH = 3;
	public static final int SCORE_BONUS = 4;
	
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
	
	public Powerup(PlayingField parent,float x, float y,int type, String parm)
	{
		super(parent,x,y,0.5f,0.5f);
		this.setType(type);
		this.setParameter(parm);
	}
	
	public Powerup(PlayingField parent, float x, float y, Powerup other)
	{
		super(parent,x,y,0.5f,0.5f);
		this.setType(other.getType());
		this.setParameter(other.getParameter());
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
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException
	{
		switch(this.getType())
		{
		case Powerup.HEAL:
			g.setColor(Color.red);
			break;
		case Powerup.PLAYER_UPGRADE:
			g.setColor(Color.yellow);
			break;
		case Powerup.WEAPON_UPGRADE:
			g.setColor(Color.green);
			break;
		case Powerup.WEAPON_SWITCH:
			g.setColor(Color.blue);
			break;
		case Powerup.SCORE_BONUS:
			g.setColor(Color.magenta);
		}
		Rectangle cbb = this.getParent().getCameraBounds();
		float cs = this.getParent().getCamScale();
		Shape adjusted = this.getBoundingBox().transform( Transform.createScaleTransform(cs,cs));
		adjusted = adjusted.transform( Transform.createTranslateTransform(-cbb.getX() * cs, -cbb.getY() * cs) );
		g.fill(adjusted);
		g.setColor(Color.white);
		g.draw(adjusted);
	}
}
