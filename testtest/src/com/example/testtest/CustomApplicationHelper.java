package com.example.testtest;

import java.util.List;
import java.util.Set;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;

/**
 * 设置默认APP.
 * 
 * @author hailong.qiu 356752238@qq.com
 *
 */
public class CustomApplicationHelper {

	private Context mContext;
	private Intent mInitIntent;
	private PackageManager pm;
	private List<ResolveInfo> resolveInfos;

	public CustomApplicationHelper(Context context) {
		this.mContext = context;
		if (mContext != null) {
			this.pm = context.getPackageManager();
		}
	}

	public CustomApplicationHelper(Context context, Intent intent) {
		this.mContext = context;
		if (mContext != null) {
			this.pm = context.getPackageManager();
			getSpeAppResolveInfos(intent);
		}
	}

	public List<ResolveInfo> getSpeAppResolveInfos(Intent intent) {
		this.mInitIntent = intent;
		if (intent != null && pm != null) {
			this.resolveInfos = pm.queryIntentActivities(mInitIntent, PackageManager.MATCH_DEFAULT_ONLY);
		}
		return this.resolveInfos;
	}

	/**
	 * 处理Intent.
	 */
	public Intent intentForResolveInfo(ResolveInfo dri) {
		Intent intent = new Intent(mInitIntent);
		intent.addFlags(Intent.FLAG_ACTIVITY_FORWARD_RESULT | Intent.FLAG_ACTIVITY_PREVIOUS_IS_TOP);
		ActivityInfo ai = dri.activityInfo;
		intent.setComponent(new ComponentName(ai.applicationInfo.packageName, ai.name));
		return intent;
	}

	/**
	 * 设置默认APP.
	 */
	public void setDefaultApplication(ResolveInfo dri) {
		// 获取 Intent.
		Intent intent = intentForResolveInfo(dri);
		//
		IntentFilter filter = new IntentFilter();
		// 初始化 action.
		if (intent.getAction() != null) {
			filter.addAction(intent.getAction());
		}
		// 初始化 CATEGORY.
		Set<String> categories = intent.getCategories();
		if (categories != null) {
			for (String cat : categories) {
				filter.addCategory(cat);
			}
		}
		filter.addCategory(Intent.CATEGORY_DEFAULT);
		//
		Uri data = intent.getData();
		int cat = dri.match & IntentFilter.MATCH_CATEGORY_MASK;

		if (cat == IntentFilter.MATCH_CATEGORY_TYPE) {
			String mimeType = intent.resolveType(mContext);
			if (mimeType != null) {
				try {
					filter.addDataType(mimeType);
				} catch (IntentFilter.MalformedMimeTypeException e) {
					filter = null;
				}
			}
		} else if (data != null && data.getScheme() != null) { // 一般是设置了数据，比如浏览器.
			filter.addDataScheme(data.getScheme());
		}

		// 设置默认应用.
		if (filter != null && pm != null) {
			final int N = resolveInfos.size();
			ComponentName[] set = new ComponentName[N];
			int bestMatch = 0;
			for (int i = 0; i < N; i++) {
				ResolveInfo r = resolveInfos.get(i);
				set[i] = new ComponentName(r.activityInfo.packageName, r.activityInfo.name);
				if (r.match > bestMatch)
					bestMatch = r.match;
			}
			pm.addPreferredActivity(filter, bestMatch, set, intent.getComponent());
		}
	}
	
	/**
	 * 清除默认选择. 清除之前的选项.
	 */
	public void clearDefaultApp() {
		if (resolveInfos != null) {
			for (int i = 0; i < resolveInfos.size(); i++) {
				ResolveInfo resolveInfo = resolveInfos.get(i);
				ActivityInfo activityInfo = resolveInfo.activityInfo;
				String packageName = activityInfo.packageName;
				String className = activityInfo.name;
				pm.clearPackagePreferredActivities(packageName);
			}
		}
	}
	
}
