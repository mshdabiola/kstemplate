```toml
name = 'SchoolServer'
id = '9d272541-7c98-483a-8812-406a0776c651'

[[environmentGroups]]
name = 'Default'
environments = ['Prod', 'Staging', 'Local']

[[environmentGroups]]
name = 'User'
environments = ['Admin', 'Owner']
```

#### Variables

```json5
{
  globals: {
    host : "{{host}}"
  },
  local: {
    host : "http://0.0.0.0:8080"
  },
  prod: {
    host : "https://school-server-dw5z.onrender.com"
  },
  staging: {
    host: "http://staging.com"
  }
  
}
```
