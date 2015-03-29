package com.mtgjson;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class LoadAllCards {
	public static void main(String[] args) throws JsonParseException,
			JsonMappingException, IOException {
		@SuppressWarnings("unchecked")
		List<MTGCard> allCards = getAllCards((Map<String, MTGSet>) new ObjectMapper()
				.readValue(new File("AllSets-x.json"),
						new TypeReference<Map<String, MTGSet>>() {
						}));
		System.out.println("Number of cards: " + allCards.size());
	}

	public static List<MTGCard> getAllCards(Map<String, MTGSet> sets) {
		PrintWriter writerSql = null;
		PrintWriter writerJson = null;
		int cardId = 0;
		int legalitiesId = 0;
		int colorsId = 0;
		try {
			writerSql = new PrintWriter("MTG.sql", "UTF-8");
			writerJson = new PrintWriter("mtg.json", "UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
		}
		writerSql.write("TRUNCATE TABLE mtgcard;\n");
		writerSql.write("TRUNCATE TABLE mtgedition;\n");
		List<MTGCard> allCards = new ArrayList<MTGCard>();
		Set<Entry<String,String>> legalities = new HashSet<Entry<String, String>>();
		Set<String> colors = new HashSet<String>();
		int editionId=0;
		//Création des cartes et editions
		for (MTGSet set : sets.values()) {
			set.setEditionId(editionId);
			writerSql.write(set.toSql());
			for (MTGCard card : set.getCards()) {
				card.setIdBdd(cardId);
				card.setSetCode(set.getCode());
				card.setSetName(set.getName());
				card.setEditionId(editionId);
				allCards.add(card);
				//Alimentation des legalities
				if(card.getLegalities() != null){					
					legalities.addAll(card.getLegalities().entrySet());
				}
				//Alimentation des couleurs
				if(card.getColors() != null){					
					colors.addAll(card.getColors());
				}
				writerJson.write(card.toJson());
				writerSql.write(card.toSql());
				writerSql.flush();
				writerJson.flush();
				cardId++;
			}
			editionId++;
		}
		
		//Création de table legalities
		for (Iterator<Entry<String, String>> keyIt = legalities.iterator(); keyIt.hasNext();) {
			StringBuilder sqlInsert = new StringBuilder();
			Entry<String, String> key = keyIt.next();
			sqlInsert.append("INSERT INTO ");
			sqlInsert.append("mtglegalities ");
			sqlInsert.append("VALUES (");
			sqlInsert.append("'" + legalitiesId + "', ");
			sqlInsert.append("'" + key.getKey() + "', ");
			sqlInsert.append("'" + key.getValue() + "'");
			sqlInsert.append(");\n");

			writerSql.write(sqlInsert.toString());
			writerSql.flush();
			legalitiesId++;
		}
		
		//Création de table jointure cards legalities
		for (MTGCard cardElement : allCards) {
			if(cardElement.getLegalities() != null){
				for(Entry<String, String> legalitieSet : cardElement.getLegalities().entrySet()){
					StringBuilder sqlInsert = new StringBuilder();
					legalitiesId=0;
					for (Iterator<Entry<String, String>> keyIt = legalities.iterator(); keyIt.hasNext();) {
						Entry<String, String> key = keyIt.next();
						if(key.equals(legalitieSet)){
							sqlInsert.append("INSERT INTO ");
							sqlInsert.append("mtgcardslegalities ");
							sqlInsert.append("VALUES (");
							sqlInsert.append("'" + cardElement.getIdBdd() + "', ");
							sqlInsert.append("'" + legalitiesId + "'");
							sqlInsert.append(");\n");
							break;
						}
						legalitiesId++;
					}
					
					
					writerSql.write(sqlInsert.toString());
					writerSql.flush();
					
				}
			}
		}
		
		//Création de table color
		for (String color : colors) {
			StringBuilder sqlInsert = new StringBuilder();
			sqlInsert.append("INSERT INTO ");
			sqlInsert.append("mtgcolors ");
			sqlInsert.append("VALUES (");
			sqlInsert.append("'" + colorsId + "', ");
			sqlInsert.append("'" + color + "'");
			sqlInsert.append(");\n");

			writerSql.write(sqlInsert.toString());
			writerSql.flush();
			colorsId++;
		}
		
		//Création de table jointure cards colors
		for (MTGCard cardElement : allCards) {
			if(cardElement.getColors() != null){
				for (String color : cardElement.getColors()) {
					StringBuilder sqlInsert = new StringBuilder();
					colorsId=0;
					for (String col : colors) {
						if(col.equals(color)){
							sqlInsert.append("INSERT INTO ");
							sqlInsert.append("mtgcardscolors ");
							sqlInsert.append("VALUES (");
							sqlInsert.append("'" + cardElement.getIdBdd() + "', ");
							sqlInsert.append("'" + colorsId + "'");
							sqlInsert.append(");\n");
							break;
						}
						colorsId++;
					}
					
					
					writerSql.write(sqlInsert.toString());
					writerSql.flush();
					
				}
			}
		}
		
		writerSql.write("commit;\n");
		writerSql.flush();
		writerJson.flush();
		writerSql.close();
		writerJson.close();
		return allCards;
	}
}
