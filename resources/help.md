Name of the environment variable: SONAR_TOKEN 
Value of the environment variable: 2b41922a04cfc17aeae7a6157412097008804415 
Execute the SonarScanner for Maven from your CI
Update your pom.xml file with the following properties:
<properties>
  <sonar.projectKey>jahanvibansal_OnlineBankingSystems</sonar.projectKey>
  <sonar.organization>payalbnsl-github</sonar.organization>
  <sonar.host.url>https://sonarcloud.io</sonar.host.url>
</properties>
Run the following command in the project folder:

mvn verify org.sonarsource.scanner.maven:sonar-maven-plugin:sonar