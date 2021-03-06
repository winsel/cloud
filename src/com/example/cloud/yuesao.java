package com.example.cloud;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.format.DateFormat;
import android.text.format.Time;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.SeekBar;
import android.widget.TextView;

public class yuesao extends Activity implements SeekBar.OnSeekBarChangeListener {
	private int back_yuesao_int = 1;
	private int btn_yuesao_ok_delete_cle = 0;
	private int choice_zhujia = 0;
	private int btn_yuesao_ok_cle = 0;
	private TextView text_choicedeliverdate;
	// private TextView text_yuesao_age;
	private TextView text_yuesao_jiaweijiage;
	private SeekBar seekbar_yuesao_age;
	private SeekBar seekbar_yuesao_xinlijiawei;
	private Button btn_yuesao_boy;
	private int btn_yuesao_boy_i = 1;
	private Button btn_yuesao_buzhujia;
	private int btn_yuesao_buzhujia_i = 1;
	private Button btn_yuesao_zhujia;
	private int btn_yuesao_zhujia_i = 1;
	private Button btn_yuesao_girl;
	private int btn_yuesao_girl_i = 1;
	private Button btn_yuesao_bigdog;
	private int btn_yuesao_bigdog_i = 1;
	private Button btn_yuesao_smalldog;
	private int btn_yuesao_smalldog_i = 1;
	private Button btn_yuesao_cat;
	private int btn_yuesao_cat_i = 1;
	private TextView btn_yuesao_back;
	private TextView text_yuesao_serverscon;
	private TextView yuesao_otherdetail;
	private TextView text_yuesao_address;
	private String temp_other = "未填写";
	private String temp = "";
	private String address_temp_other = "";
	private String orderid;
	private String userid = "1";
	private String typeid = "6";
	private String typename = "月嫂";
	// private String sex = " ";
	private String zhujia = " ";
	private String pet = " ";
	private Button btn_yuesao_ok;
	private String text_yuesao_contact_text;
	private String text_yuesao_phone_text;
	// private String text_mianjidaxiao_text;
	private String text_jiaweijiage_text;
	private TextView text_yuesao_contact;
	private TextView text_yuesao_phone;
	private String text_choicedeliverdatetext = "";
	InputStream is = null;
	private String username;
	private String password;
	private SharedPreferences pre;
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
			}
		}
	};

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			AlertDialog.Builder alertDialog = new AlertDialog.Builder(yuesao.this);
			alertDialog.setTitle("月嫂");
			alertDialog.setMessage("确定抛弃该订单吗？");
			alertDialog.setPositiveButton("点错了", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface arg0, int arg1) {
					// TODO Auto-generated method stub
				}
			});
			alertDialog.setNegativeButton("确定退出", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface arg0, int arg1) {
					// TODO Auto-generated method stub
					back_yuesao_int = 0;
					finish();
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
		setContentView(R.layout.layoutyuesao);
		pre = getSharedPreferences("userinfo", MODE_PRIVATE);
		read();
		userid = username;
		Intent intent = this.getIntent(); // 获取已有的intent对象
		Bundle bundle = intent.getExtras(); // 获取intent里面的bundle对象
		btn_yuesao_ok = (Button) findViewById(R.id.btn_yuesao_ok);
		text_yuesao_contact = (TextView) findViewById(R.id.text_yuesao_contact);
		text_yuesao_phone = (TextView) findViewById(R.id.text_yuesao_phone);
		final TextView et1 = (TextView) findViewById(R.id.text_choicedeliverdate);
		text_choicedeliverdate = (TextView) findViewById(R.id.text_choicedeliverdate);
		final Calendar c1 = Calendar.getInstance();
		text_choicedeliverdate = (TextView) findViewById(R.id.text_choicedeliverdate);
		btn_yuesao_bigdog = (Button) findViewById(R.id.btn_yuesao_bigdog);
		btn_yuesao_smalldog = (Button) findViewById(R.id.btn_yuesao_smalldog);
		btn_yuesao_cat = (Button) findViewById(R.id.btn_yuesao_cat);
		btn_yuesao_zhujia = (Button) findViewById(R.id.btn_yuesao_zhujia);
		btn_yuesao_buzhujia = (Button) findViewById(R.id.btn_yuesao_buzhujia);
		text_yuesao_jiaweijiage = (TextView) findViewById(R.id.text_yuesao_jiaweijiage);
		seekbar_yuesao_xinlijiawei = (SeekBar) findViewById(R.id.seekbar_yuesao_xinlijiawei);
		seekbar_yuesao_xinlijiawei.setOnSeekBarChangeListener(this);
		text_yuesao_serverscon = (TextView) findViewById(R.id.text_yuesao_serverscon);
		btn_yuesao_back = (TextView) findViewById(R.id.btn_yuesao_back);
		yuesao_otherdetail = (TextView) findViewById(R.id.yuesao_otherdetail);
		text_yuesao_address = (TextView) findViewById(R.id.text_yuesao_address);
		final ArrayList nameValuePairs = new ArrayList();
		btn_yuesao_ok.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				choice_zhujia = 0;
				pet = "";
				zhujia = "";
				text_yuesao_contact_text = text_yuesao_contact.getText().toString();
				text_yuesao_phone_text = text_yuesao_phone.getText().toString();
				text_jiaweijiage_text = (String) text_yuesao_jiaweijiage.getText();
				text_choicedeliverdatetext = text_choicedeliverdate.getText().toString();
				// sex = "";
				// private int btn_yuesao_buzhujia_i = 1;
				// private Button btn_yuesao_zhujia;
				// private int btn_yuesao_zhujia_i = 1;
				// private int btn_yuesao_boy_i = 1;
				// if (btn_yuesao_boy_i == -1) {
				// sex = "男孩";
				// } else if (btn_yuesao_girl_i == -1) {
				// sex = "女孩";
				// } else if (true) {
				// sex = "女孩";

				// }
				if (btn_yuesao_buzhujia_i == -1) {
					zhujia = "不住家";
					choice_zhujia = 1;
				} else if (btn_yuesao_zhujia_i == -1) {
					zhujia = "住家";
					choice_zhujia = 1;
				} else if (true) {
					zhujia = "无要求";
					choice_zhujia = 0;

				}

				if (btn_yuesao_bigdog_i == -1) {
					pet = pet + "大型犬 ";
				}
				if (btn_yuesao_smalldog_i == -1) {
					pet = pet + "小型犬 ";
				}
				if (btn_yuesao_cat_i == -1) {
					pet = pet + "猫 ";
				}
				if (btn_yuesao_bigdog_i == 1 && btn_yuesao_smalldog_i == 1 && btn_yuesao_cat_i == 1) {
					pet = "无宠物";
				}
				if (text_choicedeliverdatetext.equals("")) {
					AlertDialog alertDialog = new AlertDialog.Builder(yuesao.this).create();
					AlertDialog.Builder settingDialog = new AlertDialog.Builder(yuesao.this);
					settingDialog.setInverseBackgroundForced(true);
					alertDialog.setMessage("请选择预产期");
					alertDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "知道了",
							new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
						}
					});
					alertDialog.show();

				} else if (text_jiaweijiage_text.equals("请选择价格")) {
					AlertDialog alertDialog = new AlertDialog.Builder(yuesao.this).create();
					AlertDialog.Builder settingDialog = new AlertDialog.Builder(yuesao.this);
					settingDialog.setInverseBackgroundForced(true);
					alertDialog.setMessage("请选择心理价位");
					alertDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "知道了",
							new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
						}
					});
					alertDialog.show();
				} else if (choice_zhujia == 0) {
					AlertDialog alertDialog = new AlertDialog.Builder(yuesao.this).create();
					AlertDialog.Builder settingDialog = new AlertDialog.Builder(yuesao.this);
					settingDialog.setInverseBackgroundForced(true);
					alertDialog.setMessage("请选择是否住家");
					alertDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "知道了",
							new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
						}
					});
					alertDialog.show();

				} else if (address_temp_other.equals("")) {
					AlertDialog alertDialog = new AlertDialog.Builder(yuesao.this).create();
					AlertDialog.Builder settingDialog = new AlertDialog.Builder(yuesao.this);
					settingDialog.setInverseBackgroundForced(true);
					alertDialog.setMessage("请选择地址");
					alertDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "知道了",
							new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
						}
					});
					alertDialog.show();

				} else if (text_yuesao_contact_text.equals("")) {
					AlertDialog alertDialog = new AlertDialog.Builder(yuesao.this).create();
					AlertDialog.Builder settingDialog = new AlertDialog.Builder(yuesao.this);
					settingDialog.setInverseBackgroundForced(true);
					alertDialog.setMessage("请填写联系人");
					alertDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "知道了",
							new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
						}
					});
					alertDialog.show();

				} else if (text_yuesao_phone_text.equals("")) {
					AlertDialog alertDialog = new AlertDialog.Builder(yuesao.this).create();
					AlertDialog.Builder settingDialog = new AlertDialog.Builder(yuesao.this);
					settingDialog.setInverseBackgroundForced(true);
					alertDialog.setMessage("请填写联系电话");
					alertDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "知道了",
							new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
						}
					});
					alertDialog.show();

				} else if (true) {
					Time t = new Time(); // or Time t=new Time("GMT+8"); 加上Time
											// //
					// Zone资料。
					t.setToNow(); // 取得系统时间。
					int year = t.year;
					String yearstr = Integer.toString(year);
					int month = t.month + 1;
					String monthstr = Integer.toString(month);
					if (monthstr.length() == 1) {
						monthstr = "0" + monthstr;
					}
					int date1 = t.monthDay;
					String date1str = Integer.toString(date1);
					if (date1str.length() == 1) {
						date1str = "0" + date1str;
					}
					// int date2 = year * 365 + month * 30 + date1;
					// orderdate = Integer.toString(date2);
					int hour = t.hour; // 0-23
					String hourstr = Integer.toString(hour);
					if (hourstr.length() == 1) {
						hourstr = "0" + hourstr;
					}
					int minute = t.minute;
					String minutestr = Integer.toString(minute);
					if (minutestr.length() == 1) {
						minutestr = "0" + minutestr;
					}
					int second = t.second;
					String secondstr = Integer.toString(second);
					if (secondstr.length() == 1) {
						secondstr = "0" + secondstr;
					}
					// long data = (year - 2015) * 365 * 24 * 3600 + month *
					// 30 * 24 * 3600 + date1 * 24 * 3600
					// + hour * 3600 + minute * 60 + second;
					orderid = yearstr + monthstr + date1str + hourstr + minutestr + secondstr;

					// text_baojie_contact_text =
					// text_baojie_contact.getText().toString();
					// text_baojie_phone_text =
					// text_baojie_phone.getText().toString();
					// // TODO Auto-generated method stub
					// EditText tv = (EditText) findViewById(R.id.editView);
					// ArrayList<NameValuePair> nameValuePairs = new
					// ArrayList<NameValuePair>();
					// EditText tv = (EditText) findViewById(R.id.editView);
					// ArrayList nameValuePairs = new ArrayList();
					// nameValuePairs.add(new BasicNameValuePair("id", "3"));
					// nameValuePairs.add(new BasicNameValuePair("name",
					// "Guo"));
					// nameValuePairs.add(new BasicNameValuePair("school",
					// "xjtu"));

					// if (btn_baojie_ok_i == 1) {
					//
					// btn_baojie_ok.setBackgroundResource(R.drawable.bacyes);
					//
					// btn_baojie_ok_i = (-1) * btn_baojie_ok_i;
					// }
					// if (btn_baojie_ok_i == -1) {
					//
					// btn_baojie_ok.setBackgroundResource(R.drawable.bacno);
					//
					// btn_baojie_ok_i = (-1) * btn_baojie_ok_i;
					// }
					// http get
					(new Thread() {

						@Override

						public void run() {

							// nameValuePairs.add(new
							// BasicNameValuePair("orderdate", orderdate));
							// content = null;
							// private String taste = null;
							// private String frequetly = null;
							// private String pet = null;
							nameValuePairs.add(new BasicNameValuePair("pet", pet));

							nameValuePairs.add(new BasicNameValuePair("price", text_jiaweijiage_text));
							nameValuePairs.add(new BasicNameValuePair("contact", text_yuesao_contact_text));
							nameValuePairs.add(new BasicNameValuePair("phonenumber", text_yuesao_phone_text));
							nameValuePairs.add(new BasicNameValuePair("typename", typename));
							nameValuePairs.add(new BasicNameValuePair("typeid", typeid));
							nameValuePairs.add(new BasicNameValuePair("userid", userid));
							nameValuePairs.add(new BasicNameValuePair("orderid", orderid));
							nameValuePairs.add(new BasicNameValuePair("otherneed", temp_other));
							nameValuePairs.add(new BasicNameValuePair("address", address_temp_other));
							nameValuePairs.add(new BasicNameValuePair("deliverdate", text_choicedeliverdatetext));
							nameValuePairs.add(new BasicNameValuePair("zhujia", zhujia));
							nameValuePairs.add(new BasicNameValuePair("client", "true"));
							nameValuePairs.add(new BasicNameValuePair("server", "true"));
							nameValuePairs.add(new BasicNameValuePair("condition", "待处理"));
							// nameValuePairs.add(new BasicNameValuePair("sex",
							// sex));
							Log.d("hhhhhhhhh", pet);

							Log.d("hhhhhhhhh", text_jiaweijiage_text);
							Log.d("hhhhhhhhh", text_yuesao_contact_text);
							Log.d("hhhhhhhhh", text_yuesao_phone_text);
							Log.d("hhhhhhhhh", typename);
							Log.d("hhhhhhhhh", typeid);
							Log.d("hhhhhhhhh", userid);
							Log.d("hhhhhhhhh", orderid);
							Log.d("hhhhhhhhh", temp_other);
							Log.d("hhhhhhhhh", address_temp_other);
							Log.d("hhhhhhhhh", text_choicedeliverdatetext);
							Log.d("hhhhhhhhh", zhujia);
							// Log.d("hhhhhhhhh", sex);
							// nameValuePairs.add(new
							// BasicNameValuePair("orderdate", "1"));
							// nameValuePairs.add(new
							// BasicNameValuePair("totalprice", "50"));
							// nameValuePairs.add(new
							// BasicNameValuePair("lengthoftime", "2"));
							// nameValuePairs.add(new
							// BasicNameValuePair("godate",
							// "2015-45"));
							// nameValuePairs.add(new
							// BasicNameValuePair("otherneed", "otherneed"));

							// nameValuePairs.add(new
							// BasicNameValuePair("contact",
							// "吴新初"));
							// nameValuePairs.add(new
							// BasicNameValuePair("phonenumber",
							// "18710861689"));
							// nameValuePairs.add(new
							// BasicNameValuePair("markone",
							// "6"));
							// nameValuePairs.add(new
							// BasicNameValuePair("marktwo",
							// "20"));
							// nameValuePairs.add(new BasicNameValuePair("test",
							// "吴新初"));
							// nameValuePairs.add(new
							// BasicNameValuePair("testone",
							// "吴新初"));
							// 这里做网络操作
							btn_yuesao_ok_cle = 0;
							for (btn_yuesao_ok_cle = 0; btn_yuesao_ok_cle < 1; btn_yuesao_ok_cle = btn_yuesao_ok_cle) {
								if (back_yuesao_int == 1) {

									try {
										// HttpClient httpclient = new
										// DefaultHttpClient();
										// HttpGet httpget = new
										// HttpGet("http://www.sundaytek.com/test.php");
										// HttpResponse response =
										// httpclient.execute(httpget);
										// HttpEntity entity =
										// response.getEntity();
										// is = entity.getContent();
										HttpClient httpclient = new DefaultHttpClient();
										HttpPost httppost = new HttpPost(
												"http://www.sundaytek.com/insertorderyuesao.php");
										httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs, "UTF-8"));
										// httppost.setEntity(new
										// UrlEncodedFormEntity(nameValuePairs));
										HttpResponse response = httpclient.execute(httppost);
										HttpEntity entity = response.getEntity();

										btn_yuesao_ok_cle = 1;
										is = entity.getContent();
										// Log.e("log_tag", "对不对" );
									} catch (Exception e) {
										Log.e("log_tag", "Error in http connection" + e.toString());
										btn_yuesao_ok_cle = 0;
									}
								}
							}
							if (back_yuesao_int == 1) {
								Message msg = new Message();
								// 便我们做出不同的处理操作
								msg.what = 1;

								// 我们可以通过arg1和arg2给Message传入简单的数据
								msg.arg1 = 123;
								msg.arg2 = 321;
								// 我们也可以通过给obj赋值Object类型传递向Message传入任意数据
								// msg.obj = null;
								// 我们还可以通过setData方法和getData方法向Message中写入和读取Bundle类型的数据
								// msg.setData(null);
								// Bundle data = msg.getData();

								// 将该Message发送给对应的Handler
								uiHandler.sendMessage(msg);
							} else {
								btn_yuesao_ok_delete_cle = 0;
								for (btn_yuesao_ok_delete_cle = 0; btn_yuesao_ok_delete_cle < 1; btn_yuesao_ok_delete_cle = btn_yuesao_ok_delete_cle) {
									try {

										nameValuePairs.add(new BasicNameValuePair("userid", userid));
										nameValuePairs.add(new BasicNameValuePair("orderid", orderid));
										HttpClient httpclient = new DefaultHttpClient();
										HttpPost httppost = new HttpPost(
												"http://www.sundaytek.com/deleteyuesaoorder.php");
										httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs, "UTF-8"));
										HttpResponse response = httpclient.execute(httppost);
										HttpEntity entity = response.getEntity();
										is = entity.getContent();

										btn_yuesao_ok_delete_cle = 1;
										Log.e("log_tag", "isme ");
									} catch (Exception e) {
										Log.d("log_tag", "Error in http connection" + e.toString());

										btn_yuesao_ok_delete_cle = 0;
									}
								}
							}
						}
					}).start();
					// Intent intent05 = new Intent();
					// intent05.setClass(baojie.this, yuesao.class);
					// startActivity(intent05);

					// convert response to string
					// try {
					// BufferedReader reader = new BufferedReader(new
					// InputStreamReader(is, "iso-8859-1"), 8);
					// sb = new StringBuilder();
					// sb.append(reader.readLine() + "\n");
					//
					// String line = "0";
					// while ((line = reader.readLine()) != null) {
					// sb.append(line + "\n");
					// }
					// is.close();
					// result = sb.toString();result = delete(result);
					// } catch (Exception e) {
					// Log.e("log_tag", "Error converting result " +
					// e.toString());
					// }
					// // paring data
					// int ct_id;
					// String ct_name;
					// try {
					// jArray = new JSONArray(result);
					// JSONObject json_data = null;
					// for (int i = 0; i < jArray.length(); i++) {
					// json_data = jArray.getJSONObject(i);
					// ct_id = json_data.getInt("id");
					// ct_name = json_data.getString("name");
					// tv.append(ct_name + " \n");
					// }
					// } catch (JSONException e1) {
					// // Toast.makeText(getBaseContext(), "No City Found"
					// // ,Toast.LENGTH_LONG).show();
					// } catch (ParseException e1) {
					// e1.printStackTrace();
					// }
					btn_yuesao_ok.setVisibility(View.GONE);
				}

			}
		});
		text_yuesao_address.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(yuesao.this, address.class);
				// resultCode---请求码
				Bundle bundle = new Bundle(); // 创建Bundle对象
				bundle.putString("something", address_temp_other); // 装入数据
				intent.putExtras(bundle);
				startActivityForResult(intent, 2);

			}

		});
		yuesao_otherdetail.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(yuesao.this, yuesaoedit.class);
				// resultCode---请求码
				Bundle bundle = new Bundle(); // 创建Bundle对象
				bundle.putString("something", temp); // 装入数据
				intent.putExtras(bundle);
				startActivityForResult(intent, 1);

				// resultCode---请求码
				// startActivityForResult(intent, 1);

			}

		});
		text_yuesao_serverscon.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				Intent intent = new Intent(yuesao.this, yuesaoserverscon.class);
				// resultCode---请求码
				startActivity(intent);

			}

		});
		btn_yuesao_back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				AlertDialog.Builder alertDialog = new AlertDialog.Builder(yuesao.this);
				alertDialog.setTitle("月嫂");
				alertDialog.setMessage("确定抛弃该订单吗？");
				alertDialog.setPositiveButton("点错了", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface arg0, int arg1) {
						// TODO Auto-generated method stub
					}
				});
				alertDialog.setNegativeButton("确定退出", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface arg0, int arg1) {
						// TODO Auto-generated method stub
						back_yuesao_int = 0;
						finish();
					}
				});
				alertDialog.show();

			}

		});

		btn_yuesao_bigdog.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				if (btn_yuesao_bigdog_i == 1) {

					btn_yuesao_bigdog.setBackgroundResource(R.drawable.bacyes);

					btn_yuesao_bigdog_i = (-1) * btn_yuesao_bigdog_i;

				} else if (btn_yuesao_bigdog_i == -1) {

					btn_yuesao_bigdog.setBackgroundResource(R.drawable.bacno);

					btn_yuesao_bigdog_i = (-1) * btn_yuesao_bigdog_i;

				}

			}

		});
		btn_yuesao_smalldog.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				if (btn_yuesao_smalldog_i == 1) {

					btn_yuesao_smalldog.setBackgroundResource(R.drawable.bacyes);

					btn_yuesao_smalldog_i = (-1) * btn_yuesao_smalldog_i;

				} else if (btn_yuesao_smalldog_i == -1) {

					btn_yuesao_smalldog.setBackgroundResource(R.drawable.bacno);

					btn_yuesao_smalldog_i = (-1) * btn_yuesao_smalldog_i;

				}

			}

		});
		btn_yuesao_cat.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				if (btn_yuesao_cat_i == 1) {

					btn_yuesao_cat.setBackgroundResource(R.drawable.bacyes);

					btn_yuesao_cat_i = (-1) * btn_yuesao_cat_i;

				} else if (btn_yuesao_cat_i == -1) {

					btn_yuesao_cat.setBackgroundResource(R.drawable.bacno);

					btn_yuesao_cat_i = (-1) * btn_yuesao_cat_i;

				}

			}

		});
		btn_yuesao_zhujia.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				if (btn_yuesao_zhujia_i == 1) {

					btn_yuesao_zhujia.setBackgroundResource(R.drawable.bacyes);

					btn_yuesao_zhujia_i = (-1) * btn_yuesao_zhujia_i;

				} else if (btn_yuesao_zhujia_i == -1) {

					btn_yuesao_zhujia.setBackgroundResource(R.drawable.bacno);

					btn_yuesao_zhujia_i = (-1) * btn_yuesao_zhujia_i;

				}

			}

		});
		btn_yuesao_buzhujia.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				if (btn_yuesao_buzhujia_i == 1) {

					btn_yuesao_buzhujia.setBackgroundResource(R.drawable.bacyes);

					btn_yuesao_buzhujia_i = (-1) * btn_yuesao_buzhujia_i;

				} else if (btn_yuesao_buzhujia_i == -1) {

					btn_yuesao_buzhujia.setBackgroundResource(R.drawable.bacno);

					btn_yuesao_buzhujia_i = (-1) * btn_yuesao_buzhujia_i;

				}

			}

		});
		text_choicedeliverdate.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

				DatePickerDialog dialog = new DatePickerDialog(yuesao.this, new DatePickerDialog.OnDateSetListener() {
					@Override
					public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
						c1.set(year, monthOfYear, dayOfMonth);
						et1.setText(DateFormat.format("yyy-MM-dd", c1));
					}
				}, c1.get(Calendar.YEAR), c1.get(Calendar.MONTH), c1.get(Calendar.DAY_OF_MONTH));
				dialog.show();
			}
		});
	}

	@Override
	public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
		// TODO Auto-generated method stub
		switch (seekBar.getId()) {
		case R.id.seekbar_yuesao_xinlijiawei: {
			// 璁剧疆鈥滀笌绯荤粺榛樿SeekBar瀵瑰簲鐨凾extView鈥濈殑鍊�

			int i = seekBar.getProgress();
			if (i >= 0 & i <= 34) {
				text_yuesao_jiaweijiage.setText("3k-4k(每月)");
			}
			if (i > 34 & i <= 67) {
				text_yuesao_jiaweijiage.setText("4k-5k(每月)");
			}
			if (i > 67 & i <= 100) {
				text_yuesao_jiaweijiage.setText("5k-6k(每月)");
			}
			break;
		}

		default:
			break;
		}

	}

	protected void go() {
		// TODO Auto-generated method stub
		Intent intentorder = new Intent(yuesao.this, yuesaoorder.class);
		// resultCode---请求码
		Bundle bundle = new Bundle(); // 创建Bundle对象
		bundle.putString("orderid", orderid); // 装入数据
		bundle.putString("userid", userid); // 装入数据
		intentorder.putExtras(bundle);
		startActivityForResult(intentorder, 3);
	}

	@Override
	public void onStartTrackingTouch(SeekBar seekBar) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onStopTrackingTouch(SeekBar seekBar) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		if (data == null)
			return;
		Bundle bundle = data.getExtras();
		String otherneed = bundle.getString("otherneed");
		switch (requestCode) {
		case 1:

			yuesao_otherdetail.setText(otherneed);
			temp_other = otherneed;
			temp = otherneed;

			break;
		case 2:
			String address = bundle.getString("address");
			text_yuesao_address.setText(address);
			// ArrayList nameValuePairs = new ArrayList();
			// temp = address;
			address_temp_other = address;
			// nameValuePairs.add(new BasicNameValuePair("otherneed",
			// otherneed));

			break;
		case 3:
			boolean staues = bundle.getBoolean("staues");
			if (staues) {
				Log.e("log_tag", "true ");
				btn_yuesao_ok.setVisibility(View.VISIBLE);
				Intent intent = new Intent();
				intent.putExtra("staues", true);
				setResult(0, intent);
				finish();
			} else {
				Log.e("log_tag", "false ");
				btn_yuesao_ok.setVisibility(View.VISIBLE);
			}

			break;
		default:
			break;
		}
	}

	private void read() {
		// TODO Auto-generated method stub
		username = pre.getString("username", "");
		password = pre.getString("password", "");
		Log.d("username", username);
		Log.d("password", password);
	}
}
