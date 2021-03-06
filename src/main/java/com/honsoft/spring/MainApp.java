package com.honsoft.spring;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.honsoft.spring.config.SpringJdbcConfig;

public class MainApp {
   public static void main(String[] args) {
      ApplicationContext context = new AnnotationConfigApplicationContext(SpringJdbcConfig.class);

      StudentDAO studentJDBCTemplate = 
         (StudentDAO)context.getBean("studentDAOImpl");
      
      System.out.println("------Records Creation--------" );
      studentJDBCTemplate.create("Zara", 11);
      studentJDBCTemplate.create("Nuha", 2);
      studentJDBCTemplate.create("Ayan", 15);
      
      List<Student> students = new ArrayList<Student>();
      students.add(new Student("july",7));
      students.add(new Student("june",6));
      students.add(new Student("march",3));

      studentJDBCTemplate.createBatch(students);      
      
      System.out.println("------Listing Multiple Records--------" );
      students = studentJDBCTemplate.listStudents();
      
      for (Student record : students) {
         System.out.print("ID : " + record.getId() );
         System.out.print(", Name : " + record.getName() );
         System.out.println(", Age : " + record.getAge());
      }

      System.out.println("----Updating Record with ID = 2 -----" );
      studentJDBCTemplate.update(2, 20);

      System.out.println("----Listing Record with ID = 2 -----" );
      Student student = studentJDBCTemplate.getStudent(2);
      System.out.print("ID : " + student.getId() );
      System.out.print(", Name : " + student.getName() );
      System.out.println(", Age : " + student.getAge());
   }
}