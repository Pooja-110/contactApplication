package com.example.contactapplication;
public class ContactVO {
    int ContactImage;
   String ContactName;
    String ContactNumber;



    public int getContactImage() {
        return ContactImage;
    }

    public void setContactImage(String ContactImage) {
        this.ContactImage = Integer.parseInt(ContactImage);
    }

    public String getContactName() {
        return ContactName;
    }

    public void setContactName(String ContactName) {
        this.ContactName = ContactName;
    }

    public String getContactNumber() {
        return ContactNumber;
    }

    public void setContactNumber(String ContactNumber) {
        this.ContactNumber = ContactNumber;
    }
}