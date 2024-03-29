<html>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f" %>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h" %>
<%@ taglib prefix="a4j" uri="http://richfaces.org/a4j" %>
<%@ taglib prefix="rich" uri="http://richfaces.org/rich" %>
<head>
    <%@ page contentType="text/html;charset=UTF-8" language="java" %>
    <meta http-equiv=Content-Type content="text/html; charset=utf-8">
    <title>Leaderboard</title>
    <link rel="STYLESHEET" type="text/css" href="styles/css.css">
</head>
<f:view>
    <f:loadBundle basename="messages" var="translationBundle"/>
    <body>
    <a4j:keepAlive beanName="confBean"/>
    <a4j:status id="ajaxStatus1" onstart="#{rich:component('pleaseWaitPanel')}.show();" onstop="#{rich:component('pleaseWaitPanel')}.hide();" />
    <h:form>
        <h:panelGrid id="mergerPanel" columns="1" style="margin-left:1%;" cellspacing="5">
            <%--    Configuration drop-down menu    --%>
            <h:panelGroup>
                <h:outputText value="Configuration : " style="font-weight:bold;"/>
                <h:selectOneMenu id="configuration" value="#{confBean.configurationName}" required="true">
                    <f:selectItem itemValue="" itemLabel=""/>
                    <f:selectItems value="#{confBean.availableConfigurations}"/>
                    <a4j:support event="onchange" action="#{confBean.assignConfiguration}" reRender="mergerPanel" ajaxSingle="true"/>
                </h:selectOneMenu>
                <rich:message for="configuration">
                    <f:facet name="errorMarker">
                        <h:graphicImage value="images/button_drop.png"/>
                    </f:facet>
                    <f:facet name="infoMarker">
                        <h:graphicImage value="images/button_ok.png"/>
                    </f:facet>
                    <f:facet name="warnMarker">
                        <h:graphicImage value="images/exclamation.png"/>
                    </f:facet>
                </rich:message>
            </h:panelGroup>

            <%--    Configuration description   --%>
            <h:panelGroup id="descriptionPanel">
                <h:panelGroup rendered="#{confBean.configurationName ne ''}">
                    <h:outputText value="Description : " style="font-weight:bold;"/>
                    <h:outputLabel value="#{confBean.configurationDescription}" escape="false"/>
                </h:panelGroup>
            </h:panelGroup>

            <%--    Additional Configuration    --%>
            <h:panelGroup id="emailPanel">
                <h:panelGroup rendered="#{confBean.selectedLeaderboardConf.publisher eq 'emailPublisher'}">
                    <h:outputText value="Email : " style="font-weight:bold;"/>
                    <h:inputText id="email" value="#{confBean.email}" required="true">
                        <f:validator validatorId="zisist.EmailValidator"/>
                    </h:inputText>
                    <rich:message for="email">
                        <f:facet name="errorMarker">
                            <h:graphicImage value="images/button_drop.png"/>
                        </f:facet>
                        <f:facet name="infoMarker">
                            <h:graphicImage value="images/button_ok.png"/>
                        </f:facet>
                        <f:facet name="warnMarker">
                            <h:graphicImage value="images/exclamation.png"/>
                        </f:facet>
                    </rich:message>
                </h:panelGroup>
            </h:panelGroup>

            <h:panelGroup>
                <h:panelGroup rendered="#{confBean.configurationName eq 'multiThreadSupportConfiguration'}">
                    <h:selectBooleanCheckbox id="checkbox" value="#{confBean.multithreaded}" title="click it to select or deselect"/>
                    <h:outputText value="Multithreaded Execution"/>
                </h:panelGroup>
            </h:panelGroup>
            <%--    Execute algorithm steps    --%>
            <h:panelGrid columns="1">
                <a4j:commandButton id="publishResults" process="emailPanel" value="Publish" action="#{confBean.execute}" reRender="mergerPanel"/>
                <rich:messages globalOnly="true" id="generalMessages">
                    <f:facet name="errorMarker">
                        <h:graphicImage value="/images/button_drop.png"/>
                    </f:facet>
                    <f:facet name="infoMarker">
                        <h:graphicImage value="/images/button_ok.png"/>
                    </f:facet>
                </rich:messages>
            </h:panelGrid>
        </h:panelGrid>
    </h:form>
    </body>
    <rich:modalPanel id="pleaseWaitPanel" height="80" autosized="true" zindex="2001"
                     style="background-color:transparent;margin-left: auto;margin-right: auto;border:none;"
                     shadowOpacity="0">
        <h:graphicImage value="/images/ajax-loader-Big-snake.gif"/>
    </rich:modalPanel>
</f:view>
</html>