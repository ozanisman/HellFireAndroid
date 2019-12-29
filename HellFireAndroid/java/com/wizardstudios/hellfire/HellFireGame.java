package com.wizardstudios.hellfire;

import android.Manifest;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Environment;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;


/* TODO
        -fix boss hitbox/bullet placement
        -make upgrade tutorial interactive - can be minimal
        -fix settings menu - FPS (30 recommended, 35, 40), auto check for update, toggle popup texts, CREDITS - add to save file
        -use static for powerup effects in main
        -google play acheivements and leaderboard
        -change file saving to internal storage


        >>>>>>>>>>>>>>>>>>>>>>     DOUBLE CHECK RATIOS WITH OTHER DEVICES!!!!    <<<<<<<<<<<<<<<<<<<<<<<

        POSSIBLE NEW STUFFS -

        Upgrades
            - Purchase toggle buttons with positives and negatives

        Better hitboxes - multiple shapes

        More weapons - explosive mine, seeking missile, lightning gun (shot branches to nearby enemies), machine gun (random accuracy), bullets with X velocity,
        More ships - MillionthVector
        "Tribes" of ships? difficulty/new tribe based on wave? each tribe has a boss? expansions with different enemies? (pay to get???)
        Player can choose ships, each has different specialty. Different upgrades?

        Achievements? - Unlock player ships/weapons/upgrades/levels

        Better shop - buy/sell weapons
        Equip screen - players can adjust their weapons to the slots - USE MILLIONTH VECTOR 3D WEAPON ART??
*/


public class HellFireGame extends SurfaceView implements SurfaceHolder.Callback {

    private MainThread thread;
    private Canvas canvas;
    private HellFireMain main;
    private HellFireListener listener;
    private BitmapFactory.Options options;
    private Player player;
    private Spawner spawner;
    private Textures texture;
    private STATE State = STATE.INTRO;
    private MainMenu mainMenu;
    private UpgradesMenu upgradesMenu;
    private TutorialMenu tutorialMenu;
    private SettingsMenu settingsMenu;
    private Level[] level = new Level[getLevels()];
    private int startLevel = 1;
    private int currentLevel = startLevel;
    private int backgroundY;
    private boolean musicMuted;
    private boolean autoUpdateChecker = true;
    private InputStream is;
    GestureDetector gestureDetector;

    private Bitmap shipSheet, missileSheet, laserSheet, blastSheet, explosionSheet, shipExplosionSheet, powerupSheet, shieldSheet, enemyBlastSheet, bossSheet, background, pauseImage;
    private Bitmap logo;
    private int logoAlpha = 0;
    private boolean alphaIncreasing = true;


    public HellFireGame(Context context, HellFireMain main) {
        super(context);
        getHolder().addCallback(this);
        thread = new MainThread(getHolder(), this, main);
        this.main = main;
        listener = new HellFireListener(this);

        //Initialize

        options = new BitmapFactory.Options();
        options.inScaled = false;
        options.inPreferredConfig = Bitmap.Config.ARGB_8888;

        background = BitmapFactory.decodeResource(getResources(), R.drawable.background, options);
        shipSheet = BitmapFactory.decodeResource(getResources(), R.drawable.shipsheet, options);
        missileSheet = BitmapFactory.decodeResource(getResources(), R.drawable.missilesheet, options);
        laserSheet = BitmapFactory.decodeResource(getResources(), R.drawable.lasersheet, options);
        blastSheet = BitmapFactory.decodeResource(getResources(), R.drawable.blastsheet, options);
        explosionSheet = BitmapFactory.decodeResource(getResources(), R.drawable.explosionsheet, options);
        shipExplosionSheet = BitmapFactory.decodeResource(getResources(), R.drawable.shipexplosionsheet, options);
        powerupSheet = BitmapFactory.decodeResource(getResources(), R.drawable.powerupsheet, options);
        shieldSheet = BitmapFactory.decodeResource(getResources(), R.drawable.shieldsheet, options);
        enemyBlastSheet = BitmapFactory.decodeResource(getResources(), R.drawable.enemyblastsheet, options);
        bossSheet = BitmapFactory.decodeResource(getResources(), R.drawable.bosssheet, options);
        logo = BitmapFactory.decodeResource(getResources(), R.drawable.logo, options);
        pauseImage = BitmapFactory.decodeResource(getResources(), R.drawable.pauseimage, options);

        gestureDetector = new GestureDetector(context, new GestureListener());

        texture = new Textures(this);
        player = new Player(getMain().getScreenWidth() / 2 - texture.player.getWidth() / 2, getMain().getScreenHeight() * 7/10, 128, 128, this, texture);
        mainMenu = new MainMenu(this);
        upgradesMenu = new UpgradesMenu(this);
        tutorialMenu = new TutorialMenu(this, texture);
        settingsMenu = new SettingsMenu(this);
        spawner = new Spawner(this);
        for(int i = 0; i < level.length; i++) {
            level[i] = new Level(i + 1, this, texture);
        }
        backgroundY = 0;
        setFocusable(true);
    }

    public void tick() {
        if(State == STATE.INTRO) {
            return;
        } else {
            if (State == STATE.GAME) {
                if (currentLevel != level.length + 1) {
                    level[currentLevel - 1].tick();
                } else {
                    player.gameOver(true);
                }
                player.tick();
                spawner.tick();
            } else if (State == STATE.UPGRADE) {
                upgradesMenu.tick();
            }
            backgroundY++;
            if (backgroundY >= 1800) {
                backgroundY = 0;
            }
        }
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        this.canvas = canvas;
        if(State == STATE.INTRO) {
            if(!main.getAnsweredPrompt() && main.getPromptGiven()) {
                return;
            }
            Paint paint = new Paint();
            paint.setAlpha(logoAlpha);
            canvas.drawBitmap(logo, main.getScreenWidth() / 2 - logo.getWidth() / 2, main.getScreenHeight() / 2 - logo.getHeight() / 2, paint);
            if(alphaIncreasing && logoAlpha < 255) {
                logoAlpha += 5;
            } else if(!alphaIncreasing && logoAlpha > 0) {
                logoAlpha -= 5;
            } else if(alphaIncreasing) {
                try {
                    thread.sleep(1500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                alphaIncreasing = !alphaIncreasing;
            } else {
                try {
                    thread.sleep(300);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                setStateMenu();
                player.updatePlayer(getPlayerData());
                restart();
            }

        } else {
            canvas.drawBitmap(background, 0, -1800 + backgroundY, null);
            canvas.drawBitmap(background, 0, backgroundY, null);
            if (State == STATE.GAME) {
                spawner.draw(canvas);
                player.draw(canvas);
                if (currentLevel != level.length + 1) {
                    level[currentLevel - 1].draw(canvas);
                }
            } else if (State == STATE.MENU) {
                mainMenu.draw(canvas);
            } else if (State == STATE.UPGRADE) {
                upgradesMenu.draw(canvas);
            } else if (State == STATE.TUTORIAL) {
                tutorialMenu.draw(canvas);
            } else if (State == STATE.SETTINGS) {
                settingsMenu.draw(canvas);
            }
        }

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        gestureDetector.onTouchEvent(event);

        if(State == STATE.INTRO) {
            setStateMenu();
            player.updatePlayer(getPlayerData());
            restart();
            return true;
        }
        double touchX = event.getX();
        double touchY = event.getY();

        switch(event.getAction()) {
            case MotionEvent.ACTION_DOWN: listener.fingerPressed((int)touchX, (int)touchY);
            case MotionEvent.ACTION_MOVE: listener.fingerDragged((int)touchX, (int)touchY);
            default:
        }

        return true;

    }

    public class GestureListener extends GestureDetector.SimpleOnGestureListener {


        // event when double tap occurs
        @Override
        public boolean onDoubleTap(MotionEvent event) {
            double x = event.getX();
            double y = event.getY();
            if(player.getShieldReady()) {
                player.setShieldActive();
            }
            return true;
        }
    }


    public void restart() {
        startLevel = (int)player.getUpgrades().getStartLevel().getUpgradeValue();
        currentLevel = startLevel;
        player.restart();
        spawner.restart();
        for(int i = 0; i < level.length; i++) {
            level[i] = new Level(i + 1, this, texture);
        }
        tutorialMenu.setStage(0);
    }

    public enum STATE {
        MENU,
        GAME,
        UPGRADE,
        TUTORIAL,
        SETTINGS,
        INTRO
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {}

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        thread = new MainThread(getHolder(), this, main);
        thread.setRunning(true);
        thread.start();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        boolean retry = true;
        while(retry) {
            try {
                thread.setRunning(false);
                thread.join();
            } catch(InterruptedException e) {
                e.printStackTrace();
            }
            retry = false;
        }
    }

    public void stopMusic() {
        main.stopMusic();
    }

    public void playMusic() {
        main.playMusic();
    }

    public Player getPlayer() {
        return player;
    }


    public Spawner getSpawner() {
        return spawner;
    }

    public Textures getTexture() {
        return texture;
    }

    public Bitmap getShipSheet() {
        return shipSheet;
    }

    public Bitmap getMissileSheet() {
        return missileSheet;
    }

    public Bitmap getLaserSheet() {
        return laserSheet;
    }

    public Bitmap getBlastSheet() {
        return blastSheet;
    }

    public Bitmap getExplosionSheet() {
        return explosionSheet;
    }

    public Bitmap getPowerupSheet() {
        return powerupSheet;
    }

    public Bitmap getShipExplosionSheet() {
        return shipExplosionSheet;
    }

    public Bitmap getShieldSheet() {
        return shieldSheet;
    }

    public Bitmap getEnemyBlastSheet() {
        return enemyBlastSheet;
    }

    public Bitmap getBossSheet() {
        return bossSheet;
    }

    public Bitmap getPauseImage() {
        return pauseImage;
    }

    public Boolean checkState(String state) {
        if(state.equals("menu") && State == STATE.MENU) {
            return true;
        } else if(state.equals("game") && State == STATE.GAME) {
            return true;
        } else if(state.equals("upgrades") && State == STATE.UPGRADE) {
            return true;
        } else if(state.equals("settings") && State == STATE.SETTINGS) {
            return true;
        } else if(state.equals("tutorial") && State == STATE.TUTORIAL) {
            return true;
        }
        return false;
    }

    public void setStateGame() {
        player.upgrade();
        player.savePlayerData();
        State = STATE.GAME;
    }

    public void setStateMenu() {
        if(State == STATE.GAME || State == STATE.UPGRADE) {
            player.savePlayerData();
        }
        State = STATE.MENU;
    }

    public void setStateUpgrade() {
        State = STATE.UPGRADE;
        player.savePlayerData();
        restart();
    }

    public void setStateTutorial() {
        State = STATE.TUTORIAL;
    }
    public void setStateIntro() {
        State = STATE.INTRO;
    }

    public void setStateSettings() {
        State = STATE.SETTINGS;
    }

    public MainMenu getMainMenu() {
        return mainMenu;
    }

    public UpgradesMenu getUpgradesMenu() {
        return upgradesMenu;
    }

    public TutorialMenu getTutorialMenu() {
        return tutorialMenu;
    }

    public void nextLevel() {
        currentLevel++;
    }

    public int getLevels() {
        int counter = 0;
        try {
            is = this.getResources().openRawResource(R.raw.leveldata);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(is));
            String line = bufferedReader.readLine();
            while(line != null) {
                line = bufferedReader.readLine();
                counter++;
            }
            bufferedReader.close();
        } catch (IOException e) {
            main.runOnUiThread(new Runnable() {
                public void run() {
                    Toast.makeText(main, "Critical Error - Please Reinstall The Game", Toast.LENGTH_LONG).show();
                }
            });
        }
        return counter;
    }

    public boolean getMusicMuted() {
        return musicMuted;
    }

    public void setMusicMuted(boolean b) {
        musicMuted = b;
    }

    public void drawRectangle(int x, int y, int width, int height, Paint paint) {
        canvas.drawRect(x, y, x + width, y + height, paint);
    }

    public HellFireMain getMain() {
        return main;
    }

    public int[] getPlayerData() {
        int[] dataArray = new int[17];
        if(!main.checkPermission(Manifest.permission.READ_EXTERNAL_STORAGE)) {
            main.runOnUiThread(new Runnable() {
                public void run() {
                    Toast.makeText(main, "Player Data Not Loaded - Permission Denied", Toast.LENGTH_LONG).show();
                }
            });
            return dataArray;
        }
        if(Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState()) || Environment.MEDIA_MOUNTED_READ_ONLY.equals(Environment.getExternalStorageState())) {
            try {
                StringBuilder sb =  new StringBuilder();
                File file = new File(Environment.getExternalStorageDirectory() + "/HellFirePlayerData", "playerdata.txt");
                FileInputStream fis = new FileInputStream(file);
                if(fis != null) {
                    InputStreamReader isr = new InputStreamReader(fis);
                    BufferedReader br = new BufferedReader(isr);
                    String line = null;
                    for(int i = 0; i < dataArray.length; i++) {
                        line = br.readLine();
                        if(line != null) {
                            dataArray[i] = Integer.parseInt(line);
                        }
                    }
                    fis.close();
                }
                main.runOnUiThread(new Runnable() {
                    public void run() {
                        Toast.makeText(main, "Player Data Loaded", Toast.LENGTH_SHORT).show();
                    }
                });

            } catch (IOException e) {
                main.runOnUiThread(new Runnable() {
                    public void run() {
                        Toast.makeText(main, "Player Data Not Loaded - File Not Found", Toast.LENGTH_LONG).show();
                    }
                });
            }
        } else {
            main.runOnUiThread(new Runnable() {
                public void run() {
                    Toast.makeText(main, "Player Data Not Loaded - External Storage Error", Toast.LENGTH_LONG).show();
                }
            });
        }
        return dataArray;
    }

    public void savePlayerData(int[] data) {

        if(!main.checkPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            main.runOnUiThread(new Runnable() {
                public void run() {
                    Toast.makeText(main, "Player Data Not Saved - Permission Denied", Toast.LENGTH_LONG).show();
                }
            });
            return;
        }
        if(Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            File root = Environment.getExternalStorageDirectory();
            File dir = new File(root.getAbsolutePath() + "/HellFirePlayerData");
            if(!dir.exists()) {
                dir.mkdir();
            }
            File file = new File(dir, "/playerdata.txt");
            try {
                FileOutputStream fos = new FileOutputStream(file);
                for(int i = 0; i < data.length; i++) {
                    String str = "" + data[i] + "\n";
                    fos.write(str.getBytes());
                }
                fos.close();
                main.runOnUiThread(new Runnable() {
                    public void run() {
                        Toast.makeText(main, "Player Data Saved", Toast.LENGTH_SHORT).show();
                    }
                });
            } catch(Exception e) {
                main.runOnUiThread(new Runnable() {
                    public void run() {
                        Toast.makeText(main, "Player Data Not Saved - File Not Found", Toast.LENGTH_LONG).show();
                    }
                });
            }
        } else {
            Toast.makeText(main.getApplicationContext(), "Player Data Not Saved - External Storage Error", Toast.LENGTH_LONG).show();
        }

    }

    public void setStartLevel(int level) {
        startLevel = level;
        currentLevel = startLevel;
    }

    public int getStartLevel() {
        return startLevel;
    }

    public int getFPS() {
        return thread.getFPS();
    }

}