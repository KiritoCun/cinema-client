FROM findepi/graalvm:java17-native

MAINTAINER HoaLD

RUN mkdir -p /eport/vict/server/logs \
    /eport/vict/server/temp \
    /eport/vict/skywalking/agent

WORKDIR /eport/vict/server

ENV SERVER_PORT=8080

EXPOSE ${SERVER_PORT}

ADD ./target/cinema-admin.jar ./app.jar

ENTRYPOINT ["java", \
            "-Djava.security.egd=file:/dev/./urandom", \
            "-Dserver.port=${SERVER_PORT}", \
            # Application name If you want to distinguish cluster node monitoring, just change it to a different name
#            "-Dskywalking.agent.service_name=cinema-server", \
#            "-javaagent:/eport/vict/skywalking/agent/skywalking-agent.jar", \
            "-jar", "app.jar"]
