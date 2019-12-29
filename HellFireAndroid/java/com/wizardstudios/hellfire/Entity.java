package com.wizardstudios.hellfire;

import android.graphics.Canvas;
import android.graphics.Rect;

public interface Entity {
	
	public void tick();
	public void draw(Canvas canvas);
	
	public Rect getBounds();
	public double getY();
	
	public void explode();
	public boolean isTargetable();
	public void takeDamage(double damage);
}
