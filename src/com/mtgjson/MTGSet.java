package com.mtgjson;

import java.util.ArrayList;

import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
@JsonIgnoreProperties(ignoreUnknown=true)
public class MTGSet
{
	private String					name;
	private String					code;
	private String					releaseDate;
	private String					border;
	private String					type;
	private String					block;
	private String					gathererCode;
	
	private int						editionId;
	private ArrayList<MTGCard>		cards;
	
	public String getName()
	{
		return name;
	}
	public void setName(String name)
	{
		this.name = name;
	}
	public String getCode()
	{
		return code;
	}
	public void setCode(String code)
	{
		this.code = code;
	}
	public String getReleaseDate()
	{
		return releaseDate;
	}
	public void setReleaseDate(String releaseDate)
	{
		this.releaseDate = releaseDate;
	}
	public String getBorder()
	{
		return border;
	}
	public void setBorder(String border)
	{
		this.border = border;
	}
	public String getType()
	{
		return type;
	}
	public void setType(String type)
	{
		this.type = type;
	}
	public String getBlock()
	{
		return block;
	}
	public void setBlock(String block)
	{
		this.block = block;
	}
	public ArrayList<MTGCard> getCards()
	{
		return cards;
	}
	public void setCards(ArrayList<MTGCard> cards)
	{
		this.cards = cards;
	}
	public String getGathererCode() {
		return gathererCode;
	}
	public void setGathererCode(String gathererCode) {
		this.gathererCode = gathererCode;
	}
	public String toSql() {
		StringBuilder sqlInsert = new StringBuilder();
		
		sqlInsert.append("INSERT INTO ");
		sqlInsert.append("MTGEDITION ");
		sqlInsert.append("(id, name, code) ");
		sqlInsert.append("VALUES (");
		sqlInsert.append("'" + this.editionId +"', ");
		sqlInsert.append("'" + StringUtils.replace(StringEscapeUtils.escapeJson(this.name), "'", "''") +"', ");
		sqlInsert.append("'" + this.code+"'");
		
		sqlInsert.append(");\n");
		
		return sqlInsert.toString();
	}
	public int getEditionId() {
		return editionId;
	}
	public void setEditionId(int editionId) {
		this.editionId = editionId;
	}
}
