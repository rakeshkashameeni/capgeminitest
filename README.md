# Selenium Test
- You need to clone repo in IntelliJ IDEA
- Test assumes that you are running chrome 89. If you are running different version of chrome, you will need to downlaod and save the compatible chromedriver in project root 
- Make sure that cucumber plugin is installed
- then right click on feature file and run the test

# API tests
- Clone repo
- Make sure NodeJS is installed on your machine
- then run npm install
- create .env file and add KEY={API Key for Weather API}
- then run npx mocha ./json2/weather_tests.js

# Test Strategy for Selenium

- Tech Stack - Java, Maven, Cucumber
- Website's calculate rego fees feature should work on Chrome, Firefox, Edge, Safari in desktop and mobile mode as well
- Verify that fees are displayed in 3 Month, 6 Month and 12 month format
- Verify that all combinations of vehicle type, fule type, concession card are tested
- Verify that user is able to start the process all over again and previous car details are wiped out.


# Test Strategy for API tests

- Tech Stack - Java script, Mocha and Chai. JavaScript is very easy when working with JSON object 
- Verify that both weather and pollution endpoints return invalid api key error if invalid key is sent in query string
- Verify that the schema of both weather and pollution endpoint is matching with expected schema
- Verify that "city is not found" error is sent in response if invalid city is sent in query string
