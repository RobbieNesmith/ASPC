package com.bobberto1995.aspc;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

public class Bullet extends GameElement
{
	private static final float MAX_SPEED = 9001; // bullets don't accelerate, don't need a max speed
	private int damage;
	private float range;
	
	public Bullet(PlayingField parent, float x, float y, float size, float speed, float direction, int damage, float range)
	{
		super(parent, x,y,1,1,size,size);
		this.setSpeed(speed);
		this.setDirection(direction);
		this.setRange(range);
		this.setDamage(damage);
		this.setMaxSpeed(Bullet.MAX_SPEED);
	}
	
	public Bullet(float x, float y, float direction, Bullet other) // this needs some work.
	{
		super(other.getParent(), x, y,1,1,other.getWidth(),other.getHeight());
		this.setSpeed(other.getSpeed());
		this.setDirection(direction);
		this.setRange(other.getRange());
		this.setDamage(other.getDamage());
		this.setMaxSpeed(Bullet.MAX_SPEED);
	}
	
	public int getDamage()
	{
		return damage;
	}

	public void setDamage(int damage)
	{
		this.damage = damage;
	}
	
	public float getRange()
	{
		return range;
	}

	public void setRange(float range)
	{
		this.range = range;
	}

	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException
	{
		super.update(gc, sbg, delta);
		if(this.getX() < 0 || this.getX() > this.getParent().getWidth() || this.getY() < 0 || this.getY() > this.getParent().getHeight()) // kill if out of world
		{
			this.setAlive(false);
		}
		this.setRange(this.getRange() - (this.getSpeed() * 2 * delta) / 1000); // need to actually figure out why it goes twice the specified distance
		if(this.getRange() < 0)
		{
			this.setAlive(false);
		}
		super.update(gc, sbg, delta);
	}
}
