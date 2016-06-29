/**
 * 
 */
package com.xiekongye.staticdata;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author xiekongye
 *
 */
public class DB {
	private static Map<String, Book> bookMap = new LinkedHashMap<String,Book>();
	static{
		bookMap.put("1", new Book("1","C++"));
		bookMap.put("2", new Book("2","Java"));
		bookMap.put("3", new Book("3","JavaScript"));
	}

	public static Map<String, Book> getAll() {
		return bookMap;
	}
}
class Book{
    
    private String id;
    private String name;

    public Book() {
        super();
    }
    public Book(String id, String name) {
        super();
        this.id = id;
        this.name = name;
    }
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
}