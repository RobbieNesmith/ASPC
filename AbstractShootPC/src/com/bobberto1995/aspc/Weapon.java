package com.bobberto1995.aspc;

public class Weapon
{
	private int maxShotTime;
	private int shotTimer;
	private int numFired;
	private int speed;
	
	private float spread;
	
	private PlayingField parent;
	
	private Bullet bType;
	
	public Weapon(PlayingField parent, int maxShotTime, int numFired, float spread, float size, int speed, int damage,int range) //also have damage and size later
	{
		this.setMaxShotTime(maxShotTime);
		this.setNumFired(numFired);
		this.setSpread(spread);
		this.parent = parent;
		this.speed = speed;
		bType = new Bullet(parent,0,0,size,speed,0,damage,range); // copy this when creating new bullets
	}
	
	public Weapon(Weapon other)
	{
		this.setMaxShotTime(other.getMaxShotTime());
		this.setNumFired(other.getNumFired());
		this.setSpread(other.getSpread());
		this.setParent(other.getParent());
		this.setSpeed(other.getSpeed());
	}
	
	public PlayingField getParent()
	{
		return parent;
	}
	
	public void setParent(PlayingField parent)
	{
		this.parent = parent;
	}

	public int getMaxShotTime()
	{
		return maxShotTime;
	}

	public void setMaxShotTime(int maxShotTime)
	{
		this.maxShotTime = maxShotTime;
	}
	
	public float getSpread() {
		return spread;
	}

	public void setSpread(float spread) {
		this.spread = spread;
	}
	
	public int getNumFired()
	{
		return this.numFired;
	}
	
	public void setNumFired(int numFired)
	{
		this.numFired = numFired;
	}
	
	public int getSpeed()
	{
		return this.speed;
	}
	
	public void setSpeed(int speed)
	{
		this.speed = speed;
	}
	
	public void resetShotTimer()
	{
		this.shotTimer = this.getMaxShotTime();
	}
	
	public void decrementShotTimer(int delta)
	{
		this.shotTimer -= delta;
	}
	
	public boolean canFire()
	{
		return shotTimer < 0;
	}
	
	public Bullet[] shoot(float x, float y, float direction)
	{
		Bullet[] bullets = new Bullet[numFired];
		for(int i = 0; i < numFired; i++)
		{
			float tempDir;
			if(numFired > 1)
			{
				tempDir = (direction - this.getSpread() / 2) + ((float)i / (numFired - 1)) * this.getSpread();
			}
			else
			{
				tempDir = direction;
			}
			bullets[i] = new Bullet(x, y, tempDir, this.bType);
			//bullets[i] = new Bullet(this.getParent(),x,y,0.2f, this.getSpeed(),tempDir,10);
		}
		this.resetShotTimer();
		return bullets;
	}
}
