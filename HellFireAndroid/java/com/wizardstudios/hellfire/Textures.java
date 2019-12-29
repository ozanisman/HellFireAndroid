package com.wizardstudios.hellfire;


import android.graphics.Bitmap;

public class Textures {
	
	private Bitmap shipSheet; // 64x64
	private Bitmap missileSheet; // 20x20
	private Bitmap laserSheet; // 25x145
	private Bitmap blastSheet; // 20x25
	private Bitmap explosionSheet; // 30x30
	private Bitmap shipExplosionSheet; // 70x70
	private Bitmap powerupSheet; // 20x20
	private Bitmap shieldSheet; // 80x73
	private Bitmap enemyBlastSheet; // 15x32
	private Bitmap bossSheet; //	300x350
	
	public Bitmap player, playerShield1, playerShield2, blast1, blast2, blast3, blast4, blast5, missile1, missile2, missile3, missile4, missileExplosion, enemy1, enemy2, enemy3, enemy4, enemy5, enemy6, enemy7, boss1, enemyBlast1, enemyBlast2, enemyBlast3, enemyBlast4, enemyBlast5, enemyBlast6, enemyBlast7, enemyBlast8, bossBlast, enemyMissile1, enemyMissile2, enemyMissile3, enemyMissile4, powerup1, powerup2, powerup3, powerup4, powerup5, powerup6, powerup7, powerup8;
	public Bitmap[] shipExplosion = new Bitmap[7];
	public Bitmap[] bulletExplosion = new Bitmap[6];
	public Bitmap[] laser1 = new Bitmap[6], laser2 = new Bitmap[6], laser3 = new Bitmap[6], laser4 = new Bitmap[6], laser5 = new Bitmap[6];

	public Textures(HellFireGame game) {
		shipSheet = game.getShipSheet();
		missileSheet = game.getMissileSheet();
		laserSheet = game.getLaserSheet();
		blastSheet = game.getBlastSheet();
		explosionSheet = game.getExplosionSheet();
		shipExplosionSheet = game.getShipExplosionSheet();
		powerupSheet = game.getPowerupSheet();
		shieldSheet = game.getShieldSheet();
		enemyBlastSheet = game.getEnemyBlastSheet();
		bossSheet = game.getBossSheet();

		//Player sprites
		player = getCroppedBitmap(game.getShipSheet(), 1, 2, 64, 64);
		playerShield1 = getCroppedBitmap(game.getShieldSheet(), 1, 1, 80, 80);
		playerShield2 = getCroppedBitmap(game.getShieldSheet(), 1, 2, 80, 80);

		blast1 = getCroppedBitmap(blastSheet, 5, 1, 20, 25);
		blast2 = getCroppedBitmap(blastSheet, 2, 1, 20, 25);
		blast3 = getCroppedBitmap(blastSheet, 1, 1, 20, 25);
		blast4 = getCroppedBitmap(blastSheet, 3, 1, 20, 25);
		blast5 = getCroppedBitmap(blastSheet, 4, 1, 20, 25);

		missile1 = getCroppedBitmap(missileSheet, 2, 4, 20, 20);
		missile2 = getCroppedBitmap(missileSheet, 5, 4, 20, 20);
		missile3 = getCroppedBitmap(missileSheet, 2, 1, 20, 20);
		missile4 = getCroppedBitmap(missileSheet, 5, 1, 20, 20);

		for (int i = 0; i < 6; i++) {
			laser1[i] = getCroppedBitmap(laserSheet, i + 1, 1, 25, 145); //Green
		}
		for (int i = 0; i < 6; i++) {
			laser2[i] = getCroppedBitmap(laserSheet, i + 1, 3, 25, 145); //Yellow
		}
		for (int i = 0; i < 6; i++) {
			laser3[i] = getCroppedBitmap(laserSheet, i + 7, 3, 25, 145); //White
		}
		for (int i = 0; i < 6; i++) {
			laser4[i] = getCroppedBitmap(laserSheet, i + 7, 1, 25, 145); //Purple
		}
		for (int i = 0; i < 6; i++) {
			laser5[i] = getCroppedBitmap(laserSheet, i + 1, 2, 25, 145); //Red
		}

		powerup1 = getCroppedBitmap(powerupSheet, 1, 1, 20, 20); // red, restore health
		powerup2 = getCroppedBitmap(powerupSheet, 4, 1, 20, 20); // dark blue, increases shield cap
		powerup3 = getCroppedBitmap(powerupSheet, 5, 1, 19, 20); // light green, gain $100 + $10 for every upgrade bought
		powerup4 = getCroppedBitmap(powerupSheet, 1, 2, 20, 20); // yellow, increases damage
		powerup5 = getCroppedBitmap(powerupSheet, 3, 2, 20, 20); // light blue, slows enemies on screen
		powerup6 = getCroppedBitmap(powerupSheet, 2, 2, 19, 20); // brown, black hole?
		powerup7 = getCroppedBitmap(powerupSheet, 6, 2, 20, 20); // orange
		powerup8 = getCroppedBitmap(powerupSheet, 7, 2, 20, 20); // pink,

		shipExplosion[0] = getCroppedBitmap(shipExplosionSheet, 4, 2, 70, 70);
		shipExplosion[1] = getCroppedBitmap(shipExplosionSheet, 6, 2, 70, 70);
		shipExplosion[2] = getCroppedBitmap(shipExplosionSheet, 3, 3, 70, 70);
		shipExplosion[3] = getCroppedBitmap(shipExplosionSheet, 6, 3, 70, 70);
		shipExplosion[4] = getCroppedBitmap(shipExplosionSheet, 2, 4, 70, 70);
		shipExplosion[5] = getCroppedBitmap(shipExplosionSheet, 6, 4, 70, 70);
		shipExplosion[6] = getCroppedBitmap(shipExplosionSheet, 3, 6, 70, 70);

		for(int i = 0; i < bulletExplosion.length; i++) {
			bulletExplosion[i] = getCroppedBitmap(shipExplosionSheet, i + 1, 6, 70, 70);
		}

		missileExplosion = getCroppedBitmap(explosionSheet, 2, 1, 28, 30);

		enemy1 = getCroppedBitmap(shipSheet, 2, 1, 64, 64);
		enemy2 = getCroppedBitmap(shipSheet, 3, 1, 64, 64);
		enemy3 = getCroppedBitmap(shipSheet, 1, 3, 64, 64);
		enemy4 = getCroppedBitmap(shipSheet, 2, 2, 64, 64);
		enemy5 = getCroppedBitmap(shipSheet, 2, 3, 64, 64);
		enemy6 = getCroppedBitmap(shipSheet, 3, 2, 64, 64);
		enemy7 = getCroppedBitmap(shipSheet, 3, 3, 64, 64);
		boss1 =getCroppedBitmap(bossSheet, 1, 1, 300, 350);

		enemyBlast1 = getCroppedBitmap(enemyBlastSheet, 1, 1, 15, 32);
		enemyBlast2 = getCroppedBitmap(enemyBlastSheet, 2, 1, 15, 32);
		enemyBlast3 = getCroppedBitmap(enemyBlastSheet, 3, 1, 15, 32);
		enemyBlast4 = getCroppedBitmap(enemyBlastSheet, 4, 1, 15, 32);
		enemyBlast5 = getCroppedBitmap(enemyBlastSheet, 3, 2, 15, 32);
		enemyBlast6 = getCroppedBitmap(enemyBlastSheet, 2, 2, 15, 32);
		enemyBlast7 = getCroppedBitmap(enemyBlastSheet, 1, 2, 15, 32);
		enemyBlast8 = getCroppedBitmap(enemyBlastSheet, 4, 2, 15, 32);

		bossBlast = getCroppedBitmap(explosionSheet, 1, 4, 30, 30);

		enemyMissile1 = getCroppedBitmap(missileSheet, 2, 6, 20, 20);
		enemyMissile2 = getCroppedBitmap(missileSheet, 2, 3, 20, 20);
		enemyMissile3 = getCroppedBitmap(missileSheet, 5, 6, 20, 20);
		enemyMissile4 = getCroppedBitmap(missileSheet, 5, 3, 20, 20);
	}

	public Bitmap getCroppedBitmap(Bitmap source, int xPosition, int yPosition, int width, int height) {
		Bitmap b = Bitmap.createBitmap(source, (xPosition - 1) * width, (yPosition - 1) * height, width, height);
		return Bitmap.createScaledBitmap(b, (int)(width * 1.75), (int)(height * 1.75), false);
	}
	
}
