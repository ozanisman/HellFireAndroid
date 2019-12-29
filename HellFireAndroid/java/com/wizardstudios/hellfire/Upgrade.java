package com.wizardstudios.hellfire;

public class Upgrade {
	
	private HellFireGame game;
	private double upgradeValue;
	private int price = 0;
	private String description;
	private int currentUpgradeNum;
	private int totalUpgradeNum;
	
	public Upgrade(HellFireGame game, double upgradeValue, int price, String description, int currentUpgradeNum, int totalUpgradeNum) {
		this.game = game;
		this.upgradeValue = upgradeValue;
		this.price = price;
		this.description = description;
		this.currentUpgradeNum = currentUpgradeNum;
		this.totalUpgradeNum = totalUpgradeNum;
	}

	public double getUpgradeValue() {
		return upgradeValue;
	}
	
	public int getPrice() {
		return price;
	}
	
	public String getDescription() {
		return description;
	}
	
	public boolean isPurchasable() {
		if(price == 0) {
			return false;
		}
		if(game.getPlayer().getMoney() >= price) {
			return true;
		}
		return false;
	}
	
	public int getCurrentUpgradeNum() {
		return this.currentUpgradeNum;
	}
	
	public int getTotalUpgradeNum() {
		return this.totalUpgradeNum;
	}

}
