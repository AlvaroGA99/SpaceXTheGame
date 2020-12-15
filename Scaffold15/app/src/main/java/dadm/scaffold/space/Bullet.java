package dadm.scaffold.space;

import dadm.scaffold.R;
import dadm.scaffold.engine.GameEngine;
import dadm.scaffold.engine.ScreenGameObject;
import dadm.scaffold.engine.Sprite;
import dadm.scaffold.sound.GameEvent;

public class Bullet extends Sprite {
    public int type; //0 red 1 blue
    private double speedFactor;

    private SpaceShipPlayer parent;

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
    }


    public void init(SpaceShipPlayer parentPlayer, double initPositionX, double initPositionY) {
        positionX = initPositionX - width/2;
        positionY = initPositionY - height/2;
        parent = parentPlayer;
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

                // Remove both from the game (and return them to their pools)
                removeObject(gameEngine);
                Asteroid a = (Asteroid) otherObject;
                a.removeObject(gameEngine);
                gameEngine.onGameEvent(GameEvent.AsteroidHit);
                // Add some score
            }
        }
    }
}
