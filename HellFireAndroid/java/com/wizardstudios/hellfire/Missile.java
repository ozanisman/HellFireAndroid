package com.wizardstudios.hellfire;

import android.graphics.Bitmap;
import android.graphics.Paint;
import android.graphics.Rect;

import java.util.ArrayList;
	
public class Missile extends GameObject implements Entity {
	
	private int damage;
	private double explosionX;
	private double explosionY;
	private int explosionDamage;
	private int maxDiameter;
	private int currentDiameter = 20;
	private double velocityX;
	private double velocityY;
	private boolean friendly;
	private Textures texture;
	private HellFireGame game;
	private boolean explode = false;
	private int counter = 0;
	private Bitmap image;
	private boolean isTargetable = true;
	private boolean canMove = true;
	private ArrayList<Integer> hitEnemies = new ArrayList<Integer>();
	
	public Missile(int damage, int explodeDamage, int maxDiameter, double x, double y, Bitmap image, Textures texture, int width, int height, double velX, double velY, boolean friendly, HellFireGame game) {
		super(x, y, width, height);
		this.texture = texture;
		this.damage = damage;
		explosionDamage = explodeDamage;
		this.maxDiameter = maxDiameter;
		this.velocityX = velX;
		this.velocityY = velY;
		this.image = image;
		this.friendly = friendly;
		this.game = game;
	}
	
	public void tick() {
		
		if(!game.getPlayer().getPause()) {
			if(explode) {
				if(counter >= 60 || currentDiameter == maxDiameter) {
					game.getSpawner().removeBullet(this);
				}
				counter++;
			} else {
				if(canMove) {
					x += velocityX;
					y += velocityY;
					if(counter % 3 == 0) {
						velocityY--;
					}
					counter++;
				}
				if(friendly) {
					if(game.getSpawner().getBoss().isEmpty()) {
						int index = Collision.checkCollision(this, game.getSpawner().getEnemy());
						if(index > -1) {
							game.getSpawner().getEnemy().get(index).takeDamage(damage);
							explode();
						}
					} else {
						if(Collision.checkBossCollision(this, game.getSpawner().getBoss())) {
							game.getSpawner().getBoss().get(0).takeDamage(damage);
							explode();
						}
					}
				} else {
					if(Collision.checkCollision(this, game.getPlayer())) {
						canMove = false;
						explode();
						game.getPlayer().takeDamage(this.damage);
					}
				}
			}
		}
	}
	
	public void draw(android.graphics.Canvas canvas) {
		Paint paint = new Paint();
		if(explode) {
			for(int i = 0; i < game.getSpawner().getEnemy().size(); i++) {
				if(!hitEnemies.contains(i) && game.getSpawner().getEnemy().get(i).isTargetable() && checkExplosionCollision(game.getSpawner().getEnemy().get(i).getBounds())) {
					game.getSpawner().getEnemy().get(i).takeDamage(explosionDamage);
					hitEnemies.add(i);
				}
			}

			//currentDiameter = (maxDiameter / 20) * counter;
			currentDiameter += maxDiameter / 3;

			if(currentDiameter > maxDiameter) {
				currentDiameter = maxDiameter;
			}
			explosionX = x - (currentDiameter / 2);
			explosionY = y - (currentDiameter / 2);
			canvas.drawBitmap(image, new Rect(0, 0, image.getWidth(), image.getHeight()), createHitbox(getBounds().left, getBounds().top, currentDiameter, currentDiameter), null);
		} else {
			canvas.drawBitmap(image, (float)x, (float)y, null);
		}
		
		//DRAW HITBOX
		/*
		paint.setColor(Color.RED);
		paint.setStyle(Paint.Style.STROKE);
		canvas.drawOval(this.getBounds().left, this.getBounds().top, this.getBounds().right, this.getBounds().bottom, paint);
		*/
	}

	public Bitmap getImage() {
		return image;
	}

	public void setImage(Bitmap bullet) {
		this.image = bullet;
	}
	
	public boolean getFriendly() {
		return friendly;
	}
	
	public void explode() {
		counter = 0;
		this.explode = true;
		x += 10;
		explosionX = x - (maxDiameter / 30 * counter) / 2;
		explosionY = y - (maxDiameter / 30 * counter) / 2;
		this.image = texture.missileExplosion;
	}
	
	public Rect getBounds() {
		if(explode) {
			return createHitbox((int)explosionX + counter / 3, (int)explosionY + counter / 3, currentDiameter - counter / 2, currentDiameter - counter * 13 / 16);
		}
		return createHitbox((int)x + 10, (int)y + 5, 20, 30);
	}

	public boolean isTargetable() {
		return isTargetable;
	}

	public void takeDamage(double damage) {}

	public boolean checkExplosionCollision(Rect rect) {
		double width = rect.width();
		double height = rect.height();
		double initialX = rect.left;
		double initialY = rect.top;
		double pointCheckingX = initialX;
		double pointCheckingY = initialY;
		double circleRadius = currentDiameter / 2;
		double circleCenterX = explosionX + circleRadius;
		double circleCenterY = explosionY + circleRadius;
		for(int i = 0; i < width; i++) { //Checks Top Side
			if(getDistance(pointCheckingX, pointCheckingY, circleCenterX, circleCenterY) <= circleRadius) {
				return true;
			}
			pointCheckingX++;
		}
		pointCheckingX = initialX;
		pointCheckingY = initialY;
		for(int i = 0; i < height; i++) { //Checks Left Side
			if(getDistance(pointCheckingX, pointCheckingY, circleCenterX, circleCenterY) <= circleRadius) {
				return true;
			}
			pointCheckingY++;
		}
		pointCheckingX = initialX;
		pointCheckingY = initialY + height;
		for(int i = 0; i < width; i++) { //Checks Bottom Side
			if(getDistance(pointCheckingX, pointCheckingY, circleCenterX, circleCenterY) <= circleRadius) {
				return true;
			}
			pointCheckingX++;
		}
		pointCheckingX = initialX + width;
		pointCheckingY = initialY;
		for(int i = 0; i < height; i++) { //Checks Right Side
			if(getDistance(pointCheckingX, pointCheckingY, circleCenterX, circleCenterY) <= circleRadius) {
				return true;
			}
			pointCheckingY++;
		}
		return false;
	}
	
	public double getDistance(double x1, double y1, double x2, double y2) {
		return Math.sqrt(Math.pow((x2 - x1), 2) + Math.pow((y2 - y1), 2));
	}

	public Rect createHitbox(int x, int y, int width, int height) {
		return new Rect(x, y, x + width, y + height);
	}
	
}