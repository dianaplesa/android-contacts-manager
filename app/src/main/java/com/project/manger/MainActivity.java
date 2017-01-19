package com.project.manger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class MainActivity extends ListActivity {
	Intent intent;
	DBController databaseController = new DBController(this);
	private ArrayList<Contact> allContacts;
	private SimpleAdapter listAdapter;
	private List<Map<String, String>> contactList;
	private Runnable updateThread;
	private ListView list;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		list = this.getListView();
		
		allContacts = databaseController.getAllContacts();
		contactList = new ArrayList<>();
		getNamesandPhones(allContacts);
		if (contactList.size() != 0) {
			list.setOnItemClickListener(new OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
					int contactID = allContacts.get(position).getId();
					Intent objIndent = new Intent(getApplicationContext(), ViewContact.class);
					objIndent.putExtra("contactID", contactID);
					startActivity(objIndent);
				}
			});
			listAdapter = new SimpleAdapter(MainActivity.this, contactList, android.R.layout.simple_list_item_2, new String[] { "name", "phone" }, new int[] { android.R.id.text1, android.R.id.text2 });
			setListAdapter(listAdapter);
		}
		
		updateThread = new Runnable(){
            public void run(){
            	contactList.clear();
            	allContacts = databaseController.getAllContacts();
            	getNamesandPhones(allContacts);
            	System.out.println(contactList);
        		listAdapter.notifyDataSetChanged(); 
        		list.invalidateViews();
        		list.refreshDrawableState();
            }
       };
	}
	

	public void showAddForm(View view) {
		Intent objIntent = new Intent(getApplicationContext(), NewContact.class);
		startActivity(objIntent);
	}

	private void getNamesandPhones(ArrayList<Contact> contacts) {
		
		HashMap<String, String> item;
		for (Contact c : contacts) {
			item = new HashMap<>();
			String name = c.getFirstName() + " " + c.getLastName();
			String phone = c.getPhoneNumber();
			item.put("name", name);
			item.put("phone", phone);
			contactList.add(item);
		}
	}

	@Override
	public void onRestart() { // After a pause OR at startup
		super.onRestart();
		runOnUiThread(updateThread);
	}
}
