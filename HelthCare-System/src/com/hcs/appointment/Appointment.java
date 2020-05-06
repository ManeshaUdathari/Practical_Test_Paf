package com.hcs.appointment;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class Appointment {
	
	//DB connection
	
	private Connection connect()
	 {
		
		  Connection con = null;
		  
	 try
	 {
		 	Class.forName("com.mysql.jdbc.Driver");
		 	con =
		 			
		 			DriverManager.getConnection(
		 			"jdbc:mysql://127.0.0.1:3306/hcs", "root", "");
	 }
	 catch (Exception e)
	 {
		 e.printStackTrace();
	 }
	 
	 	return con;
	 } 
	
	
	//Read
	public String readAppointment()

	 {
			String output = "";
			
			try
			{
				Connection con = connect();
				if (con == null)
				{
					return "Error while connecting to the database for reading.";
	 }
				
	 // Prepare the html table to be displayed
	 output = "<table border='1'><tr><th>Patient Name</th> <th>Patient Email</th><th>Doctor Name</th>"+ "<th>Reason</th> <th>Update</th><th>Remove</th></tr>"; 
	
	 String query = "select * from appointment";
	 Statement stmt = con.createStatement();
	 ResultSet rs = stmt.executeQuery(query);
	 
	 // iterate through the rows in the result set
	 while (rs.next())
	 {
		 String AppointmentID = Integer.toString(rs.getInt("AppointmentID"));
		 String PatientName = rs.getString("PatientName");
		 String PatientEmail = rs.getString("PatientEmail");
		 String DoctorName = rs.getString("DoctorName");
		 String Reason = rs.getString("Reason");
		 
		 	
	 // Add into the html table
		 output += "<tr><td><input id='hidAppointmentIDUpdate'name='hidAppointmentIDUpdate'type='hidden' value='" + AppointmentID + "'>" + PatientName + "</td>";
		 output += "<td>" + PatientEmail + "</td>";
		 output += "<td>" + DoctorName + "</td>";
		 output += "<td>" + Reason + "</td>";
		 
	 // buttons
		 
		 output += "<td><input name='btnUpdate'          "
		   		  + "type='button' value='Update'         "
		   		  + "class='btnUpdate btn btn-secondary'></td>"      
		   		  + "<td><input name='btnRemove'         "
		   		  + "type='button' value='Remove'         "
		   		  + "class='btnRemove btn btn-danger'        "
		   		  + "data-AppointmentId='"       
		   		  + AppointmentID + "'>" + "</td></tr>"; 
	 }
	 
	 con.close();
	 
	 // Complete the html table
	 output += "</table>";
	 }
			
			catch (Exception e)
			{
				output = "Error while reading the appointments.";
				System.err.println(e.getMessage());
			}
				return output;
	 } 

	
	//Insert
	public String insertAppointment(String PatientName, String PatientEmail, String DoctorName, String Reason)
	{
		
			 String output = "";
			 
			 	try
			 		{
			 			Connection con = connect();
			 			
			 			if (con == null)
			 			{
			 				
			 				return "Error while connecting to the database for inserting.";
			 }
			 			
			 // create a prepared statement
			 String query = " insert into appointment(`AppointmentID`,`PatientName`,`PatientEmail`,`DoctorName`,`Reason`)" + " values (?, ?, ?, ?, ?)";
			 PreparedStatement preparedStmt = con.prepareStatement(query);
			 
			 // binding values
			 preparedStmt.setInt(1, 0);
			 preparedStmt.setString(2, PatientName);
			 preparedStmt.setString(3, PatientEmail);
			 preparedStmt.setString(4, DoctorName);
			 preparedStmt.setString(5, Reason);
			 
			 // execute the statement
			 preparedStmt.execute();
			 con.close();
			 String newAppointment = readAppointment();
			 output = "{\"status\":\"success\", \"data\": \"" + 
					 	newAppointment + "\"}";
			 }
		catch (Exception e)
			 	
		{
			 output = "{\"status\":\"error\", \"data\": \"Error while inserting the appointment.\"}";
			 System.err.println(e.getMessage());
		}
			 return output;
		}

	
	//Update
	public String updateAppointment(String AppointmentID, String PatientName, String PatientEmail, String DoctorName, String Reason)
	{
		
			 String output = "";
			 
			 	try
			 		{
			 			Connection con = connect();
			 			if (con == null)
			 			{
			 				return "Error while connecting to the database for updating.";
			 }
			 			
			 			
			 // create a prepared statement
			 String query = "UPDATE appointment SET PatientName=?,PatientEmail=?,DoctorName=?,Reason=? WHERE AppointmentID=?";
			 PreparedStatement preparedStmt = con.prepareStatement(query);
			 
			 
			 // binding values
			 preparedStmt.setString(1, PatientName);
			 preparedStmt.setString(2, PatientEmail);
			 preparedStmt.setString(3, DoctorName);
			 preparedStmt.setString(4, Reason);
			 preparedStmt.setInt(5, Integer.parseInt(AppointmentID));
			 
			 
			 // execute the statement
			 preparedStmt.execute();
			 con.close();
			 String newAppointment = readAppointment();
			 output = "{\"status\":\"success\", \"data\": \"" +
			 newAppointment + "\"}";
			 }
			 	
			 catch (Exception e)
			 {
				 output = "{\"status\":\"error\", \"data\":\"Error while updating the appointment.\"}";
				 System.err.println(e.getMessage());
			 }
			 	return output;
			 } 
	
	
	//Delete
	public String deleteAppointment(String AppointmentID)
	 {
			String output = "";
			try
			{
				Connection con = connect();
				if (con == null)
				{
					
	 return "Error while connecting to the database for deleting.";
	 } 
	
	 // create a prepared statement
	 String query = "delete from appointment where AppointmentID=?";
	 PreparedStatement preparedStmt = con.prepareStatement(query);
	 
	 
	 // binding values
	 preparedStmt.setInt(1, Integer.parseInt(AppointmentID));
	 
	 
	 // execute the statement
	 preparedStmt.execute();
	 con.close();
	 String newAppointment = readAppointment();
	 output = "{\"status\":\"success\", \"data\": \"" + newAppointment + "\"}";
	 }
			catch (Exception e)
			{
				output = "{\"status\":\"error\", \"data\": \"Error while deleting the appointment.\"}";
				System.err.println(e.getMessage());
			}
				return output;
	 		}
	
}	
	

