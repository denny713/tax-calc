Tax Calculate System Using Springboot

Requirement :

Java JDK 17
Maven
Spring Boot

Step :
Clean Project Terlebih dahulu menggunakan command 'mvn clean install' tanpa tanda kutip.
Run Project menggunakan command 'mvn spring-boot:run' tanpa tanda kutip.
Untuk mengetahui Coverage Code nya bisa menggunakan command 'mvn jacoco:report' setelah itu bisa dilihat di folder '
target/site/jacoco/index.html'
Testing bisa dilakukan via Swagger via link 'localhost:7878/tax/swagger-ui/index.html'.
Terdapat juga Unit Testing pada package '/src/test/java'.
Testing bisa juga dilakukan menggunakan Postman Tools curl berikut:

//Calculate Tax curl --location 'localhost:7878/tax?7000'

Happy Coding.

Created By: Denny Afrizal