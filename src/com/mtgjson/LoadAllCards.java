package com.mtgjson;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
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
		List<MTGCard> allCards = new ArrayList<MTGCard>();
		int editionId=0;
		for (MTGSet set : sets.values()) {
			set.setEditionId(editionId);
			System.out.println(set.toSql());
			for (MTGCard card : set.getCards()) {
				card.setSetCode(set.getCode());
				card.setSetName(set.getName());
				card.setEditionId(editionId);
				allCards.add(card);
				System.out.println(card.toSql());
			}
			editionId++;
		}
		return allCards;
	}
}
