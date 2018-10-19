package com.maxrocky.gundam.coreservice.common;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;

/**
 * Created by yuer5 on 16/5/10.
 */
public class ConfigSettings {

    private static final String CONF_PATH = "conf_client.properties";
    private static final String CLIENT_QOS = "client.qos";
    private static final String MQTT_SERVER_URL = "mqttserver.url";
    private static final String MQTT_SERVER_PORT = "mqttserver.port";
    private static final String SERVER_USERNAME = "mqttservice.server.username";
    private static final String SERVER_PASSWD = "mqttservice.server.passwd";
    private static final String SERVER_CLINTID = "mqttservice.server.secret";

    public int Qos;
    public String MqttServerUrl;
    public int MqttServerPort;
    public String MqttServiceUserName;
    public String MqttServicePasswd;
    public String MqttServiceClinetId;

    private static class ConfigHolder{

        private static final ConfigSettings INSTANCE = new ConfigSettings();
    }


    private ConfigSettings(){

        try {
            Configuration config = this.getConfig();
            Qos = config.getInt(CLIENT_QOS);
            MqttServerUrl = config.getString(MQTT_SERVER_URL);
            MqttServerPort = config.getInt(MQTT_SERVER_PORT);
            MqttServiceUserName = config.getString(SERVER_USERNAME);
            MqttServicePasswd = config.getString(SERVER_PASSWD);
            MqttServiceClinetId = config.getString(SERVER_CLINTID);

        } catch (ConfigurationException e) {
            e.printStackTrace();
        }

    }

    private static Configuration getConfig() throws ConfigurationException{

        return new PropertiesConfiguration(CONF_PATH);

    }


    public static final ConfigSettings getInstance() {

        return ConfigHolder.INSTANCE;

    }


}
