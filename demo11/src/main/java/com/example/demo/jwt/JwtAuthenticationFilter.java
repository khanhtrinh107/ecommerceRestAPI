//package com.example.demo.jwt;
//
//import com.example.demo.security.MyUserDetailService;
//import com.example.demo.security.UserDetail;
//import jakarta.servlet.FilterChain;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
//import org.springframework.util.ObjectUtils;
//import org.springframework.util.StringUtils;
//import org.springframework.web.filter.OncePerRequestFilter;
//
//import java.io.IOException;
//
//@Slf4j
//public class JwtAuthenticationFilter extends OncePerRequestFilter {
//    @Autowired
//    private JwtTokenProvider jwtTokenProvider;
//    @Autowired
//    private UserDetailsService userDetailService;
//    private String getJwtFromRequest(HttpServletRequest request){
//        String bearer = request.getHeader("Authorization");
//        if(StringUtils.hasText(bearer) && bearer.startsWith("Bearer")){
//            return bearer.substring(7);
//        }
//        return null;
//    }
//    @Override
//    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
//        try{
//            String jwt = getJwtFromRequest(request);
//            if(StringUtils.hasText(jwt) && jwtTokenProvider.validateToken(jwt)){
//                String username = jwtTokenProvider.getUserNameFromToken(jwt);
//                UserDetails user =  userDetailService.loadUserByUsername(username);
//                if(!ObjectUtils.isEmpty(user)){
//                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(user,null,user.getAuthorities());
//                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
//                    SecurityContextHolder.getContext().setAuthentication(authentication);
//                }
//            }
//        }catch (Exception ex){
//            log.error("fail on set authentication user");
//        }
//        filterChain.doFilter(request,response);
//    }
//}
