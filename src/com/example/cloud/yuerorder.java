package com.example.cloud;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ParseException;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class yuerorder extends Activity {

	private int btn_yuer_order_cancle_cle = 0;
	private int btn_yuer_order_confirm_cicle = 0;
	private int btn_yuer_order_confirm_cle = 0;
	private TextView text_yuer_order_typenamescreen;
	private TextView text_yuer_order_otherneedscreen;
	private TextView text_yuer_order_addressscreen;
	private TextView text_yuer_order_contactscreen;
	private TextView text_yuer_order_phonenumberscreen;
	private TextView text_yuer_order_pricescreen;
	private TextView text_yuer_order_petscreen;
	private TextView text_yuer_order_sexscreen;
	private TextView text_yuer_order_agescreen;
	private TextView text_yuer_order_zhujiascreen;
	private String typename = "error";
	private String otherneed = "error";
	private String address = "error";
	private String contact = "error";
	private String phonenumber = "error";
	private String sex = "error";
	private String age = "error";
	private String zhujia = "error";
	private String price = "error";
	private String pet = "error";
	private String orderid = "error";
	private String userid = "error";
	private Button btn_yuer_order_cancle;
	private Button btn_yuer_order_confirm;
	private int ct_id;
	private String ct_name;
	JSONArray jArray;
	String result = null;
	InputStream is = null;
	StringBuilder sb = null;
	private Handler uiHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 1:
				System.out.println("handleMessage thread id " + Thread.currentThread().getId());
				System.out.println("msg.arg1:" + msg.arg1);
				System.out.println("msg.arg2:" + msg.arg2);
				go();
				// address.this.statusTextView.setText("文件下载完成");
				break;
			case 2:
				System.out.println("handleMessage thread id " + Thread.currentThread().getId());
				System.out.println("msg.arg1:" + msg.arg1);
				System.out.println("msg.arg2:" + msg.arg2);
				goback();
				// address.this.statusTextView.setText("文件下载完成");
				break;
			}
		}
	};

	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			AlertDialog.Builder alertDialog = new AlertDialog.Builder(yuerorder.this);
			alertDialog.setTitle("订单预约");
			alertDialog.setMessage("请在页面上点击按钮实现");
			alertDialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface arg0, int arg1) {
					// TODO Auto-generated method stub
				}
			});

			alertDialog.show();
		}
		return false;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.yuerorder);
		btn_yuer_order_cancle = (Button) findViewById(R.id.btn_yuer_order_cancle);
		btn_yuer_order_confirm = (Button) findViewById(R.id.btn_yuer_order_confirm);
		text_yuer_order_typenamescreen = (TextView) findViewById(R.id.text_yuer_order_typenamescreen);
		text_yuer_order_otherneedscreen = (TextView) findViewById(R.id.text_yuer_order_otherneedscreen);
		text_yuer_order_addressscreen = (TextView) findViewById(R.id.text_yuer_order_addressscreen);
		text_yuer_order_contactscreen = (TextView) findViewById(R.id.text_yuer_order_contactscreen);
		text_yuer_order_phonenumberscreen = (TextView) findViewById(R.id.text_yuer_order_phonenumberscreen);
		// text_yuer_order_contentscreen = (TextView)
		// findViewById(R.id.text_yuer_order_contentscreen);
		// text_yuer_order_squarescreen = (TextView)
		// findViewById(R.id.text_yuer_order_squarescreen);
		// text_yuer_order_tastescreen = (TextView)
		// findViewById(R.id.text_yuer_order_tastescreen);
		text_yuer_order_sexscreen = (TextView) findViewById(R.id.text_yuer_order_sexscreen);
		text_yuer_order_agescreen = (TextView) findViewById(R.id.text_yuer_order_agescreen);
		text_yuer_order_zhujiascreen = (TextView) findViewById(R.id.text_yuer_order_zhujiascreen);
		text_yuer_order_pricescreen = (TextView) findViewById(R.id.text_yuer_order_pricescreen);
		text_yuer_order_petscreen = (TextView) findViewById(R.id.text_yuer_order_petscreen);
		Intent intent = this.getIntent(); // 获取已有的intent对象
		Bundle bundle = intent.getExtras(); // 获取intent里面的bundle对象
		orderid = bundle.getString("orderid");
		userid = bundle.getString("userid");
		Log.d("yuerorder", orderid);
		Log.d("yuerorder", userid);
		btn_yuer_order_cancle.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				(new Thread() {

					@Override

					public void run() {
						ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
						// http get
						btn_yuer_order_cancle_cle = 0;
						for (btn_yuer_order_cancle_cle = 0; btn_yuer_order_cancle_cle < 1; btn_yuer_order_cancle_cle = btn_yuer_order_cancle_cle) {
							try {

								nameValuePairs.add(new BasicNameValuePair("userid", userid));
								nameValuePairs.add(new BasicNameValuePair("orderid", orderid));
								HttpClient httpclient = new DefaultHttpClient();
								HttpPost httppost = new HttpPost("http://www.sundaytek.com/deleteyuerorder.php");
								httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs, "UTF-8"));
								HttpResponse response = httpclient.execute(httppost);
								HttpEntity entity = response.getEntity();
								is = entity.getContent();
								btn_yuer_order_cancle_cle = 1;
								Log.e("log_tag", "isme ");
							} catch (Exception e) {
								Log.d("log_tag", "Error in http connection" + e.toString());
								btn_yuer_order_cancle_cle = 1;
							}

						}
						Message msg = new Message();
						msg.what = 2;
						msg.arg1 = 123;
						msg.arg2 = 321;
						uiHandler.sendMessage(msg);
					}
				}).start();

			}

		});
		btn_yuer_order_confirm.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				Intent intent = new Intent();
				// intent.putExtra("otherneed",
				// text_otherneed.getText().toString());
				intent.putExtra("staues", true);
				// intent.putExtra("staues", "confirm");
				setResult(0, intent);
				finish();

			}

		});
		(new Thread() {

			@Override

			public void run() {
				ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
				// http get
				btn_yuer_order_confirm_cicle = 0;
				btn_yuer_order_confirm_cle = 0;
				for (btn_yuer_order_confirm_cle = 0; btn_yuer_order_confirm_cle < 1; btn_yuer_order_confirm_cle = btn_yuer_order_confirm_cle) {
					try {

						nameValuePairs.add(new BasicNameValuePair("userid", userid));
						nameValuePairs.add(new BasicNameValuePair("orderid", orderid));
						HttpClient httpclient = new DefaultHttpClient();
						HttpPost httppost = new HttpPost("http://www.sundaytek.com/selectyuerorder.php");
						httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs, "UTF-8"));
						HttpResponse response = httpclient.execute(httppost);
						HttpEntity entity = response.getEntity();
						is = entity.getContent();
						btn_yuer_order_confirm_cicle = 0;
						btn_yuer_order_confirm_cle = 0;
						Log.e("log_tag", "isme ");
					} catch (Exception e) {
						Log.d("log_tag", "Error in http connection" + e.toString());
						btn_yuer_order_confirm_cicle = 1;
					}
					if (btn_yuer_order_confirm_cicle == 0) {
						// convert response to string
						try {
							BufferedReader reader = new BufferedReader(new InputStreamReader(is, "iso-8859-1"), 8);
							sb = new StringBuilder();
							sb.append(reader.readLine() + "\n");

							String line = "0";
							while ((line = reader.readLine()) != null) {
								sb.append(line + "\n");
							}
							is.close();
							result = sb.toString();
							btn_yuer_order_confirm_cle = 1;
							result = delete(result);
							Log.e("log_tag", "ismetoo ");
						} catch (Exception e) {
							Log.e("log_tag", "Error converting result " + e.toString());
						}
						// paring data
						String typename1;
						String otherneed1;
						String address1;
						String contact1;
						String phonenumber1;
						String sex1;
						String age1;
						String zhujia1;
						String price1;
						String pet1;
						// String lengthtime1;
						// String totalprice1;
						// String gotime1;
						try {
							jArray = new JSONArray(result);
							JSONObject json_data = null;
							btn_yuer_order_confirm_cle = 1;
							for (int i = 0; i < jArray.length(); i++) {
								json_data = jArray.getJSONObject(i);

								typename1 = json_data.getString("typename");
								otherneed1 = json_data.getString("otherneed");
								address1 = json_data.getString("address");
								contact1 = json_data.getString("contact");
								phonenumber1 = json_data.getString("phonenumber");
								sex1 = json_data.getString("sex");
								age1 = json_data.getString("age");
								zhujia1 = json_data.getString("zhujia");
								price1 = json_data.getString("price");
								pet1 = json_data.getString("pet");
								// gotime1 = json_data.getString("taste");
								typename = typename1;
								otherneed = otherneed1;
								address = address1;
								contact = contact1;
								phonenumber = phonenumber1;
								sex = sex1;
								age = age1;
								zhujia = zhujia1;
								// frequency = frequency1;
								// datestart = datestart1;
								// dateend = dateend1;
								price = price1;
								pet = pet1;

							}
						} catch (JSONException e1) {
							// Toast.makeText(getBaseContext(), "No City Found"
							// ,Toast.LENGTH_LONG).show();
						} catch (ParseException e1) {
							e1.printStackTrace();
						}
					}
				}
				Message msg = new Message();
				// 便我们做出不同的处理操作
				msg.what = 1;

				msg.arg1 = 123;
				msg.arg2 = 321;

				uiHandler.sendMessage(msg);
			}
		}).start();
	}

	protected void goback() {
		// TODO Auto-generated method stub
		Intent intent = new Intent();
		// intent.putExtra("otherneed",
		// text_otherneed.getText().toString());
		// intent.putExtra("staues", "cancle");

		intent.putExtra("staues", false);
		setResult(0, intent);
		finish();

	}

	protected void go() {
		// TODO Auto-generated method stub
		text_yuer_order_typenamescreen.setText(typename);
		text_yuer_order_otherneedscreen.setText(otherneed);
		text_yuer_order_addressscreen.setText(address);
		text_yuer_order_contactscreen.setText(contact);
		text_yuer_order_phonenumberscreen.setText(phonenumber);
		text_yuer_order_sexscreen.setText(sex);
		text_yuer_order_agescreen.setText(age);
		text_yuer_order_zhujiascreen.setText(zhujia);
		text_yuer_order_pricescreen.setText(price);
		text_yuer_order_petscreen.setText(pet);
	}

	protected String delete(String result2) {
		// TODO Auto-generated method stub
		String str = result2;
		String b[] = str.split("");// 截取字符串为数组
		int k = b.length;
		String sss = Integer.toString(k);
		Log.i("log_tag", sss);
		int i = 0;
		int j = 0;
		for (int p = i; p < k; p++) {
			if (b[p].equals("[")) {
				p = k;
			} else {
				b[p] = "";
			}
			j++;
		}
		String sss1 = Integer.toString(j);
		Log.i("log_tag", sss1);
		String right = "";
		for (int s = j - 1; s < k; s++) {
			right = right + b[s];
		}
		Log.d("log_tag", "right");
		Log.i("log_tag", right);
		return right;

	}
}
