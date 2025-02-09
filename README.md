# Yas – yet another subscription tracker

Yas[^*] is a Spring Boot based web application designed to help users track their subscriptions in an organized and user-friendly way. Whether you're trying to manage recurring costs or keep track of multiple subscriptions, yas will help you stay on top of your finances.

[^*] all letters lowercase, except at the beginning of the sentence

## Foreword

Yas is currently not yet fully developed with all the functions required for the first release. The functions listed below show the target status.

## Features

- **No sign-in required or user accounts**  
  You can use yas without creating an account, or choose to sign in for a more personalized experience.

- **OAuth authentication**  
  Users can authenticate via OAuth (Google, GitHub, etc.) for easier access.

- **Role-based access control**  
  Different user roles and permissions can be assigned after signing in, giving more control over who can see or modify subscription data.

- **Detailed descriptions of subscriptions**  
  Subscriptions include detailed descriptions with links, images, and markdown files to explain the subscription items clearly.

- **Summing up subscriptions**  
  yas will automatically sum your subscription expenses, giving you an overview of your monthly or annual subscription costs.

- **Billing dates**  
  Each subscription will display the date it is charged, allowing you to track when each payment is due.

- **Payment accounts**  
  You'll also see which account the subscription is billed to, so you can keep track of your spending on different payment methods.

- **API**
  All content are accessible and modifiable via a REST API.

- **Reminders and webhook calls**
  Reminders can be set for individual subscriptions (e-mail). Alternatively, a webhook can also be called. The time of both can be configured.

## Technology Stack

- **Spring Boot**: Backend framework
- **Bootstrap**: For responsive front-end design and UI
- **Databases**:
  - MySQL
  - PostgreSQL
  - SQLite (for lightweight local development)
  - H2 (for development purposes)
- **Liquibase**: For database version control and schema management
- **OAuth2**: For secure user authentication

# Getting Started

Yas will be provided as both a Docker image and a Helm chart.

## Contribution

Contributions are welcome! Please fork the repository, create a feature branch, and submit a pull request with your changes. Make sure to write tests for your changes and ensure that the existing functionality is not broken.

## License
This project is licensed under the MIT License - see the LICENSE file for details.

## Contact
For any questions, feel free to open an issue or contact the project maintainer.
