package com.bobberto1995.aspc;

import java.util.ArrayList;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.command.Command;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Transform;
import org.newdawn.slick.state.StateBasedGame;

public class PlayingField
{
	private TestPlayerElement tpe;
	private BackgroundImage bgi;
	private Hud hud;
	private EnemyGenerator eg;
	private ArrayList<Bullet> bullets;
	private ArrayList<TestEnemyElement> enemies;
	private int width, height;
	private Rectangle cameraBounds; // camera positioning and use for GameElement.isOnCamera()
	private float camScale; // graphics width / 16
	private int mouseX, mouseY; // in local coordinates
	
	public PlayingField(GameContainer gc, StateBasedGame sbg) throws SlickException
	{
		this.mouseX = 0;
		this.mouseY = 0;
		this.width = 24;
		this.height = 16;
		float aspect = (float)gc.getHeight() / gc.getWidth();
		this.camScale = gc.getWidth() / 16f;
		this.cameraBounds = new Rectangle(0,0,16,aspect * 16); // adjust y coordinate based on screen resolution
		tpe = new TestPlayerElement(this,0.375f, 0.375f);
		hud = new Hud(this, tpe);
		bgi = new BackgroundImage(this,new Image("gfx/BGTileSmall.png"));
		eg = new EnemyGenerator(this);
		bullets = new ArrayList<Bullet>();
		enemies = new ArrayList<TestEnemyElement>();
	}
	
	public int getWidth()
	{
		return this.width;
	}
	
	public int getHeight()
	{
		return this.height;
	}
	
	public float getMouseX()
	{
		return this.mouseX / this.getCamScale() + this.getCameraBounds().getX();
	}
	
	public void setMouseX(int mouseX) // only used for getting mouseX from Game class
	{
		this.mouseX = mouseX;
	}
	
	public float getMouseY()
	{
		return this.mouseY / this.getCamScale() + this.getCameraBounds().getY();
	}
	
	public void setMouseY(int mouseY) // only used for getting mouseY from Game class
	{
		this.mouseY = mouseY;
	}
	
	public Rectangle getCameraBounds() {
		return cameraBounds;
	}

	public void setCameraBounds(Rectangle cameraBounds) {
		this.cameraBounds = cameraBounds;
	}
	
	public void setCameraX(float x)
	{
		this.cameraBounds.setCenterX(x);
	}
	
	public void setCameraY(float y)
	{
		this.cameraBounds.setCenterY(y);
	}
	
	public float getCamScale()
	{
		return this.camScale;
	}
	
	public void setCamScale(float camScale)
	{
		this.camScale = camScale;
	}
	
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException
	{
		// update entities
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
		
		// update camera

		this.setCameraX(this.getPlayer().getX());
		this.setCameraY(this.getPlayer().getY());
		
		// check camera
		float cbx = this.getCameraBounds().getCenterX();
		float cby = this.getCameraBounds().getCenterY();
		float cw = this.getCameraBounds().getWidth() / 2;
		float ch = this.getCameraBounds().getHeight() / 2;
		
		if(cbx + 0.5 < cw)
		{
			this.setCameraX(cw-0.5f);
		}
		
		if(cby + 0.5 < ch)
		{
			this.setCameraY(ch-0.5f);
		}
		
		if(cbx + cw - 0.5 > this.getWidth())
		{
			this.setCameraX(this.getWidth() - cw + 0.5f);
		}
		
		if(cby + ch - 0.5 > this.getHeight())
		{
			this.setCameraY(this.getHeight() - ch + 0.5f);
		}
		
		eg.update(gc, sbg, delta);
	}
	
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException
	{
		//Rectangle bgRect = new Rectangle(0,0,gc.getWidth(),gc.getHeight());
		//Image img = new Image("gfx/BGTile.png");
		//g.fillRect(0,0,gc.getWidth(),gc.getHeight(),img,this.cameraBounds.getX() * this.camScale,this.cameraBounds.getY() * this.camScale);
		bgi.render(gc, sbg, g);
		hud.render(gc,sbg,g);
		for(int i = bullets.size() - 1; i >= 0; i--)
		{
			if(bullets.get(i).isOnCamera())
			{
				bullets.get(i).render(gc, sbg, g);
			}
		}
		for(int i = enemies.size() - 1; i >= 0; i--)
		{
			if(enemies.get(i).isOnCamera())
			{
				enemies.get(i).render(gc, sbg, g);
			}
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
