package com.example.crud;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;

public class DatabaseHelper  extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "persons.db";
    private static final int DATABASE_VERSION = 1;
    public static final String TABLE_PERSON = "person";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_EMAIL = "email";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_PERSON_TABLE = "CREATE TABLE " + TABLE_PERSON + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                COLUMN_NAME + " TEXT," +
                COLUMN_EMAIL + " TEXT)";
        db.execSQL(CREATE_PERSON_TABLE);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if exists
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PERSON);
        onCreate(db);
    }

    public long addPerson(Person person) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, person.getName());
        values.put(COLUMN_EMAIL, person.getEmail());
        return db.insert(TABLE_PERSON, null, values);
    }

    public int updatePerson(Person person) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, person.getName());
        values.put(COLUMN_EMAIL, person.getEmail());
        return db.update(TABLE_PERSON, values, COLUMN_ID + " = ?", new String[]{String.valueOf(person.getId())});
    }

    public void deletePerson(long id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_PERSON, COLUMN_ID + " = ?", new String[]{String.valueOf(id)});
    }
    public ArrayList<Person> getAllPersons() {
        ArrayList<Person> persons = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_PERSON, null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                long id = cursor.getLong(cursor.getColumnIndexOrThrow(COLUMN_ID));
                String name = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME));
                String email = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_EMAIL));
                Person person = new Person(id, name, email);
                persons.add(person);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return persons;
    }

}
