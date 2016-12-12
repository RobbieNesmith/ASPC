package com.bobberto1995.aspc;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

public class Bullet extends GameElement
{
	private static final float MAX_SPEED = 9001; // bullets don't need a max speed
	private int damage;
	private float range;
	
	/*
	 * Creates a bullet with specific parameters
	 * @param parent the PlayingField to contain the bullet
	 * @param x the x coordinate in units
	 * @param y the y coordinate in units
	 * @param speed the speed of the bullet in units/second
	 * @param direction the direction of the bullet in radians
	 * @param damage the damage of the bullet
	 * @param range how long the bullet is alive in milliseconds
	 * @return Bullet object
	 */
	public Bullet(PlayingField parent, float x, float y, float speed, float direction, int damage, float range)
	{
		super(parent, x,y,1,1,0.1f + damage / 100f,0.1f + damage / 100f);
		this.setSpeed(speed);
		this.setDirection(direction);
		this.setRange(range);
		this.setDamage(damage);
		this.setMaxSpeed(Bullet.MAX_SPEED);
	}
	
	/*
	 * Creates a bullet copy of another (useful for Weapon class)
	 * @param x x coordinate of the bullet in units
	 * @param y y coordinate of the bullet in units
	 * @param direction the direction of the bullet in radians
	 * @param other the bullet to copy data from
	 * @return Bullet object
	 */
	public Bullet(float x, float y, float direction, Bullet other) // this needs some work.
	{
		super(other.getParent(), x, y,1,1,0.1f + other.getDamage() / 100f,0.1f + other.getDamage() / 100f);
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
		if(this.getX() < 0 || this.getX() > this.getParent().getWidth() || this.getY() < 0 || this.getY() > this.getParent().getHeight()) // kill if out of world
		{
			this.setAlive(false);
		}
		this.setRange(this.getRange() - delta);
		if(this.getRange() < 0)
		{
			this.setAlive(false);
		}
		super.update(gc, sbg, delta);
	}
}
