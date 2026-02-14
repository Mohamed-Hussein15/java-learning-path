package item.model;

import java.util.Date;

/**
 * One-to-One with Item. Each Item has at most one ItemDetails.
 */
public class ItemDetails {

    private Long id;
    private Long itemId;
    private String description;
    private Date issueDate;
    private Date expiryDate;

    public ItemDetails() {
    }

    public ItemDetails(Long itemId, String description, Date issueDate, Date expiryDate) {
        this.itemId = itemId;
        this.description = description;
        this.issueDate = issueDate;
        this.expiryDate = expiryDate;
    }

    public ItemDetails(Long id, Long itemId, String description, Date issueDate, Date expiryDate) {
        this.id = id;
        this.itemId = itemId;
        this.description = description;
        this.issueDate = issueDate;
        this.expiryDate = expiryDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(Date issueDate) {
        this.issueDate = issueDate;
    }

    public Date getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(Date expiryDate) {
        this.expiryDate = expiryDate;
    }
}
