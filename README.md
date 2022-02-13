# Frederico's notes

I decided to develop this assignment in a Java Spring project, using Maven. I chose java 14. It is only necessary to download the project, build and run using Maven. After it is already running, you can use swagger to test it:
http://localhost:8080/swagger-ui.html

Alternatively you can use the file Insomnia_assigment_fred.json to import the services urls in insomnia, or manually add the same services in another API testing tool.
It is auto-magically inserted the examples given in the assignment, so you can find results on the get services, even before any attempt to insert data. Such as the result for http://localhost:8080/availabilities-matches if you provide this list of ids as body: [1,2,3]
