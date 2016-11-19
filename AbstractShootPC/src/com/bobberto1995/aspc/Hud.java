package com.bobberto1995.aspc;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.state.StateBasedGame;

public class Hud
{
	private PlayingField parent;
	private PlayerElement player;
	
	public Hud(PlayingField parent, PlayerElement player)
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

	public PlayerElement getPlayer()
	{
		return player;
	}

	public void setPlayer(PlayerElement player) 
	{
		this.player = player;
	}
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g)
	{
		g.drawRect(0, 0, gc.getWidth(), 20);
		g.fillRect(0, 0, gc.getWidth() * (player.getHp() / 100f), 20);
		g.drawString("Score: " + this.getPlayer().getScore(), 0, gc.getHeight()-100);
		// display player score
	}
}
