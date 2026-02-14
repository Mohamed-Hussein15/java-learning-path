# Application URLs - Frontend and Backend Access

## Base Information
- **Server**: Tomcat (default port: 8080)
- **Context Path**: `/Servlet-JSP`
- **Base URL**: `http://localhost:8080/Servlet-JSP`

---

## 🎨 Frontend URLs (User Interface)

### Main Pages

#### 1. **Login / Sign Up (Level 3)**
```
http://localhost:8080/Servlet-JSP/AuthController
http://localhost:8080/Servlet-JSP/AuthController?action=signup
```
- **Description**: Login and account creation. Session and cookies applied (Level 4).
- **Logout**: `AuthController?action=logout` (or use Logout button on items page).

#### 2. **Show All Items (Main Page)** — *requires login*
```
http://localhost:8080/Servlet-JSP/ItemController?action=show-items
```
- **Description**: Displays all items with columns: ID, NAME, PRICE, TOTAL_NUMBER, DESC, ISSUE_DATE, EXPIRY_DATE (Level 2: JOIN with ITEM_DETAILS)
- **Features**: View items, Update, Delete, Add Item, **Add Item Details** / **Delete Item Details** (per row), **Logout** when logged in
- **Method**: GET

#### 3. **Add Item Page**
```
http://localhost:8080/Servlet-JSP/item/add-item.jsp
```
**OR via controller:**
```
http://localhost:8080/Servlet-JSP/ItemController?action=show-items
```
(Then click "Add Item" button)

- **Description**: Form to add a new item
- **Fields**: Name, Price, Total Number
- **Method**: POST (submits to ItemController)

#### 4. **Update Item Page**
```
http://localhost:8080/Servlet-JSP/ItemController?action=show-item&id=1
```
- **Description**: Form to update an existing item
- **Parameters**: `id` - The ID of the item to update
- **Fields**: Name, Price, Total Number, Desc, Issue Date, Expiry Date (Level 2: ITEM + ITEM_DETAILS)
- **Method**: GET (to load), POST (to submit)

#### 5. **Add Item Details (Level 2)**
```
http://localhost:8080/Servlet-JSP/ItemController?action=add-item-details&id=1
```
- **Description**: Form to add details (desc, issue_date, expiry_date) for an item that has no details yet. Button visible only when row has no ITEM_DETAILS.
- **Delete Item Details**: `ItemController?action=delete-item-details&id=1` — button visible only when row has ITEM_DETAILS.

---

## ⚙️ Backend URLs (API Endpoints)

### ItemController Servlet Endpoints

All backend operations go through the `ItemController` servlet:

#### Base URL
```
http://localhost:8080/Servlet-JSP/ItemController
```

### Available Actions

#### 1. **Show All Items**
```
GET http://localhost:8080/Servlet-JSP/ItemController?action=show-items
```
- **Action**: `show-items`
- **Method**: GET
- **Response**: Forwards to `show-items.jsp` with items list

#### 2. **Show Single Item (for Update)**
```
GET http://localhost:8080/Servlet-JSP/ItemController?action=show-item&id=1
```
- **Action**: `show-item`
- **Parameters**: `id` (required) - Item ID
- **Method**: GET
- **Response**: Forwards to `update-item.jsp` with item data

#### 3. **Add Item**
```
POST http://localhost:8080/Servlet-JSP/ItemController?action=add-item
```
- **Action**: `add-item`
- **Parameters**: 
  - `name` (required) - Item name
  - `price` (required) - Item price
  - `totalNumber` (required) - Total number
- **Method**: POST
- **Response**: 
  - Success: Shows items list with "Item added successfully" popup
  - Error: Shows error popup (e.g., duplicate name)

#### 4. **Update Item**
```
POST http://localhost:8080/Servlet-JSP/ItemController?action=update-item
```
- **Action**: `update-item`
- **Parameters**: 
  - `id` (required) - Item ID
  - `name` (required) - Item name
  - `price` (required) - Item price
  - `totalNumber` (required) - Total number
- **Method**: POST
- **Response**: 
  - Success: Shows items list with "Item updated successfully" popup
  - Error: Shows error popup (e.g., duplicate name)

#### 5. **Delete Item (Soft Delete)**
```
GET http://localhost:8080/Servlet-JSP/ItemController?action=remove-item&id=1
```
- **Action**: `remove-item`
- **Parameters**: `id` (required) - Item ID to delete
- **Method**: GET
- **Response**: Shows items list with "Item deleted successfully" popup
- **Note**: This is a soft delete - item is marked as deleted but not removed from database

---

## 📋 Quick Reference - All URLs

### Frontend Pages
| Page | URL |
|------|-----|
| **Main Page (Show Items)** | `http://localhost:8080/Servlet-JSP/ItemController?action=show-items` |
| **Add Item** | `http://localhost:8080/Servlet-JSP/item/add-item.jsp` |
| **Update Item (ID=1)** | `http://localhost:8080/Servlet-JSP/ItemController?action=show-item&id=1` |

### Backend Operations
| Operation | URL | Method |
|-----------|-----|--------|
| **List Items** | `http://localhost:8080/Servlet-JSP/ItemController?action=show-items` | GET |
| **View Item** | `http://localhost:8080/Servlet-JSP/ItemController?action=show-item&id=1` | GET |
| **Add Item** | `http://localhost:8080/Servlet-JSP/ItemController?action=add-item&name=Item1&price=10.5&totalNumber=5` | POST |
| **Update Item** | `http://localhost:8080/Servlet-JSP/ItemController?action=update-item&id=1&name=Item1&price=10.5&totalNumber=5` | POST |
| **Delete Item** | `http://localhost:8080/Servlet-JSP/ItemController?action=remove-item&id=1` | GET |

---

## 🚀 Getting Started

### Step 1: Start Tomcat Server
Make sure Tomcat is running on port 8080

### Step 2: Login (Level 3 & 4)
Open your browser and navigate to:
```
http://localhost:8080/Servlet-JSP/
```
or
```
http://localhost:8080/Servlet-JSP/AuthController
```
- If not logged in, you will be redirected to **Login**. After login you go to the main items page.
- **Sign up**: Use "Sign up" link to create an account (email must be unique).
- **Logout**: Use the "Logout" button on the items page (visible only when logged in). Logout clears session and cookies and redirects to login.

### Step 3: Access the Main Page (after login)
```
http://localhost:8080/Servlet-JSP/ItemController?action=show-items
```

### Step 3: Use the Application
- **View Items**: Already on the main page
- **Add Item**: Click "Add Item" button
- **Update Item**: Click "Update" button next to any item
- **Delete Item**: Click "Delete" button next to any item

---

## 📝 Example URLs with Parameters

### Adding an Item (via form submission)
```
POST http://localhost:8080/Servlet-JSP/ItemController
Parameters:
  action=add-item
  name=Laptop
  price=999.99
  totalNumber=10
```

### Updating an Item (via form submission)
```
POST http://localhost:8080/Servlet-JSP/ItemController
Parameters:
  action=update-item
  id=1
  name=Laptop Pro
  price=1299.99
  totalNumber=5
```

### Deleting an Item
```
GET http://localhost:8080/Servlet-JSP/ItemController?action=remove-item&id=1
```

---

## 🔍 Testing URLs

You can test the backend directly using these URLs in your browser (GET requests only):

1. **Show all items**: 
   ```
   http://localhost:8080/Servlet-JSP/ItemController?action=show-items
   ```

2. **Show item with ID 1**: 
   ```
   http://localhost:8080/Servlet-JSP/ItemController?action=show-item&id=1
   ```

3. **Delete item with ID 1**: 
   ```
   http://localhost:8080/Servlet-JSP/ItemController?action=remove-item&id=1
   ```

**Note**: POST operations (add/update) must be done through the forms, not directly via browser URL.

---

## 🌐 If Using Different Port

If your Tomcat is running on a different port (e.g., 9090), replace `8080` with your port:

```
http://localhost:9090/Servlet-JSP/ItemController?action=show-items
```

---

## 📌 Important Notes

1. **Default Port**: Tomcat default is `8080`, but it might be different on your system
2. **Context Path**: The context path `/Servlet-JSP` must match your deployment folder name in Tomcat
3. **POST Requests**: Add and Update operations use POST method and must be submitted through forms
4. **GET Requests**: Show and Delete operations can be accessed directly via browser URL
5. **Soft Delete**: Delete operation removes row from ITEM_DETAILS first (if any), then marks ITEM as deleted (not removed from DB).
6. **Authentication**: ItemController is protected; unauthenticated users are redirected to login. Run `users_table.sql` and `item_details_table.sql` for Level 2 & 3 DB setup.


