/**
 * 
 */
package com.example.test.web;

import java.sql.SQLException;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.webkit.JavascriptInterface;
import android.widget.Toast;

import com.example.test.dao.DatabaseHelper;
import com.example.test.model.Account;

/**
 * @author kevin.chan See:
 *         http://stackoverflow.com/questions/12490789/how-to-pass
 *         -a-string-value-to-webview-from-an-activity
 */
public class WebAppInterface {
	Context mContext;
	DatabaseHelper databaseHelper;

	/**
	 * Instantiate the interface and set the context
	 * 
	 * @param databaseHelper
	 */
	public WebAppInterface(Context c, DatabaseHelper databaseHelper) {
		this.mContext = c;
		this.databaseHelper = databaseHelper;
	}

	/** Show a toast from the web page */
	@JavascriptInterface
	public void showToast(String toast) {
		Toast.makeText(mContext, toast, Toast.LENGTH_SHORT).show();
	}

	/* TODO: return boolean OR JSON string */
	@JavascriptInterface
	public void addAccount(String name, String pass) {
		Account account = new Account(name, pass);
		try {
			this.databaseHelper.getAccountDao().create(account);
			showToast("User added");
		} catch (SQLException e) {
			e.printStackTrace();
			showToast("Error adding User");
		}
	}
	
	@JavascriptInterface
	public String getAllAccounts(){
		List<Account> accounts;
		try {
			accounts = this.databaseHelper.getAccountDao().queryForAll();
			JSONArray array = new JSONArray();
			for (Account account : accounts) {
				array.put(account.toJSONObject());
			}
			return array.toString();
		} catch (SQLException e) {
			e.printStackTrace();
			showToast("Error getting existing accounts");
		} catch (JSONException e) {
			e.printStackTrace();
			showToast("Error getting existing accounts");
		}
		return "";
	}

}
