package com.project.manger;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ViewContact extends Activity {
	DBController databaseController = new DBController(this);

	private EditText firstName;
	private EditText lastName;
	private EditText phone;
	private EditText email;
	private EditText address;

	private Button editButton;
	private Button removeButton;

	private int contactID;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.view_contact);

		Intent objIntent = getIntent();
		contactID = objIntent.getIntExtra(Constants.CONTACT_ID, Constants.INTENT_CONTACT_ID);
		Contact c = databaseController.getContact(contactID);

		firstName = (EditText) findViewById(R.id.firstName);
		firstName.setText(c.getFirstName());
		lastName = (EditText) findViewById(R.id.lastName);
		lastName.setText(c.getLastName());
		phone = (EditText) findViewById(R.id.phone);
		phone.setText(c.getPhoneNumber());
		email = (EditText) findViewById(R.id.email);
		email.setText(c.getEmail());
		address = (EditText) findViewById(R.id.address);
		address.setText(c.getAddress());
		
		findViewById(R.id.addressText).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (editButton.getText().toString().equals("Edit")) {
					String add = address.getText().toString();
					Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("google.navigation:q=" + add));
					startActivity(intent);
				}
			}
		});

		findViewById(R.id.emailText).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (editButton.getText().toString().equals("Edit")) {
					Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);
					emailIntent.setType("text/html");
					emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, new String[] { email.getText().toString() });
					startActivity(Intent.createChooser(emailIntent, "Email:"));
				}
			}
		});

		findViewById(R.id.phoneText).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (editButton.getText().toString().equals("Edit")) {
					Intent intent = new Intent(Intent.ACTION_DIAL);
					intent.setData(Uri.parse("tel:" + phone.getText().toString()));
					startActivity(intent);
				}
			}
		});

		editButton = (Button) findViewById(R.id.editContact);
		editButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				String label = editButton.getText().toString();
				switch (label) {
				case "Edit":
					editButton.setText("Save");
					enableFields();
					break;
				case "Save":
					saveContact();
					Toast.makeText(getApplicationContext(), Constants.SAVED_CONTACT, Toast.LENGTH_SHORT).show();
					finish();
					break;
				default:
					break;
				}

			}
		});

		removeButton = (Button) findViewById(R.id.removeContact);
		removeButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				databaseController.deleteContact(contactID);
				Toast.makeText(getApplicationContext(), Constants.DELETED_CONTACT, Toast.LENGTH_SHORT).show();
				finish();
			}
		});
	}

	private void enableFields() {
		firstName.setEnabled(true);
		lastName.setEnabled(true);
		phone.setEnabled(true);
		email.setEnabled(true);
		address.setEnabled(true);
	}

	private void saveContact() {
		Contact c = new Contact();
		c.setId(contactID);
		c.setFirstName(firstName.getText().toString());
		c.setLastName(lastName.getText().toString());
		c.setPhoneNumber(phone.getText().toString());
		c.setEmail(email.getText().toString());
		c.setAddress(address.getText().toString());
		databaseController.updateContact(c);
	}

}
