package com.izzaweb.bengkel.Models.Bengkel;

import java.util.List;

public class ResponseBengkel {
	private List<BengkelItem> bengkel;

	public void setBengkel(List<BengkelItem> bengkel){
		this.bengkel = bengkel;
	}

	public List<BengkelItem> getBengkel(){
		return bengkel;
	}

	@Override
 	public String toString(){
		return 
			"ResponseBengkel{" +
			"bengkel = '" + bengkel + '\'' + 
			"}";
		}
}