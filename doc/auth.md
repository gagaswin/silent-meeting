<h1 style="text-align: center;">
  Auth API
</h1>

---

## Register User

**POST** `/api/v1/auth/register`

- **Request Body:**

```json
{
  "username": "gagaswin",
  "firstName": "gagas",
  "lastName": "noto",
  "email": "gagaswin@example.com",
  "password": "gagaswin123",
  "phone": "078485455", // optional
  "dateOfBirth": "2000-03-27", // optional
  "address": "jalan kemerdekaan", // optional
  "company": "SpaceX", // optional
  "lastEducation": "BACHELORS_DEGREE", // optional
  "lastInstitutionName": "Institut Teknologi Indonesia"// optional
}
```

- **Response Body:**

```json
{
  "statusCode": 201,
  "message": "Register success !!!",
  "data": {
    "accessToken": "access_token",
    "refreshToken": "refresh_token"
  }
}
```

- **Response Body (Error):**

```json

```

## Login

**POST** `/api/v1/auth/login`

- **Request Body:**

```json
{
  "username": "gagaswin",
  "password": "gagaswin123"
}
```

- **Response Body:**

```json
{
    "statusCode": 200,
    "message": "Login success !!!",
    "data": {
        "accessToken": "accessToken",
        "refreshToken": "refreshToken"
    }
}
```

- **Response Body (Error):**

```json

```

## Refresh Token

**POST** `/api/v1/auth/refresh`

- **Request Body:**

```json
{
  "refreshToken": "refreshToken"
}
```

- **Response Body:**

```json
{
    "statusCode": 200,
    "message": null,
    "data": {
        "accessToken": "accessToken",
        "refreshToken": "refreshToken"
    }
}
```

- **Response Body (Error):**

```json

```