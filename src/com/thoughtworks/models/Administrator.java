package com.thoughtworks.models;

import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;

import java.util.List;

public class Administrator {
    private static final String TABLE_NAME = "administrators";

    private int id;
    private String name;
    private String phoneNumbers;
    private String email;
    private int officeId;

    public Administrator(int id, String name, String phoneNumbers, String email, int officeId) {
        this.id = id;
        this.name = name;
        this.phoneNumbers = phoneNumbers;
        this.email = email;
        this.officeId = officeId;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPhoneNumbers() {
        return phoneNumbers;
    }

    public String getEmail() {
        return email;
    }

    public int getOfficeId() {
        return officeId;
    }

    public static void reCreate(SQLiteDatabase db, List<Administrator> administrators) {
        db.delete(TABLE_NAME, null, null);
        DatabaseUtils.InsertHelper insertHelper = new DatabaseUtils.InsertHelper(db, TABLE_NAME);
        int id_index = insertHelper.getColumnIndex("_id");
        int name_index = insertHelper.getColumnIndex("name");
        int phone_num_index = insertHelper.getColumnIndex("phone_numbers");
        int email_index = insertHelper.getColumnIndex("email");
        int office_id_index = insertHelper.getColumnIndex("office_id");
        for (Administrator administrator : administrators) {
            insertHelper.prepareForInsert();
            insertHelper.bind(id_index, administrator.getId());
            insertHelper.bind(name_index, administrator.getName());
            insertHelper.bind(phone_num_index, administrator.getPhoneNumbers());
            insertHelper.bind(email_index, administrator.getEmail());
            insertHelper.bind(office_id_index, administrator.getOfficeId());
            insertHelper.execute();
        }
    }
}
