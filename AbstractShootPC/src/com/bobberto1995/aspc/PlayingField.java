package com.bobberto1995.aspc;

import java.util.ArrayList;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.command.Command;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.state.StateBasedGame;

public class PlayingField
{
	private TestPlayerElement tpe;
	private Hud hud;
	private ArrayList<Bullet> bullets;
	private ArrayList<TestEnemyElement> enemies;
	int width, height; // I think this will be 24 * 16
	private Rectangle cameraBounds; // camera positioning and use for GameElement.isOnCamera()
	int offsetX, offsetY; // for camera panning
	float camScale; // graphics width / 16
	int mouseX, mouseY; // convert to local coordinates?
	
	public PlayingField(GameContainer gc, StateBasedGame sbg)
	{
		this.width = gc.getWidth();
		this.height = gc.getHeight();
		this.mouseX = 0;
		this.mouseY = 0;
		this.width = 24;
		this.height = 16;
		this.cameraBounds = new Rectangle(0,0,16,9); // adjust based on screen resolution
		tpe = new TestPlayerElement(this, width / 2f, height / 2f);
		hud = new Hud(this, tpe);
		bullets = new ArrayList<Bullet>();
		enemies = new ArrayList<TestEnemyElement>();
		enemies.add(new TestEnemyElement(this, 0,0,50,50));
		enemies.add(new TestEnemyElement(this,this.width,0,50,50));
		enemies.add(new TestEnemyElement(this,this.width,this.height,50,50));
		enemies.add(new TestEnemyElement(this,0,this.height,50,50));
		enemies.add(new TestEnemyElement(this,0,100,50,50));
		enemies.add(new TestEnemyElement(this,0,200,50,50));
		enemies.add(new TestEnemyElement(this,100,0,50,50));
	}
	
	public int getWidth()
	{
		return this.width;
	}
	
	public int getHeight()
	{
		return this.height;
	}
	
	public int getMouseX()
	{
		return this.mouseX - offsetX;
	}
	
	public int getMouseY()
	{
		return this.mouseY - offsetY;
	}
	
	public Rectangle getCameraBounds() {
		return cameraBounds;
	}

	public void setCameraBounds(Rectangle cameraBounds) {
		this.cameraBounds = cameraBounds;
	}

	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException
	{
		for(int i = bullets.size() - 1; i >= 0; i--)
		{
			bullets.get(i).update(gc, sbg, delta);
			if(!bullets.get(i).isAlive())
			{
				bullets.remove(i);
			}
		}
		for(int i = enemies.size() - 1; i >= 0; i--)
		{
			enemies.get(i).update(gc, sbg, delta);
			if(!enemies.get(i).isAlive())
			{
				enemies.remove(i);
			}
		}
		tpe.update(gc, sbg, delta);
	}
	
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException
	{
		hud.render(gc,sbg,g);
		for(int i = bullets.size() - 1; i >= 0; i--)
		{
			bullets.get(i).render(gc, sbg, g);
		}
		for(int i = enemies.size() - 1; i >= 0; i--)
		{
			enemies.get(i).render(gc, sbg, g);
		}
		tpe.render(gc, sbg, g);
	}
	
	public void addBullet(Bullet b)
	{
		bullets.add(b);
	}
	
	public ArrayList<Bullet> getBullets()
	{
		return this.bullets;
	}
	
	public void addEnemy(TestEnemyElement e)
	{
		enemies.add(e);
	}
	
	public ArrayList<TestEnemyElement> getEnemies()
	{
		return this.enemies;
	}
	
	public TestPlayerElement getPlayer()
	{
		return this.tpe;
	}
	
	public void controlPressed(Command com)
	{
		if(com.equals(Game.moveUp))
		{
			tpe.upPressed();
		}
		else if(com.equals(Game.moveDown))
		{
			tpe.downPressed();
		}
		else if(com.equals(Game.moveLeft))
		{
			tpe.leftPressed();
		}
		else if(com.equals(Game.moveRight))
		{
			tpe.rightPressed();
		}
		else if(com.equals(Game.fireBullet))
		{
			tpe.mousePressed();
		}
	}
	public void controlReleased(Command com)
	{
		if(com.equals(Game.moveUp))
		{
			tpe.upReleased();
		}
		else if(com.equals(Game.moveDown))
		{
			tpe.downReleased();
		}
		else if(com.equals(Game.moveLeft))
		{
			tpe.leftReleased();
		}
		else if(com.equals(Game.moveRight))
		{
			tpe.rightReleased();
		}
		else if(com.equals(Game.fireBullet))
		{
			tpe.mouseReleased();
		}
	}
}
