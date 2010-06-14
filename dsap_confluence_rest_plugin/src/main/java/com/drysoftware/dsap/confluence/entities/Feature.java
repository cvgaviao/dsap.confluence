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
public class Feature {
	@XmlElement
	private String key;

	@XmlElement
	private String name;

	@XmlElement
	@XmlElementWrapper
	private List<Story> storyList;

	@XmlTransient
	private Component component;

	protected Feature() {

	}

	public Feature(Component pComponent, String pFeatureKey) {
		key = pFeatureKey;
		component = pComponent;
	}

	public String getKey() {
		return key;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Story> getStoryList() {
		if (storyList == null) {
			storyList = new ArrayList<Story>();
		}
		return storyList;
	}

	public void setStoryList(List<Story> storyList) {
		this.storyList = storyList;
	}

	public Component getComponent() {
		return component;
	}
}
