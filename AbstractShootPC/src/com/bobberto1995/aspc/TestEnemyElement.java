package com.bobberto1995.aspc;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

public class TestEnemyElement extends GameElement
{
	private static final int MAX_SPEED = 100;
	private static final int FOLLOW_AMOUNT = 500;
	private static final int AVOID_AMOUNT = -10000;
	private static final int FLOCK_AMOUNT = 100;
	private int hp;
	
	public TestEnemyElement(PlayingField parent, float x, float y, float width, float height)
	{
		super(parent, x, y, width, height);
		this.setDx(100);
		this.setHp(100);
		this.setMaxSpeed(TestEnemyElement.MAX_SPEED);
	}
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException
	{
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
					avoidY *= AVOID_AMOUNT / Math.pow(avoidMagnitude, 2); // quadratically?? inversely proportionate to distance
					
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
		}
		super.update(gc, sbg, delta);
	}
	
	public void render(GameContainer gc,StateBasedGame sbg, Graphics g) throws SlickException
	{
		//g.drawString("Theta: " + this.getDirection() + "\n" + this.getDirectionTo(this.getParent().getPlayer()), this.getX(),this.getY());
		super.render(gc, sbg, g);

		g.setColor(Color.red);
		g.drawLine(this.getX(), this.getY(), this.getX() + this.getDx(), this.getY() + this.getDy());
		g.setColor(Color.blue);
		g.drawLine(this.getX(), this.getY(), this.getX() + this.getAccelX(), this.getY() + this.getAccelY());
		g.setColor(Color.white);
	}
	public int getHp()
	{
		return hp;
	}
	public void setHp(int hp)
	{
		this.hp = hp;
	}
	
}
