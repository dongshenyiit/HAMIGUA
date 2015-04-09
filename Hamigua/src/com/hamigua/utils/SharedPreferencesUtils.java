package com.hamigua.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.util.Log;

public class SharedPreferencesUtils {

	public static String SP_NAME = "config";
	private static SharedPreferences sp;

	public static void saveBoolean(Context context, String key, boolean value) {
		if (sp == null)
			sp = context.getSharedPreferences(SP_NAME, 0);
		sp.edit().putBoolean(key, value).commit();
	}

	public static boolean getBoolean(Context context, String key,
			boolean defValue) {
		if (sp == null) {
			sp = context.getSharedPreferences(SP_NAME, 0);

		}
		return sp.getBoolean(key, defValue);
	}

	public static int getInt(Context context, String key, int defValue) {
		if (sp == null) {
			sp = context.getSharedPreferences(SP_NAME, 0);

		}
		return sp.getInt(key, defValue);
	}

	public static void saveInt(Context context, String key, int value) {
		if (sp == null)
			sp = context.getSharedPreferences(SP_NAME, 0);
		sp.edit().putInt(key, value).commit();
	}

	public static void saveLong(Context context, String key, long value) {
		if (sp == null)
			sp = context.getSharedPreferences(SP_NAME, 0);
		sp.edit().putLong(key, value).commit();
	}

	public static long getLong(Context context, String key) {
		if (sp == null) {
			sp = context.getSharedPreferences(SP_NAME, 0);

		}
		return sp.getLong(key, 0);
	}

	public static void saveString(Context context, String key, String value) {
		if (sp == null){
			sp = context.getSharedPreferences(SP_NAME, 0);
		}
		sp.edit().putString(key, value).commit();
	}

	public static String getString(Context context, String key, String defValue) {
		if (sp == null) {
			sp = context.getSharedPreferences(SP_NAME, 0);
		}
		return sp.getString(key, defValue);
	}

	// ------------------------------------------------------

	public static void clear(Context context) {
		if (sp == null) {
			sp = context.getSharedPreferences(SP_NAME, 0);
		}
		sp.edit().clear().commit();
		
	}

	/**
	 * desc:淇濆瓨�?�硅�?
	 * 
	 * @param context
	 * @param key
	 * @param obj
	 *            瑕佷繚�?�樼殑�?�硅薄锛屽彧鑳戒繚�?�樺疄鐜颁簡serializable鐨勫璞�? modified:
	 */
	public static void saveObject(Context context, String key, Object obj) {
		try {
			// 淇濆瓨�?�硅�?
			SharedPreferences.Editor sharedata = context.getSharedPreferences(
					SP_NAME, 0).edit();
			// 鍏堝皢搴忓垪鍖栫粨鏋滃啓鍒癰yte缂撳瓨涓紝鍏跺疄灏卞垎閰嶄竴涓唴瀛樼┖闂�?
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			ObjectOutputStream os = new ObjectOutputStream(bos);
			// 灏嗗璞″簭鍒楀寲鍐欏叆byte缂撳�?
			os.writeObject(obj);
			// 灏嗗簭鍒�?寲鐨勬暟鎹浆涓�?16杩涘埗淇濆瓨
			String bytesToHexString = bytesToHexString(bos.toByteArray());
			// 淇濆瓨璇�?16杩涘埗鏁扮粍
			sharedata.putString(key, bytesToHexString);
			sharedata.commit();
		} catch (IOException e) {
			e.printStackTrace();
			Log.e("", "淇濆瓨obj澶辫�?");
		}
	}

	/**
	 * desc:灏嗘暟缁勮浆涓�16杩涘�?
	 * 
	 * @param bArray
	 * @return modified:
	 */
	public static String bytesToHexString(byte[] bArray) {
		if (bArray == null) {
			return null;
		}
		if (bArray.length == 0) {
			return "";
		}
		StringBuffer sb = new StringBuffer(bArray.length);
		String sTemp;
		for (int i = 0; i < bArray.length; i++) {
			sTemp = Integer.toHexString(0xFF & bArray[i]);
			if (sTemp.length() < 2)
				sb.append(0);
			sb.append(sTemp.toUpperCase());
		}
		return sb.toString();
	}

	/**
	 * desc:鑾峰彇淇濆瓨鐨凮bject瀵硅�?
	 * 
	 * @param context
	 * @param key
	 * @return modified:
	 */
	public Object readObject(Context context, String key) {
		try {
			SharedPreferences sharedata = context.getSharedPreferences(SP_NAME,
					0);
			if (sharedata.contains(key)) {
				String string = sharedata.getString(key, "");
				if (TextUtils.isEmpty(string)) {
					return null;
				} else {
					// 灏�16杩涘埗鐨勬暟鎹浆涓烘暟缁勶紝鍑嗗鍙嶅簭鍒�?�?
					// byte[] stringToBytes = StringToBytes(string);
					// 涓存椂瑙ｅ喅閿欒�?
					byte[] stringToBytes = null;
					ByteArrayInputStream bis = new ByteArrayInputStream(
							stringToBytes);
					ObjectInputStream is = new ObjectInputStream(bis);
					// 杩斿洖鍙嶅簭鍒楀寲寰�?埌鐨勫璞�
					Object readObject = is.readObject();
					return readObject;
				}
			}
		} catch (StreamCorruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// 鎵�鏈夊紓甯歌繑鍥�?�ull
		return null;

	}

	/**
	 * desc:灏�16杩涘埗鐨勬暟鎹浆涓烘暟缁�
	 * <p>
	 * 鍒涘缓浜猴細鑱傛棴闃�? , 2014-5-25 涓婂�?11:08:33
	 * </p>
	 * 
	 * @param data
	 * @return modified:
	 */
	// public static byte[] StringToBytes(String data){
	// String hexString=data.toUpperCase().trim();
	// if (hexString.length()%2!=0) {
	// return null;
	// }
	// byte[] retData=new byte[hexString.length()/2];
	// for(int i=0;i<hexString.length();i++) {
	// ="" int="" int_ch;="" 涓や�?16杩涘埗鏁拌浆鍖栧悗鐨�?10杩涘埗鏁�?=""
	// char="" hex_char1="hexString.charAt(i);" 涓や�?16杩涘埗鏁颁腑鐨勭涓�浣�?(楂樹�?*16)=""
	// int_ch1;=""
	// if(hex_char1="">= '0' && hex_char1 <='9')
	// int_ch1 = (hex_char1-48)*16; //// 0 鐨凙scll - 48
	// else if(hex_char1 >= 'A' && hex_char1 <='F')
	// int_ch1 = (hex_char1-55)*16; //// A 鐨凙scll - 65
	// else
	// return null;
	// i++;
	// char hex_char2 = hexString.charAt(i); ///涓や�?16杩涘埗鏁颁腑鐨勭浜屼綅(浣庝�?)
	// int int_ch2;
	// if(hex_char2 >= '0' && hex_char2 <='9')
	// int_ch2 = (hex_char2-48); //// 0 鐨凙scll - 48
	// else if(hex_char2 >= 'A' && hex_char2 <='F')
	// int_ch2 = hex_char2-55; //// A 鐨凙scll - 65
	// else
	// return null;
	// int_ch = int_ch1+int_ch2;
	// retData[i/2]=(byte) int_ch;//灏嗚浆鍖栧悗鐨勬暟鏀惧叆Byte閲�
	// }
	// return retData;
	// }</hexstring.length();i++)>
}
