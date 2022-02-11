**MemoryStore Cache with CloudSQL**

A cache is a high-performance data storage layer that stores a portion of data which is typically transient in nature. The whole purpose of cache is to reduce the latency and serve the data faster. The Process of storing the data in cache is termed as Caching.

we will look at Read Through Cache Mechanism where the Application first tries to query the cache. If the Cache has that data it returns back to the application. If not, then the application queries the database directly and saves the data to cache so that it could be fetched from there in the subsequent calls, thus reducing the latency.


**Diagram**

![alt text](https://github.com/ajitesh-google/memstore-with-CloudSQL/blob/main/Untitled%20Diagram.drawio.png)

This uses MemoryStore as the Cache and CloudSQL as the backend database. I will showcase a small code on how to connect to MemoryStore and implement read through cache.


**Initial Provisioning :**
For the Demo, I have provisioned the below -

1. Created a Database “employees” and a table “session_data” using create CloudSQL instance. 
2. The table has two columns - user_id and session_id. You can use the below queries for dummy data
      create table session_data (session_id char(20), user_id char(20));

      insert into session_data values('ABSBD27376','XYZ');
      insert into session_data values('Fsgg54ZXCs','ABC');
      insert into session_data values('VVBGTER123','QWERT');
      insert into session_data values('NB892939RW','MBXC');

      select * from session_data;

 3.	Create a MemoryStore using create MemoryStore Instance








