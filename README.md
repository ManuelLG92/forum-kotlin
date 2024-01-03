# CQRS Backend Service for Forum

This project is a proof-of-concept (POC) introduction to CQRS (Command Query Responsibility Segregation) implemented using Kotlin, Gradle, and Spring Boot. The backend service offers fundamental functionalities for a forum environment.

## Overview

For the sake of simplicity, there's no database; it's an in-memory implementation with default values. Please be aware that any changes will be lost after restarting the service.

The backend service embodies CQRS principles by segregating data write operations from the process of building projections through events. The functionality is structured as follows:

- **User Operations:**
    - User updates trigger updates in corresponding projections.
    - User creation events generate associated projections.
    - Deletion of a user results in the removal of their data, including posts.
    - Listing users with pagination.

- **Post Operations:**
    - Posts initiate the creation of interconnected projections for each post and its creator.
    - Updates to posts reflect in the respective projections.
    - Post deletion removes the post from single-post projections and user-post projections.
    - Listing posts with pagination.

## Architecture

This project is a demonstrative example intended for educational purposes. It may contain code smells or suboptimal architectural choices. Its primary objective is to showcase the implementation of CQRS principles within a backend service for a forum.

## Technologies Used

- Kotlin
- Gradle
- Spring Boot
- Docker

## Disclaimer

This implementation is not intended as a definitive source of truth and was developed during leisure time. It may contain imperfections and is not recommended for production use.

## Installation and Usage

- For easy setup in IntelliJ IDEA, open and run the project using the IDE's run button located next to the main function.
- The Dockerfile is ready for deployment. I've deployed on [Render](https://render.com/), intended solely for educational purposes.

## Frontend Project

For the frontend component of this forum project, please refer to the [Next.js Frontend Repository](https://github.com/ManuelLG92/next-forum) for details on the frontend implementation.

## Contributing

Your contributions are welcome! To contribute, fork the repository, make your changes, and open a pull request. Let's maintain simplicity without overengineering the project.

## License

This project is licensed under the MIT License.
