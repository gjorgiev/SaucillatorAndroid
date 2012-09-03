package com.mattfeury.saucillator.dev.android.visuals;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.Paint.Align;

public class TabSelector extends RectButton {
  private Path polygon = new Path();
  private Path textPath = new Path();
  private static final float angleHeight = 0.2f;

  public TabSelector(String name) {
    this(name, 0, 0, 0, 0);
  }
  public TabSelector(String name, int x, int y, int width, int height) {
    super(name, x, y, x + width, y + height);

    recalculatePath();

    bg.setARGB(200, 12, 81, 4);
    focusedBg.setARGB(255, 12, 81, 4);

    bg.setStyle(Paint.Style.STROKE);
    focusedBg.setStyle(Paint.Style.FILL_AND_STROKE);
    bg.setStrokeWidth(2);
    focusedBg.setStrokeWidth(2);
    
    text.setSubpixelText(false);
    text.setAntiAlias(false);
  }
  
  public void set(int left, int top, int right, int bottom) {
    super.set(left, top, right, bottom);

    recalculatePath();
  }

  private void recalculatePath() {
    polygon.moveTo(right, top);
    polygon.lineTo(right, bottom);
    polygon.lineTo(left, bottom - (bottom - top) * angleHeight);
    polygon.lineTo(left, top + (bottom - top) * angleHeight);
    polygon.lineTo(right, top);
    polygon.close();
    
    textPath.moveTo(left + (right - left) / 2f, bottom);
    textPath.lineTo(left + (right - left) / 2f, top);
    textPath.close();
  }

  public void draw(Canvas canvas) {
    if (focused) {
      canvas.drawPath(polygon, focusedBg);
    } else {
      canvas.drawPath(polygon, bg);
    }

    canvas.save();
    canvas.rotate(-90, (right + left) / 2f, (bottom + top) / 2);
    canvas.drawText(name, (right + left) / 2f, (bottom + top) / 2, text);
    canvas.restore();
  }
}