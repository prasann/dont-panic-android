package com.thoughtworks.processors;

import com.thoughtworks.models.City;
import com.thoughtworks.models.Country;
import com.thoughtworks.utils.JSONAttributes;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.thoughtworks.utils.Constants.OBJ_MAP_CITIES;
import static com.thoughtworks.utils.Constants.OBJ_MAP_COUNTRIES;

public class JSONParser {

    public static Map<String, Object> parse(String json) {
        Map<String, Object> objectMap = new HashMap<String, Object>();
        try {
            JSONObject jsonObject = new JSONObject(json);
            objectMap.put(OBJ_MAP_COUNTRIES, countries(jsonObject));
            objectMap.put(OBJ_MAP_CITIES, cities(jsonObject));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return objectMap;
    }

    private static List<Country> countries(JSONObject jsonObject) throws JSONException {
        JSONArray countriesJSON = (JSONArray) jsonObject.get(JSONAttributes.Country.ITEMS);
        List<Country> countries = new ArrayList<Country>();
        for (int i = 0; i < countriesJSON.length(); i++) {
            JSONObject countryJSONObject = countriesJSON.getJSONObject(i);
            JSONObject countryAttrs = (JSONObject) countryJSONObject.get(JSONAttributes.Country.ITEM);
            countries.add(new Country((Integer) countryAttrs.get(JSONAttributes.Country.ID),
                    (String) countryAttrs.get(JSONAttributes.Country.NAME),
                    (String) countryAttrs.get(JSONAttributes.Country.CODE)));
        }
        return countries;
    }

    private static List<City> cities(JSONObject jsonObject) throws JSONException {
        JSONArray citiesJSON = (JSONArray) jsonObject.get(JSONAttributes.City.ITEMS);
        List<City> cities = new ArrayList<City>();
        for (int i = 0; i < citiesJSON.length(); i++) {
            JSONObject cityJSONObject = citiesJSON.getJSONObject(i);
            JSONObject cityAttrs = (JSONObject) cityJSONObject.get(JSONAttributes.City.ITEM);
            cities.add(new City((Integer) cityAttrs.get(JSONAttributes.Country.ID),
                    (String) cityAttrs.get(JSONAttributes.City.NAME),
                    (String) cityAttrs.get(JSONAttributes.City.CODE),
                    (Integer) cityAttrs.get(JSONAttributes.City.COUNTRY_ID)));
        }
        return cities;
    }

}
