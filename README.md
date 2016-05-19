Simple URL Shortner Service
===========================

This app exposes a simple API to create short URLs for longer ones and routes the requests to their specified destination.

## API

The API exposes a single endpoint to create a new short URL.

**Request**

```
POST /api/shorturl HTTP/1.1
```

```javascript
{
    "url": "https://www.corylucas.com"
}
```

**Response**

```javascript
{
    "shortUrl": "http://localhost:5000/dXATd3"
}
```

Following the link will result in a 301 to the original URL.