# Faza izgradnje
FROM maven:3.8.4-openjdk-17 AS build
# Kopiranje vašeg izvornog koda i pom.xml u kontejner
COPY src /home/app/src
COPY pom.xml /home/app
# Izgradnja aplikacije bez pokretanja testova
RUN mvn -f /home/app/pom.xml clean package -DskipTests

# Faza pokretanja
FROM openjdk:17-slim
# Kopiranje izgrađene aplikacije iz faze izgradnje
COPY --from=build /home/app/target/filmskiKatalog-0.0.1-SNAPSHOT.jar /app/filmskikatalog.jar
# Kreiranje direktorija za uploadove
RUN mkdir /app/uploads
# Postavljanje radnog direktorija
WORKDIR /app
# Expose port aplikacije
EXPOSE 8080
# Pokretanje aplikacije
ENTRYPOINT ["java","-jar","/app/filmskikatalog.jar"]
