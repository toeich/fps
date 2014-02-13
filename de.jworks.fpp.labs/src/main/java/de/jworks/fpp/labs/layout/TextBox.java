package de.jworks.fpp.labs.layout;

import de.jworks.fpp.labs.story.Story;

public class TextBox extends PageItem {

	/**
	 * The column count
	 */
	public int columnCount = 1;
	
	/**
	 * The column gap in points
	 */
	public double columnGap = 0.0;

	/**
	 * The top margin in points
	 */
	public double marginTop = 0.0;
	
	/**
	 * The bottom margin in points
	 */
	public double marginBottom = 0.0;
	
	/**
	 * The left margin in points
	 */
	public double marginLeft = 0.0;
	
	/**
	 * The right margin in points
	 */
	public double marginRight = 0.0;
	
	/**
	 * The vertical alignment
	 */
	public VerticalAlignment verticalAlignment = VerticalAlignment.TOP;

	/**
	 * The first baseline offset.
	 */
	public FirstBaselineOffset firstBaselineOffset = FirstBaselineOffset.ASCENT;
	
	/**
	 * The story
	 */
	public Story story;
	
}
