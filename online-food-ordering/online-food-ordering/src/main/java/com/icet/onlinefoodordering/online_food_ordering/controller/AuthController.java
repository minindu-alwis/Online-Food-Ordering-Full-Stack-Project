package com.icet.onlinefoodordering.online_food_ordering.controller;

import com.icet.onlinefoodordering.online_food_ordering.Util.USER_ROLE;
import com.icet.onlinefoodordering.online_food_ordering.config.JwtProvider;
import com.icet.onlinefoodordering.online_food_ordering.model.Cart;
import com.icet.onlinefoodordering.online_food_ordering.model.User;
import com.icet.onlinefoodordering.online_food_ordering.repository.CartRepository;
import com.icet.onlinefoodordering.online_food_ordering.repository.UserRepository;
import com.icet.onlinefoodordering.online_food_ordering.request.LoginRequest;
import com.icet.onlinefoodordering.online_food_ordering.response.AuthResponce;
import com.icet.onlinefoodordering.online_food_ordering.service.CartService;
import com.icet.onlinefoodordering.online_food_ordering.service.CustomerUserDetailsService;
import com.icet.onlinefoodordering.online_food_ordering.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtProvider jwtProvider;

    @Autowired
    private CustomerUserDetailsService customerUserDetailsService;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private CartService cartService;

    @PostMapping("/signup")
    public ResponseEntity<AuthResponce> createUserHandler(@RequestBody User user) throws Exception {
        User isEmailExist=userService.findByEmail(user.getEmail());
        if(isEmailExist!=null){
            throw new Exception("Email is alrey used with another account");
        }
        User createdUser=new User();
        createdUser.setEmail(user.getEmail());
        createdUser.setFullName(user.getFullName());
        createdUser.setRole(user.getRole());
        createdUser.setPassword(passwordEncoder.encode(user.getPassword()));

        User savedUser=userService.save(createdUser);
        Cart cart=new Cart();
        cart.setCustomer(savedUser);
        cartService.save(cart);

        Authentication authentication=new UsernamePasswordAuthenticationToken(user.getEmail(),user.getPassword());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt=jwtProvider.genarateToken(authentication);
        AuthResponce authResponce=new AuthResponce();
        authResponce.setJwt(jwt);
        authResponce.setMessage("register succsess");
        authResponce.setRole(savedUser.getRole());

        return new ResponseEntity<>(authResponce, HttpStatus.CREATED);
    }

    @PostMapping("/signin")
    public ResponseEntity<AuthResponce> signin(@RequestBody LoginRequest request){

        String userName= request.getEmail();
        String password=request.getPassword();

        Authentication authentication=authenticate(userName,password);

        Collection<? extends GrantedAuthority>authorities=authentication.getAuthorities();
        String role=authorities.isEmpty()?null:authorities.iterator().next().getAuthority();

        String jwt=jwtProvider.genarateToken(authentication);

        AuthResponce authResponce=new AuthResponce();
        authResponce.setJwt(jwt);
        authResponce.setMessage("register succsess");
        authResponce.setRole(USER_ROLE.valueOf(role));

        return new ResponseEntity<>(authResponce, HttpStatus.OK);

    }

    private Authentication authenticate(String userName, String password) {
        UserDetails userDetails=customerUserDetailsService.loadUserByUsername(userName);
        if(userDetails==null){
            throw new BadCredentialsException("invaild Username...");
        }
        if(!passwordEncoder.matches(password,userDetails.getPassword())){
            throw new BadCredentialsException("Invalid Password");
        }
        return new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
    }
}

