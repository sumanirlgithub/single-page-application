package com.core.finalkeyword;

import com.core.Department;
import com.core.User;
import com.core.UserDeep;

import java.util.ArrayList;
import java.util.List;

public class FinalKeywordExample {

    public static void main(String[] args) {

        final int cnt = 1;
        final List<String> aList1 = new ArrayList<>();
        aList1.add("Suman");
        aList1.add("Aman");
        System.out.println(aList1);
        aList1.set(0, "Ronak");
        System.out.println(aList1);

        List<String> aList2 = new ArrayList<>();
        //aList1 = aList1;  // compiler doesn't allow as aList1 declared as final
        //cnt = 2; // compiler doesn't allow as cnt declared as final

    }
}