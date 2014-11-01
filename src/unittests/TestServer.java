package unittests;

import static org.junit.Assert.*;

import java.io.IOException;
import java.net.Socket;

import junit.framework.Assert;
import library.PropertyFile;

import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import server.Server;

public class TestServer {

	@Rule
	public ExpectedException thrown = ExpectedException.none();

	private Server instantiateServer() throws IOException{
		
		Server server = new Server();
		server.run();
		try {
			Thread.sleep(5);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return server;
	}
	
	@Test
	public void testInitalization() throws IOException {
		
		Server server = instantiateServer();
		assertNotNull(server);
		PropertyFile prop = new PropertyFile();
		Socket socket = new Socket(prop.getServerAddress(), prop.getPort());
		assertNotNull(socket);
		server.stopListen();
	}
}
