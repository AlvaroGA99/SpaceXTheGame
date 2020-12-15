package dadm.scaffold.engine;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

import dadm.scaffold.R;
import dadm.scaffold.space.SpaceShipPlayer;

public class lifeUi extends GameObject {

    private Matrix matrix;
    private double pixelFactor;
    private final Bitmap bitmap;
    private final SpaceShipPlayer ship;

    public lifeUi(GameEngine gameEngine, SpaceShipPlayer player){
        this.ship = player;
        Resources r = gameEngine.getContext().getResources();
        Drawable boxDrawable = r.getDrawable(R.drawable.elondamusk);
        matrix = new Matrix();
        pixelFactor = gameEngine.pixelFactor;
        this.bitmap = ((BitmapDrawable) boxDrawable).getBitmap();
    }

    @Override
    public void startGame() {

    }

    @Override
    public void onUpdate(long elapsedMillis, GameEngine gameEngine) {

    }

    @Override
    public void onDraw(Canvas canvas) {
        for(int i = 0; i <= ship.hp; i++ ){
            matrix.reset();
            matrix.postScale((float)(pixelFactor*0.75),(float)(pixelFactor*0.75) );
            matrix.postTranslate( 50 + i*150,75);
            canvas.drawBitmap(bitmap,matrix,null);
        }

    }
}
