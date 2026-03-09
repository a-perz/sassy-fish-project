# Use Case : User Login

## Main Flow

### Main Event Flow
1. The user introduces his username/e-mail and password and clicks on "login" button
2. The system searches if the acredditations are correct
3. The system sends a A2F code to the user's telephone 
4. The user introduces the code within 60 seconds
5. The system validates the code
6. The user can access to its own profile and the application

## Alternative Event Flow:

### A1. User not registered
* At step 1, if the user it's not registered yet
1. User clicks on "Register button"
2. He fills information about himself: name, age, country, e-mail and chooses a username and a password
3. The system sends a confirmation email
4. The user confirms the email and the accound is created

### A2. Wrong username/password
* At step 2, if the system doesn't recognice introduced credentials
1. An error is shown "incorrect username or password, please check and try again"
2. The user can introduce his username and password again

### A3. Wrong A2F code
* At step 3, if the user introduces a wrong A2F code
1. The system shows an error "Wrong A2F code. Try again"
2. The user can introduce A2F code again

### A4. 60 second passed since A2F code
* At step 3, if more that 60 seconds passed since the A2F code has passed
1. The system shows an error "No A2F code introduced"
2. The login attempt is cancelled
3. The same device won't be able to login for 10 minutes