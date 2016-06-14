package net.robmunro.gpstest.util;

/*
 * Copyright (C) 2008 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      <!-- m --><a class="postlink" href="http://www.apache.org/licenses/LICENSE-2.0">http://www.apache.org/licenses/LICENSE-2.0</a><!-- m -->
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import com.google.android.gms.maps.model.LatLng;

/**
 * Library for some use useful latitude/longitude math
 */
public class GeoUtils {

    private static final int EARTH_RADIUS_KM = 6371;
    public static double MILLION = 1000000;

    /**
     * Computes the bearing in degrees between two points on Earth.
     *
     * @param lat1 Latitude of the first point
     * @param lon1 Longitude of the first point
     * @param lat2 Latitude of the second point
     * @param lon2 Longitude of the second point
     * @return Bearing between the two points in degrees. A value of 0 means due
     * north.
     */
    public static double bearing(final double lat1, final double lon1, final double lat2, final double lon2) {
        final double lat1Rad = Math.toRadians(lat1);
        final double lat2Rad = Math.toRadians(lat2);
        final double deltaLonRad = Math.toRadians(lon2 - lon1);

        final double y = Math.sin(deltaLonRad) * Math.cos(lat2Rad);
        final double x = Math.cos(lat1Rad) * Math.sin(lat2Rad) - Math.sin(lat1Rad) * Math.cos(lat2Rad)
                * Math.cos(deltaLonRad);
        return radToBearing(Math.atan2(y, x));
    }

    /**
     * Computes the bearing in degrees between two points on Earth.
     *
     * @param p1 First point
     * @param p2 Second point
     * @return Bearing between the two points in degrees. A value of 0 means due
     * north.
     */
    public static double bearing(final LatLng p1, final LatLng p2) {
        final double lat1 = p1.latitude;
        final double lon1 = p1.longitude;
        final double lat2 = p2.latitude;
        final double lon2 = p2.longitude;

        return bearing(lat1, lon1, lat2, lon2);
    }


    /**
     * Converts an angle in radians to degrees
     */
    public static double radToBearing(final double rad) {
        return (Math.toDegrees(rad) + 360) % 360;
    }

}

