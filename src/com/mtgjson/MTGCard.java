package com.mtgjson;

import java.util.ArrayList;
import java.util.Map;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.google.common.base.Joiner;
@JsonIgnoreProperties(ignoreUnknown=true)
public class MTGCard
{
	private String				layout;
	private String				name;
	private ArrayList<String>	names;
	private String				manaCost;	
	private Integer				cmc;
	private ArrayList<String>	colors;
	private String				type;
	private ArrayList<String>	supertypes;
	private ArrayList<String>	types;
	private ArrayList<String>	subtypes;
	private String				rarity;
	private String				text;
	private String				flavor;
	private String				artist;
	private String				number;
	private String				power;
	private String				toughness;
	private Integer				loyalty;
	private Integer				multiverseid;
	private Integer				idBdd;
	private ArrayList<String>	variations;
	private String				imageName;
	private String				border;
	private String				watermark;
	private String				reserved;
	private String				releaseDate;
	private Map<String,String>	legalities;
	
	// Not part of JSON, will be set later
	private String				setCode;
	private String				setName;
	private int					editionId;
	
	public String getLayout()
	{
		return layout;
	}
	public void setLayout(String layout)
	{
		this.layout = layout;
	}
	public String getName()
	{
		return name;
	}
	public void setName(String name)
	{
		this.name = name;
	}
	public ArrayList<String> getNames()
	{
		return names;
	}
	public void setNames(ArrayList<String> names)
	{
		this.names = names;
	}
	public String getManaCost()
	{
		return manaCost;
	}
	public void setManaCost(String manaCost)
	{
		this.manaCost = manaCost;
	}
	public Integer getCmc()
	{
		return cmc;
	}
	public void setCmc(Integer cmc)
	{
		this.cmc = cmc;
	}
	public ArrayList<String> getColors()
	{
		return colors;
	}
	public void setColors(ArrayList<String> colors)
	{
		this.colors = colors;
	}
	public String getType()
	{
		return type;
	}
	public void setType(String type)
	{
		this.type = type;
	}
	public ArrayList<String> getSupertypes()
	{
		return supertypes;
	}
	public void setSupertypes(ArrayList<String> supertypes)
	{
		this.supertypes = supertypes;
	}
	public ArrayList<String> getTypes()
	{
		return types;
	}
	public void setTypes(ArrayList<String> types)
	{
		this.types = types;
	}
	public ArrayList<String> getSubtypes()
	{
		return subtypes;
	}
	public void setSubtypes(ArrayList<String> subtypes)
	{
		this.subtypes = subtypes;
	}
	public String getRarity()
	{
		return rarity;
	}
	public void setRarity(String rarity)
	{
		this.rarity = rarity;
	}
	public String getText()
	{
		return text;
	}
	public void setText(String text)
	{
		this.text = text;
	}
	public String getFlavor()
	{
		return flavor;
	}
	public void setFlavor(String flavor)
	{
		this.flavor = flavor;
	}
	public String getArtist()
	{
		return artist;
	}
	public void setArtist(String artist)
	{
		this.artist = artist;
	}
	public String getNumber()
	{
		return number;
	}
	public void setNumber(String number)
	{
		this.number = number;
	}
	public String getPower()
	{
		return power;
	}
	public void setPower(String power)
	{
		this.power = power;
	}
	public String getToughness()
	{
		return toughness;
	}
	public void setToughness(String toughness)
	{
		this.toughness = toughness;
	}
	public Integer getLoyalty()
	{
		return loyalty;
	}
	public void setLoyalty(Integer loyalty)
	{
		this.loyalty = loyalty;
	}
	public Integer getMultiverseid()
	{
		return multiverseid;
	}
	public void setMultiverseid(Integer multiverseid)
	{
		this.multiverseid = multiverseid;
	}
	public ArrayList<String> getVariations()
	{
		return variations;
	}
	public void setVariations(ArrayList<String> variations)
	{
		this.variations = variations;
	}
	public String getImageName()
	{
		return imageName;
	}
	public void setImageName(String imageName)
	{
		this.imageName = imageName;
	}
	public String getBorder()
	{
		return border;
	}
	public void setBorder(String border)
	{
		this.border = border;
	}
	public String getWatermark()
	{
		return watermark;
	}
	public void setWatermark(String watermark)
	{
		this.watermark = watermark;
	}
	public String getSetName()
	{
		return setName;
	}
	public void setSetName(String setName)
	{
		this.setName = setName;
	}
	public String getSetCode()
	{
		return setCode;
	}
	public void setSetCode(String setCode)
	{
		this.setCode = setCode;
	}
	public String getReserved() {
		return reserved;
	}
	public void setReserved(String reserved) {
		this.reserved = reserved;
	}
	public String getReleaseDate() {
		return releaseDate;
	}
	public void setReleaseDate(String releaseDate) {
		this.releaseDate = releaseDate;
	}

	public Map<String,String> getLegalities() {
		return legalities;
	}
	public void setLegalities(Map<String,String> legalities) {
		this.legalities = legalities;
	}
	public int getEditionId() {
		return editionId;
	}
	public void setEditionId(int editionId) {
		this.editionId = editionId;
	}
	
	public String toSql() {
		
		Joiner.MapJoiner mapJoiner = Joiner.on("; ").withKeyValueSeparator("=");
		StringBuilder sqlInsert = new StringBuilder();
		
		sqlInsert.append("INSERT INTO ");
		sqlInsert.append("mtgcard ");
//		sqlInsert.append(" ");
		sqlInsert.append("VALUES (");
		sqlInsert.append("'" + this.idBdd +"', ");
		sqlInsert.append("'" + this.multiverseid +"', ");
		sqlInsert.append("'" + this.editionId +"', ");
		sqlInsert.append("'" + StringUtils.replace(StringEscapeUtils.escapeSql(this.name), "'", "''") +"', ");
		//TODO Rarity in JOIN
		sqlInsert.append("'" + this.rarity +"', ");		
		sqlInsert.append("'" + this.cmc +"', ");
		sqlInsert.append("'" + this.manaCost +"', ");
		sqlInsert.append("'" + StringUtils.replace(StringEscapeUtils.escapeSql(this.type), "'", "''") +"', ");
		sqlInsert.append("'" + this.power +"', ");
		sqlInsert.append("'" + this.toughness +"', ");
		sqlInsert.append("'" + this.loyalty +"', ");

		//TODO GESTION LISTE STRING (Types, Legalities, Colors)
		sqlInsert.append("'" + StringUtils.replace(StringEscapeUtils.escapeSql(StringUtils.join(this.supertypes,"; ")), "'", "''") +"', ");
		sqlInsert.append("'" + StringUtils.replace(StringEscapeUtils.escapeSql(StringUtils.join(this.types,"; ")), "'", "''") +"', ");
		sqlInsert.append("'" + StringUtils.replace(StringEscapeUtils.escapeSql(StringUtils.join(this.subtypes,"; ")), "'", "''") +"', ");
//		if(this.legalities != null){
//			sqlInsert.append("'" + mapJoiner.join(this.legalities) +"', ");
//		}else{
//			sqlInsert.append("'" + "null"+"', ");
//		}
		
		sqlInsert.append("'" + StringUtils.replace(StringEscapeUtils.escapeSql(this.text), "'", "''") +"', ");
		sqlInsert.append("'" + StringUtils.replace(StringEscapeUtils.escapeSql(this.flavor), "'", "''") +"' ");
		
		sqlInsert.append(");\n");
		
		return sqlInsert.toString().replaceAll("'null'", "NULL");
		
	}
	
	public String toJson() {
		StringBuilder jsonInsert = new StringBuilder();
		
		if(this.colors == null || this.colors.size() == 0){
			this.colors = new ArrayList<String>();
			this.colors.add("Colorless");
		}
		
		jsonInsert.append("{\"index\":{\"_index\":\"mtgcard\",\"_type\":\"mtgcard\",\"_id\":\"" + this.idBdd + "\"}}\n");
		jsonInsert.append("{\"name\":\""
				+ org.apache.commons.lang3.StringEscapeUtils.escapeJson(this.name)
				+ "\",\"type\":\""
				+ org.apache.commons.lang3.StringEscapeUtils.escapeJson(StringUtils
						.join(this.type, " "))
				+ "\",\"types\":\""
				+ org.apache.commons.lang3.StringEscapeUtils.escapeJson(StringUtils
						.join(this.types, " "))
				+ "\",\"supertypes\":\""
				+ org.apache.commons.lang3.StringEscapeUtils.escapeJson(StringUtils
						.join(this.supertypes, " "))
				+ "\",\"subtypes\":\""
				+ org.apache.commons.lang3.StringEscapeUtils.escapeJson(StringUtils
						.join(this.subtypes, " "))
				+ "\",\"colors\":\""
				+ org.apache.commons.lang3.StringEscapeUtils.escapeJson(StringUtils
						.join(this.colors, " ")) 
				+ "\",\"text\":\""
				+ org.apache.commons.lang3.StringEscapeUtils.escapeJson(this.text)
				// + "\n" + this.flavor
				+ "\"}\n");
		
		return jsonInsert.toString().replaceAll("\"null\"", "null");
	}
	public Integer getIdBdd() {
		return idBdd;
	}
	public void setIdBdd(Integer idBdd) {
		this.idBdd = idBdd;
	}

}
