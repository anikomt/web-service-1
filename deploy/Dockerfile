FROM openjdk:8
ARG ADMIN_PASS=admin

ENV GLASSFISH_BASE /usr/local/glassfish
ENV GLASSFISH_HOME $GLASSFISH_BASE/glassfish4
ENV DOMAIN_HOME $GLASSFISH_HOME/glassfish/domains/domain1

RUN mkdir $GLASSFISH_BASE
WORKDIR $GLASSFISH_BASE

RUN wget http://download.oracle.com/glassfish/4.0/release/glassfish-4.0.zip\
        && unzip glassfish-4.0.zip\
        && rm glassfish-4.0.zip
RUN wget https://jdbc.postgresql.org/download/postgresql-42.2.5.jar\
        && mv postgresql-42.2.5.jar $DOMAIN_HOME/lib/

WORKDIR $GLASSFISH_HOME
COPY domain.xml $DOMAIN_HOME/config
COPY target/deploy-1.0.war /build/



ENV ASADMIN_CMD $GLASSFISH_HOME/bin/asadmin
ENV PWD_FILE $GLASSFISH_BASE/pwd
ENV PWD_CHANGE_FILE $GLASSFISH_BASE/changepwd
ENV PWD_ALIAS_FILE $GLASSFISH_BASE/alias

WORKDIR $GLASSFISH_BASE
RUN $ASADMIN_CMD start-domain && \
    echo 'AS_ADMIN_PASSWORD=' > $PWD_CHANGE_FILE && \
    echo "AS_ADMIN_NEWPASSWORD=$ADMIN_PASS" >> $PWD_CHANGE_FILE && \
    echo "AS_ADMIN_PASSWORD=$ADMIN_PASS" >> $PWD_FILE && \
    echo "AS_ADMIN_PASSWORD=" >> $PWD_ALIAS_FILE && \
    echo "AS_ADMIN_ALIASPASSWORD=$ADMIN_PASS" >> $PWD_ALIAS_FILE && \
    $ASADMIN_CMD --interactive=false --user admin --passwordfile $PWD_ALIAS_FILE create-password-alias ad && \
    $ASADMIN_CMD --interactive=false enable-secure-admin-internal-user --passwordAlias ad admin && \
    $ASADMIN_CMD --interactive=false --user admin --passwordfile $PWD_CHANGE_FILE change-admin-password --domain_name domain1 && \
    $ASADMIN_CMD --interactive=false enable-secure-admin && \
    $ASADMIN_CMD stop-domain

COPY start.sh .
RUN chmod a+x start.sh

EXPOSE 4848 8080 8181
CMD ./start.sh
