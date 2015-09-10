package com.example.wikibffr;

import java.io.IOException;
import java.io.InputStream;

import android.app.Activity;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.MotionEvent;
import android.widget.ImageView;
import android.widget.TextView;

public class UnitDisplayInfos extends Activity   {
	private UnitList list = null ; 
	private UnitDef unit = null ; 
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		//recup données bundle
		Bundle extras = getIntent().getExtras();
		this.list = (UnitList)extras.getSerializable("list");
		this.unit = (UnitDef)extras.getSerializable("unit");
		
		//applique la vue
		setContentView(R.layout.unit_infos);

		//affiche les données unité
		displayUnitDatas(this.unit);

	}


	private void displayUnitDatas(UnitDef unit){
		TextView unitDescript = (TextView)findViewById(R.id.unitDescript);
		TextView unitName = (TextView)findViewById(R.id.unitName);
		ImageView unitIV = (ImageView)findViewById(R.id.unitImage);
		

		InputStream ims;
		try {
			ims = getAssets().open("images/units/full/Unit_ills_full_"+unit.getSiteNumber()+".png");
			unitIV.setImageBitmap(BitmapFactory.decodeStream(ims));
			unitIV.setMinimumHeight(300);
			unitIV.setMinimumWidth(300);

		} catch (IOException e) {
			try {
				ims = getAssets().open("images/units/full/NoPicture.png");
				unitIV.setImageBitmap(BitmapFactory.decodeStream(ims));

			} catch (IOException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
		}

		unitName.setText(unit.getNom());
		unitDescript.setText(getResources().getString(getResources().getIdentifier("unit"+unit.getSiteNumber()+"_description", "string", getPackageName())));
		
		
		
	}
	
	
	private float x1,x2;
	static final int MIN_DISTANCE = 150;
	public boolean onTouchEvent(MotionEvent event) {

		switch(event.getAction())
		{
		case MotionEvent.ACTION_DOWN:
			x1 = event.getX();                         
			break;         
		case MotionEvent.ACTION_UP:
			x2 = event.getX();
			float deltaX = x2 - x1;

			if (Math.abs(deltaX) > MIN_DISTANCE)
			{
				// unité precedente
				if (x2 > x1) this.unit = this.list.getUnitPrec(this.unit) ; 
				// unité suivante             
				else this.unit = this.list.getUnitNext(this.unit) ; 

				//affiche infos unité
				displayUnitDatas(this.unit);
			}
			else 	// consider as something else - a screen tap for example
			break;   
		}           
		return super.onTouchEvent(event);    
	}
	
	


}
