package com.floorcrasher.config;

import javax.servlet.ServletContext;
import javax.servlet.ServletRegistration;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.filter.DelegatingFilterProxy;
import org.springframework.web.servlet.DispatcherServlet;

public class FloorCrasherWebAppInitializer implements WebApplicationInitializer {

	@Override
	public void onStartup(ServletContext container) {
		
//		XmlWebApplicationContext appContext = new XmlWebApplicationContext();
//		appContext.setConfigLocation("/WEB-INF/myBatisServlet-servlet.xml");
		AnnotationConfigWebApplicationContext rootContext = new AnnotationConfigWebApplicationContext();
		rootContext.register(ServletConfig.class);
		
		// Manage the lifecycle of the root application context
		container.addListener(new ContextLoaderListener(rootContext));
		
		//Spring Security
		container.addFilter("springSecurityFilterChain", new DelegatingFilterProxy("springSecurityFilterChain"))
			.addMappingForUrlPatterns(null, false, "/*");
		
		// Register and map the dispatcher servlet
		ServletRegistration.Dynamic dispatcher = container.addServlet("dispatcherServlet", new DispatcherServlet(rootContext));
		dispatcher.setLoadOnStartup(1);
		dispatcher.addMapping("/*");
		dispatcher.addMapping("*.css");
		dispatcher.addMapping("*.eot");
		dispatcher.addMapping("*.svg");
		dispatcher.addMapping("*.ttf");
		dispatcher.addMapping("*.woff");
		dispatcher.addMapping("*.map");
		dispatcher.addMapping("*.js");
		dispatcher.addMapping("*.ico");
	}

}
