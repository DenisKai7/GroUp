# Model Api Spec

## POST Predicts Stunting Api

Endpoint: POST api/predicts/stunting

Requset Body:

```json
{
  "name": "test",
  "age": "16",
  "weight": "0.5",
  "height": "0.10",
  "gender": "Male/Female" // case sensitive
}
```

Respone Body Success:

```json
{
  "data": {
    "name": "test",
    "age": "16",
    "weight": "0.5",
    "height": "0.10",
    "gender": "Male",
    "status": "stunting"
  }
}
```

Response Body Erros:

```json
{
  "errors": "Age is not allow to be null"
}
```

## POST Predicts Stunting Api

Endpoint: POST api/predicts/similiarity

Requset Body:

```json
{
  "name": "test",
  "age": "16",
  "weight": "0.5",
  "height": "0.10",
  "gender": "Male/Female" // case sensitive
}
```

Respone Body Success:

```json
{
  "data": {
    "name": "test",
    "age": "16",
    "weight": "0.5",
    "height": "0.10",
    "gender": "Male",
    "status": "stunting"
  }
}
```

Response Body Erros:

```json
{
  "errors": "Weight is not allow to be null"
}
```
