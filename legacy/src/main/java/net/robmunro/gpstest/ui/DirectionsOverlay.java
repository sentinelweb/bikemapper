package net.robmunro.gpstest.ui;

import android.graphics.Bitmap;
import android.graphics.Paint;

import net.robmunro.gpstest.model.Directions;

@Deprecated
public class DirectionsOverlay /*extends Overlay*/ {
    public static final String TYPE_DRIVING = "driving";
    public static final String TYPE_WALKING = "walking";
    public static final String TYPE_BICYCLE = "bicycling";
    public static final int TYPE_DRIVING_INT = 1;
    public static final int TYPE_WALKING_INT = 2;
    public static final int TYPE_BICYCLE_INT = 3;

    String type = null;
    int typeInt = TYPE_WALKING_INT;
    //private GeoPoint gp1;
    //private GeoPoint gp2;
    private final int mRadius = 6;
    private final int defaultColor;
    Paint paint = new Paint();
    private Bitmap img = null;

    private String text = "";
    //public Vector<Directions.Step> directions= new Vector<Directions.Step>();

    Directions directions;


    public DirectionsOverlay(final String type) {// GeoPoint is a int. (6E)GeoPoint gp1,GeoPoint gp2,

        this.type = type;
        defaultColor = 999; // no defaultColor
        paint.setAntiAlias(true);

    }

    public DirectionsOverlay(final String type, final int defaultColor) {//GeoPoint gp1,GeoPoint gp2,
        this.type = type;
        this.defaultColor = defaultColor;
        paint.setAntiAlias(true);
    }

    public void setText(final String t) {
        this.text = t;
    }

    public void setBitmap(final Bitmap bitmap) {
        this.img = bitmap;
    }

    public void setDirections(final Directions directions) {
        this.directions = directions;
    }
//
//    @Override
//    public boolean draw(final Canvas canvas, final MapView mapView, final boolean shadow, final long when) {
//        final Projection projection = mapView.getProjection();
//        if (this.directions.route != null) {
//            for (int j = 0; j < this.directions.route.legs.size(); j++) {
//                //this.directions.currentLeg = j;
//                final Leg currLeg = this.directions.route.legs.get(j);
//                for (int i = 0; i < currLeg.steps.size(); i++) {
//                    final Directions.Step d = currLeg.steps.get(i);
//                    if (shadow == false) {
//
//                        final Point point = new Point();
//                        projection.toPixels(d.start, point);
//                        // mode=1&#65306;start
//                        paint.setColor(defaultColor);
//                        if (i == currLeg.selectedStep) {
//                            paint.setARGB(192, 0, 0, 255);
//                        } else if (i == currLeg.selectedStep + 1) {
//                            paint.setARGB(192, 128, 128, 255);
//                        }
//                        if (typeInt == TYPE_DRIVING_INT) {
//                            final RectF oval = new RectF(point.x - mRadius, point.y - mRadius,
//                                    point.x + mRadius, point.y + mRadius);
//                            // start point
//                            canvas.drawOval(oval, paint);
//                        } else if (typeInt == TYPE_WALKING_INT) {
//                            final Point point2 = new Point();
//                            projection.toPixels(d.end, point2);
//                            paint.setStrokeWidth(5);
//                            paint.setAlpha(120);
//                            canvas.drawLine(point.x, point.y, point2.x, point2.y, paint);
//                        } else if (typeInt == TYPE_BICYCLE_INT) {
//                            final Point point2 = new Point();
//                            projection.toPixels(d.end, point2);
//                            paint.setStrokeWidth(5);
//                            paint.setAlpha(120);
//                            canvas.drawLine(point.x, point.y, point2.x, point2.y, paint);
//                            final RectF oval = new RectF(point2.x - mRadius, point2.y - mRadius,
//                                    point2.x + mRadius, point2.y + mRadius);
//                            /* end point */
//                            paint.setAlpha(255);
//                            canvas.drawOval(oval, paint);
//                        }
//                    }
//                }
//            }
//        }
//        return super.draw(canvas, mapView, shadow, when);
//    }


}
