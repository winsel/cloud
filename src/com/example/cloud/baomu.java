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

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
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

public class baomu extends Activity implements SeekBar.OnSeekBarChangeListener {
	private int chioce_taste = 0;
	private int btn_baomu_ok_cle = 0;
	private int back_baomu_int = 1;
	private int btn_baomu_ok_delete_cle = 0;
	private TextView text_baomu_jiaweijiage;
	private TextView text_baomu_mianjidaxiao;
	private TextView text_baomu_contact;
	private TextView text_baomu_phone;
	private SeekBar seekbar_baomu_xinlijiawei;
	private SeekBar seekbar_baomu_fangwumianji;
	private Button btn_baomu_xian;
	private int btn_baomu_xian_i = 1;
	private Button btn_baomu_dan;
	private int btn_baomu_dan_i = 1;
	private Button btn_baomu_la;
	private int btn_baomu_la_i = 1;
	private Button btn_baomu_qingdan;
	private int btn_baomu_qingdan_i = 1;
	private Button btn_baomu_mianshi;
	private int btn_baomu_mianshi_i = 1;
	private Button btn_baomu_bigdog;
	private int btn_baomu_bigdog_i = 1;
	private Button btn_baomu_smalldog;
	private int btn_baomu_smalldog_i = 1;
	private Button btn_baomu_cat;
	private int btn_baomu_cat_i = 1;
	private TextView baomu_otherdetail;
	private TextView btn_baomu_back;
	private TextView text_baomu_serverscon;
	private TextView text_baomu_address;
	private String temp_other = "未填写";
	private String temp = "";
	private String orderid;
	private String userid = "1";
	private String address_temp_other = "";
	private Button btn_baomu_ok;
	private String taste = " ";
	private String pet = " ";
	private String text_baomu_contact_text;
	private String text_baomu_phone_text;
	private String text_mianjidaxiao_text;
	private String text_jiaweijiage_text;
	private String typeid = "3";
	private String typename = "住家保姆";
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
			AlertDialog.Builder alertDialog = new AlertDialog.Builder(baomu.this);
			alertDialog.setTitle("住家保姆");
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
					back_baomu_int = 0;// TODO Auto-generated method stub
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
		setContentView(R.layout.layoutbaomu);
		pre = getSharedPreferences("userinfo", MODE_PRIVATE);
		read();
		userid = username;
		btn_baomu_ok = (Button) findViewById(R.id.btn_baomu_ok);
		text_baomu_jiaweijiage = (TextView) findViewById(R.id.text_baomu_jiaweijiage);
		text_baomu_mianjidaxiao = (TextView) findViewById(R.id.text_baomu_mianjidaxiao);
		seekbar_baomu_xinlijiawei = (SeekBar) findViewById(R.id.seekbar_baomu_xinlijiawei);
		seekbar_baomu_xinlijiawei.setOnSeekBarChangeListener(this);
		seekbar_baomu_fangwumianji = (SeekBar) findViewById(R.id.seekbar_baomu_fangwumianji);
		seekbar_baomu_fangwumianji.setOnSeekBarChangeListener(this);
		btn_baomu_xian = (Button) findViewById(R.id.btn_baomu_xian);
		btn_baomu_dan = (Button) findViewById(R.id.btn_baomu_dan);
		btn_baomu_la = (Button) findViewById(R.id.btn_baomu_la);
		btn_baomu_qingdan = (Button) findViewById(R.id.btn_baomu_qingdan);
		btn_baomu_mianshi = (Button) findViewById(R.id.btn_baomu_mianshi);
		btn_baomu_bigdog = (Button) findViewById(R.id.btn_baomu_bigdog);
		btn_baomu_smalldog = (Button) findViewById(R.id.btn_baomu_smalldog);
		btn_baomu_cat = (Button) findViewById(R.id.btn_baomu_cat);
		baomu_otherdetail = (TextView) findViewById(R.id.baomu_otherdetail);
		Intent intent = this.getIntent(); // 获取已有的intent对象
		Bundle bundle = intent.getExtras(); // 获取intent里面的bundle对象
		text_baomu_contact = (TextView) findViewById(R.id.text_baomu_contact);
		text_baomu_phone = (TextView) findViewById(R.id.text_baomu_phone);
		text_baomu_serverscon = (TextView) findViewById(R.id.text_baomu_serverscon);
		btn_baomu_back = (TextView) findViewById(R.id.btn_baomu_back);
		text_baomu_address = (TextView) findViewById(R.id.text_baomu_address);
		final ArrayList nameValuePairs = new ArrayList();
		btn_baomu_ok.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				text_baomu_contact_text = text_baomu_contact.getText().toString();
				text_baomu_phone_text = text_baomu_phone.getText().toString();
				text_mianjidaxiao_text = (String) text_baomu_mianjidaxiao.getText();
				text_jiaweijiage_text = (String) text_baomu_jiaweijiage.getText();
				chioce_taste = 0;
				taste = " ";
				pet = " ";
				if (btn_baomu_xian_i == -1) {
					taste = taste + "偏咸 ";
					chioce_taste = 1;
				}
				if (btn_baomu_dan_i == -1) {
					taste = taste + "偏淡 ";
					chioce_taste = 1;
				}
				if (btn_baomu_la_i == -1) {
					taste = taste + "偏辣 ";
					chioce_taste = 1;
				}
				if (btn_baomu_qingdan_i == -1) {
					taste = taste + "清淡";
					chioce_taste = 1;
				}
				if (btn_baomu_mianshi_i == -1) {
					taste = taste + "面食";
					chioce_taste = 1;
				}
				if (btn_baomu_xian_i == 1 && btn_baomu_dan_i == 1 && btn_baomu_la_i == 1 && btn_baomu_qingdan_i == 1
						&& btn_baomu_mianshi_i == 1) {
					taste = "无要求";
					chioce_taste = 0;
				}

				if (btn_baomu_bigdog_i == -1) {
					pet = pet + "大型犬 ";
				}
				if (btn_baomu_smalldog_i == -1) {
					pet = pet + "小型犬 ";
				}
				if (btn_baomu_cat_i == -1) {
					pet = pet + "猫 ";
				}
				if (btn_baomu_bigdog_i == 1 && btn_baomu_smalldog_i == 1 && btn_baomu_cat_i == 1) {
					pet = "无宠物";
				}
				if (text_mianjidaxiao_text.equals("请选择面积")) {
					AlertDialog alertDialog = new AlertDialog.Builder(baomu.this).create();
					AlertDialog.Builder settingDialog = new AlertDialog.Builder(baomu.this);
					settingDialog.setInverseBackgroundForced(true);
					alertDialog.setMessage("请选择房屋面积");
					alertDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "知道了",
							new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
						}
					});
					alertDialog.show();
				} else if (chioce_taste == 0) {
					AlertDialog alertDialog = new AlertDialog.Builder(baomu.this).create();
					AlertDialog.Builder settingDialog = new AlertDialog.Builder(baomu.this);
					settingDialog.setInverseBackgroundForced(true);
					alertDialog.setMessage("请选择饭菜口味");
					alertDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "知道了",
							new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
						}
					});
					alertDialog.show();
				} else if (text_jiaweijiage_text.equals("请选择价格")) {
					AlertDialog alertDialog = new AlertDialog.Builder(baomu.this).create();
					AlertDialog.Builder settingDialog = new AlertDialog.Builder(baomu.this);
					settingDialog.setInverseBackgroundForced(true);
					alertDialog.setMessage("请选择心理价位");
					alertDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "知道了",
							new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
						}
					});
					alertDialog.show();
				} else if (address_temp_other.equals("")) {
					AlertDialog alertDialog = new AlertDialog.Builder(baomu.this).create();
					AlertDialog.Builder settingDialog = new AlertDialog.Builder(baomu.this);
					settingDialog.setInverseBackgroundForced(true);
					alertDialog.setMessage("请选择地址");
					alertDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "知道了",
							new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
						}
					});
					alertDialog.show();

				} else if (text_baomu_contact_text.equals("")) {
					AlertDialog alertDialog = new AlertDialog.Builder(baomu.this).create();
					AlertDialog.Builder settingDialog = new AlertDialog.Builder(baomu.this);
					settingDialog.setInverseBackgroundForced(true);
					alertDialog.setMessage("请填写联系人");
					alertDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "知道了",
							new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
						}
					});
					alertDialog.show();

				} else if (text_baomu_phone_text.equals("")) {
					AlertDialog alertDialog = new AlertDialog.Builder(baomu.this).create();
					AlertDialog.Builder settingDialog = new AlertDialog.Builder(baomu.this);
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
							nameValuePairs.add(new BasicNameValuePair("taste", taste));
							nameValuePairs.add(new BasicNameValuePair("pet", pet));
							nameValuePairs.add(new BasicNameValuePair("square", text_mianjidaxiao_text));
							nameValuePairs.add(new BasicNameValuePair("price", text_jiaweijiage_text));
							nameValuePairs.add(new BasicNameValuePair("contact", text_baomu_contact_text));
							nameValuePairs.add(new BasicNameValuePair("phonenumber", text_baomu_phone_text));
							nameValuePairs.add(new BasicNameValuePair("typename", typename));
							nameValuePairs.add(new BasicNameValuePair("typeid", typeid));
							nameValuePairs.add(new BasicNameValuePair("userid", userid));
							nameValuePairs.add(new BasicNameValuePair("orderid", orderid));
							nameValuePairs.add(new BasicNameValuePair("otherneed", temp_other));
							nameValuePairs.add(new BasicNameValuePair("address", address_temp_other));
							nameValuePairs.add(new BasicNameValuePair("client", "true"));
							nameValuePairs.add(new BasicNameValuePair("server", "true"));
							nameValuePairs.add(new BasicNameValuePair("condition", "待处理"));
							Log.d("hhhhhhhhh", taste);
							Log.d("hhhhhhhhh", pet);
							Log.d("hhhhhhhhh", text_mianjidaxiao_text);
							Log.d("hhhhhhhhh", text_jiaweijiage_text);
							Log.d("hhhhhhhhh", text_baomu_contact_text);
							Log.d("hhhhhhhhh", text_baomu_phone_text);
							Log.d("hhhhhhhhh", typename);
							Log.d("hhhhhhhhh", typeid);
							Log.d("hhhhhhhhh", userid);
							Log.d("hhhhhhhhh", orderid);
							Log.d("hhhhhhhhh", temp_other);
							Log.d("hhhhhhhhh", address_temp_other);
							btn_baomu_ok_cle = 0;
							for (btn_baomu_ok_cle = 0; btn_baomu_ok_cle < 1; btn_baomu_ok_cle = btn_baomu_ok_cle) {
								if (back_baomu_int == 1) {

									try {
										HttpClient httpclient = new DefaultHttpClient();
										HttpPost httppost = new HttpPost(
												"http://www.sundaytek.com/insertorderbaomu.php");
										httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs, "UTF-8"));
										HttpResponse response = httpclient.execute(httppost);
										HttpEntity entity = response.getEntity();

										btn_baomu_ok_cle = 1;
										is = entity.getContent();
									} catch (Exception e) {
										Log.e("log_tag", "Error in http connection" + e.toString());
										btn_baomu_ok_cle = 0;
									}
								}
							}
							if (back_baomu_int == 1) {
								Message msg = new Message();
								msg.what = 1;
								msg.arg1 = 123;
								msg.arg2 = 321;
								uiHandler.sendMessage(msg);
							} else {
								btn_baomu_ok_delete_cle = 0;
								for (btn_baomu_ok_delete_cle = 0; btn_baomu_ok_delete_cle < 1; btn_baomu_ok_delete_cle = btn_baomu_ok_delete_cle) {
									try {
										nameValuePairs.add(new BasicNameValuePair("userid", userid));
										nameValuePairs.add(new BasicNameValuePair("orderid", orderid));
										HttpClient httpclient = new DefaultHttpClient();
										HttpPost httppost = new HttpPost(
												"http://www.sundaytek.com/deletebaomuorder.php");
										httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs, "UTF-8"));
										HttpResponse response = httpclient.execute(httppost);
										HttpEntity entity = response.getEntity();

										btn_baomu_ok_delete_cle = 1;
										is = entity.getContent();
										Log.e("log_tag", "isme ");
									} catch (Exception e) {
										Log.d("log_tag", "Error in http connection" + e.toString());
										btn_baomu_ok_delete_cle = 0;
									}
								}
							}
						}

					}).start();
					btn_baomu_ok.setVisibility(View.GONE);
				}

			}
		});
		text_baomu_address.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(baomu.this, address.class);
				Bundle bundle = new Bundle(); // 创建Bundle对象
				bundle.putString("something", address_temp_other); // 装入数据
				intent.putExtras(bundle);
				startActivityForResult(intent, 2);
			}
		});
		text_baomu_serverscon.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				Intent intent = new Intent(baomu.this, baomuserverscon.class);
				startActivity(intent);

			}

		});
		btn_baomu_back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				AlertDialog.Builder alertDialog = new AlertDialog.Builder(baomu.this);
				alertDialog.setTitle("住家保姆");
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
						back_baomu_int = 0;// TODO Auto-generated method stub
						finish();
					}
				});
				alertDialog.show();

			}

		});

		baomu_otherdetail.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(baomu.this, baomuedit.class);
				// resultCode---请求码
				Bundle bundle = new Bundle(); // 创建Bundle对象
				bundle.putString("something", temp); // 装入数据
				intent.putExtras(bundle);
				// startActivityForResult(intent, 1);

				startActivityForResult(intent, 1);

			}

		});
		btn_baomu_bigdog.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				if (btn_baomu_bigdog_i == 1) {

					btn_baomu_bigdog.setBackgroundResource(R.drawable.bacyes);

					btn_baomu_bigdog_i = (-1) * btn_baomu_bigdog_i;

				} else if (btn_baomu_bigdog_i == -1) {

					btn_baomu_bigdog.setBackgroundResource(R.drawable.bacno);

					btn_baomu_bigdog_i = (-1) * btn_baomu_bigdog_i;

				}

			}

		});
		btn_baomu_smalldog.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				if (btn_baomu_smalldog_i == 1) {

					btn_baomu_smalldog.setBackgroundResource(R.drawable.bacyes);

					btn_baomu_smalldog_i = (-1) * btn_baomu_smalldog_i;

				} else if (btn_baomu_smalldog_i == -1) {

					btn_baomu_smalldog.setBackgroundResource(R.drawable.bacno);

					btn_baomu_smalldog_i = (-1) * btn_baomu_smalldog_i;

				}

			}

		});
		btn_baomu_cat.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				if (btn_baomu_cat_i == 1) {

					btn_baomu_cat.setBackgroundResource(R.drawable.bacyes);

					btn_baomu_cat_i = (-1) * btn_baomu_cat_i;

				} else if (btn_baomu_cat_i == -1) {

					btn_baomu_cat.setBackgroundResource(R.drawable.bacno);

					btn_baomu_cat_i = (-1) * btn_baomu_cat_i;

				}

			}

		});
		btn_baomu_xian.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				if (btn_baomu_xian_i == 1) {

					btn_baomu_xian.setBackgroundResource(R.drawable.bacyes);

					btn_baomu_xian_i = (-1) * btn_baomu_xian_i;

				} else if (btn_baomu_xian_i == -1) {

					btn_baomu_xian.setBackgroundResource(R.drawable.bacno);

					btn_baomu_xian_i = (-1) * btn_baomu_xian_i;

				}

			}

		});
		btn_baomu_dan.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				if (btn_baomu_dan_i == 1) {

					btn_baomu_dan.setBackgroundResource(R.drawable.bacyes);

					btn_baomu_dan_i = (-1) * btn_baomu_dan_i;

				} else if (btn_baomu_dan_i == -1) {

					btn_baomu_dan.setBackgroundResource(R.drawable.bacno);

					btn_baomu_dan_i = (-1) * btn_baomu_dan_i;

				}

			}

		});
		btn_baomu_la.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				if (btn_baomu_la_i == 1) {

					btn_baomu_la.setBackgroundResource(R.drawable.bacyes);

					btn_baomu_la_i = (-1) * btn_baomu_la_i;

				} else if (btn_baomu_la_i == -1) {

					btn_baomu_la.setBackgroundResource(R.drawable.bacno);

					btn_baomu_la_i = (-1) * btn_baomu_la_i;

				}

			}

		});
		btn_baomu_qingdan.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				if (btn_baomu_qingdan_i == 1) {

					btn_baomu_qingdan.setBackgroundResource(R.drawable.bacyes);

					btn_baomu_qingdan_i = (-1) * btn_baomu_qingdan_i;

				} else if (btn_baomu_qingdan_i == -1) {

					btn_baomu_qingdan.setBackgroundResource(R.drawable.bacno);

					btn_baomu_qingdan_i = (-1) * btn_baomu_qingdan_i;

				}

			}

		});
		btn_baomu_mianshi.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				if (btn_baomu_mianshi_i == 1) {

					btn_baomu_mianshi.setBackgroundResource(R.drawable.bacyes);

					btn_baomu_mianshi_i = (-1) * btn_baomu_mianshi_i;

				} else if (btn_baomu_mianshi_i == -1) {

					btn_baomu_mianshi.setBackgroundResource(R.drawable.bacno);

					btn_baomu_mianshi_i = (-1) * btn_baomu_mianshi_i;

				}

			}

		});

	}

	protected void go() {
		// TODO Auto-generated method stub
		Intent intentorder = new Intent(baomu.this, baomuorder.class);
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
		case R.id.seekbar_baomu_xinlijiawei: {
			// 璁剧疆鈥滀笌绯荤粺榛樿SeekBar瀵瑰簲鐨凾extView鈥濈殑鍊�

			int i = seekBar.getProgress();
			if (i >= 0 & i <= 34) {
				text_baomu_jiaweijiage.setText("3k-4k(每月)");
			}
			if (i > 34 & i <= 67) {
				text_baomu_jiaweijiage.setText("4k-5k(每月)");
			}
			if (i > 67 & i <= 100) {
				text_baomu_jiaweijiage.setText("5k-6k(每月)");
			}
			break;
		}
		case R.id.seekbar_baomu_fangwumianji: {
			int i = seekBar.getProgress();
			if (i == 0) {

				text_baomu_mianjidaxiao.setText("50平方以下");
			}
			if (i > 0 & i < 20) {
				text_baomu_mianjidaxiao.setText("50-80平方");
			}
			if (i >= 20 & i < 40) {
				text_baomu_mianjidaxiao.setText("80-110平方");
			}
			if (i >= 40 & i < 60) {
				text_baomu_mianjidaxiao.setText("110-140平方");
			}
			if (i >= 60 & i < 80) {
				text_baomu_mianjidaxiao.setText("140-170平方");
			}
			if (i >= 80 & i < 100) {
				text_baomu_mianjidaxiao.setText("170-200平方");
			}
			if (i >= 200) {
				text_baomu_mianjidaxiao.setText("200平方以上");
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

			baomu_otherdetail.setText(otherneed);
			temp_other = otherneed;
			temp = otherneed;
			break;
		case 2:
			String address = bundle.getString("address");
			text_baomu_address.setText(address);
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
				btn_baomu_ok.setVisibility(View.VISIBLE);
				Intent intent = new Intent();
				intent.putExtra("staues", true);
				setResult(0, intent);
				finish();
			} else {
				Log.e("log_tag", "false ");
				btn_baomu_ok.setVisibility(View.VISIBLE);
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
