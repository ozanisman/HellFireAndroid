package com.wizardstudios.hellfire;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

public class SettingsMenu {
	
	private HellFireGame game;
	public boolean musicMuted;
	
	public SettingsMenu(HellFireGame game) {
		this.game = game;
		musicMuted = game.getMusicMuted();
	}
	
	public void draw(Canvas canvas) {
		Paint paint = new Paint();

		paint.setColor(Color.RED);
		paint.setTextSize(50);
		Rect bounds = new Rect();
		paint.getTextBounds("AUC - Daniele Maddaluno", 0, "AUC - Daniele Maddaluno".length(), bounds);
		game.drawRectangle(game.getMain().getScreenWidth() / 2 - bounds.width() * 52/100, (game.getMain().getScreenHeight() * 6 / 20) - bounds.height() * 15/10, bounds.width() * 21/20, 160, paint);
		game.drawRectangle(game.getMain().getScreenWidth() / 2 - bounds.width() * 52/100, (game.getMain().getScreenHeight() * 9 / 20) - bounds.height() * 15/10, bounds.width() * 21/20, 200, paint);
		game.drawRectangle(game.getMain().getScreenWidth() / 2 - bounds.width() * 52/100, (game.getMain().getScreenHeight() * 13 / 20) - bounds.height() * 15/10, bounds.width() * 21/20, 160, paint);
		paint.setColor(Color.BLACK);
		game.getMainMenu().drawStringCenterX(canvas, paint, "Programming by:", 0, game.getMain().getScreenHeight() * 6 / 20);
		game.getMainMenu().drawStringCenterX(canvas, paint, "Oz Anisman", 0, game.getMain().getScreenHeight() * 7 / 20);
		game.getMainMenu().drawStringCenterX(canvas, paint, "Assets used from:", 0, game.getMain().getScreenHeight() * 9 / 20);
		game.getMainMenu().drawStringCenterX(canvas, paint, "AUC - Daniele Maddaluno", 0, game.getMain().getScreenHeight() * 10 / 20);
		game.getMainMenu().drawStringCenterX(canvas, paint, "Art - Millionth Vector", 0, game.getMain().getScreenHeight() * 11 / 20);
		game.getMainMenu().drawStringCenterX(canvas, paint, "Music by:", 0, game.getMain().getScreenHeight() * 13 / 20);
		game.getMainMenu().drawStringCenterX(canvas, paint, "Blarrgensnorf", 0, game.getMain().getScreenHeight() * 14 / 20);




		paint.setTextSize(80);
		paint.setColor(Color.RED);
		game.getMainMenu().drawStringCenterX(canvas, paint, "CREDITS", 0, 150);


		//game.drawRectangle(game.getMain().getScreenWidth() - 230, game.getMain().getScreenHeight() - 80, 220, 70, paint);
		game.drawRectangle(10, game.getMain().getScreenHeight() - 80, 220, 70, paint);

		paint.setColor(Color.BLACK);
		paint.setTextSize(50);
		canvas.drawText("MENU", 50, game.getMain().getScreenHeight() - 27, paint);

	}
	
}
