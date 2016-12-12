package com.bobberto1995.aspc;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Transform;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;

public class GameElement
{
	private static final float MAX_SPEED = 2;
	
	private Vector2f velocity;
	private Vector2f acceleration;
	private Vector2f prevacc;
	private boolean alive;
	private PlayingField parent;
	private Shape boundingBox; // needs a better name, not necessarily a box...
	private float maxSpeed;
	
	public GameElement(PlayingField parent, float x, float y, float width, float height)
	{
		this.velocity = new Vector2f(0,0);
		this.acceleration = new Vector2f(0,0);
		this.prevacc = new Vector2f(0,0);
		this.setAlive(true);
		this.parent = parent;
		this.setBoundingBox(new Rectangle(x - width / 2f, y - height / 2f, width, height));
		this.setMaxSpeed(GameElement.MAX_SPEED);
	}
	
	public GameElement(PlayingField parent, float x, float y, float dx, float dy, float width, float height)
	{
		this.velocity = new Vector2f(dx, dy);
		this.acceleration = new Vector2f(0,0);
		this.prevacc = new Vector2f(0,0);
		this.setAlive(true);
		this.parent = parent;
		this.setBoundingBox(new Rectangle(x - width / 2f, y - height / 2f, width, height));
		this.setMaxSpeed(GameElement.MAX_SPEED);
	}

	public PlayingField getParent()
	{
		return parent;
	}
	
	public void setParent(PlayingField parent)
	{
		this.parent = parent;
	}

	public Shape getBoundingBox()
	{
		return boundingBox;
	}

	public void setBoundingBox(Shape boundingBox)
	{
		this.boundingBox = boundingBox;
	}

	public float getX()
	{
		return this.boundingBox.getCenterX();
	}
	
	public void setX(float x)
	{
		this.boundingBox.setCenterX(x);
	}
	
	public float getY()
	{
		return this.boundingBox.getCenterY();
	}
	
	public void setY(float y)
	{
		this.boundingBox.setCenterY(y);
	}
	
	public float getWidth()
	{
		return this.boundingBox.getWidth();
	}
	
	public void setWidth(float width)
	{
		float xScale = width / this.getWidth();
		this.boundingBox = this.boundingBox.transform(Transform.createScaleTransform(xScale, 1));
	}
	
	public float getHeight()
	{
		return this.boundingBox.getHeight();
	}
	
	public void setHeight(float height)
	{
		float yScale = height / this.getHeight();
		this.boundingBox = this.boundingBox.transform(Transform.createScaleTransform(1,yScale));
	}
	
	public Vector2f getVelocity()
	{
		return this.velocity;
	}
	
	public float getDx()
	{
		return this.velocity.getX();
	}
	
	public void setDx(float dx)
	{
		this.velocity.set(dx, this.velocity.getY());
	}
	public float getDy()
	{
		return this.velocity.getY();
	}
	
	public void setDy(float dy)
	{
		this.velocity.set(this.velocity.getX(), dy);
	}
	
	
	public Vector2f getAcceleration()
	{
		return this.acceleration;
	}
	
	public float getAccelX()
	{
		return this.acceleration.getX();
	}
	
	public void setAccelX(float ax)
	{
		this.acceleration.set(ax, this.acceleration.getY());
	}
	
	public float getAccelY()
	{
		return this.acceleration.getY();
	}
	
	public void setAccelY(float ay)
	{
		this.acceleration.set(this.acceleration.getX(), ay);
	}
	
	public boolean isAlive()
	{
		return alive;
	}

	public void setAlive(boolean alive)
	{
		this.alive = alive;
	}

	public float getSpeed()
	{
		return this.velocity.length();
	}
	
	public void setSpeed(float speed)
	{
		if(velocity.length() > 0)
		{
			this.velocity.normalise();
			this.velocity.scale(speed);
		}
		else this.setDx(speed);
	}
	
	public float getMaxSpeed()
	{
		return this.maxSpeed;
	}
	
	public void setMaxSpeed(float maxSpeed)
	{
		this.maxSpeed = maxSpeed;
	}
	
	public double getDirection()
	{
		return this.velocity.getTheta() * Math.PI / 180;
	}
	
	public void setDirection(double direction)
	{
		this.velocity.setTheta(direction / Math.PI * 180);
	}
	
	public double getDirectionTo(GameElement other)
	{
		double dir = Math.atan2(other.getY() - this.getY(), other.getX() - this.getX());
		if(dir < 0)
		{
			dir += 2 * Math.PI;
		}
		return dir;
	}
	
	public boolean intersects(GameElement other)
	{
		return this.getBoundingBox().intersects(other.getBoundingBox());
	}
	
	public boolean isOnCamera() // so we can render only the entities on the screen
	{
		try
		{
			return this.getBoundingBox().intersects(parent.getCameraBounds());
		}
		catch(Exception e)
		{
			System.out.println(this.getParent());
			return false;
		}
	}
	
	public void applyForce(Vector2f force)
	{
		this.acceleration.add(force);
	}
	
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException
	{
		this.acceleration.scale(delta / 1000f);
		this.velocity.add(this.acceleration);
		if(this.getSpeed() > this.getMaxSpeed())
		{
			this.setSpeed(this.getMaxSpeed());
			System.out.println("Max speed reached: " + this.getMaxSpeed());
		}
		this.prevacc = this.acceleration.copy();
		this.acceleration.scale(0);
		Vector2f scaled = this.velocity.copy().scale(delta / 1000f);
		this.setX(this.getX() + scaled.getX());
		this.setY(this.getY() + scaled.getY());
	}
	
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException
	{
		g.pushTransform();
		Rectangle cbb = parent.getCameraBounds();
		float cs = parent.getCamScale();
		
		g.translate(-cbb.getX() * cs, -cbb.getY() * cs);
		g.scale(cs, cs);
		
		g.setColor(Color.black);
		g.fill(this.getBoundingBox());
		g.setColor(Color.white);
		g.draw(this.getBoundingBox());

		g.setColor(Color.red);
		g.drawLine(this.getX(), this.getY(), this.getX() + this.getDx(), this.getY() + this.getDy());
		g.setColor(Color.blue);
		g.drawLine(this.getX(), this.getY(), this.getX() + this.prevacc.x, this.getY() + this.prevacc.y);
		g.setColor(Color.white);
		g.popTransform();
	}
}
