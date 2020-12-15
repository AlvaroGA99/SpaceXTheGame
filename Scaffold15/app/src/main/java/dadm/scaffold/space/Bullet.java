package dadm.scaffold.space;

import android.widget.TextView;

import dadm.scaffold.R;
import dadm.scaffold.ScaffoldActivity;
import dadm.scaffold.counter.EndGameFragment;
import dadm.scaffold.counter.GameFragment;
import dadm.scaffold.engine.GameEngine;
import dadm.scaffold.engine.ScreenGameObject;
import dadm.scaffold.engine.Sprite;
import dadm.scaffold.sound.GameEvent;

public class Bullet extends Sprite {
    public int type; //0 red 1 blue
    private double speedFactor;

    private SpaceShipPlayer parent;

    private int movX;

    public Bullet(GameEngine gameEngine, int drawableRes){
        super(gameEngine, drawableRes);

        if(drawableRes == R.drawable.bluebullet){
            type = 0;
        }
        else{
            type = 1;
        }

        speedFactor = gameEngine.pixelFactor * -300d / 1000d;
    }

    @Override
    public void startGame() {}

    @Override
    public void onUpdate(long elapsedMillis, GameEngine gameEngine) {
        positionY += speedFactor * elapsedMillis;
        if (positionY < -height) {
            gameEngine.removeGameObject(this);
            // And return it to the pool
            parent.releaseBullet(this);
        }
        positionX += movX * 0.8f * speedFactor * elapsedMillis;
        if (positionX < -height) {
            gameEngine.removeGameObject(this);
            // And return it to the pool
            parent.releaseBullet(this);
        }
    }


    public void init(SpaceShipPlayer parentPlayer, double initPositionX, double initPositionY, int mX) {
        positionX = initPositionX - width/2;
        positionY = initPositionY - height/2;
        parent = parentPlayer;
        movX = mX;
    }

    private void removeObject(GameEngine gameEngine) {
        gameEngine.removeGameObject(this);
        // And return it to the pool
        parent.releaseBullet(this);
    }

    @Override
    public void onCollision(GameEngine gameEngine, ScreenGameObject otherObject) {
        if (otherObject instanceof Asteroid) {

            Asteroid ast = (Asteroid)otherObject;
            if(ast.type == this.type) {
                gameEngine.asteroid_counter += 100;
                // Remove both from the game (and return them to their pools)
                gameEngine.updateScore();
                removeObject(gameEngine);
                ast.removeObject(gameEngine);
                gameEngine.onGameEvent(GameEvent.AsteroidHit);
                // Add some score
                if(gameEngine.asteroid_counter == 2500){
                    gameEngine.stopGame();
                    ((ScaffoldActivity) gameEngine.mainActivity).navigateToFragment(new EndGameFragment(),"\nLEVEL COMPLETED\nYOU GOT "+ gameEngine.asteroid_counter + "/2500 POINTS");
                }
            }
        }
    }
}


