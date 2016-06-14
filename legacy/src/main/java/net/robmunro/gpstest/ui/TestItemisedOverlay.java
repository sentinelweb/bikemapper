package net.robmunro.gpstest.ui;

public class TestItemisedOverlay /*extends ItemizedOverlay<OverlayItem> */ {
    /*
    private ArrayList<OverlayItem> mOverlays = new ArrayList<OverlayItem>();
	boolean clickable = false;
	//LocationTestActivity act = null;
	private boolean addStar;
	private Point initialPoint;
	String id = null;
	OnCompleteListener<GeoPoint> onPointClickListener;
	public TestItemisedOverlay(Drawable defaultMarker,OnCompleteListener<GeoPoint> onPointClickListener,String id) {//,LocationTestActivity act
		//super(defaultMarker);
		super(boundCenterBottom(defaultMarker));
		this.clickable=onPointClickListener!=null;
		//this.act=act;
		this.onPointClickListener = onPointClickListener;
		this.id=id;
	}

	@Override
	protected OverlayItem createItem(int arg0) {
		return mOverlays.get(arg0);
	}

	public void addOverlay(OverlayItem overlay) {
	    mOverlays.add(overlay);
	    populate();
	}
	
	@Override
	public int size() {
		return mOverlays.size();
	}
	
	long downTimer = -1;
	@Override
	public boolean onTouchEvent(MotionEvent event, MapView mapView) {
		Log.d(LocationTestActivity.LOG_TAG, "on touch:"+id+":"+" click:"+this.clickable+":"+event.getAction() +" ; "+hashCode() +":dt:"+downTimer+":tm:"+(System.currentTimeMillis()-downTimer));
		if (this.clickable) {
			 if (event.getAction() == MotionEvent.ACTION_DOWN) {     
				 addStar = true;
				 initialPoint = new Point((int)event.getX(),(int)event.getY());
				 downTimer=System.currentTimeMillis();
			 } else if (event.getAction() == MotionEvent.ACTION_MOVE) {  
				 if (Math.abs(event.getX()-initialPoint.x)>10 || Math.abs(event.getY()-initialPoint.y)>20  ) {
					 addStar = false;
				 }
			 } else if (event.getAction() == MotionEvent.ACTION_UP) {     
				 //Log.d(LocationTestActivity.LOG_TAG, "on touch up");
				 GeoPoint p = mapView.getProjection().fromPixels(  (int) event.getX(), (int) event.getY());
				 if ( addStar ) {
					if ( System.currentTimeMillis() - downTimer > 500 ) {
		                //Log.d(LocationTestActivity.LOG_TAG, p.getLatitudeE6() / 1E6 + "," + p.getLongitudeE6() /1E6 );
		                if ( onPointClickListener!=null ) { onPointClickListener.onComplete(p); }
					 } else {
						 // Log.d(LocationTestActivity.LOCATION_TEST_TAG, "set currrent:"+p.getLatitudeE6()+":"+p.getLongitudeE6());
						 
						// act.setCurrentLocation(p);
					 }
					return false;
				 }
	         }                            
	         return false;
		}
		return false;
	}
	*/
    /*
    @Override
	public boolean onTap(GeoPoint p, MapView mapView) {
		GeoPoint point = new GeoPoint((int)(p.getLatitudeE6()/1E6) , (int)(p.getLongitudeE6() / 1E6));
        OverlayItem overlayitem = new OverlayItem(point, "touched", "t1");
        addOverlay(overlayitem);
		return super.onTap(p, mapView);
	}
	*/

}
