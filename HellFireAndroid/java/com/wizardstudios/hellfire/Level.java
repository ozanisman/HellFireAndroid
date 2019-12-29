package com.wizardstudios.hellfire;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Level {

	private int bossLevel1 = 50;

	private HellFireGame game;
	private Textures texture;
	private int level;
	private boolean money = true;
	private Enemy enemy1, enemy2, enemy3, enemy4, enemy5, enemy6, enemy7;
	private Boss boss1;
	
	private int[] enemies = new int[8];
	private int counter = 0;
	private int numSpawnable = 0;
	private boolean spawnEnemies = false;

	public Level(int level, HellFireGame game, Textures texture) {
		this.level = level;
		this.game = game;
		this.texture = texture;
		try {
			StringBuffer stringBuffer = new StringBuffer();
			InputStream is = game.getResources().openRawResource(R.raw.leveldata);
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(is));
			int index = 1;
			String line = "";
			while(index <= level) {
				line = bufferedReader.readLine();
				stringBuffer.append(line);
				index++;
			}
			index = 0;
			for(int i = 0; i < enemies.length; i++) {
				int enemyNum = 0;
				if(index < line.length()) {
					char currentChar = line.charAt(index);
					while(currentChar != ' ' && index < line.length()) {
						currentChar = line.charAt(index);
						if(currentChar == '/') {
							break;
						}
						if(currentChar != ' ') {
							enemyNum *= 10;
							enemyNum += Integer.parseInt(String.valueOf(currentChar));
						}
						index++;
					}
				}
				enemies[i] = enemyNum;
			}
			bufferedReader.close();
		} catch (IOException e) {
			final HellFireMain main = game.getMain();
			main.runOnUiThread(new Runnable() {
				public void run() {
					Toast.makeText(main, "Unable to Locate Level File!", Toast.LENGTH_SHORT).show();
				}
			});
		}
		
		enemy1 = new Enemy(Math.random() * (game.getMain().getScreenWidth() - 128), game.getMain().getScreenHeight() / 10 - 128, 64, 64, 10, 10, 60, 10, 2.75, texture.enemy1, texture.enemyBlast1, game, texture, 1);
		enemy2 = new Enemy(Math.random() * (game.getMain().getScreenWidth() - 128), game.getMain().getScreenHeight() / 10 - 128, 64, 64, 20, 20, 70, 20, 2.6, texture.enemy2, texture.enemyBlast2, game, texture, 1);
		enemy3 = new Enemy(Math.random() * (game.getMain().getScreenWidth() - 128), game.getMain().getScreenHeight() / 10 - 128, 64, 64, 50, 100, 60, 30, 2.5, texture.enemy3, texture.enemyBlast3, game, texture, 2);
		enemy4 = new Enemy(Math.random() * (game.getMain().getScreenWidth() - 128), game.getMain().getScreenHeight() / 10 - 128, 64, 64, 75, 250, 70, 30, 2.25, texture.enemy4, texture.enemyBlast4, game, texture, 3);
		enemy5 = new Enemy(Math.random() * (game.getMain().getScreenWidth() - 128), game.getMain().getScreenHeight() / 10 - 128, 64, 64, 100, 500, 50, 50, 2.3, texture.enemy5, texture.enemyBlast5, game, texture, 3);
		enemy6 = new Enemy(Math.random() * (game.getMain().getScreenWidth() - 128), game.getMain().getScreenHeight() / 10 - 128, 64, 64, 300, 750, 80, 100, 2, texture.enemy6, texture.enemyBlast6, game, texture, 5);
		enemy7 = new Enemy(Math.random() * (game.getMain().getScreenWidth() - 128), game.getMain().getScreenHeight() / 10 - 128, 64, 64, 500, 1200, 75, 75, 1.7, texture.enemy7, texture.enemyBlast7, game, texture, 6);

		//boss1 = new Boss(game.getMain().getScreenWidth() / 2 - texture.boss1.getWidth() / 2, -texture.boss1.getHeight(), 350, 300, 5000, 50000, texture.boss1, texture.enemyBlast7, game, texture, 2);
	}
	
	public void tick() {
		if(level == bossLevel1) {
			if(money) {
				money = false;
				game.getPlayer().money(100 * level);
			}
			if(spawnEnemies && enemies[7] > 0) {
				game.getSpawner().addBoss(boss1);
				enemies[7]--;
			} else if(spawnEnemies && game.getSpawner().getBoss().isEmpty()) {
				game.nextLevel();
			}
			counter++;
		} else {
			if(!game.getPlayer().getPause()) {
				if(level % 5 == 0 && money) {
					money = false;
					game.getPlayer().money(100 * level);
				}
				if(spawnEnemies && counter >= 30) {
					int random = getRandomSpawn();
					if(random != -1 && enemies[random] > 0) {
						Enemy tempEnemy;
						if(random == 0) {
							tempEnemy = enemy1.clone();
							tempEnemy.setX(Math.random() * 510 + 64);
							game.getSpawner().addEnemy(tempEnemy);
						} else if(random == 1) {
							tempEnemy = enemy2.clone();
							tempEnemy.setX(Math.random() * 510 + 64);
							game.getSpawner().addEnemy(tempEnemy);
						} else if(random == 2) {
							tempEnemy = enemy3.clone();
							tempEnemy.setX(Math.random() * 510 + 64);
							game.getSpawner().addEnemy(tempEnemy);
						} else if(random == 3) {
							tempEnemy = enemy4.clone();
							tempEnemy.setX(Math.random() * 510 + 64);
							game.getSpawner().addEnemy(tempEnemy);
						} else if(random == 4) {
							tempEnemy = enemy5.clone();
							tempEnemy.setX(Math.random() * 510 + 64);
							game.getSpawner().addEnemy(tempEnemy);
						} else if(random == 5) {
							tempEnemy = enemy6.clone();
							tempEnemy.setX(Math.random() * 510 + 64);
							game.getSpawner().addEnemy(tempEnemy);
						} else if(random == 6) {
							tempEnemy = enemy7.clone();
							tempEnemy.setX(Math.random() * 510 + 64);
							game.getSpawner().addEnemy(tempEnemy);
						}
						enemies[random]--;
						counter = 0;
					} else if(game.getSpawner().getEnemy().isEmpty()) {
						game.nextLevel();
					}
				}
				counter++;
			}
		}
	}
	
	public void draw(Canvas canvas) {
		if(level >= 50) {
			game.getPlayer().gameOver(true);
			return;
		}
		Paint paint = new Paint();
		if(!spawnEnemies && counter < 150) {
			if(level == bossLevel1) {
				game.getPlayer().increaseHealth(game.getPlayer().getMaxHealth());
				paint.setColor(Color.RED);
				paint.setTextSize(70);
				game.getMainMenu().drawStringCenterX(canvas, paint, "Final Boss", 0, game.getMain().getScreenHeight() * 3 / 10);
				paint.setTextSize(50);
				game.getMainMenu().drawStringCenterX(canvas, paint, "Your Armor Has Been", 0, game.getMain().getScreenHeight() * 4 / 10);
				game.getMainMenu().drawStringCenterX(canvas, paint, "Restored To Full", 0, game.getMain().getScreenHeight() * 4 / 10 + 50);
			} else {
				paint.setColor(Color.RED);
				paint.setTextSize(100);
				game.getMainMenu().drawStringCenterX(canvas, paint, "Wave " + level, 0, game.getMain().getScreenHeight() * 3 / 10);

				paint.setTextSize(50);
				if(level % 5 == 0) {
					game.getMainMenu().drawStringCenterX(canvas, paint, "Wave Bonus: $" + (100 * level / 5), 0, game.getMain().getScreenHeight() * 4 / 10);
				}
			}
		} else {
			startSpawning();
		}
	}
	
	public int getRandomSpawn() {
		int num = 0;
		for(int i = 0; i < enemies.length; i++) {
			if(enemies[i] > 0) {
				num++;
			}
		}
		if(num == 0) {
			return -1;
		}
		int random = (int)(Math.random() * num);
		for(int i = random; i < enemies.length; i++) {
			if(enemies[i] > 0) {
				return i;
			}
		}
		return -1;
	}
	
	public void startSpawning() {
		this.spawnEnemies = true;
	}
}
