![Karumi logo][karumilogo] KataTODOApiClient for Kotlin [![Build Status](https://travis-ci.org/Karumi/KataTODOApiClientKotlin.svg?branch=master)](https://travis-ci.org/Karumi/KataTODOApiClientKotlin)
============================

- We are here to practice integration testsing using HTTP stubbing. 
- We are going to use [MockWebServer][mockwebserver] to simulate a HTTP server.
- We are going to use [JUnit][junit] to perform assertions.
- We are going to practice pair programming.

---

## Getting started

This repository contains an API client to interact with a remote service we can use to implement a TODO application.

This API Client is based on one class with name ``TodoApiClient`` containing some methods to interact with the API. Using this class we can get all the tasks we have created before, get a task using the task id, add a new task, update a task or delete an already created task.

The API client has been implemented using a networking framework named [Retrofit][retrofit]. Review the project documentation if needed.

## Tasks

Your task as a Kotlin Developer is to **write all the integration tests** needed to check if the API Client is working as expected.

**This repository is ready to build the application, pass the checkstyle and your tests in Travis-CI environments.**

Our recommendation for this exercise is:

  * Before starting
    1. Fork this repository.
    2. Checkout `kata-todo-api-client` branch.
    3. Execute the repository playground and make yourself familiar with the code.
    4. Execute `TodoApiClientTest` and watch the only test it contains pass.

  * To help you get started, these are some tests already written at `TodoApiClientTest ` class. Review it carefully before to start writing your own tests. Here you have the description of some tests you can write to start working on this Kata:
	1. Test that the ``Accept`` and ``ContentType`` headers are sent.
    2. Test that the list of ``TaskDto`` instances obtained invoking the getter method of the property ``allTasks``  contains the expected values.
    3. Test that the request is sent to the correct path using the correct HTTP method.
    4. Test that adding a task the body sent to the server is the correct one.

## Considerations

* If you get stuck, `master` branch contains all the tests already solved.

* You will find some utilities to help you test the APIClient easily in:
  ``MockWebServerTest`` and the test resources directory.

## Extra Tasks

If you've covered all the application functionality using integration tests you can continue with some extra tasks: 

* Replace some integration tests we have created with unit tests. A starting point could be the ``DefaultHeadersInterceptor`` class.
* Create your own API client to consume one of the services described in this web: [http://jsonplaceholder.typicode.com/][jsonplaceholder]

---

## Documentation

There are some links which can be useful to finish these tasks:

* [Kata TODO Api Client in Java][kataTodoApiClientJava]
* [MockWebServer official documentation][mockwebserver]
* [JUnit documentation][junit]
* [Retrofit documentation][retrofit]
* [World-Class Testing Development Pipeline for Android - Part 3][wordl-class-testing-development-pipeline]

# Contributors

Thank you all for your work!

| [<img src="https://avatars2.githubusercontent.com/u/201209?v=4" width="100px;"/><br /><sub><b>Igor Ganapolsky</b></sub>](https://github.com/IgorGanapolsky) |
| :---: |

# License

Copyright 2017 Karumi

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

  http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.

[karumilogo]: https://cloud.githubusercontent.com/assets/858090/11626547/e5a1dc66-9ce3-11e5-908d-537e07e82090.png
[mockwebserver]: https://github.com/square/okhttp/tree/master/mockwebserver
[junit]: https://github.com/junit-team/junit
[jsonplaceholder]: http://jsonplaceholder.typicode.com/
[wordl-class-testing-development-pipeline]: http://blog.karumi.com/world-class-testing-development-pipeline-for-android-part-3/
[retrofit]: http://square.github.io/retrofit/
[kataTodoApiClientJava]: https://github.com/Karumi/KataTODOApiClientJava
