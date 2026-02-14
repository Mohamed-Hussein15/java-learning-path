# Item Management Application - Setup Instructions

## What Was Missing

This document outlines what was missing in the application and how to fix it.

### 1. **web.xml Configuration File** ✅ FIXED
   - **Location**: `main/webapp/WEB-INF/web.xml`
   - **Issue**: The `@Resource` annotation requires a `web.xml` file with resource reference configuration for proper JNDI lookup in Tomcat.
   - **Solution**: Created `web.xml` with proper resource-ref configuration.

### 2. **Database Table** ✅ DOCUMENTED
   - **Location**: `database_setup.sql`
   - **Issue**: The application references `HR.ITEM` table which doesn't exist.
   - **Solution**: Created SQL script to create the table with proper schema.

### 3. **Null Pointer Exception Prevention** ✅ FIXED
   - **Location**: `main/webapp/item/show-items.jsp`
   - **Issue**: If `items` list is null, the JSP would throw a NullPointerException.
   - **Solution**: Added null check and empty state message.

### 4. **Error Handling** ✅ IMPROVED
   - **Location**: `main/java/item/service/impl/ItemServiceImpl.java`
   - **Issue**: Service methods return `null` on error, which can cause issues.
   - **Solution**: Changed to return empty list instead of null for better error handling.

## Setup Steps

### Step 1: Database Setup
1. Connect to your Oracle Database (XEPDB1) as HR user or a user with CREATE TABLE privileges.
2. Run the `database_setup.sql` script:
   ```sql
   @database_setup.sql
   ```
   Or copy and paste the contents into SQL Developer/SQLPlus.

### Step 2: Verify Database Connection
- Ensure Oracle Database is running on `localhost:1521`
- Verify the connection string in `META-INF/context.xml` matches your database:
  - URL: `jdbc:oracle:thin:@//localhost:1521/XEPDB1`
  - Username: `hr`
  - Password: `hr`

### Step 3: Deploy to Tomcat
1. Build your project (if using Maven/Gradle) or compile Java files.
2. Copy the `src/main/webapp` folder contents to Tomcat's `webapps/Servlet-JSP` directory.
3. Copy compiled `.class` files to `WEB-INF/classes`.
4. Ensure `ojdbc8.jar` is in `WEB-INF/lib` folder.
5. Ensure `context.xml` is properly configured (it's already in `META-INF/context.xml`).

### Step 4: Start Tomcat
1. Start Tomcat server.
2. Access the application at: `http://localhost:8080/Servlet-JSP/ItemController?action=show-items`

## Application URLs

- **Show All Items**: `http://localhost:8080/Servlet-JSP/ItemController?action=show-items`
- **Add Item**: `http://localhost:8080/Servlet-JSP/item/add-item.html`
- **Update Item**: `http://localhost:8080/Servlet-JSP/ItemController?action=show-item&id=1`
- **Delete Item**: `http://localhost:8080/Servlet-JSP/ItemController?action=remove-item&id=1`

## Troubleshooting

### Issue: DataSource not found
- **Solution**: Ensure `web.xml` is in `WEB-INF` folder and `context.xml` is properly configured.

### Issue: Table does not exist
- **Solution**: Run the `database_setup.sql` script to create the table.

### Issue: Connection refused
- **Solution**: Verify Oracle Database is running and connection details in `context.xml` are correct.

### Issue: ClassNotFoundException for Oracle Driver
- **Solution**: Ensure `ojdbc8.jar` is in `WEB-INF/lib` folder.

## Notes

- The application uses Servlet 3.0+ annotations (`@WebServlet`), so servlet mapping in `web.xml` is optional.
- However, `web.xml` is still required for `@Resource` injection to work properly.
- The `context.xml` file should be in `META-INF` folder for Tomcat to pick it up automatically.

