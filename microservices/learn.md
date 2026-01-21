# API gateway
Each services have own port, Eg. 
user-service :8081/
order-service :8082/

Need using API gateway : Spring Cloud Gateway, Nginx
mapping 8081 and 8021 into 8080

# Run & Develop
Binding mount each services
Using 1 container DB, each services have schema
- user-schema
- order-schema

# Eureka Discovery Server (Optional)
- Should use when all services built with Spring
Eureka server/client

Subscribe each services with Eureka client 
- localhost:port/ -> lb:port/

This help a lot when use Docker 

# OpenFegin (Optional)
- Calling services like 'fucntion' ?
- Only use when Spring -> Spring 
