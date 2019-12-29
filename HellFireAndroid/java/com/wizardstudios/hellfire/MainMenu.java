package com.wizardstudios.hellfire;


import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

public class MainMenu {
	
	private HellFireGame game;
	private Rect r = new Rect();
	
	
	public MainMenu(HellFireGame game) {
		this.game = game;
	}
	
	public void draw(Canvas canvas) {
		Paint paint = new Paint();
		paint.setTextSize(125);
		paint.setColor(Color.RED);
		drawStringCenterX(canvas, paint, "HELLFIRE", 0, game.getMain().getScreenHeight() * 2 / 12);
		paint.setTextSize(70);
		Rect bounds = new Rect();
		//paint.setStyle(Paint.Style.STROKE);
		paint.getTextBounds("TUTORIAL", 0, "TUTORIAL".length(), bounds);
		game.drawRectangle(game.getMain().getScreenWidth() / 2 - bounds.width() * 2/3, (game.getMain().getScreenHeight() * 4 / 12) - bounds.height() * 15/10, bounds.width() * 4/3, 100, paint);
		game.drawRectangle(game.getMain().getScreenWidth() / 2 - bounds.width() * 2/3, (game.getMain().getScreenHeight() * 6 / 12) - bounds.height() * 15/10, bounds.width() * 4/3, 100, paint);
		game.drawRectangle(game.getMain().getScreenWidth() / 2 - bounds.width() * 2/3, (game.getMain().getScreenHeight() * 8 / 12) - bounds.height() * 15/10, bounds.width() * 4/3, 100, paint);
		game.drawRectangle(game.getMain().getScreenWidth() / 2 - bounds.width() * 2/3, (game.getMain().getScreenHeight() * 10 / 12) - bounds.height() * 15/10, bounds.width() * 4/3, 100, paint);

		paint.setColor(Color.BLACK);
		paint.setStyle(Paint.Style.FILL);
		drawStringCenterX(canvas, paint, "PLAY", 0, game.getMain().getScreenHeight() * 4 / 12);
		drawStringCenterX(canvas, paint, "TUTORIAL", 0, game.getMain().getScreenHeight() * 6 / 12);
		drawStringCenterX(canvas, paint, "CREDITS", 0, game.getMain().getScreenHeight() * 8 / 12);
		paint.setTextSize(40);
		drawStringCenterX(canvas, paint, "Have a suggestion?", 0, game.getMain().getScreenHeight() * 81 / 100);
		drawStringCenterX(canvas, paint, "Click here to email me!", 0, game.getMain().getScreenHeight() * 84 / 100);

	}

	public void drawStringCenterX(Canvas canvas, Paint paint, String text, int additionalX, int y) {
		canvas.getClipBounds(r);
		int cWidth = r.width();
		paint.setTextAlign(Paint.Align.LEFT);
		paint.getTextBounds(text, 0, text.length(), r);
		float x = cWidth / 2f - r.width() / 2f - r.left;
		canvas.drawText(text, x + additionalX, y, paint);
	}

	
}
