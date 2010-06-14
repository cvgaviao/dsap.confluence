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
public class Component {

	@XmlElement
	private String key;

	@XmlElement
	private String name;

	@XmlElement
	@XmlElementWrapper
	private List<Feature> featureList;

	@XmlTransient
	private Project project;

	public Component() {
	}

	public Component(Project pProject, String pComponentKey) {
		key = pComponentKey;
		project = pProject;
	}

	public String getKey() {
		return key;
	}

	public List<Feature> getFeatureList() {
		if (featureList == null) {
			featureList = new ArrayList<Feature>();
		}
		return featureList;
	}

	public Project getProject() {
		return project;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

}
