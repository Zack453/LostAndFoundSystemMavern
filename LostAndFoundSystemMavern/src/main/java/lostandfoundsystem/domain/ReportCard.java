package lostandfoundsystem.domain;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class ReportCard {

    private int item_id;
    private String itemName, category, description, status;

    private int reportID, personID, itemID;
    private String dateLost, location, itemType;
    private byte[] imageData;

    public ReportCard(int item_id, String itemName, String category, String description, String status, int reportID, int personID, int itemID, String dateLost, String location, String itemType, byte[] imageData) {
        this.item_id = item_id;
        this.itemName = itemName;
        this.category = category;
        this.description = description;
        this.status = status;
        this.reportID = reportID;
        this.personID = personID;
        this.itemID = itemID;
        this.dateLost = dateLost;
        this.location = location;
        this.itemType = itemType;
        this.imageData = imageData;
    }

    public ReportCard() {
        
       
    }

    public int getItem_id() {
        return item_id;
    }

    public void setItem_id(int item_id) {
        this.item_id = item_id;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getReportID() {
        return reportID;
    }

    public void setReportID(int reportID) {
        this.reportID = reportID;
    }

    public int getPersonID() {
        return personID;
    }

    public void setPersonID(int personID) {
        this.personID = personID;
    }

    public int getItemID() {
        return itemID;
    }

    public void setItemID(int itemID) {
        this.itemID = itemID;
    }

    public String getDateLost() {
        return dateLost;
    }

    public void setDateLost(String dateLost) {
        this.dateLost = dateLost;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getItemType() {
        return itemType;
    }

    public void setItemType(String itemType) {
        this.itemType = itemType;
    }

    public byte[] getImageData() {
        return imageData;
    }

    public void setImageData(byte[] imageData) {
        this.imageData = imageData;
    }

    @Override
    public String toString() {
        return "ReportCard{" + "item_id=" + item_id + ", itemName=" + itemName + ", category=" + category + ", description=" + description + ", status=" + status + ", reportID=" + reportID + ", personID=" + personID + ", itemID=" + itemID + ", dateLost=" + dateLost + ", location=" + location + ", itemType=" + itemType + ", imageData=" + imageData + '}';
    }

}
