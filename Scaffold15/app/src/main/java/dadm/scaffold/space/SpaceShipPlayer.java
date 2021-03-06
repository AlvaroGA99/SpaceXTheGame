package dadm.scaffold.space;

import android.app.FragmentManager;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.support.v4.app.Fragment;

import java.util.ArrayList;
import java.util.List;

import dadm.scaffold.R;
import dadm.scaffold.ScaffoldActivity;
import dadm.scaffold.counter.EndGameFragment;
import dadm.scaffold.engine.GameEngine;
import dadm.scaffold.engine.ScreenGameObject;
import dadm.scaffold.engine.Sprite;
import dadm.scaffold.input.InputController;
import dadm.scaffold.sound.GameEvent;

public class SpaceShipPlayer extends Sprite {
    public int type; //0 blue 1 red

    private static final int INITIAL_BULLET_POOL_AMOUNT = 24;
    private static final long TIME_BETWEEN_BULLETS = 250;
    List<Bullet> redBullets = new ArrayList<Bullet>();
    List<Bullet> blueBullets = new ArrayList<Bullet>();
    private long timeSinceLastFire;
    private int ship;
    private int maxX;
    private int maxY;
    private double speedFactor;
    public byte hp = 2;


    public SpaceShipPlayer(GameEngine gameEngine,int ship){
        super(gameEngine, R.drawable.blueship);
        if(ship == 1){
            this.spriteDrawable = gameEngine.getContext().getResources().getDrawable(R.drawable.blueanothership);
            this.bitmap = bitmap = ((BitmapDrawable) spriteDrawable).getBitmap();
        }

        this.ship = ship;
        type = 0;
        speedFactor = pixelFactor * 150d / 1000d; // We want to move at 150px per second on a 400px tall screen
        maxX = gameEngine.width - width;
        maxY = gameEngine.height - height;

        initBulletPool(gameEngine);
    }

    private void initBulletPool(GameEngine gameEngine) {
        for (int i=0; i<INITIAL_BULLET_POOL_AMOUNT; i++) {
            redBullets.add(new Bullet(gameEngine, R.drawable.redbullet));
            blueBullets.add(new Bullet(gameEngine, R.drawable.bluebullet));
        }
    }

    private Bullet getBullet() {
        if (this.type == 1) {
            if (redBullets.isEmpty()) {
                return null;
            }
            return redBullets.remove(0);
        }
        else{
            if (blueBullets.isEmpty()) {
                return null;
            }

            return blueBullets.remove(0);
        }
    }

    void releaseBullet(Bullet bullet) {
        if(bullet.type == 1) {
            redBullets.add(bullet);
        }
        else {
            blueBullets.add(bullet);
        }
    }


    @Override
    public void startGame() {
        positionX = maxX / 2;
        positionY = maxY;
    }

    @Override
    public void onUpdate(long elapsedMillis, GameEngine gameEngine) {
        // Get the info from the inputController
        updatePosition(elapsedMillis, gameEngine.theInputController);
        checkFiring(elapsedMillis, gameEngine);
    }

    private void updatePosition(long elapsedMillis, InputController inputController) {
        positionX += speedFactor * inputController.horizontalFactor * elapsedMillis;
        if (positionX < 0) {
            positionX = 0;
        }
        if (positionX > maxX) {
            positionX = maxX;
        }
        positionY += speedFactor * inputController.verticalFactor * elapsedMillis;
        if (positionY < 0) {
            positionY = 0;
        }
        if (positionY > maxY) {
            positionY = maxY;
        }
    }

    private void checkFiring(long elapsedMillis, GameEngine gameEngine) {
        if (gameEngine.theInputController.colorSwitch){
            gameEngine.theInputController.colorSwitch = false;

            type ^= 1;

            if(type == 0){
                if(ship == 0){
                    this.spriteDrawable = gameEngine.getContext().getResources().getDrawable(R.drawable.blueship);
                }else{
                    this.spriteDrawable = gameEngine.getContext().getResources().getDrawable(R.drawable.blueanothership);
                }

            }
            else{
                if(ship == 0){
                    this.spriteDrawable = gameEngine.getContext().getResources().getDrawable(R.drawable.redship);
                }else{
                    this.spriteDrawable = gameEngine.getContext().getResources().getDrawable(R.drawable.redanothership);
                }
            }

            this.bitmap = bitmap = ((BitmapDrawable) spriteDrawable).getBitmap();
        }
        if(timeSinceLastFire > TIME_BETWEEN_BULLETS) {
            if(gameEngine.theInputController.isMoving) {
                Bullet bullet1 = getBullet();
                if (bullet1 == null) {
                    return;
                }
                Bullet bullet2 = getBullet();
                if (bullet2 == null) {
                    releaseBullet(bullet1);
                    return;
                }
                bullet1.init(this, positionX + width / 2, positionY, 0);
                bullet2.init(this, positionX + width / 2 - 75, positionY, 0);
                gameEngine.addGameObject(bullet1);
                gameEngine.addGameObject(bullet2);
                timeSinceLastFire = 0;
                gameEngine.onGameEvent(GameEvent.LaserFired);
            }
            if (gameEngine.theInputController.isFiring) {
                Bullet bullet3 = getBullet();
                if (bullet3 == null) {
                    return;
                }
                Bullet bullet4 = getBullet();
                if (bullet4 == null) {
                    releaseBullet(bullet3);
                    return;
                }
                Bullet bullet5 = getBullet();
                if (bullet5 == null) {
                    releaseBullet(bullet3);
                    releaseBullet(bullet4);
                    return;
                }
                Bullet bullet6 = getBullet();
                if (bullet6 == null) {
                    releaseBullet(bullet3);
                    releaseBullet(bullet4);
                    releaseBullet(bullet5);
                    return;
                }
                bullet3.init(this, positionX + width / 2, positionY, 1);
                bullet4.init(this, positionX + width / 2 - 75, positionY, 1);
                bullet5.init(this, positionX + width / 2, positionY, -1);
                bullet6.init(this, positionX + width / 2 - 75, positionY, -1);
                gameEngine.addGameObject(bullet3);
                gameEngine.addGameObject(bullet4);
                gameEngine.addGameObject(bullet5);
                gameEngine.addGameObject(bullet6);
                gameEngine.onGameEvent(GameEvent.LaserFired);
                timeSinceLastFire = 0;
            }
        }
        else {
            timeSinceLastFire += elapsedMillis;
        }
    }

    @Override
    public void onCollision(GameEngine gameEngine, ScreenGameObject otherObject) {
        if (otherObject instanceof Asteroid){
            Asteroid ast = (Asteroid)otherObject;
            if(ast.type != this.type) {

                //gameEngine.removeGameObject(this);
                //gameEngine.stopGame();
                ast.removeObject(gameEngine);
                gameEngine.onGameEvent(GameEvent.SpaceshipHit);
                if (this.hp > 0) {
                    this.hp--;
                } else {
                    gameEngine.stopGame();
                    ((ScaffoldActivity) gameEngine.mainActivity).navigateToFragment(new EndGameFragment(),"\nGAME OVER\nYOU GOT "+ gameEngine.asteroid_counter + "/2500 POINTS");

                }
            }
        }
    }

    private FragmentManager getSupportFragmentManager() {
        return null;
    }
}
