package com.littlersmall.rabbitmqaccess.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by littlersmall on 2018/5/16.
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class MessageWithTime {
    private long id;
    private long time;
    private Object message;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getTime() {
		return time;
	}
	public void setTime(long time) {
		this.time = time;
	}
	public Object getMessage() {
		return message;
	}
	public void setMessage(Object message) {
		this.message = message;
	}
	public MessageWithTime(long id, long time, Object message) {
		super();
		this.id = id;
		this.time = time;
		this.message = message;
	}
    
	
}
