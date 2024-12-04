| Endpoint | Method | Request | Response |
|----------|--------|---------|----------|
| `/students/add` | POST | ```json
{
"mshs": "string",
"fullName": "string",
"dob": "YYYY-MM-DD",
"gender": "string",
"email": "string",
"address": "string"
}
``` | ```json
{
"status": "success/error",
"studentId": "string"
}
``` |

### Search Students
| Endpoint | Method | Request | Response |
|----------|--------|---------|----------|
| `/students/search` | GET | ```json
{
  "searchTerm": "string",
  "searchType": "mshs/name/email"
}
``` | ```json
{
  "students": [
    {
      "mshs": "string",
      "fullName": "string",
      "class": "string",
      "academicYear": "string"
    }
  ]
}
``` |

## Class Management APIs

### Create Class
| Endpoint | Method | Request | Response |
|----------|--------|---------|----------|
| `/classes/create` | POST | ```json
{
  "className": "string",
  "academicYear": "string",
  "grade": "10/11/12"
}
``` | ```json
{
  "status": "success",
  "classId": "string"
}
``` |

## Grading APIs

### Add Student Grades
| Endpoint | Method | Request | Response |
|----------|--------|---------|----------|
| `/grades/add` | POST | ```json
{
  "studentId": "string",
  "subject": "string",
  "gradeTypes": {
    "15minGrade": "float",
    "1periodGrade": "float"
  }
}
``` | ```json
{
  "status": "success",
  "averageGrade": "float"
}
``` |

## Reporting APIs

### Subject Performance Report
| Endpoint | Method | Request | Response |
|----------|--------|---------|----------|
| `/reports/subject-summary` | GET | ```json
{
  "subject": "string",
  "academicYear": "string",
  "semester": "string"
}
``` | ```json
{
  "classes": [
    {
      "className": "string",
      "totalStudents": "int",
      "passedStudents": "int",
      "passRate": "float"
    }
  ]
}
``` |

## Configuration APIs

### Update Student Rules
| Endpoint | Method | Request | Response |
|----------|--------|---------|----------|
| `/settings/student-rules/update` | PUT | ```json
{
  "minAge": "int",
  "maxAge": "int"
}
``` | ```json
{
  "status": "success",
  "newRules": {
    "minAge": "int", 
    "maxAge": "int"
  }
}
``` |

## Authentication API

### Logout
| Endpoint | Method | Request | Response |
|----------|--------|---------|----------|
| `/auth/logout` | POST | ```json
{
  "userId": "string"
}
``` | ```json
{
  "status": "success"
}
``` |