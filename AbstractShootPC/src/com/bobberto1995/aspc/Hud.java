package com.bobberto1995.aspc;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.state.StateBasedGame;

public class Hud
{
	private PlayingField parent;
	private TestPlayerElement player;
	
	public Hud(PlayingField parent, TestPlayerElement player)
	{
		this.parent = parent;
		this.player = player;
	}

	public PlayingField getParent()
	{
		return parent;
	}

	public void setParent(PlayingField parent)
	{
		this.parent = parent;
	}

	public TestPlayerElement getPlayer()
	{
		return player;
	}

	public void setPlayer(TestPlayerElement player) 
	{
		this.player = player;
	}
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g)
	{
		g.drawRect(0, 0, parent.getWidth(), 20);
		g.fillRect(0, 0, parent.getWidth() * (player.getHp() / 100f), 20);
		// display player score
	}
}
