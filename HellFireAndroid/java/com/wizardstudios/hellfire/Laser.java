package com.wizardstudios.hellfire;

import android.graphics.Bitmap;
import android.graphics.Paint;
import android.graphics.Rect;



public class Laser extends GameObject implements Entity {
	
	private double damage;
	private Textures texture;
	private HellFireGame game;
	private int counter = 0;
	private int currentImage = 0;
	private Bitmap[] images;
	private boolean isTargetable = true;
	private boolean friendly;
	private int laserDuration;
	private int changeImageDuration;
	private int waitBetweenShots;
	
	public Laser(int damage, int x, int y, Bitmap[] images, Textures texture, int width, int height, boolean friendly, HellFireGame game) {
		super(x, y, width, height);
		this.game = game;
		this.texture = texture;
		this.damage = damage;
		this.images = images;
		this.friendly = friendly;
		if(friendly) {
			laserDuration = 30;
			changeImageDuration = 2;
			waitBetweenShots = 20;
		} else {
			laserDuration = 40;
			changeImageDuration = 1;
			waitBetweenShots = 50;
		}
	}
	
	public void tick() {
		if(!game.getPlayer().getPause()) {
			if(friendly) {
                x = game.getPlayer().getX();
                y = game.getPlayer().getY();
            }
			if(currentImage == 0) {
				if(friendly) {
					int[] index = Collision.laserCollision(this, game.getSpawner().getEnemy());
					if(index != null) {
						for(int i = 0; i < index.length; i++) {
							game.getSpawner().getEnemy().get(index[i]).takeDamage(damage / 20);
						}
					}
				} else {
					if(Collision.checkCollision(this, game.getPlayer())) {
						game.getPlayer().takeDamage(damage / 20);
					}
				}
			}
			if(currentImage == images.length) {
				if(counter >= waitBetweenShots) {
					currentImage = 0;
					counter = 0;
				} else {
					counter++;
				}
			} else {
				if(currentImage == 0 && counter < laserDuration) {
					counter++;
				} else if(currentImage == 0 && counter >= laserDuration) {
					currentImage++;
					counter = 0;
				} else if(currentImage != 0 && counter < changeImageDuration) {
					counter++;
				} else {
					currentImage++;
					counter = 0;
				}
			}
		}
	}
	
	public void draw(android.graphics.Canvas canvas) {
	    Paint paint = new Paint();
	    if(currentImage < images.length) {
			if (friendly) {
				canvas.drawBitmap(images[currentImage], new Rect(0, 0, images[0].getWidth(), images[0].getHeight()), createHitbox((int) x + 31, game.getMain().getScreenHeight() / 10, width, (int) y - 50), null);
			} else {
				canvas.drawBitmap(images[currentImage], new Rect(0, 0, images[0].getWidth(), images[0].getHeight()), createHitbox((int) x, (int) y, width, game.getMain().getScreenHeight() - (int) y), null);
			}
		}
		
		//DRAW HITBOX
		/*
        paint.setColor(Color.RED);
        paint.setStyle(Paint.Style.STROKE);
		canvas.drawRect(this.getBounds(), paint);
		*/

	}
	
	public void setDamage(int damage) {
		this.damage = damage;
	}
	
	public void setX(int x) {
		this.x = x;
	}
	
	public void setY(int y) {
		this.y = y;
	}
	
	public Rect getBounds() {
		if(friendly) {
			return createHitbox((int)game.getPlayer().getX() + 43, 0, width - 20, (int)game.getPlayer().getY() + 5);
		}
		return createHitbox((int)x + 10, (int)y, width - 20, game.getMain().getScreenHeight() - (int)y);
	}

	public Bitmap[] getImage() {
		return images;
	}

	public void setImage(Bitmap[] images) {
		this.images = images;
	}
	
	public void setCurrentImage(int x) {
		currentImage = x;
	}

	public boolean isTargetable() {
		return isTargetable;
	}

	public void takeDamage(double damage) {}

	public void explode() {}

	public Rect createHitbox(int x, int y, int width, int height) {
		return new Rect(x, y, x + width, y + height);
	}
	
}