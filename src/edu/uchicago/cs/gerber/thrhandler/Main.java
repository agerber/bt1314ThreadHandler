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
public class Main extends Activity implements OnClickListener {

	private static final String STR_KEY = "str";
	private static final String SER_KEY = "ser";
	TextView txtShow;
	Button btnString, btnNum, btnSerial;

	//this is a member of this class
	private Handler hndHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {

			if (msg.getData().containsKey(STR_KEY) && msg.what == 0) {

				String strMsg = msg.getData().getString(STR_KEY);
				txtShow.setText(strMsg);

			} else if (msg.getData().containsKey(SER_KEY) && msg.what == 0) {

				MySer msrObject = (MySer) msg.getData()
						.getSerializable(SER_KEY);
				txtShow.setText(msrObject.toString());
			}

			else {

				txtShow.setText(String.valueOf("numeric message passed "
						+ msg.what));

			}
		}
	};

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		txtShow = (TextView) findViewById(R.id.txtShow);

		btnString = (Button) findViewById(R.id.btnString);
		btnString.setOnClickListener(this);

		btnNum = (Button) findViewById(R.id.btnNum);
		btnNum.setOnClickListener(this);

		btnSerial = (Button) findViewById(R.id.btnSerial);
		btnSerial.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		Thread thr = null;
		switch (v.getId()) {
		case R.id.btnNum:
			thr = new Thread(new RunerBilateral(85));
			break;

		case R.id.btnSerial:
	
			thr = new Thread(new RunnerUnilateral());
			break;

		case R.id.btnString:
			thr = new Thread(new RunerBilateral("original string "));
			break;

		default:
			thr = new Thread(new RunerBilateral(0));
			break;
		}

		thr.start();

	}

	//this Runner is used for bilateral communication i.e. from the UI Thread to the Background thread and back again.
	private class RunerBilateral implements Runnable {

		private Object objPassed;

		public RunerBilateral(Object objPassed) {
			this.objPassed = objPassed;
		}
		@Override
		public void run() {

			try {
				//simulate some background work
				Thread.sleep(1500);
	
				if (objPassed instanceof String) {
					Bundle bnd = new Bundle();
					Message msg = new Message();
					bnd.putString(STR_KEY, (String) objPassed
							+ " some messsage added to my string.");
					msg.setData(bnd);
					hndHandler.sendMessage(msg);
				} else if (objPassed instanceof MySer) {

					MySer msr = ((MySer) objPassed);
					msr.setFirst("Zeke");
					Bundle bnd = new Bundle();
					Message msg = new Message();
					//pass in the modified object, with a key
					bnd.putSerializable(SER_KEY, msr);
					msg.setData(bnd);
					hndHandler.sendMessage(msg);

				} else if (objPassed instanceof Integer) {
					hndHandler.sendEmptyMessage((Integer) objPassed + 1);
				} else {
					hndHandler.sendEmptyMessage(0);
				}

			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	
	//this Runner is used for unilateral communication i.e. from the Background thread to the UI Thread.
		private class RunnerUnilateral implements Runnable {

    		@Override
			public void run() {

				try {
					//simulate some background work
					Thread.sleep(1000);
					java.util.Random rnd = new java.util.Random();
				
					if (rnd.nextInt(100) < 50){

						MySer msr = new MySer("Barak","Obama");
						Bundle bnd = new Bundle();
						Message msg = new Message();
						//pass in the object, with a key
						bnd.putSerializable(SER_KEY, msr);
						msg.setData(bnd);
						hndHandler.sendMessage(msg);
					} else {
						hndHandler.sendEmptyMessage(55);
					}

				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
	

	private class MySer implements Serializable {

		private String strFirst;
		private String strLast;

		public MySer(String strFirst, String strLast) {
			this.strFirst = strFirst;
			this.strLast = strLast;
		}

		public String getFirst() {
			return strFirst;
		}

		public void setFirst(String strFirst) {
			this.strFirst = strFirst;
		}

		public String getLast() {
			return strLast;
		}

		public void setLast(String strLast) {
			this.strLast = strLast;
		}

		@Override
		public String toString() {
			return getFirst() + " : " + getLast();
		}

	}

}