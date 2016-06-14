/**
 * 
 */
package net.robmunro.gpstest.util;

import net.robmunro.gpstest.DirectionsRequest;

public abstract class OnCompleteListener<SrcType>{
	public abstract void onComplete(SrcType o);
}