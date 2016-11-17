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
	
	public Bullet(Bullet other) // this needs some work.
	{
		super(other.getParent(), other.getX(),other.getY(),1,1,0.2f,0.2f);
		this.setSpeed(other.getSpeed());
		this.setDirection(other.getDirection());
		this.setMaxSpeed(Bullet.MAX_SPEED);
	}
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException
	{
		super.update(gc, sbg, delta);
		if(this.getX() < 0 || this.getX() > this.getParent().getWidth() || this.getY() < 0 || this.getY() > this.getParent().getHeight())
		{
			this.setAlive(false);
		}
		super.update(gc, sbg, delta);
	}
}
