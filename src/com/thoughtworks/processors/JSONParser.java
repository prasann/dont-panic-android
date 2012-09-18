package com.thoughtworks.processors;

import android.util.Log;
import com.thoughtworks.models.*;
import com.thoughtworks.utils.JSONAttributes;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.thoughtworks.utils.Constants.*;

public class JSONParser {

    public static Map<String, Object> parse(String json) {
        Map<String, Object> objectMap = new HashMap<String, Object>();
        JSONParser jsonParser = new JSONParser();
        try {
            JSONObject jsonObject = new JSONObject(json);
            objectMap.put(OBJ_MAP_COUNTRIES, jsonParser.countries(jsonObject));
            objectMap.put(OBJ_MAP_CITIES, jsonParser.cities(jsonObject));
            objectMap.put(OBJ_MAP_COMPANIES, jsonParser.companies(jsonObject));
            objectMap.put(OBJ_MAP_ADMINISTRATORS, jsonParser.administrators(jsonObject));
            objectMap.put(OBJ_MAP_OFFICES, jsonParser.offices(jsonObject));
            objectMap.put(OBJ_MAP_PLACES, jsonParser.places(jsonObject));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return objectMap;
    }

    private List<Country> countries(JSONObject jsonObject) throws JSONException {
        JSONArray countriesJSON = (JSONArray) jsonObject.get(JSONAttributes.Country.ITEMS);
        List<Country> countries = new ArrayList<Country>();
        for (int i = 0; i < countriesJSON.length(); i++) {
            JSONObject countryAttrs = countriesJSON.getJSONObject(i);
            countries.add(new Country((Integer) countryAttrs.get(JSONAttributes.Country.ID),
                    (String) countryAttrs.get(JSONAttributes.Country.NAME),
                    (String) countryAttrs.get(JSONAttributes.Country.CODE)));
        }
        return countries;
    }

    private List<City> cities(JSONObject jsonObject) throws JSONException {
        JSONArray citiesJSON = (JSONArray) jsonObject.get(JSONAttributes.City.ITEMS);
        List<City> cities = new ArrayList<City>();
        for (int i = 0; i < citiesJSON.length(); i++) {
            JSONObject cityAttrs = citiesJSON.getJSONObject(i);
            cities.add(new City((Integer) cityAttrs.get(JSONAttributes.City.ID),
                    (String) cityAttrs.get(JSONAttributes.City.NAME),
                    (String) cityAttrs.get(JSONAttributes.City.CODE),
                    (Integer) cityAttrs.get(JSONAttributes.City.COUNTRY_ID)));
        }
        return cities;
    }

    private List<Company> companies(JSONObject jsonObject) throws JSONException {
        JSONArray companiesJSON = (JSONArray) jsonObject.get(JSONAttributes.Company.ITEMS);
        List<Company> companies = new ArrayList<Company>();
        for (int i = 0; i < companiesJSON.length(); i++) {
            JSONObject companyAttrs = companiesJSON.getJSONObject(i);
            companies.add(new Company((Integer) companyAttrs.get(JSONAttributes.Company.ID),
                    (String) companyAttrs.get(JSONAttributes.Company.NAME)));
        }
        return companies;
    }

    private List<Administrator> administrators(JSONObject jsonObject) throws JSONException {
        JSONArray administratorsJSON = (JSONArray) jsonObject.get(JSONAttributes.Administrator.ITEMS);
        List<Administrator> administrators = new ArrayList<Administrator>();
        for (int i = 0; i < administratorsJSON.length(); i++) {
            JSONObject administratorsAttrs = administratorsJSON.getJSONObject(i);
            administrators.add(new Administrator((Integer) administratorsAttrs.get(JSONAttributes.Administrator.ID),
                    (String) administratorsAttrs.get(JSONAttributes.Administrator.NAME),
                    (String) administratorsAttrs.get(JSONAttributes.Administrator.PHONE_NUMBERS),
                    (String) administratorsAttrs.get(JSONAttributes.Administrator.EMAIL),
                    (Integer) administratorsAttrs.get(JSONAttributes.Administrator.OFFICE_ID)));
        }
        return administrators;
    }

    private List<Office> offices(JSONObject jsonObject) throws JSONException {
        JSONArray officesJSON = (JSONArray) jsonObject.get(JSONAttributes.Office.ITEMS);
        List<Office> offices = new ArrayList<Office>();
        for (int i = 0; i < officesJSON.length(); i++) {
            JSONObject officesAttrs = officesJSON.getJSONObject(i);
            offices.add(new Office((Integer) officesAttrs.get(JSONAttributes.Office.ID),
                    (String) officesAttrs.get(JSONAttributes.Office.NAME),
                    (String) officesAttrs.get(JSONAttributes.Office.ADDRESS),
                    toDouble(officesAttrs, JSONAttributes.Office.LONGITUDE),
                    toDouble(officesAttrs, JSONAttributes.Office.LATITUDE),
                    (String) officesAttrs.get(JSONAttributes.Office.PHONE_NUMBERS),
                    (String) officesAttrs.get(JSONAttributes.Office.EMAIL),
                    (Integer) officesAttrs.get(JSONAttributes.Office.CITY_ID),
                    (Integer) officesAttrs.get(JSONAttributes.Office.COMPANY_ID)));
        }
        return offices;
    }

    private List<Place> places(JSONObject jsonObject) throws JSONException {
        JSONArray officesJSON = (JSONArray) jsonObject.get(JSONAttributes.Place.ITEMS);
        List<Place> places = new ArrayList<Place>();
        for (int i = 0; i < officesJSON.length(); i++) {
            JSONObject placesAttrs = officesJSON.getJSONObject(i);
            places.add(new Place((Integer) placesAttrs.get(JSONAttributes.Place.ID),
                    (String) placesAttrs.get(JSONAttributes.Place.NAME),
                    (String) placesAttrs.get(JSONAttributes.Place.DESCRIPTION),
                    (String) placesAttrs.get(JSONAttributes.Place.ADDRESS),
                    toDouble(placesAttrs, JSONAttributes.Place.LONGITUDE),
                    toDouble(placesAttrs, JSONAttributes.Place.LATITUDE),
                    (String) placesAttrs.get(JSONAttributes.Place.PHONE_NUMBERS),
                    (String) placesAttrs.get(JSONAttributes.Place.EMAIL),
                    (Integer) placesAttrs.get(JSONAttributes.Place.PLACE_TYPE_ID),
                    (Integer) placesAttrs.get(JSONAttributes.Place.CITY_ID)));
        }
        return places;
    }

    private Double toDouble(JSONObject attrs, String value) throws JSONException {
        Double val = new Double("0");
        try {
            val = (Double) attrs.get(value);
        } catch (Exception e) {
            Log.w("Double Conversion", "Error while converting : " + value);
        }
        return val;
    }
}
