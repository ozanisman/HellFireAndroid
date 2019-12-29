package com.wizardstudios.hellfire;

import android.content.Intent;
import android.graphics.Paint;
import android.graphics.Rect;
import android.net.Uri;
import android.widget.Toast;

public class HellFireListener {

	private HellFireGame game;


	public HellFireListener(HellFireGame game) {
		this.game = game;
	}

	public void fingerPressed(int touchX, int touchY) {

		if(game.checkState("menu")) {
			Paint paint = new Paint();
			paint.setTextSize(70);
			Rect bounds = new Rect();
			paint.getTextBounds("TUTORIAL", 0, "TUTORIAL".length(), bounds);
			if(checkBounds(game.getMain().getScreenWidth() / 2 - bounds.width() * 2/3, game.getMain().getScreenWidth() / 2 - bounds.width() * 2/3 + bounds.width() * 4/3, (game.getMain().getScreenHeight() * 4 / 12) - bounds.height() * 3/2, (game.getMain().getScreenHeight() * 4 / 12) - bounds.height() * 3/2 + 100, touchX, touchY)) {
				game.setStateGame();
			}
			if(checkBounds(game.getMain().getScreenWidth() / 2 - bounds.width() * 2/3, game.getMain().getScreenWidth() / 2 - bounds.width() * 2/3 + bounds.width() * 4/3, (game.getMain().getScreenHeight() * 6 / 12) - bounds.height() * 3/2, (game.getMain().getScreenHeight() * 6 / 12) - bounds.height() * 3/2 + 100, touchX, touchY)) {
				game.setStateTutorial();
			}
			if(checkBounds(game.getMain().getScreenWidth() / 2 - bounds.width() * 2/3, game.getMain().getScreenWidth() / 2 - bounds.width() * 2/3 + bounds.width() * 4/3, (game.getMain().getScreenHeight() * 8 / 12) - bounds.height() * 3/2, (game.getMain().getScreenHeight() * 8 / 12) - bounds.height() * 3/2 + 100, touchX, touchY)) {
				game.setStateSettings();
			}
			if(checkBounds(game.getMain().getScreenWidth() / 2 - bounds.width() * 2/3, game.getMain().getScreenWidth() / 2 - bounds.width() * 2/3 + bounds.width() * 4/3, (game.getMain().getScreenHeight() * 10 / 12) - bounds.height() * 3/2, (game.getMain().getScreenHeight() * 10 / 12) - bounds.height() * 3/2 + 100, touchX, touchY)) {

				Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.parse("mailto: oz.anisman@gmail.com"));
				intent.putExtra(Intent.EXTRA_SUBJECT, "HellFire Game Suggestion");
				intent.putExtra(Intent.EXTRA_TEXT, "");
				try {
					game.getMain().startActivity(intent);
				} catch (android.content.ActivityNotFoundException ex) {
					Toast.makeText(game.getMain(), "Mail account not configured", Toast.LENGTH_SHORT).show();
				}
			}
		} else if(game.checkState("game")) {
			if(checkBounds(game.getMain().getScreenWidth() - 105, game.getMain().getScreenWidth() - 5, 5, game.getMain().getScreenHeight() / 10, touchX, touchY)) {
                if(!game.getPlayer().getPause()) {
                    game.getPlayer().setPause(true);
                } else {
                    game.getPlayer().setPause(false);
                }

            }
			if(game.getPlayer().getPause()) {
				Rect bounds = new Rect();
				Paint paint = new Paint();
				paint.setTextSize(70);
				paint.getTextBounds("TUTORIAL", 0, "TUTORIAL".length(), bounds);

				if(checkBounds(game.getMain().getScreenWidth() / 2 - bounds.width() * 2/3, game.getMain().getScreenWidth() / 2 - bounds.width() * 2/3 + bounds.width() * 4/3, (game.getMain().getScreenHeight() * 4 / 10) - bounds.height() * 15/10, (game.getMain().getScreenHeight() * 4 / 10) - bounds.height() * 15/10 + 100, touchX, touchY)) {
					game.getPlayer().setPause(false);
				} else if(checkBounds(game.getMain().getScreenWidth() / 2 - bounds.width() * 2/3, game.getMain().getScreenWidth() / 2 - bounds.width() * 2/3 + bounds.width() * 4/3, (game.getMain().getScreenHeight() * 5 / 10) - bounds.height() * 15/10, (game.getMain().getScreenHeight() * 5 / 10) - bounds.height() * 15/10 + 100, touchX, touchY)) {
					game.setStateUpgrade();
					game.restart();
				} else if(checkBounds(game.getMain().getScreenWidth() / 2 - bounds.width() * 2/3, game.getMain().getScreenWidth() / 2 - bounds.width() * 2/3 + bounds.width() * 4/3, (game.getMain().getScreenHeight() * 6 / 10) - bounds.height() * 15/10, (game.getMain().getScreenHeight() * 6 / 10) - bounds.height() * 15/10 + 100, touchX, touchY)) {
					game.setStateSettings();
					game.restart();
				} else if(checkBounds(game.getMain().getScreenWidth() / 2 - bounds.width() * 2/3, game.getMain().getScreenWidth() / 2 - bounds.width() * 2/3 + bounds.width() * 4/3, (game.getMain().getScreenHeight() * 7 / 10) - bounds.height() * 15/10, (game.getMain().getScreenHeight() * 7 / 10) - bounds.height() * 15/10 + 100, touchX, touchY)) {
					game.setStateMenu();
					game.restart();
				}
			}
		} else if(game.checkState("tutorial")) {
			if(game.getTutorialMenu().getStage() == 7 && checkBounds(game.getMain().getScreenWidth() - 230, game.getMain().getScreenWidth() - 10, game.getMain().getScreenHeight() - 80, game.getMain().getScreenHeight() - 10, touchX, touchY)) {
				game.setStateGame();
			} else if(checkBounds(game.getMain().getScreenWidth() - 230, game.getMain().getScreenWidth() - 10, game.getMain().getScreenHeight() - 80, game.getMain().getScreenHeight() - 10, touchX, touchY)) {
				game.getTutorialMenu().setStage(game.getTutorialMenu().getStage() + 1);
			}
			if(checkBounds(10, 230, game.getMain().getScreenHeight() - 80, game.getMain().getScreenHeight() - 10, touchX, touchY)) {
				if(game.getTutorialMenu().getStage() == 0) {
					game.setStateMenu();
				} else {
					game.getTutorialMenu().setStage(game.getTutorialMenu().getStage() - 1);
				}
			}
		} else if(game.checkState("settings")) {
			if(checkBounds(10, 230, game.getMain().getScreenHeight() - 80, game.getMain().getScreenHeight() - 10, touchX, touchY)) {
				game.setStateMenu();
			}
		} else if(game.checkState("upgrades")) {
			if(checkBounds(game.getMain().getScreenWidth() - 220, game.getMain().getScreenWidth() - 10, game.getMain().getScreenHeight() - 80, game.getMain().getScreenHeight() - 10, touchX, touchY)) {
				game.setStateGame();
			} else if(checkBounds(10, 230, game.getMain().getScreenHeight() - 80, game.getMain().getScreenHeight() - 10, touchX, touchY)) {
				game.setStateMenu();
			}

			if(game.getUpgradesMenu().getState() == 0) {
				//////////////////////////////////////////////////////STATE 0\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\

				if(checkBounds(10, game.getMain().getScreenWidth() - 20, 200 + game.getMain().getScreenHeight() / 12 * 0, 260 + game.getMain().getScreenHeight() / 12 * 0, touchX, touchY)) {
					if(game.getPlayer().getMoney() >= game.getPlayer().getUpgrades().getBlastDamage().getPrice()) {
						game.getPlayer().money(-game.getPlayer().getUpgrades().getBlastDamage().getPrice());
						game.getPlayer().getUpgrades().upgradeBlastDamage();
						game.getPlayer().getUpgrades().upgradeBlastType();
					}
				} else if(checkBounds(10, game.getMain().getScreenWidth() - 20, 200 + game.getMain().getScreenHeight() / 12 * 1, 260 + game.getMain().getScreenHeight() / 12 * 1, touchX, touchY)) {
					if(game.getPlayer().getMoney() >= game.getPlayer().getUpgrades().getBlastFirerate().getPrice()) {
						game.getPlayer().money(-game.getPlayer().getUpgrades().getBlastFirerate().getPrice());
						game.getPlayer().getUpgrades().upgradeBlastFirerate();
					}
				} else if(checkBounds(10, game.getMain().getScreenWidth() - 20, 200 + game.getMain().getScreenHeight() / 12 * 2, 260 + game.getMain().getScreenHeight() / 12 * 2, touchX, touchY)) {
					if(game.getPlayer().getMoney() >= game.getPlayer().getUpgrades().getMissileDamage().getPrice()) {
						if(game.getPlayer().getUpgrades().getMissileDamage().getUpgradeValue() == 0) {
							game.getPlayer().getUpgrades().upgradeMissileFirerate();
							game.getPlayer().getUpgrades().upgradeMissileExplosionDamage();
							game.getPlayer().getUpgrades().upgradeMissileExplosionRadius();
							if(game.getPlayer().getUpgrades().getDualShot().getUpgradeValue() == 1) {
								game.getPlayer().getUpgrades().upgradeDualShot();
							}
						}
						game.getPlayer().money(-game.getPlayer().getUpgrades().getMissileDamage().getPrice());
						game.getPlayer().getUpgrades().upgradeMissileDamage();
						if(game.getPlayer().getUpgrades().getMissileDamage().getUpgradeValue() != 0) {
							game.getPlayer().getUpgrades().upgradeMissileType();
						}
					}
				} else if(checkBounds(10, game.getMain().getScreenWidth() - 20, 200 + game.getMain().getScreenHeight() / 12 * 3, 260 + game.getMain().getScreenHeight() / 12 * 3, touchX, touchY)) {
					if(game.getPlayer().getMoney() >= game.getPlayer().getUpgrades().getMissileFirerate().getPrice() && game.getPlayer().getUpgrades().getMissileDamage().getUpgradeValue() != 0) {
						game.getPlayer().money(-game.getPlayer().getUpgrades().getMissileFirerate().getPrice());
						game.getPlayer().getUpgrades().upgradeMissileFirerate();
						
					}
				} else if(checkBounds(10, game.getMain().getScreenWidth() - 20, 200 + game.getMain().getScreenHeight() / 12 * 4, 260 + game.getMain().getScreenHeight() / 12 * 4, touchX, touchY)) {
					if(game.getPlayer().getMoney() >= game.getPlayer().getUpgrades().getMissileExplosionDamage().getPrice() && game.getPlayer().getUpgrades().getMissileDamage().getUpgradeValue() != 0) {
						game.getPlayer().money(-game.getPlayer().getUpgrades().getMissileExplosionDamage().getPrice());
						if(game.getPlayer().getUpgrades().getMissileExplosionDamage().getUpgradeValue() == 0) {
							game.getPlayer().getUpgrades().upgradeMissileExplosionRadius();
						}
						game.getPlayer().getUpgrades().upgradeMissileExplosionDamage();
		
					}
				} else if(checkBounds(10, game.getMain().getScreenWidth() - 20, 200 + game.getMain().getScreenHeight() / 12 * 5, 260 + game.getMain().getScreenHeight() / 12 * 5, touchX, touchY)) {
					if(game.getPlayer().getMoney() >= game.getPlayer().getUpgrades().getMissileExplosionRadius().getPrice() && game.getPlayer().getUpgrades().getMissileExplosionDamage().getUpgradeValue() != 0) {
						game.getPlayer().money(-game.getPlayer().getUpgrades().getMissileExplosionRadius().getPrice());
						game.getPlayer().getUpgrades().upgradeMissileExplosionRadius();
					}
				} else if(checkBounds(10, game.getMain().getScreenWidth() - 20, 200 + game.getMain().getScreenHeight() / 12 * 6, 260 + game.getMain().getScreenHeight() / 12 * 6, touchX, touchY)) {
					if(game.getPlayer().getMoney() >= game.getPlayer().getUpgrades().getLaserDamage().getPrice()) {
						if(game.getPlayer().getUpgrades().getLaserDamage().getUpgradeValue() != 0) {
							game.getPlayer().getUpgrades().upgradeLaserType();
						}
						game.getPlayer().money(-game.getPlayer().getUpgrades().getLaserDamage().getPrice());
						game.getPlayer().getUpgrades().upgradeLaserDamage();
						
					}
				} else if(checkBounds(10, game.getMain().getScreenWidth() - 20, 200 + game.getMain().getScreenHeight() / 12 * 7, 260 + game.getMain().getScreenHeight() / 12 * 7, touchX, touchY)) {
					if(game.getPlayer().getUpgrades().getDualShot().getUpgradeValue() == 0 && game.getPlayer().getMoney() >= game.getPlayer().getUpgrades().getDualShot().getPrice() && game.getPlayer().getUpgrades().getMissileDamage().getUpgradeValue() > 0) {
						game.getPlayer().getUpgrades().upgradeDualShot();
						game.getPlayer().getUpgrades().upgradeDualShot();
					} else if(game.getPlayer().getUpgrades().getDualShot().getUpgradeValue() != 1 && game.getPlayer().getMoney() >= game.getPlayer().getUpgrades().getDualShot().getPrice()) {
						game.getPlayer().money(-game.getPlayer().getUpgrades().getDualShot().getPrice());
						game.getPlayer().getUpgrades().upgradeDualShot();
					}
				}
			} else if(game.getUpgradesMenu().getState() == 1) {
				//////////////////////////////////////////////////////STATE 1\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
				if(checkBounds(10, game.getMain().getScreenWidth() - 20, 200 + game.getMain().getScreenHeight() / 12 * 0, 260 + game.getMain().getScreenHeight() / 12 * 0, touchX, touchY)) {
					if(game.getPlayer().getMoney() >= game.getPlayer().getUpgrades().getShieldCooldown().getPrice()) {
						if(game.getPlayer().getUpgrades().getShieldCooldown().getUpgradeValue() == 0) {
							game.getPlayer().getUpgrades().upgradeShieldDuration();
						}
						game.getPlayer().money(-game.getPlayer().getUpgrades().getShieldCooldown().getPrice());
						game.getPlayer().getUpgrades().upgradeShieldCooldown();
					}
				} else if(checkBounds(10, game.getMain().getScreenWidth() - 20, 200 + game.getMain().getScreenHeight() / 12 * 1, 260 + game.getMain().getScreenHeight() / 12 * 1, touchX, touchY)) {
					if(game.getPlayer().getMoney() >= game.getPlayer().getUpgrades().getShieldDuration().getPrice() && game.getPlayer().getUpgrades().getShieldCooldown().getUpgradeValue() != 0) {
						game.getPlayer().money(-game.getPlayer().getUpgrades().getShieldDuration().getPrice());
						game.getPlayer().getUpgrades().upgradeShieldDuration();
					}
				} else if(checkBounds(10, game.getMain().getScreenWidth() - 20, 200 + game.getMain().getScreenHeight() / 12 * 2, 260 + game.getMain().getScreenHeight() / 12 * 2, touchX, touchY)) {
					if(game.getPlayer().getMoney() >= game.getPlayer().getUpgrades().getShipHealth().getPrice()) {
						game.getPlayer().money(-game.getPlayer().getUpgrades().getShipHealth().getPrice());
						game.getPlayer().getUpgrades().upgradeShipHealth();
					}
				}
			} else {
				//////////////////////////////////////////////////////STATE 2\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
				 if(checkBounds(10, game.getMain().getScreenWidth() - 20, 200 + game.getMain().getScreenHeight() / 12 * 0, 260 + game.getMain().getScreenHeight() / 12 * 0, touchX, touchY)) {
					if(game.getPlayer().getMoney() >= game.getPlayer().getUpgrades().getDamageReduction().getPrice()) {
						game.getPlayer().money(-game.getPlayer().getUpgrades().getDamageReduction().getPrice());
						game.getPlayer().getUpgrades().upgradeDamageReduction();
					}
				} else if(checkBounds(10, game.getMain().getScreenWidth() - 20, 200 + game.getMain().getScreenHeight() / 12 * 1, 260 + game.getMain().getScreenHeight() / 12 * 1, touchX, touchY)) {
					if(game.getPlayer().getMoney() >= game.getPlayer().getUpgrades().getPowerupDrop().getPrice()) {
						game.getPlayer().money(-game.getPlayer().getUpgrades().getPowerupDrop().getPrice());
						game.getPlayer().getUpgrades().upgradePowerupDrop();
					}
				} else if(checkBounds(10, game.getMain().getScreenWidth() - 20, 200 + game.getMain().getScreenHeight() / 12 * 2, 260 + game.getMain().getScreenHeight() / 12 * 2, touchX, touchY)) {
					if(game.getPlayer().getMoney() >= game.getPlayer().getUpgrades().getDamageBonus().getPrice()) {
						game.getPlayer().money(-game.getPlayer().getUpgrades().getDamageBonus().getPrice());
						game.getPlayer().getUpgrades().upgradeDamageBonus();
					}
				} else if(checkBounds(10, game.getMain().getScreenWidth() - 20, 200 + game.getMain().getScreenHeight() / 12 * 3, 260 + game.getMain().getScreenHeight() / 12 * 3, touchX, touchY)) {
					if(game.getPlayer().getMoney() >= game.getPlayer().getUpgrades().getMoneyBonus().getPrice()) {
						game.getPlayer().money(-game.getPlayer().getUpgrades().getMoneyBonus().getPrice());
						game.getPlayer().getUpgrades().upgradeMoneyBonus();
					}
				} else if(checkBounds(10, game.getMain().getScreenWidth() - 20, 200 + game.getMain().getScreenHeight() / 12 * 4, 260 + game.getMain().getScreenHeight() / 12 * 4, touchX, touchY)) {
					 if(game.getPlayer().getMoney() >= game.getPlayer().getUpgrades().getStartLevel().getPrice()) {
						 game.getPlayer().money(-game.getPlayer().getUpgrades().getStartLevel().getPrice());
						 game.getPlayer().getUpgrades().upgradeStartLevel();
						 game.setStartLevel((int)game.getPlayer().getUpgrades().getStartLevel().getUpgradeValue());
					 }
				 }
			}
			if(checkBounds(game.getMain().getScreenWidth() * 31 / 100, game.getMain().getScreenWidth() * 69 / 100, 115, 175, touchX, touchY)) {
				game.getUpgradesMenu().setState(0);
			} else if(checkBounds(0, game.getMain().getScreenWidth() * 31 / 100, 115, 175, touchX, touchY)) {
				game.getUpgradesMenu().setState(1);
			} else if(checkBounds(game.getMain().getScreenWidth() * 69 / 100, game.getMain().getScreenWidth(), 115, 175, touchX, touchY)) {
				game.getUpgradesMenu().setState(2);
			}
		}
	}

	public void fingerDragged(int touchX, int touchY) {
		if(!game.getPlayer().getPause()) {
			if(!game.getPlayer().canMove) {
				return;
			}

			touchX -= 70;
			touchY -= 90;
			if(touchY < game.getMain().getScreenHeight() / 10) {
				return;
			}
            if(touchY > game.getMain().getScreenHeight()) {
                touchY = game.getMain().getScreenHeight();
			}
			if(touchX > game.getMain().getScreenWidth() - 110) {
				touchX = game.getMain().getScreenWidth() - 110;
			} else if(touchX < 0) {
                touchX = 0;
            }
			game.getPlayer().setX(touchX);
			game.getPlayer().setY(touchY);
		}
	}
	
	public boolean checkBounds(int x, int finalX, int y, int finalY, int touchX, int touchY) {
		if(touchX >= x && touchX <= finalX && touchY >= y && touchY <= finalY) {
			return true;
		}
		return false;
	}

}
