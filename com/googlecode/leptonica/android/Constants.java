package com.googlecode.leptonica.android;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class Constants {
    public static final int IFF_BMP = 1;
    public static final int IFF_DEFAULT = 15;
    public static final int IFF_GIF = 13;
    public static final int IFF_JFIF_JPEG = 2;
    public static final int IFF_JP2 = 14;
    public static final int IFF_PNG = 3;
    public static final int IFF_PNM = 11;
    public static final int IFF_PS = 12;
    public static final int IFF_SPIX = 16;
    public static final int IFF_TIFF = 4;
    public static final int IFF_TIFF_G3 = 7;
    public static final int IFF_TIFF_G4 = 8;
    public static final int IFF_TIFF_LZW = 9;
    public static final int IFF_TIFF_PACKBITS = 5;
    public static final int IFF_TIFF_RLE = 6;
    public static final int IFF_TIFF_ZIP = 10;
    public static final int IFF_UNKNOWN = 0;
    public static final int L_CLONE = 2;
    public static final int L_COPY = 1;
    public static final int L_COPY_CLONE = 3;
    public static final int L_INSERT = 0;
    public static final int L_SORT_BY_AREA = 10;
    public static final int L_SORT_BY_ASPECT_RATIO = 11;
    public static final int L_SORT_BY_HEIGHT = 6;
    public static final int L_SORT_BY_MAX_DIMENSION = 8;
    public static final int L_SORT_BY_MIN_DIMENSION = 7;
    public static final int L_SORT_BY_PERIMETER = 9;
    public static final int L_SORT_BY_WIDTH = 5;
    public static final int L_SORT_BY_X = 3;
    public static final int L_SORT_BY_Y = 4;
    public static final int L_SORT_DECREASING = 2;
    public static final int L_SORT_INCREASING = 1;

    @Retention(RetentionPolicy.SOURCE)
    public @interface SortBy {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface SortOrder {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface StorageFlag {
    }
}
