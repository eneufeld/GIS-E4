package de.neufeld.gis.core.test;

import static org.junit.Assert.*;

import java.lang.instrument.UnmodifiableClassException;
import java.util.Collections;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import de.neufeld.gis.core.ProjectService;

public class ProjectServiceTest {

	private ProjectService projectService;
	
	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void getMapsTest() {
		projectService.getMaps();
		fail("Not yet implemented");
	}
	@Test(expected= UnmodifiableClassException.class)
	public void getMapsUnmodifiableTest() {
		projectService.getMaps().add(null);
		fail("Not yet implemented");
	}

}
