package com.drysoftware.dsap.confluence.entities;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@XmlSeeAlso(Component.class)
public class Project {

	@XmlElement
	private String key;

	@XmlElement
	private String name;
	
	@XmlElement
	private String jiraProjectKey;

	@XmlElement(name="component")
	@XmlElementWrapper(name="componentList")
	private List<Component> componentList;
	
	protected Project() {
	}
	
	public Project(String pProjectKey) {
		key = pProjectKey;
	}


	public String getKey() {
		return key;
	}


	public List<Component> getComponentList() {
		if (componentList == null)
		{
			componentList = new ArrayList<Component>();
		}
		return componentList;
	}

	public void setName(String name) {
		this.name = name;
	}


	public String getName() {
		return name;
	}

	public void setJiraProjectKey(String jiraProjectKey) {
		this.jiraProjectKey = jiraProjectKey;
	}

	public String getJiraProjectKey() {
		return jiraProjectKey;
	}
}
