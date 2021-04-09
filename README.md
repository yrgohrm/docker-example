# Simple Docker Example

This is a very simple example of what we can do with Docker and Docker Compose.
It is made for students at Yrgo to get a feel for what Docker can do as a
tool for developers.

It is not intended to show off any best practice, nor are the example programs
in here anything other than very simplistic examples based on things we have
talked about in some lectures here and there.

## Get it all up and running

To build the projects and build the docker images you need the following tools:
* Maven
* NodeJS & NPM
* Docker Desktop

The tools need to be on the PATH.

If you want to you can modify the two passwords in `.backend.env` and 
`mssql.env`. They must match and meet the complexity requirements of 
Microsoft SQL Server.

Then, from the root directory, simply run `build.cmd` (Windows) or `build.sh` 
(Linux/MacOS). It will compile everything and build the Docker images for 
Docker Compose.

Then run `docker-compose up` and it should start three docker images in a
Docker Compose.

The containers will expose port 8080 for the web interface and 1433 for the
MS SQL Server Express database.

To stop them run `docker-compose down` from the root directory and they will
be shut down and the containers will be removed.