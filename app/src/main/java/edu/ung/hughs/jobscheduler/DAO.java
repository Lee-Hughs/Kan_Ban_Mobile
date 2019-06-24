package edu.ung.hughs.jobscheduler;

import android.annotation.SuppressLint;
import android.os.StrictMode;
import android.util.Log;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;

public class DAO {

    private static final String ip = "jobschedulerdb.cz4f7rwct8mz.us-east-1.rds.amazonaws.com";
    private static final String db = "jobschedulerdb";
    private static final String un = "User";
    private static final String pw = "password";
    private Connection con;

    public DAO() {
        try {
            con = connectionclass(un, pw, db, ip);
        } catch (Exception ex) {
            Log.e("Exception", ex.getMessage());
        }
    }

    public int logIn(String username, String password)
    {
        try {
            if ( con == null)
                System.out.println("con == null");
            // Change below query according to your own database.
            String query = "select personID from People where UserName = ? and HashPass = ?";
            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                int personID = rs.getInt(1);
                con.close();
                return personID;
            } else {
                con.close();
                return 0;
            }
        }
        catch(SQLException e)
        {
            Log.e("Connection issue: ", e.getMessage());
            return -1;
        }
    }

    public ArrayList<Board> getBoardList(int personID)
    {
        try {
            String query = "select * from Boards where BoardID in ( select BoardID from BoardLink where PersonID = ?)";
            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setInt(1, personID);
            ResultSet rs = pstmt.executeQuery();
            ArrayList<Board> boards = new ArrayList();
            while(rs.next())
            {
                boards.add(new Board(rs.getInt("BoardID"), rs.getInt("CreatedBy"), rs.getString("Name"), rs.getString("Description"), rs.getDate("DateCreated"), rs.getString("TimeCreated")));
            }
            return boards;
        }
        catch(Exception e)
        {
            Log.e("Exception:", e.getMessage());
        }
        return null;
    }

    public ArrayList<Job> getJobListByBoard(int boardID, String status)
    {
        try {
            String query = "select * from Jobs where BoardID = ? and Status = ?";
            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setInt(1, boardID);
            pstmt.setString(2, status);
            ResultSet rs = pstmt.executeQuery();
            ArrayList<Job> jobs = new ArrayList();
            while(rs.next())
            {   //todo:Fix this to reflect new database structure
                jobs.add(new Job(rs.getInt("JobID"), rs.getString("Name"), rs.getString("Description"), rs.getInt("CreatedBy"), rs.getInt("BoardID"), rs.getDate("DateCreated"), rs.getString("TimeCreated"), rs.getString("Status")));
            }
            return jobs;
        }
        catch(Exception e)
        {
            Log.e("Exception:", e.getMessage());
        }
        return null;
    }

    public boolean addBoard(String name, String desc, int personID, ArrayList<String> statusList)
    {
        int boardID = 0;
        //insert into boards
        try{
            String query = "insert into Boards (Name, Description, CreatedBy) values (?, ?, ?)";
            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setString(1, name);
            pstmt.setString(2, desc);
            pstmt.setInt(3, personID);
            pstmt.executeUpdate();
            //return true;
        }
        catch(SQLException e)
        {
            Log.e("Problem Inserting Board: ", e.getMessage());
            return false;
        }
        //capture boardID
        try{
            String query = "select BoardID from Boards where [Name] = ?";
            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setString(1, name);
            ResultSet rs = pstmt.executeQuery();
            rs.next();
            boardID = rs.getInt(1);
        }
        catch(SQLException e)
        {
            Log.e("Problem fetching boardID: ", e.getMessage());
        }
        //insert into status table
        ArrayList<String> newStatuses = new ArrayList<>();
        newStatuses.addAll(statusList);
        Log.e("List: ", newStatuses.toString());
        try{
            String query = "select [Name] from [Status]";
            PreparedStatement stmt = con.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();
            //rs.next();
            String sql = "insert into Status(Name, CreatedBy) values";
            while(rs.next())
            {
                Log.e("We got here", "We got to line 144, in the while loop, item = " + rs.getString(1));
                String item = rs.getString(1);
                if(statusList.contains(item))
                {
                    Log.e("We got here", "We got to line 148, in the if statement, itm = " + item);
                    newStatuses.remove(item);
                }
            }
            Log.e("List: ", newStatuses.toString());
            for(int i = 0; i < newStatuses.size(); i++)
            {
                sql += "(?, " + personID + "),";
            }
            sql = sql.substring(0,sql.length()-1);
            PreparedStatement pstmt = con.prepareStatement(sql);
            for(int i = 1; i <= newStatuses.size(); i++)
            {
                Log.e("We got here", "We got to line 157 in the for loop, i = " + i + ", and item = " + newStatuses.get(i-1));
                pstmt.setString(i, newStatuses.get(i-1));
            }
            pstmt.executeUpdate();
        }
        catch(SQLException e){
            Log.e("Problem inserting into statusBoard: ", e.getMessage());
            return false;
        }
        try{
            Log.e("List ", statusList.toString());
            String sql = "insert into StatusLink(StatusName, BoardID, CreatedBy) values";
            for(int i = 0; i <statusList.size(); i++)
            {
                sql += "(?, " + boardID + ", " + personID + "),";
            }
            sql = sql.substring(0,sql.length()-1);
            Log.e("SQL Query", sql);
            PreparedStatement pstmt = con.prepareStatement(sql);
            for(int i = 1; i <= statusList.size(); i++)
            {
                pstmt.setString(i, statusList.get(i-1));
            }
            pstmt.executeUpdate();
        }
        catch (SQLException e)
        {
            Log.e("Problem inserting into linking table: ", e.getMessage());
            return false;
        }
        return true;
    }

    public boolean signUp(String firstName, String lastName, String company, String username, String hashedPass)
    {
        try {
            String query = "insert into People (FirstName, LastName, Company, UserName, HashPass) values (?, ?, ?, ?, ?)";
            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setString(1, firstName);
            pstmt.setString(2, lastName);
            if (company != null)
                pstmt.setString(3, company);
            else
                pstmt.setNull(3, Types.NVARCHAR);
            pstmt.setString(4, username);
            pstmt.setString(5, hashedPass);
            pstmt.executeUpdate();
            return true;
        }
        catch(SQLException e)
        {
            Log.e("SQL Exception: ", e.getMessage());
            return false;
        }
    }

    @SuppressLint("NewApi")
    public Connection connectionclass(String user, String password, String database, String server)
    {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        Connection connection = null;
        String ConnectionURL = null;
        try
        {
            Class.forName("net.sourceforge.jtds.jdbc.Driver");
            ConnectionURL = "jdbc:jtds:sqlserver://" + server + ";databaseName=" + database + ";user=" + user + ";password=" + password + ";";
            connection = DriverManager.getConnection(ConnectionURL);
        }
        catch (SQLException se)
        {
            Log.e("error here 1 : ", se.getMessage());
        }
        catch (ClassNotFoundException e)
        {
            Log.e("error here 2 : ", e.getMessage());
        }
        catch (Exception e)
        {
            Log.e("error here 3 : ", e.getMessage());
        }
        return connection;
    }
}
