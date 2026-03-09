# Use Case : Add Friends

## Main Flow

### Preconditions
* The user is registered and authenticated
* At least other user is registered aun authenticated

### Main Event Flow
1. The user clicks/taps onto the "search" bar on top of the sreen
2. The user introduces the username of the person who wants to become friends with
3. The user clicks on the user and enters its profile
4. Once in the profile, the user clicks on the "send friend request"
5. The other user accept the request and they both become friends, the number of friends of both increment by 1

## Alternative Event Flow:

### A1. Username not found (mistake/not existing)
* At step 3, if the system can't find anybody with that username
1. The system shows an error message "username not found/not existing. You may be refering to..."
2. The system then shows users with similar username to what the user has introduced in the search bar

### A2. Users were already friends
*At step 4, if both users were already friends
1. The button shows "stop friendship" instead of "send friend request"

### A3. Request nor accepted nor denied
* At step 5, if the other user doesn't interact with the request
1. The system won't forget the request and will always be shown to the other user until he interacts with it
2. Until interacting, he will always be able to deny or accept it

### A4. Request not accepted 
* At step 5, if the other user denys the request
1. The request is marked as denied and erased to both users
2. The user may sent a request again

