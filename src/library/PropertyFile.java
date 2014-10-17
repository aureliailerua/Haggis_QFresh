
package library;

import java.util.Properties;
import java.io.*;
import java.net.InetAddress;
import java.net.UnknownHostException;

import server.Server;

public class PropertyFile extends Properties{
	
	private static String propFileName = "config.properties";
	private InputStream inputStream;
	
	/**
	 * @param args
	 * @throws IOException 
	 * try to read propertyfile
	 */
	
	public PropertyFile() throws IOException{
		super();
		String myLocation = Server.class.getProtectionDomain().getCodeSource().getLocation().getPath();
		String configFile = myLocation+ PropertyFile.propFileName ;
		this.inputStream =  new FileInputStream(configFile);
		this.load(this.inputStream);
        if (inputStream == null) {
            throw new FileNotFoundException("property file '" + propFileName + "' not found in the classpath");
        }

	}
	public InetAddress getServerAddress() throws UnknownHostException {
		return InetAddress.getByName(getProperty("server.address"));
	}
	public int getPort(){
		return Integer.parseInt(getProperty("port")); 
	}
}
