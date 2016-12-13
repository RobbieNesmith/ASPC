package com.bobberto1995.aspc;

import org.newdawn.slick.geom.Vector2f;

public class Pathfinder
{
	GameElement parent;
	public Pathfinder(GameElement parent)
	{
		this.parent = parent;
	}
	
	public GameElement getParent()
	{
		return this.parent;
	}
	
	public Vector2f getInfluence(int delta)
	{
		return new Vector2f(0,0); // default Pathfinder does nothing
	}
	
	public PlayingField getPlayingField()
	{
		return this.getParent().getParent(); // yikes
	}
}
