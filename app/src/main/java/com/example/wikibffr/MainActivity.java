package com.example.wikibffr;

import android.view.View;
import android.widget.ImageButton;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class MainActivity extends Activity {
//	public static String Package_name = "com.google.wikibfrpg"  ; 

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        
        ImageButton jeu = (ImageButton)findViewById(R.id.jeu);
        jeu.setOnClickListener(new View.OnClickListener()
        {
        	public void onClick(View v)
        	{
        		Intent intent = new Intent(getApplicationContext(), UnitDisplayList.class);
        		startActivity(intent);
        	}
        });
        ImageButton units = (ImageButton)findViewById(R.id.units);
        units.setOnClickListener(new View.OnClickListener()
        {
        	public void onClick(View v)
        	{
        		Intent intent = new Intent(getApplicationContext(), UnitDisplayList.class);
        		startActivity(intent);
        	}
        });
        ImageButton guides = (ImageButton)findViewById(R.id.guides);
        guides.setOnClickListener(new View.OnClickListener()
        {
        	public void onClick(View v)
        	{
        		Intent intent = new Intent(getApplicationContext(), UnitDisplayList.class);
        		startActivity(intent);
        	}
        });
        ImageButton faq = (ImageButton)findViewById(R.id.faq);
        faq.setOnClickListener(new View.OnClickListener()
        {
        	public void onClick(View v)
        	{
        	//	UnitDef unit = new UnitDef("menu1","menu2","menu3","menu4","menu4","menu6") ; 
        		Intent intent = new Intent(getApplicationContext(), UnitDisplayInfos.class);
        	//	intent.putExtra("unit", unit);
        		startActivity(intent);
        	}
        });
        
    }
}
