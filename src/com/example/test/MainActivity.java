package com.example.test;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.example.test.dao.DatabaseHelper;
import com.example.test.model.Account;
import com.j256.ormlite.android.apptools.OpenHelperManager;

public class MainActivity extends Activity {
  

  //TODO: test out with relationship together with different obejcts
  private DatabaseHelper databaseHelper = null;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    Button save = (Button)findViewById(R.id.button1);
    save.setOnClickListener(new OnClickListener() {
      
      @Override
      public void onClick(View v) {
        TextView nameText = (TextView)findViewById(R.id.editText1);
        TextView passText = (TextView)findViewById(R.id.editText2);
        String name = nameText.getText().toString();
        String pass = passText.getText().toString();
        if (name != null && name != "") {
          Account account = new Account(name, pass);
          try {
            getHelper().getDao().create(account);
            updateList();
          } catch (SQLException e) {
            e.printStackTrace();
          }
        }
            
        
      }
    });
    updateList();
    
  }

  private void updateList() {
    DatabaseHelper dbHelper = getHelper();
    
    try {
      List<Account> accounts = dbHelper.getDao().queryForAll();
      ListView view = (ListView)findViewById(R.id.listView1);
      List<Map<String, String>> data = new ArrayList<Map<String,String>>();
      for (Account account : accounts) {
        Map<String, String> dataItem = new HashMap<String, String>();
        dataItem.put("name", account.getName());
        dataItem.put("pass", account.getPassword());
        data.add(dataItem);
      }
      
      view.setAdapter(new SimpleAdapter(this, data, android.R.layout.simple_list_item_2, new String[] {"name", "pass"}, new int[] {android.R.id.text1,android.R.id.text2}));
      
    } catch (SQLException e) {
      e.printStackTrace();
    }
    
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.main, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    // Handle action bar item clicks here. The action bar will
    // automatically handle clicks on the Home/Up button, so long
    // as you specify a parent activity in AndroidManifest.xml.
    int id = item.getItemId();
    if (id == R.id.action_settings) {
      return true;
    }
    return super.onOptionsItemSelected(item);
  }

  @Override
  protected void onDestroy() {
      super.onDestroy();
      if (databaseHelper != null) {
          OpenHelperManager.releaseHelper();
          databaseHelper = null;
      }
  }

  private DatabaseHelper getHelper() {
      if (databaseHelper == null) {
          databaseHelper =
              OpenHelperManager.getHelper(this, DatabaseHelper.class);
      }
      return databaseHelper;
  }
}
