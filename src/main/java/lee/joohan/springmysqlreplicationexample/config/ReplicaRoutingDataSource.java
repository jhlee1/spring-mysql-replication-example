package lee.joohan.springmysqlreplicationexample.config;


import java.util.HashMap;
import java.util.Map;
import javax.sql.DataSource;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * Created by Joohan Lee on 2020/07/07
 */
public class ReplicaRoutingDataSource extends AbstractRoutingDataSource {
  private static final ThreadLocal<Type> currentDataSource = new ThreadLocal<>();

  public ReplicaRoutingDataSource(DataSource master, DataSource slave) {
    Map<Object, Object> dataSources = new HashMap<>();
    dataSources.put(Type.MASTER, master);
    dataSources.put(Type.REPLICA, slave);

    super.setTargetDataSources(dataSources);
    super.setDefaultTargetDataSource(master);

  }

  @Override
  protected Object determineCurrentLookupKey() {
    return currentDataSource.get();
  }


  static void setReadonlyDataSource(boolean isReadonly) {
    currentDataSource.set(isReadonly ? Type.REPLICA : Type.MASTER);
  }

  private enum Type {
    MASTER, REPLICA
  }
}
