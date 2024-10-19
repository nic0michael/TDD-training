package za.customer.dtos;

public class CustomerRequest {

    private String customerIdentity;
    private String name;
    private String age;
    private String custGender;
    private String income;
    private String incomeTax;

    public CustomerRequest() {
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

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getCustGender() {
        return custGender;
    }

    public void setCustGender(String custGender) {
        this.custGender = custGender;
    }

    public String getIncome() {
        return income;
    }

    public void setIncome(String income) {
        this.income = income;
    }

    public String getIncomeTax() {
        return incomeTax;
    }

    public void setIncomeTax(String incomeTax) {
        this.incomeTax = incomeTax;
    }
}
