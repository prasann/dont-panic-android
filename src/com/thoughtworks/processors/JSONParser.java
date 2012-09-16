package com.thoughtworks.processors;

import android.util.Log;
import com.thoughtworks.models.Administrator;
import com.thoughtworks.models.City;
import com.thoughtworks.models.Company;
import com.thoughtworks.models.Country;
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
        try {
            JSONObject jsonObject = new JSONObject(json);
            objectMap.put(OBJ_MAP_COUNTRIES, countries(jsonObject));
            objectMap.put(OBJ_MAP_CITIES, cities(jsonObject));
            objectMap.put(OBJ_MAP_COMPANIES, companies(jsonObject));
            objectMap.put(OBJ_MAP_ADMINISTRATORS, administrators(jsonObject));
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
            cities.add(new City((Integer) cityAttrs.get(JSONAttributes.City.ID),
                    (String) cityAttrs.get(JSONAttributes.City.NAME),
                    (String) cityAttrs.get(JSONAttributes.City.CODE),
                    (Integer) cityAttrs.get(JSONAttributes.City.COUNTRY_ID)));
        }
        return cities;
    }

    private static List<Company> companies(JSONObject jsonObject) throws JSONException {
        JSONArray companiesJSON = (JSONArray) jsonObject.get(JSONAttributes.Company.ITEMS);
        List<Company> companies = new ArrayList<Company>();
        for (int i = 0; i < companiesJSON.length(); i++) {
            JSONObject companyJSONObject = companiesJSON.getJSONObject(i);
            JSONObject companyAttrs = (JSONObject) companyJSONObject.get(JSONAttributes.Company.ITEM);
            companies.add(new Company((Integer) companyAttrs.get(JSONAttributes.Company.ID),
                    (String) companyAttrs.get(JSONAttributes.Company.NAME)));
        }
        return companies;
    }

    private static List<Administrator> administrators(JSONObject jsonObject) throws JSONException {
        JSONArray administratorsJSON = (JSONArray) jsonObject.get(JSONAttributes.Administrator.ITEMS);
        List<Administrator> administrators = new ArrayList<Administrator>();
        for (int i = 0; i < administratorsJSON.length(); i++) {
            JSONObject administratorsJSONObject = administratorsJSON.getJSONObject(i);
            JSONObject administratorsAttrs = (JSONObject) administratorsJSONObject.get(JSONAttributes.Administrator.ITEM);
            administrators.add(new Administrator((Integer) administratorsAttrs.get(JSONAttributes.Administrator.ID),
                    (String) administratorsAttrs.get(JSONAttributes.Administrator.NAME),
                    (String) administratorsAttrs.get(JSONAttributes.Administrator.PHONE_NUMBERS),
                    (String) administratorsAttrs.get(JSONAttributes.Administrator.EMAIL),
                    (Integer) administratorsAttrs.get(JSONAttributes.Administrator.OFFICE_ID)));
        }
        Log.v("JSONP",String.valueOf(administrators.size()));
        return administrators;
    }

}
