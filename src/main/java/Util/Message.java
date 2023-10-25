package Util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Timestamp;

public class Message
{
	public String text;
	public Integer senderID;
	public Integer receiverID;
	public Timestamp createdTime;
	
	public Message(String text, Integer senderID, Integer receiverID, Timestamp createdTime)
	{
		this.text = text;
		this.senderID = senderID;
		this.receiverID = receiverID;
		this.createdTime = createdTime;
	}
	
	public void insertIntoDatabase()
	{
		try
		{
			Class.forName("com.mysql.jdbc.Driver");
			Connection connection = DriverManager.getConnection(Constant.Url, Constant.DBUserName, Constant.DBPassword);
			String sql = "INSERT INTO Message (text, senderID, receiverID, createdTime) VALUES (?, ?, ?, ?)";
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setString(1, this.text);
			ps.setInt(2, this.senderID);
			ps.setInt(3, this.receiverID);
			ps.setTimestamp(4, this.createdTime);
			ps.execute();
			ps.close();
		}
		catch (Exception e) {e.printStackTrace();}
	}
}
