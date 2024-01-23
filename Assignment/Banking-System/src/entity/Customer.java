package entity;

public class Customer {
    private int customer_id;
    private String first_name;
    private String last_name;
    private String email;
    private long phone_number;
    private String address;

    public Customer(int customer_id, String first_name, String last_name, String email, long phone_number, String address , String DOB) {
        this.customer_id = customer_id;
        this.first_name = first_name;
        this.last_name = last_name;
        setEmail(email);
        setPhone_number(phone_number);
        this.address = address;
    }

    public int getCustomer_id() {
        return customer_id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public String getEmail() {
        return email;
    }

    public long getPhone_number() {
        return phone_number;
    }

    public String getAddress() {
        return address;
    }

    public void setEmail(String email) {

        if (email.contains("@") && email.contains(".") && email.indexOf("@") < email.lastIndexOf(".")) {
            this.email = email;
        } else {
            System.out.println("Invalid email address");
        }
    }

    public void setPhone_number(long phone_number) {
        if (String.valueOf(phone_number).length() == 10) {
            this.phone_number = phone_number;
        } else {
            System.out.println("Invalid phone number");
        }
    }


    public void printInfo() {
        System.out.println("Customer ID: " + customer_id);
        System.out.println("First Name: " + first_name);
        System.out.println("Last Name: " + last_name);
        System.out.println("Email Address: " + email);
        System.out.println("Phone Number: " + phone_number);
        System.out.println("Address: " + address);
    }
}