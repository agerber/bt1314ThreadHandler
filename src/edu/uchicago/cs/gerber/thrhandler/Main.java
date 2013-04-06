package edu.uchicago.cs.gerber.thrhandler;

import java.io.Serializable;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

//<?xml version="1.0" encoding="utf-8"?>
//<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
//    android:layout_width="fill_parent"
//    android:layout_height="fill_parent"
//    android:orientation="vertical" >
//
//    <Button
//        android:id="@+id/btnString"
//        android:layout_width="match_parent"
//        android:layout_height="wrap_content"
//        android:text="String" />
//
//        <Button
//        android:id="@+id/btnSerial"
//        android:layout_width="match_parent"
//        android:layout_height="wrap_content"
//        android:text="Serialized Ojbect" />
//    
//            <Button
//        android:id="@+id/btnNum"
//        android:layout_width="match_parent"
//        android:layout_height="wrap_content"
//        android:text="Number" />
//
//    <TextView
//        android:id="@+id/txtShow"
//        android:layout_width="fill_parent"
//        android:layout_height="match_parent"
//        android:text="something" />
//
//</LinearLayout>
//################################################
// this shows how to pass objects from the UI Thread to a handler, then modify that object
//in the background, and then return it to the UI Thread
//################################################
public class Main extends Activity  {

	private static final String STR_KEY = "str";
	private static final String SER_KEY = "ser";
	
	//_thr_handler

}