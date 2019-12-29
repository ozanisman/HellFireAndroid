package com.wizardstudios.hellfire;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

public class UpgradesMenu {
	
	private HellFireGame game;
	private int state = 0;
	private Upgrade[] upgrades = new Upgrade[16];
	/*
	blastDamage
	blastFirerate
	missileDamage
	missileFirerate
	missileExplosionRadius
	missileExplosionDamage
	laserDamage
	dualShot
	shieldCooldown
	shieldDuration
	shipHealth
	damageReduction
	powerupDrop
	damageBonus
	moneyBonus
	startLevel
	*/
	
	public UpgradesMenu(HellFireGame game) {
		this.game = game;
		tick();
	}
	
	public void tick() {
		upgrades[0] = game.getPlayer().getUpgrades().getBlastDamage();
		upgrades[1] = game.getPlayer().getUpgrades().getBlastFirerate();
		upgrades[2] = game.getPlayer().getUpgrades().getMissileDamage();
		upgrades[3] = game.getPlayer().getUpgrades().getMissileFirerate();
		upgrades[4] = game.getPlayer().getUpgrades().getMissileExplosionDamage();
		upgrades[5] = game.getPlayer().getUpgrades().getMissileExplosionRadius();
		upgrades[6] = game.getPlayer().getUpgrades().getLaserDamage();
		upgrades[7] = game.getPlayer().getUpgrades().getDualShot();
		upgrades[8] = game.getPlayer().getUpgrades().getShieldCooldown();
		upgrades[9] = game.getPlayer().getUpgrades().getShieldDuration();
		upgrades[10] = game.getPlayer().getUpgrades().getShipHealth();
		upgrades[11] = game.getPlayer().getUpgrades().getDamageReduction();
		upgrades[12] = game.getPlayer().getUpgrades().getPowerupDrop();
		upgrades[13] = game.getPlayer().getUpgrades().getDamageBonus();
		upgrades[14] = game.getPlayer().getUpgrades().getMoneyBonus();
		upgrades[15] = game.getPlayer().getUpgrades().getStartLevel();
	}
	
	public void draw(Canvas canvas) {
		Paint paint = new Paint();
		paint.setTextSize(80);
		paint.setColor(Color.RED);

		game.getMainMenu().drawStringCenterX(canvas, paint, "UPGRADES", 0, 95);
		paint.setTextSize(50);
		game.getMainMenu().drawStringCenterX(canvas, paint, "$" + game.getPlayer().getMoney(), 0, game.getMain().getScreenHeight()  - 27);

		game.drawRectangle(game.getMain().getScreenWidth() - 230, game.getMain().getScreenHeight() - 80, 220, 70, paint);
		game.drawRectangle(10, game.getMain().getScreenHeight() - 80, 220, 70, paint);
		paint.setColor(Color.BLACK);
		canvas.drawText("LAUNCH", game.getMain().getScreenWidth() - 220, game.getMain().getScreenHeight() - 27, paint);
		canvas.drawText("MENU", 50, game.getMain().getScreenHeight() - 27, paint);
		
		//Upgrade Buttons
		paint.setTextSize(25);
		paint.setColor(Color.RED);

		if(state == 0) { /////////////////////////////////////////// STATE 0 \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
			for(int i = 0; i < 8; i++) {
				paint.setColor(Color.DKGRAY);
				game.drawRectangle(10, 200 + game.getMain().getScreenHeight() / 12 * i, (int)(upgrades[i].getCurrentUpgradeNum() / ((0.0) + upgrades[i].getTotalUpgradeNum()) * (game.getMain().getScreenWidth() - 20)), 60, paint);
				if(upgrades[i].isPurchasable()) {
					paint.setColor(Color.GREEN);
				} else {
					paint.setColor(Color.RED);
				}
				paint.setStyle(Paint.Style.STROKE);
				game.drawRectangle(10, 200 + game.getMain().getScreenHeight() / 12 * i, game.getMain().getScreenWidth() - 20, 60, paint);
				paint.setStyle(Paint.Style.FILL);
				canvas.drawText(upgrades[i].getDescription(), 30, 239 + game.getMain().getScreenHeight() / 12 * i, paint);
				Rect bounds = new Rect();
				if(upgrades[i].getPrice() != 0) {
					paint.getTextBounds("$" + upgrades[i].getPrice(), 0, ("$" + upgrades[i].getPrice()).length(), bounds);
					canvas.drawText("$" + upgrades[i].getPrice(), game.getMain().getScreenWidth() - bounds.width() - 30, 239 + game.getMain().getScreenHeight() / 12 * i, paint);
				}
			}
			
		} else if(state == 1) { /////////////////////////////// STATE 1 \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
			for(int i = 8; i < 11; i++) {
                paint.setColor(Color.DKGRAY);
                game.drawRectangle(10, 200 + game.getMain().getScreenHeight() / 12 * (i - 8), (int)(upgrades[i].getCurrentUpgradeNum() / ((0.0) + upgrades[i].getTotalUpgradeNum()) * (game.getMain().getScreenWidth() - 20)), 60, paint);
                if(upgrades[i].isPurchasable()) {
                    paint.setColor(Color.GREEN);
                } else {
                    paint.setColor(Color.RED);
                }
                paint.setStyle(Paint.Style.STROKE);
                game.drawRectangle(10, 200 + game.getMain().getScreenHeight() / 12 * (i - 8), game.getMain().getScreenWidth() - 20, 60, paint);
                paint.setStyle(Paint.Style.FILL);
                canvas.drawText(upgrades[i].getDescription(), 30, 239 + game.getMain().getScreenHeight() / 12 * (i - 8), paint);
                Rect bounds = new Rect();
                if(upgrades[i].getPrice() != 0) {
                    paint.getTextBounds("$" + upgrades[i].getPrice(), 0, ("$" + upgrades[i].getPrice()).length(), bounds);
                    canvas.drawText("$" + upgrades[i].getPrice(), game.getMain().getScreenWidth() - bounds.width() - 30, 239 + game.getMain().getScreenHeight() / 12 * (i - 8), paint);
                }
			}

		} else if(state == 2) { ////////////////////////////////////////////// STATE 2 \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
			for(int i = 11; i < upgrades.length; i++) {
				paint.setColor(Color.DKGRAY);
				game.drawRectangle(10, 200 + game.getMain().getScreenHeight() / 12 * (i - 11), (int)(upgrades[i].getCurrentUpgradeNum() / ((0.0) + upgrades[i].getTotalUpgradeNum()) * (game.getMain().getScreenWidth() - 20)), 60, paint);
				if(upgrades[i].isPurchasable()) {
					paint.setColor(Color.GREEN);
				} else {
					paint.setColor(Color.RED);
				}
				paint.setStyle(Paint.Style.STROKE);
				game.drawRectangle(10, 200 + game.getMain().getScreenHeight() / 12 * (i - 11), game.getMain().getScreenWidth() - 20, 60, paint);
				paint.setStyle(Paint.Style.FILL);
				canvas.drawText(upgrades[i].getDescription(), 30, 239 + game.getMain().getScreenHeight() / 12 * (i - 11), paint);
				Rect bounds = new Rect();
				if(upgrades[i].getPrice() != 0) {
					paint.getTextBounds("$" + upgrades[i].getPrice(), 0, ("$" + upgrades[i].getPrice()).length(), bounds);
					canvas.drawText("$" + upgrades[i].getPrice(), game.getMain().getScreenWidth() - bounds.width() - 30, 239 + game.getMain().getScreenHeight() / 12 * (i - 11), paint);
				}
			}
		}

		paint.setTextSize(50);
		paint.setColor(Color.DKGRAY);
		game.drawRectangle(0, 115, game.getMain().getScreenWidth(), 60, paint); //weapons

		paint.setColor(checkState(1));
		game.getMainMenu().drawStringCenterX(canvas, paint, "SHIP", -game.getMain().getScreenWidth() * 30 / 100, 165);
		
		paint.setColor(checkState(0));
		game.getMainMenu().drawStringCenterX(canvas, paint, "WEAPONS", 0, 165); //DRAWN IN MIDDLE BUT DEFAULT


		paint.setColor(checkState(2));
        game.getMainMenu().drawStringCenterX(canvas, paint, "MISC", +game.getMain().getScreenWidth() * 30 / 100, 165);

        paint.setColor(Color.BLACK);
        canvas.drawLine(game.getMain().getScreenWidth() * 31 / 100, 115, game.getMain().getScreenWidth() * 31 / 100, 175, paint);
		canvas.drawLine(game.getMain().getScreenWidth() * 69 / 100, 115, game.getMain().getScreenWidth() * 69 / 100, 175, paint);

	}
	
	public void setState(int state) {
		this.state = state;
	}
	
	public int getState() {
		return state;
	}
	
	public int checkState(int state) {
		if(this.state == state) {
			return Color.GREEN;
		}
		return Color.RED;
	}
	
	public Upgrade[] getCurrentUpgrades() {
		return upgrades;
	}
}
