# Weather-Station
A Java GUI Weather station with a Live dashboard and 24 hr graphs for Australians

This Java application can be run on any system where the JVM is welcome and can be left open at all times thanks to its automatic updating and ease of use.

I designed this program to be clear and simple in displaying Bureau Of Meteorology weather information. 

#How it works
The application first requests the user's postcode. It then searches for the closest weather station on the BOM webiste, then downloads the JSON file containing the latest observations and displays the results as both a most recent dashboard and 24 hr graph. 

In the background when the program initializes, the full list of weather stations is downloaded, unzipped, cleaned of none useful fields and then parses into an ArrayList. There are over 20,000 stations.

Not all stations have observations. Only about 900 have useable JSON files and, of these, some have NULL values for some fields, like air temp.

The program then requests the user's postcode. 

The postcode search starts at the user's postcode and then if there are no stations with that postcode then a new search is done for the next postcode up and down. 
Eg 4605 - no match --> 4606 - no match --> 4604 - match

The process continues until a match can be found.

The matching process firsts gets suburb names from online and then checks each one to find a station with a similar name. It then checks if that station has a JSON observation file. 

The matching file is then downloaded and checked for Null obseravations. If some are found the station is blacklisted and the program searches for another station. When a good station is found the data is parsed and output to the dashboard and graph panels.

On the graph panel, the observation set can be selected and viewed, for example: air temperature, wind speed and humidity.

#Getting Started
Download the Zip and build using your favourite IDE or compiler. 
