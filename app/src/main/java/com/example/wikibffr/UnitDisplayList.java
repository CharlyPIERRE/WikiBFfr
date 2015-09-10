package com.example.wikibffr;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class UnitDisplayList extends Activity implements OnItemSelectedListener{
	UnitDAO unitBdd = new UnitDAO(this);
	UnitList UL_units = null ;
	Button b_feu = null ; 
	Button b_Eau = null ; 
	Button b_Terre = null ; 
	Button b_Foudre = null ; 
	EditText UnitSearch = null ; 
	LinearLayout ll = null ;
	Spinner spinner = null ; 

	int height = 0 ; 
	int width = 0 ; 

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
        
        DisplayMetrics displaymetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        this.height = displaymetrics.heightPixels;
        this.width = displaymetrics.widthPixels;
        System.out.println("h" + height + "\nW" + width) ; 

		// Selection vue layout
		setContentView(R.layout.units);

		// Definition des objets du layout
		this.initLayout();

		// Definition des listeners 
		this.initListeners();

		// ouverture base de donnees unites
		this.unitBdd.open();

		// Si la table a besoin d'etre chargee (1ere utilisation, MAJ)
		if (this.needToLoadTable()) 
			// Chargement de la table
			this.chargeTable() ; 


		// Recuperation des infos unites dans la bdd dans une liste
//		this.UL_units = this.unitBdd.getAllUnits();

		// Affiche les informations de la liste d'unite
//		this.AfficheUnites(UL_units) ;

		this.unitBdd.close() ; 

	}

	private void initLayout(){
		// champs de recherche par nom
		this.UnitSearch = (EditText)findViewById(R.id.editText1);

		// boutons de filtre par element
		this.b_feu = (Button)findViewById(R.id.button1);
		this.b_Eau = (Button)findViewById(R.id.button2);
		this.b_Terre = (Button)findViewById(R.id.button3);
		this.b_Foudre = (Button)findViewById(R.id.button4);
		this.ll = (LinearLayout)findViewById(R.id.listeunites);

		// spinner de filtre par element
		this.spinner = (Spinner) findViewById(R.id.spinner1);
		spinner.setOnItemSelectedListener(this) ; 
		// Create an ArrayAdapter using the string array and a default spinner layout
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
				R.array.Elements, android.R.layout.simple_spinner_item);
		// Specify the layout to use when the list of choices appears
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// Apply the adapter to the spinner
		spinner.setAdapter(adapter);


	}

	private void initListeners(){
		// listener du champ de recherche par nom
		this.UnitSearch.addTextChangedListener(new TextWatcher(){
			public void afterTextChanged(Editable s) {
				unitBdd.open();
				UL_units = unitBdd.getUnitsWithNameStartBy(UnitSearch.getText().toString()) ; 
				unitBdd.close();
				AfficheUnites(UL_units) ; 

			}
			public void beforeTextChanged(CharSequence s, int start, int count, int after){}
			public void onTextChanged(CharSequence s, int start, int before, int count){}
		}); 

		// listener de bouton (type de recherche , valeur ) 
		this.b_feu.setOnClickListener(onClickButton("Element" , "Feu"));
		this.b_Eau.setOnClickListener(onClickButton("Element" , "Eau"));
		this.b_Terre.setOnClickListener(onClickButton("Element" , "Terre"));
		this.b_Foudre.setOnClickListener(onClickButton("Element" , "Foudre"));



	}

	private boolean needToLoadTable(){
		// TODO  : methode temporaire... devra etre remplacee par un fichier indicateur par exemple (supprimé apres le chargement de la table)
		// Recuperation des infos unites dans la bdd dans une liste
		this.UL_units = this.unitBdd.getAllUnits();

		// Si dans la liste complete l'unite numero 1 existe, alors la table n'a pas besoin d'etre chargee 
		if (this.unitBdd.getUnitsWith("No", "1") != null) return false ;

		// Sinon elle a besoin de l'etre
		return true ; 
	}

	private void chargeTable() {
		// declaration unite temporaire utile au chargement de la table
		UnitDef o_unit = new UnitDef() ; 

		// Lecture du fichier csv ligne par ligne, separateur ";" , et encodé en UTF-8 ! ! !
		InputStream json;
		try {
			json = getAssets().open("UnitList.csv");
			BufferedReader in=
					new BufferedReader(new InputStreamReader(json, "UTF-8"));
			String str;

			while ((str=in.readLine()) != null) {
				String[] RowData = str.split(";");
				o_unit.setNo(RowData[0]) ; 
				o_unit.setNom(RowData[1]) ; 
				o_unit.setRarete(RowData[2]) ; 
				o_unit.setElement(RowData[3]) ; 
				o_unit.setNvMax(RowData[4]) ; 
				o_unit.setCout(RowData[5]) ; 
				o_unit.setSiteNumber(RowData[6]) ; 

				unitBdd.insertUnit(o_unit);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}



	@SuppressLint("NewApi") private void AfficheUnites(final UnitList UL_units){
		// Si la liste contient de elements, 
		if (!UL_units.getUnitList().isEmpty()) 		
			// on efface la liste precedente, sinon on garde l'ancienne liste affichee
			ll.removeAllViews() ; 

		// Pour chaque unite dans la liste
		for (UnitDef unit : UL_units.getUnitList() ) {
			// On cree un linear layout,horizontal , avec une bordure
			LinearLayout UnitInfos = new LinearLayout(this);
			UnitInfos.setOrientation(LinearLayout.HORIZONTAL);
			UnitInfos.setBackgroundResource(R.drawable.border);

			// On definit les informations a afficher dans de layout
			TextView tv_No = new TextView(this);

			final ImageView iv = new ImageView(getBaseContext()) ; 	
			iv.setOnClickListener(onClickUnit(unit)) ; 



			// On charge l'image de l'unite
			this.recupImageUnite(iv , unit.getSiteNumber()) ; 
			iv.setMinimumHeight(dpToPx(30));
			iv.setMaxHeight(dpToPx(30));
			iv.setMinimumWidth(dpToPx(30));
			iv.setMaxHeight(dpToPx(30));


			tv_No.setText(unit.getNo());
			tv_No.setBackgroundResource(R.drawable.border);
			tv_No.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL) ; 
			tv_No.setMinimumHeight(iv.getMinimumHeight());
			tv_No.setMinimumWidth(findViewById(R.id.textView1).getWidth());


			TextView tv_Nom = new TextView(this);
			tv_Nom.setText(unit.getNom());
			tv_Nom.setTextSize((float) 11.0) ; 
			tv_Nom.setBackgroundResource(R.drawable.border);
			tv_Nom.setGravity(Gravity.CENTER_VERTICAL) ; 
			tv_Nom.setMinimumHeight(iv.getMinimumHeight());
			tv_Nom.setMinimumWidth(findViewById(R.id.textView2).getWidth() - iv.getMinimumHeight());



			TextView tv_Rarete = new TextView(this);
			tv_Rarete.setText(unit.getRarete());
			tv_Rarete.setBackgroundResource(R.drawable.border);
			tv_Rarete.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL) ; 
			tv_Rarete.setMinimumHeight(iv.getMinimumHeight());
			tv_Rarete.setMinimumWidth(findViewById(R.id.textView3).getWidth());


			TextView tv_Element = new TextView(this);
			tv_Element.setText(unit.getElement() );
			tv_Element.setBackgroundResource(R.drawable.border);
			tv_Element.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL) ; 
			tv_Element.setMinimumHeight(iv.getMinimumHeight());
			tv_Element.setMinimumWidth(findViewById(R.id.textView4).getWidth());


			UnitInfos.addView(tv_No);
			UnitInfos.addView(iv);

			UnitInfos.addView(tv_Nom);
			UnitInfos.addView(tv_Rarete);
			UnitInfos.addView(tv_Element);

			ll.addView(UnitInfos);
			System.out.println(findViewById(R.id.textView2).getWidth() - iv.getMinimumHeight());
			System.out.println(tv_Nom.getWidth() + "!");
			while (findViewById(R.id.textView2).getWidth() - iv.getMinimumHeight() < tv_Nom.getWidth()){
				tv_Nom.setTextSize(tv_Nom.getTextSize() - 1) ; 
			}


		}

	}
	private int dpToPx(int dp)
	{
	    float density = getApplicationContext().getResources().getDisplayMetrics().density;
	    return Math.round((float)dp * density);
	}

	private void recupImageUnite(final ImageView iv , String SiteNumber){
		try {
			// get input stream
			InputStream ims = getAssets().open("images/units/thum/Unit_ills_thum_"+SiteNumber+".png");
			iv.setImageDrawable(Drawable.createFromStream(ims, null));

		}
		catch(IOException ex) {
			try {
				// get input stream
				InputStream ims = getAssets().open("images/units/thum/NoPicture.png");
				iv.setImageDrawable(Drawable.createFromStream(ims, null));

			}
			catch(IOException ex2) {
				ex2.printStackTrace();
			}			
		}

	}



	public OnClickListener onClickButton(final String Colonne, final String value)
	{
		return  new OnClickListener() {
			@Override
			public void onClick(View view) {
				unitBdd.open();
				UL_units = unitBdd.getUnitsWith(Colonne, value) ; 
				unitBdd.close();
				AfficheUnites(UL_units) ; 
			}
		};

	}

	public OnClickListener onClickUnit(final UnitDef unit)
	{
		return  new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getApplicationContext(), UnitDisplayInfos.class);
				intent.putExtra("list", UL_units);
				intent.putExtra("unit", unit);

				startActivity(intent);
			}

		};

	}


	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub

		Toast.makeText(getBaseContext(), parent.getItemAtPosition(position).toString() , Toast.LENGTH_SHORT).show();
		unitBdd.open();
		UL_units = unitBdd.getUnitsWith("Element", parent.getItemAtPosition(position).toString()) ; 
		unitBdd.close();
		AfficheUnites(UL_units) ; 


	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub

	}

}



