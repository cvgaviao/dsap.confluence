package com.drysoftware.dsap.confluence.entities;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Story {

	@XmlElement
	private String key;

	@XmlElement
	private String name;

	@XmlElement
	@XmlElementWrapper
	private List<Scenario> scenarioList;

	@XmlTransient
	private Feature feature;

	protected Story() {
	}

	public Story(Feature pFeature, String pStoryKey) {
		key = pStoryKey;
		feature = pFeature;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String pKey) {
		this.key = pKey;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Scenario> getScenarioList() {
		if (scenarioList == null) {
			scenarioList = new ArrayList<Scenario>();
		}
		return scenarioList;
	}

	public Feature getFeature() {
		return feature;
	}
}
