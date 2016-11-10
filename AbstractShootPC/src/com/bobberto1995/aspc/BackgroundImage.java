package com.bobberto1995.aspc;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Transform;
import org.newdawn.slick.state.StateBasedGame;

public class BackgroundImage extends GameElement
{
	Image img;
	
	public BackgroundImage(PlayingField parent, Image img)
	{
		super(parent, parent.getWidth() / 2, parent.getHeight() / 2, parent.getWidth(), parent.getHeight());
		this.img = img.getScaledCopy((int)parent.getCamScale(),(int)parent.getCamScale());
	}
	
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g)
	{
		Rectangle cbb = this.getParent().getCameraBounds();
		float cs = this.getParent().getCamScale();
		Shape adjusted = this.getBoundingBox().transform( Transform.createScaleTransform(cs,cs));
		adjusted = adjusted.transform( Transform.createTranslateTransform(-cbb.getX() * cs, -cbb.getY() * cs) );
		float imScale = 100;
		float x = Math.max(adjusted.getX(), 0);
		float y = Math.max(adjusted.getY(), 0);
		float w = Math.min(adjusted.getMaxX(), gc.getWidth());
		float h = Math.min(adjusted.getMaxY(), gc.getHeight());
		g.fillRect(x,y,w,h,img,(cbb.getX() * cs) % img.getWidth(),(cbb.getY() * cs) % img.getHeight());
	}
}
