FROM java:8
MAINTAINER Nilhcem "nilhcem@gmail.com"

RUN DEBIAN_FRONTEND=noninteractive apt update
RUN DEBIAN_FRONTEND=noninteractive apt install -y wget unzip
RUN wget -q http://dist.sonar.codehaus.org/sonarqube-5.1.zip
RUN unzip -qq sonarqube-5.1.zip -d /opt/
RUN rm sonarqube-5.1.zip

EXPOSE 9000
EXPOSE 9092

CMD ["/opt/sonarqube-5.1/bin/linux-x86-64/sonar.sh", "console"]
