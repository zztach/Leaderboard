package org.zisist.web;

import org.apache.log4j.Logger;
import org.zisist.conf.ConfigurationLoader;
import org.zisist.leaderboard.LeaderboardsHandler;
import org.zisist.model.Configuration;
import org.zisist.model.xml.LeaderboardConfiguration;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zis.tax@gmail.com on 08/10/2012 at 11:31 PM
 */
public class ConfigurationBean {
    private static Logger log = Logger.getLogger(ConfigurationBean.class);
    private String email;
    private String configurationName;
    private String description;
    private LeaderboardsHandler leaderboardsHandler;
    private ConfigurationLoader leaderConfigurationLoader;
    private LeaderboardConfiguration selectedLeaderboardConf;

    public ConfigurationBean() {
        configurationName = "";
        email = "";
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public String getConfigurationName() {
        return configurationName;
    }

    public void setConfigurationName(String configurationName) {
        this.configurationName = configurationName;
    }

    public Object execute() {
        Configuration configuration = new Configuration(configurationName, email);
        try {
            leaderboardsHandler.handleLeaderboards(configuration);
            log.info("Results unified successfully!");
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
                    "Action completed Successfully","Action completed Successfully"));
        } catch (Exception e) {
            log.error("Something went wrong during results unification!", e);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "An error occurred","An error occurred"));
        }

        return null;
    }

    public LeaderboardsHandler getLeaderboardsHandler() {
        return leaderboardsHandler;
    }

    public void setLeaderboardsHandler(LeaderboardsHandler leaderboardsHandler) {
        this.leaderboardsHandler = leaderboardsHandler;
    }

    public void setLeaderConfigurationLoader(ConfigurationLoader leaderConfigurationLoader) {
        this.leaderConfigurationLoader = leaderConfigurationLoader;
    }

    public ConfigurationLoader getLeaderConfigurationLoader() {
        return leaderConfigurationLoader;
    }

    public Object getConfigurationDescription() {
        if (configurationName != null) {
            description = leaderConfigurationLoader.getConfiguration(configurationName).getDescription();
        }
        return description;
    }

    public Map<String, String> getAvailableConfigurations() {
        Map<String, String> values = new LinkedHashMap<String, String>();
        List<LeaderboardConfiguration> configurations = leaderConfigurationLoader.getAvailableConfigurations();
        for(LeaderboardConfiguration conf : configurations) {
            values.put(conf.getName(), conf.getName());
        }
        return values;
    }

    public Object assignConfiguration() {
        selectedLeaderboardConf = leaderConfigurationLoader.getConfiguration(configurationName);
        return null;  //To change body of created methods use File | Settings | File Templates.
    }

    public LeaderboardConfiguration getSelectedLeaderboardConf() {
        return selectedLeaderboardConf;
    }

    public void setSelectedLeaderboardConf(LeaderboardConfiguration selectedLeaderboardConf) {
        this.selectedLeaderboardConf = selectedLeaderboardConf;
    }
}
