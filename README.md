# News-app

[![Build Status](https://travis-ci.org/muatik/news-app.svg?branch=master)](https://travis-ci.org/muatik/news-app)

This is a REST API application which exposes necessary functionalities to organize news articles. 

Basic features are:

 * create authors, update authors and delete them
 * create articles, update and delete them
 * get a specific article or all of them
 * filter articles by author id, date period or a keyword


## REST APIs

#### Create a new author
Request
```http
POST /api/v1/authors HTTP/1.1
Host: localhost:8080
Content-Type: application/json

{
	"email": "a1@a.com"
}
```

Response
```http
HTTP/1.1 201 Created
Content-Type: application/json

{
	"id": 1,
	"email": "a1@a.com"
}

```

#### Get authors list
Request
```http
GET /api/v1/authors HTTP/1.1
Host: localhost:8080


```

Response
```http
HTTP/1.1 200
Content-Type: application/json;charset=UTF-8


[
  {
    "id": 1,
    "email": "a1@a.com"
  },
  {
    "id": 2,
    "email": "a2@a.com"
  }
]
```

#### Get a specific author
Request
```http
GET /api/v1/authors/1 HTTP/1.1
Host: localhost:8080


```

Response
```http
HTTP/1.1 200
Content-Type: application/json

{
	"id": 1,
	"email": "a1@a.com"
}
```

#### Delete author
Request
```http
DELETE /api/v1/authors/1 HTTP/1.1
Host: localhost:8080


```

Response
```http
HTTP/1.1 204 No content
Content-Type: application/json


```

#### Update author
Request
```http
PUT /api/v1/authors HTTP/1.1
Host: localhost:8080

{
	"id": 1,
	"email": "a1@bbbbb.com"
}
```

Response
```http
HTTP/1.1 200 
Content-Type: application/json

{
	"id": 1,
	"email": "a1@bbbbb.com"
}
```


#### Create a new article
Request
```http
POST /api/v1/articles HTTP/1.1
Host: localhost:8080
Content-Type: application/json

{
  "header": "article header",
  "text": "lorem ipsum",
  "publishDate": "2017-01-01 21:20:30",
  "authors": [
    {
      "id": 1,
      "email": "a1@a.com"
    }
  ],
  "keywords": []
}
```

Response
```http
HTTP/1.1 201 Created
Content-Type: application/json

{
  "id": 1,
  "header": "article header",
  "text": "lorem ipsum",
  "publishDate": "2017-01-01 21:20:30",
  "authors": [
    {
      "id": 1,
      "email": "a1@a.com"
    }
  ],
  "keywords": []
}
```

#### Get articles list
Request
```http
GET /api/v1/articles HTTP/1.1
Host: localhost:8080


```

Response
```http
HTTP/1.1 200
Content-Type: application/json;charset=UTF-8

[
  {
    "id": 1,
    "header": "header 1",
    "text": "lorem ipsum",
    "publishDate": "2017-01-01 21:20:30",
    "authors": [
      {
        "id": 1,
        "email": "a1@a.com"
      }
    ],
    "keywords": [
    	"tech",
        "AI"
    ]
  },
  {
    "id": 2,
    "header": "header 2",
    "text": "lorem ipsum",
    "publishDate": "2017-01-03 12:10:41",
    "authors": [
      {
        "id": 1,
        "email": "a1@a.com"
      },
      {
        "id": 2,
        "email": "a2@a.com"
      }
    ],
    "keywords": [
    	"tech",
        "Mobile"
    ]
  }
]
```

#### Get a specific article
Request
```http
GET /api/v1/articles/1 HTTP/1.1
Host: localhost:8080

```

Response
```http
HTTP/1.1 200
Content-Type: application/json

{
  "id": 1,
  "header": "article header",
  "text": "lorem ipsum",
  "publishDate": "2017-01-01 21:20:30",
  "authors": [
    {
      "id": 1,
      "email": "a1@a.com"
    }
  ],
  "keywords": [
    	"tech",
        "Mobile"
    ]
}
```

#### Delete article
Request
```http
DELETE /api/v1/articles/1 HTTP/1.1
Host: localhost:8080


```

Response
```http
HTTP/1.1 204 No content
Content-Type: application/json


```

#### Update article
Request
```http
PUT /api/v1/articles HTTP/1.1
Host: localhost:8080


{
  "id": 1,
  "header": "a very fancy article header",
  "text": "lorem ipsum and doler sit ames",
  "publishDate": "2017-01-01 21:20:30",
  "authors": [
    {
      "id": 1,
      "email": "a1@a.com"
    }
  ],
  "keywords": [
    	"tech",
        "Mobile",
        "android"
    ]
}
```

Response
```http
HTTP/1.1 200 
Content-Type: application/json

{
  "id": 1,
  "header": "a very fancy article header",
  "text": "lorem ipsum and doler sit ames",
  "publishDate": "2017-01-01 21:20:30",
  "authors": [
    {
      "id": 1,
      "email": "a1@a.com"
    }
  ],
  "keywords": [
    	"tech",
        "Mobile",
        "android"
    ]
}
```



## Technology
This application is written in Java with the spring-boot framework and a few dependencies. Because this is a very basic application, there aren't many dependencies. For instance, as it wasn't required, I didn't have to consider caching, splitting API as write-API and read-API. 

As a database, HSQLDB is used because it is simple and easy, does not require much effort to run it. However, in production, you should consider switching to more robust RDBMS such as PostgreSQL, MySQL etc; or NoSQL DB's.

There are only two entities in the app, which represents articles and authors. And every entity has its own repository and service which uses the repository.

Only the required functionalities and data are exposed as RESTful APIs. 

## Testing
For articles and authors, some amount of unit-tests is written. They only check whether the unit works as expected in the expected situation. That means, testing extreme cases(such giving a negative number as id) and error handling tests are not complete.

To check the system as a whole, integration test for REST APIs are written. In simple terms, they assert CRUD operations on author and articles.

Travis.CI is used for continuous testing. [for builds](https://travis-ci.org/muatik/news-app)

To test:
```
mvn clean test
```

## Build

In order to build this application, run the following maven command.

```
mvn clean package
```


## Contributing

Contributions are welcome!

Review the [Contributing Guidelines](https://github.com/muatik/news-app/wiki/contribution) for details on how to:

* Submit issues
* Add solutions to existing challenges
* Add new challenges



## License

MIT
