package com.change.likeyixin;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;

public class BitmapHalper {
	private Context context;
	public BitmapHalper(Context context) {
		this.context = context;
	}
	public Bitmap getRoundedCornerBitmap(Bitmap bitmap){
        Bitmap outBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Config.ARGB_8888);
        Canvas canvas = new Canvas(outBitmap);
        final int color =0xff424242;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0,0,bitmap.getWidth(),bitmap.getHeight());
        final RectF rectF = new RectF(rect);
        final float roundPX = bitmap.getWidth()/2;
        paint.setAntiAlias(true);
        canvas.drawARGB(0,0,0,0);
        paint.setColor(color);
        canvas.drawRoundRect(rectF, roundPX, roundPX, paint);
        paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);
        return outBitmap;
    }
	
	public Bitmap getWhiteCicleBitmap(Bitmap bitmap){
	    Bitmap outBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Config.ARGB_8888);
        Canvas canvas = new Canvas(outBitmap);
        BitmapFactory.Options opt = new Options();
        final Paint paint = new Paint();
        opt.inPreferredConfig = Config.ARGB_8888;
        Bitmap cicle = BitmapFactory.decodeResource(context.getResources(), R.drawable.head_cover_profile, opt);
        Bitmap head = getRoundedCornerBitmap(bitmap);
        canvas.drawBitmap(head, 0, 0, paint);
        canvas.drawBitmap(cicle,0,0,paint);
        return outBitmap;
	}
}
