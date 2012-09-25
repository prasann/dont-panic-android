package com.thoughtworks.utils;

public class JSONAttributes {

    public static class Country {
        public static String ITEMS = "countries";
        public static String ID = "id";
        public static String NAME = "name";
        public static String CODE = "code";
    }

    public static class City {
        public static String ITEMS = "cities";
        public static String ID = "id";
        public static String NAME = "name";
        public static String CODE = "code";
        public static String COUNTRY_ID = "country_id";
    }

    public static class Company {
        public static String ITEMS = "companies";
        public static String ID = "id";
        public static String NAME = "name";
    }

    public static class PlaceType {
        public static String ITEMS = "place_types";
        public static String ID = "id";
        public static String NAME = "name";
    }

    public static class Administrator {
        public static String ITEMS = "administrators";
        public static String ID = "id";
        public static String NAME = "name";
        public static String PHONE_NUMBERS = "phone_numbers";
        public static String EMAIL = "email";
        public static String OFFICE_ID = "office_id";
    }

    public static class Office {
        public static String ITEMS = "offices";
        public static String ID = "id";
        public static String NAME = "name";
        public static String ADDRESS = "address";
        public static String LONGITUDE = "longitude";
        public static String LATITUDE = "latitude";
        public static String PHONE_NUMBERS = "phone_numbers";
        public static String EMAIL = "email";
        public static String CITY_ID = "city_id";
        public static String COMPANY_ID = "company_id";
        public static String MAP = "map";
    }

    public static class Place {
        public static String ITEMS = "places";
        public static String ID = "id";
        public static String NAME = "name";
        public static String DESCRIPTION = "description";
        public static String ADDRESS = "address";
        public static String LONGITUDE = "longitude";
        public static String LATITUDE = "latitude";
        public static String PHONE_NUMBERS = "phone_numbers";
        public static String EMAIL = "email";
        public static String CITY_ID = "city_id";
        public static String PLACE_TYPE_ID = "place_type_id";
    }
}
