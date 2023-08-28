# Design Document

# _river Pet Sitting Application_ Design

## 1. Problem Statement

_**river** is an application to provide pet owners with pet sitters._

## 2. Top Questions to Resolve in Review

_List the most important questions you have about your design, or things that you are still debating internally that you might like help working through._

1. Do we need a calendar or a list of dates as the calendar?
2. Should we have a user table at all? If so, should it have 1 table for users OR 1 table for owners and 1 for sitters?
3.

## 3. Use Cases

U1. _As a [pet owner] customer, I want to create a new reservation with an available [pet sitter]_

U2. _As a [pet owner] customer, I want to view(get) my active reservations_

U3. _As a [pet owner] customer, I want to update an active reservation_

U4. _As a [pet owner] customer, I want to delete/cancel an active reservation_

U5. _As a [pet owner] customer, I want to view my pet's profile_

U6. _As a [pet owner] customer, I want to update my pet's profile_

U7. _As a [pet owner] customer, I want to add a new pet to my list of pet profiles_


## 3.1 Stretch Use cases:
SU0. Add [pet sitter] functionality
  SU0.1 _As a [pet sitter] customer, I want to view upcoming reservations_
  
  SU0.2 _As a [pet sitter] customer, I want to delete/cancel an active reservation_

SU1. Reviews and rating for pets

SU2. Messaging between users (sitters and owners).

SU3. Reviews and rating for sitters 

SU4. Set availability 

SU5. Invites to accept a booking


# 4. Project Scope

### 4.1. In Scope

- creating, retrieving, updating, deleting a reservation.
- retrieving all reservations a sitter has.
- viewing pet profiles


### 4.2. Out of Scope

- the 3.1 stretch goals
- if pets run away, get hurt, or pets damage the sitter's home, etc.
- notifications when a pet is available to sit

# 5. Proposed Architecture Overview

This initial iteration will provide the minimum lovable product (MLP) including creating, retrieving, and updating reservations for pet sitting requests.
It will also allow users to set up and manage pet profiles.

We will use API Gateway and AWS Lambda to create the following endpoints: 
- CreateReservation
- GetReservation
- UpdateReservation
- CancelReservation
- GetPetProfile
- CreatePetProfile
- UpdatePetProfile

We will store reservation and pet data in separate DynamoDB tables.


# 6. API

## 6.1. Public Models

```
// ReservationModel
 
String reservationId
Date startDate
Date endDate
String status (enum, completed, upcoming, etc)

the user ids for sitter, owner, and pet(s)
```

```
// PetModel

String petId
String petName
Owner - maybe do not need

Stretch/ optional
pet photo
String breed
String age
etc
```

Optional model:
```
// UserModel

String userId
List<ReservationId>
List<petId>

Stretch 
Boolean isSitter
availabilty?
```

## 6.2 Endpoints

### 6.2.0 _Create Reservation Endpoint_
- Accepts `POST` requests to `/reservations`
- Accepts data to create a new reservation with a provided Pet owner ID, pet Sitter ID, pet ID, start date,
and end date. Returns the new reservation, including a reservation ID assigned by the service.

### 6.2.1 _Get Reservation Endpoint_
- Accepts `GET` requests to `/reservation/:id`
- Accepts a reservation ID and returns the corresponding ReservationModel
  - If the given reservation ID is not found, will throw a `ReservationNotFoundException`

### 6.2.2 _Cancel Reservation Endpoint_
- Accepts a `DELETE` request to `/reservation/:id`
- Accepts a reservation ID and returns the corresponding Deleted ReservationModel
  - If the given reservation ID is not found, will throw a `ReservationNotFoundException`

### 6.2.3 _Update Reservation Endpoint_
- Accepts a `PUT` request to `/reservation/:id`
- Accepts data to update a reservation including the updated start and end dates. returns the updated reservation
  - If the given reservation ID is not found, will throw a `ReservationNotFoundException`

### 6.2.4 _Get Pet Profile Endpoint_
- Accepts `GET` request to `/pets/:id`
- Accepts a Pet ID and returns the corresponding PetModel
    - If the given pet ID is not found, will throw a `PetIdNotFoundException`

### 6.2.5 _Create Pet Profile Endpoint_
- Accepts `POST` request to `/pets`
- Accepts data to create a new Pet with a provided  pet name, their pet owner's ID. returns the petModel with pet ID assigned by the service.
   

### 6.2.6 _Update Pet Profile Endpoint_
- Accepts `PUT` request to `/pets/:id`
- Accepts data to update a pet with a provided user ID to update desired fields. returns the updated userModel.
    - If the given Pet ID is not found, will throw a `PetIdNotFoundException`

# 7. Tables

_Define the DynamoDB tables you will need for the data your service will use. It may be helpful to first think of what 
objects your service will need, then translate that to a table structure, like with the *`Playlist` POJO* versus the
`playlists` table in the Unit 3 project._

## 7.1 `reservations`
```
id // partition key, string
ownerId // sort, key, String
startDate // string (converted date)
endDate // string (converted date)
status // String
petId // String

Stretch goal:
sitterId // String
```

## 7.2 `Pets`
```
id // partition key, string
ownerId // sort, key, String
petName // String

Stretch goal:
pet details
pet picture
```

# 8. Page storyboard

_Include mock-ups of the web pages you expect to build. These can be as sophisticated as mockups/wireframes using drawing software, or as simple as hand-drawn pictures that represent the key customer-facing components of the pages. It should be clear what the interactions will be on the page, especially where customers enter and submit data. You may want to accompany the mockups with some description of behaviors of the page (e.g. “When customer submits the submit-dog-photo button, the customer is sent to the doggie detail page”)_
