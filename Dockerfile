#build app using maven
FROM maven:3.6-jdk-8-slim AS build
RUN mkdir -p /app
WORKDIR /app
ADD source/src /app/src
ADD source/pom.xml /app
RUN ls -la
RUN mvn clean install


FROM jboss/wildfly:latest

# Application server
ENV WILDFLY_USER admin
ENV WILDFLY_PASS password
ENV JBOSS_CLI /opt/jboss/wildfly/bin/jboss-cli.sh
ENV DEPLOYMENT_DIR /opt/jboss/wildfly/standalone/deployments/

# Database
ENV DB_NAME shopdb
ENV DB_USER maria
ENV DB_PASS maria
ENV DB_URI db:3306
ENV DATASOURCE_NAME shopDS
ENV MARIADB_CONNECTOR_VERSION 2.3.0


# Add admin user
RUN $JBOSS_HOME/bin/add-user.sh -u $WILDFLY_USER -p $WILDFLY_PASS --silent

# Start Wildfly server
RUN bash -c '$JBOSS_HOME/bin/standalone.sh &' && \
# Wait for the server to boot
      bash -c 'until `$JBOSS_CLI -c ":read-attribute(name=server-state)" 2> /dev/null | grep -q running`; do echo `$JBOSS_CLI -c ":read-attribute(name=server-state)" 2> /dev/null`; sleep 1; done' && \
# Download MariaDB driver
      curl --location --output /tmp/mariadb-java-client-${MARIADB_CONNECTOR_VERSION}.jar --url https://downloads.mariadb.com/Connectors/java/connector-java-${MARIADB_CONNECTOR_VERSION}/mariadb-java-client-${MARIADB_CONNECTOR_VERSION}.jar && \
# Add MariaDB module
      $JBOSS_CLI --connect --command="module add --name=org.mariadb --resources=/tmp/mariadb-java-client-${MARIADB_CONNECTOR_VERSION}.jar --dependencies=javax.api,javax.transaction.api" && \
# Add MariaDB driver
      $JBOSS_CLI --connect --command="/subsystem=datasources/jdbc-driver=mariadb:add(driver-name=mariadb,driver-module-name=org.mariadb,driver-xa-datasource-class-name=org.mariadb.jdbc.MariaDbDataSource)" && \
# Create a new datasource
      $JBOSS_CLI --connect --command="data-source add \
        --name=${DATASOURCE_NAME} \
        --jndi-name=java:/simpleShop/${DATASOURCE_NAME} \
        --user-name=${DB_USER} \
        --password=${DB_PASS} \
        --driver-name=mariadb \
        --connection-url=jdbc:mysql://${DB_URI}/${DB_NAME} \
        --use-ccm=false \
        --max-pool-size=25 \
        --blocking-timeout-wait-millis=5000 \
        --enabled=true" && \
# Shutdown WildFly
      $JBOSS_CLI --connect --command=":shutdown" && \
# Clean up
      rm -rf $JBOSS_HOME/standalone/configuration/standalone_xml_history/ $JBOSS_HOME/standalone/log/* && \
      rm -f /tmp/*.jar

# Expose webapp and admin ports
EXPOSE 8080 9990

# Copy war file to deployment directory
COPY --from=build /app/target/SimpleShop-1.0.war $DEPLOYMENT_DIR/SimpleShop-1.0.war

# Start WildFly
CMD ["/opt/jboss/wildfly/bin/standalone.sh", "-b", "0.0.0.0", "-bmanagement", "0.0.0.0"]