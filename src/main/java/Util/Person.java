package Util;

public class Person {
	public Integer personid;
	public String name;
	public String gender;
	public Double overall_rating;
	public Integer rating_count;
	
	public Person(Integer id, String name, String gender, Double rating, Integer count) {
		this.personid = id;
		this.name = name;
		this.gender = gender;
		this.overall_rating = rating;
		this.rating_count = count;
	}
	
}
