package classes;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 *
 * @23093023
 */

public class Claim {
    
    private int claimId;
    private int personId;
    private int itemId;
    private String status;
    
    //Formatted Date
    LocalDate dateObj = LocalDate.now();
    DateTimeFormatter formatDate = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    
    private String date = dateObj.format(formatDate); 
    
    public Claim() {
    }
    
    public Claim(int claimId, int personId, int itemId, String date, String status) {
        this.claimId = claimId;
        this.personId = personId;
        this.itemId = itemId;
        this.date = date;
        this.status = status;
    }

    public int getClaimId() {
        return claimId;
    }

    public int getPersonId() {
        return personId;
    }

    public int getItemId() {
        return itemId;
    }

    public String getStatus() {
        return status;
    }

    public DateTimeFormatter getFormatDate() {
        return formatDate;
    }

    public String getDate() {
        return date;
    }

    public void setClaimId(int claimId) {
        this.claimId = claimId;
    }

    public void setPersonId(int personId) {
        this.personId = personId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setDate(String date) {
        this.date = date;
    }
    
}