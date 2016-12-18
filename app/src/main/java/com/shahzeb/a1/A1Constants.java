package com.shahzeb.a1;

public class A1Constants {

    public final static String KEY_WA = "coding-puzzle-client-449cc9d";
    public final static int PAGE_SIZE = 10;

    public final static String URL_BASE = "http://api.wkda-test.com/";
    public final static String URL_TARGET = URL_BASE + "v1/car-types/";
    public final static String URL_MANUFACTURER = URL_TARGET + "manufacturer";
    public final static String URL_MAIN_TYPES = URL_TARGET + "main-types";
    public final static String URL_BUILT_DATES = URL_TARGET + "built-dates";

    public final static String PARAM_WA_KEY = "wa_key";
    public final static String PARAM_PAGE = "page";
    public final static String PARAM_PAGE_SIZE = "pageSize";
    public final static String PARAM_MANUFACTURER = "manufacturer";
    public final static String PARAM_MAIN_TYPE = "main-type";

    public final static String KEY_LIST_TYPE = "list_type";
    public final static String KEY_MANUFACTURER = "manufacturer";
    public final static String KEY_MAIN_TYPE = "main_type";
    public final static String KEY_BUILT_DATE = "built_date";
    public final static String KEY_SUMMARY = "summary";

    public final static int HTTP_SUCCESS = 200;
    public final static int HTTP_CLIENT_ERROR = 400;
    public final static int HTTP_SERVER_ERROR = 500;

    public final static String TAG_FRAGMENT_MANUFACTURER = "fragment_manufacturer";
    public final static String TAG_FRAGMENT_MAIN_TYPE = "fragment_main_type";
    public final static String TAG_FRAGMENT_BUILT_DATES = "fragment_built_dates";
    public final static String TAG_FRAGMENT_SUMMARY = "fragment_summary";

    public enum FragmentType {
        MANUFACTURER,
        MAIN_TYPE,
        BUILT_DATES,
        SUMMARY
    }

}
