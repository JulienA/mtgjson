package com.mtgjson;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
		try {
			writerSql = new PrintWriter("MTG.sql", "UTF-8");
			writerJson = new PrintWriter("mtg.json", "UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
		}
		writerSql.write("TRUNCATE TABLE MTGCARD;\n");
		writerSql.write("TRUNCATE TABLE MTGEDITION;\n");
		List<MTGCard> allCards = new ArrayList<MTGCard>();
		int editionId=0;
		for (MTGSet set : sets.values()) {
			set.setEditionId(editionId);
//			System.out.println(set.toSql());
			writerSql.write(set.toSql());
			for (MTGCard card : set.getCards()) {
				card.setIdBdd(cardId);
				card.setSetCode(set.getCode());
				card.setSetName(set.getName());
				card.setEditionId(editionId);
				allCards.add(card);
//				System.out.println(card.toSql());
//				System.out.println(card.getTypes());
//				System.out.println(card.getSupertypes());
//				System.out.println(card.toJson());
				writerJson.write(card.toJson());
				writerSql.write(card.toSql());
				writerSql.flush();
				writerJson.flush();
				cardId++;
			}
			editionId++;
		}
		writerSql.write("commit;\n");
		writerSql.flush();
		writerJson.flush();
		writerSql.close();
		writerJson.close();
		return allCards;
	}
}
