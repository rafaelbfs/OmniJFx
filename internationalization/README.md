# OmniJFX/Intenationalization

This module provides internationalization functions for Java standalone applications. 
It can parse the resources from `.properties` and `.yaml` files. In the `YAML` format, this library 
can flatten the structure turning it to a `.properties`-like structure that fits in a single `Map` each 
level of the key hierarchy is qualified by a `.` dot, just like in `java` properties.

Alternatively, `systems.terranatal.omnijfx.internationalization.yaml.ObjectYamlResourceBundle` can be used to
parse the YAML file directly into a POJO, by using SnakeYAML `Constructors`, an example can be found in 
`systems.terranatal.omnijfx.internationalization.yaml.Sample` and in the test cases that use it.
