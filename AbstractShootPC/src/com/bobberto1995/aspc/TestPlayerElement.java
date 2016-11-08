package com.bobberto1995.aspc;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;


public class TestPlayerElement extends GameElement
{
	private static int ACCEL = 5000;
	private static int FRIC = 1000;
	private static int MAX_SPEED = 400;
	private static float BOUNCE = 1f;

	private int maxInvincibleTime;
	private int invincibleTimer;
	
	private int hp;
	private int score;
	
	private boolean leftHeld, rightHeld, upHeld, downHeld, mouseHeld;
	
	private Weapon playerWeapon;
	
	public TestPlayerElement(PlayingField parent, float x, float y)
	{
		super(parent, x, y, 50, 50);
		this.setMaxSpeed(TestPlayerElement.MAX_SPEED);
		this.setMaxInvincibleTime(1000);
		this.resetInvincibleTimer();
		this.setHp(100);
		this.playerWeapon = new Weapon(this.getParent(),250,1,0,1000);
		leftHeld = false;
		rightHeld = false;
		upHeld = false;
		downHeld = false;
	}
	public Weapon getPlayerWeapon()
	{
		return playerWeapon;
	}
	public void setPlayerWeapon(Weapon playerWeapon)
	{
		this.playerWeapon = playerWeapon;
	}
	public int getHp() {
		return hp;
	}
	public void setHp(int hp)
	{
		this.hp = hp;
	}
	public void setHp(int hp, boolean relative)
	{
		if(relative)
		{
			this.hp += hp;
		}
		else
		{
			this.hp = hp;
		}
	}
	public int getScore()
	{
		return score;
	}
	public void setScore(int score)
	{
		this.score = score;
	}
	public void setScore(int score, boolean relative)
	{
		if(relative)
		{
			this.score += score;
		}
		else
		{
			this.score = score;
		}
	}
	public int getMaxInvincibleTime()
	{
		return maxInvincibleTime;
	}
	public void setMaxInvincibleTime(int maxInvincibleTime)
	{
		this.maxInvincibleTime = maxInvincibleTime;
	}
	public void decrementInvincibleTimer(int delta)
	{
		this.invincibleTimer -= delta;
	}
	public void resetInvincibleTimer()
	{
		this.invincibleTimer = this.maxInvincibleTime;
	}
	
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException
	{
		// collision
		this.decrementInvincibleTimer(delta);
		
		for(TestEnemyElement tee : this.getParent().getEnemies())
		{
			if(this.intersects(tee) && this.invincibleTimer < 0)
			{
				this.resetInvincibleTimer();
				this.setHp(-10, true);
			}
		}
		
		// movement
		if(this.getSpeed() > 1) //set threshold so it doesn't get confused
		{
			this.setAccelX(this.getDx() / this.getSpeed() * -FRIC); // set friction in the opposite direction of motion
			this.setAccelY(this.getDy() / this.getSpeed() * -FRIC);
		}
		else
		{
			this.setAccelX(0); // to prevent drift when player shouldn't be moving
			this.setAccelY(0);
			this.setDx(0);
			this.setDy(0);
		}
		if(leftHeld)
		{
			this.setAccelX(-ACCEL);
		}
		if(rightHeld)
		{
			this.setAccelX(ACCEL);
		}
		if(upHeld)
		{
			this.setAccelY(-ACCEL);
		}
		if(downHeld)
		{
			this.setAccelY(ACCEL);
		}
		super.update(gc, sbg, delta);
		
		// do bounce calculations after changing position
		
		if(this.getX() <= this.getWidth() / 2)
		{
			this.setX(this.getWidth() - this.getX());
			this.setDx(BOUNCE * Math.abs(this.getDx()));
		}
		if(this.getX() >= 1280 - this.getWidth() / 2)
		{
			this.setX(2 * this.getParent().getWidth() - this.getWidth() - this.getX());
			this.setDx(-BOUNCE * Math.abs(this.getDx()));
		}
		if(this.getY() <= this.getHeight() / 2)
		{
			this.setY(this.getHeight() - this.getY());
			this.setDy(BOUNCE * Math.abs(this.getDy()));
		}
		if(this.getY() >= 720 - this.getHeight() / 2)
		{
			this.setY(2 * this.getParent().getHeight() - this.getHeight() - this.getY());
			this.setDy(-BOUNCE * Math.abs(this.getDy()));
		}
		
		// shot timer related calculations
		this.shoot();
		playerWeapon.decrementShotTimer(delta);
	}
	
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException
	{
		//g.drawString("X = " + this.getX() + "Y = " + this.getY() + "\ndX = " + this.getDx() + " dY = " + this.getDy(), 200,0);
		if(this.invincibleTimer > 0)
		{
			g.setColor(Color.green);
		}
		else
		{
			g.setColor(Color.white);
		}
		super.render(gc,sbg,g);
//		g.setColor(Color.red);
//		g.drawLine(this.getX(), this.getY(), this.getX() + this.getDx(), this.getY() + this.getDy());
//		g.setColor(Color.blue);
//		g.drawLine(this.getX(), this.getY(), this.getX() + this.getAccelX(), this.getY() + this.getAccelY());
		g.setColor(Color.white);
	}
	public void leftPressed()
	{
		leftHeld = true;
	}
	public void leftReleased()
	{
		leftHeld = false;
	}
	public void rightPressed()
	{
		rightHeld = true;
	}
	public void rightReleased()
	{
		rightHeld = false;
	}
	public void upPressed()
	{
		upHeld = true;
	}
	public void upReleased()
	{
		upHeld = false;
	}
	public void downPressed()
	{
		downHeld = true;
	}
	public void downReleased()
	{
		downHeld = false;
	}
	public void mousePressed()
	{
		mouseHeld = true;
	}
	public void mouseReleased()
	{
		mouseHeld = false;
	}
	public void shoot()
	{
		if(playerWeapon.canFire() && mouseHeld)
		{
			float direction = (float)Math.atan2(this.getParent().getMouseY() - this.getY(), this.getParent().getMouseX() - this.getX());
			Bullet[] bullets = playerWeapon.shoot(this.getX(), this.getY(), direction);
	//		b.setDx(b.getDx() + this.getDx());
	//		b.setDy(b.getDy() + this.getDy());
			for(Bullet b : bullets)
			{
				this.getParent().addBullet(b);
			}
			playerWeapon.resetShotTimer();
		}
	}
}
