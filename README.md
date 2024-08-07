# FutureCloud-PlayerServiceManager

FutureCloud-PlayerServiceManager is a module for the FutureCloud API that monitors player activities on servers. It creates or updates services based on player names and sets the service status to INVISIBLE if the service has been inactive for 30 seconds.

## Features

- Monitors player activities on servers of a specific group.
- Creates or updates a service with the player's name in another group.
- Sets the service status to VISIBLE when the player joins.
- Sets the service status to INVISIBLE if the service has been inactive for 30 seconds.

## Prerequisites

- FutureCloud API
- Java 8 or higher
- Kotlin

## Installation

1. Clone the repository:

   ```sh
   git clone https://github.com/YourUsername/SimpleCloud-PlayerServiceManager.git
