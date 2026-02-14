<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="item.model.ItemWithDetails"%>
<%@page import="item.model.ItemDetails"%>
<%@page import="java.text.SimpleDateFormat"%>
<!DOCTYPE html>
<%
  ItemWithDetails itemWithDetails = (ItemWithDetails) request.getAttribute("itemWithDetails");
  item.model.Item item = (item.model.Item) request.getAttribute("item");
  String descVal = "";
  String issueVal = "";
  String expiryVal = "";
  if (itemWithDetails != null && itemWithDetails.getDetails() != null) {
    ItemDetails d = itemWithDetails.getDetails();
    if (d.getDescription() != null) descVal = d.getDescription();
    if (d.getIssueDate() != null) issueVal = new SimpleDateFormat("yyyy-MM-dd").format(d.getIssueDate());
    if (d.getExpiryDate() != null) expiryVal = new SimpleDateFormat("yyyy-MM-dd").format(d.getExpiryDate());
  }
  if (item == null) item = itemWithDetails != null ? itemWithDetails.getItem() : null;
%>
<html lang="en" >
<head>
  <meta charset="UTF-8">
  <title>Update Item</title>
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/meyer-reset/2.0/reset.min.css">
<style type="text/css">
  /* Base styles */
* {
    margin: 0;
    padding: 0;
    outline: none;
    box-sizing: border-box;
    font-family: 'Poppins', sans-serif;
}

body {
    display: flex;
    align-items: center;
    justify-content: center;
    min-height: 100vh;
    padding: 40px;
    background: linear-gradient(135deg, #71b7e6, #9b59b6);
    font-family: 'Poppins', sans-serif;
}

/* Container */
.container {
    max-width: 700px;
    width: 100%;
    background: rgba(255, 255, 255, 0.95);
    padding: 40px 50px;
    border-radius: 20px;
    box-shadow: 0 15px 50px rgba(0, 0, 0, 0.2);
    backdrop-filter: blur(10px);
    border: 1px solid rgba(255, 255, 255, 0.3);
    position: relative;
    overflow: hidden;
}

.container::before {
    content: '';
    position: absolute;
    top: 0;
    left: 0;
    width: 100%;
    height: 5px;
    background: linear-gradient(90deg, #71b7e6, #9b59b6);
}

/* Text heading */
.text {
    font-size: 3rem;
    font-weight: 700;
    text-align: center;
    background: linear-gradient(45deg, #71b7e6, #9b59b6);
    -webkit-background-clip: text;
    -webkit-text-fill-color: transparent;
    margin-bottom: 50px;
    letter-spacing: 1px;
    text-shadow: 2px 2px 4px rgba(0, 0, 0, 0.1);
}

/* Form */
form {
    width: 100%;
}

/* Form rows */
.form-row {
    display: flex;
    margin-bottom: 40px;
    flex-wrap: wrap;
    gap: 30px;
}

.form-row .input-data {
    width: 100%;
    height: 70px;
    position: relative;
    flex: 1;
    min-width: 250px;
}

/* Input fields */
.input-data input {
    display: block;
    width: 100%;
    height: 100%;
    border: none;
    font-size: 1.1rem;
    background: transparent;
    padding-top: 15px;
    color: #333;
    border-bottom: 2px solid rgba(0, 0, 0, 0.12);
    transition: all 0.3s ease;
}

.input-data input:focus ~ label,
.input-data input:valid ~ label {
    transform: translateY(-35px);
    font-size: 0.95rem;
    color: #3498db;
}

.input-data label {
    position: absolute;
    bottom: 25px;
    left: 0;
    color: #666;
    font-size: 1.1rem;
    pointer-events: none;
    transition: all 0.3s ease;
    font-weight: 500;
}

/* Underline animation */
.input-data .underline {
    position: absolute;
    bottom: 0;
    height: 2px;
    width: 100%;
    background: rgba(0, 0, 0, 0.12);
}

.input-data .underline:before {
    position: absolute;
    content: "";
    height: 100%;
    width: 100%;
    background: linear-gradient(90deg, #71b7e6, #9b59b6);
    transform: scaleX(0);
    transform-origin: center;
    transition: transform 0.3s ease;
}

.input-data input:focus ~ .underline:before,
.input-data input:valid ~ .underline:before {
    transform: scaleX(1);
}

/* Submit button */
.button {
    display: block;
    width: 100%;
    max-width: 300px;
    margin: 60px auto 40px;
    padding: 18px 30px;
    font-size: 1.2rem;
    font-weight: 600;
    letter-spacing: 1px;
    text-transform: uppercase;
    border: none;
    border-radius: 50px;
    cursor: pointer;
    background: linear-gradient(45deg, #71b7e6, #9b59b6);
    color: white;
    transition: all 0.4s ease;
    box-shadow: 0 10px 30px rgba(113, 183, 230, 0.4);
    position: relative;
    overflow: hidden;
    z-index: 1;
}

.button::before {
    content: '';
    position: absolute;
    top: 0;
    left: -100%;
    width: 100%;
    height: 100%;
    background: linear-gradient(45deg, #9b59b6, #71b7e6);
    transition: all 0.4s ease;
    z-index: -1;
}

.button:hover::before {
    left: 0;
}

.button:hover {
    transform: translateY(-3px);
    box-shadow: 0 15px 40px rgba(113, 183, 230, 0.6);
}

.button:active {
    transform: translateY(-1px);
}

/* Back link */
.back {
    text-align: center;
    margin-top: 30px;
}

.back a {
    color: #666;
    text-decoration: none;
    font-size: 1.1rem;
    font-weight: 500;
    position: relative;
    padding: 10px 20px;
    transition: all 0.3s ease;
    display: inline-block;
}

.back a::before {
    content: '← ';
    transition: all 0.3s ease;
}

.back a:hover {
    color: #3498db;
    transform: translateX(-5px);
}

.back a:hover::before {
    transform: translateX(-5px);
}

/* Back link underline */
.back a::after {
    content: '';
    position: absolute;
    bottom: 0;
    left: 50%;
    width: 0;
    height: 2px;
    background: linear-gradient(90deg, #71b7e6, #9b59b6);
    transition: all 0.3s ease;
    transform: translateX(-50%);
}

.back a:hover::after {
    width: 100%;
}

/* Responsive design */
@media (max-width: 700px) {
    .container {
        padding: 30px 25px;
        margin: 20px;
    }
    
    .text {
        font-size: 2.5rem;
        margin-bottom: 40px;
    }
    
    .form-row {
        flex-direction: column;
        gap: 20px;
        margin-bottom: 20px;
    }
    
    .form-row .input-data {
        width: 100%;
        min-width: 100%;
    }
    
    .button {
        max-width: 100%;
        padding: 16px 25px;
        font-size: 1.1rem;
    }
}

@media (max-width: 480px) {
    body {
        padding: 20px 10px;
    }
    
    .container {
        padding: 25px 20px;
    }
    
    .text {
        font-size: 2rem;
        margin-bottom: 30px;
    }
    
    .input-data {
        height: 60px;
    }
    
    .input-data input {
        font-size: 1rem;
    }
    
    .button {
        margin: 40px auto 30px;
        padding: 14px 20px;
        font-size: 1rem;
    }
    
    .back a {
        font-size: 1rem;
    }
}

/* Animation for form elements */
@keyframes fadeInUp {
    from {
        opacity: 0;
        transform: translateY(20px);
    }
    to {
        opacity: 1;
        transform: translateY(0);
    }
}

.container {
    animation: fadeInUp 0.6s ease-out;
}

.form-row .input-data {
    animation: fadeInUp 0.6s ease-out forwards;
    opacity: 0;
}

.form-row .input-data:nth-child(1) { animation-delay: 0.1s; }
.form-row .input-data:nth-child(2) { animation-delay: 0.2s; }
.form-row .input-data:nth-child(3) { animation-delay: 0.3s; }
.button { animation: fadeInUp 0.6s ease-out 0.4s forwards; opacity: 0; }
.back { animation: fadeInUp 0.6s ease-out 0.5s forwards; opacity: 0; }

/* Focus styles */
.input-data input:focus {
    border-bottom: 2px solid transparent;
}

/* Placeholder */
.input-data input::placeholder {
    color: transparent;
}

.input-data input:focus::placeholder {
    color: #999;
}

/* Loading animation for submit */
.button.loading {
    pointer-events: none;
    position: relative;
    color: transparent;
}

.button.loading::after {
    content: '';
    position: absolute;
    left: 50%;
    top: 50%;
    width: 20px;
    height: 20px;
    margin: -10px 0 0 -10px;
    border: 2px solid white;
    border-top-color: transparent;
    border-radius: 50%;
    animation: spin 0.8s linear infinite;
}

@keyframes spin {
    to { transform: rotate(360deg); }
}

/* Error message */
.error-message {
    color: #f44336;
    font-size: 0.9rem;
    margin-top: 5px;
    display: none;
}

.input-data input.invalid {
    border-bottom-color: #f44336;
}

.input-data input.invalid ~ .error-message {
    display: block;
}
  </style>
  <script>
        // Show popup messages for errors
        window.onload = function() {
            <% 
            String errorMessage = (String) request.getAttribute("errorMessage");
            if (errorMessage != null) { 
            %>
                alert("<%= errorMessage %>");
            <% 
            } 
            %>
        };
        
        // Front-end validation
        function validateForm() {
            var name = document.getElementsByName("name")[0].value.trim();
            var price = document.getElementsByName("price")[0].value.trim();
            var totalNumber = document.getElementsByName("totalNumber")[0].value.trim();
            
            var isValid = true;
            var nameInput = document.getElementsByName("name")[0];
            var priceInput = document.getElementsByName("price")[0];
            var totalNumberInput = document.getElementsByName("totalNumber")[0];
            
            // Validate name
            if (name === "" || name.length < 2) {
                nameInput.classList.add("invalid");
                isValid = false;
            } else {
                nameInput.classList.remove("invalid");
            }
            
            // Validate price
            var priceNum = parseFloat(price);
            if (price === "" || isNaN(priceNum) || priceNum <= 0) {
                priceInput.classList.add("invalid");
                isValid = false;
            } else {
                priceInput.classList.remove("invalid");
            }
            
            // Validate total number
            var totalNum = parseInt(totalNumber);
            if (totalNumber === "" || isNaN(totalNum) || totalNum < 0 || !Number.isInteger(totalNum)) {
                totalNumberInput.classList.add("invalid");
                isValid = false;
            } else {
                totalNumberInput.classList.remove("invalid");
            }
            
            if (!isValid) {
                alert("Please fix the validation errors:\n- Name must be at least 2 characters\n- Price must be a positive number\n- Total number must be a non-negative integer");
                return false;
            }
            
            return true;
        }
    </script>
</head>
<body>
<!-- partial:index.partial.html -->
<div class="container">
  <div class="text">
    Update Item
  </div>
  <form action="/Servlet-JSP/ItemController" method="post" onsubmit="return validateForm()">
    <div class="form-row">
      <div class="input-data">
        <input type="text" required name="name" id="name" value="<%= item != null ? item.getName() : "" %>" pattern=".{2,}" title="Name must be at least 2 characters">
        <div class="underline"></div>
        <label>Name</label>
        <span class="error-message">Name must be at least 2 characters</span>
      </div>
      <div class="input-data">
        <input type="number" required name="price" id="price" value="<%= item != null ? item.getPrice() : "" %>" step="0.01" min="0.01" title="Price must be a positive number">
        <div class="underline"></div>
        <label>PRICE</label>
        <span class="error-message">Price must be a positive number</span>
      </div>
    </div>
    <div class="form-row">
      <div class="input-data">
        <input type="number" required name="totalNumber" id="totalNumber" value="<%= item != null ? item.getTotalNumber() : "" %>" min="0" step="1" title="Total number must be a non-negative integer">
        <div class="underline"></div>
        <label>TOTAL_NUMBER</label>
        <span class="error-message">Total number must be a non-negative integer</span>
      </div>
    </div>
    <div class="form-row">
      <div class="input-data">
        <input type="text" name="desc" id="desc" value="<%= descVal %>" placeholder="Description">
        <div class="underline"></div>
        <label>DESC (Item Details)</label>
      </div>
    </div>
    <div class="form-row">
      <div class="input-data">
        <input type="date" name="issue_date" id="issue_date" value="<%= issueVal %>">
        <div class="underline"></div>
        <label>ISSUE_DATE</label>
      </div>
      <div class="input-data">
        <input type="date" name="expiry_date" id="expiry_date" value="<%= expiryVal %>">
        <div class="underline"></div>
        <label>EXPIRY_DATE</label>
      </div>
    </div>
    <input type="hidden" name="id" value="<%= item != null ? item.getId() : "" %>">
    <input type="hidden" name="action" value="update-item">
    <input type="submit" value="Update" class="button">
  </form>

  <p class="back">
    <a href="/Servlet-JSP/ItemController?action=show-items"  >Back To Items</a>
  </p>
</div>
<!-- partial -->

</body>
</html>
