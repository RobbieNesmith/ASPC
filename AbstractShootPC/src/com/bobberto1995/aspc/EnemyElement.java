package com.bobberto1995.aspc;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;

public class EnemyElement extends GameElement
{
	private static final int MAX_SPEED = 2;
	private static final int FOLLOW_AMOUNT = 8;
	private static final int AVOID_AMOUNT = -4;
	private static final int FLOCK_AMOUNT = 4;
	private int hp;
	private int score; // how many points this is worth to kill
	private int damage; // how much damage this enemy does to the player
	private int dropChance; // percent chance EnemyElement will drop a Powerup
	
	private int follow;
	private int avoid;
	private int flock;
	
	private Pathfinder pathfinder;
	
	/*
	 * Creates default EnemyElement
	 * @param parent the PlayingField to contain the EnemyElement
	 * @param x the x coordinate
	 * @param y the y coordinate
	 */
	public EnemyElement(PlayingField parent, float x, float y, float size)
	{
		super(parent, x, y, size, size);
		this.setDx(2);
		this.setHp(100);
		this.setScore(100);
		this.setDamage(10);
		this.setMaxSpeed(EnemyElement.MAX_SPEED);
		this.setDropChance(10);
		this.pathfinder = new PathfinderSwarm(this,this.getParent().getPlayer());
	}
	
	public EnemyElement(PlayingField parent, float x, float y, float size, int hp, int score, int damage, int maxSpeed, int dropChance, int follow, int avoid, int flock)
	{
		super (parent,x,y,size,size);
		this.setDx(2);
		this.setHp(hp);
		this.setScore(score);
		this.setDamage(damage);
		this.setMaxSpeed(maxSpeed);
		this.setDropChance(dropChance);
		this.pathfinder = new PathfinderSwarm(this,this.getParent().getPlayer());
	}
	
	public Pathfinder getPathfinder()
	{
		return this.pathfinder;
	}
	
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException
	{
		// steering
		
		Vector2f inf = this.getPathfinder().getInfluence(delta);
		this.applyForce(inf);
		
		
		// collide with bullets
		
		for(Bullet b : this.getParent().getBullets())
		{
			if(this.intersects(b) && this.getHp() > 0 && b.isAlive() && !b.getOwner())
			{
				b.setAlive(false);
				this.setDx(this.getDx() + b.getDx()); 
				this.setDy(this.getDy() + b.getDy());
				this.setHp(this.getHp() - b.getDamage());
			}
		}
		if(this.getHp() <= 0)
		{
			this.setAlive(false);
			this.getParent().getPlayer().setScoreRelative(this.getScore());
			if(Math.random() * 0 < this.getDropChance())
			{
				Powerup dropped = PowerupListGenerator.getRandomPowerup();
				this.getParent().addPowerup(new Powerup(this.getParent(), this.getX(), this.getY(), dropped));
			}
		}
		
		// move and stuff
		
		super.update(gc, sbg, delta);
	}
	
	public void render(GameContainer gc,StateBasedGame sbg, Graphics g) throws SlickException
	{
		//g.drawString("Theta: " + this.getDirection() + "\n" + this.getDirectionTo(this.getParent().getPlayer()), this.getX(),this.getY());
		super.render(gc, sbg, g);
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
	public int getDropChance() {
		return dropChance;
	}
	public void setDropChance(int dropChance) {
		this.dropChance = dropChance;
	}
	public int getFollow() {
		return follow;
	}
	public void setFollow(int follow) {
		this.follow = follow;
	}
	public int getAvoid() {
		return avoid;
	}
	public void setAvoid(int avoid) {
		this.avoid = avoid;
	}
	public int getFlock() {
		return flock;
	}
	public void setFlock(int flock) {
		this.flock = flock;
	}
	
}
