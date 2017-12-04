
# Mini project in Jooby
## Project Description
Cheque Management System API

The system would provide the following functions
1) User management : create users accepting username, password , branch and role.
2) Cheque management: Create and assign new cheques. To create a cheque you need to accept the bank name, start and end number of the cheque since cheques are serial. You are to assign cheques to users who have the branch manager role.
3) Disbursement Request: User's with loan officer role should be able to request for cheques to be issued. The system must accept the customer's name, customer number , loan amount . These information would be stored on the database.

## Setup

The configuration settings are found in the application.conf file. Mysql is the default DBMS.
Set the port and db name in the db.url configuration to match your database. To use an in-memory 
database remove the db configuration and add ```db = mem```


## Running

    mvn jooby:run
Running the maven command mvn jooby:run will start up the server on port 8888.


## Functions
The application adds three users on first run, one for each role. It also adds four branches.

Branches include:

Head Office - 0001
Ojuelegba Road - 0002
John Road - 0003
Marina - 0004
Ajose Adeogun - 0005

Role Types:

ADMIN
BRANCH_MANAGER
LOAN_OFFICER

### Summary of Endpoints
  POST /users               [*/*]     [*/*]    (/anonymous)
  GET  /users/{id:\d+}      [*/*]     [*/*]    (/anonymous)
  GET  /users               [*/*]     [*/*]    (/anonymous)
  POST /cheques             [*/*]     [*/*]    (/anonymous)
  GET  /cheques             [*/*]     [*/*]    (/anonymous)
  GET  /cheques/{id:\d+}    [*/*]     [*/*]    (/anonymous)
  POST /loans               [*/*]     [*/*]    (/anonymous)
  GET  /loans/{id:\d+}      [*/*]     [*/*]    (/anonymous)
  GET  /loans               [*/*]     [*/*]    (/anonymous)
  GET  /branches            [*/*]     [*/*]    (/anonymous)
  POST /branches            [*/*]     [*/*]    (/anonymous)

 The required inputs for the API endpoints can be found in the DTO classes in the ```mini.jooby.dtos``` package.
 
Authentication is done using Basic Authentication.
