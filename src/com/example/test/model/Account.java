/**
 * Copyright 2014 - Auximedic Solutions Ltd.
 * Created on: 7/08/2014
 * Original Author: kevin.chan
 */
package com.example.test.model;

import org.json.JSONException;
import org.json.JSONObject;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * @author kevin.chan
 *
 */
@DatabaseTable(tableName = "accounts")
public class Account {
  
  @DatabaseField(id = true)
  private String name;
  @DatabaseField
  private String password;
  
  public Account() {
      // ORMLite needs a no-arg constructor 
  }
  public Account(String name, String password) {
      this.name = name;
      this.password = password;
  }
  public String getName() {
      return name;
  }
  public void setName(String name) {
      this.name = name;
  }
  public String getPassword() {
      return password;
  }
  public void setPassword(String password) {
      this.password = password;
  }
  
  public JSONObject toJSONObject() throws JSONException{
	  JSONObject obj = new JSONObject();
	  obj.put("name", getName());
	  obj.put("password", getPassword());
	  return obj;
  }
}
