package com.wizardstudios.hellfire;

import android.graphics.Canvas;

import java.util.ArrayList;


public class Spawner {	
	private ArrayList<Entity> bullets = new ArrayList<Entity>();
	private ArrayList<Entity> enemies = new ArrayList<Entity>();
	private ArrayList<Entity> boss = new ArrayList<Entity>();
	private ArrayList<Entity> powerups = new ArrayList<Entity>();
	private HellFireGame game;
	private Entity holder;
	private int i = 0;

	public Spawner(HellFireGame game) {
		this.game = game;
	}
	
	public void tick() {
		for(i = 0; i < bullets.size(); i++) {
			holder = bullets.get(i);
			if(holder.getY() < game.getMain().getScreenHeight() / 10 || holder.getY() > game.getMain().getScreenHeight() - holder.getBounds().height() + 100) {
				removeBullet(holder);
			}
			holder.tick();
		}
		
		for(i = 0; i < enemies.size(); i++) {
			holder = (Enemy)enemies.get(i);
			if(holder.getY() > game.getMain().getScreenHeight() - holder.getBounds().height() + 100) {
				removeEnemy(holder);
				game.getPlayer().takeEscapeDamage();
			}
			holder.tick();
		}
		
		for(i = 0; i < boss.size(); i++) {
			holder = boss.get(i);
			holder.tick();
		}
		
		for(i = 0; i < powerups.size(); i++) {
			holder = powerups.get(i);
			if(holder.getY() > game.getMain().getScreenHeight() + holder.getBounds().height() + 100) {
				removePowerup(holder);
			}
			holder.tick();
		}
	}
	
	public void draw(Canvas canvas) {
		for(i = 0; i < bullets.size(); i++) {
			holder = bullets.get(i);
			holder.draw(canvas);
		}
		
		for(i = 0; i < enemies.size(); i++) {
			holder = enemies.get(i);
			holder.draw(canvas);
		}
		
		for(i = 0; i < boss.size(); i++) {
			holder = boss.get(i);
			holder.draw(canvas);
		}
		
		for(i = 0; i < powerups.size(); i++) {
			holder = powerups.get(i);
			holder.draw(canvas);
		}
	}
	
	public void addBullet(Entity b) {
		bullets.add(b);
	}
	
	public void removeBullet(Entity holder) {
		bullets.remove(holder);
		i--;
	}
	
	public ArrayList<Entity> getBullet() {
		return bullets;
	}
	
	public void addEnemy(Entity e) {
		enemies.add(e);
	}
	
	public void removeEnemy(Entity holder) {
		enemies.remove(holder);
		i--;
	}
	
	public ArrayList<Entity> getEnemy() {
		return enemies;
	}
	
	public void addBoss(Entity e) {
		boss.add(e);
	}
	
	public void removeBoss(Entity holder) {
		boss.remove(holder);
	}
	
	public ArrayList<Entity> getBoss() {
		return boss;
	}
	
	public void addPowerup(Powerup p) {
		powerups.add(p);
	}
	
	public void removePowerup(Entity holder) {
		powerups.remove(holder);
	}
	
	public ArrayList<Entity> getPowerup() {
		return powerups;
	}
	
	public void restart() {
		bullets.clear();
		enemies.clear();
		boss.clear();
		powerups.clear();
	}

	public void setTargetable(Enemy e, boolean b) {
		e.setTargetable(b);
	}
}
