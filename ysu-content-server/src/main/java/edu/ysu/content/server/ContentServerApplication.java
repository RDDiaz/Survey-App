package edu.ysu.content.server;

import com.zaxxer.hikari.HikariDataSource;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DataSourceConnectionProvider;
import org.jooq.impl.DefaultConfiguration;
import org.jooq.impl.DefaultDSLContext;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;

import javax.annotation.Resource;
import javax.sql.DataSource;

@SpringBootApplication
public class ContentServerApplication {
	public static void main(String[] args) {
		SpringApplication.run(ContentServerApplication.class, args);
	}

	@Resource
	private Environment env;

	@Bean
	public DataSource dataSource()
	{
		HikariDataSource dataSource = new HikariDataSource();

		dataSource.setDriverClassName(env.getRequiredProperty("hikari.driverClass"));
		dataSource.setJdbcUrl(env.getRequiredProperty("hikari.url"));
		dataSource.setUsername(env.getRequiredProperty("hikari.username"));
		dataSource.setPassword(env.getRequiredProperty("hikari.password"));
		dataSource.setTransactionIsolation(env.getRequiredProperty("hikari.transactionIsolation"));
		dataSource.setMinimumIdle(Integer.parseInt(env.getRequiredProperty("hikari.minimumIdle")));
		dataSource.setMaximumPoolSize(Integer.parseInt(env.getRequiredProperty("hikari.maximumPoolSize")));

		return dataSource;
	}

	@Bean
	public DataSourceConnectionProvider dataSourceConnectionProvider()
	{
		return new DataSourceConnectionProvider(dataSource());
	}

	@Bean
	public DefaultConfiguration defaultConfiguration()
	{
		DefaultConfiguration defaultConfiguration = new DefaultConfiguration();

		defaultConfiguration.setConnectionProvider(dataSourceConnectionProvider());
		defaultConfiguration.setSQLDialect(SQLDialect.POSTGRES);

		return defaultConfiguration;
	}

	@Bean
	public DSLContext dslContext()
	{
		return new DefaultDSLContext(defaultConfiguration());
	}
}
