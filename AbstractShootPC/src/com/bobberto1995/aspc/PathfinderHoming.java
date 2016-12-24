package com.bobberto1995.aspc;

import java.util.ArrayList;

import org.newdawn.slick.geom.Vector2f;

public class PathfinderHoming extends Pathfinder
{
	private int follow;
	private GameElement parent;
	private GameElement target;
	ArrayList<GameElement> targets;
	public PathfinderHoming(GameElement parent,ArrayList<GameElement> targets)
	{
		super(parent);
		this.targets = targets;
		this.target = this.getClosestTarget();
		this.follow = 8;
	}
	public PathfinderHoming(GameElement parent, ArrayList<GameElement> targets, int follow)
	{
		super(parent);
		this.targets = targets;
		this.target = this.getClosestTarget();
		this.follow = follow;
	}
	
	public GameElement getParent()
	{
		return this.parent;
	}
	
	public GameElement getClosestTarget()
	{
		float d = Float.POSITIVE_INFINITY;
		GameElement closest = null;
		for(GameElement g : targets)
		{
			if(this.getParent().getDistanceTo(g) < d)
			{
				closest = g;
			}
		}
		return closest;
	}
	
	public GameElement getTarget()
	{
		return this.target;
	}
	public void setTarget(GameElement target)
	{
		this.target = target;
	}
	
	public Vector2f getInfluence(int delta)
	{
		if(this.getTarget() == null)
		{
			GameElement newTarget = this.getClosestTarget();
			if(newTarget != null)
			{
				this.setTarget(newTarget);
			}
			else
			{
				Vector2f res = this.getParent().getVelocity();
				return res.getPerpendicular();
			}
		}
		Vector2f res = this.getParent().getVectorTo(this.getTarget());
		res.normalise();
		res.scale(this.follow);
		return res;
	}
}
