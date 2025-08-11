```toml
name = 'Project'
id = '9a9c66a6-ed46-4a09-86e3-909ad8cf8d45'

[[environmentGroups]]
name = 'Default'
environments = ['Local', 'Prod']
```

#### Variables

```json5
{
  globals: {
    baseUrl : "{{host}}/alal"
  },
  local: {
    host : "http://0.0.0.0:8080"
  },
  prod: {
    host : "http://0.0.0.0:8080"
  }
  
}
```
