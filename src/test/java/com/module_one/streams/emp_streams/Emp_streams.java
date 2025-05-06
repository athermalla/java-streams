package com.module_one.streams.emp_streams;


import com.google.common.collect.ImmutableList;
import com.module_one.streams.beans.Employee;
import com.module_one.streams.mockdata.MockData;
import org.junit.Test;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.maxBy;


public class Emp_streams {


  /*
  *
  * Query 3.1 : How many male and female employees are there in the organization?

For queries such as above where you need to group the input elements, use the Collectors.groupingBy()
*  method. In this query, we use Collectors.groupingBy() method which takes two arguments.
* We pass Employee::getGender as first argument which groups the input elements based on gender
* and Collectors.counting() as second argument which counts the number of entries in each group.
  *
  * */



  @Test
  public void checkGender() throws IOException {
    List<Employee> emp = MockData.getEmployees();

    emp.stream()
            .collect(
                    Collectors.groupingBy(Employee::getGender, Collectors.counting())
            )
            .entrySet()
            .forEach(System.out::println);



  }

  //  Query 3.2 : Print the name of all departments in the organization?
//
//Use distinct() method after calling map(Employee::getDepartment) on the stream.
// It will return unique departments.
  @Test
  public void distinctDepartment() throws Exception {
    ImmutableList<Employee> emp = MockData.getEmployees();

    emp.stream()
            .map(Employee::getDepartment)
            .distinct()
            .forEach(System.out::println);

//    emp.stream()
//            .collect(groupingBy(Employee::getDepartment,counting()))
//            .entrySet()
//            .iterator()
//            .forEachRemaining(System.out::println);
  }


  //Query 3.3 : What is the average age of male and female employees?
  //
  //Use same method as query 3.1 but pass Collectors.averagingInt(Employee::getAge)
  // as the second argument to Collectors.groupingBy().

  @Test
  public void avgAge() throws Exception {
    ImmutableList<Employee> emp = MockData.getEmployees();

    emp.stream()
            .collect(
                    Collectors.groupingBy(Employee::getGender,Collectors.averagingInt(Employee::getAge))
            )
            .entrySet()
            .iterator()
            .forEachRemaining(System.out::println);

  }

  //Query 3.4 : Get the details of highest paid employee in the organization?
  //
  //Use Collectors.maxBy() method which returns maximum element
  // wrapped in an Optional object based on supplied Comparator.
  @Test
  public void highestPaid() throws Exception {
    ImmutableList<Employee> emp = MockData.getEmployees();

    //Sol 1
//    Optional<Double> res = emp.stream()
//            .map(Employee::getSalary)
//            .max(Comparator.naturalOrder())
//            ;
//
//    res.ifPresent(System.out::println);

    //Sol 2
    Employee eemp = emp.stream()
            .max(Comparator.comparing(Employee::getSalary)).get();
    System.out.println(eemp);

    //Sol 3
    emp.stream()
            .collect(maxBy(Comparator.comparingDouble(Employee::getSalary)))
            .ifPresent(System.out::println);

    //Sol 4
    // Solution using Java Streams
    List<Employee> maxSalaryEmployees = emp.stream()
            .collect(Collectors.collectingAndThen(
                    Collectors.groupingBy(
                            Employee::getSalary,
                            Collectors.toList()
                    ),
                    map -> {
                      OptionalDouble maxSalary = map.keySet().stream()
                              .mapToDouble(Double::doubleValue)
                              .max();

                      return maxSalary.isPresent()
                              ? map.get(maxSalary.getAsDouble())
                              : Collections.EMPTY_LIST;
                    }
            ));

    maxSalaryEmployees.forEach(System.out::println);

    //Sol 5
    OptionalDouble maxSalaryOpt = emp.stream()
            .mapToDouble(Employee::getSalary)
            .max();

// Then find all employees with that salary
    List<Employee> maxSalaryEmployeess = maxSalaryOpt.isPresent()
            ? emp.stream()
            .filter(e -> e.getSalary() == maxSalaryOpt.getAsDouble())
            .collect(Collectors.toList())
            : Collections.EMPTY_LIST;
    maxSalaryEmployeess.forEach(System.out::println);

  }

  //  Query 3.5 : Get the names of all employees who have joined after 2015?
//
//For such queries which require filtering of input elements,
//use Stream.filter() method which filters input elements according to supplied Predicate.
  @Test
  public void joinedAfterYear() throws Exception {
    ImmutableList<Employee> emp = MockData.getEmployees();

    //sol 1
    emp.stream()
            .filter(e ->e.getYearOfJoining()>2015)
            .forEach(e -> System.out.println(e.getName()));

    //sol 2
    emp.stream()
            .filter(e -> e.getYearOfJoining() > 2015)
            .map(Employee::getName)
            .forEach(System.out::println);

  }


//Query 3.6 : Count the number of employees in each department?
//
//This query is same as query 3.1 but here we are grouping the elements by department.

  @Test
  public void noOfEmpInDepart() throws Exception {
    ImmutableList<Employee> emp = MockData.getEmployees();

    emp.stream()
            .collect(
                    Collectors.groupingBy(Employee::getDepartment, Collectors.counting() )
            )
            .entrySet()
            .forEach(System.out::println);


  }


  //Query 3.7 : What is the average salary of each department?
//
//Use the same method as in the above query 3.6, but here pass
//Collectors.averagingDouble(Employee::getSalary) as second argument to Collectors.groupingBy() method.
  @Test
  public void avgSalInDepart() throws Exception {
    ImmutableList<Employee> emp = MockData.getEmployees();

    emp.stream()
            .collect(
                    Collectors.groupingBy(Employee::getDepartment, Collectors.averagingDouble(Employee::getSalary))
            )
            .entrySet()
            .forEach(System.out::println);


  }

  //Query 3.8 : Get the details of youngest male employee in the Business Development department?
//
//For this query, use Stream.filter() method to filter male employees in Business Development department
// and to find youngest among them, use Stream.min() method.
  @Test
  public void youngMaleInDepart() throws Exception {
    ImmutableList<Employee> emp = MockData.getEmployees();

//  emp.stream()
//          .map(Employee::getDepartment)
//          .distinct()
//          .forEach(System.out::println);

    emp.stream()
            .filter(e->
                    "Business Development".equalsIgnoreCase(e.getDepartment())
                            && "Male".equalsIgnoreCase(e.getGender())
            )
            .min(Comparator.comparing(Employee::getAge))
            .ifPresent(System.out::println);

  }

//Query 3.9 : Who has the most working experience in the organization?
//
//For this query, sort employeeList by yearOfJoining in natural order and
// first employee will have most working experience in the organization.
// To solve this query, we will be using sorted() and findFirst() methods of Stream.

  @Test
  public void mostWorkingExp() throws Exception {
    ImmutableList<Employee> emp = MockData.getEmployees();

    //sol 1
    emp.stream()
            .min(Comparator.comparing(Employee::getYearOfJoining))
            .ifPresent(System.out::println);

    //sol 2
    emp.stream().sorted(Comparator.comparingInt(Employee::getYearOfJoining)).findFirst().ifPresent(System.out::println);

  }

  //Query 3.10 : How many male and female employees are there in the sales and marketing team?
//
//This query is same as query 3.1, but here use filter() method to filter sales and marketing employees.
  @Test
  public void maleFemalein() throws Exception {
    ImmutableList<Employee> emp = MockData.getEmployees();

//    emp.stream()
//            .map(Employee::getDepartment)
//            .distinct()
//            .forEach(System.out::println);

    emp.stream()
            .filter(e ->
                    "Sales".equalsIgnoreCase(e.getDepartment())
                            || "Marketing".equalsIgnoreCase(e.getDepartment())
            ).collect(Collectors.groupingBy(Employee::getGender,Collectors.counting()))
            .entrySet()
            .forEach(System.out::println);
    //now if i want the count of only 1 gender that is female
    long count =  emp.stream()
            .filter(e ->
                    ( "Sales".equalsIgnoreCase(e.getDepartment())
                            || "Marketing".equalsIgnoreCase(e.getDepartment())
                    ) && "Female".equalsIgnoreCase(e.getGender())
            )
            .count();
    System.out.println(count
    );


  }



  //Query 3.11 : What is the average salary of male and female employees?
  //
  //This query is same as query 3.3 where you have found average age of male and female employees.
  // Here, we will be finding average salary of male and female employees.


  @Test
  public void avgSal() throws Exception {
    ImmutableList<Employee> emp = MockData.getEmployees();

    //avg salary in each gender
    emp.stream()
            .collect(
                    Collectors.groupingBy(Employee::getGender,Collectors.averagingDouble(Employee::getSalary))
            )
            .entrySet()
            .forEach(System.out::println);


  }

//  Query 3.12 : List down the names of all employees in each department?
//
//For this query, we will be using Collectors.groupingBy() method by passing Employee::getDepartment as an argument.

  @Test
  public void nameOfEmpInDepart() throws Exception {
    ImmutableList<Employee> emp = MockData.getEmployees();

    //sol 1
    //avg salary in each gender
    emp.stream()
            .collect(
                    Collectors.groupingBy(
                            Employee::getDepartment,Collectors.mapping(
                                    Employee::getName,Collectors.toList()
                            )
                    )
            )
            .entrySet()
            .forEach(System.out::println);

    //sol 2

    emp.stream()
            .collect(Collectors.groupingBy(Employee::getDepartment))
            .entrySet()
            .forEach(System.out::println);

  }


  //  Query 3.13 : What is the average salary and total salary of the whole organization?
//
//For this query, we use Collectors.summarizingDouble() on Employee::getSalary which will return
// statistics of the employee salary like max, min, average and total.
  @Test
  public void avgAndTotalSal() throws Exception {
    ImmutableList<Employee> emp = MockData.getEmployees();

    DoubleSummaryStatistics employeeSalaryStatistics= emp.
            stream()
                    .collect(Collectors.summarizingDouble(Employee::getSalary));

    System.out.println("Avg sal : "+employeeSalaryStatistics.getAverage());
    System.out.printf("Total sal : "+ employeeSalaryStatistics.getSum());

  }

//  Query 3.14 : Separate the employees who are younger or equal to 25 years from those employees
//  who are older than 25 years.
//
//For this query, we will be using Collectors.partitioningBy() method which separates input elements
// based on supplied Predicate.
@Test
public void sepEmp() throws Exception {
  ImmutableList<Employee> emp = MockData.getEmployees();

  emp.stream()
          .collect(Collectors.partitioningBy(e -> e.getAge() > 25))
          .entrySet()
          .forEach(System.out::println);

}

//Query 3.15 : Who is the oldest employee in the organization? What is his age and which department he belongs to?
@Test
public void oldestEmp() throws Exception {
  ImmutableList<Employee> emp = MockData.getEmployees();

 emp.stream()
          .max(Comparator.comparingInt(Employee::getAge))
         .ifPresent(System.out::println);


}














//  @Test
//  public void oldestEmp() throws Exception {
//    ImmutableList<Employee> emp = MockData.getEmployees();
//
//    emp.stream()
//            .max(Comparator.comparingInt(Employee::getAge))
//            .ifPresent(System.out::println);
//
//
//  }






















}
