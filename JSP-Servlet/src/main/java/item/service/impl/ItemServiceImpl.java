package item.service.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.sql.DataSource;

import item.model.Item;
import item.service.ItemService;

public class ItemServiceImpl implements ItemService {

	private DataSource dataSource;
	
	public ItemServiceImpl(DataSource dataSource) {
		this.dataSource = dataSource;
	}
	
	@Override
	public List<Item> getItems() {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		
		try {
			connection = dataSource.getConnection();
			// Using PreparedStatement to prevent SQL injection
			// Only get items that are not deleted (soft delete)
			String query = "SELECT * FROM HR.ITEM WHERE (DELETED IS NULL OR DELETED = 0) ORDER BY ID";
			preparedStatement = connection.prepareStatement(query);
			ResultSet resultSet = preparedStatement.executeQuery();
			
			List<Item> items = new ArrayList<Item>();
			while (resultSet.next()) {
				Item item = new Item(
						resultSet.getLong("id"),
						resultSet.getString("name"),
						resultSet.getDouble("price"),
						resultSet.getInt("TOTAL_NUMBER")
				);
				items.add(item);
			}
			
			return items;
		} catch (Exception exception) {
			System.out.println("ex => " + exception.getMessage());
			exception.printStackTrace();
		} finally {
			try {
				if(Objects.nonNull(preparedStatement)) {
					preparedStatement.close();
				}
				
				if(Objects.nonNull(connection)) {
					connection.close();
				}
			} catch (SQLException exception) {
				System.out.println("ex => " + exception.getMessage());
			}
		}
		
		return new ArrayList<Item>();
	}

	@Override
	public Item getItem(Long id) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		
		try {
			connection = dataSource.getConnection();
			// Using PreparedStatement to prevent SQL injection
			String query = "SELECT * FROM HR.ITEM WHERE ID = ?";
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setLong(1, id);
			ResultSet resultSet = preparedStatement.executeQuery();
			
			if (resultSet.next()) {
				return new Item(
						resultSet.getLong("id"),
						resultSet.getString("name"),
						resultSet.getDouble("price"),
						resultSet.getInt("TOTAL_NUMBER")
				);
			}
			
		} catch (Exception exception) {
			System.out.println("ex => " + exception.getMessage());
			exception.printStackTrace();
		} finally {
			try {
				if(Objects.nonNull(preparedStatement)) {
					preparedStatement.close();
				}
				
				if(Objects.nonNull(connection)) {
					connection.close();
				}
			} catch (SQLException exception) {
				System.out.println("ex => " + exception.getMessage());
			}
		}
		
		return null;
	}

	@Override
	public Boolean createItem(Item item) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		
		try {
			connection = dataSource.getConnection();
			// Using PreparedStatement to prevent SQL injection
			String query = "INSERT INTO HR.ITEM (NAME, PRICE, TOTAL_NUMBER, DELETED) VALUES(?, ?, ?, 0)";
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, item.getName());
			preparedStatement.setDouble(2, item.getPrice());
			preparedStatement.setInt(3, item.getTotalNumber());
			
			int rowsAffected = preparedStatement.executeUpdate();
			
			return rowsAffected > 0;
		} catch (Exception exception) {
			System.out.println("ex => " + exception.getMessage());
			exception.printStackTrace();
		} finally {
			try {
				if(Objects.nonNull(preparedStatement)) {
					preparedStatement.close();
				}
				
				if(Objects.nonNull(connection)) {
					connection.close();
				}
			} catch (SQLException exception) {
				System.out.println("ex => " + exception.getMessage());
			}
		}
		
		return false;
	}

	@Override
	public Boolean updateItem(Item item) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		
		try {
			connection = dataSource.getConnection();
			// Using PreparedStatement to prevent SQL injection
			String query = "UPDATE HR.ITEM SET NAME = ?, PRICE = ?, TOTAL_NUMBER = ? WHERE ID = ?";
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, item.getName());
			preparedStatement.setDouble(2, item.getPrice());
			preparedStatement.setInt(3, item.getTotalNumber());
			preparedStatement.setLong(4, item.getId());
			
			int rowsAffected = preparedStatement.executeUpdate();
			
			return rowsAffected > 0;
		} catch (Exception exception) {
			System.out.println("ex => " + exception.getMessage());
			exception.printStackTrace();
		} finally {
			try {
				if(Objects.nonNull(preparedStatement)) {
					preparedStatement.close();
				}
				
				if(Objects.nonNull(connection)) {
					connection.close();
				}
			} catch (SQLException exception) {
				System.out.println("ex => " + exception.getMessage());
			}
		}
		
		return false;
	}

	@Override
	public Boolean removeItem(Long id) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		
		try {
			connection = dataSource.getConnection();
			// Soft delete: Mark as deleted instead of removing from database
			// Using PreparedStatement to prevent SQL injection
			String query = "UPDATE HR.ITEM SET DELETED = 1 WHERE ID = ?";
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setLong(1, id);
			
			int rowsAffected = preparedStatement.executeUpdate();
			
			return rowsAffected > 0;
		} catch (Exception exception) {
			System.out.println("ex => " + exception.getMessage());
			exception.printStackTrace();
		} finally {
			try {
				if(Objects.nonNull(preparedStatement)) {
					preparedStatement.close();
				}
				
				if(Objects.nonNull(connection)) {
					connection.close();
				}
			} catch (SQLException exception) {
				System.out.println("ex => " + exception.getMessage());
			}
		}
		
		return false;
	}

	@Override
	public Item getItemByName(String name) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		
		try {
			connection = dataSource.getConnection();
			// Using PreparedStatement to prevent SQL injection
			String query = "SELECT * FROM HR.ITEM WHERE UPPER(NAME) = UPPER(?) AND (DELETED IS NULL OR DELETED = 0)";
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, name);
			ResultSet resultSet = preparedStatement.executeQuery();
			
			if (resultSet.next()) {
				return new Item(
						resultSet.getLong("id"),
						resultSet.getString("name"),
						resultSet.getDouble("price"),
						resultSet.getInt("TOTAL_NUMBER")
				);
			}
			
		} catch (Exception exception) {
			System.out.println("ex => " + exception.getMessage());
			exception.printStackTrace();
		} finally {
			try {
				if(Objects.nonNull(preparedStatement)) {
					preparedStatement.close();
				}
				
				if(Objects.nonNull(connection)) {
					connection.close();
				}
			} catch (SQLException exception) {
				System.out.println("ex => " + exception.getMessage());
			}
		}
		
		return null;
	}

}