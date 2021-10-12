package com.exist.altheo.connection;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

import org.hibernate.MappingException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.query.Query;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.tool.hbm2ddl.SchemaExport;
import org.hibernate.tool.hbm2ddl.SchemaExport.Action;
import org.hibernate.tool.schema.TargetType;


//TODO - ADD AUTOMATIC GENERATION OF TABLE WITHOUT USING hbmbml.auto PROPERTY
public class DBConnection {
    
    public static SessionFactory setSessionFactory( SessionFactory sessionFactory) {
        // A SessionFactory is set up once for an application!
		final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure("hibernate.cfg.xml").build();
        try {
            sessionFactory =  new MetadataSources( registry ).buildMetadata().buildSessionFactory();
            
            // MetadataSources metadata = new MetadataSources(registry);
           
            // EnumSet<TargetType> enumSet = EnumSet.of(TargetType.DATABASE);
            // SchemaExport schemaExport = new SchemaExport();
            // schemaExport.execute(enumSet, Action.BOTH, metadata.buildMetadata());
            // sessionFactory = new Configuration().configure("/hibernate.cfg.xml").buildSessionFactory();
        }
        catch (MappingException e){
           System.out.println("MAPPING EXCEPTION");
           System.out.println(e.getMessage());
           StandardServiceRegistryBuilder.destroy( registry );
        }
        catch (Exception e) {
            // The registry would be destroyed by the SessionFactory, but we had trouble building the SessionFactory
            // so destroy it manually.
            System.out.println(e.getMessage());
            StandardServiceRegistryBuilder.destroy( registry );
        }
        return sessionFactory;
    }

    //TODO ERROR: Method has service not available error message
    public static void exportDatabaseToBuildTools(){
        Map<String,String> settings = new HashMap<String,String>();
        
        settings.put("connection.driver.class","org.postgresql.Driver");
        settings.put("connection.url","jdbc:postgresql://localhost:5432/exercise-database");
        settings.put("dialect","org.hibernate.dialect.PostgreSQLDialect");
        settings.put("connection.username","postgres");
        settings.put("connection.password","Enfeeble_Dendi");

        ServiceRegistry serviceRegistry = new 
            StandardServiceRegistryBuilder().applySettings(settings).build();

        MetadataSources metadata = new MetadataSources(serviceRegistry);
        // metadata.addResource("/resources/Role.hbm.sql");
        // metadata.addResource("ContactInformation.hbm.sql");
        // metadata.addResource("Person.hbm.sql");

        EnumSet<TargetType> enumSet = EnumSet.of(TargetType.DATABASE);
        SchemaExport schemaExport = new SchemaExport();
        schemaExport.execute(enumSet, Action.BOTH, metadata.buildMetadata());
    }

    public static void executeStartingSQLScript(SessionFactory sessionFactory){
        Session session = sessionFactory.openSession();

		session.beginTransaction();

		String sql_query_create = 
		"create table IF NOT EXISTS PERSON ( " +
		"	PERSON_ID  serial not null, " +
		"	first_name varchar(255), " +
		"	middle_name varchar(255)," +
		"	last_name varchar(255), "+
		"	suffix varchar(255), "+
		"	title varchar(255), "+
		"	address varchar(255), "+
		"	zip_code varchar(255), "+
		"	gwa float8, "+
		"	date_hired date, "+
		"	birthdate date, "+
		"	is_currently_employed boolean, "+
		"	ROLE_ID int4, "+
		"	primary key (PERSON_ID) "+
		");" +

		"create table IF NOT EXISTS ROLE ( " +
		"	ROLE_ID serial not null, " +
		"	role_name varchar(255), " +
		"	PERSON_ID int4, " +
		"	primary key (ROLE_ID) " +
		");" +

		"create table IF NOT EXISTS CONTACT_INFORMATION ( " + 
		"	CONTACT_ID int4 not null, " + 
		"	PERSON_ID int4," + 
		"	landline varchar(255)," + 
		"	mobile_number varchar(255)," + 
		"	email varchar(255), " + 
		"	primary key (CONTACT_ID)" + 

		");" + 

		"alter table CONTACT_INFORMATION " +
		"	add constraint FK_CONTACT_ID " +
		"	foreign key (PERSON_ID) references PERSON; " +

		
		"alter table PERSON " +
		"	add constraint FK_ROLE_ID " +
		"	foreign key (ROLE_ID) references ROLE;" +

		"alter table ROLE " +
		"	add constraint FK_PERSON_ID " +
		"	foreign key (PERSON_ID)references PERSON;" 
		;
		
		Query q = session.createSQLQuery(sql_query_create);

		q.executeUpdate();

		session.close();
    }

    public static void flushDbTables(SessionFactory sessionFactory){
        Session session = sessionFactory.openSession();

		session.beginTransaction();

		String sql_query_flush_tables = 
		"drop table if exists PERSON cascade; " +
		"drop table if exists ROLE cascade;" +
		"drop table if exists CONTACT_INFORMATION cascade; "
		;
		
		Query q = session.createSQLQuery(sql_query_flush_tables);

		q.executeUpdate();

		session.close();
    }


    
}
