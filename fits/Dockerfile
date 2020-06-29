FROM tomcat:8.5.51-jdk8-openjdk

ENV CATALINA_BASE "/usr/local/tomcat"
ENV FITS_PORT 8080


RUN apt update && \
    apt install -y file && \
    apt clean && \
    rm -rf /var/lib/apt/lists/

RUN wget https://github.com/harvard-lts/fits/releases/download/1.5.0/fits-1.5.0.zip && \
    unzip fits-1.5.0.zip -d /fits/ && \
    rm fits-1.5.0.zip

RUN wget https://projects.iq.harvard.edu/files/fits/files/fits-1.2.1.war -O $CATALINA_BASE/webapps/fits.war


RUN echo 'fits.home=/fits/' >> ${CATALINA_BASE}/conf/catalina.properties
RUN echo "shared.loader=/fits/lib/*.jar" >> $CATALINA_BASE/conf/catalina.properties
RUN echo "http.port=${FITS_PORT}" >> $CATALINA_BASE/conf/catalina.properties



ENTRYPOINT ["catalina.sh"]
CMD ["run"]

