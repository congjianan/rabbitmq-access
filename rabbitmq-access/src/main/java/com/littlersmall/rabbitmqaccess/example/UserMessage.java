package com.littlersmall.rabbitmqaccess.example;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by littlersmall on 16/6/28.
 */
//for example
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserMessage {
	int id;
    String name;
    /*public UserMessage(int i, String string) {
    }*/
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public UserMessage() {
		super();
	}
	public UserMessage(int id, String name) {
		super();
		System.out.println("初始化了UserMessage"+id+"|"+name);
		this.id = id;
		this.name = name;
	}
	@Override
	public String toString() {
		return "UserMessage [id=" + id + ", name=" + name + "]";
	}
    
    
}
