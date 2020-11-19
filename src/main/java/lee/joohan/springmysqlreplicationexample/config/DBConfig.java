package lee.joohan.springmysqlreplicationexample.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import net.ttddyy.dsproxy.listener.logging.SLF4JLogLevel;
import net.ttddyy.dsproxy.listener.logging.SLF4JQueryLoggingListener;
import net.ttddyy.dsproxy.support.ProxyDataSourceBuilder;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

/**
 * Created by Joohan Lee on 2020/07/07
 */

@Configuration
@EntityScan
public class DBConfig {

  @Bean
  @ConfigurationProperties(prefix = "app.datasource.master")
  public HikariConfig masterConfiguration() {
    return new HikariConfig();
  }

  @Bean
  @ConfigurationProperties(prefix = "app.datasource.replica")
  public HikariConfig replicaConfiguration() {
    return new HikariConfig();
  }

  @Bean
  public DataSource routingDataSource() {
    return new ReplicaRoutingDataSource(
        loggingProxy("master", new HikariDataSource(masterConfiguration())),
        loggingProxy("replica", new HikariDataSource(replicaConfiguration())));
  }

  @Bean
  public LocalContainerEntityManagerFactoryBean entityManagerFactory(EntityManagerFactoryBuilder builder) {
    return builder
        .dataSource(routingDataSource())
        .packages("lee.joohan.springmysqlreplicationexample.domain")
        .build();
  }

  @Bean
  public PlatformTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
    return new ReplicaAwareTransactionManager(new JpaTransactionManager(entityManagerFactory));
  }

  private DataSource loggingProxy(String name, DataSource dataSource) {

// Master or Slave 중 어디로 가는지 확인하기 위한 로그, 실제로 서비스에 적용할 떈 빼기
    SLF4JQueryLoggingListener loggingListener = new SLF4JQueryLoggingListener();

    loggingListener.setLogLevel(SLF4JLogLevel.INFO);
    loggingListener.setLogger(name);
    loggingListener.setWriteConnectionId(false);

    return ProxyDataSourceBuilder
        .create(dataSource)
        .name(name)
        .listener(loggingListener)
        .build();
  }
}
