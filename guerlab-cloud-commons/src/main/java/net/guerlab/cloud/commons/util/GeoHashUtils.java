/*
 * Copyright 2018-2022 guerlab.net and other contributors.
 *
 * Licensed under the GNU LESSER GENERAL PUBLIC LICENSE, Version 3 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.gnu.org/licenses/lgpl-3.0.html
 *
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package net.guerlab.cloud.commons.util;

import net.guerlab.cloud.commons.domain.Geo;
import net.guerlab.cloud.commons.domain.GeoHash;

import java.math.BigDecimal;

/**
 * 地理hash工具
 *
 * @author guer
 */
public class GeoHashUtils {

    private static final int[] BITS = { 16, 8, 4, 2, 1 };

    private static final String BASE32 = "0123456789bcdefghjkmnpqrstuvwxyz";

    private static final int RIGHT = 0;

    private static final int LEFT = 1;

    private static final int TOP = 2;

    private static final int BOTTOM = 3;

    private static final int EVEN = 0;

    private static final int ODD = 1;

    private static final String[][] NEIGHBORS;

    private static final String[][] BORDERS;

    static {
        NEIGHBORS = new String[4][2];
        BORDERS = new String[4][2];

        NEIGHBORS[BOTTOM][EVEN] = "bc01fg45238967deuvhjyznpkmstqrwx";
        NEIGHBORS[TOP][EVEN] = "238967debc01fg45kmstqrwxuvhjyznp";
        NEIGHBORS[LEFT][EVEN] = "p0r21436x8zb9dcf5h7kjnmqesgutwvy";
        NEIGHBORS[RIGHT][EVEN] = "14365h7k9dcfesgujnmqp0r2twvyx8zb";

        BORDERS[BOTTOM][EVEN] = "bcfguvyz";
        BORDERS[TOP][EVEN] = "0145hjnp";
        BORDERS[LEFT][EVEN] = "prxz";
        BORDERS[RIGHT][EVEN] = "028b";

        NEIGHBORS[BOTTOM][ODD] = NEIGHBORS[LEFT][EVEN];
        NEIGHBORS[TOP][ODD] = NEIGHBORS[RIGHT][EVEN];
        NEIGHBORS[LEFT][ODD] = NEIGHBORS[BOTTOM][EVEN];
        NEIGHBORS[RIGHT][ODD] = NEIGHBORS[TOP][EVEN];

        BORDERS[BOTTOM][ODD] = BORDERS[LEFT][EVEN];
        BORDERS[TOP][ODD] = BORDERS[RIGHT][EVEN];
        BORDERS[LEFT][ODD] = BORDERS[BOTTOM][EVEN];
        BORDERS[RIGHT][ODD] = BORDERS[TOP][EVEN];
    }

    private GeoHashUtils() {

    }

    private static void refineInterval(double[] interval, int cd, int mask) {
        if ((cd & mask) > 0) {
            interval[0] = (interval[0] + interval[1]) / 2.0;
        } else {
            interval[1] = (interval[0] + interval[1]) / 2.0;
        }
    }

    private static String calculateAdjacent(final String hash, int dir) {
        String srcHash = hash.toLowerCase();

        char lastChr = srcHash.charAt(srcHash.length() - 1);
        int type = srcHash.length() % 2 == 1 ? ODD : EVEN;

        String base = srcHash.substring(0, srcHash.length() - 1);

        if (BORDERS[dir][type].indexOf(lastChr) != -1) {
            base = calculateAdjacent(base, dir);
        }

        return base + BASE32.charAt(NEIGHBORS[dir][type].indexOf(lastChr));
    }

    /**
     * 获取相邻的geoHash
     *
     * @param geoHash
     *         geoHash
     * @param length
     *         长度
     * @return 相邻的geoHash
     */
    public static GeoHash getGeoHashExpand(final String geoHash, int length) {
        int maxLength = Math.min(geoHash.length(), length);

        String hash = geoHash.substring(0, maxLength);

        String top = calculateAdjacent(hash, TOP);
        String bottom = calculateAdjacent(hash, BOTTOM);
        String right = calculateAdjacent(hash, RIGHT);
        String left = calculateAdjacent(hash, LEFT);

        String topLeft = calculateAdjacent(left, TOP);
        String topRight = calculateAdjacent(right, TOP);
        String bottomRight = calculateAdjacent(right, BOTTOM);
        String bottomLeft = calculateAdjacent(left, BOTTOM);

        return new GeoHash(hash, top, bottom, right, left, topLeft, topRight, bottomRight, bottomLeft);
    }

    /**
     * 解析
     *
     * @param geoHash
     *         geoHash
     * @return 地理对象
     */
    @SuppressWarnings("unused")
    public static Geo decode(String geoHash) {
        boolean even = true;
        double[] lat = new double[3];
        double[] lon = new double[3];

        lat[0] = -90.0;
        lat[1] = 90.0;
        lon[0] = -180.0;
        lon[1] = 180.0;

        for (int i = 0; i < geoHash.length(); i++) {
            char c = geoHash.charAt(i);
            int cd = BASE32.indexOf(c);
            for (int mask : BITS) {
                refineInterval(even ? lon : lat, cd, mask);
                even = !even;
            }
        }
        lat[2] = (lat[0] + lat[1]) / 2.0;
        lon[2] = (lon[0] + lon[1]) / 2.0;

        return new Geo(BigDecimal.valueOf(lon[2]), BigDecimal.valueOf(lat[2]));
    }

    /**
     * 编码geoHash
     *
     * @param longitude
     *         经度
     * @param latitude
     *         纬度
     * @return geoHash
     */
    public static String encode(BigDecimal longitude, BigDecimal latitude) {
        return encode(longitude.doubleValue(), latitude.doubleValue());
    }

    /**
     * 编码geoHash
     *
     * @param longitude
     *         经度
     * @param latitude
     *         纬度
     * @return geoHash
     */
    public static String encode(double longitude, double latitude) {
        boolean even = true;
        double[] lat = new double[3];
        double[] lon = new double[3];
        int bit = 0;
        int ch = 0;
        int precision = 12;
        StringBuilder geoHash = new StringBuilder();

        lat[0] = -90.0;
        lat[1] = 90.0;
        lon[0] = -180.0;
        lon[1] = 180.0;

        while (geoHash.length() < precision) {
            if (even) {
                double mid = (lon[0] + lon[1]) / 2.0;
                if (longitude > mid) {
                    ch |= BITS[bit];
                    lon[0] = mid;
                } else {
                    lon[1] = mid;
                }
            } else {
                double mid = (lat[0] + lat[1]) / 2.0;
                if (latitude > mid) {
                    ch |= BITS[bit];
                    lat[0] = mid;
                } else {
                    lat[1] = mid;
                }
            }
            even = !even;
            if (bit < 4) {
                bit++;
            } else {
                geoHash.append(BASE32.charAt(ch));
                bit = 0;
                ch = 0;
            }
        }
        return geoHash.toString();
    }
}
