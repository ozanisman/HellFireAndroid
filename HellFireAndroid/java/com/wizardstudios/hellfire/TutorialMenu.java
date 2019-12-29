package com.wizardstudios.hellfire;


import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

public class TutorialMenu {
	
	private HellFireGame game;
	private Textures texture;
	private Enemy enemy;
	private int stage = 0;

	public TutorialMenu (HellFireGame game, Textures texture) {
		this.game = game;
		this.texture = texture;
		enemy = new Enemy(game.getMain().getScreenWidth() / 2 - 64, game.getMain().getScreenHeight() * 3 / 10 - 64, 64, 64, 10, 10, 60, 5, 0, texture.enemy1, texture.enemyBlast1, game, texture, 1);
	}
	
	public void draw(Canvas canvas) {
		Paint paint = new Paint();
		paint.setColor(Color.RED);

		paint.setTextSize(40);
		if(stage == 0) {
			paint.setTextSize(70);
			canvas.drawBitmap(texture.player, game.getMain().getScreenWidth() / 2 - 64, game.getMain().getScreenHeight() * 4 / 10 - 64, null);
			game.getMainMenu().drawStringCenterX(canvas, paint, "Welcome to HellFire!", 0, game.getMain().getScreenHeight() * 2 / 10);
			paint.setTextSize(40);
			game.getMainMenu().drawStringCenterX(canvas, paint, "This is your ship.", 0, game.getMain().getScreenHeight() * 5 / 10);
			game.getMainMenu().drawStringCenterX(canvas, paint, "Movement is controlled by tapping", 0, game.getMain().getScreenHeight() * 6 / 10);
			game.getMainMenu().drawStringCenterX(canvas, paint, "or dragging on the screen.", 0, game.getMain().getScreenHeight() * 6 / 10 + 50);

		} else if(stage == 1) {
			game.getMainMenu().drawStringCenterX(canvas, paint, "Money is earned every 5 waves", 0, game.getMain().getScreenHeight() * 2 / 10);
			game.getMainMenu().drawStringCenterX(canvas, paint, "and from killing enemies.", 0, game.getMain().getScreenHeight() * 2 / 10 + 50);
			game.getMainMenu().drawStringCenterX(canvas, paint, "Money can be used to upgrade", 0, game.getMain().getScreenHeight() * 3 / 10 + 50);
			game.getMainMenu().drawStringCenterX(canvas, paint, "your ship after dying.", 0, game.getMain().getScreenHeight() * 3 / 10 + 100);
			game.getMainMenu().drawStringCenterX(canvas, paint, "Green upgrades are affordable.", 0, game.getMain().getScreenHeight() * 4 / 10 + 100);
			game.getMainMenu().drawStringCenterX(canvas, paint, "Progress on upgrades is shown in gray:", 0, game.getMain().getScreenHeight() * 4 / 10 + 150);
			paint.setColor(Color.DKGRAY);
			Upgrades upgrades = new Upgrades(game, game.getTexture());
			paint.setTextSize(25);
			paint.setColor(Color.DKGRAY);
			game.drawRectangle(10, game.getMain().getScreenHeight() * 5 / 10 + 150, (int)(3 / ((0.0) + 6) * (game.getMain().getScreenWidth() - 20)), 60, paint);
			game.drawRectangle(10, game.getMain().getScreenHeight() * 6 / 10 + 150, (int)(2 / ((0.0) + 7) * (game.getMain().getScreenWidth() - 20)), 60, paint);

			paint.setColor(Color.RED);
			paint.setStyle(Paint.Style.STROKE);
			game.drawRectangle(10, game.getMain().getScreenHeight() * 5 / 10 + 150, game.getMain().getScreenWidth() - 20, 60, paint);
            paint.setStyle(Paint.Style.FILL);
			canvas.drawText("Blast Damage", 30, 39 + game.getMain().getScreenHeight() * 5 / 10 + 150, paint);
			Rect bounds = new Rect();
			paint.getTextBounds("$" + 1000, 0, ("$" + 1000).length(), bounds);
			canvas.drawText("$" + 1000, game.getMain().getScreenWidth() - bounds.width() - 30, 39 + game.getMain().getScreenHeight() * 5 / 10 + 150, paint);

			paint.setColor(Color.GREEN);
            paint.setStyle(Paint.Style.STROKE);
			game.drawRectangle(10, game.getMain().getScreenHeight() * 6 / 10 + 150, game.getMain().getScreenWidth() - 20, 60, paint);
            paint.setStyle(Paint.Style.FILL);
            canvas.drawText("Blast Firerate", 30, 39 + game.getMain().getScreenHeight() * 6 / 10 + 150, paint);
			paint.getTextBounds("$" + 500, 0, ("$" + 500).length(), bounds);
			canvas.drawText("$" + 500, game.getMain().getScreenWidth() - bounds.width() - 30, 39 + game.getMain().getScreenHeight() * 6 / 10 + 150, paint);

			paint.setStyle(Paint.Style.FILL);
		} else if(stage == 2) {
			canvas.drawBitmap(texture.enemy1, game.getMain().getScreenWidth() / 2 - 64, game.getMain().getScreenHeight() * 3 / 10 - 64, null);
			game.getMainMenu().drawStringCenterX(canvas, paint, "This is a basic enemy.", 0, game.getMain().getScreenHeight() * 2 / 10);
			game.getMainMenu().drawStringCenterX(canvas, paint, "Enemies travel downwards and", 0, game.getMain().getScreenHeight() * 4 / 10);
			game.getMainMenu().drawStringCenterX(canvas, paint, "shoot bullets at set intervals.", 0, game.getMain().getScreenHeight() * 4 / 10 + 50);
			game.getMainMenu().drawStringCenterX(canvas, paint, "Enemies that escape out the", 0, game.getMain().getScreenHeight() * 5 / 10 + 50);
			game.getMainMenu().drawStringCenterX(canvas, paint, "bottom deal damage to you.", 0, game.getMain().getScreenHeight() * 5 / 10 + 100);

		} else if(stage == 3) {
			game.getMainMenu().drawStringCenterX(canvas, paint, "Practice dodging the enemy blasts.", 0, game.getMain().getScreenHeight() * 2 / 10);
			canvas.drawBitmap(texture.player, (int)game.getPlayer().getX(), (int)game.getPlayer().getY(), null);
			game.getPlayer().setTargetable(true);
			if(game.getSpawner().getEnemy().isEmpty()) {
				game.getSpawner().addEnemy(enemy.clone());
				((Enemy)game.getSpawner().getEnemy().get(0)).setTargetable(false);
			}
			game.getSpawner().tick();
			game.getSpawner().draw(canvas);
		} else if(stage == 4) {
			game.getMainMenu().drawStringCenterX(canvas, paint, "Practice shooting while dodging.", 0, game.getMain().getScreenHeight() * 2 / 10);
			canvas.drawBitmap(texture.player, (int)game.getPlayer().getX(), (int)game.getPlayer().getY(), null);
			if(game.getSpawner().getEnemy().isEmpty()) {
				game.getSpawner().addEnemy(enemy.clone());
			}
			((Enemy)game.getSpawner().getEnemy().get(0)).setTargetable(true);
			game.getPlayer().tick();
			game.getSpawner().tick();
			game.getSpawner().draw(canvas);
		} else if(stage == 5) {
			game.getSpawner().restart();
			canvas.drawBitmap(texture.player, (int)game.getPlayer().getX(), (int)game.getPlayer().getY(), null);
			canvas.drawBitmap(texture.playerShield1, (int)game.getPlayer().getX() - 15, (int)game.getPlayer().getY() - 10, null);
			game.getPlayer().tick();
			game.getMainMenu().drawStringCenterX(canvas, paint, "Once the shield is bought in Upgrades", 0, game.getMain().getScreenHeight() * 2 / 10);
			game.getMainMenu().drawStringCenterX(canvas, paint, "an in-game shield bar will be unlocked.", 0, game.getMain().getScreenHeight() * 2 / 10 + 50);
			game.getMainMenu().drawStringCenterX(canvas, paint, "Double tap to activate the shield.", 0, game.getMain().getScreenHeight() * 3 / 10);

			game.getMainMenu().drawStringCenterX(canvas, paint, "While activated, the", 0, game.getMain().getScreenHeight() * 4 / 10);
			game.getMainMenu().drawStringCenterX(canvas, paint, "shield negates all damage.", 0, game.getMain().getScreenHeight() * 4 / 10 + 50);

			game.getMainMenu().drawStringCenterX(canvas, paint, "The cooldown period can be", 0, game.getMain().getScreenHeight() * 5 / 10 + 50);
			game.getMainMenu().drawStringCenterX(canvas, paint, "long so use each charge wisely.", 0, game.getMain().getScreenHeight() * 5 / 10 + 100);

		} else if(stage == 6) {
            paint.setTextSize(35);
			game.getMainMenu().drawStringCenterX(canvas, paint, "Once powerups are unlocked in Upgrades", 0, game.getMain().getScreenHeight() * 2 / 10);
            game.getMainMenu().drawStringCenterX(canvas, paint, "enemies have a chance to drop powerups:", 0, game.getMain().getScreenHeight() * 2 / 10 + 50);

			canvas.drawBitmap(texture.powerup1, game.getMain().getScreenWidth() * 1/20, game.getMain().getScreenHeight() * 3 / 10, null);
            game.getMainMenu().drawStringCenterX(canvas, paint, "Restores 75 health", 0, game.getMain().getScreenHeight() * 3 / 10 + 30);

			canvas.drawBitmap(texture.powerup3, game.getMain().getScreenWidth() * 1/20, game.getMain().getScreenHeight() * 4 / 10, null);
			game.getMainMenu().drawStringCenterX(canvas, paint, "Gives money depending on", 0, game.getMain().getScreenHeight() * 4 / 10);
			game.getMainMenu().drawStringCenterX(canvas, paint, "the number of upgrades bought", 0, game.getMain().getScreenHeight() * 4 / 10 + 50);

			canvas.drawBitmap(texture.powerup4, game.getMain().getScreenWidth() * 1/20, game.getMain().getScreenHeight() * 5 / 10, null);
			game.getMainMenu().drawStringCenterX(canvas, paint, "Increases player damage by 50%", 0, game.getMain().getScreenHeight() * 5 / 10 + 30);

			canvas.drawBitmap(texture.powerup5, game.getMain().getScreenWidth() * 1/20, game.getMain().getScreenHeight() * 6 / 10, null);
			game.getMainMenu().drawStringCenterX(canvas, paint, "Freezes all enemies", 0, game.getMain().getScreenHeight() * 6 / 10);
			game.getMainMenu().drawStringCenterX(canvas, paint, "currently on screen", 0, game.getMain().getScreenHeight() * 6 / 10 + 50);

			canvas.drawBitmap(texture.powerup6, game.getMain().getScreenWidth() * 1/20, game.getMain().getScreenHeight() * 7 / 10, null);
			game.getMainMenu().drawStringCenterX(canvas, paint, "Reduces enemy damage by", 0, game.getMain().getScreenHeight() * 7 / 10);
			game.getMainMenu().drawStringCenterX(canvas, paint, "50% but does not stack", 0, game.getMain().getScreenHeight() * 7 / 10 + 50);

			game.getMainMenu().drawStringCenterX(canvas, paint, "Powerups have a duration of 10 seconds.", 0, game.getMain().getScreenHeight() * 8 / 10 + 50);

		} else if(stage == 7) {
			game.getMainMenu().drawStringCenterX(canvas, paint, "You've reached the end of the tutorial!", 0, game.getMain().getScreenHeight() * 3 / 10);
			game.getMainMenu().drawStringCenterX(canvas, paint, "Press Launch to play the game.", 0, game.getMain().getScreenHeight() * 4 / 10);
			game.getMainMenu().drawStringCenterX(canvas, paint, "Press Back to review the tutorial.", 0, game.getMain().getScreenHeight() * 5 / 10);

		}

		paint.setTextSize(50);
		paint.setColor(Color.RED);
		game.drawRectangle(10, game.getMain().getScreenHeight() - 80, 220, 70, paint);
		paint.setColor(Color.BLACK);
		if(stage == 0) {
			canvas.drawText("MENU", 50, game.getMain().getScreenHeight() -27, paint);
		} else {
			canvas.drawText("BACK", 50, game.getMain().getScreenHeight() -27, paint);
		}
		paint.setColor(Color.RED);
		game.drawRectangle(game.getMain().getScreenWidth() - 230, game.getMain().getScreenHeight() - 80, 220, 70, paint);
		paint.setColor(Color.BLACK);
		if(stage < 7) {
			canvas.drawText("NEXT", game.getMain().getScreenWidth() - 180, game.getMain().getScreenHeight() -27, paint);
		} else {
			canvas.drawText("LAUNCH", game.getMain().getScreenWidth() - 220, game.getMain().getScreenHeight() - 27, paint);
		}

	}
	
	public void setStage(int stage) {
		this.stage = stage;
	}
	
	public int getStage() {
		return stage;
	}
	
}
