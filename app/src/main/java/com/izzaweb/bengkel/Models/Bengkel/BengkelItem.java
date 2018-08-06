package com.izzaweb.bengkel.Models.Bengkel;

public class BengkelItem{
	private String alamatBengkel;
	private String hariBuka;
	private String namaBengkel;
	private String idBengkel;
	private String latitude;
	private String noTelp;
	private String longitude;

	public void setAlamatBengkel(String alamatBengkel){
		this.alamatBengkel = alamatBengkel;
	}

	public String getAlamatBengkel(){
		return alamatBengkel;
	}

	public void setHariBuka(String hariBuka){
		this.hariBuka = hariBuka;
	}

	public String getHariBuka(){
		return hariBuka;
	}

	public void setNamaBengkel(String namaBengkel){
		this.namaBengkel = namaBengkel;
	}

	public String getNamaBengkel(){
		return namaBengkel;
	}

	public void setIdBengkel(String idBengkel){
		this.idBengkel = idBengkel;
	}

	public String getIdBengkel(){
		return idBengkel;
	}

	public void setLatitude(String latitude){
		this.latitude = latitude;
	}

	public String getLatitude(){
		return latitude;
	}

	public void setNoTelp(String noTelp){
		this.noTelp = noTelp;
	}

	public String getNoTelp(){
		return noTelp;
	}

	public void setLongitude(String longitude){
		this.longitude = longitude;
	}

	public String getLongitude(){
		return longitude;
	}

	@Override
 	public String toString(){
		return 
			"BengkelItem{" + 
			"alamat_bengkel = '" + alamatBengkel + '\'' + 
			",hari_buka = '" + hariBuka + '\'' + 
			",nama_bengkel = '" + namaBengkel + '\'' + 
			",id_bengkel = '" + idBengkel + '\'' + 
			",latitude = '" + latitude + '\'' + 
			",no_telp = '" + noTelp + '\'' + 
			",longitude = '" + longitude + '\'' + 
			"}";
		}
}
