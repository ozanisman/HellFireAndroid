package com.wizardstudios.hellfire;

import android.graphics.Rect;

import java.util.ArrayList;

public class Collision {

	public static int checkCollision(Entity e, ArrayList<Entity> eList) {
		for(int i = 0; i < eList.size(); i++) {
			if(eList.get(i).isTargetable()) {
				if(e.getBounds().intersect(eList.get(i).getBounds())) {
					return i;
				}
			}
		}
		return -1;
	}
	
	public static boolean checkCollision(Entity e1, Player e2) {
		if(e2.isTargetable()) {
			if(e1.getBounds().intersect(e2.getPlayerBounds()[0]) || e1.getBounds().intersect(e2.getPlayerBounds()[1])) {
				return true;
			}
		}
		return false;
	}
	
	public static boolean checkBossCollision(Entity e1, ArrayList<Entity> e2) {
		Boss boss = (Boss)e2.get(0);
		if(boss.isTargetable()) {
			Rect[] bounds = boss.getBossBounds();
			for(int i = 0; i < bounds.length; i++) {
				if(e1.getBounds().intersect(bounds[i])) {
					return true;
				}
			}
		}
		return false;
	}
	
	public static int[] laserCollision(Entity e, ArrayList<Entity> eList) {
		ArrayList<Integer> holder = new ArrayList<Integer>();
		for(int i = 0; i < eList.size(); i++) {
			if(eList.get(i).isTargetable()) {
				if(e.getBounds().intersect(eList.get(i).getBounds())) {
					holder.add(i);
				}
			}
		}
		if(holder.isEmpty()) {
			return null;
		}
		int[] indexHit = new int[holder.size()];
		for(int i = 0; i < indexHit.length; i++) {
			indexHit[i] = holder.get(i);
		}
		return indexHit;
	}

}
