package com.bobberto1995.aspc;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.state.StateBasedGame;

public class EnemyGenerator
{
	private PlayingField parent;
	
	public EnemyGenerator(PlayingField parent)
	{
		this.parent = parent;
	}
	
	public void update(GameContainer gc, StateBasedGame sbg, int delta)
	{
		
	}
	
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g)
	{
		
	}
	
	public void generateEnemy()
	{
		this.parent.addEnemy(new TestEnemyElement(parent, 0, 0, 0.75f, 0.75f));
	}
}
