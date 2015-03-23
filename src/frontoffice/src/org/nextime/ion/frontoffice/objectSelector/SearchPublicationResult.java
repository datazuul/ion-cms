package org.nextime.ion.frontoffice.objectSelector;

import org.nextime.ion.framework.helper.SearchResult;

public class SearchPublicationResult {
	
	private SearchResult searchResult;
	private PublicationResult publicationResult;

	/**
	 * Returns the publicationResult.
	 * @return PublicationResult
	 */
	public PublicationResult getPublicationResult() {
		return publicationResult;
	}

	/**
	 * Returns the searchResult.
	 * @return SearchResult
	 */
	public SearchResult getSearchResult() {
		return searchResult;
	}

	/**
	 * Sets the publicationResult.
	 * @param publicationResult The publicationResult to set
	 */
	public void setPublicationResult(PublicationResult publicationResult) {
		this.publicationResult = publicationResult;
	}

	/**
	 * Sets the searchResult.
	 * @param searchResult The searchResult to set
	 */
	public void setSearchResult(SearchResult searchResult) {
		this.searchResult = searchResult;
	}

}
