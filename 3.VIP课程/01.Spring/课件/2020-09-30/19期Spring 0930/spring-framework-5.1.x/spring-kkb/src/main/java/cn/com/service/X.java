package cn.com.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class X {


	public Y y;

	public X(){
		System.out.println("creat X");
	}
}
