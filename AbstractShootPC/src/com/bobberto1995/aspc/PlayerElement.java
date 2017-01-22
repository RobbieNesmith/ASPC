package com.bobberto1995.aspc;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;


public class PlayerElement extends GameElement
{
	private static float ACCEL = 40;
	private static float FRIC = 10;
	private static float MAX_SPEED = 5f;
	private static float BOUNCE = 1f;

	private int maxInvincibleTime;
	private int invincibleTimer;
	
	private int hp;
	private int maxHp;
	private int score;
	
	private boolean leftHeld, rightHeld, upHeld, downHeld, mouseHeld;
	
	private Weapon playerWeapon;
	
	public PlayerElement(PlayingField parent, float x, float y)
	{
		super(parent, x, y, 0.75f, 0.75f);
		this.setMaxSpeed(PlayerElement.MAX_SPEED);
		this.setMaxInvincibleTime(1000);
		this.resetInvincibleTimer();
		this.setMaxHp(100);
		this.setHp(this.getMaxHp());
		//this.playerWeapon = new Weapon(this.getParent(),250,1,0,0.2f,8,10,2.5f);
		try
		{
			this.playerWeapon = new Weapon(this.getParent(),WeaponListGenerator.weaponList.get("Default"));
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			this.playerWeapon = new Weapon(this.getParent(),250,1,0,0,10,0,10,1000);
		}
		leftHeld = false;
		rightHeld = false;
		upHeld = false;
		downHeld = false;
	}
	public Weapon getWeapon()
	{
		return playerWeapon;
	}
	public void setWeapon(Weapon playerWeapon)
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
	public void setHpRelative(int hp)
	{
		this.hp += hp;		
	}
	
	public int getMaxHp() {
		return maxHp;
	}
	public void setMaxHp(int maxHp) {
		this.maxHp = maxHp;
	}
	public int getScore()
	{
		return score;
	}
	public void setScore(int score)
	{
		this.score = score;
	}
	public void setScoreRelative(int score)
	{
		this.score += score;
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
		
		for(EnemyElement tee : this.getParent().getEnemies())
		{
			if(this.intersects(tee) && this.invincibleTimer < 0)
			{
				this.resetInvincibleTimer();
				this.setHpRelative(-tee.getDamage());
			}
		}
		
		for(Powerup p : this.getParent().getPowerups())
		{
			if(this.intersects(p))
			{
				this.parsePowerup(p);
				p.setAlive(false);
			}
		}
		
		// movement

		if(this.getSpeed() > 0.1) //friction threshold so it doesn't get confused
		{
			this.applyForce(this.getVelocity().copy().normalise().scale(-FRIC));
		}
		
		if(leftHeld)
		{
			this.applyForce(new Vector2f(-ACCEL,0));
		}
		if(rightHeld)
		{
			this.applyForce(new Vector2f(ACCEL,0));
		}
		if(upHeld)
		{
			this.applyForce(new Vector2f(0,-ACCEL));
		}
		if(downHeld)
		{
			this.applyForce(new Vector2f(0,ACCEL));
		}

		
		super.update(gc, sbg, delta);
		// do bounce calculations after changing position
		
		if(this.getX() <= this.getWidth() / 2)
		{
			this.setX(this.getWidth() - this.getX());
			this.setDx(BOUNCE * Math.abs(this.getDx()));
		}
		if(this.getX() >= this.getParent().getWidth() - this.getWidth() / 2)
		{
			this.setX(2 * this.getParent().getWidth() - this.getWidth() - this.getX());
			this.setDx(-BOUNCE * Math.abs(this.getDx()));
		}
		if(this.getY() <= this.getHeight() / 2)
		{
			this.setY(this.getHeight() - this.getY());
			this.setDy(BOUNCE * Math.abs(this.getDy()));
		}
		if(this.getY() >= this.getParent().getHeight() - this.getHeight() / 2)
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
	
	public void parsePowerup(Powerup p)
	{
		int type = p.getType();
		String parm = p.getParameter();
		String[] parts;
		switch(type)
		{
		case Powerup.HEAL:
			this.setHpRelative(Integer.parseInt(parm));
			System.out.println("Heal " + parm);
			System.out.println("Your health: " + this.getHp());
			if(this.getHp() > this.getMaxHp())
			{
				this.setHp(this.getMaxHp());
			}
			break;
		case Powerup.PLAYER_UPGRADE:
			parts = parm.split(" ");
			if(parts[0].equals("HEAL"))
			{
				System.out.println("Health Up: " + parts[1]);
				this.setMaxHp(this.getMaxHp() + Integer.parseInt(parts[1]));
				System.out.println("Health " + this.getHp() + "/" + this.getMaxHp());
			}
			else if(parts[0].equals("SPED"))
			{
				System.out.println("Speed Up: " + parts[1]);
				System.out.println("Your max speed: " + this.getMaxSpeed());
				this.setMaxSpeed(this.getMaxSpeed() + Float.parseFloat(parts[1]));
			}
			break;
		case Powerup.WEAPON_UPGRADE:
			parts = parm.split(" ");
			if(parts[0].equals("DAMG"))
			{
				int damg = this.getWeapon().getDamage();
				float scale = Float.parseFloat(parts[1]);
				damg *= scale;
				this.getWeapon().setDamage(this.getWeapon().getDamage() + Math.max(damg,1));
				System.out.println("Weapon Damage = " + this.getWeapon().getDamage());
			}
			else if(parts[0].equals("RATE"))
			{
				int rate = this.getWeapon().getMaxShotTime();
				float scale = Float.parseFloat(parts[1]);
				rate *= scale;
				System.out.println("Reload time decreased by " + Math.abs((Math.min(Integer.parseInt(parts[1]) * rate,-1))) + " ms.");
				this.getWeapon().setMaxShotTime(this.getWeapon().getMaxShotTime() + Math.min(rate,-1));
			}
			else if(parts[0].equals("RSPR"))
			{
				float rspr = this.getWeapon().getRandSpread();
				if(rspr > 0)
				{
					System.out.println("Spread changed by " + parts[1]);
					this.getWeapon().setRandSpread(this.getWeapon().getRandSpread() * Float.parseFloat(parts[1]));
				}
				else if(Float.parseFloat(parts[1]) > 1)
				{
					System.out.println("Spread set to " + Math.PI / 16f);
					this.getWeapon().setRandSpread((float) (Math.PI / 16f));
				}
			}
			else if(parts[0].equals("NPRO"))	
			{
				this.getWeapon().setNumFired(this.getWeapon().getNumFired() + Integer.parseInt(parts[1]));
				if(this.getWeapon().getSpread() == 0)
				{
					this.getWeapon().setSpread((float)(Math.PI / 16f));
				}
				System.out.println("Number of projectiles: " + this.getWeapon().getNumFired());
			}
			else if(parts[0].equals("RANG"))
			{
				this.getWeapon().setRange(this.getWeapon().getRange() + Integer.parseInt(parts[1]));
				System.out.println("Range Up: " + parts[1]);
			}
			else
			{
				System.out.println("Unimplemented: " + parts[0]);
			}
			break;
		case Powerup.WEAPON_SWITCH:
			System.out.println("Switching weapon to: " + parm);
			this.setWeapon(new Weapon(this.getParent(), WeaponListGenerator.weaponList.get(parm)));
			break;
		case Powerup.SCORE_BONUS:
			System.out.println("Adding " + parm + " to score.");
			this.setScoreRelative(Integer.parseInt(parm));
			break;
		}
		
		
	}
}
