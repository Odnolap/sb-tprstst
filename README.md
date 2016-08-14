Payment table with adding, confirming and searching.<br>
Project using java 1.7 and Spring Boot. "Fat JAR" doesn't need Tomcat.<br>
Web-interface (only searching) - localhost:8080/payments<br>
REST prepare (adding) - GET localhost:8080/rest/prepare?(article, contragent_id, contragent_time)<br>
REST pay (confirming) - GET localhost:8080/rest/pay?(payment_id, sum)<br>