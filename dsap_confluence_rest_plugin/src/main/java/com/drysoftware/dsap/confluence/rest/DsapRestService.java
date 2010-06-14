package com.drysoftware.dsap.confluence.rest;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.felix.moduleloader.ResourceNotFoundException;
import org.randombits.confluence.metadata.MetadataManager;
import org.randombits.confluence.metadata.MetadataStorage;

import com.atlassian.confluence.pages.Page;
import com.atlassian.confluence.pages.PageManager;
import com.atlassian.confluence.spaces.Space;
import com.atlassian.confluence.spaces.SpaceManager;
import com.atlassian.plugins.rest.common.security.AnonymousAllowed;
import com.drysoftware.dsap.confluence.entities.Component;
import com.drysoftware.dsap.confluence.entities.Feature;
import com.drysoftware.dsap.confluence.entities.Project;
import com.drysoftware.dsap.confluence.entities.Story;

@Produces({ MediaType.APPLICATION_XML })
@AnonymousAllowed
@Path("/model")
public class DsapRestService {

	private final String PROJECT_DETAIL_PAGE = "1. Detalhes do Projeto";
	private PageManager pageManager;
	private SpaceManager spaceManager;

	@GET
	@Path("/project/{pProjectKey}")
	public Response getProject(@PathParam("pProjectKey") String pProjectKey) {
		try {
			System.out.println("Getting Project...");
			return Response.ok(searchforProject(pProjectKey)).build();
		} catch (ResourceNotFoundException e) {
			return Response.status(404).build();
		} catch (Exception e2) {
			return Response.status(500).build();
		} finally {
			System.out.println("Finished Getting Project...");
		}
	}

	@GET
	@Path("/component/{pProjectKey}/{pComponentKey}")
	public Response getComponent(@PathParam("pProjectKey") String pProjectKey,
			@PathParam("pComponentKey") String pComponentKey) {
		try {
			System.out.println("Getting Component...");
			return Response.ok(searchforComponent(pProjectKey, pComponentKey))
					.build();
		} catch (ResourceNotFoundException e) {
			return Response.status(404).build();
		} catch (Exception e2) {
			return Response.status(500).build();
		}
	}

	@GET
	@Path("/feature/{pProjectKey}/{pComponentKey}/{pFeatureKey}")
	public Response getFeature(@PathParam("pProjectKey") String pProjectKey,
			@PathParam("pComponentKey") String pComponentKey,
			@PathParam("pFeatureKey") String pFeatureKey) {
		try {
			System.out.println("Getting Feature...");
			return Response.ok(
					searchforFeature(pProjectKey, pComponentKey, pFeatureKey))
					.build();
		} catch (ResourceNotFoundException e) {
			return Response.status(404).build();
		} catch (Exception e2) {
			return Response.status(500).build();
		}
	}

	@GET
	@Path("/story/{pProjectKey}/{pComponentKey}/{pFeatureKey}/{pStoryKey}")
	public Response getStory(@PathParam("pProjectKey") String pProjectKey,
			@PathParam("pComponentKey") String pComponentKey,
			@PathParam("pFeatureKey") String pFeatureKey,
			@PathParam("pStoryKey") String pStoryKey) {
		try {
			System.out.println("Getting Story...");
			return Response.ok(
					searchforStory(pProjectKey, pComponentKey, pFeatureKey,
							pStoryKey)).build();
		} catch (ResourceNotFoundException e) {
			return Response.status(404).build();
		} catch (Exception e2) {
			return Response.status(500).build();
		}
	}

	private Story searchforStory(String pProjectKey, String pComponentKey,
			String pFeatureKey, String pStoryKey) throws Exception {
		Feature feature = searchforFeature(pProjectKey, pComponentKey,
				pFeatureKey);
		Story story = new Story(feature, pStoryKey);
		feature.getStoryList().add(story);
		return story;
	}

	/**
	 * Um projeto no Confluence é associado a um SPACE, já no Jira, é também um
	 * PROJETO.
	 * 
	 * @param pProjectKey
	 * @return
	 * @throws Exception
	 */
	private Project searchforProject(String pProjectKey) throws Exception {

		Project project = new Project(pProjectKey);
		project.setName("Não foi encontrado o space no confluence.");
		Page projectDetailPage = null;

		// busca os dados do projeto, que é um space no confluence.
		Space space = spaceManager.getSpace(pProjectKey);
		if (space != null) {
			System.out.println("Achei o space " + space.getName());
			project.setName(space.getName());
			projectDetailPage = pageManager.getPage(pProjectKey,
					PROJECT_DETAIL_PAGE);
			if (projectDetailPage != null) {
				// Get Metadata info
				MetadataStorage dataStorage = MetadataManager.getInstance()
						.loadReadableData(projectDetailPage);

				// get Jira Project Key
				String jiraProjectKey = dataStorage.getString("jiraProjectKey",
						"sem valor");
				project.setJiraProjectKey(jiraProjectKey);

				// get Components
				project.getComponentList().addAll(
						searchforComponents(project, dataStorage));
			} else {
				project.setName("Não foi encontrado a Página de Detalhes de Projeto no confluence.");
			}
		} else {
			throw new ResourceNotFoundException("Space '" + pProjectKey
					+ "' não foi encontrada.");
		}

		return project;
	}

	private Feature searchforFeature(String pProjectKey, String pComponentKey,
			String pFeatureKey) throws Exception {
		Component component = searchforComponent(pProjectKey, pComponentKey);
		Feature feature = new Feature(component, pFeatureKey);
		component.getFeatureList().add(feature);
		return feature;
	}

	@SuppressWarnings("unchecked")
	private List<Component> searchforComponents(Project pProject,
			MetadataStorage pDataStorage) throws Exception {
		List<Component> componentList = new ArrayList<Component>();
		// get the components
		try {

			Map<Object, Map> mapComponentes = (Map) pDataStorage.getObject(
					"Components", null);
//			System.out.println("Map Comp:" + mapComponentes);

			for (Object index : mapComponentes.keySet()) {

				Object row = mapComponentes.get(index);

				if (row instanceof Map) {
//					System.out.println("RowMAP:" + row + ":" + row.getClass());
					Map mapRow = (Map) row;
					Component component = new Component(pProject, (String) mapRow.get("componentKey"));
					component.setName((String) mapRow.get("componentName"));
					componentList.add(component);
					
					
				}
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return componentList;
	}

	private Component searchforComponent(String pProjectKey,
			String pComponentKey) throws Exception {
		Project project = searchforProject(pProjectKey);
		Component component = new Component(project, pComponentKey);
		project.getComponentList().add(component);
		return component;
	}

	public void setPageManager(PageManager pageManager) {
		this.pageManager = pageManager;
	}

	public PageManager getPageManager() {
		return pageManager;
	}

	public void setSpaceManager(SpaceManager spaceManager) {
		this.spaceManager = spaceManager;
	}

	public SpaceManager getSpaceManager() {
		return spaceManager;
	}
}
