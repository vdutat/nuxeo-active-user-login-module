# nuxeo-active-user-login-module

This plugin allows to to disable a user or to define a deactivation date and time on a user, the said user won't be able to login anymore.
2 new fields `active` (`boolean`) and `expired` (`datetime`) are added to `user` schema and will be checked at login time to allow or reject the user's login.
Currently, only administration pages for the **JSF UI** are availaable, **Web UI** to come soon.

# Requirements

Building requires the following software:

* git
* maven

# Build

```
git clone ...
cd nuxeo-active-user-login-module

mvn clean install
```

# Installation

```
nuxeoctl mp-install nuxeo-active-user-login-module/nuxeo-active-user-login-module-package/target/nuxeo-active-user-login-module-package-*.zip
```

# Support

**These features are not part of the Nuxeo Production platform, they are not supported**

These solutions are provided for inspiration and we encourage customers to use them as code samples and learning resources.

This is a moving project (no API maintenance, no deprecation process, etc.) If any of these solutions are found to be useful for the Nuxeo Platform in general, they will be integrated directly into platform, not maintained here.


# Licensing

[Apache License, Version 2.0](http://www.apache.org/licenses/LICENSE-2.0)


# About Nuxeo

Nuxeo dramatically improves how content-based applications are built, managed and deployed, making customers more agile, innovative and successful. Nuxeo provides a next generation, enterprise ready platform for building traditional and cutting-edge content oriented applications. Combining a powerful application development environment with SaaS-based tools and a modular architecture, the Nuxeo Platform and Products provide clear business value to some of the most recognizable brands including Verizon, Electronic Arts, Sharp, FICO, the U.S. Navy, and Boeing. Nuxeo is headquartered in New York and Paris.

More information is available at [www.nuxeo.com](http://www.nuxeo.com).
