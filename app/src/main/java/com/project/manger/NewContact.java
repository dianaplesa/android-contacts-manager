package com.project.manger;


import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class NewContact extends Activity {

	private EditText firstName;
	private EditText lastName;
	private EditText phone;
	private EditText email;
	private EditText address;

	DBController databaseController = new DBController(this);

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_contact);
		firstName = (EditText) findViewById(R.id.firstName);
		lastName = (EditText) findViewById(R.id.lastName);
		phone = (EditText) findViewById(R.id.phone);
		email = (EditText) findViewById(R.id.email);
		address = (EditText) findViewById(R.id.address);
	}

	public void saveContact(View view) {
		Contact c = new Contact();
		c.setFirstName(firstName.getText().toString());
		c.setLastName(lastName.getText().toString());
		c.setPhoneNumber(phone.getText().toString());
		c.setEmail(email.getText().toString());
		c.setAddress(address.getText().toString());

		databaseController.insertContact(c);
		Toast.makeText(getApplicationContext(), Constants.ADDED_CONTACT, Toast.LENGTH_SHORT).show();
		this.finish();
	}
}
