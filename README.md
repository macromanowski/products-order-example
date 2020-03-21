# Product Order example

# Prerequisite
Created & tested on Ubuntu 18.04

## Dependencies:
* Java 11 (tested against OpenJDK)
* Gradle (tested against 5.2.1)
* Docker CE (tested against 19.03.8)
* Docker Compose (tested against 1.25.4)

# Set up
## Building environment
Following script will build, run (in detached mode) & create new environment via docker-compose
```
./build_locally.sh
```

## Stop environment
```
./stop_locally.sh
```

## Start previously built environment
```
./start_locally.sh
```

## Set up details
If containers weren't deleted then stored data should survive all restarts.
More detailed info about environment setup can be found in `docker/docker-compose.yml`

# Available API
All available API can be found in `postman/ProductOrderExample.postman_collection.json` (please use [Postman](https://www.postman.com/downloads/) to load it)

# Current solution
## Assumptions made
* Once product has been added to an order it cannot be deleted afterwards - we want to ensure that in the future old orders will not contain only part of relevant data
* Orders will always viewed fully - that means there is no reason (at least for now) that someone wants to see only order summary (i.e. email & price ony)
* Above assumption is also applied for products

# Further development
## Authentication
We might use JWT Token for checking user roles/permissions. In this case user receives a token from some Authentication/Authorization server and uses it for further call with the service.
Thanks to that service needs to only check if token was actually signed by trusted server and check permissions (thanks to encoded payload).

## Is service redundant?
Yes, especially if we need more operations needed to be done on products or orders. Right now we're making one service responsible for creating/updating/showing products as also creating/viewing orders. 
In my opinion, current service has too many responsibilities and it will very fast grow to a big monolith. But, to make it properly, we need to understand the domain and processes being made within and then make a decisions which parts should belong where.
Then we can decide how to structure our architecture and create proper services.