package com.bobberto1995.aspc;

public class Weapon
{
	private int maxShotTime;
	private int shotTimer;
	private int numFired;
	
	private float speed, randSpeed;
	private float spread, randSpread;
	
	private PlayingField parent;
	
	private Bullet bType;
	
	/*
	* Creates a weapon with specific parameters
	* @param parent the PlayingField to add bullets
	* @param maxShotTime fire rate in milliseconds
	* @param numFired number of projectiles fired
	* @param spread the spread of projectiles in radians (for multi-shot)
	* @param randSpread random spread modifier applied to individual bullets
	* @param speed speed of bullets in units/second
	* @param randSpeed random speed modifier applied to individual bullets
	* @param damage damage of bullets
	* @param range time bullet is alive in milliseconds
	* @return Weapon object
	*/
	public Weapon(PlayingField parent, int maxShotTime, int numFired, float spread, float randSpread, float speed, float randSpeed, int damage,float range)
	{
		this.setMaxShotTime(maxShotTime);
		this.setNumFired(numFired);
		this.setSpread(spread);
		this.setRandSpread(randSpread);
		this.setParent(parent);
		this.setSpeed(speed);
		this.setRandSpeed(randSpeed);
		this.bType = new Bullet(parent,0,0,speed,0,damage,range); // copy this when creating new bullets
	}
	/*
	 * Copies another weapon (useful for getting a weapon from a list)
	 * @param parent the PlayingField to add bullets
	 * @param other the Weapon to copy
	 * @return Weapon object
	 */
	public Weapon(PlayingField parent, Weapon other)
	{
		this.setMaxShotTime(other.getMaxShotTime());
		this.setNumFired(other.getNumFired());
		this.setSpread(other.getSpread());
		this.setRandSpread(other.getRandSpread());
		this.setParent(parent);
		this.setSpeed(other.getSpeed());
		this.setRandSpeed(other.getRandSpeed());
		this.setBtype(new Bullet(0,0,0,other.getBtype()));
		this.getBtype().setParent(parent);
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
	
	public float getRandSpread()
	{
		return this.randSpread;
	}
	
	public void setRandSpread(float randSpread)
	{
		this.randSpread = randSpread;
	}
	
	public int getNumFired()
	{
		return this.numFired;
	}
	
	public void setNumFired(int numFired)
	{
		this.numFired = numFired;
	}
	
	public float getSpeed()
	{
		return this.speed;
	}
	
	public void setSpeed(float speed)
	{
		this.speed = speed;
	}
	
	public float getRandSpeed()
	{
		return this.randSpeed;
	}
	
	public void setRandSpeed(float randSpeed)
	{
		this.randSpeed = randSpeed;
	}
	
	public float getSize()
	{
		return this.bType.getWidth();
	}
	
	public int getDamage()
	{
		return this.bType.getDamage();
	}
	
	public void setDamage(int damage)
	{
		this.bType.setDamage(damage);
	}
	
	public float getRange()
	{
		return bType.getRange();
	}
	
	public void setRange(float range)
	{
		this.bType.setRange(range);
	}
	
	public void setSize(float size)
	{
		this.bType.setWidth(size);
		this.bType.setHeight(size);
	}
	
	public Bullet getBtype()
	{
		return this.bType;
	}
	
	public void setBtype(Bullet bType)
	{
		this.bType = bType;
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
			tempDir += (Math.random() * this.getRandSpread()) - this.getRandSpread() / 2;
			bullets[i] = new Bullet(x, y, tempDir, this.bType);
			float speedMod = (float) ((Math.random() * this.getRandSpeed()) - this.getRandSpeed() / 2);
			bullets[i].setSpeed(bullets[i].getSpeed() + speedMod);
		}
		this.resetShotTimer();
		return bullets;
	}
}
