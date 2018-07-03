package com.topica.daonc.java8.convert_collection;

import com.google.common.collect.Lists;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Test {
    public static void main(String[] args) {
        listToMap();
        mapToList();
        testParallelStream();
    }

    public static void listToMap() {
        List<Employee> employeeList = Lists.newArrayList(
                new Employee(1, "Dao", 2000),
                new Employee(2, "Dung", 1500),
                new Employee(3, "Nam", 3000));

        Map<Integer, Employee> map = employeeList.stream().collect(Collectors.toMap(Employee::getId, employee -> employee));
        System.out.println(map);
    }

    public static void mapToList() {

        Employee e1 = new Employee(1, "Dao", 2000);
        Employee e2 = new Employee(2, "Dung", 2500);
        Employee e3 = new Employee(3, "Nam", 2700);

        Map<String, Integer> map = new HashMap<>();
        map.put(e1.getName(), e1.getSalary());
        map.put(e2.getName(), e2.getSalary());
        map.put(e3.getName(), e3.getSalary());

        List<String> names = map.keySet().stream()
                .filter(x -> !x.equalsIgnoreCase("Dao"))
                .collect(Collectors.toList());

        names.forEach(System.out::println);

    }

    public static void testParallelStream() {
        long t1, t2;
        List<Employee> eList = new ArrayList<Employee>();

        for (int i = 0; i < 1000000; i++) {
            eList.add(new Employee(1, "A", 20000));
            eList.add(new Employee(2, "B", 3000));
            eList.add(new Employee(3, "C", 15002));
            eList.add(new Employee(4, "D", 7856));
            eList.add(new Employee(5, "E", 200));
        }

        t1 = System.currentTimeMillis();
        System.out.println("Sequential Stream Count = " + eList.stream().filter(e -> e.getSalary() > 15000).count());
        t2 = System.currentTimeMillis();
        System.out.println("Sequential Stream Time Taken = " + (t2 - t1) + "\n");

        t1 = System.currentTimeMillis();
        System.out.println("Parallel Stream Count = " + eList.parallelStream().filter(e -> e.getSalary() > 15000).count());
        t2 = System.currentTimeMillis();
        System.out.println("Parallel Stream Time Taken = " + (t2 - t1));


    }

}
