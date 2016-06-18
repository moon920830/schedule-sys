package com.rj.sys.config;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import javax.validation.Validation;
import javax.validation.ValidatorFactory;

import org.dozer.DozerBeanMapper;
import org.hibernate.dialect.H2Dialect;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@ImportResource("classpath:init-db.xml")
@ComponentScan(basePackages = {"com.rj.sys"})
public class TestConfiguration {
	
	@Value("${schedule-sys.db.username}")
	private String dbUsername;
	@Value("${schedule-sys.db.password}")
	private String dbPassword;
	@Value("${schedule-sys.db.url}")
	private String dbUrl;
	@Value("${schedule-sys.db.autoddl}")
	private String dbAutoDdl;
	@Value("${schedule-sys.db.driver}")
	private String driverClassName;
	
	@Bean
	public DozerBeanMapper dozerBeanMapper(){
		List<String> mappingFiles = new ArrayList<String>();
		mappingFiles.add("dozer/dozer-config.xml");
		DozerBeanMapper bm = new DozerBeanMapper(mappingFiles);
		return bm;
	}
	
	@Bean
	public ValidatorFactory validatorFactory(){
		return Validation.buildDefaultValidatorFactory();
	}
	
	@Bean
	public DataSource dataSource(){
		DriverManagerDataSource ds = new DriverManagerDataSource(dbUrl, dbUsername, dbPassword);
		ds.setDriverClassName(driverClassName);
		return ds;
	}
	
	@Bean
	public PlatformTransactionManager transactionManager(EntityManagerFactory entityManagerFactory){
		return new JpaTransactionManager(entityManagerFactory);
	}
	
	@Bean
	public Map<String, Object> jpaProperties() {
		Map<String, Object> props = new HashMap<String, Object>();
		props.put("hibernate.dialect", H2Dialect.class.getName());
		props.put("hibernate.hbm2ddl.auto", dbAutoDdl);
		props.put("hibernate.show_sql", false);
		return props;
	}
	
	@Bean
	public JpaVendorAdapter jpaVendorAdapter() {
		HibernateJpaVendorAdapter adapter = new HibernateJpaVendorAdapter();
		adapter.setShowSql(true);
		adapter.setGenerateDdl(true);
		adapter.setDatabase(Database.H2);
		return adapter;
	}
	
	@Bean
	public LocalContainerEntityManagerFactoryBean localContainerEntityManagerFactoryBean() {
		LocalContainerEntityManagerFactoryBean emf = new LocalContainerEntityManagerFactoryBean();
		emf.setDataSource(dataSource());
		emf.setJpaPropertyMap(jpaProperties());
		emf.setJpaVendorAdapter(jpaVendorAdapter());
		emf.setPackagesToScan("com.rj.sys.domain");
		return emf;
	}
	
	@Bean
	public static PropertyPlaceholderConfigurer placeholderConfigurer(){
		PropertyPlaceholderConfigurer configurer = new PropertyPlaceholderConfigurer();
		Resource[] resources = new ClassPathResource[]{new ClassPathResource("test-application.properties")};
		configurer.setLocations(resources);
		configurer.setIgnoreUnresolvablePlaceholders(true);
		configurer.setOrder(1);
		return configurer;
	}
}