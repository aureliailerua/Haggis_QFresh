package unittests;

import static org.junit.Assert.*;

import java.io.IOException;

import library.PropertyFile;

import org.junit.BeforeClass;
import org.junit.Test;

import server.Server;

public class TestPropertyFile {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@Test
	public void testProperties() throws IOException {
		
		PropertyFile prop = new PropertyFile();
		assertNotNull(prop.getServerAddress());
		assertNotNull(prop.getPort());
	}
}