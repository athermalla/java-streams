package com.module_one.streams.lectures;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.module_one.streams.beans.Person;
import com.module_one.streams.mockdata.MockData;
import org.junit.Test;

public class Lecture12 {
  @Test
  public void understandingCollect() throws Exception {
    List<String> emails = MockData.getPeople()
        .stream()
        .map(Person::getEmail)
        .collect(
            ArrayList::new,
            ArrayList::add,
            (list1, list2) -> list1.addAll(list2));
//        .collect(Collectors.toList());

    emails.forEach(System.out::println);
  }
}
