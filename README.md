# Spring Integration with WebClient

I am attempting to perform a simple set of operations to retrieve a set of objects and
enrich them with additional information.

This is a contrived example but is representative of the operation I'm trying to perform.

There are 5 total endpoints in use

* /myobject - Gets a list of IDs of MyObject available in the system
* /myobject/{id} - Gets a singular MyObject based on `id`
* /myobject/{id}/secondary - Gets the secondary information of MyObject with `id`
* /myobject/{id}/tertiary - Gets the tertiary information of MyObject with `id`
* /integration-flow - Attempts to execute an integration flow

## Flow Diagram

![Integration Flow Diagram](https://github.com/djgraff209/reactive-spring-integration/blob/master/doc/Integration%20Flow.drawio.png?raw=true)
