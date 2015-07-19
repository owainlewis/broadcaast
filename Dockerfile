FROM dockerfile/java:oracle-java8
MAINTAINER Owain Lewis <owain@owainlewis.com>
EXPOSE 9000
WORKDIR /opt/docker
ADD opt /opt
RUN ["chown", "-R", "daemon:daemon", "."]
USER daemon
ENTRYPOINT ["bin/discusslr", "-Dconfig.resource=application.prod.conf", "-DapplyEvolutions.default=true"]
CMD []
