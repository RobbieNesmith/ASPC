package com.bobberto1995.aspc;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.tiled.TiledMap;

public class BackgroundImage extends GameElement
{
	//Image img;
	TiledMap bg;
	
	public BackgroundImage(PlayingField parent, Image img) throws SlickException
	{
		super(parent, parent.getWidth() / 2, parent.getHeight() / 2, parent.getWidth(), parent.getHeight());
		//this.img = img.getScaledCopy((int)parent.getCamScale(),(int)parent.getCamScale());
		bg = new TiledMap("gfx/background.tmx");
	}
	
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g)
	{
		Rectangle cbb = this.getParent().getCameraBounds();
		float cs = this.getParent().getCamScale();
		int tileSize = bg.getTileWidth();
		
		//g.fillRect(x,y,w,h,img,(cbb.getX() * cs) % img.getWidth(),(cbb.getY() * cs) % img.getHeight());
		g.pushTransform();
		g.translate(-cs * cbb.getX(), -cs * cbb.getY());
		g.scale(cs / tileSize, cs / tileSize);
		bg.render(0,0);
		
		g.popTransform();
	}
}
