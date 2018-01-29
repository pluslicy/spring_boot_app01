package com.briup.apps.app01.web;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.briup.apps.app01.bean.User;
import com.briup.apps.app01.view.UserView;
import com.fasterxml.jackson.annotation.JsonRawValue;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@CrossOrigin
@RequestMapping(path={"/users"})
public class UserController {
	/**
	 * 参数写在URL中
	 * 可以匹配url为	http://localhost:8080/users/findById/1001
	 * */
	@GetMapping(path={"/findById/{id}"})
	public String allUsers(@PathVariable long id) {
		return "this is users, id :"+id;
	}
	
	/**
	 * 参数获取
	 * */
	@PostMapping(path = "/saveUser", consumes = "application/json")
	public String saveUser(@RequestBody User user,@RequestParam int id) {
		System.out.println("id:"+id);
		System.out.println("user:"+user);
		return "";
	}
	/**
	 * c参数获取
	 * */
	@PostMapping(path = "/addUser")
	@ResponseBody
	public String addUser(@ModelAttribute User user) {
		System.out.println("addUser:"+user);
		return "success";
	}
	
	/**
	 * 获取cookie
	 * */
	@GetMapping(path = "/getCookie")
	public String getCookie(@CookieValue("JSESSIONID") String cookie
			, HttpServletRequest request) {
		System.out.println("cookie:"+cookie);
		Cookie[] cookies = request.getCookies();
		if(cookies!=null) {
			for(Cookie c :cookies) {
				System.out.println(c.getName()+":"+c.getValue());
			}
		}
		return "";
	}
	
	/**
	 * 设置cookie
	 * */
	@GetMapping(path = "/setCookie")
	public String setCookie(HttpSession session) {
		User user = new User(1001L, "terry");
		session.setAttribute("user", user);
		return "登录成功！";
	}
	
	@GetMapping(path="/getUsers")
	@JsonRawValue
	public List<User> getUsers() {
		List<User> list = new ArrayList<>();
		User user1 = new User(1001L, "中文");
		list.add(user1);
		list.add(new User(1002L, "terry"));
		return list	;
	}
	/**
	 * 
	 * */
	@GetMapping(path="/getUserJson")
	//@JsonView(UserView.Detail.class)
	@JsonRawValue
	public User getUser() {
		User user = null;
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			//初始化对象
			Date birth = sdf.parse("1991-10-1");
			user = new User(1001L, "terry","男",birth);
			//设置集合
			List<String> ls = new ArrayList<>();
			for(int i=1;i<10;i++) {
				ls.add("1889682792"+i);
			}
			user.setPhones(ls);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return user;
	}
	
	/**
	 * 文件上传
	 * */
	@PostMapping("/form")
    public String handleFormUpload(@RequestParam("name") String name,
            @RequestParam("picture") MultipartFile file, HttpServletRequest request) {
		String realPath = request.getServletContext().getRealPath("/");
        if (!file.isEmpty()) {
            try {
				byte[] bytes = file.getBytes();
				
				System.out.println(file.getContentType());
				System.out.println(bytes);
				
				File out = new File(realPath+"a.doc");
				if(!out.exists()) {
					out.createNewFile();
				}
				FileCopyUtils.copy(bytes,out );
			} catch (IOException e) {
				e.printStackTrace();
				return "redirect:uploadFailure";
			}
            return "redirect:uploadSuccess";
        }

        return "redirect:uploadFailure";
    }
}
