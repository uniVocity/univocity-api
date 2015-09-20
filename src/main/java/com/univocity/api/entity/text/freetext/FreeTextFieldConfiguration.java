package com.univocity.api.entity.text.freetext;

import java.util.*;

public class FreeTextFieldConfiguration {

	private final List<String[]> matchingWords = new ArrayList<String[]>();

	public void addMatchingWords(String ... words){
		matchingWords.add(words);
	}

	public List<String[]> getMatchingWords(){
		return Collections.unmodifiableList(matchingWords);
	}
}
