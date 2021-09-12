## JetBrains Academy Java Challenging course
_[Car Sharing track at hyperskill](https://hyperskill.org/projects/140?track=1)_
### What can I find here?
I uploaded some [tasks](./Problems) from _Java Backend Developer_ track and the 
[final project](./Car%20Sharing/task/src/carsharing).
I'm sharing my feedback on the track here.

### What is the project?
Console application "Car sharing" for clients and managers, which allows editing the list of
cars, and car owning companies.

### What did I learn?
- Relational databases
    - Database connectivity
    - Writing SQL queries
    - Handling the results
- Code reusing

### About the track
I was expecting for this track to be better at these:
- #### Organizing the database connection in production-ready style

    Nothing was said about that. In fact, the only examples were given 
    at [H2 Database](https://www.h2database.com/html/main.html).
  I was expecting for this track to learn to work with JDBC / JPA.
  
- #### Safe transactions
  
    This track does not cover transactions and Spring at all.
    I did not learn anything about Optimistic / Pessimistic locks,
    retrying requests and rollbacks.   
  
    _Example:_ Two clients are renting the same car at the same time.
    The track does not require implementing protection from such cases.
  
- #### Too much console interface code

    The result is a simple REST service with overcomplicated code.
    It would be much simpler and more beautiful with Spring.
  
    Most of the time I was bored writing new options to 
    different State implementations. I believe I could write 
    it better, but paid subscription left no choice.  
  
