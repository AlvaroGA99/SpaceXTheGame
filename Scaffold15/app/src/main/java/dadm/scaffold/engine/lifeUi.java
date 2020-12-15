package dadm.scaffold.engine;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

import dadm.scaffold.R;

public class lifeUi extends GameObject {

    private Matrix matrix;
    private double pixelFactor;
    private final Bitmap bitmap;

    public lifeUi(GameEngine gameEngine){
        Resources r = gameEngine.getContext().getResources();
        Drawable boxDrawable = r.getDrawable(R.drawable.butt);
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
        matrix.reset();
        matrix.postScale((float)pixelFactor/6,(float)pixelFactor/6 );
        matrix.postTranslate(50,50);
        canvas.drawBitmap(bitmap,matrix,null);
    }
}
