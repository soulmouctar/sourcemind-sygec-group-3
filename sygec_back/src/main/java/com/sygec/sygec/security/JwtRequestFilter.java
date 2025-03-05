package com.sygec.sygec.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.sygec.sygec.serviceImpl.JwtUserDetailsService;

import io.jsonwebtoken.ExpiredJwtException;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

	@Autowired
	private JwtUserDetailsService jwtUserDetailsService;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
	        throws ServletException, IOException {

	    final String requestTokenHeader = request.getHeader("Authorization");
	    String username = null;
	    String jwtToken = null;

	    // Vérifiez si le token est présent et commence bien par "Bearer "
	    if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
	        jwtToken = requestTokenHeader.substring(7);
	        try {
	            username = jwtTokenUtil.getUsernameFromToken(jwtToken);
	        } catch (IllegalArgumentException e) {
	            logger.error("Impossible d'obtenir le token JWT");
	        } catch (ExpiredJwtException e) {
	            logger.warn("Le token JWT a expiré");
	        }
	    } else {
	        // Créer une liste des chemins publics qui ne nécessitent pas d'authentification
	        String path = request.getRequestURI();
	        boolean isPublicPath = path.contains("/api/auth/") || 
	                              path.equals("/") || 
	                              path.contains("/favicon.ico") || 
	                              path.contains("/actuator/") ||
	                              path.contains("/public/") ||
	                              path.startsWith("/css/") ||
	                              path.startsWith("/js/") ||
	                              path.startsWith("/images/");
	        
	        // Ne pas logger d'avertissement pour les chemins publics ou les requêtes OPTIONS
	        if (!isPublicPath && !request.getMethod().equals("OPTIONS")) {
	            // Utiliser DEBUG au lieu de WARN pour réduire le bruit dans les logs
	            logger.debug("Token JWT manquant ou invalide pour: " + path);
	        }
	    }

	    // Une fois que nous avons le token, validons-le
	    if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
	        UserDetails userDetails = this.jwtUserDetailsService.loadUserByUsername(username);

	        // Si le token est valide, configurez Spring Security
	        if (jwtTokenUtil.validateToken(jwtToken, userDetails)) {
	            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
	                    userDetails, null, userDetails.getAuthorities());
	            usernamePasswordAuthenticationToken
	                    .setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
	            
	            SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
	        }
	    }
	    chain.doFilter(request, response);
	}
}
