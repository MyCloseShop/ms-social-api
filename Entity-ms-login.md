```plantuml
class JwtUserDetails {
   boolean enabled
   boolean accountNonLocked
   String password
   boolean credentialsNonExpired
   boolean accountNonExpired
   Collection~GrantedAuthority~ authorities
   String username
}
class Role {
   String roleName
   int roleId
   Instant updatedAt
   Set~User~ users
   Instant createdAt
}
class User {
   String passwordHash
   String lastName
   String email
   String phoneNumber
   UUID userId
   String firstName
   String username
   Instant updatedAt
   Set~Role~ roles
   Instant createdAt
}
class UserRole {
   Role role
   UserRoleId id
   User user
}
class UserRoleId {
   int roleId
   UUID userId
}

JwtUserDetails "1" *--> "user 1" User 
Role "1" *--> "users *" User 
User "1" *--> "roles *" Role 
UserRole "1" *--> "role 1" Role 
UserRole "1" *--> "user 1" User 
UserRole "1" *--> "id 1" UserRoleId 
```