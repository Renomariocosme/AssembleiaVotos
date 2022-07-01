FROM openjdk:11
RUN mkdir /app
COPY /target/*.jar /app/solutis-desafio.jar
EXPOSE 5000
ENTRYPOINT ["java", "-jar","/apps/E:\Downloads\zip.solutis.jar"]