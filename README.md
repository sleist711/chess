# ♕ BYU CS 240 Chess

This project demonstrates mastery of proper software design, client/server architecture, networking using HTTP and WebSocket, database persistence, unit testing, serialization, and security.

## 10k Architecture Overview

The application implements a multiplayer chess server and a command line chess client.

[![Sequence Diagram](10k-architecture.png)](https://sequencediagram.org/index.html#initialData=C4S2BsFMAIGEAtIGckCh0AcCGAnUBjEbAO2DnBElIEZVs8RCSzYKrgAmO3AorU6AGVIOAG4jUAEyzAsAIyxIYAERnzFkdKgrFIuaKlaUa0ALQA+ISPE4AXNABWAexDFoAcywBbTcLEizS1VZBSVbbVc9HGgnADNYiN19QzZSDkCrfztHFzdPH1Q-Gwzg9TDEqJj4iuSjdmoMopF7LywAaxgvJ3FC6wCLaFLQyHCdSriEseSm6NMBurT7AFcMaWAYOSdcSRTjTka+7NaO6C6emZK1YdHI-Qma6N6ss3nU4Gpl1ZkNrZwdhfeByy9hwyBA7mIT2KAyGGhuSWi9wuc0sAI49nyMG6ElQQA)

## IntelliJ Support

Open the project directory in IntelliJ in order to develop, run, and debug your code using an IDE.

## Maven Support

You can use the following commands to build, test, package, and run your code.

| Command                    | Description                                     |
| -------------------------- | ----------------------------------------------- |
| `mvn compile`              | Builds the code                                 |
| `mvn package`              | Run the tests and build an Uber jar file        |
| `mvn package -DskipTests`  | Build an Uber jar file                          |
| `mvn install`              | Installs the packages into the local repository |
| `mvn test`                 | Run all the tests                               |
| `mvn -pl shared tests`     | Run all the shared tests                        |
| `mvn -pl client exec:java` | Build and run the client `Main`                 |
| `mvn -pl server exec:java` | Build and run the server `Main`                 |

These commands are configured by the `pom.xml` (Project Object Model) files. There is a POM file in the root of the project, and one in each of the modules. The root POM defines any global dependencies and references the module POM files.

### Running the program using Java

Once you have compiled your project into an uber jar, you can execute it with the following command.

```sh
java -jar client/target/client-jar-with-dependencies.jar

♕ 240 Chess Client: chess.ChessPiece@7852e922
```
### UML Server Diagram
https://sequencediagram.org/index.html?presentationMode=readOnly#initialData=IYYwLg9gTgBAwgGwJYFMB2YBQAHYUxIhK4YwDKKUAbpTngUSWDAEooDmSAzmFMARDQVqhFHXyFiwUgBF+wAIIgQKLl0wATeQCNgXFDA3bMmdLwCeXXETTsYABgB0ADkzsoEAK7YYAYjTAVOasHNy8-EiCfgDuABZIYGKIqKQAtAB85JQ0UABcMADaAAoA8mQAKgC6MAD0nvpQADpoAN71lAEAtigANDC4atHQGn0oncBICAC+mMI5MBkhnDx8AkLZovlQoTyUABTtUF29-XpcQ1AjMGMTCACUmGzL4WtzoguZcmCKyqpc+ewUGAAKoNA4NY4PL4-FRqD6GbT5MgAUQAMsi4OUYIdjjAAGYeTrYhqYaFKWFcBaLJ5hVaRdYiFT5NCeBAIR47F70t4qeFk35qfIgbb8FCg-Y44DdPoDc7DKHycl-eFGfIASQAciiWFjJdLToNhqNxpMYJryiVibQaSsIoIeQZFvyKUKRYkFJ4wLFwR0pSgFd8lXDFqqzVrkTqrUc-X1gJ7YuUIABrdBhi0wONe0mKgWU1LUzl0+0bJkZ+OJlNoDnPIsMqjvfOZOaUfKZhPJ9CzbKUKmZZJmfIAJns9maLTbFfQM3QGhMbg83j80GkgJgqIgnDQMXiiUw-bSTe7eUKpQq1Rq+jU9LHepOsouGhmzdgGRtXOLjJQ+QQG6QaB90b6ve8rVrSdp1g2nw5i6MCAiCYKHBqfoBjCyohoi5BohiuoQn6+KElGJjOmhBY1uBDr5IcoG2q8JaOukxGCjAICxCgIBJkUZwPgBMpcSBjF5pkoYouimIGnKlz4RARJUQJvZLGBtGfq25YdlWb61g68nPipXqTlWz7yfuYBDiOY4Tmp05oLO85eD4vjuCgqbruwXjML4cQJEkyBmPCOmFDIWHlMi56Xlw14BKplZdtQPakYp3J0d+G5uXsFmVg8GnkXRfLQX8AJAh6XoAUh3QoUGgkIkiWFiW2kCVlJRJttASAAF7gdmga5vJWVKfWpbNVAbUdb1iWfrlXUwRoKAIECKBFd6g3DWs5Xdeh+SBeiwVll6LXtWsnWocG8U0WN-VfjALJstR74QbyjZZLFx5XeyhkPcZpn2KY1kmO4dl+NsGhrmEMAAOJ+pSHk7t5KTMIs-kFKDyJVLU7B+s06Wdm9mSjR+53fmE4PdFwaVRegmWFtl41OnlTFwQtpN6Wpq0UiqGEidhO2xPVqYEtJXN7R1ckPbjd0XUt+30jdmk5TTk35bBQJEygqJhHsLNoUJ7M1ViaPEwUlSHRVvaixReuqAb0tU+d2lHgCEOW29iwfTAw6jq05tcAbVk2X9i6+NgnhQNgs3wG6BjK9uXl7j5B6PTk+TFGUKM1ObGNk2gY7m6VKBPkeHym0lzHh8rjPtpWfTZ8hVt9ZBAkFWADOY2gGvBlr1WiVizeNQLQ2S4IRtrSdt0Ub3y1S4X1NQfLTHCigoql1XZWD6z61htqut+jnaaWubNdnZBMD18X8+JMraoyOrK+a1V68Rpv3QXzvsF+tfx045TtelkvJzmxf+94wbPDO2L9ug50rn6f+Ts+yxxMq7MyrQABEP9EEwHto-GQPtfoLnsuYGaP5ogwAAFIQD-GDPCvhtAIFAEmGOsM-IgOKMCFOadIpM0rGOYycAIA-igBAjBUwYAxXmMPGWykYAACtSFoFLs3PoXCeHQH4SgC+FMyJf3okfWm-xFaN3jGXfSrdKrCR1lzfSPcJZC20T1T+B8BrxkFgdSeNs5ZHR0XBRekCZBGLZh3Tmf8ZA9z3sLUR1tNigLEM4w+x9vBaDPn6PYAT5GwO4bwnxa9NQb3gCkxRsBzS71fiE9IUSmQvQAWLW2T1mSslevnd6sDPrfV9jgvw7hgDBEQPPWAwBsAh0IOBKOu5jIMKqQFIKIVahGGEXFD+6i7EXRALNPAV8SmaOPtNWaiRxRQCvsLduR9xlRmOLxQ0lxjS3D6LJaxItbGANLGU1ZE03H5A2XNUu6T9mbWRsiCJF8+gAHVobbOOTAAAQtQ9iwKYwRPAREt+eZXy3LFtU66jzXEVReTNOaDMPm3y+dtORRzCnXNCRolF7I0XANGWU6B2TYaNKaZgIAA
