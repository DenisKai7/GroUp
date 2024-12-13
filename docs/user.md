# User Api Spec

## Register User Api

Endpoint: POST /api/users

Request Body:

```json
{
  "email": "yuki@gmail.com",
  "password": "*****",
  "name": "yuki"
}
```

Response Body Success:

```json
{
  "data": {
    "email": "yuki@gmail.com",
    "name": "yuki"
  }
}
```

Response Body Erros:

```json
{
  "errors": "Email Already Registered"
}
```

## Login User Api

Endpoint: POST /api/users/login

Request Body:

```json
{
  "email": "yuki@gmail.com",
  "Password": "*****"
}
```

Response Body Success:

```json
{
  "data": {
    "accessToken": "jwt_access_token",
    "status": "active"
  }
}
```

Response Body Erros:

```json
{
  "errors": "Email or Password Wrong"
}
```

## Update User Api

Endpoint: PATCH /api/users/current

- Authorization: Bearer "your_accessToken"

Request Body:

```json
{
  "Password": "***** new", //optional
  "name": "yuki new" //optional
}
```

Response Body Success:

```json
{
  "data": {
    "email": "yuki@gmail.com",
    "name": "yuki new"
  }
}
```

Response Body Erros:

```json
{
  "errors": "name max lenght 100"
}
```

## Get User Api

Endpoint: GET /api/users/current

Headers:

- Authorization: Bearer "your_accessToken"

Respone Body Success:

```json
{
  "data": {
    "email": "yuki@gmail.com",
    "name": "yuki new",
    "status": "active"
  }
}
```

Response Body Erros:

```json
{
  "errors": "User is Not Found"
}
```

## Logout User Api

Endpoint: DELETE /api/users/logout

Headers:

- Authorization: Bearer "your_accessToken"

Response Body Success:

```json
{
  "data": "OK"
}
```

Response Body Erros:

```json
{
  "errors": "Unauthorized"
}
```
