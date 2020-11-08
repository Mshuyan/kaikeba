-XX:CMSInitiatingOccupancyFraction

+CMSClassUnloadingEnabled



-server -Xms1280m -Xmx1280m **-Xmn128m** -Xss256k -XX:PermSize=96m -XX:MaxPermSize=96m -XX:+UseConcMarkSweepGC -XX:+UseParNewGC -XX:CMSFullGCsBeforeCompaction=1 -XX:+UseCMSCompactAtFullCollection -XX:+CMSParallelRemarkEnabled -XX:+UseCMSInitiatingOccupancyOnly -XX:CMSInitiatingOccupancyFraction=70 -XX:+CMSClassUnloadingEnabled -XX:+DisableExplicitGC -verbose:gc -XX:+PrintGCDetails -XX:+PrintGCTimeStamps