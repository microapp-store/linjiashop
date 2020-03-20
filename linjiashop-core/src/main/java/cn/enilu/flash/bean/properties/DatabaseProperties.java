package cn.enilu.flash.bean.properties;

import cn.enilu.flash.utils.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Properties;

/**
 * 从数据库里读取配置
 * @author enilu
 * @version 2018-07-25
 */
public class DatabaseProperties {

    private static final Logger log = LoggerFactory.getLogger(DatabaseProperties.class);
    private Properties props = new Properties();
    private DataSource datasource;
    private String query = "select cfg_name,cfg_value from t_sys_cfg";

    public DatabaseProperties(DataSource datasource) {
        this.datasource = datasource;
        initProperties();
    }

    public Properties getProps() {
        return props;
    }

    private void initProperties() {
        Connection connection = null;
        try {
            connection = datasource.getConnection();
            PreparedStatement ps = connection.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            log.info("load server properties start.......");
            while (rs.next()) {
                String key = rs.getString(1);
                String value = rs.getString(2);
                if (StringUtil.isNotEmpty(key) && StringUtil.isNotEmpty(value)) {
                    log.info("load property. Key=" + key + ",Value=" + value);
                    props.setProperty(key, value);
                }
            }
            rs.close();
            ps.close();
        } catch (Exception e) {
            log.error(e.getMessage(),e);
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (Exception e) {
                    log.error(e.getMessage(),e);
                }
            }
        }
    }
}
