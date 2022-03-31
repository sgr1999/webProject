package com.example.controller;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.example.dao.UserRepository;
import com.example.entites.Contact;
import com.example.entites.User;

@Controller
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserRepository userRepository;

	//method to adding common data to
	@ModelAttribute
	public void addCommonData(Model model, Principal principal)
	{
		String userName = principal.getName();
		System.out.println(userName);
		
		User user = userRepository.getUserByUserName(userName);
		
		System.out.println(user);
		
		model.addAttribute("user",user);
	}
	
	@RequestMapping("/index")
	public String dashbord(Model model, Principal principal) {
		
		return "normal/user_dashboard";
	}

	//open add form handler
	@RequestMapping("/add-contact")
	public String openAddContactForm(Model m){

		m.addAttribute("title","Contact Form");
		m.addAttribute("contact", new Contact());

		return "normal/add_contact_form";
	}

	@RequestMapping("/process-contact")
	public String process(
		@ModelAttribute Contact contact, 
	
		Principal principal){

		try {
			String name = principal.getName();
		    User user =this.userRepository.getUserByUserName(name);

		contact.setUser(user);

		// if(file.isEmpty()){

		// }else{
		// 	contact.setimageUrl(file.getOriginalFilename());

		// 	File saveFile = new ClassPathResource("static/img").getFile();
			
		// 	Path path = Paths.get(saveFile.getAbsolutePath()+File.separator+file.getOriginalFilename());


		// 	Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
		// }

		user.getContacts().add(contact);
		
		if(contact.getimageUrl()==null)
		{
			contact.setimageUrl("my image");
		}

		this.userRepository.save(user);
		
		System.out.println(contact);
		System.out.println("Added to data bases");

		
		} catch (Exception e) {
			System.out.println("error : "+e.getMessage());
			e.printStackTrace();
		}
		return "normal/add_contact_form";
	}
}
