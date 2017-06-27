package com.example.cloud;

import java.io.InputStream;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import android.R.string;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.opengl.EGLSurface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.format.Time;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

public class yuer extends Activity implements SeekBar.OnSeekBarChangeListener {
	private int back_yuer_int = 1;
	private int btn_yuer_ok_delete_cle = 0;
	private int chioce_sex = 0;
	private int chioce_zhujia = 0;
	private int btn_yuer_ok_cle = 0;
	private TextView text_yuer_jiaweijiage;
	private SeekBar seekbar_yuer_age;
	private SeekBar seekbar_yuer_xinlijiawei;
	private Button btn_yuer_boy;
	private int btn_yuer_boy_i = 1;
	private Button btn_yuer_buzhujia;
	private int btn_yuer_buzhujia_i = 1;
	private Button btn_yuer_zhujia;
	private int btn_yuer_zhujia_i = 1;
	private Button btn_yuer_girl;
	private int btn_yuer_girl_i = 1;
	private Button btn_yuer_bigdog;
	private int btn_yuer_bigdog_i = 1;
	private Button btn_yuer_smalldog;
	private int btn_yuer_smalldog_i = 1;
	private Button btn_yuer_cat;
	private int btn_yuer_cat_i = 1;
	private TextView btn_yuer_back;
	private TextView text_yuer_serverscon;
	private TextView yuer_otherdetail;
	private TextView text_yuer_address;
	private String address_temp_other = "";
	private String temp_other = "未填写";
	private String temp = "";
	private String orderid;
	private String userid = "1";
	private String typeid = "4";
	private String typename = "育儿嫂";
	private String sex = " ";
	private String zhujia = " ";
	private String pet = " ";
	private Button btn_yuer_ok;
	private String text_yuer_contact_text;
	private String text_yuer_phone_text;
	private String text_mianjidaxiao_text;
	private String text_jiaweijiage_text;
	private TextView text_yuer_contact;
	private TextView text_yuer_phone;
	private TextView text_yuer_age;
	private String text_yuer_age_text;
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
			AlertDialog.Builder alertDialog = new AlertDialog.Builder(yuer.this);
			alertDialog.setTitle("育儿嫂");
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
					back_yuer_int = 0;
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
		setContentView(R.layout.layoutyuer);
		pre = getSharedPreferences("userinfo", MODE_PRIVATE);
		read();
		userid = username;
		Intent intent = this.getIntent(); // 获取已有的intent对象
		Bundle bundle = intent.getExtras(); // 获取intent里面的bundle对象
		text_yuer_contact = (TextView) findViewById(R.id.text_yuer_order_contactyuer);
		text_yuer_phone = (TextView) findViewById(R.id.text_yuer_order_phoneyuer);
		btn_yuer_ok = (Button) findViewById(R.id.btn_yuer_ok);
		btn_yuer_bigdog = (Button) findViewById(R.id.btn_yuer_bigdog);
		btn_yuer_smalldog = (Button) findViewById(R.id.btn_yuer_smalldog);
		btn_yuer_cat = (Button) findViewById(R.id.btn_yuer_cat);
		btn_yuer_zhujia = (Button) findViewById(R.id.btn_yuer_zhujia);
		btn_yuer_buzhujia = (Button) findViewById(R.id.btn_yuer_buzhujia);
		btn_yuer_boy = (Button) findViewById(R.id.btn_yuer_boy);
		btn_yuer_girl = (Button) findViewById(R.id.btn_yuer_girl);
		text_yuer_age = (TextView) findViewById(R.id.text_yuer_ageyuer);
		text_yuer_jiaweijiage = (TextView) findViewById(R.id.text_yuer_jiaweijiage);
		seekbar_yuer_age = (SeekBar) findViewById(R.id.seekbar_yuer_age);
		seekbar_yuer_age.setOnSeekBarChangeListener(this);
		seekbar_yuer_xinlijiawei = (SeekBar) findViewById(R.id.seekbar_yuer_xinlijiawei);
		seekbar_yuer_xinlijiawei.setOnSeekBarChangeListener(this);
		text_yuer_serverscon = (TextView) findViewById(R.id.text_yuer_serverscon);
		btn_yuer_back = (TextView) findViewById(R.id.btn_yuer_back);
		yuer_otherdetail = (TextView) findViewById(R.id.yuer_otherdetail);
		text_yuer_address = (TextView) findViewById(R.id.text_yuer_address);
		final ArrayList nameValuePairs = new ArrayList();
		btn_yuer_ok.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				text_yuer_contact_text = text_yuer_contact.getText().toString();
				text_yuer_phone_text = text_yuer_phone.getText().toString();
				text_jiaweijiage_text = (String) text_yuer_jiaweijiage.getText();
				text_yuer_age_text = text_yuer_age.getText().toString();
				pet = "";
				zhujia = "";
				sex = "";
				chioce_sex = 0;
				chioce_zhujia = 0;
				if (btn_yuer_boy_i == -1) {
					sex = "男孩";
					chioce_sex = 1;
				} else if (btn_yuer_girl_i == -1) {
					sex = "女孩";
					chioce_sex = 1;
				} else if (true) {
					sex = "未选择";
					chioce_sex = 0;
				}
				if (btn_yuer_buzhujia_i == -1) {
					zhujia = "不住家";
					chioce_zhujia = 1;
				} else if (btn_yuer_zhujia_i == -1) {
					zhujia = "住家";
					chioce_zhujia = 1;
				} else if (true) {
					zhujia = "无要求";
					chioce_zhujia = 0;
				}

				if (btn_yuer_bigdog_i == -1) {
					pet = pet + "大型犬 ";
				}
				if (btn_yuer_smalldog_i == -1) {
					pet = pet + "小型犬 ";
				}
				if (btn_yuer_cat_i == -1) {
					pet = pet + "猫 ";
				}
				if (btn_yuer_bigdog_i == 1 && btn_yuer_smalldog_i == 1 && btn_yuer_cat_i == 1) {
					pet = "无宠物";
				}
				if (chioce_sex == 0) {
					AlertDialog alertDialog = new AlertDialog.Builder(yuer.this).create();
					AlertDialog.Builder settingDialog = new AlertDialog.Builder(yuer.this);
					settingDialog.setInverseBackgroundForced(true);
					alertDialog.setMessage("请选择宝贝性别");
					alertDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "知道了",
							new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
						}
					});
					alertDialog.show();

				} else if (text_yuer_age_text.equals("请选择年龄")) {
					AlertDialog alertDialog = new AlertDialog.Builder(yuer.this).create();
					AlertDialog.Builder settingDialog = new AlertDialog.Builder(yuer.this);
					settingDialog.setInverseBackgroundForced(true);
					alertDialog.setMessage("请选择宝宝年龄");
					alertDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "知道了",
							new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
						}
					});
					alertDialog.show();
				} else if (text_jiaweijiage_text.equals("请选择价格")) {
					AlertDialog alertDialog = new AlertDialog.Builder(yuer.this).create();
					AlertDialog.Builder settingDialog = new AlertDialog.Builder(yuer.this);
					settingDialog.setInverseBackgroundForced(true);
					alertDialog.setMessage("请选择心理价位");
					alertDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "知道了",
							new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
						}
					});
					alertDialog.show();
				} else if (chioce_zhujia == 0) {

					AlertDialog alertDialog = new AlertDialog.Builder(yuer.this).create();
					AlertDialog.Builder settingDialog = new AlertDialog.Builder(yuer.this);
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
					AlertDialog alertDialog = new AlertDialog.Builder(yuer.this).create();
					AlertDialog.Builder settingDialog = new AlertDialog.Builder(yuer.this);
					settingDialog.setInverseBackgroundForced(true);
					alertDialog.setMessage("请选择地址");
					alertDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "知道了",
							new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
						}
					});
					alertDialog.show();

				} else if (text_yuer_contact_text.equals("")) {
					AlertDialog alertDialog = new AlertDialog.Builder(yuer.this).create();
					AlertDialog.Builder settingDialog = new AlertDialog.Builder(yuer.this);
					settingDialog.setInverseBackgroundForced(true);
					alertDialog.setMessage("请填写联系人");
					alertDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "知道了",
							new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
						}
					});
					alertDialog.show();

				} else if (text_yuer_phone_text.equals("")) {
					AlertDialog alertDialog = new AlertDialog.Builder(yuer.this).create();
					AlertDialog.Builder settingDialog = new AlertDialog.Builder(yuer.this);
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

					(new Thread() {

						@Override

						public void run() {
							nameValuePairs.add(new BasicNameValuePair("pet", pet));

							nameValuePairs.add(new BasicNameValuePair("price", text_jiaweijiage_text));
							nameValuePairs.add(new BasicNameValuePair("contact", text_yuer_contact_text));
							nameValuePairs.add(new BasicNameValuePair("phonenumber", text_yuer_phone_text));
							nameValuePairs.add(new BasicNameValuePair("typename", typename));
							nameValuePairs.add(new BasicNameValuePair("typeid", typeid));
							nameValuePairs.add(new BasicNameValuePair("userid", userid));
							nameValuePairs.add(new BasicNameValuePair("orderid", orderid));
							nameValuePairs.add(new BasicNameValuePair("otherneed", temp_other));
							nameValuePairs.add(new BasicNameValuePair("address", address_temp_other));
							nameValuePairs.add(new BasicNameValuePair("age", text_yuer_age_text));
							nameValuePairs.add(new BasicNameValuePair("zhujia", zhujia));
							nameValuePairs.add(new BasicNameValuePair("sex", sex));
							nameValuePairs.add(new BasicNameValuePair("client", "true"));
							nameValuePairs.add(new BasicNameValuePair("server", "true"));
							nameValuePairs.add(new BasicNameValuePair("condition", "待处理"));
							Log.d("hhhhhhhhh", pet);

							Log.d("hhhhhhhhh", text_jiaweijiage_text);
							Log.d("hhhhhhhhh", text_yuer_contact_text);
							Log.d("hhhhhhhhh", text_yuer_phone_text);
							Log.d("hhhhhhhhh", typename);
							Log.d("hhhhhhhhh", typeid);
							Log.d("hhhhhhhhh", userid);
							Log.d("hhhhhhhhh", orderid);
							Log.d("hhhhhhhhh", temp_other);
							Log.d("hhhhhhhhh", address_temp_other);
							Log.d("hhhhhhhhh", text_yuer_age_text);
							Log.d("hhhhhhhhh", zhujia);
							Log.d("hhhhhhhhh", sex);
							btn_yuer_ok_cle = 0;
							for (btn_yuer_ok_cle = 0; btn_yuer_ok_cle < 1; btn_yuer_ok_cle = btn_yuer_ok_cle) {
								if (back_yuer_int == 1) {

									try {
										HttpClient httpclient = new DefaultHttpClient();
										HttpPost httppost = new HttpPost(
												"http://www.sundaytek.com/insertorderyuer.php");
										httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs, "UTF-8"));
										HttpResponse response = httpclient.execute(httppost);
										HttpEntity entity = response.getEntity();

										btn_yuer_ok_cle = 1;
										is = entity.getContent();
										// Log.e("log_tag", "对不对" );
									} catch (Exception e) {
										Log.e("log_tag", "Error in http connection" + e.toString());
										btn_yuer_ok_cle = 0;
									}
								}
							}
							if (back_yuer_int == 1) {
								Message msg = new Message();
								msg.what = 1;
								msg.arg1 = 123;
								msg.arg2 = 321;
								uiHandler.sendMessage(msg);
							} else {
								btn_yuer_ok_delete_cle = 0;
								for (btn_yuer_ok_delete_cle = 0; btn_yuer_ok_delete_cle < 1; btn_yuer_ok_delete_cle = btn_yuer_ok_delete_cle) {
									try {

										nameValuePairs.add(new BasicNameValuePair("userid", userid));
										nameValuePairs.add(new BasicNameValuePair("orderid", orderid));
										HttpClient httpclient = new DefaultHttpClient();
										HttpPost httppost = new HttpPost(
												"http://www.sundaytek.com/deleteyuerorder.php");
										httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs, "UTF-8"));
										HttpResponse response = httpclient.execute(httppost);
										HttpEntity entity = response.getEntity();
										is = entity.getContent();
										btn_yuer_ok_delete_cle = 1;
										Log.e("log_tag", "isme ");
									} catch (Exception e) {
										Log.d("log_tag", "Error in http connection" + e.toString());
										btn_yuer_ok_delete_cle = 1;
									}
								}
							}
						}

					}).start();
					btn_yuer_ok.setVisibility(View.GONE);
				}

			}
		});
		text_yuer_address.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(yuer.this, address.class);
				// resultCode---请求码
				Bundle bundle = new Bundle(); // 创建Bundle对象
				bundle.putString("something", address_temp_other); // 装入数据
				intent.putExtras(bundle);
				startActivityForResult(intent, 2);

			}

		});
		yuer_otherdetail.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(yuer.this, yueredit.class);
				// resultCode---请求码
				Bundle bundle = new Bundle(); // 创建Bundle对象
				bundle.putString("something", temp); // 装入数据
				intent.putExtras(bundle);
				startActivityForResult(intent, 1);

				// resultCode---请求码
				// startActivityForResult(intent, 1);

			}

		});
		text_yuer_serverscon.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				Intent intent = new Intent(yuer.this, yuerserverscon.class);
				// resultCode---请求码
				startActivity(intent);

			}

		});
		btn_yuer_back.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				AlertDialog.Builder alertDialog = new AlertDialog.Builder(yuer.this);
				alertDialog.setTitle("育儿嫂");
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
						back_yuer_int = 0;
						finish();
					}
				});
				alertDialog.show();

			}

		});

		btn_yuer_bigdog.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				if (btn_yuer_bigdog_i == 1) {

					btn_yuer_bigdog.setBackgroundResource(R.drawable.bacyes);

					btn_yuer_bigdog_i = (-1) * btn_yuer_bigdog_i;

				} else if (btn_yuer_bigdog_i == -1) {

					btn_yuer_bigdog.setBackgroundResource(R.drawable.bacno);

					btn_yuer_bigdog_i = (-1) * btn_yuer_bigdog_i;

				}

			}

		});
		btn_yuer_smalldog.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				if (btn_yuer_smalldog_i == 1) {

					btn_yuer_smalldog.setBackgroundResource(R.drawable.bacyes);

					btn_yuer_smalldog_i = (-1) * btn_yuer_smalldog_i;

				} else if (btn_yuer_smalldog_i == -1) {

					btn_yuer_smalldog.setBackgroundResource(R.drawable.bacno);

					btn_yuer_smalldog_i = (-1) * btn_yuer_smalldog_i;

				}

			}

		});
		btn_yuer_cat.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				if (btn_yuer_cat_i == 1) {

					btn_yuer_cat.setBackgroundResource(R.drawable.bacyes);

					btn_yuer_cat_i = (-1) * btn_yuer_cat_i;

				} else if (btn_yuer_cat_i == -1) {

					btn_yuer_cat.setBackgroundResource(R.drawable.bacno);

					btn_yuer_cat_i = (-1) * btn_yuer_cat_i;

				}

			}

		});
		btn_yuer_zhujia.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				if (btn_yuer_zhujia_i == 1) {

					btn_yuer_zhujia.setBackgroundResource(R.drawable.bacyes);

					btn_yuer_zhujia_i = (-1) * btn_yuer_zhujia_i;

				} else if (btn_yuer_zhujia_i == -1) {

					btn_yuer_zhujia.setBackgroundResource(R.drawable.bacno);

					btn_yuer_zhujia_i = (-1) * btn_yuer_zhujia_i;

				}

			}

		});
		btn_yuer_buzhujia.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				if (btn_yuer_buzhujia_i == 1) {

					btn_yuer_buzhujia.setBackgroundResource(R.drawable.bacyes);

					btn_yuer_buzhujia_i = (-1) * btn_yuer_buzhujia_i;

				} else if (btn_yuer_buzhujia_i == -1) {

					btn_yuer_buzhujia.setBackgroundResource(R.drawable.bacno);

					btn_yuer_buzhujia_i = (-1) * btn_yuer_buzhujia_i;

				}

			}

		});
		btn_yuer_boy.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				if (btn_yuer_boy_i == 1) {

					btn_yuer_boy.setBackgroundResource(R.drawable.bacyes);

					btn_yuer_boy_i = (-1) * btn_yuer_boy_i;

				} else if (btn_yuer_boy_i == -1) {

					btn_yuer_boy.setBackgroundResource(R.drawable.bacno);

					btn_yuer_boy_i = (-1) * btn_yuer_boy_i;

				}

			}

		});
		btn_yuer_girl.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				if (btn_yuer_girl_i == 1) {

					btn_yuer_girl.setBackgroundResource(R.drawable.bacyes);

					btn_yuer_girl_i = (-1) * btn_yuer_girl_i;

				} else if (btn_yuer_girl_i == -1) {

					btn_yuer_girl.setBackgroundResource(R.drawable.bacno);

					btn_yuer_girl_i = (-1) * btn_yuer_girl_i;

				}

			}

		});
	}

	protected void go() {
		// TODO Auto-generated method stub
		Intent intentorder = new Intent(yuer.this, yuerorder.class);
		// resultCode---请求码
		Bundle bundle = new Bundle(); // 创建Bundle对象
		bundle.putString("orderid", orderid); // 装入数据
		bundle.putString("userid", userid); // 装入数据
		intentorder.putExtras(bundle);
		startActivityForResult(intentorder, 3);
	}

	@Override
	public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
		// TODO Auto-generated method stub
		switch (seekBar.getId()) {
		case R.id.seekbar_yuer_xinlijiawei: {
			// 璁剧疆鈥滀笌绯荤粺榛樿SeekBar瀵瑰簲鐨凾extView鈥濈殑鍊�

			int i = seekBar.getProgress();
			if (i >= 0 & i <= 34) {
				text_yuer_jiaweijiage.setText("3k-4k(每月)");
			}
			if (i > 34 & i <= 67) {
				text_yuer_jiaweijiage.setText("4k-5k(每月)");
			}
			if (i > 67 & i <= 100) {
				text_yuer_jiaweijiage.setText("5k-6k(每月)");
			}
			break;
		}
		case R.id.seekbar_yuer_age: {
			int i = seekBar.getProgress();

			if (i >= 0 & i < 25) {
				text_yuer_age.setText("1-6个月");
			}
			if (i >= 25 & i < 50) {
				text_yuer_age.setText("6-12个月");
			}
			if (i >= 50 & i < 98) {
				text_yuer_age.setText("12-24个月");
			}
			if (i >= 98) {
				text_yuer_age.setText("24个月以上");
			}

			break;
		}
		default:
			break;
		}
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

			yuer_otherdetail.setText(otherneed);
			temp_other = otherneed;
			temp = otherneed;
			break;
		case 2:
			String address = bundle.getString("address");
			text_yuer_address.setText(address);
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
				btn_yuer_ok.setVisibility(View.VISIBLE);
				Intent intent = new Intent();
				intent.putExtra("staues", true);
				setResult(0, intent);
				finish();
			} else {
				Log.e("log_tag", "false ");
				btn_yuer_ok.setVisibility(View.VISIBLE);
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
