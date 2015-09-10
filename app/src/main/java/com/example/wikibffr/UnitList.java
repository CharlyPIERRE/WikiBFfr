package com.example.wikibffr;

import java.io.Serializable;
import java.util.Vector;

@SuppressWarnings("serial")
public class UnitList implements Serializable { 
	private Vector<UnitDef> v_units = new Vector<UnitDef>() ; 
	private Vector<String> unitNumber = new Vector<String>() ; 
	
	public UnitList(){
		super();
	}
	
	public void addUnit(UnitDef unit){
		this.v_units.addElement(unit);
		this.unitNumber.addElement(unit.getNo());
	}
	
	public Vector <UnitDef> getUnitList() {
		return this.v_units ; 
	}

	public UnitDef getUnitNext(UnitDef unit){
		if (v_units.indexOf(unit)== -1) unit = unitFromNumber(unit.getNo()) ; 
		if (v_units.lastElement() == unit) return unit ; 
		return (v_units.get(v_units.indexOf(unit)+1)) ; 
	}

	public UnitDef getUnitPrec(UnitDef unit){
		if (v_units.indexOf(unit)== -1) unit = unitFromNumber(unit.getNo()) ; 
		if (v_units.firstElement() == unit) return unit ; 
		return (v_units.get(v_units.indexOf(unit)-1)) ; 
	}
	
    private UnitDef unitFromNumber(String number){
    	return this.v_units.elementAt(this.unitNumber.indexOf(number));
    }
	

}
