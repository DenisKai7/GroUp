# User Api Spec

## Register User Api

Endpoint: POST api/users/register

Requset Body:

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

Endpoint: POST api/users/login

Requset Body:

```json
{
  "email": "yuki@gmail.com",
  "Password": "*****"
}
```

Requset Body Success:

```json
{
  "data": {
    "accessToken": "jwt_access_token",
    "refreshToken": "jwt_refresh_token"
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

Endpoint: PATCH api/users/current

- Authorization: Bearer "your_accessToken"

Requset Body:

```json
{
  "email": "yuki@gmail.com", //optional
  "Password": "***** new", //optional
  "name": "yuki new" //optional
}
```

Requset Body Success:

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

Endpoint: GET api/users/current

Headers:

- Authorization: Bearer "your_accessToken"

Requset Body:

```json
{
  "email": "yuki@gmail.com"
}
```

Requset Body Success:

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
  "errors": "User is Not Found"
}
```

## Refres Token User Api

Endpoint: POST api/users/refreshToken

Headers:

- Authorization: Bearer "your_refreshToken"

Requset Body Success:

```json
{
  "data": {
    "accessToken": "jwt_access_token",
    "refreshToken": "jwt_refresh_token"
  }
}
```

Response Body Erros:

```json
{
  "errors": "Unauthorized"
}
```

## Logout User Api

Endpoint: DELETE api/users/logout

Headers:

- Authorization: Bearer "your_accessToken"

Requset Body Success:

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
