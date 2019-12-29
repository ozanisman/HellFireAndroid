package com.wizardstudios.hellfire;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

public class Blast extends GameObject implements Entity {
	
	private int damage;
	private double velocityX;
	private double velocityY;
	private boolean friendly;
	private Textures texture;
	private HellFireGame game;
	private boolean explode = false;
	private int explodeCounter = 0;
	private int counter = 0;
	private int shootType;
	private Bitmap image;
	private boolean isTargetable = true;
	private Bitmap[] explosion;
	private boolean canMove = true;
	private int formationCounter = 1000;
	
	public Blast(int damage, double x, double y, Bitmap image, Textures texture, int width, int height, double velX, double velY, boolean friendly, HellFireGame game, int shootType) {
		super(x, y, width, height);
		this.texture = texture;
		this.damage = damage;
		this.velocityX = velX;
		this.velocityY = velY;
		this.image = image;
		this.friendly = friendly;
		this.game = game;
		this.shootType = shootType;
		explosion = texture.bulletExplosion;
	}
	
	public void tick() {
		if(!game.getPlayer().getPause()) {
			if(explode) {
				if(explodeCounter >= explosion.length) {
					game.getSpawner().removeBullet(this);
				} else if(counter == 4) {
					image = explosion[explodeCounter];
					explodeCounter++;
					counter = 0;
				} else {
					counter++;
				}
			} else {
				if(canMove) {
					y += velocityY;
					if(shootType == 2) {

						if(formationCounter > 1015) {
							formationCounter = 0;
						} else if(formationCounter >= 1000) {
							x += 5;
						} else if(formationCounter < 30) {
                            x += -5;
                        } else if(formationCounter < 60) {
                            x += 5;
                        } else {
							formationCounter = 0;
						}
						formationCounter++;

					} else if(shootType == 3) {

						x += -1;

					} else if(shootType == 4) {

						x += 1;

					} else if(shootType == 5) {

					    if(getX() < game.getPlayer().getX() + 34) {
					        x += 3;
                        } else if(getX() > game.getPlayer().getX() + 38) {
					        x += -3;
                        }

					} else if(shootType == 6) {

						if(y > game.getMain().getScreenHeight() / 2) {
							game.getSpawner().addBullet(new Blast(damage, x, y, image, texture, image.getWidth(), image.getHeight(), 0, 10, false, game, 4));
							game.getSpawner().addBullet(new Blast(damage, x, y, image, texture, image.getWidth(), image.getHeight(), 0, 10, false, game, 3));
							game.getSpawner().addBullet(new Blast(damage, x, y, image, texture, image.getWidth(), image.getHeight(), 0, 10, false, game, 1));
							explode();
						}

						if(getX() < game.getPlayer().getX() + 34) {
							x += 3;
						} else if(getX() > game.getPlayer().getX() + 38) {
							x += -3;
						}
					}

					formationCounter++;
				}

				if(friendly) {
					if(game.getSpawner().getBoss().isEmpty()) {
						int index = Collision.checkCollision(this, game.getSpawner().getEnemy());
						if(index > -1) {
							game.getSpawner().getEnemy().get(index).takeDamage(damage);
							game.getSpawner().removeBullet(this);
						}
					} else {
						if(Collision.checkBossCollision(this, game.getSpawner().getBoss())) {
							game.getSpawner().getBoss().get(0).takeDamage(damage);
							game.getSpawner().removeBullet(this);
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
		this.explode = true;
		this.image = explosion[0];
		explodeCounter++;
		this.x -= 50;
	}
	
	public Rect getBounds() {
		if(image == texture.blast1 || image == texture.blast2 || image == texture.blast3 || image == texture.blast4 || image == texture.blast5) {
			return createHitbox((int)x + 7, (int)y + 5, width - 15, height - 5);
		}
		if(image == texture.enemyBlast1 || image == texture.enemyBlast2 || image == texture.enemyBlast3 || image == texture.enemyBlast4 || image == texture.enemyBlast5 || image == texture.enemyBlast6 || image == texture.enemyBlast7 || image == texture.enemyBlast8) {
			return createHitbox((int)x + 7, (int)y + 5, width - 12, height - 8);
		}
		return createHitbox((int)x, (int)y, width, height);
	}

	public boolean isTargetable() {
		return isTargetable;
	}

	public void takeDamage(double damage) {}

	public Rect createHitbox(int x, int y, int width, int height) {
		return new Rect(x, y, x + width, y + height);
	}
	
}