# Design Document

## _Project Title_ Design

## 1. Problem Statement

_**river** is an application to provide pet owners with pet sitters._

## 2. Top Questions to Resolve in Review

_List the most important questions you have about your design, or things that you are still debating internally that you might like help working through._

1. Do we need a calendar or a list of dates as the calendar?
2.
3.

## 3. Use Cases

U1. _As a [pet owner] customer, I want to create a new reservation with an available [pet sitter]_

U2. _As a [pet owner] customer, I want to view my active reservations_

U3. _As a [pet owner] customer, I want to view my pet's profile_

U4. _As a [pet owner] customer, I want to update an active reservation_

U5. _As a [pet owner] customer, I want to delete/cancel an active reservation_

U6. _As a [pet sitter] customer, I want to view upcoming reservations_

U7. _As a [pet sitter] customer, I want to delete/cancel an active reservation_

U8. _As a [pet owner] customer, I want to view a list of available sitters_

### 3.1 Stretch Use cases:
SU1. Reviews and rating for pets

SU2. Messaging between users (sitters and owners).

SU3. Reviews and rating for sitters 

SU4. set availability 


## 4. Project Scope

_Clarify which parts of the problem you intend to solve. It helps reviewers know what questions to ask to make sure you 
are solving for what you say and stops discussions from getting sidetracked by aspects you do not intend to handle in your design._

### 4.1. In Scope

_Which parts of the problem defined in Sections 1 and 2 will you solve with this design? This should include the base 
functionality of your product. What pieces are required for your product to work?_
_The functionality described above should be what your design is focused on. You do not need to include the design for any out of scope features or expansions._

- creating, retrieving, updating, deleting a reservation.
- retrieving all reservations a sitter has.
- viewing pet profiles


### 4.2. Out of Scope

_Based on your problem description in Sections 1 and 2, are there any aspects you are not planning to solve? 
Do potential expansions or related problems occur to you that you want to explicitly say you are not worrying about now?
Feel free to put anything here that you think your team can't accomplish in the unit, but would love to do with more time._
_The functionality here does not need to be accounted for in your design._

- the 3.1 stretch goals
- if pets run away, get hurt, or pets damage the sitter's home, etc.
- notifications when a pet is available to sit

# 5. Proposed Architecture Overview

_Describe broadly how you are proposing to solve for the requirements you described in Section 2. This may include 
class diagram(s) showing what components you are planning to build. You should argue why this architecture 
(organization of components) is reasonable. That is, why it represents a good data flow and a good separation of 
concerns. Where applicable, argue why this architecture satisfies the stated requirements._

This initial iteration will provide the minimum lovable product (MLP) including creating, retrieving, and updating reservations for pet sitting requests.
It will also allow users to set up and manage pet profiles.

We will use API Gateway and AWS Lambda to create the following endpoints: 
- CreateReservation
- GetReservation
- UpdateReservation
- CancelReservation
- GetUserProfile
- createUserProfile
- UpdateUserProfile
- GetPetProfile
- CreatePetProfile
- UpdatePetProfile

We will store reservation, user, and pet data in a DynamoDB table.


# 6. API

## 6.1. Public Models

_Define the data models your service will expose in its responses via your *`-Model`* package. These will be 
equivalent to the *`PlaylistModel`* and *`SongModel`* from the Unit 3 project._

```
// ReservationModel
 
String reservationId
Date startDate
Date endDate
String status (enum, completed, upcoming, etc)

the user ids for sitter, owner, and pet(s)
```

```
// UserModel

String userId
Boolean isSitter
List<Reservation>
List<pet>

availabilty?
```

```
// PetModel

String petId
String petName
Owner

optional
pet photo
String breed
String age
etc
```

## 6.2. _Create Reservation Endpoint_
- Accepts `POST` requests to `/reservations`
- Accepts data to create a new reservation with a provided Pet owner ID, pet Sitter ID, pet ID, start date,
and end date. Returns the new reservation, including a reservation ID assigned by the service.

## 6.3 _Get Reservation Endpoint_
- Accepts `GET` requests to `/reservation/:id`
- Accepts a reservation ID and returns the corresponding ReservationModel
  - If the given reservation ID is not found, will throw a `ReservationNotFoundException`

## 6.4 _Cancel Reservation Endpoint_
- Accepts a `DELETE` request to `/reservation/:id`
- Accepts a reservation ID and returns the corresponding Deleted ReservationModel
  - If the given reservation ID is not found, will throw a `ReservationNotFoundException`

## 6.5 _Update Reservation Endpoint_
- Accepts a `PUT` request to `/reservation/:id`
- Accepts data to update a reservation including the updated start and end dates. returns the updated reservation
  - If the given reservation ID is not found, will throw a `ReservationNotFoundException`

## 6.6 _Get User Profile Endpoint_
- Accepts `GET` request to `/users/:id`
- Accepts a user ID and returns the corresponding UserModel
    - If the given user ID is not found, will throw a `UserIdNotFoundException`

## 6.6 _Create User Profile Endpoint_
- Accepts `POST` request to `/users`
- Accepts data to create a new user with a provided username, if they are a sitter or not, empty list of reservations, 
and a list of pet IDs. returns the UserModel with user ID assigned by the service

## 6.7 _Update User Profile Endpoint_
- Accepts `PUT` request to `/users/:id`
- Accepts data to update a user with a provided user ID to update desired fields. returns the updated userModel.
    - If the given user ID is not found, will throw a `UserIdNotFoundException`

## 6.8 _Get Pet Profile Endpoint_
- Accepts `GET` request to `/pets/:id`
- Accepts a Pet ID and returns the corresponding PetModel
    - If the given pet ID is not found, will throw a `PetIdNotFoundException`

## 6.9 _Create Pet Profile Endpoint_
- Accepts `POST` request to `/pets`
- Accepts data to create a new Pet with a provided  pet name, their pet owner's ID. returns the petModel with pet ID assigned by the service.
   

## 6.10 _Update Pet Profile Endpoint_
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
startDate // string (converted date)
endDate // string (converted date)
sitterId // String
ownerId // String
status // String
```

## 7.2 `User`
```
id // partition key, string
isSitter // Boolean
reservations // List<Reservation>
pets // List<pet>
```

## 7.3 `Pets`
```
id // partition key, string
petName // String
ownerId // String
```
# 8. Pages

_Include mock-ups of the web pages you expect to build. These can be as sophisticated as mockups/wireframes using drawing software, or as simple as hand-drawn pictures that represent the key customer-facing components of the pages. It should be clear what the interactions will be on the page, especially where customers enter and submit data. You may want to accompany the mockups with some description of behaviors of the page (e.g. “When customer submits the submit-dog-photo button, the customer is sent to the doggie detail page”)_
