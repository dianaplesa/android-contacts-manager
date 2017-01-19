package com.project.manger;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBController extends SQLiteOpenHelper {
	private static final String LOGCAT = null;

	public DBController(Context applicationcontext) {
		super(applicationcontext, "androidsqlite.db", null, 1);
		Log.d(LOGCAT, "Created");
	}

	@Override
	public void onCreate(SQLiteDatabase database) {
		String query;
		query = "CREATE TABLE contacts ( ID INTEGER PRIMARY KEY, firstName TEXT, lastName TEXT, phone TEXT, address TEXT, email TEXT)";
		database.execSQL(query);
		Log.d(LOGCAT, "Contacts Created");
	}

	@Override
	public void onUpgrade(SQLiteDatabase database, int version_old, int current_version) {
		String query;
		query = "DROP TABLE IF EXISTS contacts";
		database.execSQL(query);
		onCreate(database);
	}

	/**
	 * Insert Contact into the database
	 * 
	 * @param contact
	 */
	public void insertContact(Contact contact) {
		SQLiteDatabase database = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put("firstName", contact.getFirstName());
		values.put("lastName", contact.getLastName());
		values.put("phone", contact.getPhoneNumber());
		values.put("address", contact.getAddress());
		values.put("email", contact.getEmail());
		database.insert("contacts", null, values);
		database.close();
	}

	/**
	 * Update contact in the database
	 * 
	 * @param contact
	 * @return
	 */
	public int updateContact(Contact contact) {
		SQLiteDatabase database = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put("firstName", contact.getFirstName());
		values.put("lastName", contact.getLastName());
		values.put("phone", contact.getPhoneNumber());
		values.put("address", contact.getAddress());
		values.put("email", contact.getEmail());
		return database.update("contacts", values, "ID" + " = ?", new String[] { Integer.toString(contact.getId()) });

	}

	/**
	 * Delete contact from database
	 * 
	 * @param id
	 */
	public void deleteContact(int id) {
		Log.d(LOGCAT, "delete");
		SQLiteDatabase database = this.getWritableDatabase();
		String deleteQuery = "DELETE FROM contacts where ID='" + id + "'";
		Log.d("query", deleteQuery);
		database.execSQL(deleteQuery);
	}

	/**
	 * Get all contacts from the database
	 * 
	 * @return list of all contacts
	 */
	public ArrayList<Contact> getAllContacts() {

		ArrayList<Contact> contacts = new ArrayList<Contact>();
		String selectQuery = "SELECT  * FROM contacts";
		SQLiteDatabase database = this.getWritableDatabase();
		Cursor cursor = database.rawQuery(selectQuery, null);
		if (cursor.moveToFirst()) {
			do {
				Contact c = new Contact();
				c.setId(cursor.getInt(0));
				c.setFirstName(cursor.getString(1));
				c.setLastName(cursor.getString(2));
				c.setPhoneNumber(cursor.getString(3));
				c.setAddress(cursor.getString(4));
				c.setEmail(cursor.getString(5));
				contacts.add(c);
			} while (cursor.moveToNext());
		}

		return contacts;
	}
	
	/**
	 * Get a contact with specified ID
	 * @param id
	 * @return
	 */
	public Contact getContact(int id) {
		Contact c = new Contact();
		String selectQuery = "SELECT  * FROM contacts WHERE ID="+id;
		SQLiteDatabase database = this.getWritableDatabase();
		Cursor cursor = database.rawQuery(selectQuery, null);
		if (cursor.moveToFirst()) {
			do {
	
				c.setId(cursor.getInt(0));
				c.setFirstName(cursor.getString(1));
				c.setLastName(cursor.getString(2));
				c.setPhoneNumber(cursor.getString(3));
				c.setAddress(cursor.getString(4));
				c.setEmail(cursor.getString(5));
			} while (cursor.moveToNext());
		}

		return c;
	}
}
