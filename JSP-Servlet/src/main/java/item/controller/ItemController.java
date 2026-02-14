package item.controller;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

import javax.annotation.Resource;
import javax.servlet.ServletException;
//import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import item.model.Item;
import item.model.ItemDetails;
import item.model.ItemWithDetails;
import item.service.ItemService;
import item.service.impl.ItemServiceImpl;
//http://localhost:8080/Servlet-JSP/ItemController?name=&price=&totalnumber=
// Item Controller     USER --> *

//http://localhost:8080/Servlet-JSP/ItemController
//http://localhost:8080/Servlet-JSP/ItemController?action=abc
//http://localhost:8080/Servlet-JSP/ItemController?action=add-item&name=&price=&totalnumber=
//http://localhost:8080/Servlet-JSP/ItemController?action=remove-item&id=
//http://localhost:8080/Servlet-JSP/ItemController?action=update-item&id=&name=&price=&totalnumber=
//http://localhost:8080/Servlet-JSP/ItemController?action=show-item&id=1
//http://localhost:8080/Servlet-JSP/ItemController?action=show-items

//@WebServlet("/ItemController")
public class ItemController extends HttpServlet {
	

	@Resource(name = "jdbc/connection")
	private DataSource dataSource;
	
	// Refactored: Extract service initialization to prevent duplication
	private ItemService getItemService() {
		return new ItemServiceImpl(dataSource);
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getParameter("action");
		
		if (Objects.isNull(action)) {
			action = "show-items";
		}
		
		switch(action) {
			case "add-item":
					addItem(request, response);
				break;
			case "add-item-details":
					showAddItemDetails(request, response);
				break;
			case "save-item-details":
					saveItemDetails(request, response);
				break;
			case "delete-item-details":
					deleteItemDetails(request, response);
				break;
			case "remove-item":
			case "delete-item":
					removeItem(request, response);
				break;
			case "update-item":
					updateItem(request, response);
				break;
			case "show-item":
					showItem(request, response);
				break;
			case "show-items":
					showItems(request, response);
				break;
			default:
				showItems(request, response);
		}
		
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

	private void showItems(HttpServletRequest request, HttpServletResponse response) {
		 try {
			ItemService itemService = getItemService();
			List<ItemWithDetails> itemsWithDetails = itemService.getItemsWithDetails();
			request.setAttribute("allItemsWithDetails", itemsWithDetails);
			request.getRequestDispatcher("/item/show-items.jsp").forward(request, response);
		} catch (Exception e) {
			System.out.println("ex => " + e.getMessage());
			e.printStackTrace();
		}
	}

	private void showItem(HttpServletRequest request, HttpServletResponse response) {
		try {
			ItemService itemService = getItemService();
			Long id = Long.parseLong(request.getParameter("id"));
			ItemWithDetails itemWithDetails = itemService.getItemWithDetails(id);
			if (itemWithDetails == null) {
				showItems(request, response);
				return;
			}
			request.setAttribute("item", itemWithDetails.getItem());
			request.setAttribute("itemWithDetails", itemWithDetails);
			request.getRequestDispatcher("/item/update-item.jsp").forward(request, response);
		} catch (Exception e) {
			System.out.println("ex => " + e.getMessage());
			e.printStackTrace();
		}
	}

	private void showAddItemDetails(HttpServletRequest request, HttpServletResponse response) {
		try {
			ItemService itemService = getItemService();
			Long id = Long.parseLong(request.getParameter("id"));
			Item item = itemService.getItem(id);
			if (item == null || itemService.hasItemDetails(id)) {
				showItems(request, response);
				return;
			}
			request.setAttribute("item", item);
			request.getRequestDispatcher("/item/add-item-details.jsp").forward(request, response);
		} catch (Exception e) {
			System.out.println("ex => " + e.getMessage());
			e.printStackTrace();
		}
	}

	private void saveItemDetails(HttpServletRequest request, HttpServletResponse response) {
		try {
			ItemService itemService = getItemService();
			Long itemId = Long.parseLong(request.getParameter("id"));
			if (itemService.hasItemDetails(itemId)) {
				showItems(request, response);
				return;
			}
			String desc = request.getParameter("desc");
			String issueDateStr = request.getParameter("issue_date");
			String expiryDateStr = request.getParameter("expiry_date");
			java.util.Date issueDate = parseDate(issueDateStr);
			java.util.Date expiryDate = parseDate(expiryDateStr);
			ItemDetails details = new ItemDetails(itemId, desc, issueDate, expiryDate);
			if (itemService.createItemDetails(details)) {
				request.setAttribute("successMessage", "Item details added successfully");
			}
			showItems(request, response);
		} catch (Exception e) {
			System.out.println("ex => " + e.getMessage());
			e.printStackTrace();
			showItems(request, response);
		}
	}

	private void deleteItemDetails(HttpServletRequest request, HttpServletResponse response) {
		try {
			ItemService itemService = getItemService();
			Long id = Long.parseLong(request.getParameter("id"));
			itemService.deleteItemDetailsByItemId(id);
			request.setAttribute("successMessage", "Item details deleted successfully");
			showItems(request, response);
		} catch (Exception e) {
			System.out.println("ex => " + e.getMessage());
			e.printStackTrace();
			showItems(request, response);
		}
	}

	private static java.util.Date parseDate(String s) {
		if (s == null || s.trim().isEmpty()) return null;
		try {
			return new java.sql.Date(java.sql.Date.valueOf(s.trim()).getTime());
		} catch (Exception e) {
			return null;
		}
	}

	private void updateItem(HttpServletRequest request, HttpServletResponse response) {
		try {
			ItemService itemService = getItemService();
			Long id = Long.parseLong(request.getParameter("id"));
			String name = request.getParameter("name");
			Double price = Double.parseDouble(request.getParameter("price"));
			Integer totalNumber = Integer.parseInt(request.getParameter("totalNumber"));
			String desc = request.getParameter("desc");
			String issueDateStr = request.getParameter("issue_date");
			String expiryDateStr = request.getParameter("expiry_date");

			Item existingItem = itemService.getItemByName(name);
			if (existingItem != null && !existingItem.getId().equals(id)) {
				request.setAttribute("errorMessage", "Item name already exists in the system");
				ItemWithDetails iwd = itemService.getItemWithDetails(id);
				request.setAttribute("item", iwd != null ? iwd.getItem() : itemService.getItem(id));
				request.setAttribute("itemWithDetails", iwd);
				request.getRequestDispatcher("/item/update-item.jsp").forward(request, response);
				return;
			}

			Item item = new Item(id, name, price, totalNumber);
			Boolean isItemUpdated = itemService.updateItem(item);
			if (!isItemUpdated) {
				request.setAttribute("errorMessage", "Failed to update item");
				ItemWithDetails iwd = itemService.getItemWithDetails(id);
				request.setAttribute("item", item);
				request.setAttribute("itemWithDetails", iwd);
				request.getRequestDispatcher("/item/update-item.jsp").forward(request, response);
				return;
			}

			// Update or create ITEM_DETAILS (Level 2: update includes both ITEM and ITEM_DETAILS)
			java.util.Date issueDate = parseDate(issueDateStr);
			java.util.Date expiryDate = parseDate(expiryDateStr);
			if (itemService.hasItemDetails(id)) {
				ItemDetails details = new ItemDetails(id, desc, issueDate, expiryDate);
				itemService.updateItemDetails(details);
			} else if (desc != null || issueDate != null || expiryDate != null) {
				ItemDetails details = new ItemDetails(id, desc, issueDate, expiryDate);
				itemService.createItemDetails(details);
			}

			request.setAttribute("successMessage", "Item updated successfully");
			showItems(request, response);
		} catch (Exception e) {
			System.out.println("ex => " + e.getMessage());
			e.printStackTrace();
			request.setAttribute("errorMessage", "An error occurred: " + e.getMessage());
			try {
				Long id = Long.parseLong(request.getParameter("id"));
				ItemService itemService = getItemService();
				ItemWithDetails iwd = itemService.getItemWithDetails(id);
				request.setAttribute("item", iwd != null ? iwd.getItem() : itemService.getItem(id));
				request.setAttribute("itemWithDetails", iwd);
				request.getRequestDispatcher("/item/update-item.jsp").forward(request, response);
			} catch (Exception ex) {
				showItems(request, response);
			}
		}
	}

	private void removeItem(HttpServletRequest request, HttpServletResponse response) {
		try {
			ItemService itemService = getItemService();
			Long id = Long.parseLong(request.getParameter("id"));
			// Level 2: Remove from ITEM_DETAILS first, then soft-delete ITEM
			itemService.deleteItemDetailsByItemId(id);
			Boolean isItemDeleted = itemService.removeItem(id);
			if (isItemDeleted) {
				request.setAttribute("successMessage", "Item deleted successfully");
			} else {
				request.setAttribute("errorMessage", "Failed to delete item");
			}
			showItems(request, response);
		} catch (Exception e) {
			System.out.println("ex => " + e.getMessage());
			e.printStackTrace();
			request.setAttribute("errorMessage", "An error occurred: " + e.getMessage());
			showItems(request, response);
		}
	}

	private void addItem(HttpServletRequest request, HttpServletResponse response) {
		try {
			ItemService itemService = getItemService();
			
			String name = request.getParameter("name");
			Double price = Double.parseDouble(request.getParameter("price"));
			Integer totalNumber = Integer.parseInt(request.getParameter("totalNumber"));
			
			// Check if item name already exists
			Item existingItem = itemService.getItemByName(name);
			if (existingItem != null) {
				request.setAttribute("errorMessage", "Item name already exists in the system");
				request.getRequestDispatcher("/item/add-item.jsp").forward(request, response);
				return;
			}
			
			Item item = new Item(name, price, totalNumber);
			Boolean isItemCreated = itemService.createItem(item);
			
			if (isItemCreated) {
				request.setAttribute("successMessage", "Item added successfully");
				showItems(request, response);
			} else {
				request.setAttribute("errorMessage", "Failed to add item");
				request.getRequestDispatcher("/item/add-item.jsp").forward(request, response);
			}
		} catch (Exception e) {
			System.out.println("ex => " + e.getMessage());
			e.printStackTrace();
			request.setAttribute("errorMessage", "An error occurred: " + e.getMessage());
			try {
				request.getRequestDispatcher("/item/add-item.jsp").forward(request, response);
			} catch (Exception ex) {
				showItems(request, response);
			}
		}
		
	}

	

}