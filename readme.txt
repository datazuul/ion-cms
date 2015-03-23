requirement :
	- tomcat 4.1.18
	- ant 1.5
	- jdk 1.4
	- mysql or hsqldb

------------------------------
1. copy .ant.properties.sample to .ant.properties
2. edit .ant.properties to adjust settings
3. run ant initdb to create tables on your database (only if database.type is set to mysql or hsql)
4. run ant install
5. start tomcat

you should be abble to acceed ion apllications at :
	frontoffice: http://localhost:8080/
	backoffice: http://localhost:8080/backoffice
	ion admin: http://localhost:8080/ion

3 users aree created by the initdb task :
	user/user
	webmaster/webmaster
	admin/admin
