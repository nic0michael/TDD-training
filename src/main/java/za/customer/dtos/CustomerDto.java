package za.customer.dtos;


import za.customer.enums.Gender;


public class CustomerDto {
    private String customerIdentity;
    private String name;
    private int age;
    private Gender custGender;
    private double income;
    private double incomeTax;

    public CustomerDto() {
        super();
    }

    public String getCustomerIdentity() {
        return customerIdentity;
    }

    public void setCustomerIdentity(String customerIdentity) {
        this.customerIdentity = customerIdentity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Gender getCustGender() {
        return custGender;
    }

    public void setCustGender(Gender custGender) {
        this.custGender = custGender;
    }

    public double getIncome() {
        return income;
    }

    public void setIncome(double income) {
        this.income = income;
    }

    public double getIncomeTax() {
        return incomeTax;
    }

    public void setIncomeTax(double incomeTax) {
        this.incomeTax = incomeTax;
    }
}
