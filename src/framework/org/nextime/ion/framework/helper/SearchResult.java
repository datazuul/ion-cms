package org.nextime.ion.framework.helper;

import org.nextime.ion.framework.business.Publication;

public class SearchResult {

	private Publication publication;
	private int version;
	private float score;
	
	public SearchResult(Publication p, int version, float s) {
		setPublication(p);
		setVersion(version);
		setScore(s);
	}
	
	/**
	 * Returns the publication.
	 * @return Publication
	 */
	public Publication getPublication() {
		return publication;
	}

	/**
	 * Returns the score.
	 * @return float
	 */
	public float getScore() {
		return score;
	}

	/**
	 * Sets the publication.
	 * @param publication The publication to set
	 */
	public void setPublication(Publication publication) {
		this.publication = publication;
	}

	/**
	 * Sets the score.
	 * @param score The score to set
	 */
	public void setScore(float score) {
		this.score = score;
	}

	/**
	 * Returns the version.
	 * @return int
	 */
	public int getVersion() {
		return version;
	}

	/**
	 * Sets the version.
	 * @param version The version to set
	 */
	public void setVersion(int version) {
		this.version = version;
	}

}
