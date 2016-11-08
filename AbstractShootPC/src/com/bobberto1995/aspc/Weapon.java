package com.bobberto1995.aspc;

public class Weapon
{
	private int maxShotTime;
	private int shotTimer;
	private int numFired;
	private int speed;
	
	private float spread;
	
	private PlayingField parent;
	
	public Weapon(PlayingField parent, int maxShotTime, int numFired, float spread, int speed) //also have damage and size later
	{
		this.setMaxShotTime(maxShotTime);
		this.numFired = numFired;
		this.setSpread(spread);
		this.parent = parent;
		this.speed = speed;
	}
	
	public PlayingField getParent()
	{
		return parent;
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
			float tempDir = (direction - this.getSpread() / 2) + ((float)i / numFired) * this.getSpread();
			bullets[i] = new Bullet(this.getParent(),x, y, this.speed, tempDir);
		}
		this.resetShotTimer();
		return bullets;
	}
}
