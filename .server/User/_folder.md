```toml
name = 'User'
sortWeight = 2000000
id = '41421af4-269c-4219-835e-010ab921e7ce'

[[headers]]
key = 'Accept'
value = '*/*'
disabled = true
```

#### Variables

```json5
{
  host : "{{host}}/users",
  user : {
    "name": "\\{{$randomFullName}}\\",
    "age": "\\{{$randomInt(45, 64)}}\\"
  }
}
```
