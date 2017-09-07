INSTALLATION STEPS OF THE SOAF-LOGGER:

1-
JMS Reporting Provider needs to be untargeted:
- Weblogic console -> Deployments -> JMS Reporting Provider -> Targets -> Untarget Enterprise Application + EJB

2-
Deploy soaf-logger.ear manually after weblogic is started

3-
Change the default deployment order value of 100 to 150.