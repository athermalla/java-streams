package emp_streams;

import beans.Employee;
import beans.Person;
import com.google.common.collect.ImmutableList;
import mockdata.MockData;
import org.assertj.core.util.Lists;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.*;


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

//    Optional<Double> res = emp.stream()
//            .map(Employee::getSalary)
//            .max(Comparator.naturalOrder())
//            ;
//
//    res.ifPresent(System.out::println);

    Employee eemp = emp.stream()
            .max(Comparator.comparing(Employee::getSalary)).get();

    System.out.println(eemp);

  }







}
