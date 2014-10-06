
package library;

import java.util.Properties;
import java.io.*;

public class PropertyFile extends Properties{
	
	private static String propFileName = "config.properties";
	private InputStream inputStream;
	
	/**
	 * @param args
	 * @throws IOException 
	 * try to read propertyfile
	 */
	
	public PropertyFile(String folder) throws IOException{
		super();
		String configFile = folder+ PropertyFile.propFileName ;
		this.inputStream =  new FileInputStream(configFile);
		this.load(this.inputStream);
        if (inputStream == null) {
            throw new FileNotFoundException("property file '" + propFileName + "' not found in the classpath");
        }

	}
}
