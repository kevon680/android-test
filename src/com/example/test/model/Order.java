/**
 * Copyright 2014 - Auximedic Solutions Ltd.
 * Created on: 11/08/2014
 * Original Author: kevin.chan
 */
package com.example.test.model;

import java.math.BigDecimal;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * @author kevin.chan
 *
 */
@DatabaseTable(tableName = "orders")
public class Order {
  
  @DatabaseField(generatedId=true)
  private int id;
  
  //turn auto refresh on to always pull account?
  @DatabaseField(canBeNull = false, foreign=true, foreignAutoRefresh=false)
  private Account account;
  
  @DatabaseField(dataType=DataType.BIG_DECIMAL)
  private BigDecimal price;
  
  @DatabaseField
  private String trackingCode;
  
  
  public Order() {
    //used by ORM
  }
  
  public Order(Account account, BigDecimal price, String trackingCode){
    this.account = account;
    this.price = price;
    this.trackingCode = trackingCode;
  }

  /**
   * @return the account
   */
  public Account getAccount() {
    return account;
  }

  /**
   * @return the price
   */
  public BigDecimal getPrice() {
    return price;
  }

  /**
   * @return the trackingCode
   */
  public String getTrackingCode() {
    return trackingCode;
  }

}
