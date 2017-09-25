package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.UriComponentsBuilder;

import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;

@Controller
public class PrivateResourceController {
	
	@Autowired
	private UserRepository userRepository;
	
	/**
	 * hasRole 好像不管用？？？
	 * @return
	 */
	@PreAuthorize(value="hasRole('admin')")
	@RequestMapping(value="/api/users/", method = RequestMethod.GET)
	@ResponseBody
	public List<User> findAllUsers(){
		return userRepository.findAll();
	}
	
	@RequestMapping(value="/test", method = RequestMethod.GET)
	@ResponseBody
	public List<User> test(){
		return userRepository.findAll();
	}
	
	
	
	 //-------------------Retrieve Single User--------------------------------------------------------
    
    @RequestMapping(value = "/api/user/{id}", method = RequestMethod.GET)
//    @RequestMapping(value = "/api/user/{id}", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE,MediaType.APPLICATION_XML_VALUE})
    @ResponseBody
    public ResponseEntity<User> getUser(@PathVariable("id") long id) {
        System.out.println("Fetching User with id " + id);
        User user = userRepository.findOne(id);
        if (user == null) {
            System.out.println("User with id " + id + " not found");
            return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<User>(user, HttpStatus.OK);
    }
    
  //-------------------Create a User--------------------------------------------------------
    
    @RequestMapping(value = "/api/user/", method = RequestMethod.POST)
    public ResponseEntity<Void> createUser(@RequestBody User user, UriComponentsBuilder ucBuilder) {
        System.out.println("Creating User " + user.getUsername());
        
  
        if (user.getId() != null && userRepository.exists(user.getId())) {
            System.out.println("A User with name " + user.getUsername() + " already exist");
            return new ResponseEntity<Void>(HttpStatus.CONFLICT);
        }
  
        userRepository.save(user);
  
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/user/{id}").buildAndExpand(user.getId()).toUri());
        return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
    }
  
      
    //------------------- Update a User --------------------------------------------------------
      
    @RequestMapping(value = "/api/user/{id}", method = RequestMethod.PUT)
    public ResponseEntity<User> updateUser(@PathVariable("id") long id, @RequestBody User user) {
        System.out.println("Updating User " + id);
          
        User currentUser = userRepository.findOne(id);
          
        if (currentUser==null) {
            System.out.println("User with id " + id + " not found");
            return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
        }
  
        currentUser.setUsername(user.getUsername());
          
        userRepository.saveAndFlush(user);
        return new ResponseEntity<User>(currentUser, HttpStatus.OK);
    }
  
    //------------------- Delete a User --------------------------------------------------------
      
    @RequestMapping(value = "/api/user/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<User> deleteUser(@PathVariable("id") long id) {
        System.out.println("Fetching & Deleting User with id " + id);
  
        User user = userRepository.findOne(id);
        if (user == null) {
            System.out.println("Unable to delete. User with id " + id + " not found");
            return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
        }
  
        userRepository.delete(id);
        return new ResponseEntity<User>(HttpStatus.NO_CONTENT);
    }
  
      
    //------------------- Delete All Users --------------------------------------------------------
      
    @RequestMapping(value = "/api/user/", method = RequestMethod.DELETE)
    public ResponseEntity<User> deleteAllUsers() {
        System.out.println("Deleting All Users");
  
        userRepository.deleteAll();
        return new ResponseEntity<User>(HttpStatus.NO_CONTENT);
    }
  

}
