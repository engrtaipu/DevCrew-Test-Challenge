package com.devcrew.testproject;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class StaticService {
	
	public static final String my_url = "http://www.who.int/feeds/entity/csr/don/en/rss.xml";

	public static boolean isInternetOn(Context context) {
		// Context context = getApplicationContext();
		ConnectivityManager connectivity = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (connectivity == null) {
			// boitealerte(,"getSystemService rend null");
		} else {
			NetworkInfo[] info = connectivity.getAllNetworkInfo();
			if (info != null) {
				for (int i = 0; i < info.length; i++) {
					if (info[i].getState() == NetworkInfo.State.CONNECTED) {
						return true;
					}
				}
			}
		}
		return false;
	}

}
