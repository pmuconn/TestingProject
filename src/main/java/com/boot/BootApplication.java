package com.boot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@SpringBootApplication
@EnableWebSecurity
@PropertySources({
    @PropertySource("classpath:testprojectboot.properties"),
})
@ComponentScan({"com.boot"})
public class BootApplication extends WebSecurityConfigurerAdapter {
	
    @Autowired
    private CustomAuthenticationProvider authProvider;	

	@Autowired
	private SecurityProperties securityProperties;
	
	public static void main(String[] args) {
		
		SpringApplication app = new SpringApplication(BootApplication.class);
		app.run(args);
	}

	public static Authentication getAuthentication() {
		return SecurityContextHolder.getContext().getAuthentication();
	}

	
	@Override
	public void configure(WebSecurity webSecurity) throws Exception {
		webSecurity.ignoring() //No Security for these paths
			.antMatchers("/helloworld/**")
			.antMatchers("/logview/**")
			.antMatchers("/test/**")
			.antMatchers("/pdfreport/**"); 
		super.configure(webSecurity);
	}
	
    @Override
    protected void configure(final AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authProvider);
    }
	

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.authorizeRequests().antMatchers("/").permitAll();
        httpSecurity.cors();
        httpSecurity.csrf().ignoringAntMatchers("/shutdown");
        httpSecurity.csrf().disable();
        
        //httpSecurity.httpBasic().disable();
        httpSecurity.httpBasic(); 
        
        super.configure(httpSecurity);
    }
    
    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.applyPermitDefaultValues();
        configuration.addExposedHeader("Access-Control-Allow-Origin");
        configuration.addExposedHeader("Access-Control-Allow-Credentials");
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
    
    @Bean
    public static PropertySourcesPlaceholderConfigurer propertyConfigInDev() {
        return new PropertySourcesPlaceholderConfigurer();
    }
    
    //Used to debug all bean names created. Uncomment and beans will display in console.
//  @Bean
//  public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
//      return args -> {
//          System.out.println("Let's inspect the beans provided by Spring Boot:");
//          String[] beanNames = ctx.getBeanDefinitionNames();
//          Arrays.sort(beanNames);
//          for (String beanName : beanNames) {
//              System.out.println(beanName);
//          }
//      };
//  }
    
    
	
}