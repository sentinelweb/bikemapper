package net.robmunro.gpstest.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Vector;

import android.app.Activity;
import android.content.Context;
import android.util.Log;



public class MyTimer {
	public static Vector<Timer> timers = new Vector<Timer>();
	public static boolean debug = false;// turn on to check timer activates and cancels
	/**
	 * @param object the Activity that the timer is called from
	 * @param method the method to call in the activity
	 * @param interval the interval between method calls
	 * @param isUI states that the method should be called from the UI thread
	 * @return
	 */
	public static int setInterval(Context object,String method,int interval,boolean isUI) {
		Timer activateTimer = activateTimer(object, method, interval, isUI,false);
		if (activateTimer!=null) {
			return activateTimer.hashCode();
		}
		return -1;
	}
	public static Timer setOneshot(Context object,String method,int interval,boolean isUI) {
		return activateTimer(object, method, interval, isUI,true);
	}
	public static Timer activateTimer(Context object, String method,int interval, boolean isUI,boolean oneshot) {
		Timer timeoutTimer= new Timer();
		TimeoutTask timeoutTimerTask;
		try {
			timeoutTimerTask = new TimeoutTask(object,method,isUI,timeoutTimer.hashCode());
			if (debug) {Log.d(MyTimer.class.getSimpleName(), "timer created :");}
		} catch (Exception e) {
			Log.d(MyTimer.class.getSimpleName(), "error creating timer :"+method,e);
			return null;
		} 
		if (oneshot) {
			timeoutTimer.schedule(timeoutTimerTask, interval);
		} else {
			timeoutTimer.scheduleAtFixedRate(timeoutTimerTask, 0, interval);	
			timers.add(timeoutTimer);
		}
		return timeoutTimer;
	}
	
	public static int cancelTimer(int code) {
		Timer timeoutTimer = null;
		for (int i=0;i<timers.size();i++) {
			if (timers.get(i).hashCode()==code) {
				timeoutTimer=timers.get(i);
			}
		}
		if (cancelTimer(timeoutTimer)) {
			return -1;
		} else {
			return code;
		}
		/*
		if (timeoutTimer!=null) {
			timers.remove(timeoutTimer);
			timeoutTimer.cancel();
			timeoutTimer.purge();
			if (debug) {Log.d(MyTimer.class.getSimpleName(), "timer deleted :");}
			return -1;
		} else {
			if (debug) {Log.d(MyTimer.class.getSimpleName(), "timer not found :"+code);}
		}
		return code;
		*/
	}
	public static boolean cancelTimer(Timer timeoutTimer) {
		if (timeoutTimer!=null) {
			timers.remove(timeoutTimer);
			timeoutTimer.cancel();
			timeoutTimer.purge();
			if (debug) {Log.d(MyTimer.class.getSimpleName(), "timer deleted :");}
			return true;
		} else {
			if (debug) {Log.d(MyTimer.class.getSimpleName(), "timer nnull :");}
		}
		return false;
	}
	
	private static class TimeoutTask extends TimerTask {
		Context targetObject;
		Method targetMethod;
		Runnable invoker;
		int code ;
		public TimeoutTask(Context targetObject, String targetMethod,boolean isUI,int code) throws SecurityException, NoSuchMethodException {
			super();
			this.targetObject = targetObject;
			this.targetMethod = targetObject.getClass().getDeclaredMethod(targetMethod, new Class[]{});
			this.code=code;
			if (isUI) {
				Activity a = (Activity)targetObject;// will throw a class cast if use incorrectly, eg invoking ui from service
				invoker = new ThreadRunner((Activity)this.targetObject,this.targetMethod);
			}
		}
		
		@Override
		public void run() {
			try {
				if (debug) {Log.d(MyTimer.class.getSimpleName(), "invoke timer :");}
				if (invoker==null) {
					targetMethod.invoke(targetObject, new Object[]{});
				} else {
					((Activity)targetObject).runOnUiThread(invoker);
				}
			} catch(Exception e) {
				Log.d(MyTimer.class.getSimpleName(), "error invoking:"+targetMethod.getName(),e);
				cancelTimer(code);
				Log.d(MyTimer.class.getSimpleName(), "timer:"+code+" has been cancelled");
			}
		}
		
	}
	
	private static class ThreadRunner implements Runnable{
		Activity targetObject;
		Method targetMethod;
		
		public ThreadRunner(Activity targetObject, Method targetMethod) {
			super();
			this.targetObject = targetObject;
			this.targetMethod = targetMethod;
		}

		@Override
		public void run() {
			try {
				targetMethod.invoke(targetObject, new Object[]{});
			} catch (Exception e) {
				Log.d(MyTimer.class.getSimpleName(), "error invoking:"+targetMethod.getName(),e);
			}
		}
	}
	
	public static void runOnUI(final Activity act,String method,final Object data) {
		try {
			Class[] sig=null;
			//Log.d(MyTimer.class.getSimpleName(),"data class:"+data.getClass().getSimpleName()+":"+data.getClass().getClass().getSimpleName());
			if (data!=null) {
				sig = new Class[]{data.getClass()};
			} else {
				sig = new Class[]{};
			}
			final Method targetMethod = act.getClass().getDeclaredMethod(method, sig);
			act.runOnUiThread(new Runnable() {
				@Override
				public void run() {
					try {
						targetMethod.invoke(act, new Object[]{data});
					} catch (Exception e) {
						Log.d(MyTimer.class.getSimpleName(), "error invoking method :"+targetMethod.getName(),e);
					}
				}
			});
		} catch (Exception e) {
			Log.d(MyTimer.class.getSimpleName(), "error getting method :"+method,e);
		} 
		
	}
}
