package com.bobberto1995.aspc;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

public class Bullet extends GameElement
{
	private static final float MAX_SPEED = 9001; // bullets don't accelerate, don't need a max speed
	
	public Bullet(PlayingField parent, float x, float y, float speed, float direction)
	{
		super(parent, x,y,1,1,0.2f,0.2f);
		this.setSpeed(speed);
		this.setDirection(direction);
		this.setMaxSpeed(Bullet.MAX_SPEED);
	}
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException
	{
		super.update(gc, sbg, delta);
		if(this.getX() < 0 || this.getX() > 1280 || this.getY() < 0 || this.getY() > 720)
		{
			this.setAlive(false);
		}
		super.update(gc, sbg, delta);
	}
}
