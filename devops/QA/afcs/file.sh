#/bin/bash
#versionTimestamped=wget -q -O- --no-check-certificate "http://192.168.0.91:8081/repository/maven-snapshots/RKI-TMS/chatak-tms/4.0.0-SNAPSHOT/maven-metadata.xml" | grep -m 1 \<value\> | sed -e 's/<value>\(.*\)<\/value>/\1/' | sed -e 's/ //g'
#echo $test

afcs=$(curl -L $URL/repository/maven-snapshots/$groupid/$artifactid1/4.0.0-SNAPSHOT/maven-metadata.xml | grep -m 1 \<value\> | sed -e 's/<value>\(.*\)<\/value>/\1/' | sed -e 's/ //g')
wget --no-check-certificate "$URL/repository/maven-snapshots/$groupid/$artifactid1/4.0.0-SNAPSHOT/$artifactid1-${afcs}.jar" -O $artifactid1.jar

#transit=$(curl -L $URL/repository/maven-snapshots/$groupid/$artifactid2/4.0.0-SNAPSHOT/maven-metadata.xml | grep -m 1 \<value\> | sed -e 's/<value>\(.*\)<\/value>/\1/' | sed -e 's/ //g')
#wget --no-check-certificate "$URL/repository/maven-snapshots/$groupid/$artifactid2/4.0.0-SNAPSHOT/$artifactid2-${transit}.war" -O $artifactid2.war
