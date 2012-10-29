package org.zisist.web;

import org.apache.log4j.Logger;
import org.springframework.util.StopWatch;
import org.zisist.SpringApplicationContext;
import org.zisist.conf.ConfigurationLoader;
import org.zisist.leaderboard.LeaderboardHandler;
import org.zisist.model.Configuration;
import org.zisist.model.xml.LeaderboardConfiguration;
import org.zisist.utils.WebKeys;

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
    private LeaderboardHandler leaderboardsHandler;
    private ConfigurationLoader leaderConfigurationLoader;
    private LeaderboardConfiguration selectedLeaderboardConf;
    private Boolean multithreaded;

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
            String leaderboardHandlerName = (multithreaded != null && multithreaded) ? WebKeys.LEADERBOARD_HANDLER_MULTITHREAD : WebKeys.LEADERBOARD_HANDLER_SINGLETHREAD;
            leaderboardsHandler = SpringApplicationContext.getBean(leaderboardHandlerName, LeaderboardHandler.class);
            StopWatch watch = new StopWatch();
            watch.start();
            leaderboardsHandler.handleLeaderboards(configuration);
            watch.stop();
            log.info("Results unified successfully!");
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
                    "Action completed Successfully.Execution Time : " + watch.getTotalTimeSeconds()+ " sec!",
                    "Action completed Successfully.Execution Time : " + watch.getTotalTimeSeconds()+ " sec!"));
        } catch (Exception e) {
            log.error("Something went wrong during results unification!", e);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "An error occurred! Details : " + e.getMessage(), "An error occurred! Details : " + e.getMessage()));
        }

        return null;
    }

    public LeaderboardHandler getLeaderboardsHandler() {
        return leaderboardsHandler;
    }

    public void setLeaderboardsHandler(LeaderboardHandler leaderboardsHandler) {
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

    public Boolean getMultithreaded() {
        return multithreaded;
    }

    public void setMultithreaded(Boolean multithreaded) {
        this.multithreaded = multithreaded;
    }
}
