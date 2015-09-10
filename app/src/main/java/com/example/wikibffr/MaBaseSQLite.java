package com.example.wikibffr;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase.CursorFactory;

public class MaBaseSQLite extends SQLiteOpenHelper {

	private static final String TABLE_LIVRES = "UnitList";
	private static final String COL_ID = "Id";
	private static final String COL_NO = "No";
	private static final String COL_NOM = "Nom";
	private static final String COL_RARETE = "Rarete";
	private static final String COL_ELEMENT = "Element";
	private static final String COL_NVMAX = "NvMax";
	private static final String COL_COUT = "Cout";
	private static final String COL_SINU = "SiteNumber";

	private static final String CREATE_BDD = "CREATE TABLE " + TABLE_LIVRES + " ("
			+ COL_ID 		+ " INTEGER PRIMARY KEY AUTOINCREMENT, " 
			+ COL_NO 		+ " TEXT NOT NULL, "
			+ COL_NOM 		+ " TEXT NOT NULL, " 
			+ COL_RARETE 	+ " TEXT NOT NULL, "
			+ COL_ELEMENT 	+ " TEXT NOT NULL, " 
			+ COL_NVMAX 	+ " TEXT NOT NULL, "
			+ COL_COUT 		+ " TEXT NOT NULL, " 
			+ COL_SINU 		+ " TEXT NOT NULL" 
			+");";

	public MaBaseSQLite(Context context, String name, CursorFactory factory, int version) {
		super(context, name, factory, version);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		//on cr�� la table � partir de la requ�te �crite dans la variable CREATE_BDD
		db.execSQL(CREATE_BDD);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		//On peut fait ce qu'on veut ici moi j'ai d�cid� de supprimer la table et de la recr�er
		//comme �a lorsque je change la version les id repartent de 0
		db.execSQL("DROP TABLE " + TABLE_LIVRES + ";");
		onCreate(db);
	}

}