package com.project.manger;

import java.util.HashMap;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

public class EditContact extends Activity{
	EditText name;
	DBController controller = new DBController(this);
	 @Override
	    public void onCreate(Bundle savedInstanceState) {
		 	super.onCreate(savedInstanceState);
			setContentView(R.layout.edit_contact);
			name = (EditText) findViewById(R.id.name);
			Log.d("Reading: ", "Reading all contacts..");
	    }

	public void editContact(View view) {
		HashMap<String, String> queryValues =  new  HashMap<>();
		name = (EditText) findViewById(R.id.name);
		Intent objIntent = getIntent();
		String contactId = objIntent.getStringExtra("contactId");
		queryValues.put("contactId", contactId);
		queryValues.put("name", name.getText().toString());

		//databaseController.updateAnimal(queryValues);
		this.callHomeActivity(view);
		
	}
	public void removeContact(View view) {
		Intent objIntent = getIntent();
		String contactId = objIntent.getStringExtra("contactId");
		//databaseController.deleteAnimal(contactId);
		this.callHomeActivity(view);
		
	}
	public void callHomeActivity(View view) {
		Intent objIntent = new Intent(getApplicationContext(), MainActivity.class);
		startActivity(objIntent);
	}
}

