package models;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.PorterDuff.Mode;
import android.graphics.drawable.Drawable;

import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;

import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;

import java.util.AbstractList;
import java.util.Iterator;

public class BitmapDescriptorDesigner {

    private Context context;
    private AbstractList<Drawable> drawables;

    public BitmapDescriptorDesigner(Context context, AbstractList<Drawable> drawables) {
        this.context = context;
        this.drawables = drawables;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public Context getContext() {
        return this.context;
    }

    public void setDrawables(AbstractList<Drawable> drawables) {
        this.drawables = drawables;
    }

    public AbstractList<Drawable> getDrawables() {
        return this.drawables;
    }

    public void drawDrawableFromID(int id, int color, int left, int top, int right, int bottom) {
        if (this.context != null) {
            if (this.drawables != null) {
                Drawable drawable = ContextCompat.getDrawable(this.context, id);
                DrawableCompat.setTint(drawable, color);
                DrawableCompat.setTintMode(drawable, Mode.SRC_IN);
                drawable.setBounds(left, top, right, bottom);
                this.drawables.add(drawable);
            }
        }
    }

    public BitmapDescriptor getBitmapDescriptor(int width, int height, int dstWidth, int dstHeight) {
        BitmapDescriptor bitmapDescriptor = null;
        if (this.drawables != null) {
            Bitmap bitmap = Bitmap.createBitmap(width, height, Config.ARGB_8888);
            Bitmap.createScaledBitmap(bitmap, dstWidth, dstHeight, false);
            Canvas canvas = new Canvas(bitmap);
            Iterator<Drawable> iterator = this.drawables.iterator();
            while (iterator.hasNext()) {
                Drawable drawable = iterator.next();
                if (drawable != null) {
                    drawable.draw(canvas);
                }
            }
            bitmapDescriptor = BitmapDescriptorFactory.fromBitmap(bitmap);
        }
        return bitmapDescriptor;
    }

    public void resetDrawables() {
        if (this.drawables != null) {
            this.drawables.removeAll(this.drawables);
        }
    }

}