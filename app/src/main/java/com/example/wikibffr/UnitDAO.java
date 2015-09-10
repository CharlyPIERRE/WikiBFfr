package com.example.wikibffr;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class UnitDAO {

	private static final int VERSION_BDD = 1;
	private static final String NOM_BDD = "units.sqlite";

	private static final String TABLE_UNITS = "UnitList";
	private static final String COL_ID = "Id";
//	private static final int NUM_COL_ID = 0;
	private static final String COL_NO = "No";
	private static final int NUM_COL_NO = 1;
	private static final String COL_NAME = "Nom";
	private static final int NUM_COL_NAME = 2;
	private static final String COL_RARE = "Rarete";
	private static final int NUM_COL_RARE = 3;
	private static final String COL_ELEMENT = "Element";
	private static final int NUM_COL_ELEMENT = 4;
	private static final String COL_NVMAX = "NvMax";
	private static final int NUM_COL_NVMAX = 5;
	private static final String COL_COUT = "Cout";
	private static final int NUM_COL_COUT = 6;
	private static final String COL_SINU = "SiteNumber";
	private static final int NUM_COL_SINU = 7;

	private SQLiteDatabase bdd;

	private MaBaseSQLite maBaseSQLite;

	public UnitDAO(Context context){
		//On créer la BDD et sa table
		maBaseSQLite = new MaBaseSQLite(context, NOM_BDD, null, VERSION_BDD);
	}

	public void open(){
		//on ouvre la BDD en écriture
		bdd = maBaseSQLite.getWritableDatabase();
	}

	public void close(){
		//on ferme l'accès à la BDD
		bdd.close();
	}

	public SQLiteDatabase getBDD(){
		return bdd;
	}

	public long insertUnit(UnitDef unit){
		//Création d'un ContentValues (fonctionne comme une HashMap)
		ContentValues values = new ContentValues();
		//on lui ajoute une valeur associé à une clé (qui est le nom de la colonne dans laquelle on veut mettre la valeur)
		values.put(COL_NO, unit.getNo());
		values.put(COL_NAME, unit.getNom());
		values.put(COL_RARE, unit.getRarete());
		values.put(COL_ELEMENT, unit.getElement());
		values.put(COL_NVMAX, unit.getNvMax());
		values.put(COL_COUT, unit.getCout());
		values.put(COL_SINU, unit.getSiteNumber());
		//on insère l'objet dans la BDD via le ContentValues
		return bdd.insert(TABLE_UNITS, null, values);
	}

	public int updateUnit(int id, UnitDef unit){
		//Meme principe que pour l'insertion....
		ContentValues values = new ContentValues();
		values.put(COL_NO, unit.getNo());
		values.put(COL_NAME, unit.getNom());
		values.put(COL_RARE, unit.getRarete());
		values.put(COL_ELEMENT, unit.getElement());
		values.put(COL_NVMAX, unit.getNvMax());
		values.put(COL_COUT, unit.getCout());
		values.put(COL_SINU, unit.getSiteNumber());
		//...Mais update avec numero Id 
		return bdd.update(TABLE_UNITS, values, COL_ID + " = " +id, null);
	}



	public UnitList getUnitsWith(String colonne_name, String valeur){
		UnitList units =  new UnitList() ; 
		Cursor c = bdd.query(TABLE_UNITS, new String[] {
				COL_ID, 
				COL_NO, 
				COL_NAME, 
				COL_RARE, 
				COL_ELEMENT, 
				COL_NVMAX, 
				COL_COUT, 
				COL_SINU 
				},
				colonne_name + " = '" + valeur + "'", null, null, null, null);
		
		if (c.getCount() == 0) return null;
		
		while (c.moveToNext()) {
			units.addUnit(new UnitDef(
					c.getString(NUM_COL_NO), 
					c.getString(NUM_COL_NAME),
					c.getString(NUM_COL_RARE), 
					c.getString(NUM_COL_ELEMENT), 
					c.getString(NUM_COL_NVMAX),
					c.getString(NUM_COL_COUT),
					c.getString(NUM_COL_SINU)
					)) ; 
		}
		c.close();

		return units;
	}


	public UnitList getAllUnits(){
		UnitList units =  new UnitList() ; 
		Cursor c = bdd.query(TABLE_UNITS, new String[] {
				COL_ID, 
				COL_NO, 
				COL_NAME, 
				COL_RARE, 
				COL_ELEMENT, 
				COL_NVMAX, 
				COL_COUT, 
				COL_SINU 

				},
				"" , null, null, null, null);
		
		if (c.getCount() == 0) return null;
		
		while (c.moveToNext()) {
			units.addUnit(new UnitDef(
					c.getString(NUM_COL_NO), 
					c.getString(NUM_COL_NAME),
					c.getString(NUM_COL_RARE), 
					c.getString(NUM_COL_ELEMENT), 
					c.getString(NUM_COL_NVMAX),
					c.getString(NUM_COL_COUT),
					c.getString(NUM_COL_SINU)
					)) ; 
		}
		c.close();

		return units;
	}
	
	
	public UnitList getUnitsWithNameStartBy(String value){
		UnitList units =  new UnitList() ; 
		Cursor c = bdd.query(TABLE_UNITS, new String[] {
				COL_ID, 
				COL_NO, 
				COL_NAME, 
				COL_RARE, 
				COL_ELEMENT, 
				COL_NVMAX, 
				COL_COUT, 
				COL_SINU 

				},
				COL_NAME + " LIKE '" + value + "%'" , null, null, null, null);
		
		if (c.getCount() == 0) return units;
		
		while (c.moveToNext()) {
			units.addUnit(new UnitDef(
					c.getString(NUM_COL_NO), 
					c.getString(NUM_COL_NAME),
					c.getString(NUM_COL_RARE), 
					c.getString(NUM_COL_ELEMENT), 
					c.getString(NUM_COL_NVMAX),
					c.getString(NUM_COL_COUT),
					c.getString(NUM_COL_SINU)
					)) ; 
		}
		c.close();

		return units;
	}


}
