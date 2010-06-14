package com.drysoftware.dsap.confluence.entities;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Scenario {
		
	private Story story;

	@XmlElement
	private String id;

	@XmlElement
	private String name;

	@XmlElement
	private String narrative;

	protected Scenario() {
	}

	public Scenario(Story pStory, String pScenarioKey) {
		id = pScenarioKey;
		story = pStory;
	}

	public String getKey() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Story getStory() {
		return story;
	}

	public void setNarrative(String narrative) {
		this.narrative = narrative;
	}

	public String getNarrative() {
		return narrative;
	}

}
