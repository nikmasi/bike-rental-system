
<h1 align="center">Bike Rental System</h1></br>
<p align="center">  
Bike Rental System is used for managing a bicycle rental service.
The system consists of a mobile application for users and a web-based administrative panel.</p>

<p align="center">  
Users can rent and return bicycles, report issues, and upload photos of the bicycle’s condition.
Administrators can manage the bicycle fleet, monitor rentals, review reported problems, and make decisions regarding maintenance or temporary removal of bicycles from the system.
</p>
</br>

# Bike Rental Admin Panel (Kobweb)

A web-based administrative application for a bike rental system, developed using the Kobweb framework and JetBrains Compose for Web.
The application allows administrators to fully manage bicycles and monitor all rental activities through a user-friendly UI.

Data is currently stored in Local Storage.

## Features
Administrator functionalities

- Adding new bicycles to the system
- Editing existing bicycle data
- Changing bicycle status (availability and condition)
- Viewing all rentals
- Access to bicycle photos submitted by users during the return process
- Making decisions based on reported issues, such as:
  - sending a bicycle for maintenance
  - temporarily removing a bicycle from the system

## Tech Stack & Libraries
Core
- 100% Kotlin
- Kobweb – a framework for Compose Multiplatform Web applications
- JetBrains Compose for Web – declarative UI framework

Versions
```bash
[versions]
jetbrains-compose = "1.8.0"
kobweb = "0.23.3"
kotlin = "2.2.20"
```

State & Data
- Local Storage – used for storing bicycle and rental data
- Compose state management (remember, mutableStateOf)

## Screenshots
<p align="center">
<img src="/preview/login-kobweb.png" width="270"/>
<img src="/preview/bikes-kobweb.png" width="270"/>
<img src="/preview/add-bike-kobweb.png" width="270"/>
<img src="/preview/rentals-kobweb.png" width="270"/>
</p>


## How to Run

Clone the repository

Run the Kobweb project:
```bash
cd kobweb-project/project/site
kobweb run
```

Open the application in your browser at:

```bash
http://localhost:8080
```

# Bike Rental Mobile App (Android)

The user-facing mobile application for the Bike Rental System.

## Features

User functionalities

- Interactive Map: Locate nearby bike stations.
- QR Code Scanner
- Active Ride Tracking: Real-time dashboard showing ride duration, bike details, and current station.
- Station Details: View pricing (classic vs. electro) and capacity for each location.

## Tech Stack & Libraries

Core

- Kotlin
- Jetpack Compose

Architecture & DI

- MVVM Architecture
- Hilt 
- Navigation Compose

Storage

- Room 
- ViewModels

## Screenshots

<p align="center">
<img src="/preview/welcome-page.png" width="250"/>
<img src="/preview/my-profile.png" width="250"/>
<img src="/preview/map-page.png" width="250"/>
<img src="/preview/station-details-page.png" width="250"/>
</p>

## How to Run

1. Clone the repository
2. Open in Android Studio
3. Run the App