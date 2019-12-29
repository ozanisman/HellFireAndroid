package com.wizardstudios.hellfire;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

public class Player extends GameObject implements Entity {
	
	//Upgrade Variables
	private Upgrades upgrades;
	private int maxHealth;
	private int health;
	private int shieldDuration;
	private int currentShieldTimer;
	private int shieldCooldown;
	private boolean shieldActive;
	private boolean shieldReady;
	private int blastDamage;
	private int missileDamage;
	private int missileExplosionDamage;
	private int missileExplosionRadius;
	private double damageBonus = 1;
	private double moneyBonus = 1;
	private int blastShootingSpeed;
	private int missileShootingSpeed;
	
	//Other
	private int[] data;
	private int money = 0;
	private Bitmap playerImage;
	private Bitmap blastImage;
	private Bitmap missileImage;
	private Laser laser;
	private HellFireGame game;
	private Textures texture;
	private int counter = 0;
	private boolean explode = false;
	private int explodeCounter;
	private boolean isTargetable = true;
	private int alpha = 255;
	private int gotHit = -1;
	public boolean canMove = true;
	private boolean gameOver = false;
	private boolean pause = false;
	private int escapeDamage = 5;
	private double excessDamage = 0;
	private int blastShootingCounter = 0;
	private int missileShootingCounter = 0;

	
	public Player(double x, double y, int width, int height, HellFireGame game, Textures texture) {
		super(x, y, width, height);
		this.game = game;
		this.texture = texture;
		upgrades = new Upgrades(game, texture);
		laser = new Laser(0, (int)getX() + 18, (int)getY() - 600, upgrades.getLaserType(), texture, texture.laser1[0].getWidth(), texture.laser1[0].getHeight(), true, game);
		playerImage = texture.player;
		upgrade();
		data = new int[17];
	}
	
	public void tick() {
        if(!pause) {
			if(explode) {
				canMove = false;
				if(explodeCounter >= texture.shipExplosion.length) {
					game.setStateUpgrade();
				} else if(counter == 2) {
					playerImage = texture.shipExplosion[explodeCounter];
					explodeCounter++;
					counter = 0;
				} else {
					counter++;
				}
			} else {
				laser.setX((int)getX() + 18);
				laser.setY((int)getY() - 600);
				if(blastShootingCounter % blastShootingSpeed == 0) {
					if(upgrades.getDualShot().getUpgradeValue() > 0) {
						game.getSpawner().addBullet(new Blast(blastDamage, getX() + width / 2 - blastImage.getWidth() / 2 + 3, getY() + 5, blastImage, texture, blastImage.getWidth(), blastImage.getHeight(), 0, -12, true, game, 1));
						game.getSpawner().addBullet(new Blast(blastDamage, getX() + width / 2 - blastImage.getWidth() - 3, getY() + 5, blastImage, texture, blastImage.getWidth(), blastImage.getHeight(), 0, -12, true, game, 1));
					} else {
						game.getSpawner().addBullet(new Blast(blastDamage, getX() + width / 2 - blastImage.getWidth() * 3 / 4, getY() - 8, blastImage, texture, blastImage.getWidth(), blastImage.getHeight(), 0, -12, true, game, 1));
					}
				}
				if(missileShootingSpeed != 0 && missileShootingCounter % missileShootingSpeed == 0) {
					if(upgrades.getDualShot().getUpgradeValue() > 2) {
						game.getSpawner().addBullet(new Missile(missileDamage, missileExplosionDamage, missileExplosionRadius, getX() + width / 2, getY() + 40, missileImage, texture, 20, 20, 0, -5, true, game));
						game.getSpawner().addBullet(new Missile(missileDamage, missileExplosionDamage, missileExplosionRadius, getX() + width / 2 - missileImage.getWidth() * 5/3 + 3, getY() + 40, missileImage, texture, 20, 20, 0, -5, true, game));
					} else {
						game.getSpawner().addBullet(new Missile(missileDamage, missileExplosionDamage, missileExplosionRadius, getX() + width / 2 - missileImage.getWidth() * 3/4, getY() - 8, missileImage, texture, 20, 20, 0, -5, true, game));

					}
				}
				if(counter % game.getFPS() == 0) {
					currentShieldTimer++;
				}
				if(counter == Integer.MAX_VALUE) {
					counter = 0;
				}
				counter++;
				blastShootingCounter++;
				missileShootingCounter++;
			}
		}
	}
	
	public void draw(Canvas canvas) {
		Paint paint = new Paint();
		if(upgrades.getLaserDamage().getUpgradeValue() != 0) {
			laser.tick();
			laser.draw(canvas);
		}

		paint.setTextSize(50);

		if(gameOver) {
			paint.setColor(Color.RED);
			game.getMainMenu().drawStringCenterX(canvas, paint, "YOU WIN!", 0, game.getMain().getScreenHeight() * 3 / 10);
			game.getMainMenu().drawStringCenterX(canvas, paint, "Thanks for playing!", 0, game.getMain().getScreenHeight() * 4 / 10);
			game.getMainMenu().drawStringCenterX(canvas, paint, "Stay tuned for updates!", 0, game.getMain().getScreenHeight() * 5 / 10);

		}
		paint.setColor(Color.DKGRAY);
		canvas.drawRect(0, 0, game.getMain().getScreenWidth(), game.getMain().getScreenHeight() / 10, paint);
		canvas.drawBitmap(playerImage, (float)x, (float)y, null);
		if(shieldActive) {
			canvas.drawBitmap(texture.playerShield1, (int)game.getPlayer().getX() - 15, (int)game.getPlayer().getY() - 10, null);
		}
		canvas.drawBitmap(game.getPauseImage(), new Rect(0, 0, game.getPauseImage().getWidth(), game.getPauseImage().getHeight()), new Rect(game.getMain().getScreenWidth() - 105, 5, game.getMain().getScreenWidth() - 5, 105), null);


		paint.setColor(Color.RED);

		if(gotHit != -1) {
			if(alpha >= 0 && alpha <= 255) {
				if(gotHit == 0) {
					paint.setColor(Color.RED);
				} else {
					paint.setColor(Color.CYAN);
				}
				paint.setAlpha(alpha);
				game.drawRectangle(0, game.getMain().getScreenHeight() / 10, game.getMain().getScreenWidth(), 10, paint); //TOP
				game.drawRectangle(0, game.getMain().getScreenHeight() / 10 + 10, 10, game.getMain().getScreenHeight() + 100, paint); //LEFT
				game.drawRectangle(10, game.getMain().getScreenHeight() - 10, game.getMain().getScreenWidth() - 20, 10, paint); //BOTTOM
				game.drawRectangle(game.getMain().getScreenWidth() - 10, game.getMain().getScreenHeight() / 10 + 10, 10, game.getMain().getScreenHeight() + 100, paint); //RIGHT
				if(!pause) {
					alpha -= 5;
				}
			}
		}
		paint = new Paint();
        paint.setColor(Color.WHITE);
        game.drawRectangle(game.getMain().getScreenWidth() / 40, game.getMain().getScreenHeight() / 40, game.getMain().getScreenWidth() / 3, 50, paint);
        game.drawRectangle(game.getMain().getScreenWidth() * 38 / 100, game.getMain().getScreenHeight() / 40, game.getMain().getScreenWidth() / 3, 50, paint);

        paint.setColor(Color.RED);
		paint.setTextSize(27);
		game.drawRectangle(game.getMain().getScreenWidth() / 40, game.getMain().getScreenHeight() / 40, (int)(health/(0.0 + maxHealth) * game.getMain().getScreenWidth() / 3), 50, paint);
		canvas.drawText("Health: " + health, game.getMain().getScreenWidth() / 10, game.getMain().getScreenHeight() / 11, paint);
		if(shieldActive && shieldDuration != 0) {
			if(shieldDuration - currentShieldTimer == 0) {
				shieldActive = false;
				currentShieldTimer = 0;
			} else {
				paint.setColor(Color.GREEN);
				game.drawRectangle(game.getMain().getScreenWidth() * 38 / 100, game.getMain().getScreenHeight() / 40, game.getMain().getScreenWidth() / 3 - ((int)(currentShieldTimer / (0.0 + shieldDuration) * game.getMain().getScreenWidth() / 3)), 50, paint);
				canvas.drawText("Shield Active For: " + (shieldDuration - currentShieldTimer), game.getMain().getScreenWidth() * 37 / 100, game.getMain().getScreenHeight() / 11, paint);
			}
			shieldReady = false;
		} else if(shieldCooldown != 0 && currentShieldTimer < shieldCooldown) {
			paint.setColor(Color.CYAN);
			game.drawRectangle(game.getMain().getScreenWidth() * 38 / 100, game.getMain().getScreenHeight() / 40, (int)(currentShieldTimer/(0.0 + shieldCooldown) * game.getMain().getScreenWidth() / 3), 50, paint);
			canvas.drawText("Shield Charged In: " + (shieldCooldown - currentShieldTimer), game.getMain().getScreenWidth() * 37 / 100, game.getMain().getScreenHeight() / 11, paint);
		} else if(shieldCooldown != 0 && currentShieldTimer >= shieldCooldown) {
			shieldReady = true;
			paint.setColor(Color.GREEN);
			game.drawRectangle(game.getMain().getScreenWidth() * 38 / 100, game.getMain().getScreenHeight() / 40, game.getMain().getScreenWidth() / 3, 50, paint);
			canvas.drawText("Shield Charged!", game.getMain().getScreenWidth() * 37 / 100, game.getMain().getScreenHeight() / 11, paint);
		}

		paint.setColor(Color.RED);
		paint.setTextSize(27);


		paint.setColor(Color.RED);
		canvas.drawText("$" + money, game.getMain().getScreenWidth() * 72 / 100, game.getMain().getScreenHeight() / 19, paint);
		game.getMainMenu().drawStringCenterX(canvas, paint, "Next enemy escape deals " + escapeDamage + " damage", 0, 25);


		 //DRAW HITBOX
		/*
		paint.setColor(Color.RED);
        paint.setStyle(Paint.Style.STROKE);
		for(int i = 0; i < getPlayerBounds().length; i++) {
			canvas.drawRect(getPlayerBounds()[i], paint);
		}
		*/

		if(pause) {
			paint = new Paint();
			paint.setColor(Color.DKGRAY);
			game.drawRectangle(game.getMain().getScreenWidth() / 6, game.getMain().getScreenHeight() * 3 / 10, game.getMain().getScreenWidth() * 2 / 3, game.getMain().getScreenHeight() * 45 / 100, paint);
			paint.setColor(Color.RED);
			paint.setTextSize(70);
			Rect bounds = new Rect();
			paint.getTextBounds("TUTORIAL", 0, "TUTORIAL".length(), bounds);
			game.drawRectangle(game.getMain().getScreenWidth() / 2 - bounds.width() * 2/3, (game.getMain().getScreenHeight() * 4 / 10) - bounds.height() * 15/10, bounds.width() * 4/3, 100, paint);
			game.drawRectangle(game.getMain().getScreenWidth() / 2 - bounds.width() * 2/3, (game.getMain().getScreenHeight() * 5 / 10) - bounds.height() * 15/10, bounds.width() * 4/3, 100, paint);
			game.drawRectangle(game.getMain().getScreenWidth() / 2 - bounds.width() * 2/3, (game.getMain().getScreenHeight() * 6 / 10) - bounds.height() * 15/10, bounds.width() * 4/3, 100, paint);
			game.drawRectangle(game.getMain().getScreenWidth() / 2 - bounds.width() * 2/3, (game.getMain().getScreenHeight() * 7 / 10) - bounds.height() * 15/10, bounds.width() * 4/3, 100, paint);
			paint.setColor(Color.BLACK);
			game.getMainMenu().drawStringCenterX(canvas, paint, "RESUME", 0, game.getMain().getScreenHeight() * 4 / 10);
			game.getMainMenu().drawStringCenterX(canvas, paint, "UPGRADES", 0, game.getMain().getScreenHeight() * 5 / 10);
			game.getMainMenu().drawStringCenterX(canvas, paint, "CREDITS", 0, game.getMain().getScreenHeight() * 6 / 10);
			game.getMainMenu().drawStringCenterX(canvas, paint, "MENU", 0, game.getMain().getScreenHeight() * 7 / 10);
		}

	}
	
	public void upgrade() {
		maxHealth = (int) upgrades.getShipHealth().getUpgradeValue();
		health = maxHealth;
		damageBonus = (int) (upgrades.getDamageBonus().getUpgradeValue());
		blastDamage = (int)(upgrades.getBlastDamage().getUpgradeValue() * damageBonus);
		blastShootingSpeed = (int) upgrades.getBlastFirerate().getUpgradeValue();
		blastImage = upgrades.getBlastType();
		missileDamage = (int)(upgrades.getMissileDamage().getUpgradeValue() * damageBonus);
		missileShootingSpeed = (int) upgrades.getMissileFirerate().getUpgradeValue();
		missileExplosionRadius = (int) upgrades.getMissileExplosionRadius().getUpgradeValue();
		missileExplosionDamage = (int)(upgrades.getMissileExplosionDamage().getUpgradeValue() * damageBonus);
		missileImage = upgrades.getMissileType();
		laser.setDamage((int)(upgrades.getLaserDamage().getUpgradeValue() * damageBonus));
		laser.setImage(upgrades.getLaserType());
		shieldCooldown = (int) upgrades.getShieldCooldown().getUpgradeValue();
		currentShieldTimer = 0;
		shieldDuration = (int) upgrades.getShieldDuration().getUpgradeValue();
		shieldActive = false;
		moneyBonus = upgrades.getMoneyBonus().getUpgradeValue();
		damageBonus = upgrades.getDamageBonus().getUpgradeValue();
	}
		
	public void restart() {
		blastShootingCounter = 0;
		missileShootingCounter = 0;
		health = maxHealth;
		currentShieldTimer = 0;
		shieldActive = false;
		canMove = true;
		explode = false;
		isTargetable = true;
		damageBonus = 1;
		counter = 0;
		explodeCounter = 0;
		playerImage= texture.player;
		gotHit = -1;
		escapeDamage = 5;
		pause = false;
		gameOver = false;
	}
	
	public void setX(double x) {
		this.x = x;
	}
	
	public void setY(double y) {
		this.y = y;
	}
	
	public void explode() {
		counter = 0;
		this.explode = true;
		isTargetable = false;
	}

	public boolean isTargetable() {
		return isTargetable;
	}
	
	public void setImage(Bitmap image) {
		this.playerImage= image;
	}
	
	public Rect[] getPlayerBounds() {
		Rect[] rectangles = new Rect[] {createHitbox((int)x + 4, (int)y + 60, width * 4/5, height - 90), createHitbox((int)x + 45, (int)y + 5, width - 107, height - 70)};
		return rectangles;
	}
	public void takeDamage(double damage) {
		if(game.checkState("tutorial")) {
			return;
		}
		this.alpha = 255;
		if(shieldActive) {
			gotHit = 1;
			return;
		}


		damage = damage * (1 - upgrades.getDamageReduction().getUpgradeValue());


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
		gotHit = 0;
		if(health <= 0) {
			health = 0;
			explode();
		}
	}
	
	public void money(int value) {
		if(value > 0) {
			value *= moneyBonus;
		}
		this.money += value;
		data[0] = money;
	}
	
	public int getMoney() {
		return money;
	}
	
	public void gameOver(boolean boo) {
		gameOver = boo;
	}
	
	public Upgrades getUpgrades() {
		return upgrades;
	}
	
	public void takeEscapeDamage() {
		this.takeDamage(escapeDamage);
		escapeDamage += 5;
	}
	
	public boolean getPause() {
		return pause;
	}
	
	public void setPause(boolean pause) {
		this.pause = pause;	
	}
	
	public void increaseHealth(int increase) {
		health += increase;
		if(health > maxHealth) {
			health = maxHealth;
		}
	}

	public void setTargetable(boolean b) {
		isTargetable = b;
	}
	
	public void increaseDamage(double percent) {
		damageBonus += percent;
	}
	
	public boolean getShieldReady() {
		return shieldReady;
	}

	public int getMaxHealth() {
		return maxHealth;
	}
	
	public void setShieldActive() {
		shieldActive = true;
		shieldReady = false;
		currentShieldTimer = 0;
	}

	public double getY() {
		return this.y;
	}

	public double getX() {
		return this.x;
	}

	public Rect getBounds() {
		return null;
	}

	public Rect createHitbox(int x, int y, int width, int height) {
		return new Rect(x, y, x + width, y + height);
	}

	public void upgradeBought(int index) {
		data[index]++;
	}

	public void updatePlayer(int[] dataArray) {
		//money = dataArray[0];
		money = 100000;
		data[0] = dataArray[0];
		for(int i = 0; i < dataArray[1]; i++) {
			upgrades.upgradeBlastDamage();
			upgrades.upgradeBlastType();
		}
		for(int i = 0; i < dataArray[2]; i++) {
			upgrades.upgradeBlastFirerate();
		}
		for(int i = 0; i < dataArray[3]; i++) {
			upgrades.upgradeMissileDamage();
			upgrades.upgradeMissileType();
		}
		for(int i = 0; i < dataArray[4]; i++) {
			upgrades.upgradeMissileFirerate();
		}
		for(int i = 0; i < dataArray[5]; i++) {
			upgrades.upgradeMissileExplosionDamage();
		}
		for(int i = 0; i < dataArray[6]; i++) {
			upgrades.upgradeMissileExplosionRadius();
		}
		for(int i = 0; i < dataArray[7]; i++) {
			upgrades.upgradeLaserDamage();
			upgrades.upgradeLaserType();
		}
		for(int i = 0; i < dataArray[8]; i++) {
			upgrades.upgradeDualShot();
		}
		for(int i = 0; i < dataArray[9]; i++) {
			upgrades.upgradeShieldCooldown();
		}
		for(int i = 0; i < dataArray[10]; i++) {
			upgrades.upgradeShieldDuration();
		}
		for(int i = 0; i < dataArray[11]; i++) {
			upgrades.upgradeShipHealth();
		}
		for(int i = 0; i < dataArray[12]; i++) {
			upgrades.upgradeDamageReduction();
		}
		for(int i = 0; i < dataArray[13]; i++) {
			upgrades.upgradePowerupDrop();
		}
		for(int i = 0; i < dataArray[14]; i++) {
			upgrades.upgradeDamageBonus();
		}
		for(int i = 0; i < dataArray[15]; i++) {
			upgrades.upgradeMoneyBonus();
		}
		for(int i = 0; i < dataArray[16]; i++) {
			upgrades.upgradeStartLevel();
		}

	}

	public void savePlayerData() {
        game.savePlayerData(data);
    }

	
}