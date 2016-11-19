package com.bobberto1995.aspc;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.state.StateBasedGame;

public class EnemyGenerator
{
	private PlayingField parent;
	
	private int maxGeneratorTime;
	private int generatorTimer;
	
	public EnemyGenerator(PlayingField parent)
	{
		this.parent = parent;
		this.maxGeneratorTime = 1000;
	}
	
	public void update(GameContainer gc, StateBasedGame sbg, int delta)
	{
		generatorTimer -= delta;
		if(generatorTimer < 0)
		{
			generatorTimer = maxGeneratorTime;
			generateEnemy();
		}
	}
	
	public void generateEnemy()
	{
		int side = (int)(Math.random() * 4);
		int x = 0;
		int y = 0;
		
		switch(side)
		{
		case 0:
			x = 25;
			y = (int)(Math.random() * 27) - 1; // -1 to 25 inclusive
			break;
		case 1:
			x = (int)(Math.random() * 19) - 1; // -1 to 17 inclusive
			y = -1;
			break;
		case 2:
			x = -1;
			y = (int)(Math.random() * 27) - 1; // -1 to 25 inclusive
			break;
		case 3:
			x = (int)(Math.random() * 19) - 1; // -1 to 17 inclusive
			y = 17;
			break;
		}
		
		this.parent.addEnemy(new EnemyElement(parent, x, y, 0.75f, 0.75f));
	}
}
