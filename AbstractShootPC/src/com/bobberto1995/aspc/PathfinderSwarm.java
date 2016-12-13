package com.bobberto1995.aspc;

import org.newdawn.slick.geom.Vector2f;

public class PathfinderSwarm extends Pathfinder
{
	private int follow;
	private int avoid;
	private int flock;
	
	private GameElement target;
	
	public PathfinderSwarm(GameElement parent, GameElement target)
	{
		super(parent);
		this.target = target;
		this.follow = 8;
		this.avoid = 4;
		this.flock = -4; 
	}
	
	public PathfinderSwarm(GameElement parent, GameElement target, int follow, int avoid, int flock)
	{
		super(parent);
		this.target = target;
		this.follow = follow;
		this.avoid = avoid;
		this.flock = flock;
	}
	
	public GameElement getTarget()
	{
		return this.target;
	}
	
	public Vector2f getInfluence(int delta)
	{
		Vector2f inf = new Vector2f(0,0);
		
		Vector2f followVec = this.getTarget().getVectorTo(this.getParent());
		followVec.normalise();
		followVec.scale(follow);
		inf.add(followVec);
		
		if(this.getPlayingField().getEnemies().size() > 1)
		{
			Vector2f averageVec = new Vector2f(0,0);
			
			for(EnemyElement tee : this.getPlayingField().getEnemies()) // TODO check for same instance type and same pathfinder type
			{
				Vector2f otherloc = tee.getLoc();
				averageVec.add(otherloc);
				if(tee != this.getParent())
				{
					Vector2f avoidVec = this.getParent().getVectorTo(tee);
					float avoidMagnitude = avoidVec.lengthSquared();
					avoidVec.scale(avoid / avoidMagnitude);
					inf.add(avoidVec);
				}
			}
			averageVec.scale(1.0f/this.getPlayingField().getEnemies().size());
			Vector2f flockVec = this.getParent().getLoc().sub(averageVec);
			flockVec.normalise();
			flockVec.scale(flock);
			inf.add(flockVec);
		}
		
		return inf;
	}
}
