package br.com.apiEntra21;

import javax.ws.rs.ApplicationPath;

import org.glassfish.jersey.server.ResourceConfig;

@ApplicationPath("rest")
public class MyApplication extends ResourceConfig {
	
	public MyApplication() {
		packages("br.com.apiEntra21.controller");
	}
	
}
