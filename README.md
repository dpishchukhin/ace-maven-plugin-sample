ace-maven-plugin-sample
=======================

Test of org.ops4j.pax.ace-maven-plugin

- download Apache ACE
- unpack server and target
- run server
- connect to http://localhost:8080 (u: d, p: f)
- Add Artifact: add AutoConf Resource Processor to support OSGi configuration (ACE trick)
- run target (java -Ddiscovery=http://localhost:9090 -jar target.jar)
- mvn -P install-runtime-bundles - install runtime bundles (OSGi DS)
- mvn -P register-targets - register ACE Targets
- mvn deploy - bundles should be built and deployed
- mvn -P create-distributions - create features and distributions
- mvn -P install-distribution - connect distributions and targets and approve installation
- test on target that new bundles are installed and run

please use ACE admin console to track changes