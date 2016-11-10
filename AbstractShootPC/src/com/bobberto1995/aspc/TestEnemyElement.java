package com.bobberto1995.aspc;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.state.StateBasedGame;

public class TestEnemyElement extends GameElement
{
	private static final int MAX_SPEED = 2;
	private static final int FOLLOW_AMOUNT = 8;
	private static final int AVOID_AMOUNT = -4;
	private static final int FLOCK_AMOUNT = 4;
	private int hp;
	private int score; // how many points this is worth to kill
	private int damage; // how much damage this enemy does to the player
	
	public TestEnemyElement(PlayingField parent, float x, float y, float width, float height)
	{
		super(parent, x, y, width, height);
		this.setDx(2);
		this.setHp(100);
		this.setScore(100);
		this.setDamage(10);
		this.setMaxSpeed(TestEnemyElement.MAX_SPEED);
	}
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException
	{
		// steering
		
		float followX = (this.getParent().getPlayer().getX() - this.getX());
		float followY = (this.getParent().getPlayer().getY() - this.getY());
		
		float followMagnitude = (float) Math.sqrt(Math.pow(followX, 2) + Math.pow(followY, 2));
		
		followX *= FOLLOW_AMOUNT / followMagnitude;
		followY *= FOLLOW_AMOUNT / followMagnitude;
		
		this.setAccelX(followX);  // steer towards goal (player)
		this.setAccelY(followY);
		
		if(this.getParent().getEnemies().size() > 1) // steer towards center of mass, avoid other enemies
		{
			float averageX = 0;
			float averageY = 0;
			
			for(TestEnemyElement tee : this.getParent().getEnemies())
			{
				if(!tee.equals(this))
				{
					averageX += tee.getX();
					averageY += tee.getY();
					float avoidX = (tee.getX() - this.getX());
					float avoidY = (tee.getY() - this.getY());
					float avoidMagnitude = (float) Math.sqrt(Math.pow(avoidX, 2) + Math.pow(avoidY, 2));
					
					avoidX *= AVOID_AMOUNT / Math.pow(avoidMagnitude, 2);
					avoidY *= AVOID_AMOUNT / Math.pow(avoidMagnitude, 2); // quadratically inversely proportionate to distance
					
					this.setAccelX(this.getAccelX() + avoidX);
					this.setAccelY(this.getAccelY() + avoidY);
				}
			}
			
			int numEnemies = this.getParent().getEnemies().size();
			
			averageX /= numEnemies;
			averageY /= numEnemies;
			
			float flockX = averageX - this.getX();
			float flockY = averageY - this.getY();
			
			float flockMagnitude = (float) Math.sqrt(Math.pow(flockX, 2) + Math.pow(flockY, 2));
			
			flockX *= FLOCK_AMOUNT / flockMagnitude;
			flockY *= FLOCK_AMOUNT / flockMagnitude;
			
			this.setAccelX(this.getAccelX() + flockX);
			this.setAccelY(this.getAccelY() + flockY);
		}
		
		// collide with bullets
		
		for(Bullet b : this.getParent().getBullets())
		{
			if(this.intersects(b) && b.isAlive())
			{
				b.setAlive(false);
				this.setDx(this.getDx() + b.getDx()); 
				this.setDy(this.getDy() + b.getDy());
				this.setHp(this.getHp() - 10);
			}
		}
		if(this.getHp() <= 0)
		{
			this.setAlive(false);
			this.getParent().getPlayer().setScoreRelative(this.getScore());
		}
		
		// move and stuff
		
		super.update(gc, sbg, delta);
	}
	
	public void render(GameContainer gc,StateBasedGame sbg, Graphics g) throws SlickException
	{
		//g.drawString("Theta: " + this.getDirection() + "\n" + this.getDirectionTo(this.getParent().getPlayer()), this.getX(),this.getY());
		super.render(gc, sbg, g);
		
		// display velocity and acceleration vectors
//		Rectangle cbb = this.getParent().getCameraBounds();
//		float cs = this.getParent().getCamScale();
//		float tempX = (this.getX() - cbb.getX()) * cs;
//		float tempY = (this.getY() - cbb.getY()) * cs;
//		float tdX = (this.getX() + this.getDx() - cbb.getX()) * cs;
//		float tdY = (this.getY() + this.getDy() - cbb.getY()) * cs;
//		float taX = (this.getX() + this.getAccelX() - cbb.getX()) * cs;
//		float taY = (this.getY() + this.getAccelY() - cbb.getY()) * cs;
//		
//		g.setColor(Color.red);
//		g.drawLine(tempX, tempY, tdX, tdY);
//		g.setColor(Color.blue);
//		g.drawLine(tempX, tempY, taX, taY);
//		g.setColor(Color.white);
	}
	public int getHp()
	{
		return hp;
	}
	public void setHp(int hp)
	{
		this.hp = hp;
	}
	public int getScore() {
		return score;
	}
	public void setScore(int score) {
		this.score = score;
	}
	public int getDamage() {
		return damage;
	}
	public void setDamage(int damage) {
		this.damage = damage;
	}
	
}
