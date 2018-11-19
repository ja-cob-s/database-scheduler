## Database Scheduler
Project for my BSCS degree.

### Introduction
This project is a desktop application made in Java using NetBeans and Scene Builder. The application displays consulants' appointment schedules with customers. There is functionality to add, update, and remove appointments and customers. It accesses the following tables in the mySQL database: **address**, **appointment**, **city**, **country**, **customer**, and **user**. A full ERD can be found in the `/resources` directory.

### Key Features
* Alerts when user has appointments within 15 minutes of login
* Display appointments by type and by week or month
* Appointment times shown converted from UTC to Local Time
* Report for number of appointments by type and by month
* Report of each consultant's schedule
* Report of number of customers by city
* Locale-based and can be set to two system languages - English and German
* Log file that tracks user login attempts

### Getting Started
* Clone this repository: `https://github.com/ja-cob-s/database-scheduler.git`
* Add NetBeans project or import into your IDE of choice
* MySQL connector file can be found in `/resources` directory
* Login with username `test` and password `test`
     ```
     Connection String
     Server name:  52.206.157.109 
     Database name:  U05Mc2
     Username:  U05Mc2
     Password:  53688542146
     ```