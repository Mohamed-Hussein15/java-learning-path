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
import item.model.ItemDetails;
import item.model.ItemWithDetails;
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
	public List<ItemWithDetails> getItemsWithDetails() {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		try {
			connection = dataSource.getConnection();
			String query = "SELECT i.ID, i.NAME, i.PRICE, i.TOTAL_NUMBER, d.ID AS DETAIL_ID, d.ITEM_ID, d.\"desc\" AS ITEM_DESC, d.ISSUE_DATE, d.EXPIRY_DATE "
					+ "FROM HR.ITEM i LEFT JOIN HR.ITEM_DETAILS d ON i.ID = d.ITEM_ID "
					+ "WHERE (i.DELETED IS NULL OR i.DELETED = 0) ORDER BY i.ID";
			preparedStatement = connection.prepareStatement(query);
			ResultSet rs = preparedStatement.executeQuery();
			List<ItemWithDetails> list = new ArrayList<>();
			while (rs.next()) {
				Item item = new Item(rs.getLong("ID"), rs.getString("NAME"), rs.getDouble("PRICE"), rs.getInt("TOTAL_NUMBER"));
				ItemDetails details = null;
				long detailId = rs.getLong("DETAIL_ID");
				if (!rs.wasNull()) {
					details = new ItemDetails();
					details.setId(detailId);
					details.setItemId(rs.getLong("ITEM_ID"));
					details.setDescription(rs.getString("ITEM_DESC"));
					java.sql.Date issueDate = rs.getDate("ISSUE_DATE");
					details.setIssueDate(issueDate != null ? new java.util.Date(issueDate.getTime()) : null);
					java.sql.Date expiryDate = rs.getDate("EXPIRY_DATE");
					details.setExpiryDate(expiryDate != null ? new java.util.Date(expiryDate.getTime()) : null);
				}
				list.add(new ItemWithDetails(item, details));
			}
			return list;
		} catch (Exception e) {
			System.out.println("ex => " + e.getMessage());
			e.printStackTrace();
		} finally {
			closeQuietly(preparedStatement, connection);
		}
		return new ArrayList<>();
	}

	@Override
	public ItemWithDetails getItemWithDetails(Long itemId) {
		Item item = getItem(itemId);
		if (item == null) return null;
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		try {
			connection = dataSource.getConnection();
			String query = "SELECT ID, ITEM_ID, \"desc\" AS ITEM_DESC, ISSUE_DATE, EXPIRY_DATE FROM HR.ITEM_DETAILS WHERE ITEM_ID = ?";
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setLong(1, itemId);
			ResultSet rs = preparedStatement.executeQuery();
			ItemDetails details = null;
			if (rs.next()) {
				details = new ItemDetails();
				details.setId(rs.getLong("ID"));
				details.setItemId(rs.getLong("ITEM_ID"));
				details.setDescription(rs.getString("ITEM_DESC"));
				java.sql.Date issueDate = rs.getDate("ISSUE_DATE");
				details.setIssueDate(issueDate != null ? new java.util.Date(issueDate.getTime()) : null);
				java.sql.Date expiryDate = rs.getDate("EXPIRY_DATE");
				details.setExpiryDate(expiryDate != null ? new java.util.Date(expiryDate.getTime()) : null);
			}
			return new ItemWithDetails(item, details);
		} catch (Exception e) {
			System.out.println("ex => " + e.getMessage());
			e.printStackTrace();
		} finally {
			closeQuietly(preparedStatement, connection);
		}
		return new ItemWithDetails(item, null);
	}

	@Override
	public boolean hasItemDetails(Long itemId) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		try {
			connection = dataSource.getConnection();
			String query = "SELECT 1 FROM HR.ITEM_DETAILS WHERE ITEM_ID = ?";
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setLong(1, itemId);
			ResultSet rs = preparedStatement.executeQuery();
			return rs.next();
		} catch (Exception e) {
			System.out.println("ex => " + e.getMessage());
		} finally {
			closeQuietly(preparedStatement, connection);
		}
		return false;
	}

	@Override
	public Boolean createItemDetails(ItemDetails details) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		try {
			connection = dataSource.getConnection();
			String query = "INSERT INTO HR.ITEM_DETAILS (ITEM_ID, \"desc\", ISSUE_DATE, EXPIRY_DATE) VALUES (?, ?, ?, ?)";
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setLong(1, details.getItemId());
			preparedStatement.setString(2, details.getDescription());
			preparedStatement.setDate(3, details.getIssueDate() != null ? new java.sql.Date(details.getIssueDate().getTime()) : null);
			preparedStatement.setDate(4, details.getExpiryDate() != null ? new java.sql.Date(details.getExpiryDate().getTime()) : null);
			return preparedStatement.executeUpdate() > 0;
		} catch (Exception e) {
			System.out.println("ex => " + e.getMessage());
			e.printStackTrace();
		} finally {
			closeQuietly(preparedStatement, connection);
		}
		return false;
	}

	@Override
	public Boolean deleteItemDetailsByItemId(Long itemId) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		try {
			connection = dataSource.getConnection();
			String query = "DELETE FROM HR.ITEM_DETAILS WHERE ITEM_ID = ?";
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setLong(1, itemId);
			return preparedStatement.executeUpdate() >= 0;
		} catch (Exception e) {
			System.out.println("ex => " + e.getMessage());
			e.printStackTrace();
		} finally {
			closeQuietly(preparedStatement, connection);
		}
		return false;
	}

	@Override
	public Boolean updateItemDetails(ItemDetails details) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		try {
			connection = dataSource.getConnection();
			String query = "UPDATE HR.ITEM_DETAILS SET \"desc\" = ?, ISSUE_DATE = ?, EXPIRY_DATE = ? WHERE ITEM_ID = ?";
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, details.getDescription());
			preparedStatement.setDate(2, details.getIssueDate() != null ? new java.sql.Date(details.getIssueDate().getTime()) : null);
			preparedStatement.setDate(3, details.getExpiryDate() != null ? new java.sql.Date(details.getExpiryDate().getTime()) : null);
			preparedStatement.setLong(4, details.getItemId());
			return preparedStatement.executeUpdate() > 0;
		} catch (Exception e) {
			System.out.println("ex => " + e.getMessage());
			e.printStackTrace();
		} finally {
			closeQuietly(preparedStatement, connection);
		}
		return false;
	}

	private void closeQuietly(PreparedStatement ps, Connection conn) {
		try {
			if (ps != null) ps.close();
			if (conn != null) conn.close();
		} catch (SQLException e) {
			System.out.println("ex => " + e.getMessage());
		}
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