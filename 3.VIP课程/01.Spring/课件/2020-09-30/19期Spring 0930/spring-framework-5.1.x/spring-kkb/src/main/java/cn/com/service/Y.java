package cn.com.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Y {


	public X x;

	public Y(){
		System.out.println("creat Y");
	}

}
