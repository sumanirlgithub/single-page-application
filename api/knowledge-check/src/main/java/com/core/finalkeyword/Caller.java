package com.core.finalkeyword;

public class Caller {

    public static void main(String[] args) {

        Address address = new Address();
        address.setHouseNumber(101);
        address.setStreet1("Esker Lane");

        Employee employee = new Employee(address);
        System.out.println(employee);

        employee.updateAddress(501, "Esker Drive");
        System.out.println(employee);
    }
}
