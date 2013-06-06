package running;

import WordPressXML.DatabaseXML;
import WordPressXML.WordPressXMLParser;

public class ParseXMLMain {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		WordPressXMLParser wp  = new WordPressXMLParser(); 
		wp.parse(DatabaseXML.loadXml("src/WordPressXML/bayareachristianchurch.wordpress.2013-06-06.xml"));

	}

}
