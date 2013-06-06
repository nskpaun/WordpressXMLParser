package WordPressXML;

import java.util.ArrayList;
import java.util.HashMap;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.Node;



public class WordPressXMLParser {
	
	public final String PICTURE_URL = "picture_url";
	public final String CONTENT_KEY = "contentencoded";
	public final String CREATOR_KEY = "dccreator";
	public final String TITLE_KEY = "title";
	public final String DATE_KEY = "wppost_date";
	public final String EXCERPT_KEY = "excerptencoded";
	
	HashMap<String,String> itemMap;
	HashMap<String,String> creatorMap;
	ArrayList<HashMap<String,String>> posts;
	
	public ArrayList<HashMap<String,String>> parse(Document document) {
		posts = new ArrayList<HashMap<String,String>>();
		creatorMap = itemMap = new HashMap<String,String>();
		treeWalk(document);
		return posts;
	}
	
	public void treeWalk(Document document) {
        treeWalk( document.getRootElement() );
    }

    public void treeWalk(Element element) {
    	
    	if ( element.getName().equals("author") ) {
    		String key = null;
    		String value = null;
    		for ( int i = 0, size = element.nodeCount(); i < size; i++ ) {
    			Element el = (Element) element.node(i);
    			if ( el.getName().equals("author_login") ) {
    				key = el.getText();
    			} else if ( el.getName().equals("author_display_name")  ) {
    				value = el.getText();
    			}
    		}
    		
    		if ( key != null && value != null ) {
    			creatorMap.put(key, value);
    		}
    	}
    	
    	
    	boolean item = false;
    	if ( element.getName().equals("item") ) {
    		item = true;
    		itemMap = new HashMap<String,String>();
    		
    	}
    	
        for ( int i = 0, size = element.nodeCount(); i < size; i++ ) {
            Node node = element.node(i);
            
            if ( node instanceof Element ) {
            	if (itemMap!=null) {
	            	Element e = (Element) node;
	            	String keyString = e.getNamespacePrefix()+e.getName();
	            	itemMap.put(keyString, e.getText());
            	}
                treeWalk( (Element) node );
            }
            else {
                
            }
        }
        
        if (item) {
        	if (itemMap.containsKey(CONTENT_KEY)) {
        		String content = itemMap.get(CONTENT_KEY);
        		if ( content.startsWith("<a href=") ) {
        			content = content.substring(9);
            		String pictureUrl = content.substring(0,content.indexOf("\""));
            		
            		if (pictureUrl.endsWith(".jpg")) {
            			
            			String creator = itemMap.get(CREATOR_KEY);
            			creator = creatorMap.get(creator);
            			
            			itemMap.put(CREATOR_KEY, creator);
            			itemMap.put(CONTENT_KEY,content);
            			itemMap.put(PICTURE_URL, pictureUrl);
            			
            			posts.add(itemMap);
            			
            			System.out.println(" break--------------------------------------");
            			System.out.println(itemMap.get(PICTURE_URL));
            			System.out.println(itemMap.get(CREATOR_KEY));
            			System.out.println(itemMap.get(DATE_KEY));
            			System.out.println(itemMap.get(TITLE_KEY));
            			System.out.println(itemMap.get(EXCERPT_KEY));
            			System.out.println(itemMap.get(CONTENT_KEY));
            			
            		}
        		}
        	}
        	itemMap = null;
        }
    }

}
