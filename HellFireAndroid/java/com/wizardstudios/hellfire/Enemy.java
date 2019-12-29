package com.wizardstudios.hellfire;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

public class Enemy extends GameObject implements Entity, Cloneable {
	
	private int money;
	private int health;
	private int shootingSpeed;
	private int bulletDamage;
	private double defaultVelocityY;
	private double velocityY;
	private int damageDecrease = 1;
	private Bitmap image;
	private Bitmap bulletImage;
	private HellFireGame game;
	private Textures texture;
	private int counter = 0;
	private boolean explode = false;
	private int explodeCounter = 0;
	private boolean isTargetable = true;
	private double excessDamage = 0;
	private int shootType;

	
	public Enemy(double x, double y, int width, int height, int money, int health, int shootingSpeed, int bulletDamage, double velocityY, Bitmap image, Bitmap bulletImage, HellFireGame game, Textures texture, int shootType) {
		super(x, y, width, height);
		this.money = money;
		this.health = health;
		this.shootingSpeed = shootingSpeed;
		this.bulletDamage = bulletDamage;
		this.velocityY = velocityY;
		this.defaultVelocityY = velocityY;
		this.image = image;
		this.bulletImage = bulletImage;
		this.game = game;
		this.texture = texture;
		this.shootType = shootType;
	}
	
	public void tick() {
		if(!game.getPlayer().getPause()) {
			if(explode) {
				if(explodeCounter >= texture.shipExplosion.length) {
					game.getSpawner().removeEnemy(this);
					//POWERUP CODE
					double random = Math.random();
					if(random <= game.getPlayer().getUpgrades().getPowerupDrop().getUpgradeValue()) {
						game.getSpawner().addPowerup(new Powerup(getX() + 32, getY() + 32, texture.powerup1.getWidth(), texture.powerup1.getHeight(), game, texture, (int)(Math.random() * 5)));
					}
				} else if(counter == 2) {
					image = texture.shipExplosion[explodeCounter];
					explodeCounter++;
					counter = 0;
				} else {
					counter++;
				}
			} else {
				if(velocityY > defaultVelocityY) {
					velocityY = defaultVelocityY;
				}
				if(velocityY > 0) {
					y += velocityY;
				}
				if(counter == shootingSpeed) {

					if(shootType == 3) {
						game.getSpawner().addBullet(new Blast(bulletDamage / damageDecrease, x + image.getWidth() / 2 - bulletImage.getWidth() / 2, getY() + 40, bulletImage, texture, bulletImage.getWidth(), bulletImage.getHeight(), 0, 10, false, game, 3));
						game.getSpawner().addBullet(new Blast(bulletDamage / damageDecrease, x + image.getWidth() / 2 - bulletImage.getWidth() / 2, getY() + 40, bulletImage, texture, bulletImage.getWidth(), bulletImage.getHeight(), 0, 10, false, game, 4));
					} else if(shootType == 4) {
						game.getSpawner().addBullet(new Blast(bulletDamage / damageDecrease, x + image.getWidth() / 2 - bulletImage.getWidth() / 2, getY() + 40, bulletImage, texture, bulletImage.getWidth(), bulletImage.getHeight(), 0, 10, false, game, 3));
						game.getSpawner().addBullet(new Blast(bulletDamage / damageDecrease, x + image.getWidth() / 2 - bulletImage.getWidth() / 2, getY() + 40, bulletImage, texture, bulletImage.getWidth(), bulletImage.getHeight(), 0, 10, false, game, 4));
						game.getSpawner().addBullet(new Blast(bulletDamage / damageDecrease, x + image.getWidth() / 2 - bulletImage.getWidth() / 2, getY() + 40, bulletImage, texture, bulletImage.getWidth(), bulletImage.getHeight(), 0, 10, false, game, 1));
					} else {
						game.getSpawner().addBullet(new Blast(bulletDamage / damageDecrease, x + image.getWidth() / 2 - bulletImage.getWidth() / 2, getY() + 40, bulletImage, texture, bulletImage.getWidth(), bulletImage.getHeight(), 0, 10, false, game, shootType));
					}
					counter = 0;
				} else {
					counter++;
				}
			}
		}
	}
	
	public void draw(Canvas canvas) {
		Paint paint = new Paint();
		canvas.drawBitmap(image, (float)x, (float)y, null);
		
		//DRAW HITBOX
		/*
		paint.setColor(Color.RED);
		paint.setStyle(Paint.Style.STROKE);
		canvas.drawRect(this.getBounds(), paint);
		*/
	}

	public Rect getBounds() {
		if(image == texture.enemy1) {
			return createHitbox((int)getX() + 12, (int)getY() + 15, 86, 75);
		} else if(image == texture.enemy2) {
			return createHitbox((int)getX() + 35, (int)getY() + 10, 40, 90);
		} else if(image == texture.enemy3) {
			return createHitbox((int)getX() + 5, (int)getY() + 5, 100, 100);
		} else if(image == texture.enemy4) {
			return createHitbox((int)getX() + 10, (int)getY() + 10, 95, 90);
		} else if(image == texture.enemy5) {
			return createHitbox((int)getX() + 15, (int)getY() + 10, 80, 90);
		} else if(image == texture.enemy6) {
			return createHitbox((int)getX() + 15, (int)getY() + 10, 80, 90);
		} else if(image == texture.enemy7){
			return createHitbox((int)getX() + 15, (int)getY() + 10, 80, 85);
		} else {
			return createHitbox((int)getX(), (int)getY(), image.getWidth(), image.getHeight());
		}
	}
	
	public void setX(double x) {
		this.x = x;
	}
	
	public void changeVelocityY(double velocityY) {
		this.velocityY += velocityY;
	}

	public double getVelocityY() {
		return velocityY;
	}
	
	public void explode() {
		game.getPlayer().money(money);
		counter = 0;
		this.explode = true;
		isTargetable = false;
	}

	public boolean isTargetable() {
		return isTargetable;
	}

	public void setTargetable(boolean b) {
		isTargetable = b;
	}
	
	public void takeDamage(double damage) {
		int whole = (int)damage % 1;
		double fraction = damage - whole;
		
		if(damage % 1 == 0) {
			health -= damage;
		} else {
			health -= whole;
			excessDamage += fraction;
		}
		while(excessDamage > 1) {
			health -= 1;
			excessDamage -= 1;
		}
		if(health <= 0) {
			explode();
		}
	}
	
	public void setDamageDecrease(int d) {
		damageDecrease = d;
	}

	public Enemy clone() {
	    try {
			return (Enemy)super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
	    return null;
	}

	public Rect createHitbox(int x, int y, int width, int height) {
		return new Rect(x, y, x + width, y + height);
	}
}
