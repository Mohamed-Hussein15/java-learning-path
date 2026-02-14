# Level 2, 3 & 4 Implementation Summary

This document summarizes the implementation of **Level 2** (ITEM_DETAILS, UI buttons), **Level 3** (Authentication: Login & Signup), and **Level 4** (Session, Cookies, Logout) from the Project Tasks Specification.

---

## Level 2 – Item Details & UI

### 1. ITEM_DETAILS table
- **SQL file**: `src/item_details_table.sql`
- **Columns**: `id`, `item_id` (UNIQUE FK to ITEM), `"desc"`, `issue_date`, `expiry_date`
- **Relationship**: One-to-One with ITEM (each ITEM has at most one ITEM_DETAILS row)

### 2. Model & service
- **ItemDetails** (`item.model.ItemDetails`): model for desc, issueDate, expiryDate
- **ItemWithDetails** (`item.model.ItemWithDetails`): DTO combining Item + optional ItemDetails for list/update
- **ItemService** extended with:
  - `getItemsWithDetails()` – uses **JOIN** (LEFT JOIN ITEM_DETAILS)
  - `getItemWithDetails(Long itemId)`, `hasItemDetails(Long itemId)`
  - `createItemDetails()`, `deleteItemDetailsByItemId()`, `updateItemDetails()`

### 3. show-items.jsp (load-items)
- New columns: **DESC**, **ISSUE_DATE**, **EXPIRY_DATE** (from JOIN)
- **Add Item Details** button: visible only when the row has **no** ITEM_DETAILS
- **Delete Item Details** button: visible only when the row **has** ITEM_DETAILS

### 4. Delete / Update behavior
- **Delete item**: Removes row from ITEM_DETAILS first, then soft-deletes ITEM (removed from screen only, not from DB).
- **Update item**: Update form includes **name, price, totalNumber** and **desc, issue_date, expiry_date**. Both ITEM and ITEM_DETAILS are updated (or details created if missing).

### 5. New pages / actions
- **Add Item Details**: `ItemController?action=add-item-details&id=<itemId>` → `add-item-details.jsp` (form: desc, issue_date, expiry_date)
- **Save details**: `action=save-item-details`
- **Delete Item Details**: `ItemController?action=delete-item-details&id=<itemId>`

---

## Level 3 – Authentication (Login & Signup)

### 1. USERS table
- **SQL file**: `src/users_table.sql`
- **Columns**: `id`, `name`, `email`, `password`
- **UNIQUE** constraint on `email`

### 2. User model & service
- **User** (`user.model.User`): id, name, email, password
- **UserService** / **UserServiceImpl**: `findByEmail`, `findById`, `createUser`, `validateUser` (all with PreparedStatement)

### 3. AuthController
- **Login**: POST email + password → success: main page (show-items); failure: redirect to login with message *"Invalid username or password"*
- **Signup**: POST name, email, password → success: redirect to login (or main) with success message; failure: redirect to signup with validation message (e.g. email already exists)

### 4. Pages
- **Login**: `/auth/login.jsp` (served by `AuthController`)
- **Signup**: `/auth/signup.jsp` (`AuthController?action=signup`)

---

## Level 4 – Session and Cookies

### 1. Session
- On login: `session.setAttribute("user", user)` and `session.setAttribute("loggedIn", true)`
- On logout: session attributes removed and `session.invalidate()`

### 2. Cookies
- On login: cookie `userEmail` set (optional for “remember” behavior)
- On logout: **all cookies** cleared (maxAge=0, path="/")

### 3. Logout
- **Logout button**: Shown on the main items page **only when the user is logged in**
- **Logout action**: Redirect to login; set session to false / remove session attributes; remove cookies (as above)

### 4. Protected access
- **AuthFilter** applied to `/ItemController`: if user is not logged in, redirect to `AuthController` (login)
- **index.jsp**: Redirects to login or to show-items depending on login state

---

## Database setup order

1. Run `src/database_setup.sql` (ITEM table with DELETED column).
2. Run `src/item_details_table.sql` (ITEM_DETAILS table).
3. Run `src/users_table.sql` (USERS table).

---

## Files added/updated

### New files
- `src/item_details_table.sql`
- `src/users_table.sql`
- `src/main/java/item/model/ItemDetails.java`
- `src/main/java/item/model/ItemWithDetails.java`
- `src/main/java/user/model/User.java`
- `src/main/java/user/service/UserService.java`
- `src/main/java/user/service/impl/UserServiceImpl.java`
- `src/main/java/user/controller/AuthController.java`
- `src/main/java/user/filter/AuthFilter.java`
- `src/main/webapp/auth/login.jsp`
- `src/main/webapp/auth/signup.jsp`
- `src/main/webapp/item/add-item-details.jsp`
- `src/main/webapp/index.jsp`

### Modified files
- `ItemService.java` – new methods for details and JOIN
- `ItemServiceImpl.java` – getItemsWithDetails (JOIN), item details CRUD, closeQuietly
- `ItemController.java` – showItemsWithDetails, add/delete/save item details, update item+details, delete flow (details then item)
- `show-items.jsp` – columns desc/issue_date/expiry_date, Add/Delete Item Details buttons, Logout when logged in
- `update-item.jsp` – desc, issue_date, expiry_date fields; uses ItemWithDetails
- `web.xml` – AuthController servlet, AuthFilter, filter-mapping for ItemController, welcome-file index.jsp
- `APPLICATION_URLS.md` – auth URLs, item details, logout, DB notes

---

## Quick test checklist

**Level 2**
- [ ] Run `item_details_table.sql` and `users_table.sql`
- [ ] Main page shows DESC, ISSUE_DATE, EXPIRY_DATE columns
- [ ] “Add Item Details” only on rows without details; “Delete Item Details” only on rows with details
- [ ] Add details → save → row shows details; Delete details → row shows empty details
- [ ] Update item with details: change desc/dates and save; both ITEM and ITEM_DETAILS updated
- [ ] Delete item: row disappears; ITEM_DETAILS deleted, ITEM soft-deleted in DB

**Level 3**
- [ ] Sign up with name, email, password → redirect to login with success message
- [ ] Login with correct credentials → main page
- [ ] Login with wrong credentials → login page with “Invalid username or password”
- [ ] Sign up with existing email → signup page with validation message

**Level 4**
- [ ] Without login, opening ItemController URL redirects to login
- [ ] After login, Logout button visible on items page
- [ ] Logout → redirect to login; session cleared; cookies cleared; Logout button no longer visible until login again
