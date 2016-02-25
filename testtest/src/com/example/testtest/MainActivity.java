package com.example.testtest;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 默认应用程序测试.
 * 
 * @author hailong.qiu 356752238@qq.com
 */
public class MainActivity extends Activity {

	List<ResolveInfo> resolveInfos;
	ListView mAppListView;
	AppInfoAdapter mAppInfoAdapter;
	CustomApplicationHelper mCustomApplicationHelper;
	private PackageManager pm;
	Intent mainIntent;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		pm = getApplicationContext().getPackageManager();
		mCustomApplicationHelper = new CustomApplicationHelper(getApplicationContext());
		//
		// init 浏览器.
		mainIntent = new Intent();
		mainIntent.setAction(Intent.ACTION_VIEW);
		// 图片APP.
		// mainIntent.setType("image/*");
		// 文本类型的APP,比如office.
		mainIntent.setType("text/plain");
		// application/pdf pdf文件.
		// 音乐APP.
		// Uri uri = Uri.parse("file:///");
		// mainIntent.setDataAndType(uri, "audio/*");
		// 视频APP.
		// Uri uri = Uri.parse("file:///");
		// mainIntent.setDataAndType(uri , "video/*");
		// 浏览器APP.
		// mainIntent.setData(Uri.parse("http://"));
		// 获取launcher app.
		// mainIntent.setAction(Intent.ACTION_MAIN);
		// mainIntent.addCategory(Intent.CATEGORY_HOME);
		// mainIntent.addCategory(Intent.CATEGORY_DEFAULT);
		//
		resolveInfos = mCustomApplicationHelper.getSpeAppResolveInfos(mainIntent);
		//
		initAllViews();
	}

	Button test_btn;
	Button clear_btn;
	Button music_btn;
	Button web_btn;

	private void initAllViews() {
		mAppListView = (ListView) findViewById(R.id.app_listview);
		mAppListView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				ResolveInfo resolveInfo = resolveInfos.get(position);
				mCustomApplicationHelper.setDefaultApplication(resolveInfo);
				Toast.makeText(getApplicationContext(), "默认应用设置完成!!", Toast.LENGTH_LONG).show();
			}
		});
		mAppInfoAdapter = new AppInfoAdapter(getApplicationContext(), resolveInfos);
		mAppListView.setAdapter(mAppInfoAdapter);

		test_btn = (Button) findViewById(R.id.test_btn);
		clear_btn = (Button) findViewById(R.id.clear_btn);
		music_btn = (Button) findViewById(R.id.music_btn);
		web_btn = (Button) findViewById(R.id.web_btn);

		test_btn.setOnClickListener(mOnClickListener);
		clear_btn.setOnClickListener(mOnClickListener);
		music_btn.setOnClickListener(mOnClickListener);
		web_btn.setOnClickListener(mOnClickListener);
	}

	OnClickListener mOnClickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			int id = v.getId();
			switch (id) {
			case R.id.clear_btn: // 清除默认应用.
				mCustomApplicationHelper.clearDefaultApp();
				break;
			case R.id.test_btn: // 测试.
				setTestUseApp();
				break;
			case R.id.music_btn: // 音乐应用测试.
				mainIntent.setAction(Intent.ACTION_VIEW);
				Uri uri = Uri.parse("file:///");
				mainIntent.setDataAndType(uri, "audio/*");
				resolveInfos = mCustomApplicationHelper.getSpeAppResolveInfos(mainIntent);
				mAppInfoAdapter.setmResolveInfos(resolveInfos);
				break;
			case R.id.web_btn: // 浏览器.
				mainIntent.setAction(Intent.ACTION_VIEW);
				mainIntent.setData(Uri.parse("http://www.baidu.com"));
				resolveInfos = mCustomApplicationHelper.getSpeAppResolveInfos(mainIntent);
				mAppInfoAdapter.setmResolveInfos(resolveInfos);
				break;
			default:
				break;
			}
		}
	};

	/**
	 * 测试.
	 */
	public void setTestUseApp() {
		startActivity(mainIntent);
	}

	public class AppInfoAdapter extends BaseAdapter {

		private List<ResolveInfo> mResolveInfos;
		private Context mContext;
		private LayoutInflater mInflater;

		public AppInfoAdapter(Context context, List<ResolveInfo> resolveInfos1) {
			this.mResolveInfos = resolveInfos1;
			this.mContext = context;
			this.mInflater = LayoutInflater.from(context);
		}

		public List<ResolveInfo> getmResolveInfos() {
			return mResolveInfos;
		}

		public void setmResolveInfos(List<ResolveInfo> mResolveInfos) {
			this.mResolveInfos = mResolveInfos;
			notifyDataSetChanged();
		}

		@Override
		public int getCount() {
			return mResolveInfos.size();
		}

		@Override
		public Object getItem(int position) {
			return position;
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder holder = null;
			if (convertView == null) {
				holder = new ViewHolder();
				convertView = mInflater.inflate(R.layout.item_listview, null);
				holder.title = (TextView) convertView.findViewById(R.id.textView);
				holder.icon_iv = (ImageView) convertView.findViewById(R.id.icon_iv);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			ResolveInfo resolveInfo = mResolveInfos.get(position);
			if (resolveInfo != null) {
				String name = (String) resolveInfo.loadLabel(pm);
				Drawable icon = resolveInfo.loadIcon(pm);
				holder.title.setText(name);
				holder.icon_iv.setImageDrawable(icon);
			}
			return convertView;
		}

		public class ViewHolder {
			public TextView title;
			public ImageView icon_iv;
		}

	}
}
