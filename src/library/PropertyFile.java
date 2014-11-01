
package library;

import java.util.Properties;
import java.io.*;
import java.net.InetAddress;
import java.net.URISyntaxException;
import java.net.UnknownHostException;
import java.nio.file.Paths;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import server.Server;

public class PropertyFile extends Properties{
	
	private static String propFileName = "config.properties";
	private InputStream inputStream;
	private static final Logger log = LogManager.getLogger( Server.class.getName() );
	
	/**
	 * @param args
	 * @throws IOException 
	 * try to read propertyfile
	 */
	
	public PropertyFile() throws IOException{
		super();
		try {
			File myLocation = Paths.get(Server.class.getProtectionDomain().getCodeSource().getLocation().toURI()).toFile();
			File configFile = new File(myLocation,PropertyFile.propFileName);
			log.debug("propertyFileName:" + configFile.toString());
			this.inputStream =  new FileInputStream(configFile);
			
			
			this.load(this.inputStream);
	        if (inputStream == null) {
	            throw new FileNotFoundException("property file '" + propFileName + "' not found in the classpath");
	        }
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	public InetAddress getServerAddress() throws UnknownHostException {
		return InetAddress.getByName(getProperty("server.address"));
	}
	public int getPort(){
		return Integer.parseInt(getProperty("port")); 
	}
}
