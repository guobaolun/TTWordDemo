package com.storm.ttword.dialog.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.storm.ttword.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;


/**
 * 将raw中得数据库文件写入到data数据库中
 * 
 * @author sy
 * 
 */
class AddressHelper {
	static final String DB_NAME = "address.db";
	static String DB_PATH ;//= Application.getApplicationContext().getFilesDir().getAbsolutePath(); // 存放路径
	private Context mContext;
	private SQLiteDatabase database;

	AddressHelper(Context context) {
		this.mContext = context;
		DB_PATH = context.getApplicationContext().getFilesDir().getAbsolutePath(); // 存放路径
	}


	void openDateBase() {
		this.database = this.openDateBase(DB_PATH + "/" + DB_NAME);
	}

	/**
	 * 打开数据库
	 * @param dbFile dbFile
	 * @return SQLiteDatabase
	 */
	private SQLiteDatabase openDateBase(String dbFile) {
		File file = new File(dbFile);
		if (!file.exists()) {
			// // 打开raw中得数据库文件，获得stream流
			InputStream stream = this.mContext.getResources().openRawResource(R.raw.address);
			try {

				// 将获取到的stream 流写入道data中
				FileOutputStream outputStream = new FileOutputStream(dbFile);
				int BUFFER_SIZE = 400000;
				byte[] buffer = new byte[BUFFER_SIZE];
				int count;
				while ((count = stream.read(buffer)) > 0) {
					outputStream.write(buffer, 0, count);
				}
				outputStream.close();
				stream.close();
				return SQLiteDatabase.openOrCreateDatabase(dbFile,null);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return database;
	}

	void closeDatabase() {
		if (database != null && database.isOpen()) {
			this.database.close();
		}
	}
}
