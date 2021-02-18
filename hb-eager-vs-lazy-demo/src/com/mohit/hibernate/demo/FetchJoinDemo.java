package com.mohit.hibernate.demo;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import com.mohit.hibernate.demo.entity.Course;
import com.mohit.hibernate.demo.entity.Instructor;
import com.mohit.hibernate.demo.entity.InstructorDetail;

public class FetchJoinDemo {

	public static void main(String[] args) {

		// create session factory
		SessionFactory factory = new Configuration()
								.configure("hibernate.cfg.xml")
								.addAnnotatedClass(Instructor.class)
								.addAnnotatedClass(InstructorDetail.class)
								.addAnnotatedClass(Course.class)
								.buildSessionFactory();
		
		// create session
		Session session = factory.getCurrentSession();
		
		try {
			
			// start a transaction
			session.beginTransaction();
						
			// option 2: Hibernate query with HQL
			
			// get the instructor form db
			int theId = 1;
			
			Query<Instructor> query =									// When executed, will load instructor and courses all at once
					session.createQuery("select i from Instructor i "
									+ "JOIN FETCH i.courses "
									+ "where i.id=:theInstructorId",
									Instructor.class);
			
			// set parameter on query
			query.setParameter("theInstructorId", theId);
			
			// execute query and get instructor
			Instructor tempInstructor = query.getSingleResult();		// Load instructor and courses all at once
			
			System.out.println("x: Instrcutor: " + tempInstructor);
			
			// commit transaction
			session.getTransaction().commit();
			
			// close the session
			session.close();
			
			// since courses are lazy loaded .. this should fail because we closed the session and then tried to access lazy data
			
			// to solve this
			
			System.out.println("\nx: The sesssion is now closed!\n");
			
			// option 1: call the getter method while session is open
			
			// option 2: Hibernate query with HQL
			
			// get courses for the instructor
			System.out.println("x: Courses: " + tempInstructor.getCourses());
			
			System.out.println("x: Done!");
		}
		finally {
			
			// add clean up code
			session.close();
			
			factory.close();
		}
	}

}
