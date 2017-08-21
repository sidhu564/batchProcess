package com.example.demo.person;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PersonController {
	
    private static final Logger LOGGER = LoggerFactory.getLogger(PersonController.class);

    @RequestMapping(value="/api/getPerson",method = RequestMethod.GET)
    public List<Person> findPersons() {
        LOGGER.info("Finding all persons");
        List<Person> personData = readPersonsFromTextFile();
        LOGGER.info("Found {} persons", personData.size());
        return personData;
    }
    
    @RequestMapping(value="/api/persistPerson",method = RequestMethod.POST)
    public ResponseEntity<Person> persistPersons(@RequestBody Person person) {
    	LOGGER.info("Persisting Person: FirstName : "+person.getFirstName()+" :: Last Name: "+person.getLastName());
    	writePersonsToTextFile(person);
    	return new ResponseEntity<Person>(person, HttpStatus.OK);
    }
       

    private List<Person> readPersonsFromTextFile() {
    	List<Person> personList = new ArrayList<Person>();
    	BufferedReader reader = null;
		try {
			//File file = ResourceUtils.getFile("classpath:input.txt");
			File file = ResourceUtils.getFile("C:\\opt\\in\\input.txt");
			reader = new BufferedReader(new FileReader(file));
            String readLine = "";
            Person person;
            while ((readLine = reader.readLine()) != null) {
            	String[] data = readLine.split(",");
            	if(data!=null && data.length == 2){
            		person = new Person(data[0],data[1]);
            		personList.add(person);
            	}
            }

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			try{
				if(reader!=null){
					reader.close();
				}
			}catch(IOException e){
				e.printStackTrace();
			}
		}
		return personList;
    }
    
  
    private void writePersonsToTextFile(Person person) {
    	BufferedWriter bw = null;
		FileWriter fw = null;
		try {
			File folder = new File("C:\\opt\\out");
			if (!folder.exists()) {
				folder.mkdir();
			}
			File file = new File("C:\\opt\\out\\output.txt");
			if (!file.exists()) {
				file.createNewFile();
			}
			fw = new FileWriter(file.getAbsoluteFile(), true);
			bw = new BufferedWriter(fw);
			bw.write(person.getFirstName()+","+person.getLastName()+"\n");
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (bw != null)
					bw.close();
				if (fw != null)
					fw.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}

	}

}
