package com.core.finalkeyword;

import lombok.Data;

@Data
public class Employee {

    private final Address address;

    public Employee(Address address) {
        this.address = address;
    }

    public Address updateAddress(int houseNumber, String street1) {
        Address address1 = new Address();
        address1.setHouseNumber(houseNumber);
        address1.setStreet1(street1);
        return address1;
    }

}
