# Trippy Application

## Overview
This is a simple aplication providing users with the ability to give and retrieve data, like general information and reviews from other users, about different venues, in particular restaurants, bars and hotels in different cities. 

## Guidelines
1. Clone this repository

2. Go to TrippyApplication.java and start run()

3. Once the java app starts, one should be able to execute different CRUD operation and see the result whether in Postman or whichever Web browser. All one need is to connect to the http://localhost:8080 port. The java app will wait and try to connect to Postgres Database.

4. Once run() is started and Java app connects successfully, a user can choose to print information about other users, venues and reviews by providing the appropriate paht. More over, one can add, update and delete certain items, again by providing the appropriate path and information. 

# Functionalities 
### Implemented
1. Getting the list of establishments
2. Filtering venues according to their type, city and rating
3. Venue implementation with name, city in which it is located, type (hotel, bar or restaurant), number of reviews, average rating and address.
4. Adding a new venue, if such does not already exist with the same name and city
5. Updating the address of an existing venue
6. Creating a review for a venue by choice
7. Retrieving all reviews for a particular venue
8. Review implementation with username of the user who left it, the creation date, the venue being reviewed, rating and the review text itself.
9. Creating a new user with information about username, email, city they live in and the date at which they joined


### Not implemented
1. Filtering users by id, username or email
2. Users list of reviews they left
3. Updating or deleting of reviews only by the person who left them

## License
MIT
The code in this repository is covered by the included license.