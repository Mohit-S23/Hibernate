package com.mohit.hibernate.demo;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.mohit.hibernate.demo.entity.Instructor;
import com.mohit.hibernate.demo.entity.InstructorDetail;

public class CreateDemo {

	public static void main(String[] args) {

		// create session factory
		SessionFactory factory = new Configuration()
								.configure("hibernate.cfg.xml")
								.addAnnotatedClass(Instructor.class)
								.addAnnotatedClass(InstructorDetail.class)
								.buildSessionFactory();
		
		// create session
		Session session = factory.getCurrentSession();
		
		try {
			// create the objects
//			Instructor tempInstructor = new Instructor("Harshad", "Mehta", "mehta.harshad@gmail.com");
//			
//			InstructorDetail tempInstructorDetail = new InstructorDetail("http://www.xyz.com/youtube", "Stocks!"); 
			
			Instructor tempInstructor = new Instructor("Rohit", "Sharma", "sharma.rohit@gmail.com");
			
			InstructorDetail tempInstructorDetail = new InstructorDetail("http://www.youtube.com", "Cricket");
			
			// associate the objects
			tempInstructor.setInstructorDetail(tempInstructorDetail);
			
			// start a transaction
			session.beginTransaction();
			
			// save the instructor
			//
			// Note: this will also save the details object because of CascadeType.ALL
			//
			System.out.println("Saving instructor: " + tempInstructor);
			session.save(tempInstructor);
						
			// commit transaction
			session.getTransaction().commit();
			
			System.out.println("Done!");
		}
		finally {
			factory.close();
		}
	}

}
