package com.Hotels.YourHome.controller;


import com.Hotels.YourHome.customException.ControllerException;
import com.Hotels.YourHome.jwt.Jwt;
import com.Hotels.YourHome.models.User;
import com.Hotels.YourHome.models.UserLogin;
import com.Hotels.YourHome.projections.UserView;
import com.Hotels.YourHome.service.UserService;
import com.Hotels.YourHome.validators.OnlyWords;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.Pattern;
import java.io.IOException;
import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;


@RestController @RequestMapping("/api")
public class UserController {


    @Autowired
    UserService userService;

    @Autowired
    UserDetailsService userDetailsService;

    @Autowired
    Jwt jwt;

    @Autowired
    AuthenticationManager authenticationManager;



    @PostMapping("user-management/user")
    @ApiOperation(value = "Add User", notes = "Id,updatedAt,createdAt will be Auto Generated Please remove fields")
    public ResponseEntity<?> user(@Valid @RequestBody User user) {
        userService.addUser(user);
        HttpHeaders headers = new HttpHeaders();
        headers.add("desc", "Add user");
        Map<String,Object> body = new HashMap<>();
        body.put("Status","Created user");
        body.put("Details",user);
        return ResponseEntity.status(HttpStatus.CREATED).headers(headers).body(body);
    }

    @DeleteMapping("user-management/{id}/delete")
    @ApiOperation(value = "Delete User By id")
    public ResponseEntity<?> delete(@PathVariable("id") @Pattern(regexp = "^[0-9]+$", message = "Only numbers allowded!") String id) {
        userService.delete(Integer.parseInt(id));
        HttpHeaders headers = new HttpHeaders();
        headers.add("desc", "Delete by id");
        return ResponseEntity.status(HttpStatus.OK).headers(headers).body("Deleted!");
    }

    @GetMapping("user-management/users")
    @ApiOperation(value = "Get All Users")
    @PreAuthorize("hasAnyAuthority('user')")
    public ResponseEntity<?> getAllUser(HttpServletResponse response, Principal p) throws IOException {
        List<UserView> users = userService.getUser();
        HttpHeaders headers = new HttpHeaders();
        headers.add("desc", "Get all the Users");
        return ResponseEntity.status(HttpStatus.OK).headers(headers).body(users);
    }

    @GetMapping("admin/user-management/user")
    @ApiOperation(value = "Get User by name")
    @OnlyWords
    public ResponseEntity<?> getUserByName(@RequestParam("name") String name, HttpServletRequest request) {

        Optional<UserView> user1 = userService.getUserByName(name);
        HttpHeaders headers = new HttpHeaders();
        headers.add("desc", "Getting user by name");
        return ResponseEntity.status(HttpStatus.OK).headers(headers).body(user1);
    }

    @PutMapping("user-management/{id}/update")
    @ApiOperation(value = "Update any field but password is mandatory")
    public ResponseEntity<?> update(@PathVariable("id") @Pattern(regexp = "^[0-9]+$", message = "Only numbers allowded!") int id, @RequestBody User user) {
        userService.updateUser(id, user);
        HttpHeaders headers = new HttpHeaders();
        headers.add("desc", "Updating address value only");
        return ResponseEntity.status(HttpStatus.OK).headers(headers).body("Updated!");
    }

    @GetMapping("/refreshToken")
    @ApiOperation(value = "Refresh")
    public void Login(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String token = request.getHeader(AUTHORIZATION);
        if (token != null) {
            try {
                String userName = jwt.getClaims(token).getSubject();
                UserDetails user = userDetailsService.loadUserByUsername(userName);
                String atoken = jwt.generate(userName, user);
                Map<String, String> body = new HashMap<String, String>();
                body.put("User Name", user.getUsername());
                body.put("Access Token", atoken);
                body.put("Refresh Token",token);
                body.put("Expiration", jwt.getExpDate(token).toString());
                body.put("IsExpired", String.valueOf(jwt.isexpired(token)));
                response.setContentType(APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(),body);
            } catch (Exception e) {
                Map<String, String> error = new HashMap<String, String>();
                error.put("Error", e.getMessage());
                response.setStatus(401);
                response.setContentType(APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(), error);
            }
        } else {
            throw new ControllerException(404,"Not Valid refresh token");
        }

    }

    @GetMapping("user-management/welcome")
    public ResponseEntity<String> accessData(Principal p) {
        try {
            return ResponseEntity.ok("Hello " + p.getName());
        } catch (Exception e) {
            e.printStackTrace();
            throw new ControllerException(401, "Login Please! ");
        }

    }

}
