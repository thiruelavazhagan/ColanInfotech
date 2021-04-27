package com.example.colaninfotech;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class SQLite extends SQLiteOpenHelper {

	Context context;

	private static final int DATABASE_VERSION = 1;
	private static final String DATABASE_NAME = "usercomments"; // Databse Name

	private static final String USER_REG = "comments"; // TableName

	/*--------------Table Columns-----------*/
	private static final String KEY_ID = "id";
	private static final String KEY_UID = "userid";
	private static final String KEY_COMMENT = "comment";


	/*--------------Table Columns Array-----------*/
	public static final String REG_COLUMN[] = {KEY_ID,KEY_UID,KEY_COMMENT};

	public SQLite(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		this.context = context;
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		String create_table = "CREATE TABLE " + USER_REG + "(" + KEY_ID + "  INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_UID + " TEXT," +KEY_COMMENT + " TEXT)";

		db.execSQL(create_table);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		db.execSQL("DROP TABLE IF EXISTS " + USER_REG);
		this.onCreate(db);
	}

	/*-----------------User Information register----------------*/
	public long user_register(String uid, String comment) {
		long i=0;
		try {
			SQLiteDatabase db = this.getWritableDatabase();
			ContentValues cvalues = new ContentValues();

			cvalues.put(KEY_UID, uid);
			cvalues.put(KEY_COMMENT, comment);


			i=db.insert(USER_REG, null, cvalues);
			Log.e("sdasdasdasd",""+i);
			db.close();

			Toast.makeText(context, "User Registered Successfully !", Toast.LENGTH_SHORT).show();


		} catch (Exception e) {
			Toast.makeText(context, "Check" + e, Toast.LENGTH_SHORT).show();
		}
		return i;
	}



	public List<Comment> getcomment(String uid) {
		List<Comment> comment=null;
		// array of columns to fetch
		String[] columns = {
				KEY_ID,
				KEY_UID,
				KEY_COMMENT
		};
		// sorting orders
		String sortOrder =
				KEY_COMMENT + " ASC";

		SQLiteDatabase db = this.getReadableDatabase();

		String selection = KEY_UID + " = ?";

		// selection argument
		String[] selectionArgs = {uid};

		// query the user table
		/**
		 * Here query function is used to fetch records from user table this function works like we use sql query.
		 * SQL query equivalent to this query function is
		 * SELECT user_id,user_name,user_email,user_password FROM user ORDER BY user_name;
		 */
		Cursor cursor = db.query(USER_REG, //Table to query
				columns,    //columns to return
				selection,        //columns for the WHERE clause
				selectionArgs,        //The values for the WHERE clause
				null,       //group the rows
				null,       //filter by row groups
				sortOrder); //The sort order

		comment = new ArrayList<>();
		// Traversing through all rows and adding to list
		if (cursor.moveToFirst()) {
			do {

				comment.add(new Comment(cursor.getString(cursor.getColumnIndex(KEY_ID)),cursor.getString(cursor.getColumnIndex(KEY_UID)),cursor.getString(cursor.getColumnIndex(KEY_COMMENT))));


			} while (cursor.moveToNext());
		}
		cursor.close();
		db.close();

		// return user list
		return comment;
	}


	

}
