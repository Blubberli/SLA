package de.ws.server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import org.mindrot.jbcrypt.BCrypt;

import java.util.List;

import de.ws.shared.Admin;
import de.ws.shared.Person;
import de.ws.shared.Text;
import de.ws.shared.User;

public class DatabaseStorage {
	private DatabaseStorage() {
	}

	static Connection open() {
		try {
			Class.forName("org.sqlite.JDBC");
			String path = "jdbc:sqlite:users.db";
			return DriverManager.getConnection(path);

		} catch (Exception e) {
			e.printStackTrace();

		}
		return null;
	}

	static void insert(String name2, String passwd2) {

		try (Connection co = open()) {

			String name = name2;
			String passwd = BCrypt.hashpw(passwd2, BCrypt.gensalt());
			String query = "INSERT INTO users (name, passwd) " + "VALUES(?, '" + passwd + "');";
			PreparedStatement pstmt = co.prepareStatement(query);
			pstmt.setString(1, name);
			
			pstmt.execute();
			System.out.println("User inserted");

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	static void delete(String name) {
		try (Connection co = open()) {

			String query = "DELETE FROM users WHERE name=?;";

			PreparedStatement pstmt = co.prepareStatement(query);
			pstmt.setString(1, name);
			pstmt.execute();
			System.out.println("User deleted");

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	static void deleteWord(String word, int id) {
		try (Connection co = open()) {

			String query = "DELETE FROM userWords WHERE word=? AND userId=?;";

			PreparedStatement pstmt = co.prepareStatement(query);
			pstmt.setString(1, word);
			pstmt.setInt(2, id);
			
			pstmt.execute();
			System.out.println("Word deleted");

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	static void saveWords(String word, User user) {
		try (Connection co = open()) {
			int userId = user.getId();
			String query = "INSERT INTO userWords (userId, word) VALUES( ? , ?);";


			PreparedStatement pstmt = co.prepareStatement(query);
			pstmt.setInt(1, userId);
			pstmt.setString(2, word);
			
			pstmt.execute();
			System.out.println("Word is saved");

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	static void saveTexts(Text text, User user) {
		try (Connection co = open()) {
			int userId = user.getId();
			String query = "INSERT INTO userTexts (userId, text, name, difficulty) " + "VALUES(?, ?, ?, ?);";

			PreparedStatement pstmt = co.prepareStatement(query);
			pstmt.setInt(1, userId);
			pstmt.setString(2, text.getText());
			pstmt.setString(3, text.getTitle());
			pstmt.setString(4, text.getLevel());
			
			pstmt.execute();
			System.out.println("Text is saved");

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	static Person select(String name2, String password2) {
		Person person = null;
		if (!verifyUser(name2, password2)) {
			return null;
		}
		try (Connection co = open()) {
			// String query = "SELECT id, name, word FROM users ORDER BY name;";
			String query = "SELECT id FROM users where name = ?;";

			PreparedStatement pstmt = co.prepareStatement(query);
			pstmt.setString(1, name2);
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				int id = rs.getInt("id");
				int userId = id;
				if (name2.equals("admin")) {
					Admin admin = new Admin();
					person = admin;
				} else {
					User user = new User();
					user.setId(userId);
					user.setName(name2);
					setDatabaseWordsForUser(user);
					setDatabaseTextsForUser(user);
					person = user;
				}

			}

			rs.close();
			pstmt.close();
			System.out.println("User selected");

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return person;
	}

	private static void setDatabaseTextsForUser(User user) {
		int id = user.getId();
		List<Text> textlist = new ArrayList<>();
		try (Connection co = open()) {
			String query = "SELECT * FROM userTexts WHERE userId = ?;";

			PreparedStatement pstmt;
			pstmt = co.prepareStatement(query);
			pstmt.setInt(1, id);
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				Text text = new Text();
				text.setText(rs.getString("text"));
				text.setTitle(rs.getString("name"));
				text.setLevel(rs.getString("difficulty"));
				textlist.add(text);
			}

			rs.close();
			pstmt.close();

			user.setTexts(textlist);

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
	}

	static boolean verifyUser(String name2, String passwd2) {
		try (Connection co = open()) {
			String query = "SELECT passwd FROM users WHERE name = ? ;";

			PreparedStatement pstmt = co.prepareStatement(query);
			pstmt.setString(1, name2);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				String passwd = rs.getString("passwd");
				try {
					if (BCrypt.checkpw(passwd2, passwd)) {
						return true;
					}

				} catch (Exception e) {
					if (passwd2.equals(passwd))
						return true;
				}
			}

			System.out.println("User verified");
			rs.close();
			pstmt.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;

	}

	static void setDatabaseWordsForUser(User user) {
		int id = user.getId();
		List<String> wordlist = new ArrayList<>();
		try (Connection co = open()) {
			String query = "SELECT word FROM userWords WHERE userId = ?;";

			PreparedStatement pstmt;
			pstmt = co.prepareStatement(query);
			pstmt.setInt(1, id);
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {

				wordlist.add(rs.getString("word"));
			}

			rs.close();
			pstmt.close();

			user.setWordList(wordlist);

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	static ArrayList<String> getUsersList() {
		ArrayList<String> userList = new ArrayList<>();
		try (Connection co = open()) {
			String query = "SELECT id, name FROM users;";

			PreparedStatement pstmt = co.prepareStatement(query);
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				String name = rs.getString("name");
				userList.add(name);
			}

			rs.close();
			pstmt.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return userList;
	}

}

