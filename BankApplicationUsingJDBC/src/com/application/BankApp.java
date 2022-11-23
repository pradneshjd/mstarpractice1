package com.application;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.sql.*;
import java.util.Scanner;


//import com.jdbc_v1.model.Registration;
import com.model.Register;
import com.mysql.cj.xdevapi.Statement;

public class BankApp {
	
	public static Register register(Connection con) throws SQLException {
		
		
		Scanner sc3 = new Scanner(System.in);
		System.out.println("Enter User ID: ");
		int U_Id = sc3.nextInt();
		System.out.println("Enter the User_name: ");
		String U_Name = sc3.next();
		System.out.println("Enter the First Name: ");
		String F_Name = sc3.next();
		System.out.println("Enter the Last Name2: ");
		String L_Name = sc3.next();
		System.out.print("Enter the password: ");
		String password = sc3.next();
		System.out.println();
		System.out.println("Enter the Role Admin or User: ");
		String Role = sc3.next();
		System.out.println();
		Register reg = new Register(U_Id, U_Name, F_Name, L_Name, password, Role);
		//System.out.println(reg.getUser_id());
		PreparedStatement pstmt = con.prepareStatement("insert into register values(?, ?, ?, ?, ?, ?)");
		pstmt.setInt(1, U_Id);
		pstmt.setString(2, F_Name);
		pstmt.setString(3, L_Name);
		pstmt.setString(4, U_Name);
		pstmt.setString(5, password);
		pstmt.setString(6, Role);
		pstmt.executeUpdate();
		pstmt.close();
		return reg;	
	}
	
	public static void login(Connection con) throws SQLException{
		System.out.println("Please enter the username and password To login");
		System.out.print("Username: ");
		Scanner sc2 = new Scanner(System.in);
		String uname = sc2.next();
		System.out.println();
		
		System.out.print("Password: ");
		String password = sc2.next();
		System.out.println();
		
		java.sql.Statement stmt = con.createStatement();
		ResultSet rs = stmt.executeQuery("Select * from register");
		while(rs.next()) {
			System.out.println(rs.getInt(1)+" "+rs.getString(2)+" "+rs.getString(3)+" "+rs.getString(4)+" "+rs.getString(5)
			+" "+rs.getString(6));
		}
//		ResultSet rs = stmt.executeQuery("Select user_name, password from registration where user_name="+uname);
//		if(rs.getString("user_name").equals(uname) && rs.getString("password").equals(password)) {
//			System.out.println("You are successfully Logged In !!!!");
//		}else{
//			System.out.println("Invalid Username or Password");
//			login(con);
//		}
		
	}
	
	public static void entryPoint(Connection con) throws SQLException {
		System.out.println("1.Login.\n2.Register.\n");
		int choice = 0;
		Scanner sc4 = new Scanner(System.in);
		do {
			choice = sc4.nextInt();
			switch (choice) {
			case 1:
				login(con);
				break;
			case 2:
				Register acc=null;
				System.out.println("\n Register yourself with us please provide \nfollowing details");
				acc = register(con);
				if(acc!=null)
					login(con);
				break;
			default:
				break;
			}
		} while(choice!=0);
	}
	
	public static void main(String[] args) throws Exception{
	
		Class.forName("com.mysql.cj.jdbc.Driver");
		Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/bankdb","root","manager");
		System.out.println("Welcome to the Banking System !!!!!");
		entryPoint(con);
		
	}

}
